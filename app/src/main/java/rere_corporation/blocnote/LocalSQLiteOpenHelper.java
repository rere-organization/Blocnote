package rere_corporation.blocnote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalSQLiteOpenHelper extends SQLiteOpenHelper {

    /***********************************************************************************************
     * Composition of the datebase
     * Table NOTE is composed :
     *      - id       : long    : id note
     *      - note     : string  : content of a note
     *      - favorite : boolean : have in favorite
     *      - date     : string  : date of the last modification
     *      - color    : int     : background color
     **********************************************************************************************/

    static String DB_NAME = "Blocnote.db";
    static int DB_VERSION = 2;


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Request - the option autoincrementation for the primary key is not define for performing reason
        String sqlFilTable = "CREATE TABLE NOTE (id INTEGER PRIMARY KEY, favorite BOOLEAN, note TEXT, " +
                "date DATE, color INT);";

        db.execSQL(sqlFilTable);
    }

    public LocalSQLiteOpenHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        for(int i = oldVersion; i < newVersion; i++){

            int versionToUpgrade = i + 1;

            // Update version 2
            if(versionToUpgrade == 2){
                db.execSQL("DROP TABLE IF EXISTS NOTE");
                onCreate(db);
            }

        }
    }

}
