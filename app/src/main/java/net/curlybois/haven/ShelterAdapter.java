package net.curlybois.haven;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import net.curlybois.haven.model.Shelter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jake on 3/5/18.
 */

public class ShelterAdapter extends RecyclerView.Adapter<ShelterAdapter.ViewHolder> implements Filterable {

    private List<Shelter> shelters;
    private List<Shelter> sheltersFiltered;
    private ShelterListClickListener listener;

    public ShelterAdapter(List<Shelter> shelters, ShelterListClickListener listener) {
        this.shelters = shelters;
        this.sheltersFiltered = shelters;
        this.listener = listener;
    }

    public void updateData(List<Shelter> shelters) {
        this.shelters = shelters;
        this.sheltersFiltered = shelters;
        notifyDataSetChanged();
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
        holder.infoView.setText(shelter.getAddress());
    }

    @Override
    public int getItemCount() {
        return sheltersFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String[] constraints = constraint.toString().split(":");
                String name = constraints[0].toLowerCase();
                Shelter.Gender gender = Shelter.Gender.valueOf(constraints[1]);
                Shelter.Age age = Shelter.Age.valueOf(constraints[2]);
                boolean veterans = Boolean.valueOf(constraints[3]);

                List<Shelter> filteredList = new ArrayList<>();
                for (Shelter s : shelters) {
                    if (!s.getName().toLowerCase().contains(name)) {
                        continue;
                    }
                    if (gender != Shelter.Gender.NONE && !s.getGenderList().contains(gender)) {
                        continue;
                    }
                    if (age != Shelter.Age.NONE && !s.getAgeList().contains(age)) {
                        continue;
                    }
                    if (veterans && !s.isVeterans()) {
                        continue;
                    }
                    Log.d("TAG", "Added " + s.getName());
                    filteredList.add(s);
                }
                sheltersFiltered = filteredList;
                Log.d("TAG", filteredList.toString());

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

        public TextView nameView, infoView;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            nameView = itemView.findViewById(R.id.shelter_name);
            infoView = itemView.findViewById(R.id.shelter_info);
        }

        @Override
        public void onClick(View v) {
            listener.shelterListClicked(v, this.getAdapterPosition());
        }
    }
}