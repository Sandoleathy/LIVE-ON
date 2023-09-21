using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public struct BPMChange
{
    public readonly int upLimit;
    public readonly int downLimit;
    public readonly int changeValue;
    public BPMChange(int up, int down, int change)
    {
        upLimit = up;
        downLimit = down;
        changeValue = change;
    }
    override
    public readonly string ToString()
    {
        return downLimit + "-" + upLimit + " " + changeValue;
    }
}
