/**
 * This code is a DEMO for HeartBeatSystem
 * Which is an idea that comes,k out from Sandoleathy
 * Date: 2023/9/11
 */
public class Main {

    public static void main(String[] args) {
        HBSThread hbsThread = new HBSThread();
        Thread thread = new Thread(hbsThread);
        thread.start();
        HeartBeatSystem hbs = new HeartBeatSystem();
    }
}