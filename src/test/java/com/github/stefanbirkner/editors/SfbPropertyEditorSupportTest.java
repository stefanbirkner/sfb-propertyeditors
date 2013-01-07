package com.github.stefanbirkner.editors;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SfbPropertyEditorSupportTest {
	private static final ArbitraryClass ARBITRARY_OBJECT = new ArbitraryClass();
	private static final String ARBITRARY_TEXT = "arbitrary text";
	private final OurPropertyChangeListener firstListener = new OurPropertyChangeListener();
	private final OurPropertyChangeListener secondListener = new OurPropertyChangeListener();
	private final PropertyEditor editor = new SfbPropertyEditorSupport<ArbitraryClass>(
			ArbitraryClass.class) {
		public ArbitraryClass parseText(String text) {
			return ARBITRARY_OBJECT;
		}
	};
	
	@Rule
	public final ExpectedException thrown = none();

	@Test
	public void storesValue() {
		setArbitraryObjectAsValue();
		assertThat(editor,
				hasProperty("value", is(sameInstance(ARBITRARY_OBJECT))));
	}
	
	@Test
	public void storesValueWhichIsInstanceOfSubclass() {
		editor.setValue(new ArbitrarySubClass());
	}
	
	@Test
	public void rejectsValueOfWrongType() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("The value 4 is not of type ArbitraryClass.");
		editor.setValue(new Integer(4));
	}

	@Test
	public void isNotPaintable() {
		assertThat(editor, hasProperty("paintable", is(false)));
	}

	@Test
	public void isSilentWhenPaintValueIsCalled() {
		Graphics graphics = mock(Graphics.class);
		editor.paintValue(graphics, new Rectangle());
		verifyZeroInteractions(graphics);
	}

	@Test
	public void providesGibberishAsJavaInitializationString() {
		assertThat(editor,
				hasProperty("javaInitializationString", is(equalTo("???"))));
	}

	@Test
	public void providesNullAsTextForNull() {
		editor.setValue(null);
		assertEditorReturnsNullForProperty("asText");
	}

	@Test
	public void usesValuesToStringForAsText() {
		ArbitraryClass object = new ArbitraryClass() {
			@Override
			public String toString() {
				return ARBITRARY_TEXT;
			}
		};
		editor.setValue(object);
		assertThat(editor, hasProperty("asText", is(equalTo(ARBITRARY_TEXT))));
	}

	@Test
	public void providesNoTags() {
		assertEditorReturnsNullForProperty("tags");
	}

	@Test
	public void providesNoCustomEditor() {
		assertEditorReturnsNullForProperty("customEditor");
	}

	@Test
	public void doesNotSupportCustomEditor() {
		assertThat(editor.supportsCustomEditor(), is(false));
	}

	@Test
	public void firesPropertyChangeOnAllListeners() {
		addFirstListener();
		editor.addPropertyChangeListener(secondListener);
		setArbitraryObjectAsValue();
		assertNotNull("No event for first listener fired.",
				firstListener.lastEvent);
		assertNotNull("No event for second listener fired.",
				secondListener.lastEvent);

	}

	@Test
	public void doesNotFirePropertyChangeOnRemovedListener() {
		addFirstListener();
		editor.removePropertyChangeListener(firstListener);
		setArbitraryObjectAsValue();
		assertNull("Unexpected event fired for listener.",
				firstListener.lastEvent);
	}

	@Test
	public void firesEventWithItselfAsSource() {
		addFirstListener();
		setArbitraryObjectAsValue();
		assertThat(firstListener.lastEvent,
				hasProperty("source", sameInstance(editor)));
	}

	@Test
	public void firesEventWithPropertyNameValue() {
		addFirstListener();
		setArbitraryObjectAsValue();
		assertThat(firstListener.lastEvent,
				hasProperty("propertyName", equalTo("value")));
	}

	@Test
	public void firesEventWithOldValueSet() {
		addFirstListener();
		setArbitraryObjectAsValue();
		editor.setValue(new ArbitraryClass());
		assertThat(firstListener.lastEvent,
				hasProperty("oldValue", sameInstance(ARBITRARY_OBJECT)));
	}

	@Test
	public void firesEventWithNewValueSet() {
		addFirstListener();
		setArbitraryObjectAsValue();
		assertThat(firstListener.lastEvent,
				hasProperty("newValue", sameInstance(ARBITRARY_OBJECT)));
	}

	@Test
	public void firesEventWhenValueIsSetAsText() {
		addFirstListener();
		editor.setAsText(ARBITRARY_TEXT);
		assertThat(firstListener.lastEvent, is(not(nullValue())));
	}

	private void addFirstListener() {
		editor.addPropertyChangeListener(firstListener);
	}

	private void setArbitraryObjectAsValue() {
		editor.setValue(ARBITRARY_OBJECT);
	}

	private void assertEditorReturnsNullForProperty(String propertyName) {
		assertThat(editor, hasProperty(propertyName, is(nullValue())));
	}

	private static class OurPropertyChangeListener implements
			PropertyChangeListener {
		PropertyChangeEvent lastEvent;

		public void propertyChange(PropertyChangeEvent evt) {
			this.lastEvent = evt;
		}
	}

	private static class ArbitraryClass {
	}

	private static class ArbitrarySubClass extends ArbitraryClass {
	}
}
