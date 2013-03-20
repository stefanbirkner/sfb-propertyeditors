package com.github.stefanbirkner.editors.mapper;

/**
 * A {@code Mapper} maps between Strings and objects.
 * 
 * @author Stefan Birkner <mail@stefan-birkner.de>
 * @since 2.0.0
 */
public interface Mapper<T> {
	T getValueForText(String text);

	String getTextForValue(T value);
}
