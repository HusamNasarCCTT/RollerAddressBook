package com.example.husam.rolleraddressbook.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.husam.rolleraddressbook.Adapter.ContactAdapter;
import com.example.husam.rolleraddressbook.Controller.ContactController;
import com.example.husam.rolleraddressbook.Model.Contact;
import com.example.husam.rolleraddressbook.R;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private ListView contactList = null;
    private Button addNewContactButton = null;


    private Intent intent = null;
    private Realm realm = null;
    private ContactController contactController = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Execute widget initialization method...
        initializeWidgets();

        //Realm and Controller initialization...
        initializeUtilities();

        //Button Action listener...
        addNewContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddNewContactActivity();
            }
        });

        //List Item Action listener...
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Get Contact information from ContactAdapter...
                Contact contact = (Contact) adapterView.getItemAtPosition(i);
                launchContactActivity(contact);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateContactList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Closing realm in case Activity is destroyed to free memory...
        realm.close();
    }

    private void initializeWidgets(){

        contactList = (ListView) findViewById(R.id.contactList);

        LinearLayout emptyListView = (LinearLayout) findViewById(R.id.empty_list_view);
        contactList.setEmptyView(emptyListView);

        addNewContactButton = (Button) findViewById(R.id.addNewContactButton);


    }

    private void initializeUtilities(){
        realm = Realm.getDefaultInstance();
        contactController = new ContactController(realm);
    }

    private void launchAddNewContactActivity(){

        //Standard intent preparation...
        intent = new Intent(this, AddNewContactActivity.class);
        startActivity(intent);
    }

    private void updateContactList(){

        Contact[] contacts = contactController.readContacts(0);
        ContactAdapter adapter = new ContactAdapter(this, contacts);
        contactList.setAdapter(adapter);

    }

    private void launchContactActivity(Contact contact){
        intent = new Intent(this, ContactActivity.class);

        Bundle contactBundle = ContactController.contactBundle(contact);
        intent.putExtras(contactBundle);
        startActivity(intent);
    }


}
