package com.github.stefanbirkner.editors.mapper;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MapperBuilderTest {
    private static final Long DUMMY_VALUE = 23453534324374L;
    private static final Long UNDEFINED_VALUE = DUMMY_VALUE + 1;
    private final MapperBuilder<Long> builder = new MapperBuilder<Long>();

    @Test
    public void mapsTextToSpecifiedValue() {
        builder.addMapping(DUMMY_VALUE, "dummy text");
        assertBuilderCreatesMapperWhichMapsTextToValue("dummy text", DUMMY_VALUE);
    }

    @Test
    public void mapsValueToSpecifiedText() {
        builder.addMapping(DUMMY_VALUE, "dummy text");
        assertBuilderCreatesMapperWhichMapsValueToText(DUMMY_VALUE, "dummy text");
    }

    @Test
    public void mapsUndefinedTextToNull() {
        builder.addMapping(DUMMY_VALUE, "dummy text"); //mapping of another text
        assertBuilderCreatesMapperWhichMapsTextToValue("undefined text", null);
    }

    @Test
    public void mapsNullForUndefinedValue() {
        builder.addMapping(DUMMY_VALUE, "dummy text"); //mapping of another value
        assertBuilderCreatesMapperWhichMapsValueToText(UNDEFINED_VALUE, null);
    }

    @Test
    public void mapsUndefinedTextToCustomDefaultValue() {
        builder.setDefaultValue(DUMMY_VALUE);
        assertBuilderCreatesMapperWhichMapsTextToValue("undefined text", DUMMY_VALUE);
    }

    @Test
    public void mapsUndefinedValueToCustomDefaultText() {
        builder.setDefaultText("default text");
        assertBuilderCreatesMapperWhichMapsValueToText(UNDEFINED_VALUE, "default text");
    }

    @Test
    public void cannotModifyMapperAfterCreation() {
        builder.addMapping(DUMMY_VALUE, "text before creation");
        Mapper<Long> mapper = builder.toMapper();
        builder.addMapping(DUMMY_VALUE, "text after creation");
        String text = mapper.getTextForValue(DUMMY_VALUE);
        assertThat(text, is(equalTo("text before creation")));
    }

    private void assertBuilderCreatesMapperWhichMapsTextToValue(String text, Long expectedValue) {
        Mapper<Long> mapper = builder.toMapper();
        Long value = mapper.getValueForText(text);
        assertThat(value, is(equalTo(expectedValue)));
    }

    private void assertBuilderCreatesMapperWhichMapsValueToText(Long value, String expectedText) {
        Mapper<Long> mapper = builder.toMapper();
        String text = mapper.getTextForValue(value);
        assertThat(text, is(equalTo(expectedText)));
    }
}
