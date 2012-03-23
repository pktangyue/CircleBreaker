package tangyue.circlebreaker.levels;

import android.graphics.Color;

public class Level02 extends GameLevel {
	@Override
	public void initCirclesData() {
		list.add(new GameLevel.CircleData(448, 240, 30, Color.RED));
		list.add(new GameLevel.CircleData(380, 190, 30, Color.RED));
		list.add(new GameLevel.CircleData(310, 160, 30, Color.RED));
		list.add(new GameLevel.CircleData(240, 150, 30, Color.RED));
		list.add(new GameLevel.CircleData(170, 160, 30, Color.RED));
		list.add(new GameLevel.CircleData(100, 190, 30, Color.RED));
		list.add(new GameLevel.CircleData(32, 240, 30, Color.RED));

		list.add(new GameLevel.CircleData(448, 440, 30, Color.GREEN));
		list.add(new GameLevel.CircleData(380, 390, 30, Color.GREEN));
		list.add(new GameLevel.CircleData(310, 360, 30, Color.GREEN));
		list.add(new GameLevel.CircleData(240, 350, 30, Color.GREEN));
		list.add(new GameLevel.CircleData(170, 360, 30, Color.GREEN));
		list.add(new GameLevel.CircleData(100, 390, 30, Color.GREEN));
		list.add(new GameLevel.CircleData(32, 440, 30, Color.GREEN));
	}
}