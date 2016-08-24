package com.matan.listit;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
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


public class BasicList_page extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private int x=2;
    private Button addBtn;
    private TableLayout t1;
    private TableRow tr;
    private TextView proName, proCode;
    String categorySelected, productSelected, proBarcode;
    String[] categoryList = new String[] {"choose product category", "Cheese", "Deserts","Sauces"};
    String[] ProductsList= new String [] {"choose product to add to your list", "X"};
    String[] BarcodeList = new String[] {" "," "};
    List<Integer>  productsAmountList = new ArrayList<Integer>();




    private EditText ammountText;
    private CheckBox deleteCheckBox;

    Spinner catSpinner,proSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_list_page);


        catSpinner =(Spinner)findViewById(R.id.categorySpinner);
        proSpinner =(Spinner)findViewById(R.id.productSpinner);
        addBtn=(Button)findViewById(R.id.Add_Button);

        t1= (TableLayout)findViewById(R.id.ProdList);
        t1.setColumnStretchable(0,true);
        t1.setColumnStretchable(1,true);

        catAdapter() ;
        prodAdapter();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("MyList");
        query.whereNotEqualTo("Code", " ");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {}
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {

                for (int i = 0; i < parseObjects.size(); i++) {
                    ParseObject object = parseObjects.get(i);
                    productsAmountList.add(i,object.getInt("Ammount"));

                    TableLayout t = (TableLayout) findViewById(R.id.ProdList);
                    TableRow row = new TableRow(getApplicationContext());

                    TextView nameTextView = new TextView(getApplicationContext());
                    nameTextView.setText(object.getString("Product_Name"));
                    nameTextView.setTextColor(Color.BLACK);

                    TextView codeTextView = new TextView(getApplicationContext());
                    codeTextView.setText(object.getString("Code"));
                    codeTextView.setTextColor(Color.BLACK);


                    EditText amountEditText = new EditText(getApplicationContext());
                    amountEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    amountEditText.setText(String.valueOf(productsAmountList.get(i)));
                    amountEditText.setTextColor(Color.BLACK);

                    CheckBox delCheckBox = new CheckBox(getApplicationContext());
                    delCheckBox.setTextColor(Color.BLACK);

                    row.addView(nameTextView);
                    row.addView(codeTextView);
                    row.addView(amountEditText);
                    row.addView(delCheckBox);

                    t.addView(row);

                }


            }
        });

    }

    public void catAdapter(){
        ArrayAdapter Catadapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, categoryList);
        catSpinner.setAdapter(Catadapter);
        catSpinner.setOnItemSelectedListener(this);
    }
    public void prodAdapter(){
        ArrayAdapter prodadapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_expandable_list_item_1, ProductsList);
        proSpinner.setAdapter(prodadapter);
        proSpinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView parent, View view, int pos,long id) {
        categorySelected = catSpinner.getSelectedItem().toString();

        final ParseQuery<ParseObject> query = ParseQuery.getQuery("PRODUCTS");
        query.whereEqualTo("Category", categorySelected);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> prodList, java.text.ParseException e) {}
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                if (e == null) {
                    for(int i=0; i<parseObjects.size(); i++) {
                        ProductsList[i] = parseObjects.get(i).get("Product_Name").toString();
                        BarcodeList[i] = parseObjects.get(i).get("Code").toString();
                    }

                }
                //prodAdapter();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_basic_list_page, menu);
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Add_Button:
                Boolean flag = true;
                productSelected = proSpinner.getSelectedItem().toString();


                for (int i = 0; i < 2; i++) {
                    if (productSelected == ProductsList[i])
                        proBarcode = BarcodeList[i];
                }



                for (int i = 0; i < t1.getChildCount(); i++) {

                    TableRow checkIfRowExists = (TableRow) t1.getChildAt(i);
                    TextView productView = (TextView) checkIfRowExists.getVirtualChildAt(0);
                    TextView checkCode = (TextView) checkIfRowExists.getVirtualChildAt(1);
                    Toast.makeText(getApplicationContext(), "product " + proBarcode , Toast.LENGTH_LONG).show();

                    if (proBarcode == checkCode.getText() ) {
                        flag = false;
                        Toast.makeText(getApplicationContext(), "product " + productSelected + " is already listed ", Toast.LENGTH_LONG).show();
                        productView.setTextAppearance(getApplicationContext(), R.style.boldText);
                        productView.setBackgroundResource(R.color.bright_foreground_disabled_material_light);
                        break;
                    }
                }
                if (flag) {

                    tr = new TableRow(this);
                    proName = new TextView(this);
                    proCode = new TextView(this);
                    deleteCheckBox = new CheckBox(this);
                    ammountText = new EditText(this);
                    ammountText.setInputType(10);

                    proName.setText(productSelected);
                    proCode.setText(proBarcode);
                    ammountText.setText("1");


                    tr.addView(proName);
                    tr.addView(proCode);
                    tr.addView(ammountText);
                    tr.addView(deleteCheckBox);
                    t1.addView(tr);
                    break;
                }



                break;

            case R.id.update_Button:

                int ammountNum =1;

                TextView proBoxToCheck = new TextView(this);
                TextView codeBoxToCheck = new TextView(this);
                EditText ammountToCheck = new EditText(this);
