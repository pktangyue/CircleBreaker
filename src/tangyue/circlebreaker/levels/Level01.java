package tangyue.circlebreaker.levels;

import android.graphics.Color;

public class Level01 extends GameLevel {
	@Override
	public void initCirclesData() {
		list.add(new GameLevel.CircleData(40, 280, 30, Color.RED));
		list.add(new GameLevel.CircleData(140, 280, 30, Color.RED));
		list.add(new GameLevel.CircleData(240, 280, 30, Color.RED));
		list.add(new GameLevel.CircleData(340, 280, 30, Color.RED));
		list.add(new GameLevel.CircleData(440, 280, 30, Color.RED));
		list.add(new GameLevel.CircleData(240, 80, 30, Color.RED));
		list.add(new GameLevel.CircleData(240, 180, 30, Color.RED));
		list.add(new GameLevel.CircleData(240, 380, 30, Color.RED));
		list.add(new GameLevel.CircleData(240, 480, 30, Color.RED));
	}
}