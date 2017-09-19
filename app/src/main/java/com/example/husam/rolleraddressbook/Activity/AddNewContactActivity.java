package com.example.husam.rolleraddressbook.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.husam.rolleraddressbook.Controller.ContactController;
import com.example.husam.rolleraddressbook.Model.Contact;
import com.example.husam.rolleraddressbook.R;

import io.realm.Realm;

public class AddNewContactActivity extends AppCompatActivity {

    private EditText firstnameInput = null,
                    lastNameInput = null,
                    emailAddressInput = null,
                    phoneNumberInput = null,
                    addressInput = null;
    private Button saveContactButton = null;
    private Realm realm = null;
    private Contact contact = null;

    private ContactController contactController = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeWidgets();
        initializeUtilities();

        //Button Action Listener...
        saveContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateInput()){
                    saveContact();
                }
            }
        });

    }

    private void initializeWidgets(){
        firstnameInput = (EditText) findViewById(R.id.firstnameField);
        lastNameInput = (EditText) findViewById(R.id.lastnameInput);
        emailAddressInput = (EditText) findViewById(R.id.emailAddressField);
        phoneNumberInput = (EditText) findViewById(R.id.phonenumberField);
        addressInput = (EditText) findViewById(R.id.addressInput);
        saveContactButton = (Button) findViewById(R.id.saveContactButton);
    }

    private void initializeUtilities(){
        realm = Realm.getDefaultInstance();
        contactController = new ContactController(realm);
    }

    private boolean validateInput(){
        //Clearing any previous errors which may have arose from failed attempts...
        firstnameInput.setError(null);
        lastNameInput.setError(null);
        emailAddressInput.setError(null);
        phoneNumberInput.setError(null);
        addressInput.setError(null);

        if(firstnameInput.getEditableText().toString().isEmpty()){
            firstnameInput.setError("Field must not be empty");
            return false;
        }

        if(lastNameInput.getEditableText().toString().isEmpty()){
            lastNameInput.setError("Field must not be empty");
            return false;
        }

        if(emailAddressInput.getEditableText().toString().isEmpty()){
            emailAddressInput.setError("Field must not be empty");
            return false;
        }

        if(phoneNumberInput.getEditableText().toString().isEmpty()){
            phoneNumberInput.setError("Field must not be empty");
            return false;
        }

        if(addressInput.getEditableText().toString().isEmpty()){
            addressInput.setError("Field must not be empty");
            return false;
        }

        return true;
    }

    private void saveContact(){
        contact = new Contact();

                String firstname = firstnameInput.getEditableText().toString().trim();
                String lastname = lastNameInput.getEditableText().toString().trim();
                String emailAddress = emailAddressInput.getEditableText().toString().trim();
                String phoneNumber = phoneNumberInput.getEditableText().toString().trim();
                String address = addressInput.getEditableText().toString();

                contact.setFirstName(firstname);
                contact.setLastName(lastname);
                contact.setEmailAddress(emailAddress);
                contact.setPhoneNumber(phoneNumber);
                contact.setAddress(address);

        contactController.createContact(contact);

        Toast.makeText(this, "Contact added", Toast.LENGTH_SHORT).show();
        finish();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
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
