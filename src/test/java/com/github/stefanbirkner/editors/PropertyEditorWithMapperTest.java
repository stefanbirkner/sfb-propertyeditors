package com.github.stefanbirkner.editors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.beans.PropertyEditor;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.stefanbirkner.editors.mapper.Mapper;

public class PropertyEditorWithMapperTest {
	private static final Integer ARBITRARY_NUMBER = 25907;
	private static final String ARBITRARY_TEXT = "5tfc2dfa";
	private static final String ANOTHER_ARBITRARY_TEXT = "another arbitrary text";
	private static final String EMPTY_STRING = "";
	private final PropertyEditor originalEditor = mock(PropertyEditor.class);
	@SuppressWarnings("unchecked")
	private final Mapper<String> mapper = mock(Mapper.class);
	private final PropertyEditorWithMapper editor = new PropertyEditorWithMapper(originalEditor, mapper);

	@Rule
	public final ExpectedException thrown = none();
	
	@Test
	public void cannotBeCreatedWithoutPropertyEditor() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("The constructor argument 'propertyEditor' is missing.");
		new PropertyEditorWithMapper(null, mapper);
	}
	
	@Test
	public void cannotBeCreatedWithoutMapper() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("The constructor argument 'mapper' is missing.");
		new PropertyEditorWithMapper(originalEditor, null);
	}
	
	@Test
	public void callsOriginalEditorWithMappedText() {
		when(mapper.getValueForText(ARBITRARY_TEXT)).thenReturn(ANOTHER_ARBITRARY_TEXT);
		editor.setAsText(ARBITRARY_TEXT);
		verify(originalEditor).setAsText(ANOTHER_ARBITRARY_TEXT);
	}

	@Test
	public void mapsTextFromOriginalEditor() {
		when(originalEditor.getAsText()).thenReturn(ANOTHER_ARBITRARY_TEXT);
		when(mapper.getTextForValue(ANOTHER_ARBITRARY_TEXT)).thenReturn(ARBITRARY_TEXT);
		String text = editor.getAsText();
		assertThat(text, is(equalTo(ARBITRARY_TEXT)));
	}
}
