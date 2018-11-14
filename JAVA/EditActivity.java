package fr.maoz.barscanner;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity{

    private EditText editStock;
    public static EditText editRef, editEmp;
    private Button btnAddData, btnScan, btnEmp;
    private DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        myDb = new DatabaseHelper(this);

        editRef= (EditText) findViewById(R.id.edit_ref);
        editEmp= (EditText) findViewById(R.id.edit_emp);
        editStock= (EditText) findViewById(R.id.edit_stock);
        btnAddData= (Button) findViewById(R.id.add_button);
        btnScan = (Button) findViewById(R.id.scan_add);
        btnEmp = (Button) findViewById(R.id.scan_emp);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, ScanActivity.class);
                intent.putExtra("ACTIVITY",0);// 0: scan de la réference en édition
                startActivity(intent);
            }
        });

        btnEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, ScanActivity.class);
                intent.putExtra("ACTIVITY",2);// 2: scan de l'emplacement
                startActivity(intent);
            }
        });

        AddData();
    }

    public  void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(editRef.getText().toString() !="" && editStock.getText().toString()!=""&& editEmp.getText().toString()!="")
                        {
                            long isInserted = myDb.insertProduct(editRef.getText().toString(),editEmp.getText().toString(),Integer.parseInt(editStock.getText().toString()));

                            if (isInserted != -1) {
                                Toast.makeText(EditActivity.this, "Produit ajouté en " + isInserted, Toast.LENGTH_LONG).show();
                                editRef.setText("");
                                editStock.setText("");
                                editEmp.setText("");
                            } else
                                Toast.makeText(EditActivity.this, "Le produit a été mis à jour", Toast.LENGTH_LONG).show();
                                editRef.setText("");
                                editStock.setText("");
                                editEmp.setText("");
                        }
                    }
                }
        );
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
}
