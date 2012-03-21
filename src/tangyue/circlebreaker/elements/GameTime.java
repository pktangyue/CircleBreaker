package tangyue.circlebreaker.elements;

public class GameTime {
	private static float TIME_UNIT = 100f;
	private static float timeInterval = TIME_UNIT;

	public static float getTimeInterval() {
		return timeInterval;
	}

	public static void slowTime() {
		timeInterval *= 10;
	}

	public static void resetInterval() {
		timeInterval = TIME_UNIT;
	}
}
