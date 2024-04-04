package commands;

import service.TCProtocol;
import static server.ServerMovie.validSession;

public class ExitCommand implements Command{
    @Override
    public String execute(String[] components) {
        validSession = false;
        return TCProtocol.GOODBYE;
    }
}
