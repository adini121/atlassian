package com.atlassian.pageobjects.framework.element;

import com.atlassian.pageobjects.framework.element.Option;
import com.atlassian.pageobjects.framework.util.StringConcat;

/**
 * {@link com.atlassian.pageobjects.framework.element.Option} objects factory.
 *
 */
public final class Options
{
    static abstract class AbstractOption implements Option
    {
        private final String id, value, text;

        public AbstractOption(String id, String value, String text)
        {
            if (value == null && id == null && text == null)
            {
                throw new IllegalArgumentException("One of the option identifiers must be non-null");
            }
            this.value = value;
            this.id = id;
            this.text = text;
        }

        public String id()
        {
            return id;
        }

        public String value()
        {
            return value;
        }

        public String text()
        {
            return text;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj == null)
            {
                return false;
            }
            if (!Option.class.isAssignableFrom(obj.getClass()))
            {
                return false;
            }
            AbstractOption other = (AbstractOption) obj;
            if (id != null && !id.equals(other.id))
            {
                return false;
            }
            if (value != null && !value.equals(other.value))
            {
                return false;
            }
            if(text != null && !text.equals(other.text))
            {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode()
        {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            result = 31 * result + (text != null ? text.hashCode(): 0);
            return result;
        }
    }


    public static class IdOption extends AbstractOption
    {
        IdOption(String id) { super(id, null, null); }

    }

    public static class ValueOption extends AbstractOption
    {
        ValueOption(String value) { super(null, value, null); }
    }

    public static class TextOption extends AbstractOption
    {
        TextOption(String text) { super(null,null,text); }
    }

    public static class FullOption extends AbstractOption
    {
        FullOption(String id, String value, String text) { super(id, value, text); }
    }

    /**
     * New option distinguishable by its HTML ID.
     *
     * @param id HTML id of the option
     * @return new option
     */
    public static IdOption id(String id)
    {
        return new IdOption(id);
    }

    /**
     * New option distinguishable by its HTML value attribute.
     *
     * @param value HTML value attribute of the option
     * @return new option
     */
    public static ValueOption value(String value)
    {
        return new ValueOption(value);
    }

    /**
     * New option distinguishable by its text between tags
     *
     * @param text Text between the option tags
     * @return new option
     */
    public static TextOption text(String text)
    {
        return new TextOption(text);
    }

    /**
     * New option distinguishable all identifiers. Some of the identifiers may be <code>null</code>, but at least
     * one has to be non-<code>null</code>
     *
     * @param id HTML id of the option
     * @param value HTML value attribute of the option
     * @param text Text between the option tags
     * @return new option
     */
    public static Option full(String id, String value, String text)
    {
        return new FullOption(id, value, text);
    }
}
