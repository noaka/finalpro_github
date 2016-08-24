package com.matan.listit;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class My_Invetory extends ActionBarActivity {

    List<Integer>  productsAmountList = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__invetory);
     //   Parse.enableLocalDatastore(this);
     //   Parse.initialize(this, "siV3hDDGjBNayXYBEIg1XBv8G7s6LTA1p5clPycJ", "T6QQLhOV7Uplc1xWsq1FIQCSJEEyOJwax2QWrTSu");


        ParseQuery<ParseObject> query = ParseQuery.getQuery("MyInventory");
        query.whereNotEqualTo("Code", " ");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {}
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {

                for (int i = 0; i < parseObjects.size(); i++) {
                    ParseObject object = parseObjects.get(i);
                    productsAmountList.add(i,object.getInt("In_Stock"));

                    TableLayout t = (TableLayout) findViewById(R.id.invProdList);
                    TableRow row = new TableRow(getApplicationContext());

                    TextView nameTextView = new TextView(getApplicationContext());
                    nameTextView.setText(object.getString("Product_Name"));
                    nameTextView.setTextColor(Color.BLACK);

                    TextView codeTextView = new TextView(getApplicationContext());
                    codeTextView.setText(object.getString("Code"));
                    codeTextView.setTextColor(Color.BLACK);


                    TextView amountTextView = new TextView(getApplicationContext());
                    amountTextView.setInputType(InputType.TYPE_CLASS_NUMBER);
                    amountTextView.setText(String.valueOf(productsAmountList.get(i)));
                    amountTextView.setTextColor(Color.BLACK);

                    if (productsAmountList.get(i)==1) {
                        Toast.makeText(getApplicationContext(), "HEADS UP! one last unit of  " + object.getString("Product_Name"), Toast.LENGTH_LONG).show();

                        amountTextView.setTextColor(Color.RED);
                        codeTextView.setTextColor(Color.RED);
                        nameTextView.setTextColor(Color.RED);
                    }


                    row.addView(nameTextView);
                    row.addView(codeTextView);
                    row.addView(amountTextView);

                    t.addView(row);

                }


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coupon_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
