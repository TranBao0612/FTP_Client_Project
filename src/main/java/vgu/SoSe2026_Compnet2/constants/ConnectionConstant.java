package vgu.SoSe2026_Compnet2.constants;

/**
 * Defines constants related to the connection, such as default control port and timeout duration.
 */
public final class ConnectionConstant {
    public static final int FTP_CONTROL_PORT = 21;
    public static final int TIMEOUT_MILLISEC = 5000;
    public static final int DATA_CONN_INIT_TIMEOUT_MILLISEC = 1000;

    public static final int DATA_TRANSFER_CHUNK_SIZE = 4096;

    public static final int PASSIVE_MODE_RETRY_LIMIT = 5;
    public static final int MODE_RETRY_LIMIT = 2;
}
