package com.github.stefanbirkner.editors.mapper;

/**
 * An {@code EmptyStringToNullMapper} maps between Strings. It is an identity
 * mapper with one exception: the empty String is mapped to a {@code null}
 * value.
 *
 * @author Stefan Birkner <mail@stefan-birkner.de>
 * @since 2.0.0
 */
public class EmptyStringToNullMapper implements Mapper<String> {
    private static final String EMPTY_STRING = "";

    public String getValueForText(String text) {
        return (EMPTY_STRING.equals(text)) ? null : text;
    }

    public String getTextForValue(String value) {
        return (value == null) ? EMPTY_STRING : value;
    }
}
