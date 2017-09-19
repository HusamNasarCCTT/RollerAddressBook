package com.example.husam.rolleraddressbook.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.husam.rolleraddressbook.Controller.ContactController;
import com.example.husam.rolleraddressbook.Model.Contact;
import com.example.husam.rolleraddressbook.R;

public class ContactActivity extends AppCompatActivity {


    private EditText firstnameField = null,
                    lastnameField = null,
                    emailAddressField = null,
                    addressField = null,
                    phonenumberField = null;

    private Bundle contactBundle = null;
    private Contact contactInformation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeWidgets();

        /**Get Contact bundle from intent, extract contact information from it and store it in instance of Contact
         *for the sake of clarity...
         **/
        initializeUtilities();

        updateUI();

    }


    private void initializeWidgets(){

        firstnameField = (EditText) findViewById(R.id.firstnameField);
        lastnameField = (EditText) findViewById(R.id.lastnameField);
        emailAddressField = (EditText) findViewById(R.id.emailAddressField);
        addressField = (EditText) findViewById(R.id.addressField);
        phonenumberField = (EditText) findViewById(R.id.phonenumberField);

        //Rendering the fields uneditable...
        firstnameField.setEnabled(false);
        lastnameField.setEnabled(false);
        emailAddressField.setEnabled(false);
        addressField.setEnabled(false);
        phonenumberField.setEnabled(false);


    }

    private void initializeUtilities(){
        contactBundle = this.getIntent().getExtras();
        contactInformation = ContactController.contactInformationFromBundle(contactBundle);
    }

    public void updateUI(){
        //Set title with contact name...
        getSupportActionBar().setTitle(contactInformation.getFirstName() + " " + contactInformation.getLastName());

        //Populate fields with contact information...
        firstnameField.setText(contactInformation.getFirstName());
        lastnameField.setText(contactInformation.getLastName());
        emailAddressField.setText(contactInformation.getEmailAddress());
        addressField.setText(contactInformation.getAddress());
        phonenumberField.setText(contactInformation.getPhoneNumber());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:

                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
