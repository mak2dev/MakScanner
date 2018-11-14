package fr.maoz.barscanner;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;



public class DisplayActivity extends AppCompatActivity {


    public static EditText searchEdit;
    private Button btnSearch, btnScan;
    private LinearLayout firstLayout, secondLayout;
    public static TextView refDisplay, empDisplay,stockDisplay;
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        myDb = new DatabaseHelper(this);
        searchEdit = (EditText) findViewById(R.id.ref_search);
        refDisplay = (TextView) findViewById(R.id.searched_ref);
        empDisplay = (TextView) findViewById(R.id.searched_emp);
        stockDisplay = (TextView) findViewById(R.id.searched_stock);
        btnScan = (Button) findViewById(R.id.scan_btn);
        btnSearch = (Button) findViewById(R.id.search_btn);
        firstLayout = (LinearLayout) findViewById(R.id.first_layout);
        secondLayout = (LinearLayout) findViewById(R.id.second_layout);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this, ScanActivity.class);
                intent.putExtra("ACTIVITY", 1);// 1: scan de la réference en recherche
                startActivity(intent);
            }
        });

        SearchData();
    }
    public  void SearchData() {
        btnSearch.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(searchEdit.getText().toString() !="")
                        {
                            firstLayout.setVisibility(LinearLayout.GONE);
                            secondLayout.setVisibility(LinearLayout.VISIBLE);
                            Product p=myDb.getProduct(searchEdit.getText().toString());
                            refDisplay.setText(p.getReference());
                            empDisplay.setText(p.getEmplacement());
                            stockDisplay.setText(Integer.toString(p.getStock()));

                        }
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
