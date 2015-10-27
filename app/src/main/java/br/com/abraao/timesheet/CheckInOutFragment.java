package br.com.abraao.timesheet;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.abraao.timesheet.business.EntryBusiness;
import br.com.abraao.timesheet.entities.Entry;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 */
public class CheckInOutFragment extends Fragment implements AbsListView.OnItemClickListener, BackPressListerner {

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    private Entry selected;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;
    private ViewGroup mContainer;
    private boolean openEditing = false;

    // TODO: Rename and change types of parameters
    public static CheckInOutFragment newInstance() {
        CheckInOutFragment fragment = new CheckInOutFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CheckInOutFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;

        view = inflater.inflate(R.layout.fragment_checkinout_list, container, false);

        List<Entry> list = EntryBusiness.listByDate();

        if(list != null && !list.isEmpty()) {


            Entry enfim = new Entry();
            enfim.startHour = -1;

            list.add(enfim);

            mAdapter = new EntryAdapter(getContext(), list);
        }

        this.mContainer = container;
        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.entries_list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        final FloatingActionButton btnCheckIn = (FloatingActionButton) view.findViewById(R.id.btn_checkin);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckIn(v);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        /*if(!this.openEditing) {
            this.client = null;
        }

        this.openEditing = false;*/
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        this.selected = (Entry) parent.getItemAtPosition(position);
        this.refreshView();

    }

    public void onCheckIn(View v) {
        EntryBusiness.checkIn();
        this.refreshView();
    }

    public void onCheckOut(View v) {
        EntryBusiness.checkOut();
        this.refreshView();
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    @Override
    public void backPressed() {
        selected = null;
        this.refreshView();
    }

    private void refreshView() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(this);
        fragmentTransaction.attach(this);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //fragmentTransaction.addToBackStack(ClientListFragment.EDIT_CLIENT);
        fragmentTransaction.commit();
    }

    public class EntryAdapter extends ArrayAdapter<Entry> {

        long totalHours = 0;
        long totalMins = 0;
        int viewLocal = 0;

        public EntryAdapter(Context context, List<Entry> entries) {
            super(context, 0, entries);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Entry entry = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view

            if(entry == null)
                entry = new Entry();

            if(entry.endHour != null && entry.endHour >= 0 && convertView == null) {
                if(viewLocal != 1) {
                    viewLocal = 1;
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.entry_layout, parent, false);
                }
            } else {

                if(entry.startHour >= 0 && entry.endHour != null && entry.endHour < 0) {
                    if(viewLocal != 2) {
                        viewLocal = 2;
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.entry_layout_blue, parent, false);
                    }
                }
            }

            if(entry.startHour < 0 && viewLocal != 3){
                viewLocal = 3;
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.entry_total_layout, parent, false);
            }

            if(viewLocal < 3) {//entry.startHour >= 0
                // Lookup view for data population
                TextView txtBegin = (TextView) convertView.findViewById(R.id.txt_begin);
                TextView txtEnd = (TextView) convertView.findViewById(R.id.txt_end);
                TextView txtTotal = (TextView) convertView.findViewById(R.id.txt_total);
                TextView txtTsk = (TextView) convertView.findViewById(R.id.txt_task);

                txtBegin.setText(parseHour(entry.startHour, entry.startMinute, true));
                txtEnd.setText(parseHour(entry.endHour, entry.endMinute, true));
                txtTotal.setText(getTotal(entry.startHour, entry.startMinute, entry.endHour, entry.endMinute));

                if (entry.task != null)
                    txtTsk.setText(entry.task.code);
                else
                    txtTsk.setText("");
            } else {
                TextView txt = (TextView) convertView.findViewById(R.id.txt_total_entries);

                txt.setText(parseHour(totalHours, totalMins, false));
            }

            return convertView;
        }

        public String parseHour(long h, long m, boolean limit) {

            if(h < 0) {
                GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
                cal.setTime(new Date());

                h = cal.get(GregorianCalendar.HOUR_OF_DAY);
                m = cal.get(GregorianCalendar.MINUTE);
            }

            String per = " AM";

            if (h > 11) {
                per = " PM";
            }

            if (h > 12 && limit) {
                h = h - 12;
            }

            if(!limit)
                per = "";

            String sh = h > 9? ""+h: "0"+h;
            String sm = m > 9? ""+m: "0"+m;
            return sh + ":" + sm + per;
        }

        public String getTotal(long eh, long em, long sh, long sm) {

            long th = 0;
            long tm = 0;

            if(sh < 0) {
                GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
                cal.setTime(new Date());

                sh = cal.get(GregorianCalendar.HOUR_OF_DAY);
                sm = cal.get(GregorianCalendar.MINUTE);
            }

            th = sh - eh;

            if(th > 0) {
                tm = 60 - em;
                tm = sm + tm;

                if (tm >= 60) {
                    tm = tm - 60;
                    th++;
                }
            } else {
                tm = sm - em;
            }

            add(th, tm);

            return parseHour(th,tm, false);
        }

        public void add(long eh, long em) {

            totalHours = totalHours + eh;

                totalMins += em;

                if (totalMins >= 60) {
                    totalMins = totalMins - 60;
                    totalHours++;
                }

        }
    }
}
