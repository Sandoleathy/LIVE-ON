/**
 * This code is a DEMO for HeartBeatSystem
 * Which is an idea that come out from Sandoleathy
 * Date: 2023/9/11
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class HeartBeatSystem {
    private HeartBeatStatus bodyState;
    private int heartBeatRate;
    private Queue<BPMChange> changeQueue;
    private LinkedList<Drags> dragList;
    public final static int DEAD_LINE_HIGHEST = 220;
    public final static int DEAD_LINE_LOWEST = 45;
    public HeartBeatSystem(){
        bodyState = HeartBeatStatus.RESTING;
        heartBeatRate = 60;
        changeQueue = new LinkedList<BPMChange>();
        dragList = new LinkedList<Drags>();
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
        changeQueue.offer(new BPMChange(500,0,5));
    }
    public void fall(){
        changeQueue.offer(new BPMChange(500,0,10));
    }

    /**
     * Make sure body's heartbeat will randomly float
     */
    public void randomChangeHeartBeat(){
        if(changeQueue.size() < 3){
            Random random = new Random();
            if(bodyState == HeartBeatStatus.RESTING){
                if(heartBeatRate > 80){
                    changeQueue.offer(new BPMChange(500,0,-(random.nextInt(10))));
                }else if(heartBeatRate < 60){
                    changeQueue.offer(new BPMChange(500,0,random.nextInt(10)));
                }else{
                    changeQueue.offer(new BPMChange(500,0, (random.nextInt(10)) - 5 ));
                }
            }
            else if(bodyState == HeartBeatStatus.RAGE){
                if(heartBeatRate > 160){
                    changeQueue.offer(new BPMChange(500,0,-(random.nextInt(10))));
                }else if(heartBeatRate < 130){
                    changeQueue.offer(new BPMChange(500,0,random.nextInt(10)));
                }else{
                    changeQueue.offer(new BPMChange(500,0, (random.nextInt(10)) - 5 ));
                }
            }
            else if(bodyState == HeartBeatStatus.NERVOUS){
                if(heartBeatRate > 110){
                    changeQueue.offer(new BPMChange(500,0,-(random.nextInt(10))));
                }else if(heartBeatRate < 80){
                    changeQueue.offer(new BPMChange(500,0,random.nextInt(10)));
                }else{
                    changeQueue.offer(new BPMChange(500,0, (random.nextInt(10)) - 5 ));
                }
            }
        }
    }
    public void useDrag(Drags drag){
        dragList.add(drag);
        if(drag.getType() == DragType.ADRENALINE){//using adrenaline
            changeBodyState(HeartBeatStatus.RAGE);
            changeQueue.offer(new BPMChange(500,0,25));
            changeQueue.offer(new BPMChange(500,0,25));
        }
        else if(drag.getType() == DragType.TRANQUILIZER){//use tranquilizer
            changeBodyState(HeartBeatStatus.RESTING);
            changeQueue.offer(new BPMChange(500,0,-25));
            changeQueue.offer(new BPMChange(500,0,-25));
        }
    }

    /**
     * pull a value from BPM changeList
     * add it to HeartBeat
     * also check BPM and update body state
     */
    public void updateHeartBeat(){
        if(changeQueue.isEmpty()){
            return;
        }
        BPMChange change = changeQueue.poll();
        if(heartBeatRate < change.getUpLimit() && heartBeatRate > change.getDownLimit()){
            heartBeatRate = heartBeatRate + change.getBPMChange();
        }
        if(heartBeatRate <= 80){
            bodyState = HeartBeatStatus.RESTING;
        }
        else if(heartBeatRate <= 110){
            bodyState = HeartBeatStatus.NERVOUS;
        }
        else {
            bodyState = HeartBeatStatus.RAGE;
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
