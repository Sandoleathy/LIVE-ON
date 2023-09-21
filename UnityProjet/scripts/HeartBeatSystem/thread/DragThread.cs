using System;
using System.Collections;
using System.Collections.Generic;
using System.Threading;
using UnityEngine;

public class DragThread
{
    private HeartBeatSystem HBS;
    private LinkedList<IDrags> DragList;
    private int Counter;
    private Boolean IsPause;//Pause lock when game pause the thread will not work
    public DragThread(HeartBeatSystem h)
    {
        this.HBS = h;
        Counter = 0;
        IsPause = false;
    }
    public void Pause()
    {
        IsPause = true;
    }
    public void UnPause()
    {
        IsPause = false;
    }
    private void UpdateDragList()
    {
        this.DragList = HBS.GetDragList();
    }
    public void Run()
    {
        while (true)
        {
            if (IsPause)
            {
                continue;
            }
            if (DragList.Count == 0)
            {
                Counter = 0;
            }
            Debug.Log(Counter / 1000 + " second");
            foreach (IDrags d in DragList)
            {
                if (d.GetDuration() <= Counter)
                {
                    HBS.RemoveDrag(d);
                    UpdateDragList();
                }
                Counter = Counter + 1000;
            }
            Thread.Sleep(1000);
        }
    }
}
