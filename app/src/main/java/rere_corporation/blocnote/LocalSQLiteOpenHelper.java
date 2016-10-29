package rere_corporation.blocnote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rere on 14/10/16.
 *
 * *******  DataBase  *******
 * id / text / Favorite / Date
 *
 */

public class LocalSQLiteOpenHelper extends SQLiteOpenHelper {

    static String DB_NAME = "Blocnote.db";
    static int DB_VERSION = 2 ;

    // Create Database //
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Request - the option autoincrementation for the primary key is not define for performing reason
        String sqlFilTable = "CREATE TABLE NOTE (id INTEGER PRIMARY KEY, favorite BOOLEAN, note TEXT, date DATE);";

        // Execution
        db.execSQL(sqlFilTable);

    }

    public LocalSQLiteOpenHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        for(int i = oldVersion; i < newVersion; i++){

            int versionToUpgrade = i + 1;

            if(versionToUpgrade == 2){
                // Then Update to version 2
                db.execSQL("DROP TABLE IF EXISTS NOTE");
                onCreate(db);
            }else{
                // Update to other version
            }

        }
    }


}
