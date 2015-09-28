package com.sample.crud.contactmanger.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;

import com.orm.StringUtil;
import com.sample.crud.contactmanger.models.ContactModel;

public class DBUtils {
	public static void saveJob(Activity fragmentActivity, ContactModel model) {
		if (!isJobExist(model.getcreatedNo())) {
			model.save();

		} else {
			Log.e("Already exists", "" + isJobExist(model.getcreatedNo()));
		}
		/*
		 * ArrayList<ItemModel> items = model.getItems(); Log.e("Existss....",
		 * "" + items.size()); for (ItemModel item : items) { if
		 * (!isJobItemExist(item.getLookupCode(), item.getJobBookinNo())) {
		 * item.save(); } }
		 */
	}

	private static boolean isJobExist(String getcreatedNo) {
		String columnName = StringUtil.toSQLName("createdNo");
		long count = ContactModel.count(ContactModel.class,
				columnName + " = ?", new String[] { getcreatedNo });
		return count > 0;
	}

	public static ArrayList<ContactModel> getItemsBybookingNo(String bookingNo) {
		ArrayList<ContactModel> items = new ArrayList<ContactModel>();
		String columnName = StringUtil.toSQLName("createdNo");
		items.addAll(ContactModel.find(ContactModel.class, columnName + " = ?",
				bookingNo));
		return items;
	}

	public static ArrayList<ContactModel> getAllJobs() {
		ArrayList<ContactModel> list = new ArrayList<ContactModel>();
		List<ContactModel> jobList = ContactModel.listAll(ContactModel.class);

		for (ContactModel model : jobList) {
			list.add(model);
		}
		return list;
	}

}
