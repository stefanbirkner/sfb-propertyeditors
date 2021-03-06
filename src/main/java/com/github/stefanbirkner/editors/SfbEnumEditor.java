package com.github.stefanbirkner.editors;

/**
 * A {@code SfbEnumEditor} is a property editor for enums. The only
 * difference to Java's built-in editor is that it maps empty strings to
 * {@code null}.
 *
 * @param <E> the enum to be mapped.
 * @author Stefan Birkner <mail@stefan-birkner.de>
 */
public class SfbEnumEditor<E extends Enum<E>> extends EmptyStringToNullEditor {
    public SfbEnumEditor(Class<E> enumClass) {
        super(new EnumEditor<E>(enumClass));
    }
}
