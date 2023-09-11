import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class HeartBeatSystem {
    private HeartBeatStatus bodyState;
    private int heartBeatRate;
    private Queue<BPMChange> changeQueue;
    private LinkedList<Drags> dragList;
    public final static int DEAD_LINE_HIGHEST = 220;
    public final static int DEAD_LINE_LOWEST = 45;
    public HeartBeatSystem(){

    }
    //get shot or take damage
    public void takeHit(){
        changeQueue.offer(new BPMChange(500,0,10));
    }
    public void jump(){
        changeQueue.offer(new BPMChange(200,40,5));
    }
    public void slowRun(){
        changeQueue.offer(new BPMChange(150,40,6));
    }
    public void deepBreathe(){
        changeQueue.offer(new BPMChange(220,70,-10));
    }
    public void fastRun(){
        changeQueue.offer(new BPMChange(200,40,16));
    }
    public void heavyBleed(){
        changeQueue.offer(new BPMChange(500,0,50));
    }
    public void fall(){
        changeQueue.offer(new BPMChange(500,0,10));
    }
    public void useDrag(Drags drag){
        dragList.add(drag);
        if(drag.getType() == DragType.ADRENALINE){//using adrenaline
            changeBodyState(HeartBeatStatus.RAGE);
        }
        else if(drag.getType() == DragType.TRANQUILIZER){//use tranquilizer
            changeBodyState(HeartBeatStatus.RESTING);
        }
    }
    private void changeBodyState(HeartBeatStatus type){
        bodyState = type;
    }
    public int getHeartBeatRate(){
        return heartBeatRate;
    }
    public Queue<BPMChange> getChangeQueue(){
        return changeQueue;
    }
    public HeartBeatStatus getBodyState(){
        return bodyState;
    }

}
