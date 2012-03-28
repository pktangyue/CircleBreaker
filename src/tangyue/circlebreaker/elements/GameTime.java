package tangyue.circlebreaker.elements;

public class GameTime {
	private final static float TIME_UNIT = 100f;
	private final static float DURATION_UNIT = 0.5f;
	private static float timeInterval = TIME_UNIT;
	private static float duration = 0.5f;

	public static float getTimeInterval() {
		return timeInterval;
	}

	public static void slowTime() {
		timeInterval *= 10;
		duration /= 2;
	}

	public static void resetInterval() {
		timeInterval = TIME_UNIT;
		duration = DURATION_UNIT;
	}

	public static float getDuration() {
		return duration;
	}
}
