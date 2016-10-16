package rere_corporation.blocnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by rere on 15/10/16.
 */

public class Blocnote {
    // Composition of the database Blocnote
    long id;
    String title;
    String note;
    Boolean favorite;




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



    Blocnote(Cursor cursor){
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


    public void insert(Context context){
        ContentValues values = new ContentValues();

        values.put("text", this.note);
        values.put("favorite", false);

        /**
         * id INTEGER PRIMARY KEY," +
         "title TEXT, text TEXT, favorite BOOLEAN, date NUMERIC
         */


        String whereClause = "id=" + String.valueOf(this.id);

        LocalSQLiteOpenHelper helper = new LocalSQLiteOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.update("NOTE", values, whereClause, null);
        db.close();
    }


    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}
