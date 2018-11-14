package fr.maoz.barscanner;


import android.net.Uri;
import android.os.Build;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ManageDbActivity extends AppCompatActivity {

    private Button btnImport, btnExport, btnDelete;
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_db);

        myDb = new DatabaseHelper(this);
        btnImport=(Button) findViewById(R.id.importDb_btn);
        btnExport=(Button) findViewById(R.id.exportDb_btn);
        btnDelete=(Button) findViewById(R.id.deleteDb_btn);


        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                    new ExportDatabaseCSV().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                } else {

                    new ExportDatabaseCSV().execute();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.onReset();
                Toast.makeText(ManageDbActivity.this, "La base de donnée a été réinitialisée", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
    public class ExportDatabaseCSV extends AsyncTask<String, Void, Boolean> {

        private final ProgressDialog dialog = new ProgressDialog(ManageDbActivity.this);
        DatabaseHelper dbhelper;
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Exporting database...");
            this.dialog.show();
            dbhelper = new DatabaseHelper(ManageDbActivity.this);
        }

        protected Boolean doInBackground(final String... args) {

            File exportDir = new File(Environment.getExternalStorageDirectory(), "/codesss/");
            if (!exportDir.exists()) { exportDir.mkdirs(); }

            File file = new File(exportDir, "db_inventory.csv");
            try {
                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                Cursor curCSV = dbhelper.raw();
                csvWrite.writeNext(curCSV.getColumnNames());
                while(curCSV.moveToNext()) {
                    String arrStr[]=null;
                    String[] mySecondStringArray = new String[curCSV.getColumnNames().length];
                    for(int i=0;i<curCSV.getColumnNames().length;i++)
                    {
                        mySecondStringArray[i] =curCSV.getString(i);
                    }
                    csvWrite.writeNext(mySecondStringArray);
                }
                csvWrite.close();
                curCSV.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        protected void onPostExecute(final Boolean success) {
            if (this.dialog.isShowing()) { this.dialog.dismiss(); }
            if (success) {
                Toast.makeText(ManageDbActivity.this, "Export successful!", Toast.LENGTH_SHORT).show();
                ShareFile();
            } else {
                Toast.makeText(ManageDbActivity.this, "Export failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void ShareFile() {
        File exportDir = new File(Environment.getExternalStorageDirectory(), "/codesss/");
        String fileName = "db_inventory.csv";
        File sharingGifFile = new File(exportDir, fileName);
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("application/csv");
        Uri uri = Uri.fromFile(sharingGifFile);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(shareIntent, "Share CSV"));
    }

}