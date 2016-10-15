package rere_corporation.blocnote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Leave_editor(note);
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