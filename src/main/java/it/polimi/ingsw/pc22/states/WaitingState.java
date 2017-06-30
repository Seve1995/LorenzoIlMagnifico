package it.polimi.ingsw.pc22.states;

/**
 * Created by fandroid95 on 17/06/2017.
 */
public class WaitingState implements GenericState
{
    @Override
    public void printState()
    {
        System.out.println("I'M WAITIN' FOR THE MATCH YA");
    }

    @Override
    public boolean validate(String string)
    {
        System.out.println("PLEASE WAIT FOR THE MATCH TO START");

        return false;
    }

    @Override
    public void sendToServer(String string)
    {

    }
}
