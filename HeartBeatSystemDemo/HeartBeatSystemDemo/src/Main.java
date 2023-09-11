/**
 * This code is a DEMO for HeartBeatSystem
 * Which is an idea that comes,k out from Sandoleathy
 * Date: 2023/9/11
 */
public class Main {

    public static void main(String[] args) {
        HeartBeatSystem hbs = new HeartBeatSystem();
        HBSThread hbsThread = new HBSThread(hbs);
        Thread thread = new Thread(hbsThread);
        thread.start();
        hbs.useDrag(new Adrenaline(1000000));
    }
}