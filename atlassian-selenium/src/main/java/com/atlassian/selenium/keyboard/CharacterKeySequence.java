package com.atlassian.selenium.keyboard;

import java.util.List;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class CharacterKeySequence
{
    public CharacterKeySequence(final String identifier,final List<KeyEvent> keyEvents)
    {
        this.keyEvents = keyEvents;
        this.identifier = identifier;
    }

    public CharacterKeySequence()
    {
    }

    private String identifier; // as a string so we can have nice names for special keys like DOWNARROW
    private List<KeyEvent> keyEvents;

    public List<KeyEvent> getKeyEvents()
    {
        return keyEvents;
    }


    public String getIdentifier()
    {
        return identifier;
    }

    public Character getCharacter()
    {
        if (identifier.length() == 1)
        {
            return identifier.charAt(0);
        }
        else
        {
            return null;
        }
    }

}
