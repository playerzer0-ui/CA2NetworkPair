package commands;

import business.Film;
import service.TCProtocol;

import static server.ServerMovie.user;

public class AddFilmCommand implements Command{

    @Override
    public String execute(String[] components) {
        if(components.length == 3){
            if(user != null){
                if(user.isAdmin()){
                    boolean succeed = filmManager.addFilm(new Film(components[1], components[2], 0, 0));
                    if(succeed){
                        return TCProtocol.ADDED;
                    }
                    else{
                        return TCProtocol.EXISTS;
                    }
                }
                else{
                    return TCProtocol.INSUFFICIENT;
                }
            }
            else{
                return TCProtocol.INSUFFICIENT;
            }
        }
        else{
            return TCProtocol.INVALID;
        }
    }
}
