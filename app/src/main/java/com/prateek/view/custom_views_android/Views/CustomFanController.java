package com.prateek.view.custom_views_android.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CustomFanController extends View {

    private static int SELECTION_COUNT = 4; // Total number of selections.
    private float mWidth;                   // Custom view width.
    private float mHeight;                  // Custom view height.
    private Paint mTextPaint;               // For text in the view.
    private Paint mDialPaint;               // For dial circle in the view.
    private float mRadius;                  // Radius of the circle.
    private int mActiveSelection;           // The active selection.
    // String buffer for dial labels and float for ComputeXY result.
    private final StringBuffer mTempLabel = new StringBuffer(8);
    private final float[] mTempResult = new float[2];



    public CustomFanController(Context context) {
        super(context);
        init();//we call here to improve performance,bcz onDraw is called multiple times
    }

    public CustomFanController(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomFanController(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomFanController(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init(){
        //Antialiasing is the smoothing of the image.
        //ANTI_ALIAS_FLAG -Paint flag that enables antialiasing when drawing.
        mTextPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);//Geometry and text drawn with this style will be both filled and stroked at the same time
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(40f);
        mDialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDialPaint.setColor(Color.GRAY);
        mActiveSelection = 0;

        //whenever we click on the view we call this
        //works same as onTouchEvent
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rotate selection to the next valid choice.
                mActiveSelection = (mActiveSelection + 1) % SELECTION_COUNT;
                // Set dial background color to green if selection is >= 1.
                if (mActiveSelection >= 1) {
                    mDialPaint.setColor(Color.GREEN);
                } else {
                    mDialPaint.setColor(Color.GRAY);
                }
                // Redraw the view.
                invalidate();
            }
        });

        //The invalidate() method of View invalidates the entire view, forcing a call to onDraw()
        // to redraw the view. If something in your custom view changes and the change needs to be displayed,
        // you need to call invalidate()

    }


    //This is called during layout when the size of this view has changed. If you were just added to
    // the view hierarchy, you're called with the old values of 0.
    //It will be called while rotating also i guess.

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        mRadius = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);//half of min of length or width and * 0.8 bcz
                                                                //it will be lil less than boundary.
    }


    private float[] computeXYForPosition(final int pos, final float radius) {
        float[] result = mTempResult;
        Double startAngle = Math.PI * (9 / 8d);   // Angles are in radians.
        Double angle = startAngle + (pos * (Math.PI / 4));
        result[0] = (float) (radius * Math.cos(angle)) + (mWidth / 2);
        result[1] = (float) (radius * Math.sin(angle)) + (mHeight / 2);
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw dial
        canvas.drawCircle(mWidth/2,mHeight/2,mRadius,mDialPaint);
        //cy- x-coordinate of the center of the circle to be drawn
        //cy -y-coordinate of the center of the circle to be drawn
        final float labelRadius = mRadius + 20;//bcz text will be outside the circle
        StringBuffer label = mTempLabel;
        for (int i = 0; i < SELECTION_COUNT; i++) {
            float[] xyData = computeXYForPosition(i, labelRadius);
            float x = xyData[0];
            float y = xyData[1];
            label.setLength(0);
            label.append(i);
            canvas.drawText(label, 0, label.length(), x, y, mTextPaint);
        }

        final float markerRadius = mRadius - 35;
        float[] xyData = computeXYForPosition(mActiveSelection,
                markerRadius);
        float x = xyData[0];
        float y = xyData[1];
        canvas.drawCircle(x, y, 20, mTextPaint);

    }
}
