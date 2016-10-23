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

    public BlocnoteAdapter(Context context, List<Blocnote> blocnotes){
        super(context, 0, blocnotes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listblocnote, parent, false);
        }

        BlocnoteViewHolder viewHolder = (BlocnoteViewHolder) convertView.getTag();

        if(viewHolder == null) {
            viewHolder = new BlocnoteViewHolder();
            viewHolder.note = (TextView) convertView.findViewById(R.id.listItemBlocnote_note);
            convertView.setTag(viewHolder);
        }

        Blocnote blocnote = getItem(position);

        viewHolder.note.setText(blocnote.getNote());

        return convertView;



    }

    private class BlocnoteViewHolder{
        public TextView note;
    }



}