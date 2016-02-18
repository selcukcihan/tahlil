package com.selcukcihan.android.tahlil;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class TestResultFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String RESULTS_PARAMETER = "com.selcukcihan.android.tahlil.RESULTS_PARAMETER";
    private static final String TCKN_PARAMETER = "com.selcukcihan.android.tahlil.TCKN_PARAMETER";
    private static final String NAME_PARAMETER = "com.selcukcihan.android.tahlil.NAME_PARAMETER";
    private static final String SURNAME_PARAMETER = "com.selcukcihan.android.tahlil.SURNAME_PARAMETER";
    private static final String TESTDATE_PARAMETER = "com.selcukcihan.android.tahlil.TESTDATE_PARAMETER";
    private static final String BIRHTDATE_PARAMETER = "com.selcukcihan.android.tahlil.BIRHTDATE_PARAMETER";

    //private OnListFragmentInteractionListener mListener;
    private TestResultCollection mCollection;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TestResultFragment() {
    }

    public static TestResultFragment newInstance(TestResultCollection collection) {
        TestResultFragment fragment = new TestResultFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(RESULTS_PARAMETER, new ArrayList<Parcelable>(collection.Results));
        args.putString(TCKN_PARAMETER, collection.TCKN);
        args.putString(NAME_PARAMETER, collection.Name);
        args.putString(SURNAME_PARAMETER, collection.Surname);
        args.putString(TESTDATE_PARAMETER, collection.TestDate);
        args.putString(BIRHTDATE_PARAMETER, collection.BirthDate);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCollection = new TestResultCollection();
            mCollection.Results = getArguments().getParcelableArrayList(RESULTS_PARAMETER);
            mCollection.TCKN = getArguments().getString(TCKN_PARAMETER);
            mCollection.Name = getArguments().getString(NAME_PARAMETER);
            mCollection.Surname = getArguments().getString(SURNAME_PARAMETER);
            mCollection.BirthDate = getArguments().getString(BIRHTDATE_PARAMETER);
            mCollection.TestDate = getArguments().getString(TESTDATE_PARAMETER);
        }
    }

    private void renderMetaDataText(TextView header, TextView meta) {
        if (mCollection != null) {
            if (mCollection.Name != null && mCollection.Surname != null &&
                    mCollection.TCKN != null && mCollection.BirthDate != null &&
                    mCollection.TestDate != null) {
                header.setText(String.format(getResources().getString(R.string.results_test_date), mCollection.TestDate));
                meta.setText(String.format("%1$s - %2$s %3$s - %4$s", mCollection.TCKN, mCollection.Name, mCollection.Surname, mCollection.BirthDate));
                header.setVisibility(View.VISIBLE);
                meta.setVisibility(View.VISIBLE);
                return;
            }
        }
        header.setVisibility(View.GONE);
        meta.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_testresult_list, container, false);
        View subView = view.findViewById(R.id.list);

        renderMetaDataText((TextView) view.findViewById(R.id.meta_header), (TextView) view.findViewById(R.id.meta_data));

        // Set the adapter
        if (subView instanceof RecyclerView) {
            Context context = subView.getContext();
            RecyclerView recyclerView = (RecyclerView) subView;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
            recyclerView.setAdapter(new TestResultRecyclerViewAdapter(mCollection.Results));
        }
        return view;
    }

/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/


    /*
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(TestResult item);
    }*/
}
