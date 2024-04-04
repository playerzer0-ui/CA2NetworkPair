package commands;

import business.Film;
import service.TCProtocol;

public class SearchNameCommand implements Command{
    @Override
    public String execute(String[] components) {
        if(components.length == 2){
            Film film = filmManager.searchByTitle(components[1]);
            if(film != null){
                return film.toString();
            }
            else{
                return TCProtocol.NO_MATCH_FOUND;
            }
        }
        else{
            return TCProtocol.INVALID;
        }
    }
}
