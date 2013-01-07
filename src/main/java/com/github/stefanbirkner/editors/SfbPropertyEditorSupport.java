package com.github.stefanbirkner.editors;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyEditor;

/**
 * {@code SfbPropertEditorSupport} is a {@code PropertyEditor} with proper
 * listener support. It already stores the editable value. You only have to
 * implement the template method {@code parseText(String)}.
 * 
 * @author Stefan Birkner <mail@stefan-birkner.de>
 * 
 * @param <T>
 *            the supported type.
 */
public abstract class SfbPropertyEditorSupport<T> implements PropertyEditor {
	private static final String PROPERTY_NAME_FOR_EVENTS = "value";
	private static final String GIBBERISH = "???";
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);
	private final Class<T> supportedType;
	private T value;

	public SfbPropertyEditorSupport(Class<T> supportedType) {
		this.supportedType = supportedType;
	}

	@SuppressWarnings("unchecked")
	public void setValue(Object newValue) {
		assertValueHasSupportedType(newValue);
		T oldValue = value;
		value = (T) newValue;
		propertyChangeSupport.firePropertyChange(PROPERTY_NAME_FOR_EVENTS,
				oldValue, newValue);
	}

	private void assertValueHasSupportedType(Object newValue) {
		if ((newValue != null) && !this.supportedType.isInstance(newValue))
			throw new IllegalArgumentException("The value " + newValue
					+ " is not of type " + supportedType.getSimpleName() + ".");
	}

	public T getValue() {
		return value;
	}

	public boolean isPaintable() {
		return false;
	}

	public void paintValue(Graphics gfx, Rectangle box) {
	}

	public String getJavaInitializationString() {
		return GIBBERISH;
	}

	public String getAsText() {
		return (value == null) ? null : value.toString();
	}

	public void setAsText(String text) throws IllegalArgumentException {
		T newValue = parseText(text);
		setValue(newValue);
	}

	protected abstract T parseText(String text);

	public String[] getTags() {
		return null;
	}

	public Component getCustomEditor() {
		return null;
	}

	public boolean supportsCustomEditor() {
		return false;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(
				PROPERTY_NAME_FOR_EVENTS, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(
				PROPERTY_NAME_FOR_EVENTS, listener);
	}
}
