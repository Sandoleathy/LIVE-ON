package models;

import iface.DragType;
import iface.Drags;
import thread.DragThread;

public class Tranquilizer implements Drags {
    private static final DragType type = DragType.TRANQUILIZER;
    private int duration;
    public Tranquilizer(int duration){
        this.duration = duration;
    }
    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public DragType getType() {
        return type;
    }
}
