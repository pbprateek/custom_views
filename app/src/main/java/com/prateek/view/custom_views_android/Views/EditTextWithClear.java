package com.prateek.view.custom_views_android.Views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.prateek.view.custom_views_android.R;

public class EditTextWithClear extends AppCompatEditText {

    //AppCompatEditText extends EditText,EditText extends TextView And TextView extends View
    //If u wanna make some changes then just browse methods in parent class as they have nice doc what each method does


    Drawable mClearButtonImage;

    public EditTextWithClear(Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mClearButtonImage = ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_baseline_opaque_24px, null);


        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float clearButtonStart; // Used for LTR languages
                boolean isClearButtonClicked = false;
                //position where we will put cross button
                clearButtonStart = (getWidth() - getPaddingEnd() - mClearButtonImage.getIntrinsicWidth());

                // If the touch occurred after the start of the button(means cross button area but inside EditText),
                // set isClearButtonClicked to true.

                if (event.getX() > clearButtonStart) {
                    isClearButtonClicked = true;
                }
                if (isClearButtonClicked) {
                    // Check for ACTION_DOWN (always occurs before ACTION_UP).

                    //by ACTION_DOWN we will get the initial position when we first touch and by ACTION_UP we get final
                    //position where we release our touch,so when we touch the scree we change the cross from opaque to dark
                    //and finally clear text and clear cross button.

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        // Switch to the black version of clear button.
                        mClearButtonImage = ResourcesCompat.getDrawable(getResources(),
                                R.drawable.ic_baseline_clear_24px, null);
                        showClearButton();

                    }
                    // Check for ACTION_UP.
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        // Switch to the opaque version of clear button.
                        mClearButtonImage = ResourcesCompat.getDrawable(getResources(),
                                R.drawable.ic_baseline_opaque_24px, null);
                        // Clear the text and hide the clear button.
                        getText().clear();
                        hideClearButton();
                        return true;
                    }

                }
                return false;
            }
        });

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showClearButton();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void showClearButton() {
        //setCompoundDrawablesRelativeWithIntrinsicBounds method is from Textview
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null,                      // Start of text.
                        null,               // Above text.
                        mClearButtonImage,  // End of text.
                        null);              // Below text.
    }

    private void hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null,             // Start of text.
                        null,      // Above text.
                        null,      // End of text.
                        null);     // Below text.
    }


}
