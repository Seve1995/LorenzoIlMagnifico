package it.polimi.ingsw.pc22.states;

/**
 * Simple interface of generic state;
 * In every state the player can interact with the server in a certain way;
 * if he/she provides input not expected the server answers "invalid input"
 */
public interface GenericState
{
    void printState();

    boolean validate(String string);

    void sendToServer(String string);
}
