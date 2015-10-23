package br.com.abraao.timesheet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.activeandroid.query.Select;

import java.util.List;

import br.com.abraao.timesheet.entities.Client;
import br.com.abraao.timesheet.entities.Task;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 */
public class TaskFragment extends Fragment implements AbsListView.OnItemClickListener, BackPressListerner {

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Client client;
    private Task task;

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
    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;

        if(this.client == null) {
            view = inflater.inflate(R.layout.fragment_client_task_list, container, false);

            List<Client> list = new Select().from(Client.class).execute();

            if(list != null && !list.isEmpty()) {
                mAdapter = new ArrayAdapter<Client>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, list);
            }

            this.mContainer = container;
            // Set the adapter
            mListView = (AbsListView) view.findViewById(R.id.client_task_list);
            ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

            // Set OnItemClickListener so we can be notified on item clicks
            mListView.setOnItemClickListener(this);


        } else {

            if(this.task != null) {
                view = inflater.inflate(R.layout.fragment_task_edit, container, false);
                final TextView txtName = (TextView) view.findViewById(R.id.txt_task_name);
                final TextView txtCode = (TextView) view.findViewById(R.id.txt_task_code);

                txtName.setText(task.name, TextView.BufferType.EDITABLE);
                txtCode.setText(task.code, TextView.BufferType.EDITABLE);

                if (task.code == null || task.code.isEmpty()) {
                    ImageButton btnRemove = (ImageButton) view.findViewById(R.id.btn_remove_task);
                    btnRemove.setVisibility(View.INVISIBLE);
                } else {
                    ImageButton btnRemove = (ImageButton) view.findViewById(R.id.btn_remove_task);
                    btnRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                            dialog.setMessage("Are you sure you want to delete this task?");
                            dialog.setTitle("Delete Task");
                            dialog.setIcon(android.R.drawable.ic_dialog_alert);
                            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    task.delete();
                                    backPressed();
                                }
                            });
                            dialog.setCancelable(true);
                            dialog.create().show();
                        }
                    });
                }

                ImageButton btnSave = (ImageButton) view.findViewById(R.id.btn_save_task);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        client.name = txtName.getText().toString();
                        client.code = txtCode.getText().toString();
                        client.save();
                        backPressed();
                    }
                });
            } else {
                view = inflater.inflate(R.layout.fragment_task_list, container, false);

                List<Task> list = new Select().from(Task.class).where("client = ?", this.client.getId()).orderBy("name ASC").execute();

                if(list != null && !list.isEmpty()) {
                    mAdapter = new ArrayAdapter<Task>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, list);
                }

                this.mContainer = container;
                // Set the adapter
                mListView = (AbsListView) view.findViewById(R.id.list_task);
                ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
                FloatingActionButton btnAddClient = (FloatingActionButton) view.findViewById(R.id.btn_add_task);

                mListView.setOnItemClickListener(this);
                btnAddClient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onAddTaskClick(v);
                    }
                });
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
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(this.client == null) {
            this.client = (Client) parent.getItemAtPosition(position);
        } else if(this.task == null) {
            this.task = (Task) parent.getItemAtPosition(position);
        }

        this.refreshView();
    }

    public void onAddTaskClick(View v) {
        this.task = new Task();
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

        if(task != null) {
            this.task = null;
        } else {
            this.client = null;
        }

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
