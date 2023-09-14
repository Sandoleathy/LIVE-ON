package models;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class HBSJUnitTest {
    private static final HeartBeatSystem hbs = new HeartBeatSystem();
    @BeforeClass
    public static void init(){
        System.out.println("initialize test");

    }
    @Before
    public void before(){
        hbs.resetHeartBeatRate();
    }
    @Test
    public void testTakeHit(){
        hbs.takeHit();
        hbs.updateHeartBeat();
        assertEquals("Heartbeat change incorrect" , hbs.getHeartBeatRate() , 70);
    }
    @Test
    public void testJump(){
        hbs.jump();
        hbs.updateHeartBeat();
        assertEquals("Heartbeat change incorrect" , hbs.getHeartBeatRate() , 65);
    }
    @Test
    public void testSlowRun(){
        hbs.slowRun();
        hbs.updateHeartBeat();
        assertEquals("Heartbeat change incorrect" , hbs.getHeartBeatRate() , 66);
    }
}
