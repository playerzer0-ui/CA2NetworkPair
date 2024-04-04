package commands;

import service.FilmManager;
import service.UserManager;

public interface Command {

    FilmManager filmManager = new FilmManager();
    UserManager userManager = new UserManager();
    String execute(String[] components);

}
