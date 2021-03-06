package tangyue.circlebreaker.elements;

import tangyue.circlebreaker.interfaces.Drawable;
import tangyue.circlebreaker.threads.BaseThread;
import tangyue.circlebreaker.view.BreakerView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameScore implements Drawable {
	private static int currentIndex = 0;
	private static int currentColor;
	private static int totalScore = 0;
	private static int SCORE_UNIT = 50;
	private Paint paint;
	private BreakerView view;

	public GameScore(BreakerView view) {
		this.view = view;
		paint = new Paint();
		paint.setColor(Color.CYAN);
		paint.setAntiAlias(true);
		paint.setTextSize(30);
	}

	public static int getScore(int color) {
		int point;
		if (color == currentColor) {
			point = ++currentIndex * SCORE_UNIT;
			totalScore += point;
			return point;
		}
		currentColor = color;
		currentIndex = 1;
		totalScore += SCORE_UNIT;
		return SCORE_UNIT;
	}

	public static void resetIndex() {
		currentIndex = 0;
	}

	public static void resetAll() {
		currentIndex = 0;
		currentColor = 0;
		totalScore = 0;
	}

	public static int getTotalScore() {
		return totalScore;
	}

	@Override
	public void init(Canvas canvas) {
		drawSelf(canvas);
	}

	@Override
	public void drawSelf(Canvas canvas) {
		paint.setTextAlign(Paint.Align.LEFT);
		canvas.drawText("Score:" + totalScore, 0, view.getHeight(), paint);
		paint.setTextAlign(Paint.Align.RIGHT);
		canvas.drawText("Level:" + view.getLevel(), view.getWidth(),
				view.getHeight(), paint);
	}

	@Override
	public BaseThread getThread() {
		return null;
	}

	@Override
	public void disableThread() {
	}
}
