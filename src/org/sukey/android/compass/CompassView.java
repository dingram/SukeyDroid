package org.sukey.android.compass;

import java.util.ArrayList;
import java.util.List;

import org.sukey.android.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CompassView extends View {
	protected final float scale = getContext().getResources()
			.getDisplayMetrics().density;
	private final static float DEFAULT_RING_WIDTH_DP = 25.0f;

	protected Paint mPaintBackground;
	protected Paint mPaintRing;
	protected Paint mPaintRed;
	protected Paint mPaintAmber;
	protected Paint mPaintGreen;
	protected Paint mPaintUnknown;
	protected int mRingWidth = (int) (DEFAULT_RING_WIDTH_DP * scale + 0.5f);

	protected int mBackgroundColor = 0xfff0f0f0;
	protected int mRingColor = 0xff303030;
	protected int mRedColor = 0xffff0033;
	protected int mAmberColor = 0xffffff33;
	protected int mGreenColor = 0xff33cc00;
	protected int mUnknownColor = 0xff000000;

	protected float mAngleOffset = 0;

	protected RectF oval = new RectF();

	protected List<Segment> mSegments = new ArrayList<Segment>();

	public CompassView(Context context) {
		super(context);
		initView();
	}

	public CompassView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.CompassView);

		mBackgroundColor = a.getColor(R.styleable.CompassView_backgroundColor,
				mBackgroundColor);
		mRingColor = a.getColor(R.styleable.CompassView_ringColor, mRingColor);
		mRedColor = a.getColor(R.styleable.CompassView_redColor, mRedColor);
		mAmberColor = a.getColor(R.styleable.CompassView_amberColor,
				mAmberColor);
		mGreenColor = a.getColor(R.styleable.CompassView_greenColor,
				mGreenColor);
		mUnknownColor = a.getColor(R.styleable.CompassView_unknownColor,
				mUnknownColor);

		mRingWidth = a.getDimensionPixelSize(R.styleable.CompassView_ringWidth,
				mRingWidth);

		initView();

		a.recycle();
	}

	public CompassView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	protected Paint initPaint(int color) {
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG
				| Paint.FILTER_BITMAP_FLAG);
		p.setColor(color);
		return p;
	}

	protected void initView() {
		mSegments.add(new Segment(129, 10, Segment.TYPE_BLOCKED));
		mSegments.add(new Segment(308, 10, Segment.TYPE_OPEN));
		mSegments.add(new Segment(35, 10, Segment.TYPE_OPEN)); //
		mSegments.add(new Segment(3, 10, Segment.TYPE_OBSTRUCTED));
		mSegments.add(new Segment(242, 10, Segment.TYPE_OPEN));
		mSegments.add(new Segment(152, 10, Segment.TYPE_OPEN));

		mPaintBackground = initPaint(mBackgroundColor);
		mPaintRing = initPaint(mRingColor);
		mPaintRed = initPaint(mRedColor);
		mPaintAmber = initPaint(mAmberColor);
		mPaintGreen = initPaint(mGreenColor);
		mPaintUnknown = initPaint(mUnknownColor);
	}

	public float getAngleOffset() {
		return mAngleOffset;
	}

	public void setAngleOffset(float offset) {
		mAngleOffset = (offset + 360) % 360;
		invalidate();
	}

	public int getBackgroundColor() {
		return mBackgroundColor;
	}

	public void setBackgroundColor(int color) {
		mBackgroundColor = color;
		mPaintBackground = initPaint(mBackgroundColor);
		invalidate();
	}

	public int getRingColor() {
		return mRingColor;
	}

	public void setRingColor(int color) {
		mRingColor = color;
		mPaintRing = initPaint(mRingColor);
		invalidate();
	}

	public int getRedColor() {
		return mRedColor;
	}

	public void setRedColor(int color) {
		mRedColor = color;
		mPaintRed = initPaint(mRedColor);
		invalidate();
	}

	public int getAmberColor() {
		return mAmberColor;
	}

	public void setAmberColor(int color) {
		mAmberColor = color;
		mPaintAmber = initPaint(mAmberColor);
		invalidate();
	}

	public int getGreenColor() {
		return mGreenColor;
	}

	public void setGreenColor(int color) {
		mGreenColor = color;
		mPaintGreen = initPaint(mGreenColor);
		invalidate();
	}

	public int getUnknownColor() {
		return mUnknownColor;
	}

	public void setUnknownColor(int color) {
		mUnknownColor = color;
		mPaintUnknown = initPaint(mUnknownColor);
		invalidate();
	}

	public int getRingWidth() {
		return mRingWidth;
	}

	public void setRingWidth(int width) {
		mRingWidth = width;
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measuredWidth = measureWidth(widthMeasureSpec);
		int measuredHeight = measureHeight(heightMeasureSpec);
		setMeasuredDimension(measuredWidth, measuredHeight);

		final float size = Math.min((float) measuredWidth,
				(float) measuredHeight);
		final float offX;
		final float offY;

		if (measuredWidth > measuredHeight) {
			offX = (((float) measuredWidth - size) / 2.0f)
					+ ((float) mRingWidth / 2.0f);
			offY = 0.0f;
		} else {
			offX = 0.0f;
			offY = (((float) measuredHeight - size) / 2.0f)
					+ ((float) mRingWidth / 2.0f);
		}
		oval = new RectF(offX, offY, size + offX, size + offY);
	}

	/**
	 * Determines the width of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
	 * @return The width of the view, honoring constraints from measureSpec
	 */
	private int measureWidth(int measureSpec) {
		return MeasureSpec.getSize(measureSpec);
	}

	/**
	 * Determines the height of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
	 * @return The height of the view, honoring constraints from measureSpec
	 */
	private int measureHeight(int measureSpec) {
		return MeasureSpec.getSize(measureSpec);
	}

	protected Paint getSegPaint(int type) {
		switch (type) {
		case Segment.TYPE_OPEN:
			return mPaintGreen;
		case Segment.TYPE_OBSTRUCTED:
			return mPaintAmber;
		case Segment.TYPE_BLOCKED:
			return mPaintRed;
		}
		return mPaintUnknown;
	}

	/**
	 * Render the compass
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		final float pt = getPaddingTop();
		final float pl = getPaddingLeft();
		final float pb = getPaddingBottom();
		final float pr = getPaddingRight();

		final float w = getMeasuredWidth() - pl - pr;
		final float h = getMeasuredHeight() - pt - pb;

		final float cx = pl + (w / 2.0f);
		final float cy = pt + (h / 2.0f);
		final float minDim = Math.min(w, h);
		final float outerRadius = minDim / 2.0f;
		final float offX;
		final float offY;

		if (w > h) {
			offX = (w - h) / 2.0f;
			offY = 0.0f;
		} else {
			offX = 0.0f;
			offY = (h - w) / 2.0f;
		}
		oval.set(pl + offX, pt + offY, pl + offX + minDim, pt + offY + minDim);

		// clear canvas
		canvas.drawColor(mBackgroundColor);
		// draw ring
		// canvas.drawCircle(cx, cy, outerRadius, mPaintRing);
		canvas.drawArc(oval, 0, 360, true, mPaintRing);

		// draw segments
		for (Segment seg : mSegments) {
			canvas.drawArc(oval, seg.getStartAngle(mAngleOffset), seg.getWidth(),
					true, getSegPaint(seg.getType()));
		}

		// draw inner
		canvas.drawCircle(cx, cy, outerRadius - mRingWidth, mPaintBackground);
	}

	public static class Segment {
		protected float mAngle;
		protected float mWidth;
		protected int mType;

		public static final int TYPE_OPEN = 1;
		public static final int TYPE_OBSTRUCTED = 2;
		public static final int TYPE_BLOCKED = 3;
		public static final int TYPE_UNKNOWN = 0;

		public Segment(float angle, float width, int type) {
			mAngle = angle;
			mWidth = width;
			mType = type;
		}

		public float getStartAngle() {
			return getStartAngle(0);
		}

		public float getStartAngle(float offset) {
			return (((mAngle - (mWidth / 2)) + 360 + offset) % 360);
		}

		public float getAngle() {
			return getAngle(0);
		}

		public float getAngle(float offset) {
			return (mAngle + 360 + offset) % 360;
		}

		public float getWidth() {
			return mWidth;
		}

		public int getType() {
			return mType;
		}
	}
}
