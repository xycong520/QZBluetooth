package com.qz.qizhi.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.qz.qizhi.R;

/**
 * Created by Administrator on 2016/4/15.
 */
public class GrainSpaceView extends View {

    public GrainSpaceView(Context context) {
        super(context);
        init();
    }

    public GrainSpaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GrainSpaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public GrainSpaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    Paint mPaint;
    float leftX, rightX, yTop, yBottom, yMid;
    float viewWidth, viewHeight;
    float bottomWidth = 150;
    RectF mBounds;
    int grades = 5;//5等分
    int paddingRL = 50;
    int paddingTop = 80;
    int textSize = 45;
    String gradesText[] = {
            "⑤", "④", "③", "②", "①"
    };

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xFF93d3d8);
        mPaint.setStrokeWidth(3);
        leftX = getPaddingLeft() + paddingRL;
        rightX = viewWidth - getPaddingRight() - paddingRL;
        yTop = getPaddingTop() + paddingTop + textSize;
        yBottom = viewHeight - getPaddingBottom() - 80;
        yMid = viewHeight - getPaddingBottom();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        mBounds = new RectF(getLeft(), getTop(), wm.getDefaultDisplay().getWidth(), wm.getDefaultDisplay().getHeight() / 2);
        viewWidth = mBounds.right - mBounds.left;
        viewHeight = mBounds.bottom - mBounds.top;
        setMeasuredDimension((int) viewWidth, (int)viewHeight);
        init();
    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xffffffff);
        canvas.drawLine(leftX, yTop, leftX, yBottom, mPaint);
        Path path = new Path();
        path.moveTo(leftX, yTop);
        path.lineTo(leftX, yBottom);
        path.lineTo((rightX + leftX) / 2 - bottomWidth, yMid);
        path.lineTo((rightX + leftX) / 2 + bottomWidth, yMid);
        path.lineTo(rightX, yBottom);
        path.lineTo(rightX, yTop);
        canvas.drawPath(path, mPaint);
        canvas.drawLine((rightX + leftX) / 2, yTop, (rightX + leftX) / 2, yMid, mPaint);

        float oneGradesHeight = (yBottom - yTop) / grades;
        float oneGradesWidth = 50;
        Paint mPainText = new Paint();
        mPainText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPainText.setStyle(Paint.Style.STROKE);
        mPainText.setColor(0xFF000000);
        mPainText.setTextSize(textSize);
        for (int i = 1; i <= grades; i++) {
            canvas.drawText(gradesText[i - 1], leftX + 5, yTop + oneGradesHeight * i - 5, mPainText);
            canvas.drawText(gradesText[i - 1], rightX - textSize - 5, yTop + oneGradesHeight * i - 5, mPainText);
            canvas.drawLine(leftX, yTop + oneGradesHeight * i, leftX + oneGradesWidth, yTop + oneGradesHeight * i, mPaint);
            canvas.drawLine(rightX, yTop + oneGradesHeight * i, rightX - oneGradesWidth, yTop + oneGradesHeight * i, mPaint);
        }
        Bitmap shipBm = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_ship);
        if (leftX + 5 + textSize + 40 + shipBm.getWidth() > (viewWidth / 2 - 10)) {
            Matrix matrix = new Matrix();
            matrix.postScale(0.8f, 0.8f);
            shipBm = Bitmap.createBitmap(shipBm, 0, 0, shipBm.getWidth(), shipBm.getHeight(), matrix, true);
        }
        canvas.drawBitmap(shipBm, leftX + textSize + 40, yTop + value * oneGradesHeight - shipBm.getHeight(), new Paint());
        canvas.drawBitmap(shipBm, rightX - textSize - 40 - shipBm.getWidth(), yTop + value2 * oneGradesHeight - shipBm.getHeight(), new Paint());

        canvas.drawText("①舱", viewWidth / 2 - 150, paddingTop, mPainText);
        canvas.drawText("②舱", viewWidth / 2 + 50,  paddingTop, mPainText);
    }

    int value = 1, value2 = 2;

    public void setValue(int v) {
        value = v;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }
}
