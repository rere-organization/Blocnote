package rere_corporation.blocnote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.LogPrinter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by rere on 14/10/16.
 */

public class Edit_Blocnote extends ActionBarActivity {

    // Déclare élements
    EditText note;
    Button validate;
    Button cancel;
    Boolean haveFavorite;
    Drawable icone_favorie;
    long idNote;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_blocnote);

        // Get the id of the note
        final long id = getIntent().getExtras().getLong("id");

        // favorite = (MenuItem)findViewById(R.id.favorite);
        note     = (EditText)findViewById(R.id.note);
        validate = (Button)findViewById(R.id.validate);
        cancel   = (Button)findViewById(R.id.cancel);

        // If the note exist
        if(id != -1){

            // Search in the database the information of the note
            Blocnote noteExist = Blocnote.getBlocnote(getApplicationContext(), id);

            idNote = id;

            // Note
            note.setText(noteExist.getNote());

            // Favorite
            haveFavorite = noteExist.getFavorite();

        }else{
            haveFavorite = false;
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


    @Override
    public boolean onPrepareOptionsMenu (Menu menu){
        super.onPrepareOptionsMenu(menu);
        // Set the color of the icon


        if (!haveFavorite) {
            // Récupération de l'icone
            icone_favorie = ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite);

            // Changement de couleur
            icone_favorie.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);

            menu.getItem(0).setIcon(icone_favorie);
        }
        else {
            menu.getItem(0).setIcon(R.drawable.favorite);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){

        // Create the top menu
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected (final MenuItem item){
        // If a item menu is click
        switch (item.getItemId()) {

            case R.id.favorite_icone:

                Add_favorite();
                this.invalidateOptionsMenu();
                Log.i("Tag", "Favorite icone selected");

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
/**
    @Override
    public void invalidateOptionsMenu (){

        Menu menu = (Menu)findViewById(R.menu.menu);

        if (!haveFavorite) {
            // Récupération de l'icone
            icone_favorie = ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite);

            // Changement de couleur
            icone_favorie.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);

            menu.getItem(0).setIcon(icone_favorie);
        }
        else {
            menu.getItem(0).setIcon(R.drawable.favorite);
        }
    }
**/

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
                // The update is automatic
                Blocnote.update(context, id, note);
            }
            else {
                // Insert data in the database
                Blocnote.insert(context, note, haveFavorite);
            }
            // Finish the editor mode
            Edit_Blocnote.this.finish();
        }
    }

    public void Add_favorite (){

        haveFavorite = !haveFavorite;

        // If the note exist
        if (idNote != -1){
            // Update in the database
            Blocnote.update_favorite(getApplicationContext(), idNote, haveFavorite);
        }


    }



}