package it.polimi.ingsw.pc22.states;

/**
 * Created by fandroid95 on 13/06/2017.
 */
public interface GenericState
{
    void printState();

    boolean validate(String string);

    void sendToServer(String string);
}
