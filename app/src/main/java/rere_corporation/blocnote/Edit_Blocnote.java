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
    /**********************************************************************************
     * Class    : Edit_Blocnote
     * Action   :
     * Strategy :
     *********************************************************************************/

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
                Add_note(note);
            }
        });

    }


    private void Leave_editor(EditText note){
        /**********************************************************************************
         * Function      : Leave_editor
         * Prerequisites : Button "cancel" must be activated
         * Action        : If there are no text, leave the editor
         *                 Else, create a confirmation menu
         * Strategy      : Compare the text of the layout "note"
         *                 Create a AlertDialog with two choices (yes/no)
         *********************************************************************************/

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

    private void Add_note (EditText note){
        /**********************************************************************************
         * Function      : Add_note
         * Prerequisites : Button "validate" must be activated
         * Action        : If there are no text quit the editor
         *                 Else, save the note in the database
         * Strategy      : Compare the text of the layout "note"
         *                 Data recovery and sends to the database
         *********************************************************************************/

        // Text recovery
        String txt_note = note.getText().toString();

        // Compare the value
        if (txt_note.isEmpty()){
            // Finish the editor mode
            Edit_Blocnote.this.finish();
        }
        // Add to the database
        else{
            // Get the context
            Context context;
            context = getApplicationContext();

            // Insert data in the database
            Blocnote.insert(context, note);

            // Finish the editor mode
            Edit_Blocnote.this.finish();
        }

    }


}
