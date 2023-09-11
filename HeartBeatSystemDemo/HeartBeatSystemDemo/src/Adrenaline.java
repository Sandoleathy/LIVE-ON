public class Adrenaline implements Drags{
    public static final DragType type = DragType.ADRENALINE;
    private int duration;
    public Adrenaline(int duration){
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
