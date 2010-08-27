package com.atlassian.webdriver.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class License
{
    private final String license;
    private final String organisation;
    private final String datePurchased;
    private final String licenseType;
    private final String serverId;
    private final String supportEntitlementNumber;
    private final int userLimit;

    public static final String DATE_FORMAT = "dd/MM/yy";

    public License(String license, String organisation, String datePurchased, String licenseType, String serverId,
            String supportEntitlementNumber, int userLimit)
    {

        this.license = license;
        this.organisation = organisation;
        this.datePurchased = datePurchased;
        this.licenseType = licenseType;
        this.serverId = serverId;
        this.supportEntitlementNumber = supportEntitlementNumber;
        this.userLimit = userLimit;

    }

    public Date getDatePurchased()
    {
        try
        {
            return new SimpleDateFormat(DATE_FORMAT).parse(datePurchased);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }

    }

    //GENERATED CODE BELOW

    public String getLicense()
    {
        return license;
    }

    public String getOrganisation()
    {
        return organisation;
    }

    public String getLicenseType()
    {
        return licenseType;
    }

    public String getServerId()
    {
        return serverId;
    }

    public String getSupportEntitlementNumber()
    {
        return supportEntitlementNumber;
    }

    public int getUserLimit()
    {
        return userLimit;
    }
}