package com.sample.crud.contactmanger.aynctasks;

import android.content.Context;
import android.os.AsyncTask;

import com.orm.StringUtil;
import com.sample.crud.contactmanger.listener.SearchCallBack;
import com.sample.crud.contactmanger.models.ContactModel;

import java.util.ArrayList;

/**
 * Created by Sandeep on 9/28/2015.
 */
public class SearchTask extends AsyncTask<Void, Object, ArrayList> {
    private Context mContext;
    private SearchCallBack callback;
    private String jsonResponse;
    private ArrayList<ContactModel> list;
    private int length;

    public SearchTask(Context mContext, SearchCallBack callback,
                      String jsonResponse) {
        this.mContext = mContext;
        this.callback = callback;
        this.jsonResponse = jsonResponse;
    }


    @Override
    protected ArrayList doInBackground(Void... params) {
        if (callback != null) {
            callback.isRunning(true);
        }
        ArrayList<ContactModel> items = new ArrayList<ContactModel>();
        String columnNo = StringUtil.toSQLName("createdNo");
        String columnName = StringUtil.toSQLName("contactName");
        String columnEmail = StringUtil.toSQLName("contactEmail");
        //searching all fields with given string
        items.addAll(ContactModel.find(ContactModel.class, columnName + " = ?",
                jsonResponse));
        items.addAll(ContactModel.find(ContactModel.class, columnName + " = ?",
                jsonResponse));
        items.addAll(ContactModel.find(ContactModel.class, columnEmail + " = ?",
                jsonResponse));

        return items;
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        super.onPostExecute(arrayList);
        if (callback != null) {
            callback.isRunning(false);
            callback.completed(arrayList);
        }
    }
}
