package it.polimi.ingsw.pc22.gamebox;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created by matteo on 04/07/17.
 */
public class FamiliarMemberTest extends TestCase {

    private FamilyMember familyMember;

    private int familiarValue;

    private int valueModifier;

    private int familiarPermanentValue;


    @Before
    public void setUp()
    {
        familyMember = mock(FamilyMember.class);
    }

    @Test
    public void testGetValueStandard()
    {
        familiarPermanentValue=5;
        valueModifier=0;
        familiarValue=0;
        familyMember.setFamiliarValue(familiarValue);
        familyMember.setFamiliarPermanentValue(familiarPermanentValue);
        familyMember.setValueModifier(5);

        assertEquals(0, familyMember.getValue());
        
    }

    @Test
    public void testGetValueReturn0()
    {
        familiarPermanentValue=5;
        valueModifier=-10;
        familiarValue=0;
        familyMember.setFamiliarValue(familiarValue);
        familyMember.setFamiliarPermanentValue(familiarPermanentValue);
        familyMember.setValueModifier(5);

        assertEquals(0, familyMember.getValue());
        
    }



}
