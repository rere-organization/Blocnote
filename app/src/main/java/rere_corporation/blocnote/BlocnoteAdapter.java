package rere_corporation.blocnote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class BlocnoteAdapter extends ArrayAdapter<Blocnote> {
    /***********************************************************************************************
     * Create the list item having and ajust it:
     *      - Note
     *      - Favorite
     *      - Date
     *      - Color
     **********************************************************************************************/

/* ********************************************************************************************** */

    public BlocnoteAdapter(Context context, List<Blocnote> blocnotes){
        super(context, 0, blocnotes);
    }

/* ********************************************************************************************** */

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        /**********************************************************************************
         * Gets the elements linked to a note and adjusts them according to it
         *
         * Adjustment :
         *      - Set note
         *      - Favorite
         *      - Background Color
         *      - Set date
         *********************************************************************************/

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listblocnote, parent, false);
        }

        BlocnoteViewHolder viewHolder = (BlocnoteViewHolder) convertView.getTag();

        if(viewHolder == null) {
            viewHolder = new BlocnoteViewHolder();
            viewHolder.display = (LinearLayout)convertView.findViewById(R.id.list_itemBlocnote_layout);
            viewHolder.note = (TextView) convertView.findViewById(R.id.listItemBlocnote_note);
            viewHolder.favorite = (ImageView) convertView.findViewById(R.id.listItemBlocnote_favorie);
            viewHolder.favorite.setImageResource(R.drawable.favorite);
            viewHolder.date = (TextView) convertView.findViewById(R.id.listItemBlocnote_date);
            convertView.setTag(viewHolder);
        }

        Blocnote blocnote = getItem(position);

        // Display
        viewHolder.display.setBackgroundColor(blocnote.getColor());

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

        // Get the content of the date
        String Date_String = blocnote.getDate();
        String Display_date = getStringDate(Date_String);


        viewHolder.date.setText(Display_date);

        return convertView;
    }

/* ********************************************************************************************** */

    public String getStringDate(String Date_String){
        /**********************************************************************************
         * Get the date in string in the good format
         *
         * @param Date_String
         *
         * @return String : New date
         *********************************************************************************/

        String[] Date_element = Date_String.split(" ");

        // Type : AAAA-MM-DD
        String[] Date_day = Date_element[0].split("-");

        // Type : HH:MM:SS
        String[] Date_heure = Date_element[1].split(":");

        // Create the final element
        String Display_date = Date_day[1] + "/" + Date_day[2] + " " + Date_heure[0] + ":" + Date_heure[1];


        return Display_date;
    }

/* ********************************************************************************************** */

    private class BlocnoteViewHolder{
        /**********************************************************************************
         * Class BlocnoteViewHolder contains visual elements
         * BlocnoteViewHolder is composed :
         *          - note     : text of the note
         *          - favorite : image favorite
         *          - date     : date of the last modification
         *          - display  : layout containing this elements (for background color)
         *********************************************************************************/

        public TextView note;
        public ImageView favorite;
        public TextView date;
        public LinearLayout display;
    }

}