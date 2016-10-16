package rere_corporation.blocnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // Declare Button
    Button add_blocnote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); /* edit_blocnote */

        add_blocnote = (Button) findViewById(R.id.addBlocnote);

        // if the button is clicked, we change the layout
        add_blocnote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Add_Blocnote();
            }
        });
    }


    private void Add_Blocnote(){
        Intent intent = new Intent(this, Edit_Blocnote.class);
        startActivity(intent);
    }

}