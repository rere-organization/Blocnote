package rere_corporation.blocnote;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import static android.R.id.list;

public class MainActivity extends FragmentActivity {
    /***************************************************************
     * Class    : MainActivity
     * Action   : List of all the Blocnote present in the database
     *            Possibility to edit a blocnote
     *            Possibility to create a new blocnote
     * Strategy : Create a button and call the class Edit_blocnote
     *            Genere the l
    ***************************************************************/


    // Button for add a blocnote //
    Button add_blocnote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_listblocnote); /* edit_blocnote */

        // Declaration of the button
        add_blocnote = (Button) findViewById(R.id.addBlocnote);

        // If the button is clicked
        add_blocnote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Add a blocnote
                Add_Blocnote();
            }
        });
    }


    @Override
    public void onResume(){
        super.onResume();

        // Create the list of all note
        listblocnote listblocnote = new listblocnote();

        ArrayList<Blocnote> blocnotelist = Blocnote.getBlocnoteList(this);
        BlocnoteAdapter blocnoteAdapter = new BlocnoteAdapter(this, blocnotelist);




       // openFragment(listblocnote);



        //        ArrayList<DVD> dvdList = DVD.getDVDList(this);
//        DVDAdapter dvdAdapter = new DVDAdapter(this, dvdList);
//        list.setAdapter(dvdAdapter);
        // openFragment(listblocnote);

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
/**
    private void openFragment (Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_placeHolder, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
*/
}