package tangyue.circlebreaker.adapter;

public class MenuItem {
	private int level;
	private int score;
	private boolean isLocked;

	public MenuItem(int level, int score, boolean isLocked) {
		this.level = level;
		this.score = score;
		this.isLocked = isLocked;
	}

	public String getTitle() {
		return String.format("%02d", level);
	}

	public String getScore() {
		return String.valueOf(score);
	}

	public boolean isLocked() {
		return isLocked;
	}
}
