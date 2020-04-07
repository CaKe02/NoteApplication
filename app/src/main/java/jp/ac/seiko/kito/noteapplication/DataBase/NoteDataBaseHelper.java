package jp.ac.seiko.kito.noteapplication.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class NoteDataBaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_BODY = "body";

    public static final String[] COLUMNS = {
            COLUMN_TITLE,
            COLUMN_BODY
    };


    private static final String DATABASE_FILE_NAME = "NoteContent.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_PROJECT =
            "CREATE TABLE `" + TABLE_NAME + "`( "
                    + "`" + COLUMN_ID + "` INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "`" + COLUMN_TITLE + "` TEXT NOT NULL,"
                    + "`" + COLUMN_BODY + "` TEXT"
                    + ");";

    public NoteDataBaseHelper (Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PROJECT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }



    //登録する
    public boolean addProject(final String title, final String body, final String remaining) {
        try{
            SQLiteDatabase db = getReadableDatabase();
            final ContentValues values = new ContentValues(2);
            values.put(COLUMN_TITLE,title);
            values.put(COLUMN_BODY,body);
            long rowID = db.insert(TABLE_NAME,null,values);
            if (rowID >= 0) {
                // 登録に成功。
                return true;
            } else {
                // 登録に失敗。UNIQUEの条件に引っかかったことが原因の場合が多い。
                return false;
            }
        } catch (Throwable allErrors) {
            // なんかしらのエラー
            return false;
        }
    }

    //IDから取得する

    public String[] getNoteByID (String id) {
        String selection = COLUMN_ID + " =?";
        String[] selectionArgs = {
                id
        };
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = null;
        try {
            c = db.query(
                    TABLE_NAME,
                    COLUMNS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            if (c.moveToNext()) {
                String[] ret = {
                        c.getString(0),
                        c.getString(1)
                };
                return ret;
            }
        } catch (Exception e) {

        } finally {
            if (c != null) try { c.close(); } catch (Exception ex) {/*do nothing*/}
        }
        return null;
    }
}
