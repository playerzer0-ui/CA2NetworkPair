package service;

public class TCProtocol {
    public final static int PORT = 41235;
    public final static String HOST = "localhost";
    public final static String DELIMITER = "%%";
    public final static String INVALID = "INVALID_REQUEST";
    public final static String EXIT = "GOODBYE";

    public final static String SHUTDOWN = "SHUTTING_DOWN";
    public final static String INSUFFICIENT = "INSUFFICIENT_PERMISSIONS";

    //USER
    public final static String ADDED = "ADDED";
    public final static String REJECTED = "REJECTED";
    public final static String ADMIN = "SUCCESS_ADMIN";
    public final static String USER = "SUCCESS_USER";
    public final static String LOGOUT = "LOGGED_OUT";
    public final static String FAILED = "FAILED";

    //FILM
    public final static String INVALID_RATING_SUPPLIED = "INVALID_RATING_SUPPLIED";
    public final static String NO_MATCH_FOUND = "NO_MATCH_FOUND";
    public final static String EXISTS = "EXISTS";


}
