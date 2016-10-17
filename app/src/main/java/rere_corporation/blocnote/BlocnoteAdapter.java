package rere_corporation.blocnote;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rere on 17/10/16.
 */
public class BlocnoteAdapter extends ArrayAdapter<Blocnote> {

    Context context;

    public BlocnoteAdapter(Context context, List<Blocnote> objects){
        super(context, -1, objects);
        this.context = context;

    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        View view = null;

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.listblocnote, null);
        }
        else{
            view = convertView;
        }

        Blocnote blocnote = getItem(pos);

        view.setTag(blocnote);

        TextView note = (TextView)view.findViewById(R.id.listItemBlocnote_note);

        note.setText(blocnote.getNote());

        return view;

    }



}
