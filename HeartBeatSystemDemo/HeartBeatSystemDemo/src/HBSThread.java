import java.util.Timer;
import java.util.TimerTask;

/**
 * 使用继承Runnable接口的方法创建多线程
 * 将这个类实例化让后放到Thread实例的构造器中，再让Thread实例调用start()方法
 * 多线程创建成功！
 */
public class HBSThread implements Runnable{
    Timer timer = new Timer();
    @Override
    public void run() {
        timer.schedule(new TimerTask(),0 , 2000);
    }
    private class TimerTask extends java.util.TimerTask{
        private HeartBeatSystem hbs = new HeartBeatSystem();

        @Override
        public void run() {
            hbs.randomChangeHeartBeat();
            hbs.updateHeartBeat();
            System.out.println(hbs.getHeartBeatRate());
        }
    }
}
