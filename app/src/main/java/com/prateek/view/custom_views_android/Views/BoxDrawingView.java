package com.prateek.view.custom_views_android.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.prateek.view.custom_views_android.Extras.Box;

import java.util.ArrayList;
import java.util.List;

public class BoxDrawingView extends View {

    private Box currentBox;
    private List<Box> boxes=new ArrayList<>();
    private Paint boxPaint;
    private Paint backgroundPaint;

    public BoxDrawingView(Context context) {
        super(context);
        init();
    }

    public BoxDrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BoxDrawingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BoxDrawingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    void init() {
        boxPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        boxPaint.setColor(0x22ff0000);//semitransparent red so that we can see all the rectangles
        backgroundPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(Color.GRAY);

    }


    //works same as setOnClickListner(View.OnClickListner),but we are subclassing View so we can use this shortcut
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());
        String action = "";
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                action = "ACTION_DOWN";
                //Reset Drawing state
                currentBox=new Box(current);
                boxes.add(currentBox);
                //we save box in arraylist bcz when we call invalidate it redraws the entire screen and we should not
                //loose previous rectangles,and when we move our touch we just keep changing the same currentbox instance
                //with latest current position
                break;
            case MotionEvent.ACTION_MOVE:
                action = "ACTION_MOVE";
                if(currentBox!=null){
                    currentBox.setCurrent(current);
                    //origin remains same until we take off our touch and touch again which causes currentBox to be instantiate again
                    //and then we work on nw box
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                action = "ACTION_CANCEL";
                currentBox=null;
                break;
        }
        Log.i("TAG1",action+"x="+event.getX()+" y="+event.getY());
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawPaint(backgroundPaint);//to make background color as gray
        Log.e("TAG2", boxes.size()+"");

        for(Box box:boxes){
            float left=Math.min(box.getOrigin().x,box.getCurrent().x);
            float right=Math.max(box.getOrigin().x,box.getCurrent().x);
            float top=Math.min(box.getOrigin().y,box.getCurrent().y);
            float bottom=Math.max(box.getOrigin().y,box.getCurrent().y);

            canvas.drawRect(left,top,right,bottom,boxPaint);
        }
    }
}
