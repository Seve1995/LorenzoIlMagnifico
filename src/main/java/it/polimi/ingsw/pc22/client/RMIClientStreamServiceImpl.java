package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.messages.GameStatusMessage;
import it.polimi.ingsw.pc22.messages.Message;
import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;
import it.polimi.ingsw.pc22.utils.ConsoleReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public class RMIClientStreamServiceImpl implements RMIClientStreamService
{
    private Long timeout;

    private static final Logger LOGGER = Logger.getLogger(RMIClientStreamServiceImpl.class.getName());

    public RMIClientStreamServiceImpl(Long timeout)
    {
        this.timeout = timeout;
    }

    @Override
    public void printMessage(Message message) throws RemoteException
    {
        MessageHandler.handleMessage(message);
    }
}
