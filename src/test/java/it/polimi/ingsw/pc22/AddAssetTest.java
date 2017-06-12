package it.polimi.ingsw.pc22;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Created by matteo on 12/06/17.
 */
public class AddAssetTest extends TestCase {


    public AddAssetTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( AddAssetTest.class );
    }

    public void testApp()
    {
        assertTrue( true );
    }



}
