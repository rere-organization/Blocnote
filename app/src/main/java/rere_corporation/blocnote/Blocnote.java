package rere_corporation.blocnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rere on 15/10/16.
 */

public class Blocnote {
    // Composition of the database Blocnote
    long id;
    String note;
    Boolean favorite;
    String date;

    public static void insert(Context context, EditText note) {
        /**********************************************************************************
         * Insert into the database the note
         *
         * @param context
         * @param note
         *
         *********************************************************************************/

        ContentValues values = new ContentValues();

        // Generate the date
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = date_format.format(new Date());

        // Call the database
        LocalSQLiteOpenHelper helper = new LocalSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        // Put to 'values' all the data
        values.put("favorite", Boolean.FALSE);
        values.put("date", strDate);
        values.put("note", String.valueOf(note.getText()));

        // Insert to the database
        db.insert("NOTE", null, values);
        db.close();

    }

    private Blocnote(Cursor cursor){
        id = cursor.getInt(cursor.getColumnIndex("id"));
        favorite = cursor.getInt(cursor.getColumnIndex("favorite")) > 0;
        note = cursor.getString(cursor.getColumnIndex("note"));
        date = cursor.getString(cursor.getColumnIndex("date"));
    }


    public static ArrayList<Blocnote> getBlocnoteList(Context context){
        /**********************************************************************************
         * Select all notes present in the database and save add to an array
         *
         * @param context
         *
         * @return ArrayList<Blocnote>
         *
         *********************************************************************************/

        ArrayList<Blocnote> listBlocnote = new ArrayList<>();

        // Call the database
        LocalSQLiteOpenHelper helper = new LocalSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        // Create the request
        Cursor cursor = db.query(true, "NOTE", new String[]{"id", "favorite", "note", "date"}, null, null, null, null, "date DESC", null );

        // Add to the array all notes
        while (cursor.moveToNext()) {
            listBlocnote.add(new Blocnote(cursor));
        }

        cursor.close();
        db.close();

        return listBlocnote;
    }


    public static void delete(Context context, long id){
        /**********************************************************************************
         * Delete the note in the database
         *
         * @param context
         * @param id
         *
         *********************************************************************************/

        String whereClause = "id= ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = String.valueOf(id);

        LocalSQLiteOpenHelper helper = new LocalSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete("NOTE", whereClause, whereArgs);
        db.close();

    }


    public static Blocnote getBlocnote (Context context, long id){
        /**********************************************************************************
         * Get the note, whose the id is the same
         *
         * @param context
         * @param id
         *
         *********************************************************************************/

        Blocnote blocnote = null;

        LocalSQLiteOpenHelper helper = new LocalSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        // Request to the database
        String where = "id = " + String.valueOf(id);
        Cursor cursor = db.query(true, "NOTE", new String[]{"id", "favorite", "note", "date"}, where,null,null,null,null,null);

        // Create a new entry for each block
        if (cursor.moveToFirst())
            blocnote = new Blocnote(cursor);

        cursor.close();
        db.close();

        return blocnote;
    }


    public static void update(Context context, long id, EditText note){
        /**********************************************************************************
         * Update the note
         *
         * @param context
         * @param id
         * @param note
         *
         *********************************************************************************/

        ContentValues values = new ContentValues();


        // Generate the date
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = date_format.format(new Date());


        // Add the value
        values.put("note", String.valueOf(note.getText()));
        values.put("date", strDate);

        // With the same id
        String whereClause = "id=" + String.valueOf(id);


        LocalSQLiteOpenHelper helper = new LocalSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        // Request
        db.update("NOTE", values, whereClause, null);
        db.close();

    }


    public long getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public String getDate(){ return date; }

    public void setId(long id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
