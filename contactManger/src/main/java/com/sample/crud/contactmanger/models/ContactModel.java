package com.sample.crud.contactmanger.models;

import java.util.ArrayList;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.sample.crud.contactmanger.utils.DBUtils;

public class ContactModel extends SugarRecord<ContactModel> {
	// private String cid;
	private String contactName;
	private String contactEmail;
	private String createdNo;

	public ContactModel() {
		super();
	}

	public String getcontactName() {
		return contactName;
	}

	public String getcontactEmail() {
		return contactEmail;
	}

	public String getcreatedNo() {
		return createdNo;
	}

	public void setcontactName(String contactName) {
		this.contactName = contactName;
	}

	public void setcontactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public void setcontactNo(String createdNo) {
		this.createdNo = createdNo;
	}

	@Ignore
	private ArrayList<ContactModel> items;

	public ArrayList<ContactModel> getItems() {
		return items;
	}

	public void setItems(ArrayList<ContactModel> items) {
		this.items = items;
	}
	public ArrayList<ContactModel> getItemsList(String name) {
		items = DBUtils.getItemsBybookingNo(name);
		return items;
	}
}
