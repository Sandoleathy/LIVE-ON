import java.util.LinkedList;
import java.util.Stack;

public class HeartBeatSystem {
    private HeartBeatStatus bodyState;
    private int heartBeatRate;
    private Stack<Integer> changeStack;
    private LinkedList<Drags> dragList;
    public final static int DEAD_LINE_HIGHEST = 220;
    public final static int DEAD_LINE_LOWEST = 45;
    public HeartBeatSystem(){

    }
    public int getHeartBeatRate(){
        return heartBeatRate;
    }
    public Stack<Integer> getChangeStack(){
        return changeStack;
    }
    public HeartBeatStatus getBodyState(){
        return bodyState;
    }

}
