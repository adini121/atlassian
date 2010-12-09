package com.atlassian.webtest.ui.keys;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link KeySequenceBuilder} and {@link Sequences} classes.
 *
 * @since v1.21
 */
public class TestKeySequenceBuilder
{

    @Test
    public void shouldBuildDefaultCharacterSequence()
    {
        KeySequence result = Sequences.chars("something");
        assertTrue(result instanceof CharacterKeySequence);
        CharacterKeySequence charSeq = (CharacterKeySequence) result;
        assertEquals("something", charSeq.string());
        assertSequenceEquals(charSeq, "something");
    }

    private void assertSequenceEquals(KeySequence sequence, String chars)
    {

        for (int i=0; i<sequence.keys().size(); i++)
        {
            Key key = sequence.keys().get(i);
            assertTrue(key instanceof CharacterKey);
            CharacterKey charKey = (CharacterKey) key;
            assertEquals(chars.charAt(i), charKey.codeUnit());
        }
    }

}
