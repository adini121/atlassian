package com.atlassian.webdriver.it;

import com.atlassian.webdriver.it.pageobjects.SimpleTestedProduct;
import com.atlassian.webdriver.testing.annotation.TestedProductClass;
import com.atlassian.webdriver.testing.rule.DefaultProductContextRules;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@TestedProductClass(SimpleTestedProduct.class)
@RunWith(FileBasedServerRunner.class)
public abstract class AbstractFileBasedServerTest 
{
    @Inject protected static SimpleTestedProduct product;

    @Inject @ClassRule public static DefaultProductContextRules.ForClass classRules;
    @Inject @Rule public DefaultProductContextRules.ForMethod methodRules;
}
