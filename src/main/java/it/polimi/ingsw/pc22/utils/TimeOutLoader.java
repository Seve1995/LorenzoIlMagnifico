package it.polimi.ingsw.pc22.utils;


import org.json.JSONObject;

public class TimeOutLoader extends GenericLoader {

    public static TimeOut loadTimeOut(JSONObject jsonObject)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        TimeOut timeOut = new TimeOut();



        Long action = jsonObject.getLong("action");
        Long gameStarting = jsonObject.getLong("gameStarting");

        timeOut.setAction(action);
        timeOut.setGameStarting(gameStarting);

        return timeOut;
    }

}
