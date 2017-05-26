package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.rmi.RMIAuthenicationService;

import java.util.Scanner;

/**
 * Created by fandroid95 on 26/05/2017.
 */
public class RMIAuthenticationHandler implements RMIAuthenicationService
{

    @Override
    public boolean login()
    {
        System.out.println("Prova RMI inserisci username!");

        Scanner scanner = new Scanner(System.in);

        String username = scanner.nextLine();

        if (GameServer.getGameMatchMap().containsKey(username))
            return true;

        return false;
    }
}
