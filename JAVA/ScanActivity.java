package fr.maoz.barscanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScanActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    public static String myscan;
    Bundle scanBundle;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        scanBundle=getIntent().getExtras();
        mScannerView = new ZBarScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(me.dm7.barcodescanner.zbar.Result result) {

        Log.v("kkkk", result.getContents());
        Log.v("uuuu", result.getBarcodeFormat().getName());

        // 0: scan de la réference en édition, 1:scan de la réference en recherche, 2: scan de l'emplacement
        if (scanBundle.getInt("ACTIVITY") ==1)
        {
            DisplayActivity.searchEdit.setText(result.getContents());}
        if (scanBundle.getInt("ACTIVITY") == 0)
        {
            EditActivity.editRef.setText(result.getContents());}

        if (scanBundle.getInt("ACTIVITY") == 2)
        {
            EditActivity.editEmp.setText(result.getContents());}
        onBackPressed();

    }
}