package it.polimi.ingsw.pc22.states;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fandroid95 on 13/06/2017.
 */
public class LoginState implements GenericState
{
    @Override
    public void printState()
    {
        System.out.println("Write: <username> <password> l/s");
    }

    @Override
    public boolean validate(String string)
    {
        Pattern loginPattern = Pattern.compile("(^(\\w+) (\\w+) (L|S|l|s)$)");

        Matcher matcher = loginPattern.matcher(string);

        if (!matcher.find())
        {
            System.out.println("Invalid INPUT");

            return false;
        }

        return true;
    }
}
