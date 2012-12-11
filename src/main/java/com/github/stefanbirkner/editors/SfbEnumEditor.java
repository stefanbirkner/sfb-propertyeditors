package com.github.stefanbirkner.editors;

import com.sun.beans.editors.EnumEditor;

/**
 * A {@code SfbEnumEditor} is a property editor for enums. The only
 * difference to Java's built-in editor is that it maps empty strings to
 * {@code null}.
 * 
 * @author Stefan Birkner <mail@stefan-birkner.de>
 *
 * @param <E> the enum to be mapped.
 */
@SuppressWarnings("restriction")
public class SfbEnumEditor<E extends Enum<E>> extends EmptyStringToNullEditor {
	public SfbEnumEditor(Class<? extends Enum<E>> enumClass) {
		super(new EnumEditor(enumClass));
	}
}
