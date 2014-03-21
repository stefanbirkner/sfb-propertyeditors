package com.github.stefanbirkner.editors;

import com.github.stefanbirkner.editors.mapper.Mapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.*;

public class SfbPropertyEditorSupportTest {
    private static final ArbitraryClass ARBITRARY_OBJECT = new ArbitraryClass();
    private static final String ARBITRARY_TEXT = "arbitrary text";
    private final OurPropertyChangeListener firstListener = new OurPropertyChangeListener();
    private final OurPropertyChangeListener secondListener = new OurPropertyChangeListener();
    @SuppressWarnings("unchecked")
    private final Mapper<ArbitraryClass> mapper = mock(Mapper.class);
    private final PropertyEditor editor = new SfbPropertyEditorSupport<ArbitraryClass>(
            ArbitraryClass.class, mapper);

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
    public void mapsValueToTextUsingMapper() {
        when(mapper.getTextForValue(ARBITRARY_OBJECT)).thenReturn(ARBITRARY_TEXT);
        editor.setValue(ARBITRARY_OBJECT);
        assertThat(editor, hasProperty("asText", is(equalTo(ARBITRARY_TEXT))));
    }

    @Test
    public void mapsTextToValueUsingMapper() {
        when(mapper.getValueForText(ARBITRARY_TEXT)).thenReturn(ARBITRARY_OBJECT);
        editor.setAsText(ARBITRARY_TEXT);
        assertThat(editor, hasProperty("value", is(equalTo(ARBITRARY_OBJECT))));
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
