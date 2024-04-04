package commands;

import business.User;
import service.TCProtocol;

public class RegisterCommand implements Command {
    @Override
    public String execute(String[] components) {
        if (components.length == 3) {
            boolean added = userManager.addUser(new User(components[1], components[2], false));
            return added ? TCProtocol.ADDED : TCProtocol.REJECTED;
        } else {
            return TCProtocol.INVALID;
        }
    }
}
