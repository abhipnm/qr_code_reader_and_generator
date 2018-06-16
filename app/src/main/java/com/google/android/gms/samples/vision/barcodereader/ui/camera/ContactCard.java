package com.google.android.gms.samples.vision.barcodereader.ui.camera;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.samples.vision.barcodereader.R;

import java.util.regex.Matcher;

public class ContactCard extends Activity {

    private EditText name;
    private EditText mail;
    private EditText mobile;
    private EditText whatsapp;
    private EditText linkedin;
    private EditText github;
    private CheckBox check;
    private Button create;

    private String fullName;
    private String email;
    private String mobileNumber;
    private String whatsappNumber;
    private String linkedInLink;
    private String githubLink;
    private String complete;
    private boolean nameB=false,mailB=true,mobileB=false,whatsappB=true,linkedB=true,gitB=true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_card);

        name=(EditText)findViewById(R.id.fullName);
        mail=(EditText)findViewById(R.id.email);
        mobile=(EditText)findViewById(R.id.mobileNumber);
        whatsapp=(EditText)findViewById(R.id.whatsapp);
        linkedin=(EditText)findViewById(R.id.linkedin);
        github=(EditText)findViewById(R.id.github);
        check=(CheckBox)findViewById(R.id.check);
        create=(Button)findViewById(R.id.create);



        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check.isChecked()) {
                    fullName = name.getText().toString();
                    email = mail.getText().toString();
                    mobileNumber = mobile.getText().toString();
                    whatsappNumber = whatsapp.getText().toString();
                    linkedInLink = linkedin.getText().toString();
                    githubLink = github.getText().toString();

                    //Check if name is Given or not in the name edittext
                    if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(mobileNumber)) {
                        nameB=false;
                        AlertDialog alertDialog = new AlertDialog.Builder(ContactCard.this).create();
                        alertDialog.setTitle("Name and Mobile Number fields are Mandatory.");
                        alertDialog.setIcon(R.drawable.alert);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.show();
                    }
                    else if(mobileNumber.length()!=10){
                        mobileB=false;
                        AlertDialog alertDialog = new AlertDialog.Builder(ContactCard.this).create();
                        alertDialog.setTitle("Mobile Number is invalid");
                        alertDialog.setIcon(R.drawable.alert);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.show();
                    }
                    else{
                        nameB=true;
                        mobileB=true;
                    }


                    if (TextUtils.isEmpty(whatsappNumber)) {
                        whatsappNumber = " ";
                    }

                    if (TextUtils.isEmpty(linkedInLink)) {
                        linkedInLink = " ";
                    }

                    if (TextUtils.isEmpty(githubLink)) {
                        githubLink = " ";
                    }




                    if(whatsappNumber!=" ") {
                        if (whatsappNumber.length() != 10) {
                            whatsappB=false;
                            AlertDialog alertDialog = new AlertDialog.Builder(ContactCard.this).create();
                            alertDialog.setTitle("WhatsApp Number is invalid");
                            alertDialog.setIcon(R.drawable.alert);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alertDialog.show();
                        }
                        else{
                            whatsappB=true;
                        }
                    }
                    else {
                        whatsappB=true;
                    }

                    if(linkedInLink.length()>1){
                        if(isValidURL(linkedInLink)){

                            linkedB=true;
                        }
                        else{
                            linkedB=false;
                            AlertDialog alertDialog = new AlertDialog.Builder(ContactCard.this).create();
                            alertDialog.setTitle("LinkedIn link is invalid.");
                            alertDialog.setIcon(R.drawable.alert);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alertDialog.show();
                        }
                    }

                    if(githubLink.length()>1){
                        if(isValidURL(githubLink)){

                            gitB=true;
                        }
                        else{
                            gitB=false;
                            AlertDialog alertDialog = new AlertDialog.Builder(ContactCard.this).create();
                            alertDialog.setTitle("Github link is invalid.");
                            alertDialog.setIcon(R.drawable.alert);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alertDialog.show();
                        }
                    }

                    if (!(TextUtils.isEmpty(email)))

                    {
                        if (isvalidMail(email)) {

                            mailB=true;
                        } else {
                            mailB=false;
                            AlertDialog alertDialog = new AlertDialog.Builder(ContactCard.this).create();
                            alertDialog.setTitle("E-Mail Address is Invalid.");
                            alertDialog.setIcon(R.drawable.alert);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alertDialog.show();
                        }
                    }
                }
                else {
                    nameB=false;
                    AlertDialog alertDialog = new AlertDialog.Builder(ContactCard.this).create();
                    alertDialog.setTitle("Please check the terms and conditions checkbox.");
                    alertDialog.setIcon(R.drawable.alert);
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                }

                if(nameB && mailB && mobileB && whatsappB && linkedB && gitB){
                completeStringContact();}

            }

        });



    }

        public boolean isvalidMail(String email){
            Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(email);

            return matcher.matches();
        }
        public boolean isValidURL(String linkedInLink){
            Matcher mat=Patterns.WEB_URL.matcher(linkedInLink);
            return mat.matches();
        }

        public void completeStringContact(){

        complete="Name : "+fullName+"\n\nEmail ID : "+email+"\n\nMobile : "+mobileNumber+"\n\nWhatsApp Number : "+whatsappNumber+"\n\nLinkedIn Link : "+linkedInLink+"\n\nGithub Link : "+githubLink;
            //Toast.makeText(getApplicationContext(), complete, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ContactCard.this,ContactResult.class);
            intent.putExtra("contact",complete);
            startActivity(intent);
        }
    }