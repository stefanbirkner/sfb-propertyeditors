package com.github.stefanbirkner.editors;

import static com.github.stefanbirkner.editors.EnumEditorTest.ArbitraryEnum.FIRST;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class EnumEditorTest {
	public static enum ArbitraryEnum {FIRST, SECOND};
	private final EnumEditor<ArbitraryEnum> editor = new EnumEditor<ArbitraryEnum>(ArbitraryEnum.class);

	@Test
	public void usesNameOfEnumValueAsText() {
		editor.setValue(FIRST);
		assertThat(editor.getAsText(), is(equalTo("FIRST")));
	}

	@Test
	public void returnsNullAsTextOfNull() {
		editor.setValue(null);
		assertThat(editor.getAsText(), is(nullValue()));
	}
	
	@Test
	public void setsValueWhereEnumNameIsEqualToSuppliedText() {
		editor.setAsText("FIRST");
		assertThat((ArbitraryEnum) editor.getValue(), is(equalTo(FIRST)));
	}
	
	@Test
	public void setsValueNullForNullText() {
		editor.setAsText(null);
		assertThat(editor.getValue(), is(nullValue()));
	}
}
