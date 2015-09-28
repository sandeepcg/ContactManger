package com.sample.crud.contactmanger.fragments;

import com.sample.crud.contactmanger.R;
import com.sample.crud.contactmanger.models.ContactModel;
import com.sample.crud.contactmanger.utils.DBUtils;
import com.sample.crud.contactmanger.utils.Utilities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class EntryFragment extends BaseFragment implements OnClickListener {
    EditText name, email, number;
    FloatingActionButton submit;
    Button delete;
    String id;
    Toolbar tool;
    boolean existngEntry;
    EditText errorEditTxt;


    public EntryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container,
                false);
        setupParentUI(rootView);
        findviewByids(rootView);
        if (getArguments() == null) {
            existngEntry = false;
            delete.setVisibility(View.INVISIBLE);
            tool.setTitle(getString(R.string.add_contact));
            // new entry
        } else {
            existngEntry = true;
            Bundle b = getArguments();
            id = b.getString(Utilities.CID);
            tool.setTitle(getString(R.string.edit_contact));
            filldata(id);
            delete.setVisibility(View.VISIBLE);
        }
        submit.setOnClickListener(this);
        delete.setOnClickListener(this);
        return rootView;
    }

    private void filldata(String id) {
        ContactModel model = ContactModel.findById(ContactModel.class,
                Long.parseLong(id));
        name.setText(model.getcontactName());
        number.setText(model.getcreatedNo());
        email.setText(model.getcontactEmail());
    }

    private void findviewByids(View rootView) {
        tool = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(tool);

        tool.setTitleTextColor(getResources().getColor(android.R.color.white));
        name = (EditText) rootView.findViewById(R.id.editText1);
        email = (EditText) rootView.findViewById(R.id.editText2);
        number = (EditText) rootView.findViewById(R.id.editText3);
        submit = (FloatingActionButton) rootView.findViewById(R.id.button1);
        delete = (Button) rootView.findViewById(R.id.button2);
    }

    @SuppressWarnings("static-access")
    @Override
    public void onClick(View v) {
        Utilities.hideSoftKeyboard(getActivity());
        switch (v.getId()) {
            case R.id.button1:
                if (!existngEntry) {
                    validate(existngEntry);
                } else {
                    validate(existngEntry);
                }
                break;

            case R.id.button2:
                ContactModel m = new ContactModel();
                m.deleteAll(ContactModel.class, "id =?", id);
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }

    }

    private void validate(boolean existngEntry) {
        boolean isvalid = DoFirstNameValidation();
        if (!isvalid)
            return;
        isvalid = doEmailIdValidation();
        if (!isvalid)
            return;

        isvalid = DoPhoneNumberValidation();
        if (!isvalid)
            return;
        if (isvalid) {
            if (!existngEntry) {
                ContactModel model = new ContactModel();
                model.setcontactName(name.getText().toString());
                model.setcontactNo(number.getText().toString().trim());
                model.setcontactEmail(email.getText().toString().trim());
                DBUtils.saveJob(getActivity(), model);
            } else {
                ContactModel model = ContactModel.findById(ContactModel.class,
                        Long.parseLong(id));
                model.setcontactEmail(email.getText().toString().trim());
                model.setcontactName(name.getText().toString().trim());
                model.setcontactNo(number.getText().toString());
                model.save();
            }

            getActivity().getSupportFragmentManager().popBackStack();
        }

    }

    private boolean DoPhoneNumberValidation() {
        String fNameString = number.getText().toString();
        if (fNameString.length() == 0) {
            number
                    .setError(Html
                            .fromHtml("<font color='white'>Enter number to continue</font>"));
            errorEditTxt = number;
            number.addTextChangedListener(registerInputTextWatcher);
            number.requestFocus();
            return false;
        } else {
            return true;
        }

    }

    private boolean doEmailIdValidation() {
        String emailString = email.getText().toString();
        if (!Utilities.isValidEmail(emailString)) {
            email
                    .setError(Html
                            .fromHtml("<font color='white'>Enter a valid email to continue</font>"));
            errorEditTxt = email;
            email.addTextChangedListener(registerInputTextWatcher);
            email.requestFocus();
            return false;
        } else {
            return true;
        }

    }

    private boolean DoFirstNameValidation() {
        String fNameString = name.getText().toString();
        if (fNameString.length() == 0) {
            name
                    .setError(Html
                            .fromHtml("<font color='white'>Enter a name to continue</font>"));
            errorEditTxt = name;
            name.addTextChangedListener(registerInputTextWatcher);
            name.requestFocus();
            return false;
        } else {
            return true;
        }

    }

    TextWatcher registerInputTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
