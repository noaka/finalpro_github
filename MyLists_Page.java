package com.matan.listit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.Parse;


public class MyLists_Page extends ActionBarActivity {

    private Button bscBtn;
    private Button invBtn;
    private Button cartBtn;
    private Button cpnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lists__page);
        Parse.enableLocalDatastore(this);
       Parse.initialize(this, "siV3hDDGjBNayXYBEIg1XBv8G7s6LTA1p5clPycJ", "T6QQLhOV7Uplc1xWsq1FIQCSJEEyOJwax2QWrTSu");
        bscBtn=(Button)findViewById(R.id.basicListBtn);
        invBtn=(Button)findViewById(R.id.invListBtn);
        cartBtn=(Button)findViewById(R.id.cartListBtn);
        cpnBtn=(Button)findViewById(R.id.cpnListBtn);

        bscBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(MyLists_Page.this,BasicList_page.class));
            }
        });
        invBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyLists_Page.this,My_Invetory.class));

            }
        });
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(MyLists_Page.this,Cart_Page.class));
            }
        });
        cpnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(MyLists_Page.this,Coupon_page.class));
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_lists__page, menu);
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
