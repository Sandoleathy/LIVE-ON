package thread;

import iface.Drags;
import models.HeartBeatSystem;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class DragThread implements Runnable{
    private HeartBeatSystem hbs;
    private LinkedList<Drags> dragList;
    private int maxDuration;
    private Timer timer;
    private int counter;
    public DragThread(HeartBeatSystem hbs){
        this.hbs = hbs;
        timer = new Timer();
        updateMaxDuration();
        counter = 0;
    }

    /**
     * 这个方法用于更新计时器的计时时长
     * 每次一个药物时间达到上限了都要从列表删除那个药物，然后调用一次此方法
     */
    private void updateMaxDuration(){
        dragList = hbs.getDragList();
        maxDuration = 0;
        if(dragList.isEmpty()){
            return;
        }
        for(Drags drag : dragList){
            if(drag.getDuration() > maxDuration){
                maxDuration = drag.getDuration();
            }
        }
    }

    /**
     * 当药物列表是空的时候，线程不停止
     * 每隔0.5秒刷新一次，检测药物列表是否为空
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
                    updateMaxDuration();
                }
                counter = counter + 1000;
            }
        }
    }
}
