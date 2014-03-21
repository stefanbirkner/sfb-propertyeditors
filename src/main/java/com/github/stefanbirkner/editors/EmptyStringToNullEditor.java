package com.github.stefanbirkner.editors;

import com.github.stefanbirkner.editors.mapper.EmptyStringToNullMapper;
import com.github.stefanbirkner.editors.mapper.Mapper;

import java.beans.PropertyEditor;

/**
 * An {@code EmptyStringToNullEditor} is a wrapper for building
 * {@code PropertyEditor}s that handle {@code setAsText("")} like
 * {@code setAsText(null)}.
 * <p/>
 * Wrap the original editor with the {@code EmptyStringToNullEditor}
 * <p/>
 * <pre>PropertyEditor wrappedEditor = new EmptyStringToNullEditor(originalEditor);</pre>
 * <p/>
 * When somebody calls {@code setAsText("")} on the {@code wrappedEditor},
 * then it calls {@code setAsText(null)} on the {@code originalEditor}. All
 * other values and all other methods are delegated to the {@code originalEditor}.
 *
 * @author Stefan Birkner <mail@stefan-birkner.de>
 * @since 1.0.0
 */
public class EmptyStringToNullEditor extends PropertyEditorWithMapper {
    private static final Mapper<String> MAPPER = new EmptyStringToNullMapper();

    public EmptyStringToNullEditor(PropertyEditor propertyEditor) {
        super(propertyEditor, MAPPER);
    }
}
