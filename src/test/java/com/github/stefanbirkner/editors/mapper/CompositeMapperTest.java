package com.github.stefanbirkner.editors.mapper;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeMapperTest {
    private static final Integer ARBITRARY_NUMBER = 25907;
    private static final String ARBITRARY_TEXT = "arbitrary text";
    private static final String ANOTHER_ARBITRARY_TEXT = "another arbitrary text";
    @SuppressWarnings("unchecked")
    private final Mapper<Integer> objectMapper = mock(Mapper.class);
    @SuppressWarnings("unchecked")
    private final Mapper<String> stringMapper = mock(Mapper.class);
    private final Mapper<Integer> compositeMapper = new CompositeMapper<Integer>(objectMapper, stringMapper);

    @Test
    public void mapsTextToValueUsingBothMappers() {
        when(stringMapper.getValueForText(ARBITRARY_TEXT)).thenReturn(ANOTHER_ARBITRARY_TEXT);
        when(objectMapper.getValueForText(ANOTHER_ARBITRARY_TEXT)).thenReturn(ARBITRARY_NUMBER);
        Integer value = compositeMapper.getValueForText(ARBITRARY_TEXT);
        assertThat(value, is(equalTo(ARBITRARY_NUMBER)));
    }

    @Test
    public void mapsValueToTextUsingBothMappers() {
        when(objectMapper.getTextForValue(ARBITRARY_NUMBER)).thenReturn(ANOTHER_ARBITRARY_TEXT);
        when(stringMapper.getTextForValue(ANOTHER_ARBITRARY_TEXT)).thenReturn(ARBITRARY_TEXT);
        String text = compositeMapper.getTextForValue(ARBITRARY_NUMBER);
        assertThat(text, is(equalTo(ARBITRARY_TEXT)));
    }
}
