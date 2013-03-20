package com.github.stefanbirkner.editors.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

public class EmptyStringToNullMapperTest {
	private static final String EMPTY_STRING = "";
	private static final String DUMMY_TEXT = "dummy text";
	private final EmptyStringToNullMapper mapper = new EmptyStringToNullMapper();

	@Test
	public void doesNotChangeText() {
		String value = mapper.getValueForText(DUMMY_TEXT);
		assertThat(value, is(equalTo(DUMMY_TEXT)));
	}

	@Test
	public void doesNotChangeValue() {
		String value = mapper.getTextForValue(DUMMY_TEXT);
		assertThat(value, is(equalTo(DUMMY_TEXT)));
	}

	@Test
	public void mapsEmptyStringToNullValue() {
		String value = mapper.getValueForText(EMPTY_STRING);
		assertThat(value, is(nullValue()));
	}
	
	@Test
	public void mapsNullValueToEmptyString() {
		String text = mapper.getTextForValue(null);
		assertThat(text, is(equalTo(EMPTY_STRING)));
	}
}
