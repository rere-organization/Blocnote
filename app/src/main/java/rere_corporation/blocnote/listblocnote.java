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

    ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Managing the list - click

        View view = inflater.inflate(R.layout.fragment_listblocnote, null);

        list = (ListView) view.findViewById(R.id.main_List);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startViewBlocnote(id);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<Blocnote> blocnoteList = Blocnote.getBlocnoteList(getActivity());
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