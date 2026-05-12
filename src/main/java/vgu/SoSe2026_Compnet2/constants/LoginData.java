package vgu.SoSe2026_Compnet2.constants;

/**
 * Defines constants for FTP login data, 
 *      including usernames and passwords for connections.
 */
public final class LoginData {
    // ------------------- Anonymous Login Data Constants ------------------
    /**
     * Default username for anonymous FTP login
     */
    public static final String ANONYMOUS_USERNAME = "anonymous";
    public static final String[] AlTERNATIVE_ANONYMOUS_USERNAME = {"demo", "ftp"}; // Some servers may use other anonymous username
    /**
     * Default password for anonymous FTP login (actually ignored by most servers, just defined for completeness)
     */
    public static final String ANONYMOUS_PASSWORD = "anonymous";


    // ------------------- DLPTEST FTP Server Constants ------------------
    /**
     * URL to a public FTP test server which supports testing file uploads. <br>
     * More info: https://dlptest.com/ftp-test/
     */
    public static final String DLPTEST_HOST = "ftp.dlptest.com";
    /**
     * Username for the DLPTEST FTP server (public test server, supports testing file uploads).
     */
    public static final String DLPTEST_USERNAME = "dlpuser";
    /**
     * Password for the DLPTEST FTP server (public test server, supports testing file uploads).
     */
    public static final String DLPTEST_PASSWORD = "rNrKYTX9g7z3RgJRmxWuGHbeu";
}
