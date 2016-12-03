package rere_corporation.blocnote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Edit_Blocnote extends AppCompatActivity {
    /***********************************************************************************************
     * Edit Blocnote Activity
     * Mangage : - the note
     *              - Save / Update
     *           - the favorite
     *              - Add / Remove
     *           - the color
     **********************************************************************************************/

/* ********************************************************************************************** */

    private EditText note;
    private Button   validate;
    private Button   cancel;
    private Boolean  haveFavorite;
    private Drawable icone_favorie;
    private long     idNote;
    private int      color;

/* ********************************************************************************************** */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        /*******************************************************************************************
         * Create note editing activity
         * Adjust the note, favorie icon
         ******************************************************************************************/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_blocnote);

        // Get the id of the note
        idNote = getIntent().getExtras().getLong("id");

        note     = (EditText)findViewById(R.id.note);
        validate = (Button)findViewById(R.id.validate);
        cancel   = (Button)findViewById(R.id.cancel);


        // If the note exist
        if(idNote != -1){

            // Search in the database the information of the note
            Blocnote noteExist = Blocnote.getBlocnote(getApplicationContext(), idNote);

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
                Add_note(note, idNote);
            }
        });

    }

/* ********************************************************************************************** */

    @Override
    public boolean onPrepareOptionsMenu (Menu menu){
        /*******************************************************************************************
         * Set the color of the favorite icon
         ******************************************************************************************/
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

/* ********************************************************************************************** */

    @Override
    public boolean onCreateOptionsMenu (Menu menu){

        // Create the top menuFavorite
        getMenuInflater().inflate(R.menu.menu_blocnote_selected, menu);

        return true;
    }

/* ********************************************************************************************** */

    @Override
    public boolean onOptionsItemSelected (final MenuItem item){
        /*******************************************************************************************
         * Manage the click on the menu
         ******************************************************************************************/
        // If a item menuFavorite is click
        switch (item.getItemId()) {

            case R.id.favorite_icone:

                Add_favorite();
                // Recreate a new optionsMenu
                this.invalidateOptionsMenu();

                return true;

            case R.id.color_icone:
                SelectColor();
                return true;

            case R.id.save_icone:
                Add_note(note, idNote);

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

/* ********************************************************************************************** */

       private void Leave_editor(EditText note){
        /**********************************************************************************
         * @require Button cancel activate
         *
         * If there no text, leave the editor, else create an AlertDialog (yes/no)
         *********************************************************************************/

        String txt = note.getText().toString();

        // If there has no text
        if (txt.isEmpty()){
            // End the editor menuFavorite
            Edit_Blocnote.this.finish();
        }
        // Do a confirmation
        else{

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            String confirmationQuestion = getResources().getString(R.string.ConfirmationQuestion);
            builder.setMessage(confirmationQuestion);

            String textYes = getResources().getString(R.string.Yes);

            builder.setPositiveButton(textYes, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Leave the edit blocnote
                    dialog.dismiss();
                    Edit_Blocnote.this.finish();

                }

            });

            String textNo = getResources().getString(R.string.No);

            builder.setNegativeButton(textNo, new DialogInterface.OnClickListener() {

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

/* ********************************************************************************************** */

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
                Blocnote.insert(context, note, haveFavorite, color);
            }
            // Finish the editor mode
            Edit_Blocnote.this.finish();
        }
    }

/* ********************************************************************************************** */

    public void Add_favorite (){
        /*******************************************************************************************
         * Add or Remove the note in favorite
         ******************************************************************************************/

        haveFavorite = !haveFavorite;

        // If the note exist
        if (idNote != -1){
            // Update in the database
            Blocnote.update_favorite(getApplicationContext(), idNote, haveFavorite);
        }

    }

/* ********************************************************************************************** */

    public void SelectColor(){

        Intent intent = new Intent(this, ChangeColor.class);
        intent.putExtra("idNote", idNote);

        if (idNote != -1){
            startActivity(intent);
        }
        else {
            startActivityForResult(intent, 1);
        }

    }

/* ********************************************************************************************** */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_CANCELED) {
            color = data.getIntExtra("idColor", -1);
        }
    }
}