///////////////updating my list///////////////////////////////////////
                for (int i=1, j=t1.getChildCount(); i<j; i++) {

                    TableRow rowToCheck = new TableRow(this);
                    rowToCheck= (TableRow) t1.getChildAt(i);

                    proBoxToCheck= (TextView) rowToCheck.getChildAt(0);
                    final String proToUpdate = proBoxToCheck.getText().toString();


                    codeBoxToCheck =(TextView) rowToCheck.getChildAt(1);
                    final String codeToUpdate = codeBoxToCheck.getText().toString();

                    ammountToCheck = (EditText) rowToCheck.getChildAt(2);
                    String ammountToUpdate =ammountToCheck.getText().toString();
                    try {
                        ammountNum = Integer.parseInt(ammountToUpdate);
                    }
                    catch (NumberFormatException nfe){}


                    ParseQuery<ParseObject> query = ParseQuery.getQuery("MyList");
                    query.whereEqualTo("Code",codeToUpdate);
                    final TableRow finalRowToCheck = rowToCheck;
                    final int finalAmmountNum = ammountNum;
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> products, ParseException e) { }

                        @Override
                        public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                            ParseObject MyList = new ParseObject("MyList");
                            ParseObject Myinventory = new ParseObject("MyInventory");
                            ParseObject Cart = new ParseObject("Cart");


                            if(parseObjects.size()==0)
                            {
                                MyList.put("Product_Name",proToUpdate);
                                MyList.put("Code",codeToUpdate);
                                MyList.put("Ammount", finalAmmountNum);
                                MyList.saveInBackground();

                                Myinventory.put("Product_Name",proToUpdate);
                                Myinventory.put("Code",codeToUpdate);
                                Myinventory.put("In_Stock",0);
                                Myinventory.saveInBackground();

                                Cart.put("Product_Name",proToUpdate);
                                Cart.put("Code",codeToUpdate);
                                Cart.put("To_Buy",finalAmmountNum);
                                Cart.saveInBackground();
                            }
                            else
                            {
                                ParseObject X = parseObjects.get(0);
                                X.put("Ammount", finalAmmountNum);
                                X.saveInBackground();
                            }
                        }
                    });
                }
