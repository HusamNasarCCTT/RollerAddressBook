package com.example.husam.rolleraddressbook.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.husam.rolleraddressbook.Model.Contact;
import com.example.husam.rolleraddressbook.R;

/**
 * Created by husam on 9/19/17.
 */



public class ContactAdapter extends ArrayAdapter<Contact> {

    public ContactAdapter(@NonNull Context context, @NonNull Contact[] objects) {
        super(context, R.layout.contact_row, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());

        View row = inflater.inflate(R.layout.contact_row, parent, false);

        //Getting references to Row Elements
        TextView contactName = (TextView) row.findViewById(R.id.contactName);

        //Setting data to String variables for Display on TextViews...

        int contactId = getItem(position).get_id();
        String contactFirstName = getItem(position).getFirstName();
        String contactLastName = getItem(position).getLastName();
        String contactAddress = getItem(position).getAddress();
        String contactEmailAddress = getItem(position).getEmailAddress();
        String contactPhoneNumber = getItem(position).getPhoneNumber();

        contactName.setText(contactFirstName + " " + contactLastName);


        return row;
    }
}
