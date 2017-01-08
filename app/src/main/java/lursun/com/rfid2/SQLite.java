package lursun.com.rfid2;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by admin on 2016/9/5.
 */
public class SQLite extends SQLiteOpenHelper {
    public static String website =null;
    private final static int _DBVersion = 1;
    private final static String _DBName = "CardTable.db";
    public SQLite(Context context) {
        super(context, _DBName, null, _DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL = "CREATE TABLE IF NOT EXISTS CardTable( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Card TEXT,"+ //發票抬頭
                "Number Integer"+ //網址
                ");";
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }


}
