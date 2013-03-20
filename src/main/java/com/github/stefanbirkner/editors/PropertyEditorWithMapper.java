package com.github.stefanbirkner.editors;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;

import com.github.stefanbirkner.editors.mapper.Mapper;

/**
 * An PropertyEditorWithMapper modifies the text input and output of another
 * PropertyEditor. A configurable mapper is used for the mapping.
 * 
 * When somebody calls setAsText(...) on the new editor, then it uses the mapper
 * to modify the text before it is supplied to the original PropertyEditor. The
 * same happens when somebody calls getAsText().
 * 
 * @author Stefan Birkner <mail@stefan-birkner.de>
 * @since 2.0.0
 */
public class PropertyEditorWithMapper implements PropertyEditor {
	private final PropertyEditor propertyEditor;
	private final Mapper<String> mapper;

	public PropertyEditorWithMapper(PropertyEditor propertyEditor, Mapper<String> mapper) {
		if (propertyEditor == null)
			throw new IllegalArgumentException("The constructor argument 'propertyEditor' is missing.");
		this.propertyEditor = propertyEditor;
		if (mapper == null)
			throw new IllegalArgumentException("The constructor argument 'mapper' is missing.");
		this.mapper = mapper;
	}

	public void setValue(Object value) {
		propertyEditor.setValue(value);
	}

	public Object getValue() {
		return propertyEditor.getValue();
	}

	public boolean isPaintable() {
		return propertyEditor.isPaintable();
	}

	public void paintValue(Graphics gfx, Rectangle box) {
		propertyEditor.paintValue(gfx, box);
	}

	public String getJavaInitializationString() {
		return propertyEditor.getJavaInitializationString();
	}

	public String getAsText() {
		String text = propertyEditor.getAsText();
		return mapper.getTextForValue(text);
	}

	public void setAsText(String text) throws IllegalArgumentException {
		String mappedText = mapper.getValueForText(text);
		propertyEditor.setAsText(mappedText);
	}

	public String[] getTags() {
		return propertyEditor.getTags();
	}

	public Component getCustomEditor() {
		return propertyEditor.getCustomEditor();
	}

	public boolean supportsCustomEditor() {
		return propertyEditor.supportsCustomEditor();
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyEditor.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyEditor.removePropertyChangeListener(listener);
	}
}
