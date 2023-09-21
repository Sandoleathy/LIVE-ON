using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using UnityEngine;

public class HeartBeatSystem : MonoBehaviour
{
    public int HeartBeatRate;
    private HeartBeatStatus BodyState;
    public const int DEAD_LINE_HIGHEST = 220;
    public const int DEAD_LINE_LOWEST = 45;
    private Queue<BPMChange> ChangeQueue;
    private LinkedList<IDrags> DragList;
    private Thread DragThread1 = null;
    private Thread HeartBeatThread1 = null;
    private HeartBeatThread HBSThread;
    private DragThread DragTh;

    // Start is called before the first frame update
    // Start function also use as a constructor
    void Start()
    {
        BodyState = HeartBeatStatus.RESTING;
        HeartBeatRate = 60;
        ChangeQueue = new Queue<BPMChange>();
        DragList = new LinkedList<IDrags>();
        //init hearbeat thread and start it
        HBSThread = new HeartBeatThread(this);
        HeartBeatThread1 = new Thread(HBSThread.Run);
        HeartBeatThread1.IsBackground = true;
        HeartBeatThread1.Start();
        DragTh = new DragThread(this);
        Debug.Log("initialized success process start");
    }

    // Update is called once per frame
    // You can consider Update function as a Main function
    void Update()
    {
        
    }
    private void OnApplicationPause(bool pause)
    {
        if (DragTh == null || HBSThread == null)
        {
            return;
        }
        if (pause)
        {
            Debug.Log("Pause");
            DragTh.Pause();
            HBSThread.Pause();
        }
        else
        {
            DragTh.UnPause();
            HBSThread.UnPause();
        }
        
    }
    // When game quit shutdown threads
    private void OnApplicationQuit()
    {
        if(HeartBeatThread1 != null)
        {
            HeartBeatThread1.Abort();
        }
        if(DragThread1 != null)
        {
            DragThread1.Abort();
        }
    }
    public void TakeHit()
    {
        ChangeQueue.Enqueue( new BPMChange( 500 , 0 , 10 ) );
    }
    public void Jump()
    {
        ChangeQueue.Enqueue(new BPMChange(200, 40, 5));
    }
    public void SlowRun()
    {
        ChangeQueue.Enqueue(new BPMChange(150, 40, 16));
    }
    public void DeepBreathe()
    {
        ChangeQueue.Enqueue(new BPMChange(220, 70, -10));
    }
    public void FastRun()
    {
        ChangeQueue.Enqueue(new BPMChange(200, 40, 16));
    }
    public void HeavyBleed()
    {
        ChangeQueue.Enqueue(new BPMChange(500, 0, 5));
    }
    public void Fall()
    {
        ChangeQueue.Enqueue(new BPMChange(500, 0, 10));
    }
    private void ChangeQueueAdd(BPMChange change)
    {
        /*
         * 使用了和Java相同的同步锁来防止异步操作
         */
        lock (this.ChangeQueue)
        {
            ChangeQueue.Enqueue(change);
        }
    }
    public void useDrag(IDrags drag)
    {
        DragList.AddFirst(drag);
        if (drag.GetType() == DragTypes.ADRENALINE)
        {//using adrenaline
            ChangeBodyState(HeartBeatStatus.RAGE);
            ChangeQueueAdd(new BPMChange(500, 0, 25));
            ChangeQueueAdd(new BPMChange(500, 0, 25));
        }
        else if (drag.GetType() == DragTypes.TRANQUILIZER)
        {//use tranquilizer
            ChangeBodyState(HeartBeatStatus.RESTING);
            ChangeQueueAdd(new BPMChange(500, 0, -25));
            ChangeQueueAdd(new BPMChange(500, 0, -25));
        }
        if(DragThread1 != null)
        {
            return;
        }
        DragThread1= new Thread(DragTh.Run);
        DragThread1.IsBackground = true;
        DragThread1.Start();
        /*
        if (thread != null)
        {
            return;
        }
        thread = new DragThread(this);
        new Thread(thread).start();
        */
    }
    /**
     * Make sure body's heartbeat will randomly float
     */
    public void RandomrandomChangeHeartBeat()
    {
        if(ChangeQueue.Count >= 3)
        {
            return;
        }
        System.Random ran = new System.Random();
        int i = ran.Next(10);
        if(BodyState == HeartBeatStatus.RESTING)
        {
            if (HeartBeatRate > 80)
            {
                ChangeQueueAdd( new BPMChange( 500, 0, -i ) );
            }
            else if (HeartBeatRate < 60)
            {
                ChangeQueueAdd( new BPMChange( 500, 0, i ) );
            }
            else
            {
                ChangeQueueAdd(new BPMChange(500, 0, i-5 ) );
            }
        }else if (BodyState == HeartBeatStatus.RAGE)
        {
            if (HeartBeatRate > 160)
            {
                ChangeQueueAdd(new BPMChange(500, 0, -i ));
            }
            else if (HeartBeatRate < 130)
            {
                ChangeQueueAdd(new BPMChange(500, 0, i ));
            }
            else
            {
                ChangeQueueAdd(new BPMChange(500, 0, i-5 ));
            }
        }else if (BodyState == HeartBeatStatus.NERVOUS)
        {
            if (HeartBeatRate > 110)
            {
                ChangeQueueAdd(new BPMChange(500, 0, -i ));
            }
            else if (HeartBeatRate < 80)
            {
                ChangeQueueAdd(new BPMChange(500, 0, i ));
            }
            else
            {
                ChangeQueueAdd(new BPMChange(500, 0, i-5 ));
            }
        }
    }
    public void UpdateHeartBeat()
    {
        Debug.Log("Update Heartbeat");
        if(ChangeQueue.Count == 0)
        {
            return;
        }
        BPMChange Change = ChangeQueue.Dequeue();
        if (HeartBeatRate < Change.upLimit && HeartBeatRate > Change.downLimit)
        {
            HeartBeatRate = HeartBeatRate + Change.changeValue;
        }
        if (HeartBeatRate <= 80)
        {
            BodyState = HeartBeatStatus.RESTING;
        }
        else if (HeartBeatRate <= 110)
        {
            BodyState = HeartBeatStatus.NERVOUS;
        }
        else
        {
            BodyState = HeartBeatStatus.RAGE;
        }
    }
    public void RemoveDrag(IDrags d)
    {
        Debug.Log("Remove Drag");
        DragList.Remove(d);
    }
    public void ResetHeartBeatRate()
    {
        Debug.Log("Reset HeartBeat Rate");
        this.HeartBeatRate = 60;
    }
    public void ChangeBodyState( HeartBeatStatus h )
    {
        this.BodyState = h;
    }
    public int GetHeartBeatRate()
    {
        return HeartBeatRate;
    }
    public HeartBeatStatus GetBodyState()
    {
        return BodyState;
    }
    public Queue<BPMChange> GetChangeQueue()
    {
        return ChangeQueue;
    }
    public LinkedList<IDrags> GetDragList()
    {
        return DragList;
    }
}
