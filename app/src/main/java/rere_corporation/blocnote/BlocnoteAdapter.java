package rere_corporation.blocnote;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
            viewHolder.favorite = (ImageView) convertView.findViewById(R.id.listItemBlocnote_favorie);
            viewHolder.favorite.setImageResource(R.drawable.favorite);
            viewHolder.date = (TextView) convertView.findViewById(R.id.listItemBlocnote_date);
            convertView.setTag(viewHolder);
        }

        Blocnote blocnote = getItem(position);


        // Note
        viewHolder.note.setText(blocnote.getNote());

        // Favorite
        Boolean value = blocnote.getFavorite();

        if (value == true){
            viewHolder.favorite.setAlpha(1f);
        }
        else{
            viewHolder.favorite.setAlpha(0f);
        }


        // Date | Format = AAAA-MM-DD HH:MM:SS //

        // Get the content of the date
        String Date_String = blocnote.getDate();

        String[] Date_element = Date_String.split(" ");

        // AAAA-MM-DD
        String[] Date_day = Date_element[0].split("-");

        // HH:MM:SS
        String[] Date_heure = Date_element[1].split(":");

        // Create the final element
        String Display_date = Date_day[1] + "/" + Date_day[2] + " " + Date_heure[0] + ":" + Date_heure[1];


        viewHolder.date.setText(Display_date);

        return convertView;


    }

    private class BlocnoteViewHolder{
        public TextView note;
        public ImageView favorite;
        public TextView date;
    }



}