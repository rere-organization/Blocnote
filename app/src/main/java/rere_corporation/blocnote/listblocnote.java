package rere_corporation.blocnote;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by rere on 17/10/16.
 */
public class listblocnote extends Fragment {
    /***************************************************************************
     * Class    : listblocnote
     * Action   : Genere the list of all the blocnote present in the database
     * Strategy : Select all the blocnote present in the database
     *            For each blocnote, create a layout
     *            When the layout is click, we enter on the edit mode
     **************************************************************************/

    ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listblocnote, null);

        // Select the list
        list = (ListView) view.findViewById(R.id.main_List);

        // If an element of the element is click
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Then we start the edit mode
                startViewBlocnote(id);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // In a array, we stock the list of all the note
        ArrayList<Blocnote> blocnoteList = Blocnote.getBlocnoteList(getActivity());
        // We call the class BlocnoteAdapter
        BlocnoteAdapter blocnoteAdapter = new BlocnoteAdapter(getActivity(), blocnoteList);
        list.setAdapter(blocnoteAdapter);

    }

    private void startViewBlocnote(long Blocnote_id){
        // Start the blocnote
        Intent intent = new Intent(getActivity(), Edit_Blocnote.class);
        intent.putExtra("blocnote_id", Blocnote_id);
        startActivity(intent);
    }
}