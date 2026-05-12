package vgu.SoSe2026_Compnet2.util;

/**
 * A utility class to validate FTP server responses.
 */
public final class ValidateFTPResponse {

    /**
     * Checks if the FTP server response starts with the specified prefix.
     * @param response the FTP server response to validate
     * @param prefix the expected prefix of the FTP server response
     * @return true if the response starts with the specified prefix, false otherwise
     */
    public static boolean startWith(String response, String prefix) {
        if (response == null || prefix == null) {
            return false;
        }
        return response.startsWith(prefix);
    }
}
