package com.prateek.view.custom_views_android.Extras;

import android.graphics.PointF;

public class Box {
    private PointF mOrigin;
    private PointF mCurrent;
    public Box(PointF pointF){
        mOrigin=pointF;
        mCurrent=pointF;
    }

    public PointF getOrigin() {
        return mOrigin;
    }

    public void setOrigin(PointF mOrigin) {
        this.mOrigin = mOrigin;
    }

    public PointF getCurrent() {
        return mCurrent;
    }

    public void setCurrent(PointF mCurrent) {
        this.mCurrent = mCurrent;
    }
}
