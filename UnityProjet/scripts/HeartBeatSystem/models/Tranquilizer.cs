using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Tranquilizer : IDrags
{
    public const DragTypes Type = DragTypes.TRANQUILIZER;
    private readonly int Duration;
    public Tranquilizer(int duration)
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
