package service;

public class TCProtocol {
    public final static int PORT = 41235;
    public final static String HOST = "localhost";
    public final static String DELIMITER = "%%";
    public final static String KWARG = "~~";
    public final static String INVALID = "INVALID_REQUEST";
    public final static String EXIT = "exit";
    public final static String GOODBYE = "GOODBYE";
    public final static String SHUTDOWN = "shutdown";
    public final static String SHUTTING_DOWN = "SHUTTING_DOWN";
    public final static String INSUFFICIENT = "INSUFFICIENT_PERMISSIONS";

    //USER
    public final static String REGISTER = "register";
    public final static String LOGIN = "login";
    public final static String LOGOUT = "logout";
    public final static String ADDED = "ADDED";
    public final static String REJECTED = "REJECTED";
    public final static String ADMIN = "SUCCESS_ADMIN";
    public final static String USER = "SUCCESS_USER";
    public final static String LOGGED_OUT = "LOGGED_OUT";
    public final static String FAILED = "FAILED";
    public final static String NOT_LOGGED_IN = "NOT_LOGGED_IN";

    //FILM
    public final static String DISPLAY_ALL_FILMS = "DISPLAY_ALL_FILMS";
    public final static String NONE = "NONE";
    public final static String RATE = "rate";
    public final static String SEARCH_NAME = "searchByName";
    public final static String SEARCH_GENRE = "searchByGenre";
    public final static String ADD = "add";
    public final static String REMOVE = "remove";
    public final static String REMOVED = "REMOVED";
    public final static String SUCCESS = "SUCCESS";
    public final static String INVALID_RATING_SUPPLIED = "INVALID_RATING_SUPPLIED";
    public final static String NOT_FOUND = "NOT_FOUND";
    public final static String NO_MATCH_FOUND = "NO_MATCH_FOUND";
    public final static String EXISTS = "EXISTS";


}
