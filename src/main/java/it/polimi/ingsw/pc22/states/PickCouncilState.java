package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.client.AuxiliaryRMIThread;
import it.polimi.ingsw.pc22.client.Client;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * In this state the player can only choose one (or more) council privilege by typing
 * the number associated to it (please refer to CouncilPrivilege class in "utils")
 
 */
public class PickCouncilState implements GenericState
{
    private int numberOfPrivileges;

    public PickCouncilState(int numberOfPrivileges)
    {
        this.numberOfPrivileges = numberOfPrivileges;
    }

    @Override
    public void printState()
    {
        String output = "Choose " + numberOfPrivileges + " privileges among:"+ '\n';

        output += "1) One stone & One wood" + '\n';
        output += "2) Two servants" + '\n';
        output += "3) Two coins" + '\n';
        output += "4) Two military points" + '\n';
        output += "5) One faith points";

        if (numberOfPrivileges > 1)
            output += "write like use \"-\" to separate numbers";

        System.out.println(output);
    }

    @Override
    public boolean validate(String string)
    {
        Pattern pattern = null;

        if (numberOfPrivileges == 1)
        {
            pattern = Pattern.compile("[1-5]");
        }

        if (numberOfPrivileges == 2)
        {
            pattern = Pattern.compile("[1-5]-[1-5]");
        }

        if (numberOfPrivileges == 3)
        {
            pattern = Pattern.compile("[1-5]-[1-5]-[1-5]");
        }

        if (pattern == null)
        {
            System.out.println("Invalid input retry");

            return false;
        }

        Matcher matcher = pattern.matcher(string);

        if (!matcher.find())
        {
            System.out.println("Invalid input retry");

            return false;
        }

        Set<String> duplicates = new HashSet<>();

        duplicates.addAll(Arrays.asList(string.split("-")));

        if (duplicates.size() != numberOfPrivileges)
        {
            System.out.println("Duplicate choice retry");

            return false;
        }

        return true;
    }

    @Override
    public void sendToServer(String string)
    {
        if ("rmi".equals(Client.getNetworkChoice()))
        {
            new Thread(new AuxiliaryRMIThread(string, null, null, numberOfPrivileges)).start();
        }

        if ("socket".equals(Client.getNetworkChoice()))
        {
            Client.socketSend(string);
        }
    }
}
