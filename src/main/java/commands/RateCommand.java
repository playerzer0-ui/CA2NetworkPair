package commands;

import business.Film;
import service.TCProtocol;

import static server.ServerMovie.isNumber;
import static server.ServerMovie.user;

public class RateCommand implements Command{

    @Override
    public String execute(String[] components) {
        if(components.length == 3){
            if(user != null){
                if(isNumber(components[2])){
                    Film film = filmManager.searchByTitle(components[1]);
                    if(film != null){
                        boolean succeed = filmManager.rateFilm(film.getTitle(), Integer.parseInt(components[2]));
                        if(succeed){
                            return TCProtocol.SUCCESS;
                        }
                        else{
                            return TCProtocol.INVALID_RATING_SUPPLIED;
                        }
                    }
                    else{
                        return TCProtocol.NO_MATCH_FOUND;
                    }
                }
                else{
                    return TCProtocol.INVALID_RATING_SUPPLIED;
                }
            }
            else{
                return TCProtocol.NOT_LOGGED_IN;
            }
        }
        else{
            return TCProtocol.INVALID;
        }
    }
}
