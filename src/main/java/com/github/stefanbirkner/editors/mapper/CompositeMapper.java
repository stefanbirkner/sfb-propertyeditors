package com.github.stefanbirkner.editors.mapper;

/**
 * A {@code CompositeMapper} allows you to modify the input and output of a
 * arbitrary {@link Mapper}. A second {@code Mapper} is used for mapping the
 * input and output.
 * 
 * @author Stefan Birkner <mail@stefan-birkner.de>
 * @param <T>
 *            the supported type.
 * @since 2.0.0
 */
public class CompositeMapper<T> implements Mapper<T> {
	private Mapper<T> objectMapper;
	private Mapper<String> stringMapper;

	public CompositeMapper(Mapper<T> objectMapper, Mapper<String> stringMapper) {
		this.objectMapper = objectMapper;
		this.stringMapper = stringMapper;
	}

	public T getValueForText(String text) {
		String mappedText = stringMapper.getValueForText(text);
		return objectMapper.getValueForText(mappedText);
	}

	public String getTextForValue(T value) {
		String text = objectMapper.getTextForValue(value);
		return stringMapper.getTextForValue(text);
	}
}
