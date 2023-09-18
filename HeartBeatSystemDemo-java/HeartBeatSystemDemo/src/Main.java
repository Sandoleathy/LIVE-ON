import models.Adrenaline;
import models.HeartBeatSystem;
import thread.DragThread;
import thread.HBSThread;

/**
 * This code is a DEMO for Models.HeartBeatSystem
 * Which is an idea that comes,k out from Sandoleathy
 * Date: 2023/9/11
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        HeartBeatSystem hbs = new HeartBeatSystem();
        HBSThread hbsThread = new HBSThread(hbs);
        new Thread(hbsThread).start();
        hbs.useDrag(new Adrenaline(20000));
    }
}