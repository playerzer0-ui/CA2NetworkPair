package commands;

import service.TCProtocol;

public class InvalidCommand implements Command{

    @Override
    public String execute(String[] components) {
        return TCProtocol.INVALID;
    }
}
