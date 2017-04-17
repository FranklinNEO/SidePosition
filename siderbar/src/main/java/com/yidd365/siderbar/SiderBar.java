package com.yidd365.siderbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class SiderBar extends View {
    private OnTouchingBarChangedListener OnTouchingBarChangedListener;
    private Paint paint = new Paint();
    private AlertDialog msgDialog = null;
    private TextView msgText = null;
    private int size = 0;
    private int select_pos = -1;

    private int selectedColor;
    private int unSelectedColor;
    private int backgroundColor;

    public SiderBar(Context context) {
        super(context);
    }

    public SiderBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SiderBar);
        selectedColor = a.getColor(R.styleable.SiderBar_selectedColor, 0xFFFF3A3F);
        unSelectedColor = a.getColor(R.styleable.SiderBar_unSelectedColor, 0xFFD4D4D4);
        backgroundColor = a.getColor(R.styleable.SiderBar_backgroundColor, 0xFFFFFFFF);
        a.recycle();
    }

    public SiderBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void initMsgDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.dialog_layout, null);
        msgText = (TextView) layout.findViewById(R.id.msg_tv);
        msgText.setTextColor(selectedColor);
        msgDialog = new AlertDialog.Builder(context, R.style.Dialog).setView(layout).create();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (size == 0)
            return;
        setBackgroundColor(backgroundColor);
        if (msgDialog == null) {
            initMsgDialog(this.getContext());
        }
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / size;

        for (int i = 0; i < size; i++) {
            if (!isInEditMode()) {
                paint.setColor(unSelectedColor);
            }
            paint.setAntiAlias(true);
            paint.setTextSize(DensityUtil.dip2px(getContext(), 10));
            if (i == select_pos) {
                paint.setColor(selectedColor);
                paint.setFakeBoldText(true);
            }
            float xPos = width / 2 - paint.measureText(getDrawText(i)) / 2;
            float yPos = singleHeight * i + (singleHeight / 2);
            canvas.drawText(getDrawText(i), xPos, yPos, paint);
            paint.reset();
        }

        if (select_pos != -1) {
            msgText.setText(getDrawText(select_pos));
            msgDialog.show();
        } else {
            if (msgDialog.isShowing()) {
                msgDialog.dismiss();
            }
        }

    }

    private String getDrawText(int i) {
        if (i < 0 || i > size)
            return "";
        return (1 + 5 * i) + "-" + (5 * (i + 1));
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (size == 0)
            return true;
        final int action = event.getAction();
        final float y = event.getY();
        final int selected = select_pos;
        final OnTouchingBarChangedListener listener = OnTouchingBarChangedListener;
        final int position = (int) (y / getHeight() * size);

        switch (action) {
            case MotionEvent.ACTION_UP:
                select_pos = -1;//
                invalidate();
                break;
            default:
                if (selected != position) {
                    if (position >= 0 && position < size) {
                        if (listener != null) {
                            listener.OnTouchingBarChangedListener(position);
                        }
                        select_pos = position;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public void setOnTouchingBarChangedListener(OnTouchingBarChangedListener OnTouchingBarChangedListener) {
        this.OnTouchingBarChangedListener = OnTouchingBarChangedListener;
    }

    public interface OnTouchingBarChangedListener {
        void OnTouchingBarChangedListener(int pos);
    }

    public void setSize(int size) {
        if (size > 0)
            this.size = (int) Math.ceil((double) size / 5);
        postInvalidate();
    }
}