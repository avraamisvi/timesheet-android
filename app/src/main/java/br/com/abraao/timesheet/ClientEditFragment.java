package br.com.abraao.timesheet;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import br.com.abraao.timesheet.entities.Client;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientEditFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CLIENT = "client";

    // TODO: Rename and change types of parameters
    private Client client;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ClientEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientEditFragment newInstance(Client client) {
        ClientEditFragment fragment = new ClientEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(CLIENT, client);
        fragment.setArguments(args);
        return fragment;
    }

    public ClientEditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            client = getArguments().getParcelable(CLIENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*if(getActivity().getIntent() != null) {
            Client cli = getActivity().getIntent().getParcelableExtra("client");
        }*/

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_edit, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(client != null) {
            TextView txtName = (TextView) getActivity().findViewById(R.id.txt_name);
            TextView txtCode = (TextView) getActivity().findViewById(R.id.txt_code);

            txtName.setText(client.name, TextView.BufferType.EDITABLE);
            txtCode.setText(client.code, TextView.BufferType.EDITABLE);

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
