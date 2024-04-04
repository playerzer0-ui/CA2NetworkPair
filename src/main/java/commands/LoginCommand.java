package commands;

import business.User;
import server.ServerMovie;
import service.TCProtocol;

import static server.ServerMovie.user;

public class LoginCommand implements Command{

    @Override
    public String execute(String[] components) {
        if (components.length == 3) {
            User u = userManager.searchByUsername(components[1]);
            if (u != null && u.getPassword().equals(components[2])) {
                user = u;
                return user.isAdmin() ? TCProtocol.ADMIN : TCProtocol.USER;
            } else {
                return TCProtocol.FAILED;
            }
        } else {
            return TCProtocol.INVALID;
        }
    }
}
