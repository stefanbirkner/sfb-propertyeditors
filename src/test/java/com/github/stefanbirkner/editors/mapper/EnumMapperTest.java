package com.github.stefanbirkner.editors.mapper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.github.stefanbirkner.editors.mapper.EnumMapperTest.ArbitraryEnum.FIRST;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.rules.ExpectedException.none;

public class EnumMapperTest {
    public static enum ArbitraryEnum {
        FIRST, SECOND
    }

    ;

    private final EnumMapper<ArbitraryEnum> mapper = new EnumMapper<ArbitraryEnum>(
            ArbitraryEnum.class);

    @Rule
    public final ExpectedException thrown = none();

    @Test
    public void usesNameOfEnumValueAsText() {
        String text = mapper.getTextForValue(FIRST);
        assertThat(text, is(equalTo("FIRST")));
    }

    @Test
    public void usesNullAsTextOfNull() {
        String text = mapper.getTextForValue(null);
        assertThat(text, is(nullValue()));
    }

    @Test
    public void parsesTextBasedOnTheEnumName() {
        ArbitraryEnum value = mapper.getValueForText("FIRST");
        assertThat(value, is(equalTo(FIRST)));
    }

    @Test
    public void parsesValueNullForNullText() {
        ArbitraryEnum value = mapper.getValueForText(null);
        assertThat(value, is(nullValue()));
    }

    @Test
    public void throwsIllegalArgumentExceptionForATextWhichIsNotTheNameOfAnEnumValue() {
        thrown.expect(IllegalArgumentException.class);
        mapper.getValueForText("NOT AN ENUM VALUE");
    }
}
