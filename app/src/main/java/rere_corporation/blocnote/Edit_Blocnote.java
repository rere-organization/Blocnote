package rere_corporation.blocnote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by rere on 14/10/16.
 */

public class Edit_Blocnote extends Activity {

    // Déclare élements
    EditText note;
    Button validate;
    Button cancel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_blocnote);

        note     = (EditText)findViewById(R.id.note);
        validate = (Button)findViewById(R.id.validate);
        cancel   = (Button)findViewById(R.id.cancel);

        // If the button cancel is put
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Leave_editor(note);
            }

        });

        // If the button validate is put
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // Insert in the database
                /*
                Context context;
                context = getApplicationContext();
                Blocnote.this.insert(context);
                */
                /*
                Blocnote.insert(context);
                 */



                ContentValues values = new ContentValues();

                values.put("text", String.valueOf(note.getText()));
                values.put("favorite", false);

                /**
                 * id INTEGER PRIMARY KEY," +
                 "title TEXT, text TEXT, favorite BOOLEAN, date NUMERIC
                 */


                /* String whereClause = "id=" + String.valueOf(this.id); */

                LocalSQLiteOpenHelper helper = new LocalSQLiteOpenHelper(getApplicationContext());
                SQLiteDatabase db = helper.getWritableDatabase();

                db.update("NOTE", values, null, null);
                db.close();

                Edit_Blocnote.this.finish();
            }
        });

    }


    private void Leave_editor(EditText note){

        String txt = note.getText().toString();

        // If there has no text
        if (txt.isEmpty()){
            Edit_Blocnote.this.finish();
        }
        // Do a confirmation
        else{

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Are you sure ?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Leave the edit blocnote
                    dialog.dismiss();
                    Edit_Blocnote.this.finish();

                }

            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Just quit the dialog
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();


        }
    }

}
