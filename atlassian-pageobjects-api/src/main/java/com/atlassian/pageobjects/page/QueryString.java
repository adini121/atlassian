package com.atlassian.pageobjects.page;

import java.util.HashMap;
import java.util.Map;


/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class QueryString
{

    private final Map<String, String> params = new HashMap<String, String>();


    public QueryString(String... params)
    {
        //Validate.isTrue(params.length % 2 == 0, "Must be an even number of parameters");

        for (int i = 0; i < params.length; i += 2)
        {
            this.params.put(params[i], params[i + 1]);
        }
    }

    public void add(String key, String value)
    {
        params.put(key, value);
    }

    public int size()
    {
        return params.size();
    }

    public String toString()
    {
        StringBuffer result = new StringBuffer();

        for (String key : params.keySet())
        {
            if (result.length() > 0)
            {
                result.append("&");
            }

            result.append(key).append("=").append(params.get(key));
        }

        return result.toString();
    }

}
