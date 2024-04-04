package commands;

import service.TCProtocol;
import static server.ServerMovie.*;

public class ShutDownCommand implements Command{
    @Override
    public String execute(String[] components) {
        if(user != null){
            if(user.isAdmin()){
                serverOnline = false;
                validSession = false;
                return TCProtocol.SHUTTING_DOWN;
            }
            else{
                return TCProtocol.INSUFFICIENT;
            }
        }
        else{
            return TCProtocol.INSUFFICIENT;
        }
    }
}
