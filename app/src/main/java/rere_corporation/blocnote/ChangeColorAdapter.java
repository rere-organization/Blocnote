package rere_corporation.blocnote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ChangeColorAdapter extends ArrayAdapter<ChangeColor.ElementColor>{
    /***********************************************************************************************
     * Create the list item having and ajust it:
     *      - Note
     *      - Favorite
     *      - Date
     *      - Color
     **********************************************************************************************/

/* ********************************************************************************************** */

    public ChangeColorAdapter(Context context, List<ChangeColor.ElementColor> changeColor){
        super(context, 0, changeColor);
    }

/* ********************************************************************************************** */

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        /**********************************************************************************
         * Gets the elements to a color and adjusts them according to it
         *
         * Adjustment :
         *      - Set name
         *      - Background Color
         *********************************************************************************/

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listchangecolor, parent, false);
        }

        ChangeColorViewHolder viewHolder = (ChangeColorViewHolder) convertView.getTag();

        if (viewHolder == null){
            viewHolder = new ChangeColorViewHolder();

            viewHolder.name  = (TextView)convertView.findViewById(R.id.listChangeColorText);

            convertView.setTag(viewHolder);
        }

        ChangeColor.ElementColor changeColor = getItem(position);

        viewHolder.name.setBackgroundColor(changeColor.getColor());
        viewHolder.name.setText(changeColor.getNameColor());

        return convertView;
    }

/* ********************************************************************************************** */

    private class ChangeColorViewHolder{
        /**********************************************************************************
         * Class ChangeColorViewHolder contains visual element
         *********************************************************************************/
        public TextView name;

    }
}