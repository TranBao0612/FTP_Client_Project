package vgu.SoSe2026_Compnet2.util;

/**
 * A utility class to validate FTP server responses.
 */
public final class ValidateFTPResponse {
    public static boolean startWith(String response, String prefix) {
        if (response == null || prefix == null) {
            return false;
        }
        return response.startsWith(prefix);
    }
}
