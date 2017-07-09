package it.polimi.ingsw.pc22.messages;

/**
 * This message informs the user about the number of privileges
 * he can take in certain moment of the game.
 */
public class PickPrivilegeMessage extends Message
{
    private int numberOfPrivilege;

    public PickPrivilegeMessage(int numberOfPrivilege)
    {
        this.numberOfPrivilege = numberOfPrivilege;
    }

    public int getNumberOfPrivilege() {
        return numberOfPrivilege;
    }
}
