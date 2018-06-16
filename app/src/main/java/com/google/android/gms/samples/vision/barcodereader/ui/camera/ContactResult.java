package com.google.android.gms.samples.vision.barcodereader.ui.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.samples.vision.barcodereader.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class ContactResult extends Activity {

    private TextView contactDetails;
    private ImageView contactQR;

    private Bitmap bitmap;

    private static final String IMAGE_DIRECTORY = "/QRCodeScanner";
    private Button save;
    private Button shareQR;

    private  String contactDetailsString;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contect_result);

        contactDetails=(TextView)findViewById(R.id.stringResult);
        contactQR=(ImageView) findViewById(R.id.contactQR);
        contactDetails.setMovementMethod(new ScrollingMovementMethod());


        save=(Button)findViewById(R.id.saveContactQR);

        shareQR=(Button)findViewById(R.id.shareQR);
        Bundle bundle= getIntent().getExtras();
        contactDetailsString = bundle.getString("contact");

        contactDetails.setText(contactDetailsString);

        contactDetails.setText(contactDetailsString);

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        //Encode with a QR Code image
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(contactDetailsString,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            bitmap = qrCodeEncoder.encodeAsBitmap();
            ImageView myImage = (ImageView) findViewById(R.id.contactQR);
            myImage.setImageBitmap(bitmap);
            contactQR.setClickable(true);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        shareQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareImage();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
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

            save.setClickable(false);
            // Intent intent =new Intent(GenerateQRCodeActivity.this,Start.class);
            //startActivity(intent);

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


        return "";

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
