package net.curlybois.haven.adapters;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.gson.Gson;

import net.curlybois.haven.R;
import net.curlybois.haven.interfaces.ShelterListClickListener;
import net.curlybois.haven.model.Shelter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Converts the list of shelters into a viewable form
 */
@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
public class ShelterAdapter extends RecyclerView.Adapter<ShelterAdapter.ViewHolder>
        implements Filterable {

    private static final double METERS_TO_MILES = 0.000621371;

    private final List<Shelter> shelters;
    private List<Shelter> sheltersFiltered;
    private final ShelterListClickListener listener;
    private Location lastLocation;

    /**
     * Creates a new ShelterAdapter
     * @param shelters the list of shelters to show
     * @param listener a callback for clicking on a shelter
     */
    public ShelterAdapter(List<Shelter> shelters, ShelterListClickListener listener) {
        this.shelters = shelters;
        this.sheltersFiltered = shelters;
        this.listener = listener;
        this.lastLocation = null;
    }

    /**
     * Get the list of filtered shelters
     * @return the list of filtered shelters
     */
    public Iterable<Shelter> getSheltersFiltered() {
        return Collections.unmodifiableList(sheltersFiltered);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shelter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Shelter shelter = sheltersFiltered.get(position);
        holder.nameView.setText(shelter.getName());
        holder.infoView.setText(String.format(Locale.getDefault(),
                "%.1f mi • %d spots open",
                shelter.getDistance(lastLocation) * METERS_TO_MILES,
                shelter.getVacancies()));
    }

    @Override
    public int getItemCount() {
        return sheltersFiltered.size();
    }

    /**
     * Apply a filter to the list of shelters
     * @param query the filter
     */
    public void filter(FilterQuery query) {
        getFilter().filter(getFilterString(query));
    }

    /**
     * Sort the filtered shelters by distance from a location
     * @param location the location to sort by
     */
    public void sortByNearest(final Location location) {
        this.lastLocation = location;
        Collections.sort(sheltersFiltered, new Comparator<Shelter>() {
            @Override
            public int compare(Shelter o1, Shelter o2) {
                float dist1 = o1.getDistance(location);
                float dist2 = o2.getDistance(location);

                if (dist1 < dist2) {
                    return -1;
                } else if (dist1 > dist2) {
                    return 1;
                }
                return 0;
            }
        });
        notifyDataSetChanged();
    }

    private CharSequence getFilterString(FilterQuery query) {
        return new Gson().toJson(query);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            /**
             * Filters the shelter list based on filter constrains
             *
             * @param constraint a Gson-serialized FilterQuery object
             * @return the results of the filtering
             */
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterQuery query = new Gson().fromJson(constraint.toString(), FilterQuery.class);
                List<Shelter> filteredList = new ArrayList<>();
                for (Shelter shelter : shelters) {
                    if (!shelter.getName().toLowerCase().contains(query.getName())
                            || ((query.getGender() != null) && !shelter.getGenderSet()
                            .contains(query.getGender()))
                            || ((query.getAge() != null) && !shelter.getAgeSet()
                            .contains(query.getAge()))
                            || (query.isVeterans() && !shelter.isVeterans())) {
                        continue;
                    }
                    filteredList.add(shelter);
                }
                sheltersFiltered = filteredList;
                FilterResults filterResults = new FilterResults();
                filterResults.values = sheltersFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                sheltersFiltered = (ArrayList<Shelter>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView nameView;
        final TextView infoView;

        ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            nameView = itemView.findViewById(R.id.shelter_name);
            infoView = itemView.findViewById(R.id.shelter_info);
        }

        @Override
        public void onClick(View v) {
            listener.shelterListClicked(sheltersFiltered.get(this.getAdapterPosition()));
        }
    }
}
