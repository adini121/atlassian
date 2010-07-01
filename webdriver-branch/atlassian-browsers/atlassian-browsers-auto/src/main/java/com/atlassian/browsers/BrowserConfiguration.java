package com.atlassian.browsers;

import java.io.File;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public interface BrowserConfiguration
{
    File getTmpDir();
    String getBrowserName();
}
