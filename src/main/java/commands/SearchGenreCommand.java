package commands;

import business.Film;
import service.TCProtocol;

import java.util.List;

public class SearchGenreCommand implements Command{
    @Override
    public String execute(String[] components) {
        if(components.length == 2){
            List<Film> films = filmManager.searchByGenre(components[1]);
            if(!films.isEmpty()){
                StringBuilder result = new StringBuilder();

                for (Film film : films) {
                    result.append(film.toString()).append(TCProtocol.KWARG);
                }

                // Remove the last "~~" separator if it's present
                if (!result.isEmpty()) {
                    result.setLength(result.length() - 2);
                }

                return String.valueOf(result);
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
