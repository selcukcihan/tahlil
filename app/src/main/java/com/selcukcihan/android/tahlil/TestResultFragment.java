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


import java.util.ArrayList;
import java.util.List;

public class TestResultFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String RESULTS_PARAMETER = "com.selcukcihan.android.tahlil.RESULTS_PARAMETER";

    //private OnListFragmentInteractionListener mListener;
    private ArrayList<TestResult> mResults;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TestResultFragment() {
    }

    public static TestResultFragment newInstance(List<TestResult> results) {
        TestResultFragment fragment = new TestResultFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(RESULTS_PARAMETER, new ArrayList<Parcelable>(results));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mResults = getArguments().getParcelableArrayList(RESULTS_PARAMETER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_testresult_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

            //recyclerView.setAdapter(new TestResultRecyclerViewAdapter(mResults, mListener));
            recyclerView.setAdapter(new TestResultRecyclerViewAdapter(mResults));
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
