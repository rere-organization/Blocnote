package rere_corporation.blocnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;

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

/**

    // Creating the list of all the notes
    public static ArrayList<Blocnote> getBlocnoteList (Context context){

        ArrayList<Blocnote> listBlocnote = new ArrayList<>();

        LocalSQLiteOpenHelper helper = new LocalSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(true, "Note", new String[]{"id", "title", "text", "favorite", "date"}, null,null,null,null,"date",null);

        while (cursor.moveToNext()){
            listBlocnote.add(new Blocnote(cursor));
        }

        cursor.close();
        db.close();

        return listBlocnote;

    }



    private Blocnote(Cursor cursor){
        id = cursor.getInt(cursor.getColumnIndex("id"));
        title = cursor.getString(cursor.getColumnIndex("title"));
        note = cursor.getString(cursor.getColumnIndex("text"));
        favorite = cursor.getInt(cursor.getColumnIndex("favorite")) > 0;
    }


    public static Blocnote getBlocnote (Context context, long id){
        // Storing informations
        Blocnote blocnote = null;

        LocalSQLiteOpenHelper helper = new LocalSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        // Request to the database
        String where = "id = " + String.valueOf(id);
        Cursor cursor = db.query(true, "NOTE", new String[]{"id", "title", "text", "favorite"}, where,null,null,null,"title",null);

        // Create a new entry for each block
        if (cursor.moveToFirst())
            blocnote = new Blocnote(cursor);

        cursor.close();
        db.close();

        return blocnote;
    }

*/


    public static void insert(Context context, EditText note) {
        /**********************************************************************************
         * Function      : insert
         * Prerequisites : Have : String "note" / Context
         * Action        : Select all arguments for the request
         *                 And execute the request
         * Strategy      : Arguments : id / note / favorite / date
         *                 Put to the table 'NOTE'
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

    public static void select(Context context, long id){
        String Request;

        LocalSQLiteOpenHelper helper = new LocalSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        Request = "SELECT * FROM NOTE WHERE id = " + id + ";";

        db.execSQL(Request);
        db.close();

    }

    private Blocnote(Cursor cursor){
        id = cursor.getInt(cursor.getColumnIndex("id"));
        favorite = cursor.getInt(cursor.getColumnIndex("favorite")) > 0;
        note = cursor.getString(cursor.getColumnIndex("note"));
        date = cursor.getString(cursor.getColumnIndex("date"));
    }


    public static ArrayList<Blocnote> getBlocnoteList(Context context){
        ArrayList<Blocnote> listBlocnote = new ArrayList<>();

        LocalSQLiteOpenHelper helper = new LocalSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();


        Cursor cursor = db.query(true, "NOTE", new String[]{"id", "favorite", "note", "date"}, null, null, null, null, "date", null );

        while (cursor.moveToNext()) {
            listBlocnote.add(new Blocnote(cursor));
        }

        cursor.close();
        db.close();

        return listBlocnote;
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
}
