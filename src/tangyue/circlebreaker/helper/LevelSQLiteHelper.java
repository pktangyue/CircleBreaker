package tangyue.circlebreaker.helper;

import java.util.HashMap;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class LevelSQLiteHelper extends SQLiteOpenHelper {
	public final static String DB_NAME = "level.db";
	public final static String TABLE_NAME = "level_info";
	public final static String ID_KEY = "id";
	public final static String SCORE_KEY = "score";
	public final static String LEVEL_KEY = "level";
	public final static int VERSION = 1;

	private SQLiteDatabase db = null;

	public LevelSQLiteHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuffer sql = new StringBuffer("create table if not exists "
				+ TABLE_NAME);
		sql.append("(");
		sql.append(ID_KEY + " integer primary key autoincrement,");
		sql.append(LEVEL_KEY + " integer not null unique,");
		sql.append(SCORE_KEY + " integer default 0");
		sql.append(")");
		db.execSQL(sql.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public HashMap<Integer, Integer> getLevelHash() {
		db = this.getWritableDatabase();
		String[] columns = new String[] { LEVEL_KEY, SCORE_KEY };
		Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null,
				LEVEL_KEY);
		int scoreIndex = cursor.getColumnIndex(SCORE_KEY);
		int levelIndex = cursor.getColumnIndex(LEVEL_KEY);
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			result.put(cursor.getInt(levelIndex), cursor.getInt(scoreIndex));
		}
		cursor.close();
		db.close();
		return result;
	}

	public int getBestScore(int level) {
		int result = 0;
		db = this.getWritableDatabase();
		String[] columns = new String[] { SCORE_KEY };
		String where = LEVEL_KEY + "=" + level;
		Cursor cursor = db.query(TABLE_NAME, columns, where, null, null, null,
				null);
		int levelIndex = cursor.getColumnIndex(SCORE_KEY);
		if (cursor.moveToFirst()) {
			result = cursor.getInt(levelIndex);
		}
		cursor.close();
		db.close();
		return result;
	}

	public void replaceBestScore(int level, int score) {
		db = this.getWritableDatabase();
		StringBuffer sql = new StringBuffer("replace into " + TABLE_NAME);
		sql.append(" (" + SCORE_KEY + "," + LEVEL_KEY + ")");
		sql.append(" values");
		sql.append(" (" + score + "," + level + ")");
		db.execSQL(sql.toString());
		db.close();
	}

	public int getNowMaxLevel() {
		int result = 0;
		db = this.getWritableDatabase();
		String maxLevelKey = "max_" + LEVEL_KEY;
		String[] columns = new String[] { "max(" + LEVEL_KEY + ") as "
				+ maxLevelKey };
		Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null,
				null);
		int index = cursor.getColumnIndex(maxLevelKey);
		if (cursor.moveToFirst()) {
			result = cursor.getInt(index);
		}
		cursor.close();
		db.close();
		return result;
	}
}
