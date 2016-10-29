package rere_corporation.blocnote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class MainActivity extends Activity {
    /***************************************************************
     * List of all the note present in the database
     *      - Possibility to edit a blocnote
     *      - Possibility to create a new blocnote
     *      - Possibility to delete a note
    ***************************************************************/

    // Visible elements
    Button add_blocnote;
    ListView Listnote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Declaration of the button and the list
        add_blocnote = (Button) findViewById(R.id.addBlocnote);
        Listnote     = (ListView) findViewById(R.id.main_List);

        // Create a new note
        add_blocnote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Add a blocnote
                Add_Blocnote(-1);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();

        // Generate the array of all notes
        final ArrayList<Blocnote> blocnotes = genererNotes();

        final BlocnoteAdapter adapter = new BlocnoteAdapter(MainActivity.this, blocnotes);
        Listnote.setAdapter(adapter);


        // Delete the note
        Listnote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the id of the note
                long idNote = blocnotes.get(position).getId();

                // Delete the note
                delete_Blocnote(idNote);

                return true;
            }
        });

        // Edit the note
        Listnote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long idNote = blocnotes.get(position).getId();
                Add_Blocnote(idNote);
            }
        });

    }


    private ArrayList<Blocnote> genererNotes(){
        /*************************************************************
         * Generate all the notes present in the database to an array
         ************************************************************/

        ArrayList<Blocnote> blocnoteList = Blocnote.getBlocnoteList(this);

        return blocnoteList;
    }


    private void Add_Blocnote(long id){
        /*************************************************************
         * Change the layout for the blocnote editor
         *
         * @param id
         *
         ************************************************************/

        // Call the class Edit_Blocnote
        Intent intent = new Intent(this, Edit_Blocnote.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }


    private void delete_Blocnote(final long id){
        /*************************************************************
         * Delete the note
         * Create a layout Yes / No
         *
         * @param id
         *
         ************************************************************/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to delete this note ?");


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Delete the note
                Blocnote.delete(getApplicationContext(), id);

                dialog.dismiss();
                // Recreate the list of blocnote
                onResume();
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