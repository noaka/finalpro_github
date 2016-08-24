package com.matan.listit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class Main_Page extends ActionBarActivity {

    ImageButton ListsBtn;
    ImageButton ScanBtn;
    ImageButton CouponBtn;
 //   private IntentIntegrator integrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

     //  Parse.enableLocalDatastore(this);
    //    Parse.initialize(this, "siV3hDDGjBNayXYBEIg1XBv8G7s6LTA1p5clPycJ", "T6QQLhOV7Uplc1xWsq1FIQCSJEEyOJwax2QWrTSu");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ListsBtn=(ImageButton)findViewById(R.id.myLists_Button);
        ScanBtn=(ImageButton)findViewById(R.id.scan_button);
        CouponBtn=(ImageButton)findViewById(R.id.mycuopon_Button);
        ListsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(Main_Page.this,MyLists_Page.class));
            }
        });
        ScanBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v){
               scanNow(v);
            }
        });

        CouponBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(Main_Page.this,MapsActivity.class));
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
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
    public void scanNow(View view) {
        // Log.d("test", "button works!");

        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
              //  IntentResult scanResult=integrator.parseActivityResult(requestCode,resultCode, intent);
                String barcod = intent.getStringExtra("SCAN_RESULT");
               String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Log.i("xZing:::::::", "contents: "+barcod+" format: "+format); // Handle successful scan
              //  barcodeValue = intent.getStringExtra("barcode");
              //  Toast toast = Toast.makeText(getApplicationContext(), barcodeValue, Toast.LENGTH_SHORT);
              //  toast.show();
             //   String scanContent = scanResult.getContents();
             //   Intent resultData = new Intent();
             //   resultData.putExtra("barcode", scanContent);
             //   setResult(Activity.RESULT_OK, resultData);
             //   finish();
                Log.i(barcod,format);
              //  Scan_Page.scanResultxt(format,contents);
              //  Intent intent=this.getIntent();
                //startActivity(new Intent(Main_Page.this,Scan_Page.class),contents,format);
               // Intent k = new Intent(this, Scan_Page.class);

               // startActivity(k);
                Intent resultIntent = new Intent(this, Scan_Page.class);
                resultIntent.putExtra("barcodstxt", barcod);
                resultIntent.putExtra("formattxt", format);
                startActivity(resultIntent);
            }
            else
                //(resultCode == RESULT_CANCELED)
            { // Handle cancel
                Log.i("xZing", "Cancelled");
            }
        }
    }
}
