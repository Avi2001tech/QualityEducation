package QualityEducation.kidsVisualEducation.kidsQualityEducation;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class DrawingActivity extends Activity implements OnClickListener, OnTouchListener {
    DrawingView dv;
    private Paint mPaint;
    private String type = "";
    private Integer position = 0;
    private int totalItem = 0;
    ImageView nextBtn = null;
    ImageView playBtn = null;
    ImageView prevBtn = null;
    View drawingView = null;
    ViewGroup parent = null;
    RelativeLayout.LayoutParams params = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.taw_drawing);


        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");

        nextBtn = (ImageView) findViewById(R.id.nextId);
        playBtn = (ImageView) findViewById(R.id.playId);
        prevBtn = (ImageView) findViewById(R.id.prevId);
        nextBtn.setOnClickListener(this);
        nextBtn.setOnTouchListener(this);
        prevBtn.setOnClickListener(this);
        prevBtn.setOnTouchListener(this);
        playBtn.setOnClickListener(this);
        playBtn.setOnTouchListener(this);


        drawingView = findViewById(R.id.drawingViewId);

        params = (RelativeLayout.LayoutParams) drawingView.getLayoutParams();
        dv = new DrawingView(this);
        dv.setLayoutParams(params);

        parent = (ViewGroup) drawingView.getParent();
        int index = parent.indexOfChild(drawingView);
        parent.removeView(drawingView);
        parent.addView(dv, index);

        if (type.equals(ResourcePool.drawingAlphabet)) {
//dosomething
        } else {
            //dosomething
        }

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(16);

        updatePreviousButton();
    }

    public class DrawingView extends View {

        private Bitmap mBitmap;
        private Bitmap bm;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;

        public DrawingView(Context c) {
            super(c);
            context = c;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.BLUE);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);

        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

            mCanvas = new Canvas(mBitmap);

            mCanvas.drawBitmap(bm,
                    new Rect(0, 0, bm.getWidth(), bm.getHeight()), new Rect(0,
                            0, mCanvas.getWidth(), mCanvas.getHeight()),
                    mBitmapPaint);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

            canvas.drawPath(mPath, mPaint);

            canvas.drawPath(circlePath, circlePaint);
        }

        private float mX;
        private float mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touchStart(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touchMove(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;

                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
            }
        }

        private void touchUp() {
            mPath.lineTo(mX, mY);
            circlePath.reset();
            // commit the path to our offscreen
            mCanvas.drawPath(mPath, mPaint);
            // kill this so we don't double draw
            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchStart(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touchMove(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touchUp();
                    invalidate();
                    break;
            }
            return true;
        }

        public void resetCanvas() {
            bm = null;
            mBitmap = null;
            System.gc();
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (v.getId() == R.id.nextId || v.getId() == R.id.playId || v.getId() == R.id.prevId)
                    v.setAlpha(0.5f);
                break;
            case MotionEvent.ACTION_UP:
                if (v.getId() == R.id.nextId || v.getId() == R.id.playId || v.getId() == R.id.prevId)
                    v.setAlpha(1.0f);
                break;

            default:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextId:
                position++;
                changeStroke();
                break;
            case R.id.prevId:
                position--;
                changeStroke();
                break;
            case R.id.playId:
                changeStroke();
                break;
            default:
                break;
        }
    }

    private void changeStroke() {
        updateNextButton();
        updatePreviousButton();
        int index = parent.indexOfChild(dv);
        dv.resetCanvas();
        dv = null;
        parent.removeViewAt(index);
        dv = new DrawingView(this);
        dv.setLayoutParams(params);
        parent.addView(dv, index);
    }

    private void updateNextButton() {
        if (position == totalItem - 1) {
            nextBtn.setAlpha(0.5f);
            nextBtn.setClickable(false);
        } else {
            nextBtn.setAlpha(1f);
            nextBtn.setClickable(true);
        }
    }

    private void updatePreviousButton() {
        if (position == 0) {
            prevBtn.setAlpha(0.5f);
            prevBtn.setClickable(false);
        } else {
            prevBtn.setAlpha(1f);
            prevBtn.setClickable(true);
        }
    }

}
