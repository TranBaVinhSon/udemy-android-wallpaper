package com.example.sontbv.wallpaper.Utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by sontbv on 12/3/17.
 */

public class SquareImage extends ImageView {
    public SquareImage(Context context) {
        super(context);
    }

    public SquareImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // This function has 2 params are width and height.
        // And now both of them equal width of phone
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
