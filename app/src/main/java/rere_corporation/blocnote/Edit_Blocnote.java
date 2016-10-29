package rere_corporation.blocnote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
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

        final long id = getIntent().getExtras().getLong("id");

        note     = (EditText)findViewById(R.id.note);
        validate = (Button)findViewById(R.id.validate);
        cancel   = (Button)findViewById(R.id.cancel);


        // Search to the database the information if the note exist
        if(id != -1){
            Blocnote noteExist = Blocnote.getBlocnote(getApplicationContext(), id);
            note.setText(noteExist.getNote());
        }

        // Button Cancel
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Leave_editor(note);
            }

        });

        // Button Validate
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Add_note(note, id);
            }
        });

    }


    private void Leave_editor(EditText note){
        /**********************************************************************************
         * @require Button cancel activate
         *
         * If there no text, leave the editor, else create an AlertDialog (yes/no)
         *********************************************************************************/

        String txt = note.getText().toString();

        // If there has no text
        if (txt.isEmpty()){
            // End the editor menu
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

    private void Add_note (EditText note, long id){
        /**********************************************************************************
         * @require Button validate activate
         *
         * Save the note into the database
         *
         * @param note
         * @param id
         *
         *********************************************************************************/

        // Get the text
        String txt_note = note.getText().toString();

        // No text
        if (txt_note.isEmpty()){
            // Finish the editor mode
            Edit_Blocnote.this.finish();
        }
        // Add to the database
        else{
            // Get the context
            Context context;
            context = getApplicationContext();

            if(id != -1){
                Blocnote.update(context, id, note);
            }
            else {
                // Insert data in the database
                Blocnote.insert(context, note);
            }
            // Finish the editor mode
            Edit_Blocnote.this.finish();
        }
    }

}
