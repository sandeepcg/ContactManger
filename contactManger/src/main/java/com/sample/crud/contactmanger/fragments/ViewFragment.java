package com.sample.crud.contactmanger.fragments;

import java.util.ArrayList;
import java.util.HashSet;

import com.sample.crud.contactmanger.MainActivity;
import com.sample.crud.contactmanger.R;
import com.sample.crud.contactmanger.adapters.ContactListAdapter;
import com.sample.crud.contactmanger.aynctasks.SearchTask;
import com.sample.crud.contactmanger.listener.RecycleItemListener;
import com.sample.crud.contactmanger.listener.SearchCallBack;
import com.sample.crud.contactmanger.models.ContactModel;
import com.sample.crud.contactmanger.utils.DBUtils;
import com.sample.crud.contactmanger.utils.Utilities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ViewFragment extends BaseFragment implements View.OnClickListener {
    RecyclerView lv;
    FloatingActionButton add;
    ContactListAdapter clist;
    Toolbar tool;
    ArrayList<ContactModel> listData = new ArrayList<ContactModel>();
    SearchCallBack callback;
    boolean search = false;
    Context con;

    public ViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container,
                false);
        con = getActivity();
        setupParentUI(rootView);
        findviewByids(rootView);
        //lv.setOnItemClickListener(itemClick);
        add.setOnClickListener(this);
        fetchContacts();
        callback = new SearchCallBack() {
            @Override
            public void isRunning(boolean isrunning) {
                search = isrunning;
            }

            @Override
            public void onProgressUpdate(ContactModel cModel, int progress, int total) {

            }

            @Override
            public void completed(ArrayList arrayList) {
                Log.e("Completed....", "" + arrayList.size());
                if (arrayList.size() > 0) {
                    // add elements to al, including duplicates
                    //removing duplicates
                    HashSet hs = new HashSet();
                    hs.addAll(arrayList);
                    arrayList.clear();
                    listData.clear();
                    listData.addAll(hs);
                    clist = new ContactListAdapter(getActivity(), listData, recycleitemClick);
                    lv.setAdapter(clist);
                } else {
                    listData.clear();
                    // listData=arrayList;
                    clist = new ContactListAdapter(getActivity(), listData, recycleitemClick);
                    lv.setAdapter(clist);
                }
            }
        };
        return rootView;
    }

    RecycleItemListener recycleitemClick = new RecycleItemListener() {
        @Override
        public void itemClicked(int pos) {
            Log.e("Postion", "...." + pos);
            Bundle b = new Bundle();
            b.putString(Utilities.CID, listData.get(pos).getId().toString());
            Fragment f = new EntryFragment();
            f.setArguments(b);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null).replace(R.id.container, f).commit();
        }
    };

    private void fetchContacts() {
        listData = DBUtils.getAllJobs();
        clist = new ContactListAdapter(getActivity(), listData, recycleitemClick);
        lv.setAdapter(clist);

    }

    private void findviewByids(View rootView) {
        tool = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(tool);
        tool.setTitle(getString(R.string.contacts));
        tool.setTitleTextColor(getResources().getColor(android.R.color.white));
        lv = (RecyclerView) rootView.findViewById(R.id.listView1);
        lv.setLayoutManager(new LinearLayoutManager(getActivity()));
        add = (FloatingActionButton) rootView.findViewById(R.id.addButton);
        lv.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {

                Log.e("holder.getAdapterPosition()", "...." + holder.getAdapterPosition());
            }
        });
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("search query submit");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("search query " + newText);
                if (newText.length() > 0) {
                    if (callback != null) {
                        if (!search) {
                            new SearchTask(con, callback, newText).execute();
                        }
                    }
                    return false;
                } else {
                    listData = DBUtils.getAllJobs();
                    clist = new ContactListAdapter(getActivity(), listData, recycleitemClick);
                    lv.setAdapter(clist);
                }
                return false;

            }
        });


    }

    OnItemClickListener itemClick = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Bundle b = new Bundle();
            b.putString(Utilities.CID, listData.get(position).getId().toString());
            Fragment f = new EntryFragment();
            f.setArguments(b);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null).replace(R.id.container, f).commit();
        }
    };

    public void onResume() {
        super.onResume();
        listData = DBUtils.getAllJobs();
        if (!(clist == null)) {
            clist.notifyDataSetChanged();
        }
    }

    ;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addButton) {
            Fragment f = new EntryFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null).replace(R.id.container, f).commit();
        }
    }

}
