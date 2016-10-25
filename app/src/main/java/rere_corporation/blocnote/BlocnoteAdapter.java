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
            viewHolder.favorite = (TextView) convertView.findViewById(R.id.listItemBlocnote_favorie);
            viewHolder.date = (TextView) convertView.findViewById(R.id.listItemBlocnote_date);
            convertView.setTag(viewHolder);
        }

        Blocnote blocnote = getItem(position);


        // Note
        viewHolder.note.setText(blocnote.getNote());

        // Favorite
        Boolean value = blocnote.getFavorite();

        if (value == true){
            viewHolder.favorite.setText("Oui");
        }
        else{
            viewHolder.favorite.setText("Non");
        }


        // Date | Format = AAAA-MM-DD HH:MM:SS

        // Get the content of the date
        String Date_String = blocnote.getDate();
        // Split
        String[] date_text = Date_String.split("-");

        // Set the text
        viewHolder.date.setText(date_text[1] + "/" + date_text[2]);



        return convertView;


    }

    private class BlocnoteViewHolder{
        public TextView note;
        public TextView favorite;
        public TextView date;
    }



}