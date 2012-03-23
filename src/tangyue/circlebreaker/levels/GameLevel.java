package tangyue.circlebreaker.levels;

import java.util.ArrayList;
import tangyue.circlebreaker.elements.Circle;
import tangyue.circlebreaker.view.BreakerView;

public abstract class GameLevel {
	public ArrayList<GameLevel.CircleData> list = new ArrayList<GameLevel.CircleData>();
	public GameLevel(){
		initCirclesData();
	}
	public abstract void initCirclesData();

	public ArrayList<Circle> getCirclesList(BreakerView view) {
		ArrayList<Circle> circles = new ArrayList<Circle>();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			GameLevel.CircleData data = list.get(i);
			circles.add(new Circle(view, data.x, data.y, data.radius,
					data.color));
		}
		return circles;
	}

	public static int MAX_LEVEL = 99;

	protected class CircleData {
		protected float x;
		protected float y;
		protected float radius;
		protected int color;

		public CircleData(float x, float y, float radius, int color) {
			this.x = x;
			this.y = y;
			this.radius = radius;
			this.color = color;
		}
	}
}
