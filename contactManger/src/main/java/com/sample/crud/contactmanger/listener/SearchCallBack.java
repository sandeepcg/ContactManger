package com.sample.crud.contactmanger.listener;

import com.sample.crud.contactmanger.models.ContactModel;

import java.util.ArrayList;

/**
 * Created by Sandeep on 9/28/2015.
 */
public interface SearchCallBack {
    void isRunning(boolean isrunning);
    void onProgressUpdate(ContactModel cModel, int progress, int total);
    void completed(ArrayList arrayList);
}