////////////////updating in the cart/////////////////////////////////
                for (int i=1, j=t1.getChildCount(); i<j; i++) {

                    TableRow rowToCheck = new TableRow(this);
                    rowToCheck= (TableRow) t1.getChildAt(i);

                    codeBoxToCheck =(TextView) rowToCheck.getChildAt(1);
                    final String codeToUpdate = codeBoxToCheck.getText().toString();

                    ammountToCheck = (EditText) rowToCheck.getChildAt(2);
                    String ammountToUpdate =ammountToCheck.getText().toString();
                    try {
                        ammountNum = Integer.parseInt(ammountToUpdate);
                    }
                    catch (NumberFormatException nfe){}


                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Cart");
                    query.whereEqualTo("Code",codeToUpdate);
                    final TableRow finalRowToCheck = rowToCheck;
                    final int finalAmmountNum = ammountNum;
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> products, ParseException e) { }

                        @Override
                        public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                            ParseObject MyList = new ParseObject("MyList");
                            ParseObject Myinventory = new ParseObject("MyInventory");
                            ParseObject Cart = new ParseObject("Cart");


                            if(parseObjects.size()==0)
                            { }
                            else
                            {
                                ParseObject X = parseObjects.get(0);
                                X.put("To_Buy", finalAmmountNum);
                                X.saveInBackground();
                            }
                        }
                    });
                }


                break;

            case R.id.delete_Button:

                TableRow row = new TableRow(this);
                TextView barcodeView = new TextView(this);
                TextView nameView = new TextView(this);
                CheckBox checkBox = new CheckBox(this);
                String barcode, name;
                for(int i=1 ;i <t1.getChildCount(); i++)
                {
                    row= (TableRow) t1.getChildAt(i);
                    checkBox = (CheckBox) row.getVirtualChildAt(3);
                    if(checkBox.isChecked())
                    {
                        nameView = (TextView) row.getVirtualChildAt(0);
                        name = nameView.getText().toString();

                        barcodeView= (TextView) row.getVirtualChildAt(1);
                        barcode = barcodeView.getText().toString();

                        Toast.makeText(getApplicationContext(),name+ " deleted", Toast.LENGTH_LONG).show();

////////////////////delete from my list/////////////////////////////////////////////////////
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("MyList");
                        query.whereEqualTo("Code", barcode);
                        final String finalBarcode = barcode;
                        query.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> scoreList, ParseException e) {}
                            @Override
                            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                                if (parseObjects.size()!=0) {
                                    ParseObject toDelete = parseObjects.get(0);
                                    toDelete.deleteInBackground();
                                }

                            }
                        });

                    }

                }
                for(int i=1 ;i <t1.getChildCount(); i++)
                {
                    row= (TableRow) t1.getChildAt(i);
                    checkBox = (CheckBox) row.getVirtualChildAt(3);
                    if(checkBox.isChecked())
                    {
                        nameView = (TextView) row.getVirtualChildAt(0);
                        name = nameView.getText().toString();

                        barcodeView= (TextView) row.getVirtualChildAt(1);
                        barcode = barcodeView.getText().toString();

                        Toast.makeText(getApplicationContext(),name+ " deleted", Toast.LENGTH_LONG).show();

////////////////////delete from Cart/////////////////////////////////////////////////////
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cart");
                        query.whereEqualTo("Code", barcode);
                        final String finalBarcode = barcode;
                        query.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> scoreList, ParseException e) {}
                            @Override
                            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                                if (parseObjects.size()!=0) {
                                    ParseObject toDelete = parseObjects.get(0);
                                    toDelete.deleteInBackground();
                                }

                            }
                        });
                    }
                }
                for(int i=1 ;i <t1.getChildCount(); i++)
                {
                    row= (TableRow) t1.getChildAt(i);
                    checkBox = (CheckBox) row.getVirtualChildAt(3);
                    if(checkBox.isChecked())
                    {
                        t1.removeView(row);
                        nameView = (TextView) row.getVirtualChildAt(0);
                        name = nameView.getText().toString();

                        barcodeView= (TextView) row.getVirtualChildAt(1);
                        barcode = barcodeView.getText().toString();

////////////////////delete from my inventory/////////////////////////////////////////////////////
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("MyInventory");
                        query.whereEqualTo("Code", barcode);
                        final String finalBarcode = barcode;
                        query.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> scoreList, ParseException e) {}
                            @Override
                            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                                if (parseObjects.size()!=0) {
                                    ParseObject toDelete = parseObjects.get(0);
                                    toDelete.deleteInBackground();
                                }

                            }
                        });
                    }
                }
                break;
            //go iterative on the list and look for checkbox and delete this lines
        }







    }


    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}

