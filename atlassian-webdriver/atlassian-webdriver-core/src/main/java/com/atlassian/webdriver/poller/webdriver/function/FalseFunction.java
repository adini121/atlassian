package com.atlassian.webdriver.poller.webdriver.function;

import com.sun.istack.internal.Nullable;
import org.openqa.selenium.WebDriver;

/**
 * @since 2.1.0
 */
public class FalseFunction implements ConditionFunction
{
    private final ConditionFunction func;

    public FalseFunction(ConditionFunction func)
    {
        this.func = func;
    }

    public Boolean apply(WebDriver driver)
    {
        return !func.apply(driver);
    }
}
