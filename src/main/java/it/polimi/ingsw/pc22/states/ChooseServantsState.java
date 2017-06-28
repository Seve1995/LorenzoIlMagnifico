package it.polimi.ingsw.pc22.states;

/**
 * Created by fandroid95 on 27/06/2017.
 */
public class ChooseServantsState implements GenericState
{
    @Override
    public void printState()
    {
        System.out.println("");
    }

    @Override
    public boolean validate(String string) {
        return false;
    }
}
