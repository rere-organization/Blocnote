package rere_corporation.blocnote;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChangeColor extends MainActivity {
    /***********************************************************************************************
     * Create a color list and save one chosen by the user
     **********************************************************************************************/

/* ********************************************************************************************** */

    private ListView listColor;
    private String[] colorName = new String[]{"Jaune", "Orange", "Rouge", "Violet", "Bleu", "Vert", "Gris", "Aucun"};
    private int[] colorElement = new int[]{Color.YELLOW, Color.rgb(255,165,0), Color.rgb(255, 51, 51),
            Color.rgb(238,130,238), Color.rgb(153, 204, 255), Color.rgb(102, 255, 102), Color.rgb(192,192,192),
            Color.WHITE};
    private long idNote;

/* ********************************************************************************************** */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        /**********************************************************************************
         * Create a list containing colors
         * For each of them, associate the corresponding color
         **********************************************************************************/
        super.onCreate(savedInstanceState);

        // Get the id of the select note
        idNote = getIntent().getExtras().getLong("idNote");

        setContentView(R.layout.changecolor);

        listColor = (ListView)findViewById(R.id.listChangeColor);

        List<ElementColor> changeColors = genererColors();

        ChangeColorAdapter adapter = new ChangeColorAdapter(getApplicationContext(), changeColors);

        listColor.setAdapter(adapter);
    }

/* ********************************************************************************************** */

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        /**********************************************************************************
         * Hide items from OptionsMenu
         **********************************************************************************/

        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.getItem(0).setVisible(false);

        return true;
    }

/* ********************************************************************************************** */

    @Override
    public void onResume(){
        /**********************************************************************************
         * Click and save the selected color
         **********************************************************************************/
        super.onResume();

        listColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                saveColor(position);
            }
        });

    }

/* ********************************************************************************************** */

    private void saveColor(int position){
        /**********************************************************************************
         * Save the selected color in the database
         * If the note doesn't exist, keep the selected color
         *
         * @param position
         **********************************************************************************/

        if(idNote != -1){
            // Save the color in the database
            Blocnote.saveColor(getApplicationContext(), idNote, colorElement[position]);

            ChangeColor.this.finish();

        }
        else{
            // Return the value of the color
            Intent intent = new Intent(getApplicationContext(), Edit_Blocnote.class);
            intent.putExtra("idColor", colorElement[position]);
            setResult(1, intent);

            ChangeColor.this.finish();
        }

    }

/* ********************************************************************************************** */

    private List<ElementColor> genererColors(){
        /**********************************************************************************
         * Create color list with color code
         *
         * @return List<ElementColor>
         **********************************************************************************/

        List<ElementColor> changeColors = new ArrayList<ElementColor>();

        for(int i = 0; i < colorName.length; i++){
            changeColors.add(new ElementColor(colorName[i], colorElement[i]));
        }

        return changeColors;
    }

/* ********************************************************************************************** */

    public class ElementColor{
        /**********************************************************************************
         * Class ElementColor contains the elements of a color :
         *          - name
         *          - color code
         **********************************************************************************/

        private String nameColor;
        private int color;

    /* ****************************************************************************************** */

        public ElementColor(String nameColor, int color){

            this.nameColor = nameColor;
            this.color     = color;

        }

    /* ****************************************************************************************** */

        public String getNameColor(){
            return this.nameColor;
        }

        public int getColor(){
            return this.color;
        }

    }

}
