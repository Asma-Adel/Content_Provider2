package com.example.contentprovider2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputText = findViewById(R.id.textView1);
        fetchContacts();
    }

    public void fetchContacts(){

        String phoneNum = null;
        String email = null;
        Uri CONTACT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUM = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri Phone_Contact_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_Contact_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        Uri EMAIL_CONTACT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String Email_Contact_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;

        StringBuffer output = new StringBuffer();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CONTACT_URI , null ,null , null , null );

        if (cursor.getCount() > 0)
        {
            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));

                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));

                int hasPhoneNum = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUM)));

                if (hasPhoneNum > 0) {
                    output.append("\n First Name : " + name);

                    Cursor phoneCursor = contentResolver.query(Phone_Contact_URI, null, Phone_Contact_ID + " = ? ",
                            new String[]{contact_id}, null);
                    while (phoneCursor.moveToNext()) {
                        phoneNum = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        output.append("\n Phone Number : " + phoneNum);
                        phoneCursor.close();


                        Cursor emailCursor = contentResolver.query(EMAIL_CONTACT_URI, null, Email_Contact_ID + " = ? ",
                                new String[]{contact_id}, null);

                        while (emailCursor.moveToNext()) {
                            email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
                            output.append("\n Email : " + email);
                        }
                        emailCursor.close();
                    }
                    output.append("\n");
                }
                outputText.setText(output);

            }
        }


    }
}
