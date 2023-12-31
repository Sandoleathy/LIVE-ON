package models; /**
 * This code is a DEMO for Models.HeartBeatSystem
 * Which is an idea that comes out from Sandoleathy
 * Date: 2023/9/11
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import iface.*;
import thread.DragThread;

public class HeartBeatSystem {
    private HeartBeatStatus bodyState;
    private int heartBeatRate;
    private final Queue<BPMChange> changeQueue;
    private final LinkedList<Drags> dragList;
    private DragThread thread = null;
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
        if(changeQueue.size() <= 3){
            Random random = new Random();
            if(bodyState == HeartBeatStatus.RESTING){
                if(heartBeatRate > 80){
                    offerChangeQueue( new BPMChange(500,0,-(random.nextInt(10))) );
                }else if(heartBeatRate < 60){
                    offerChangeQueue( new BPMChange(500,0,random.nextInt(10)) );
                }else{
                    offerChangeQueue( new BPMChange(500,0, (random.nextInt(10)) - 5 ) );
                }
            }
            else if(bodyState == HeartBeatStatus.RAGE){
                if(heartBeatRate > 160){
                    offerChangeQueue( new BPMChange(500,0,-(random.nextInt(10))) );
                }else if(heartBeatRate < 130){
                    offerChangeQueue( new BPMChange(500,0,random.nextInt(10)) );
                }else{
                    offerChangeQueue( new BPMChange(500,0, (random.nextInt(10)) - 5 ) );
                }
            }
            else if(bodyState == HeartBeatStatus.NERVOUS){
                if(heartBeatRate > 110){
                    offerChangeQueue( new BPMChange(500,0,-(random.nextInt(10))) );
                }else if(heartBeatRate < 80){
                    offerChangeQueue( new BPMChange(500,0,random.nextInt(10)) );
                }else{
                    offerChangeQueue( new BPMChange(500,0, (random.nextInt(10)) - 5 ) );
                }
            }
        }
    }
    /**
     * 还是用一个列表存储所有药品比较方便捏
     */
    public void useDrag(Drags drag){
        dragList.add(drag);
        if(drag.getType() == DragType.ADRENALINE){//using adrenaline
            changeBodyState(HeartBeatStatus.RAGE);
            offerChangeQueue(new BPMChange(500,0,25));
            offerChangeQueue(new BPMChange(500,0,25));
        }
        else if(drag.getType() == DragType.TRANQUILIZER){//use tranquilizer
            changeBodyState(HeartBeatStatus.RESTING);
            offerChangeQueue(new BPMChange(500,0,-25));
            offerChangeQueue(new BPMChange(500,0,-25));
        }
        if(thread != null){
            return;
        }
        thread = new DragThread(this);
        new Thread(thread).start();
    }

    /**
     * pull a value from BPM changeList
     * add it to HeartBeat
     * also check BPM and update body state
     */
    public void updateHeartBeat(){
        System.out.println("update heart beat");
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

    /**\
     * 使用同步代码块，防止在未来有频繁操作时出现丢失心率变化等问题
     * @param change BPMChange对象，用于表示心率变化以及上下限
     */
    private void offerChangeQueue(BPMChange change){
        synchronized (this.changeQueue){
            changeQueue.offer(change);
        }
    }
    private void changeBodyState(HeartBeatStatus type){
        bodyState = type;
    }
    public LinkedList<Drags> getDragList(){
        return dragList;
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
    public void removeDrag(Drags drag){
        System.out.println("remove drag");
        dragList.remove(drag);
    }
    public void resetHeartBeatRate(){
        heartBeatRate = 60;
    }

}
