package com.github.stefanbirkner.editors.mapper;

import static java.lang.Enum.valueOf;

/**
 * An {@code EnumMapper} maps between enums to text and vice versa by using their names.
 * 
 * @author Stefan Birkner <mail@stefan-birkner.de>
 * 
 * @param <E>
 *            the supported type.
 * @since 2.0.0
 */
public class EnumMapper<E extends Enum<E>> implements Mapper<E> {
	private final Class<E> type;

	public EnumMapper(Class<E> type) {
		this.type = type;
	}
	
	public E getValueForText(String text) {
		return (text == null) ? null : valueOf(type, text);
	}

	public String getTextForValue(E value) {
		return (value == null) ? null : value.name();
	}
}
