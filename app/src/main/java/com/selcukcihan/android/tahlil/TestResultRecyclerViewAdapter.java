package com.selcukcihan.android.tahlil;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestResultRecyclerViewAdapter extends RecyclerView.Adapter<TestResultRecyclerViewAdapter.ViewHolder> {

    private final List<TestResult> mValues;
    private Integer mDefaultBackgroundColor = null;
    //private final TestResultFragment.OnListFragmentInteractionListener mListener;

    public TestResultRecyclerViewAdapter(List<TestResult> items/*, TestResultFragment.OnListFragmentInteractionListener listener*/) {
        mValues = items;
        //mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_testresult, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (mDefaultBackgroundColor == null) {
            mDefaultBackgroundColor = holder.mView.getDrawingCacheBackgroundColor();
        }
        if (mValues.get(position).Normal()) {
            //holder.mIdView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.circle, 0, 0, 0);
            holder.mView.setBackgroundColor(mDefaultBackgroundColor);
        } else {
            //holder.mIdView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.circle_abnormal, 0, 0, 0);
            holder.mView.setBackgroundColor(ContextCompat.getColor(holder.mView.getContext(), R.color.abnormalTest));
        }
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getName());
        holder.mContentView.setText(mValues.get(position).getValueString());
        /*
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public TestResult mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.test_name);
            mContentView = (TextView) view.findViewById(R.id.test_content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
