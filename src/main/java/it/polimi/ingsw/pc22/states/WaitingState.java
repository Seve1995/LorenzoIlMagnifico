package it.polimi.ingsw.pc22.states;

/**
 * This state basically does nothing.
 * It is a transition state, waiting for the first turn of the match.
 *
 */
public class WaitingState implements GenericState
{
    @Override
    public void printState()
    {
        System.out.println("Please wait for the match to start...");
    }

    @Override
    public boolean validate(String string)
    {
        System.out.println("Please wait for the match to start!");

        return false;
    }

    @Override
    public void sendToServer(String string)
    {

    }
}
