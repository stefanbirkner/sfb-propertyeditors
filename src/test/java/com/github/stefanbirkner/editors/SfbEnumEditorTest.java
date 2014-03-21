package com.github.stefanbirkner.editors;

import org.junit.Test;

import static com.github.stefanbirkner.editors.SfbEnumEditorTest.ArbitraryEnum.FIRST;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class SfbEnumEditorTest {
    public static enum ArbitraryEnum {FIRST, SECOND}

    ;
    private final SfbEnumEditor<ArbitraryEnum> editor = new SfbEnumEditor<ArbitraryEnum>(ArbitraryEnum.class);

    @Test
    public void providesEnumValueFirst() {
        editor.setAsText("FIRST");
        assertThat(editor, hasProperty("value", is(FIRST)));
    }

    @Test
    public void providesNullForNull() {
        editor.setAsText(null);
        assertThat(editor, hasProperty("value", is(nullValue())));
    }

    @Test
    public void providesNullForEmptyString() {
        editor.setAsText("");
        assertThat(editor, hasProperty("value", is(nullValue())));
    }
}
