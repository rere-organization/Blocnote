package rere_corporation.blocnote;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class MainActivity extends Activity {
    /***************************************************************
     * Class    : MainActivity
     * Action   : List of all the Blocnote present in the database
     *            Possibility to edit a blocnote
     *            Possibility to create a new blocnote
     * Strategy : Create a button and call the class Edit_blocnote
     *            Genere the l
    ***************************************************************/

    // Variables for elements
    Button add_blocnote;
    ListView Listnote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Declaration of the button and the list
        add_blocnote = (Button) findViewById(R.id.addBlocnote);
        Listnote     = (ListView) findViewById(R.id.main_List);


        // Generate the array of all notes
        ArrayList<Blocnote> blocnotes = genererNotes();

        BlocnoteAdapter adapter = new BlocnoteAdapter(MainActivity.this, blocnotes);
        Listnote.setAdapter(adapter);


        // If the button is clicked
        add_blocnote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Add a blocnote
                Add_Blocnote();
            }
        });

    }

    private ArrayList<Blocnote> genererNotes(){
        /*************************************************************
         * Function : genererNotes
         * Action   : Generate all the notes present in the database
         *            to an array
         * Strategy : Call the funciton "getBlocnoteList" present
         *            in the class Blocnote
         ************************************************************/

        ArrayList<Blocnote> blocnoteList = Blocnote.getBlocnoteList(this);

        return blocnoteList;
    }


    private void Add_Blocnote(){
        /*************************************************************
         * Function : Add_Blocnote
         * Action   : Change the layout for a blocnote editor
         * Strategy : Call the class Edit_Blocnote
         ************************************************************/

        Intent intent = new Intent(this, Edit_Blocnote.class);
        startActivity(intent);
    }

}