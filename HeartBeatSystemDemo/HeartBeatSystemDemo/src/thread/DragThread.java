package thread;

import iface.Drags;
import models.HeartBeatSystem;

import java.util.LinkedList;
import java.util.Timer;

public class DragThread implements Runnable{
    private HeartBeatSystem hbs;
    private LinkedList<Drags> dragList;
    private Timer timer;
    private int counter;
    public DragThread(HeartBeatSystem hbs){
        this.hbs = hbs;
        timer = new Timer();
        updateDragList();
        counter = 0;
    }

    /**
     * 这个方法用于更新计药物列表
     * 每次task执行时都会被调用
     */
    private void updateDragList(){
        dragList = hbs.getDragList();
        /*
        if(dragList.isEmpty()){
            return;
        }
        for(Drags drag : dragList){
            if(drag.getDuration() > maxDuration){
                maxDuration = drag.getDuration();
            }
        }
        */
    }

    /**
     * 该线程直接从hbs对象中启动，一旦启动就不再关闭
     */
    @Override
    public void run(){
        TimerTask task = new TimerTask();
        timer.schedule(task, 0 , 1000);
    }
    private class TimerTask extends java.util.TimerTask{
        @Override
        public void run() {
            if(dragList.isEmpty()){
                counter = 0;
            }
            System.out.println(counter/1000 + " second");
            for(Drags d : dragList){
                if(d.getDuration() <= counter){
                    hbs.removeDrag(d);
                    updateDragList();
                }
                counter = counter + 1000;
            }
        }
    }
}
