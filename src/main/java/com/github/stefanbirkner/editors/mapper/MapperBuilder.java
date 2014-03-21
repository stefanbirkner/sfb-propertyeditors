package com.github.stefanbirkner.editors.mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A {@code MapperBuilder} helps you to build a {@link Mapper} by specifying
 * the mapping for each pair of String and Object. This is mostly useful for
 * building {@link Mapper}s for tests.
 * <p/>
 * <pre>
 * MapperBuilder&lt;Long> builder = new MapperBuilder&lt;Long>();
 * builder.addMapping(1L, "one");
 * builder.addMapping(2L, "two");
 * Mapper&lt;Long> mapper = builder.toMapper();
 * mapper.getValueForText("one"); // returns 1
 * mapper.getTextForValue(2L); // returns "two"
 * </pre>
 * <p/>
 * By default every undefined value and text is mapped to {@code null}.
 * <p/>
 * <pre>
 * mapper.getValueForText("unknown"); // returns null
 * mapper.getTextForValue(20L); // returns null
 * </pre>
 * <p/>
 * You may specify a default text and value for unmapped values and texts.
 * <p/>
 * <pre>
 * builder.setDefaultValue(1000L);
 * mapper.getValueForText("unknown"); // returns 1000L
 *
 * builder.setDefaultText("undefined");
 * mapper.getTextForValue(20L); // returns "undefined"
 * </pre>
 * <p/>
 * You can build the {@link Mapper} with a single statement.
 * <p/>
 * <pre>
 * Mapper&lt;Long> mapper = new MapperBuilder&lt;Long>()
 *                               .addMapping(1L, "one")
 *                               .addMapping(2L, "two")
 *                               .setDefaultValue(1000L)
 *                               .setDefaultText("undefined")
 *                               .toMapper();
 *
 * @param <T> the type of the mapped values.
 * @author Stefan Birkner <mail@stefan-birkner.de>
 * @since 2.1.0
 */
public class MapperBuilder<T> {
    private final Map<T, String> textsForValues = new HashMap<T, String>();
    private T defaultValue = null;
    private String defaultText = null;

    /**
     * Adds a mapping between the specified value and the text.
     * <pre>
     * MapperBuilder&lt;Long> builder = new MapperBuilder&lt;Long>();
     * builder.addMapping(1L, "one");
     * Mapper&lt;Long> mapper = builder.toMapper();
     * mapper.getValueForText("one"); // returns 1
     * mapper.getTextForValue(1L); // returns "one"
     * </pre>
     */
    public void addMapping(T value, String text) {
        textsForValues.put(value, text);
    }

    /**
     * Sets the text for values that have no mapping. (@code null} is the default.
     * <pre>
     * MapperBuilder&lt;Long> builder = new MapperBuilder&lt;Long>();
     * builder.setDefaultText("default text");
     * Mapper&lt;Long> mapper = builder.toMapper();
     * mapper.getTextForValue(1L); // returns "default text"
     * </pre>
     */
    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }


    /**
     * Sets the value for texts that have no mapping. (@code null} is the default.
     * <pre>
     * MapperBuilder&lt;Long> builder = new MapperBuilder&lt;Long>();
     * builder.setDefaultValue(1L);
     * Mapper&lt;Long> mapper = builder.toMapper();
     * mapper.getValueForText("undefined"); // returns 1L
     * </pre>
     */
    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Mapper<T> toMapper() {
        return new MapBasedMapper<T>(textsForValues, defaultValue, defaultText);
    }

    private static class MapBasedMapper<T> implements Mapper<T> {
        private final Map<String, T> valuesForTexts = new HashMap<String, T>();
        private final Map<T, String> textsForValues;
        private final String defaultText;
        private final T defaultValue;

        public MapBasedMapper(Map<T, String> textsForValues, T defaultValue, String defaultText) {
            this.defaultValue = defaultValue;
            this.textsForValues = new HashMap<T, String>(textsForValues); //prevent modification
            this.defaultText = defaultText;
            for (Entry<T, String> valueAndText : textsForValues.entrySet())
                valuesForTexts.put(valueAndText.getValue(), valueAndText.getKey());
        }

        @Override
        public T getValueForText(String text) {
            T value = valuesForTexts.get(text);
            return defaultIfNull(value, defaultValue);
        }

        @Override
        public String getTextForValue(T value) {
            String text = textsForValues.get(value);
            return defaultIfNull(text, defaultText);
        }

        private <S> S defaultIfNull(S value, S valueIfNull) {
            return (value == null) ? valueIfNull : value;
        }
    }
}
