package net.curlybois.haven;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.curlybois.haven.model.Shelter;

import java.util.List;

/**
 * Created by jake on 3/5/18.
 */

public class ShelterAdapter extends RecyclerView.Adapter<ShelterAdapter.ViewHolder> {

    private List<Shelter> shelters;
    private ShelterListClickListener listener;

    public ShelterAdapter(List<Shelter> shelters, ShelterListClickListener listener) {
        this.shelters = shelters;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shelter, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Shelter shelter = shelters.get(position);
        holder.nameView.setText(shelter.getName());
    }

    @Override
    public int getItemCount() {
        return shelters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nameView;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            nameView = itemView.findViewById(R.id.shelter_name);
        }

        @Override
        public void onClick(View v) {
            listener.shelterListClicked(v, this.getAdapterPosition());
        }
    }
}
