using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Adrenaline : IDrags
{
    public const DragTypes Type = DragTypes.ADRENALINE;
    private readonly int Duration;
    public Adrenaline(int duration)
    {
        this.Duration = duration;
    }
    public int GetDuration()
    {
        return Duration;
    }

    DragTypes IDrags.GetType()
    {
        return Type;
    }

}
