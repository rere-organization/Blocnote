package rere_corporation.blocnote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /***************************************************************
     * List of all the note present in the database
     *      - Possibility to edit a blocnote
     *      - Possibility to create a new blocnote
     *      - Possibility to delete a note
     *      - Possibility to change background color
     *      - Possibility to add in favorite
    ***************************************************************/

/* ********************************************************************************************** */

    // Visible elements
    ListView Listnote;
    ArrayList<Blocnote> blocnotes;

/* ********************************************************************************************** */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Listnote     = (ListView) findViewById(R.id.main_List);

    }

/* ********************************************************************************************** */

    @Override
    public boolean onCreateOptionsMenu (Menu menu){

        // Create the top menuFavorite
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

/* ********************************************************************************************** */

    @Override
    public boolean onOptionsItemSelected (final MenuItem item){
        // If a item menuFavorite is click
        switch (item.getItemId()) {

            case R.id.add_note_icone:
                Add_Blocnote(-1);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

/* ********************************************************************************************** */

    @Override
    public void onCreateContextMenu(final ContextMenu menu, final View v, final ContextMenu.ContextMenuInfo menuInfo){
        /*******************************************************************************************
         * Create the option menu
         * Adjust the text of menu item if the note is in favorite
         ******************************************************************************************/
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId()==R.id.main_List) {

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main_listselected, menu);

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            int position = info.position;

            Boolean haveFavorite = blocnotes.get(position).getFavorite();

            if (haveFavorite) {
                MenuItem textFavorie = (MenuItem) menu.findItem(R.id.menuAddFavorite);
                String removeFavorite = getResources().getString(R.string.RemoveFavorite);
                textFavorie.setTitle(removeFavorite);
            }

        }

    }

/* ********************************************************************************************** */

    @Override
    public boolean onContextItemSelected(final MenuItem item){
        /*******************************************************************************************
         * Manage the click on the menu
         * - Edit the note
         * - Add in favorite
         * - Change the color
         * - Delete the note
         ******************************************************************************************/

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        int position = info.position;
        // Get the id of the note
        long idNote = blocnotes.get(position).getId();
        Boolean haveFavorite = blocnotes.get(position).getFavorite();


        switch (item.getItemId()){
            case R.id.menuEditNote:
                Add_Blocnote(idNote);
                return true;

            case R.id.menuAddFavorite:
                addFavoriteNote(idNote, haveFavorite);
                return true;

            case R.id.menuChangeColor:
                changeColor(idNote);
                return true;

            case R.id.menuDeleteNote:
                    delete_Blocnote(idNote);
                return true;
            default:
                return super.onContextItemSelected(item);

        }

    }

/* ********************************************************************************************** */

    @Override
    protected void onResume(){
        /*******************************************************************************************
         * Generate the list of blocnote
         * Manage the click (simple) on the item
         ******************************************************************************************/

        super.onResume();

        // Generate the array of all notes
        blocnotes = genererNotes();

        final BlocnoteAdapter adapter = new BlocnoteAdapter(MainActivity.this, blocnotes);
        Listnote.setAdapter(adapter);

        // Context menu
        registerForContextMenu(Listnote);


        // Edit the note
        Listnote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long idNote = blocnotes.get(position).getId();
                Add_Blocnote(idNote);
            }
        });

    }

/* ********************************************************************************************** */

    private ArrayList<Blocnote> genererNotes(){
        /*************************************************************
         * Generate all the notes present in the database to an array
         ************************************************************/

        ArrayList<Blocnote> blocnoteList = Blocnote.getBlocnoteList(this);

        return blocnoteList;
    }

/* ********************************************************************************************** */

    private void Add_Blocnote(long id){
        /*************************************************************
         * Change the layout for the blocnote editor
         *
         * @param id
         ************************************************************/

        // Call the class Edit_Blocnote
        Intent intent = new Intent(this, Edit_Blocnote.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

/* ********************************************************************************************** */

    private void delete_Blocnote(final long id){
        /*************************************************************
         * Delete the note
         * Create a layout Yes / No
         *
         * @param id
         ************************************************************/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String deleteQuestion = getResources().getString(R.string.DeleteQuestion);
        builder.setMessage(deleteQuestion);

        String textYes = getResources().getString(R.string.Yes);

        builder.setPositiveButton(textYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Delete the note
                Blocnote.delete(getApplicationContext(), id);

                dialog.dismiss();
                // Recreate the list of blocnote
                onResume();
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

/* ********************************************************************************************** */

    public void addFavoriteNote(long id, Boolean haveFavorite){
        /*******************************************************************************************
         * Add / Remove in favorite the note
         *
         ******************************************************************************************/

        haveFavorite = !haveFavorite;

        Blocnote.update_favorite(getApplicationContext(), id, haveFavorite);

        onResume();
    }

/* ********************************************************************************************** */

    public void changeColor(long idNote){
        /*******************************************************************************************
         * Select the new color
         * -> Call 'Edit_Blocnote'
         ******************************************************************************************/
        Intent intent = new Intent(this, ChangeColor.class);
        intent.putExtra("idNote", idNote);
        startActivity(intent);
    }

}