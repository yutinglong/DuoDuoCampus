package com.duoduo.duoduocampus.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.TextView;
import com.duoduo.duoduocampus.utils.DisplayUtil;

public class AdjustableTextView extends TextView {
    private Context mContext;
    private static float TEXT_MAX_SIZE = 18.0f;
    private static float TEXT_MIN_SIZE = 18.0f;

    public AdjustableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public AdjustableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AdjustableTextView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        TEXT_MAX_SIZE = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,20,getResources().getDisplayMetrics());
        TEXT_MIN_SIZE = TEXT_MAX_SIZE*2f/3f;
    }

    /**
     * 调整自身最大宽度
     * @param isWidthChange
     */
    public void setSelfMaxWidth(boolean isWidthChange) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        if (isWidthChange) {
                if (width == 480) {
                    setMaxWidth(DisplayUtil.dipToPixels(mContext,
                            100));
                } else if (width == 720) {
                    setMaxWidth(DisplayUtil.dipToPixels(mContext,
                            140));
                } else if (width == 1080) {
                    setMaxWidth(DisplayUtil.dipToPixels(mContext,
                            150));
                }
        } else {
                if (width == 480) {
                    setMaxWidth(DisplayUtil.dipToPixels(mContext,
                            140));
                } else if (width == 720) {
                    setMaxWidth(DisplayUtil.dipToPixels(mContext,
                            160));
                } else if (width == 1080) {
                    setMaxWidth(DisplayUtil.dipToPixels(mContext,
                            160));
                }
        }
        if(!TextUtils.isEmpty(getText().toString())){
            measureAdjustSize(getText().toString());
        }
    }


    public void setResizeText(String text){
        measureAdjustSize(text);
        setText(text);
    }


    @SuppressLint("NewApi")
	private void measureAdjustSize(String text){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
            int textCount = text.length();
            float textWidth = textCount * TEXT_MAX_SIZE;
            int maxWidth = getMaxWidth();

            if(textWidth>maxWidth){//最大宽度无法放置下
                float size = (float)maxWidth/textCount;
                if(size<TEXT_MIN_SIZE){//缩放后的字体大小是否超过最小，如果小于最小则双行显示
                    size = TEXT_MIN_SIZE;
                    setSingleLine(false);
                    setMaxLines(2);
                }else{
                    setSingleLine();
                }
                setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            }else{
                setSingleLine();
                setTextSize(TypedValue.COMPLEX_UNIT_PX, TEXT_MAX_SIZE);
            }
        }else{
            setSingleLine();
        }

    }
}
