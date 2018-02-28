package net.curlybois.haven.Controllers;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import net.curlybois.haven.R;
import net.curlybois.haven.model.Shelter;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by jessieprice on 2/28/18.
 */

// Holds information for ShelterListActivity
public class ShelterInfoFragment extends Fragment {
    public static final String ARG_SHELTER_NAME = "shelter_id";

    private Shelter sh;

    /**
     * The adapter for the recycle view list of students
     */
    private ShelterListActivity.SimpleShelterRecyclerViewAdapter adapter;

    public ShelterInfoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if we got a valid course passed to us
        if (getArguments().containsKey(ARG_SHELTER_NAME)) {
            // Get the id from the intent arguments (bundle) and
            //ask the model to give us the course object
            Shelter model = Shelter.getInstance();
            // mCourse = model.getCourseById(getArguments().getInt(ARG_COURSE_ID));
            mShelter = model.getCurrentShelter();

            Activity activity = this.getActivity();

            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mShelter.toString());
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.shelter_list, container, false);

        //Step 1.  Setup the recycler view by getting it from our layout in the main window
        View recyclerView = rootView.findViewById(R.id.shelter_list);
        assert recyclerView != null;
        //Step 2.  Hook up the adapter to the view
        setupRecyclerView((RecyclerView) recyclerView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    /**
     * Set up an adapter and hook it to the provided view
     *
     * @param recyclerView  the view that needs this adapter
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter = new SimpleStudentRecyclerViewAdapter(mShelter.getStudents());
        Log.d("Adapter", adapter.toString());
        recyclerView.setAdapter(adapter);
    }

    /**
     * This inner class is our custom adapter.  It takes our basic model information and
     * converts it to the correct layout for this view.
     *
     * In this case, we are just mapping the toString of the Student object to a text field.
     */
    public class SimpleStudentRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStudentRecyclerViewAdapter.ViewHolder> {

        /**
         * Collection of the items to be shown in this list.
         */
        private final List<Shelter> mShelters;

        /**
         * set the items to be used by the adapter
         * @param items the list of items to be displayed in the recycler view
         */
        public SimpleStudentRecyclerViewAdapter(List<Shelter> items) {
            mShelters = items;
        }

        @Override
        public SimpleStudentRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            /*
              This sets up the view for each individual item in the recycler display
              To edit the actual layout, we would look at: res/layout/course_list_content.xml
              If you look at the example file, you will see it currently just 2 TextView elements
             */
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.shelter_list_content, parent, false);
            return new SimpleStudentRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SimpleStudentRecyclerViewAdapter.ViewHolder holder, int position) {
            /*
            This is where we have to bind each data element in the list (given by position parameter)
            to an element in the view (which is one of our two TextView widgets
             */
            //start by getting the element at the correct position
            holder.mShelter = mShelters.get(position);
            Log.d("Adapter", "student: " + holder.mShelter);
            /*
              Now we bind the data to the widgets.  In this case, pretty simple, put the id in one
              textview and the string rep of a course in the other.
             */
            holder.mIdView.setText("" + mShelters.get(position).getName());
            holder.mContentView.setText(mShelters.get(position).getCapacity());

        }

        @Override
        public int getItemCount() {
            return mShelters.size();
        }

        /**
         * This inner class represents a ViewHolder which provides us a way to cache information
         * about the binding between the model element (in this case a Course) and the widgets in
         * the list view (in this case the two TextView)
         */

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public final TextView mRestrictions;
            public final TextView mLongitude;
            public final TextView mLatitude;
            public final TextView mAddress;
            public final TextView mNotes;
            public final TextView mPhone;
            public Shelter mShelter;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.shelter_name);
                Log.d("Holder", mIdView.toString());
                mContentView = (TextView) view.findViewById(R.id.shelter_capacity);
                mRestrictions = (TextView) view.findViewById(R.id.shelter_restrictions);
                mLongitude = (TextView) view.findViewById(R.id.shelter_longitude);
                mLatitude = (TextView) view.findViewById(R.id.shelter_latitude);
                mAddress = (TextView) view.findViewById(R.id.shelter_address);
                mNotes = (TextView) view.findViewById(R.id.shelter_notes);
                mPhone = (TextView) view.findViewById(R.id.shelter_phone);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }

}