package it.polimi.ingsw.pc22.messages;

import java.io.Serializable;

/**
 * Created by fandroid95 on 27/06/2017.
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
