package com.example.husam.rolleraddressbook.Controller;

import android.os.Bundle;
import android.util.Log;
import com.example.husam.rolleraddressbook.Model.Contact;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by husam on 9/19/17.
 */


/**
 * Contains Realm transaction methods for Contacts... (Creation and reading only at the moment)...
 */

public class ContactController {

    //Indices to use as references when querying Realm...
    public static final String INDEX_CONTACT_ID = "_id";
    public static final String INDEX_CONTACT_FIRSTNAME = "firstName";
    public static final String INDEX_CONTACT_LASTNAME = "lastName";
    public static final String INDEX_CONTACT_EMAILADDRESS = "emailAddress";
    public static final String INDEX_CONTACT_ADDRESS = "address";
    public static final String INDEX_CONTACT_PHONENUMBER = "phoneNumber";


    Realm realm = null;

    //Constructor...
    public ContactController(Realm realm){
        this.realm = realm;
    }

    public void createContact(final Contact contact){


        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                //Contact bgRealmObject = bgRealm.createObject(Contact.class);

                // Get the current max id in the contacts table
                Number maxId = bgRealm.where(Contact.class).max("_id");
                // If there are no rows, maxId is null, so the next id must be 1
                // If maxId is not null, increment it by 1
                int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                // Contact object created with the new Primary key
                Contact bgRealmObject = bgRealm.createObject(Contact.class, String.valueOf(nextId));
                // Updating object with new credentials...
                // The object will be automatically saved in the database when the execute method ends
                // ...
                // ...

                bgRealmObject.setFirstName(contact.getFirstName());
                bgRealmObject.setLastName(contact.getLastName());
                bgRealmObject.setEmailAddress(contact.getEmailAddress());
                bgRealmObject.setPhoneNumber(contact.getPhoneNumber());
                bgRealmObject.setAddress(contact.getAddress());

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.v("Contact added: ", contact.toString());
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.e("Error", error.getMessage());
            }
        });
    }


    public Contact[] readContacts(int id){
        //Build the query looking at all Contacts..
        RealmQuery<Contact> query = realm.where(Contact.class);
        //If an actual ID has been passed as a parameter
        //it modifies the query to return only the row which ID is equal to the parameter given...
        if(id > 0){
            query.equalTo(INDEX_CONTACT_ID, id);
        }

        // Execute the query:
        RealmResults<Contact> result = query.findAll();
        Contact[] contacts = new Contact[result.size()];
        for(int i =0; i < result.size(); i++){
            contacts[i] = result.get(i);
        }

        return contacts;
    }

    //Helper methods to convert Contact from Bundle to ContactClass form and vice-versa...
    public static Bundle contactBundle(Contact contact){
        Bundle contactBundle = new Bundle();
        contactBundle.putInt(INDEX_CONTACT_ID, contact.get_id());
        contactBundle.putString(INDEX_CONTACT_FIRSTNAME, contact.getFirstName());
        contactBundle.putString(INDEX_CONTACT_LASTNAME, contact.getLastName());
        contactBundle.putString(INDEX_CONTACT_EMAILADDRESS, contact.getEmailAddress());
        contactBundle.putString(INDEX_CONTACT_ADDRESS, contact.getAddress());
        contactBundle.putString(INDEX_CONTACT_PHONENUMBER, contact.getPhoneNumber());

        return contactBundle;

    }

    public static Contact contactInformationFromBundle(Bundle contactBundle){
        Contact contactInformation = new Contact();
        contactInformation.set_id(contactBundle.getInt(INDEX_CONTACT_ID));
        contactInformation.setFirstName(contactBundle.getString(INDEX_CONTACT_FIRSTNAME));
        contactInformation.setLastName(contactBundle.getString(INDEX_CONTACT_LASTNAME));
        contactInformation.setEmailAddress(contactBundle.getString(INDEX_CONTACT_EMAILADDRESS));
        contactInformation.setPhoneNumber(contactBundle.getString(INDEX_CONTACT_PHONENUMBER));
        contactInformation.setAddress(contactBundle.getString(INDEX_CONTACT_ADDRESS));

        return contactInformation;

    }

}
