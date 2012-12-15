package com.github.stefanbirkner.editors;

import static java.lang.Enum.valueOf;

import java.beans.PropertyEditorSupport;

/**
 * This {@code EnumEditor} is a replacement for Java's built-in enum editor.
 * Java's editor is only an internal class and therefore should not be used
 * outside of Java. This class is expected to be used by you.
 * 
 * @author Stefan Birkner <mail@stefan-birkner.de>
 * 
 * @param <E>
 *            the enum to be edited.
 */
public class EnumEditor<E extends Enum<E>> extends PropertyEditorSupport {
	private final Class<E> type;

	public EnumEditor(Class<E> type) {
		this.type = type;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Enum<E> value = (text == null) ? null : valueOf(type, text);
		setValue(value);
	}
}
