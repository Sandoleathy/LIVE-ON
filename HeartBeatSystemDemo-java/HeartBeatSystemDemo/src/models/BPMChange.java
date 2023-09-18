package models;

public class BPMChange {
    private int upLimit = HeartBeatSystem.DEAD_LINE_HIGHEST;
    private int downLimit = HeartBeatSystem.DEAD_LINE_LOWEST;
    private int changeValue;
    public BPMChange(int up , int down , int change){
        upLimit = up;
        downLimit = down;
        changeValue = change;
    }
    public int getUpLimit(){
        return  upLimit;
    }
    public int getDownLimit(){
        return downLimit;
    }
    public int getBPMChange(){
        return changeValue;
    }
    @Override
    public String toString(){
        return downLimit + "-" + upLimit + " " + changeValue;
    }
}
