package commands;

import service.TCProtocol;

import static server.ServerMovie.user;

public class RemoveFilmCommand implements Command{

    @Override
    public String execute(String[] components) {
        if(components.length == 2){
            if(user != null){
                if(user.isAdmin()){
                    boolean succeed = filmManager.removeFilm(components[1]);
                    if(succeed){
                        return TCProtocol.REMOVED;
                    }
                    else{
                        return TCProtocol.NOT_FOUND;
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
