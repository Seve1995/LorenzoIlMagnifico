package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.client.AuxiliaryRMIThread;
import it.polimi.ingsw.pc22.client.Client;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.messages.ErrorMessage;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fandroid95 on 03/07/2017.
 */
public class ChooseFamiliarState implements GenericState
{
    @Override
    public void printState()
    {
        List<FamilyMember> familyMembers =
                Client.getPlayer().getFamilyMembers();

        StringBuilder builder = new StringBuilder("Choose the familiar member for the bonus:\n");

        for (FamilyMember familiar : familyMembers)
            builder.append(familiar.toString());

        System.out.println(builder);
    }

    @Override
    public boolean validate(String string)
    {
        Pattern familiarPattern =
                Pattern.compile("^(BLACK|ORANGE|WHITE|NEUTER)$");

        Matcher matcher = familiarPattern.matcher(string);

        if (!matcher.find())
        {
            System.out.println("Please insert correct value");

            return false;
        }

        return true;
    }

    @Override
    public void sendToServer(String string)
    {
        if ("rmi".equals(Client.getNetworkChoice()))
        {
            new Thread(new AuxiliaryRMIThread(string, null, null, null)).start();
        }

        if ("socket".equals(Client.getNetworkChoice()))
        {
            Client.socketSend(string);
        }
    }
}
