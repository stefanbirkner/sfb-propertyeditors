package com.github.stefanbirkner.editors;

import com.github.stefanbirkner.editors.mapper.EnumMapper;

/**
 * This {@code EnumEditor} is a replacement for Java's built-in enum editor.
 * Java's editor is only an internal class and therefore should not be used
 * outside of Java. This class is expected to be used by you.
 *
 * @param <E> the enum to be edited.
 * @author Stefan Birkner <mail@stefan-birkner.de>
 * @since 1.0.0
 */
public class EnumEditor<E extends Enum<E>> extends SfbPropertyEditorSupport<E> {
    public EnumEditor(Class<E> type) {
        super(type, new EnumMapper<E>(type));
    }
}
