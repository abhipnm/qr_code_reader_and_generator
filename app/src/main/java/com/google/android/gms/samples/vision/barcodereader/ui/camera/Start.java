package com.google.android.gms.samples.vision.barcodereader.ui.camera;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.samples.vision.barcodereader.R;


public class Start extends Activity {
    private Button scan;
    private Button create;
    private Button selectecText;
    private Button contactCard;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        scan=(Button)findViewById(R.id.scan);
        create=(Button)findViewById(R.id.create);
        selectecText = (Button)findViewById(R.id.selectedText);
        contactCard=(Button) findViewById(R.id.contact);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Start.this,MainActivity.class);
                startActivity(intent);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Start.this,GenerateQRCodeActivity.class);
                intent.putExtra("str","a");
                startActivity(intent);
            }
        });

        selectecText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String str = (clipboardManager.getText()).toString();
                //Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Start.this,CopiedTextQRGenerator.class);
                intent.putExtra("str",str);

                startActivity(intent);
               // Intent intent=new Intent(Start.this,GenerateQRCodeActivity.class);
                //startActivity(intent);
            }
        });

        contactCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call Create Contact Class
                Intent intent = new Intent(Start.this,ContactCard.class);
                startActivity(intent);
            }
        });
    }
}
