package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.client.Client;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fandroid95 on 27/06/2017.
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
        String output = "Choose " + numberOfPrivileges + "privileges among:";

        output += "1) One stone & One wood" + '\n';
        output += "2) Two servants" + '\n';
        output += "3) Two coins" + '\n';
        output += "4) Two military points" + '\n';
        output += "5) Two faith points";

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

        Matcher matcher = pattern.matcher(string);

        if (pattern == null || !matcher.find())
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
            try
            {
                Client.getRmiServerInterface().takeCouncilDecision
                        (string, Client.getAssignedID(), numberOfPrivileges);
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }

        if ("socket".equals(Client.getNetworkChoice()))
        {
            Client.socketSend(string);
        }
    }
}
