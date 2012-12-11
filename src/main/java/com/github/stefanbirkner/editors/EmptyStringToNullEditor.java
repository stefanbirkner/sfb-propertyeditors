package com.github.stefanbirkner.editors;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;

/**
 * An {@code EmptyStringToNullEditor} is a wrapper for building
 * {@code PropertyEditor}s that handle {@code setAsText("")} like
 * {@code setAsText(null)}.
 * <p>
 * Wrap the original editor with the {@code EmptyStringToNullEditor}
 * 
 * <pre>PropertyEditor wrappedEditor = new EmptyStringToNullEditor(originalEditor);</pre>
 * 
 * When somebody calls {@code setAsText("")} on the {@code wrappedEditor},
 * then it calls {@code setAsText(null)} on the {@code originalEditor}. All
 * other values and all other methods are delegated to the {@code originalEditor}.
 * 
 * @author Stefan Birkner <mail@stefan-birkner.de>
 */
public class EmptyStringToNullEditor implements PropertyEditor {
	private static final String EMPTY_STRING = "";

	private PropertyEditor propertyEditor;

	public EmptyStringToNullEditor(PropertyEditor propertyEditor) {
		if (propertyEditor == null)
			throw new IllegalArgumentException("The constructor argument 'propertyEditor' is missing.");
		this.propertyEditor = propertyEditor;
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
		return propertyEditor.getAsText();
	}

	public void setAsText(String text) throws IllegalArgumentException {
		String textForDelegate = (EMPTY_STRING.equals(text)) ? null : text;
		propertyEditor.setAsText(textForDelegate);
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
