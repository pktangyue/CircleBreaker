package tangyue.circlebreaker.levels;

public class LevelFactory {
	public static GameLevel creator(int level) {
		GameLevel gamelevel = null;
		try {
			gamelevel = (GameLevel) Class.forName(
					"tangyue.circlebreaker.levels.Level"
							+ String.format("%02d", level)).newInstance();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return gamelevel;
	}
}