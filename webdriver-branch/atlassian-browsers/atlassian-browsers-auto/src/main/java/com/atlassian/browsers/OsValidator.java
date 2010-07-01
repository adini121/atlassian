package com.atlassian.browsers;

/**
 * Determines which OS is running
 *
 * @since 2.0
 */
public class OsValidator
{
    private static String os = System.getProperty("os.name").toLowerCase();

	public static boolean isWindows()
    {
		//windows
	    return (os.indexOf( "win" ) >= 0);
	}

	public static boolean isMac()
    {
    	//Mac
	    return (os.indexOf( "mac" ) >= 0);
    }

	public static boolean isUnix()
    {
    	//linux or unix
	    return (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0);
    }
}