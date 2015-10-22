package br.com.abraao.timesheet;

import android.app.Activity;
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
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.abraao.timesheet.dummy.DummyContent;
import br.com.abraao.timesheet.entities.Client;
import br.com.abraao.timesheet.repository.ClientRepository;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 */
public class ClientListFragment extends Fragment implements AbsListView.OnItemClickListener, BackPressListerner {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String EDIT_CLIENT = "EDIT_CLIENT";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Client client;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;
    private ViewGroup mContainer;
    private boolean openEditing = false;

    // TODO: Rename and change types of parameters
    public static ClientListFragment newInstance(String param1, String param2) {
        ClientListFragment fragment = new ClientListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ClientListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        ClientRepository clientRepository = new ClientRepository();

        List<Client> list = clientRepository.getClients();

        // TODO: Change Adapter to display your content
        if(list != null && !list.isEmpty()) {
            mAdapter = new ArrayAdapter<Client>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, list);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;

        if(this.client == null) {
            view = inflater.inflate(R.layout.fragment_client, container, false);

            this.mContainer = container;
            // Set the adapter
            mListView = (AbsListView) view.findViewById(android.R.id.list);
            ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
            FloatingActionButton btnAddClient = (FloatingActionButton) view.findViewById(R.id.btn_add_client);

            // Set OnItemClickListener so we can be notified on item clicks
            mListView.setOnItemClickListener(this);
            btnAddClient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClientListFragment.this.onAddClientClick(v);
                }
            });


        } else {
            view = inflater.inflate(R.layout.fragment_client_edit, container, false);
            TextView txtName = (TextView) view.findViewById(R.id.txt_name);
            TextView txtCode = (TextView) view.findViewById(R.id.txt_code);

            txtName.setText(client.name, TextView.BufferType.EDITABLE);
            txtCode.setText(client.code, TextView.BufferType.EDITABLE);

            if(client.id == null || client.id.isEmpty()) {
                ImageButton btnRemove = (ImageButton) view.findViewById(R.id.btn_remove_client);
                btnRemove.setVisibility(View.INVISIBLE);
            }
        }

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

        this.client = (Client) parent.getItemAtPosition(position);
        //this.openEditing = true;

        this.refreshView();

    }

    public void onAddClientClick(View v) {
        this.client = new Client("","New Client","New Code");
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
        client = null;

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
}
