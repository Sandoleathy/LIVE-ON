import java.util.Timer;
import java.util.TimerTask;

/**
 * 使用继承Runnable接口的方法创建多线程
 * 将这个类实例化让后放到Thread实例的构造器中，再让Thread实例调用start()方法
 * 多线程创建成功！
 */
public class HBSThread implements Runnable{
    private HeartBeatSystem hbs;//这个hbs对象要从主函数传过来，现在还没有解决多线程共同处理一个对象会出现抢资源的问题
    public HBSThread(HeartBeatSystem hbs){
        this.hbs = hbs;
    }
    Timer timer = new Timer();
    @Override
    public void run() {
        timer.schedule(new TimerTask(),0 , 2000);
    }
    private class TimerTask extends java.util.TimerTask{//一个内部类，继承了TimerTask用于给Timer的周期任务方法使用
        private HeartBeatSystem hbs = HBSThread.this.hbs;//这里的hbs同样继承自主线程，因为游戏心中只有一个太阳

        @Override
        public void run() {
            hbs.randomChangeHeartBeat();
            hbs.updateHeartBeat();
            System.out.println(hbs.getHeartBeatRate());
        }
    }
}
