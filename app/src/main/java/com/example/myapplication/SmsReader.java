package com.example.myapplication;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsReader {
    private static final String TAG = SmsReader.class.getSimpleName();

    public String readSms(Context context) {
        // Create Inbox box URI
        Uri inboxURI = Uri.parse("content://sms/inbox");
        ContentResolver cr = context.getContentResolver();
        Cursor c = null;
        try {
            c = cr.query(inboxURI, null, "date > " + new SimpleDateFormat("dd.MM.yyyy").parse("20.03.2020").getTime(), null, null);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int totalSMS = 0;
        if (c != null) {
            totalSMS = c.getCount();
            if (c.moveToFirst()) {

                for (int j = 0; j < totalSMS; j++) {
                    StringBuilder message = new StringBuilder();
                    String smsDateLong = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE));
                    Date smsDate = new Date(Long.valueOf(smsDateLong));
                    String number = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                    String body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY));
                    message.append(j + ": Date = " + new SimpleDateFormat("dd.MM.yyyy").format(smsDate) + "; ");
                    message.append("Number = " + number + "; ");
                    message.append("Body = " + body + "; ");
                    Log.i(TAG, message.toString());
                    c.moveToNext();
                }
            }
        }
        return String.valueOf(totalSMS);
    }
}
