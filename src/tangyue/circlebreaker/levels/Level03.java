package tangyue.circlebreaker.levels;

import android.graphics.Color;

public class Level03 extends GameLevel {
	@Override
	public void initCirclesData() {
		list.add(new GameLevel.CircleData(100, 150, 30, Color.RED));
		list.add(new GameLevel.CircleData(100, 250, 30, Color.RED));
		list.add(new GameLevel.CircleData(200, 150, 30, Color.RED));
		list.add(new GameLevel.CircleData(200, 250, 30, Color.RED));

		list.add(new GameLevel.CircleData(300, 150, 30, Color.GREEN));
		list.add(new GameLevel.CircleData(300, 250, 30, Color.GREEN));
		list.add(new GameLevel.CircleData(400, 150, 30, Color.GREEN));
		list.add(new GameLevel.CircleData(400, 250, 30, Color.GREEN));

		list.add(new GameLevel.CircleData(100, 350, 30, Color.BLUE));
		list.add(new GameLevel.CircleData(100, 450, 30, Color.BLUE));
		list.add(new GameLevel.CircleData(200, 350, 30, Color.BLUE));
		list.add(new GameLevel.CircleData(200, 450, 30, Color.BLUE));

		list.add(new GameLevel.CircleData(300, 350, 30, Color.YELLOW));
		list.add(new GameLevel.CircleData(300, 450, 30, Color.YELLOW));
		list.add(new GameLevel.CircleData(400, 350, 30, Color.YELLOW));
		list.add(new GameLevel.CircleData(400, 450, 30, Color.YELLOW));
	}
}