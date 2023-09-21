using System;
using System.Collections;
using System.Collections.Generic;
using System.Threading;
using UnityEngine;

public class HeartBeatThread
{
    private HeartBeatSystem HBS;
    private Boolean IsPause;
    public HeartBeatThread(HeartBeatSystem hbs)
    {
        this.HBS = hbs;
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
    public void Run() 
    {
        while (true)
        {
            if (IsPause)
            {
                continue;
            }
            Update();
            Thread.Sleep(2000);
        }
    }
    public void Update()
    {
        HBS.RandomrandomChangeHeartBeat();
        HBS.UpdateHeartBeat();
        Debug.Log(HBS.GetHeartBeatRate());
    }
}
