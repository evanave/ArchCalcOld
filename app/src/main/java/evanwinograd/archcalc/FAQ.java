package evanwinograd.archcalc;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Evan on 12/30/2014.
 */
public class FAQ extends ListActivity {
    String [][] QandA;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    setContentView(R.layout.faq);

        String Q1 = getString(R.string.Q1);
        String A1 = getString(R.string.A1);
        String Q2 = getString(R.string.Q2);
        String A2 = getString(R.string.A2);
        String Q3 = getString(R.string.Q3);
        String A3 = getString(R.string.A3);
        String Q4 = getString(R.string.Q4);
        String A4 = getString(R.string.A4);

        String Q5 = getString(R.string.Q1);
        String A5 = getString(R.string.A1);
        String Q6 = getString(R.string.Q2);
        String A6 = getString(R.string.A2);
        String Q7 = getString(R.string.Q3);
        String A7 = getString(R.string.A3);
        String Q8 = getString(R.string.Q4);
        String A8 = getString(R.string.A4);


       QandA = new String[][]{
               {Q1,A1},
               {Q2,A2},
               {Q3,A3},
               {Q4,A4},
               {Q5,A5},
               {Q6,A6},
               {Q7,A7},
               {Q8,A8}

        };


        String [] questions = new String[]{Q1,Q2,Q3,Q4,Q5};

        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,questions));


    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Object o = this.getListAdapter().getItem(position);
        String q = o.toString();
       // String a = (String) ("A" + position);
     //    ((TextView) findViewById(R.id.question)).setText(QandA[position][0]);
     //   ((TextView) findViewById(R.id.answer)).setText(QandA[position][1]);

        Toast.makeText(this,QandA[position][1], Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_faq, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        //     return true;
        // }

        if (id == R.id.action_Home) {
                onBackPressed();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void goHome(View view){
        startActivity(new Intent(FAQ.this, ArchCalc1.class));
    }
}
