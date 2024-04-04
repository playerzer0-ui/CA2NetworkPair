package commands;

import service.TCProtocol;
import static server.ServerMovie.user;

public class LogoutCommand implements Command{
    @Override
    public String execute(String[] components) {
        user = null;
        return TCProtocol.LOGGED_OUT;
    }
}
