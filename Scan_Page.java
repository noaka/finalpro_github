package com.matan.listit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class Scan_Page extends ActionBarActivity {

    public static TextView formatTxt;
    public static TextView barcodTxt;
    ImageButton addBtn,delBtn;
/*
    public Scan_Page(Intent intent, String contents, String format) {

       // super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_page);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        formatTxt.setText("FORMAT: " + format);
        contentTxt.setText("CONTENT: " + contents);


    }
*/
   public static void setformatTxt(String s1){
       formatTxt.setText(s1);

   }
    public String getformatTxt(){
        return String.valueOf(formatTxt);

    }
    public static void setcontentTxt(String s2) {
        barcodTxt.setText(s2);
    }

    public String getcontentTxt(){
        return String.valueOf(barcodTxt);

    }

    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "siV3hDDGjBNayXYBEIg1XBv8G7s6LTA1p5clPycJ", "T6QQLhOV7Uplc1xWsq1FIQCSJEEyOJwax2QWrTSu");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_page);


        formatTxt = (TextView)findViewById(R.id.scan_format);
        barcodTxt = (TextView)findViewById(R.id.scan_content);
        Intent intent = getIntent();

        final String barcods= intent.getStringExtra("barcodstxt");
        String format= intent.getStringExtra("formattxt");

        formatTxt.setText("FORMAT: " + format);
        barcodTxt.setText("Barcode: " + intent.getStringExtra("barcodstxt"));

        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
                                                              @Override
                                                              public void onClick(View v) {

                                                                  ParseQuery<ParseObject> query1 = ParseQuery.getQuery("MyInventory");
                                                                  query1.whereEqualTo("Code", barcods);
                                                                  query1.getFirstInBackground(new GetCallback<ParseObject>() {
                                                                      public void done(ParseObject object, ParseException e) {
                                                                          int unit1 = 0;
                                                                          if (object != null) {
                                                                              // iterate over all messages and delete them
                                                                              unit1 = (int) object.getNumber("In_Stock") + 1;

                                                                              object.put("In_Stock",unit1 );
                                                                              object.saveInBackground();
                                                                              Log.i("the unit is", String.valueOf(unit1));
                                                                              Toast.makeText(Scan_Page.this, "The Iteam been added!!", Toast.LENGTH_LONG).show();
                                                                          } else {


                                                                          }
                                                                      }
                                                                  });

                                                              //   ParseObject listProduct = new ParseObject("MyInventory");
                                                                //  listProduct.put("Code",barcods );

                                                                 // listProduct.saveInBackground();


                                                                  ParseQuery<ParseObject> query = ParseQuery.getQuery("Cart");
                                                                  query.whereEqualTo("Code", barcods);
                                                                  query.getFirstInBackground(new GetCallback<ParseObject>() {
                                                                      public void done(ParseObject object, ParseException e) {
                                                                          int unit = 0;
                                                                          if (object != null) {
                                                                              // iterate over all messages and delete them
                                                                              unit = (int) object.getNumber("To_Buy") - 1;

                                                                              object.put("To_Buy",unit );
                                                                              object.saveInBackground();
                                                                             Log.i("the unit is", String.valueOf(unit));
                                                                          } else {


                                                                          }
                                                                      }
                                                                  });
                                                              }
                                                          });
        findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() {
                                  @Override
                                   public void onClick(View v) {

                                      ParseQuery<ParseObject> query2 = ParseQuery.getQuery("MyInventory");
                                      query2.whereEqualTo("Code", barcods);
                                      query2.getFirstInBackground(new GetCallback<ParseObject>() {
                                          public void done(ParseObject object, ParseException e) {
                                              int unit2 = 0;
                                              if (object != null) {
                                                  // iterate over all messages and delete them
                                                  unit2 = (int) object.getNumber("In_Stock") - 1;

                                                  object.put("In_Stock",unit2 );
                                                  object.saveInBackground();
                                                  // Log.i("the unit is", String.valueOf(unit));
                                                  Toast.makeText(Scan_Page.this, "The Iteam been deleted!!", Toast.LENGTH_LONG).show();
                                              } else {


                                              }
                                          }
                                      });
                                    //  ParseQuery<ParseObject> query = ParseQuery.getQuery("MyInventory");
                                     // query.whereEqualTo("Code", barcods);
                                      //query.findInBackground(new FindCallback<ParseObject>() {
                                      //    public void done(List<ParseObject> invites, ParseException e) {
                                        //      if (e == null) {
                                                  // iterate over all messages and delete them
                                          //        for (ParseObject invite : invites) {
                                                //      invite.deleteInBackground();

                                                      ParseQuery<ParseObject> query = ParseQuery.getQuery("Cart");
                                                      query.whereEqualTo("Code", barcods);
                                                      query.getFirstInBackground(new GetCallback<ParseObject>() {
                                                          public void done(ParseObject object, ParseException e) {
                                                              int unit = 0;
                                                              if (object != null) {
                                                                  // iterate over all messages and delete them
                                                                  unit = (int) object.getNumber("To_Buy") + 1;
                                                                  //object.increment("To_Buy",1);
                                                                  object.put("To_Buy",unit );
                                                                  object.saveInBackground();
                                                                  // Log.i("the unit is", String.valueOf(unit));
                                                              } else {

                                                              }
                                                          }
                                                      });




                                          //        }
                                         //     } else {
                                                  //Handle condition here
                                         //     }
                                    //      }
                                  //    });


                }
            });
       // addBtn = (ImageButton) findViewById(R.id.imageButton);
       // addBtn.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View v){
          //      startActivity(new Intent(Scan_Page.this,MyLists_Page.class));
         //   }
     //   });

    }
    public static void scanResultxt(String format, String contents){

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scan_page, menu);


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
