package com.google.android.gms.samples.vision.barcodereader.ui.camera;

import com.google.android.gms.samples.vision.barcodereader.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class GenerateQRCodeActivity extends Activity implements OnClickListener{

    private String qrInputText;
    private Bitmap bitmap;
    private Button saveQR;
    private static final String IMAGE_DIRECTORY = "/QRCodeScanner";
    private String copiedText;
    private EditText qrInput;
    private String completeProfile;
    private Button shareQR;

    private String LOG_TAG = "GenerateQRCode";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generator);

         qrInput = (EditText) findViewById(R.id.qrInput);

            Bundle bundle1 = getIntent().getExtras();
            copiedText = bundle1.getString("str");

            Bundle bundle2=getIntent().getExtras();
            completeProfile = bundle2.getString("details");


        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);

        shareQR=(Button)findViewById(R.id.shareQR);
        shareQR.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareImage();
            }
        });
        saveQR=(Button) findViewById(R.id.saveQR);
        saveQR.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage(bitmap);
            }
        });
    }

    public String saveImage(Bitmap bitmap1){

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {
            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();   //give read write permission
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
            Toast.makeText(getApplicationContext(),"File Saved::--->" + f.getAbsolutePath(),Toast.LENGTH_LONG).show();

            saveQR.setClickable(false);
            // Intent intent =new Intent(GenerateQRCodeActivity.this,Start.class);
            //startActivity(intent);

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


        return "";

    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button1:
                //if (copiedText=="a") {
                    qrInputText = qrInput.getText().toString();
                    Log.v(LOG_TAG, qrInputText);

                    //Find screen size
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;

                    //Encode with a QR Code image
                    QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                            null,
                            Contents.Type.TEXT,
                            BarcodeFormat.QR_CODE.toString(),
                            smallerDimension);
                    try {
                        bitmap = qrCodeEncoder.encodeAsBitmap();
                        ImageView myImage = (ImageView) findViewById(R.id.imageView1);
                        myImage.setImageBitmap(bitmap);
                        saveQR.setClickable(true);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                    break;
               // }
                /*else {
                    qrInputText=copiedText;
                    qrInput.setClickable(false);

                    Log.v(LOG_TAG, qrInputText);

                    //Find screen size
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;

                    //Encode with a QR Code image
                    QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                            null,
                            Contents.Type.TEXT,
                            BarcodeFormat.QR_CODE.toString(),
                            smallerDimension);
                    try {
                        bitmap = qrCodeEncoder.encodeAsBitmap();
                        ImageView myImage = (ImageView) findViewById(R.id.imageView1);
                        myImage.setImageBitmap(bitmap);
                        saveQR.setClickable(true);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                    break;
                }*/

        }
    }


    public void Check(){

        if (copiedText=="a") {
            copiedText=null;
            qrInputText = qrInput.getText().toString();
            Log.v(LOG_TAG, qrInputText);

            //Find screen size
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            //Encode with a QR Code image
            QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                    null,
                    Contents.Type.TEXT,
                    BarcodeFormat.QR_CODE.toString(),
                    smallerDimension);
            try {
                bitmap = qrCodeEncoder.encodeAsBitmap();
                ImageView myImage = (ImageView) findViewById(R.id.imageView1);
                myImage.setImageBitmap(bitmap);
                saveQR.setClickable(true);

            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        else {
            qrInputText=copiedText;
            qrInput.setClickable(false);

            Log.v(LOG_TAG, qrInputText);

            //Find screen size
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            //Encode with a QR Code image
            QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                    null,
                    Contents.Type.TEXT,
                    BarcodeFormat.QR_CODE.toString(),
                    smallerDimension);
            try {
                bitmap = qrCodeEncoder.encodeAsBitmap();
                ImageView myImage = (ImageView) findViewById(R.id.imageView1);
                myImage.setImageBitmap(bitmap);
                saveQR.setClickable(true);

            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

    }

    public void ShareImage(){
        File cache = getApplicationContext().getExternalCacheDir();
        File sharefile = new File(cache, "toshare.png");
        try {
            FileOutputStream out = new FileOutputStream(sharefile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {

        }
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + sharefile));
        try {
            startActivity(Intent.createChooser(share, "Choose App to Share..."));
        } catch (Exception e) {

        }
    }

}