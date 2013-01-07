package com.github.stefanbirkner.editors;

import static java.lang.Enum.valueOf;

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
public class EnumEditor<E extends Enum<E>> extends SfbPropertyEditorSupport<E> {
	private final Class<E> type;

	public EnumEditor(Class<E> type) {
		super(type);
		this.type = type;
	}

	@Override
	protected E parseText(String text) {
		return (text == null) ? null : valueOf(type, text);
	}
}
