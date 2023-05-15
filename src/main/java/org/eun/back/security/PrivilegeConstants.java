package org.eun.back.security;

/**
 * Constants for Spring Security authorities.
 * DA - Delete Access
 * RA - Read Access
 * EP - Execute Privileges
 * WA - Write Access
 * AC - Access Control
 * CA - Create Access
 * CP - Configuration Privileges
 * AuP - Audit Privileges
 * MP - Moderation Privileges
 */
public final class PrivilegeConstants {

    public static final String DELETE = "DA";

    public static final String EXECUTE_PRIVILEGE = "EP";

    public static final String WRITE = "WA";

    public static final String ACCESS_CONTROL = "AC";

    public static final String CREATE = "CA";

    public static final String CONFIGURATION_PRIVILEGE = "CP";

    public static final String MODERATION_PRIVILEGE = "MP";

    private PrivilegeConstants() {}
}
