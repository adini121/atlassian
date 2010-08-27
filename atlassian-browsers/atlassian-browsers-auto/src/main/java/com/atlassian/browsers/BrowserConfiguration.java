package com.atlassian.browsers;

import java.io.File;

/**
 * Defaines the interface that each client has to implement to be used by the BrowserAutoInstaller.
 * This is so that each client can define the temporary location for installing the browsers as well
 * as the way to get the browserName
 * @see com.atlassian.browsers.BrowserAutoInstaller
 */
public interface BrowserConfiguration
{
    File getTmpDir();
    String getBrowserName();
}
