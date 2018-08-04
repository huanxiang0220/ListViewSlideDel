package com.tang.listviewslidedel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;

/**
 * author：Tang
 * 创建时间：2018/8/4
 * Description：重写ListView，添加item左滑显示删除按钮功能
 */
public class SlideDelListView extends ListView {

    public SlideDelListView(Context context) {
        this(context, null);
    }

    public SlideDelListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private PopupWindow popupWindow;
    private int winHeight;
    private int touchSlop;

    public SlideDelListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.layout_del, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.getContentView().measure(0, 0);
//        winWidth = popupWindow.getContentView().getMeasuredWidth();
        winHeight = popupWindow.getContentView().getMeasuredHeight();
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private float xDown, yDown;
    private View curView;
    private boolean isSliding;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float x = ev.getX();
        float y = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                xDown = x;
                yDown = y;
                //如果当前的PopupWindow显示，则直接隐藏，然后屏蔽touch事件的下传
                if (popupWindow.isShowing()) {
                    dismissPopWindow();
                    return false;//不执行onTouch
                }
                //获取当前按下的itemView和位置
                int curPos = pointToPosition((int) xDown, (int) yDown);
                curView = getChildAt(curPos - getFirstVisiblePosition());
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = x - xDown;
                float dy = y - yDown;
                //判断是否满足条件显示popupWindow
                if (dx > touchSlop && Math.abs(dy) < touchSlop) {
                    isSliding = true;//可以滑动
                }
                break;
        }
        return curView != null && super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isSliding) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    //获取弹出框的准确的位置，在该想item的垂直居中
                    int[] location = new int[2];
                    //获取当前item的x,y
                    curView.getLocationOnScreen(location);
                    popupWindow.setAnimationStyle(R.style.AnimationPreview);
                    popupWindow.update();
                    popupWindow.showAtLocation(curView, Gravity.TOP | Gravity.START,
                            location[0] + curView.getWidth(),
                            location[1] + curView.getHeight() / 2 - winHeight / 2);
                    break;
                case MotionEvent.ACTION_UP:
                    isSliding = false;
                    break;
            }
            return true;//可以滑动时自己消耗滑动事件
        }
        return super.onTouchEvent(ev);
    }

    private void dismissPopWindow() {
        if (popupWindow != null) popupWindow.dismiss();
    }

}
