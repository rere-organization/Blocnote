package rere_corporation.blocnote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rere on 14/10/16.
 *
 * *******  DataBase  *******
 * id / title / text / Favorite / Date
 *
 */

public class LocalSQLiteOpenHelper extends SQLiteOpenHelper {

    static String DB_NAME = "Blocnote.db";
    static int DB_VERSION = 1 ;

    public LocalSQLiteOpenHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Create Database //
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Request
        String sqlFilTable = "CREATE TABLE NOTE (id INTEGER PRIMARY KEY," +
                "title TEXT, text TEXT, favorite BOOLEAN, date NUMERIC);";
        // Execution
        db.execSQL(sqlFilTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(int i = oldVersion; i < newVersion; i++){

            int versionToUpgrade = i + 1;

            if(versionToUpgrade == 2){
                // Then Update to version 2
            }else{
                // Update to other version
            }

        }
    }


}
