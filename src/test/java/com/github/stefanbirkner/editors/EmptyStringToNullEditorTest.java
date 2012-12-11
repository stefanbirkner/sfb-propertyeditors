package com.github.stefanbirkner.editors;

import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.*;

import java.beans.PropertyEditor;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EmptyStringToNullEditorTest {
	private static final String ARBITRARY_TEXT = "5tfc2dfa";
	private static final String EMPTY_STRING = "";
	private final PropertyEditor editor = mock(PropertyEditor.class);
	private final PropertyEditor wrappedEditor = new EmptyStringToNullEditor(editor);

	@Rule
	public final ExpectedException thrown = none();
	
	@Test
	public void cannotBeCreatedWithoutPropertyEditor() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("The constructor argument 'propertyEditor' is missing.");
		new EmptyStringToNullEditor(null);
	}
	
	@Test
	public void replacesEmptyStringWithNull() {
		wrappedEditor.setAsText(EMPTY_STRING);
		verify(editor).setAsText(null);
	}

	@Test
	public void delegatesSetAsTextWithNullText() {
		wrappedEditor.setAsText(null);
		verify(editor).setAsText(null);
	}

	@Test
	public void delegatesSetAsTextWithArbitraryText() {
		wrappedEditor.setAsText(ARBITRARY_TEXT);
		verify(editor).setAsText(ARBITRARY_TEXT);
	}
}
