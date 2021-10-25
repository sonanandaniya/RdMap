package com.devpractical;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.devpractical.databinding.RowLocationsListingBinding;
import com.devpractical.room.CityData;
import com.devpractical.room.RoomDb;

import java.util.List;


public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.MyViewHolder> {

    private Context context;
    private List<CityData> locationList;
    RecyclerViewClickListener listener;
    RoomDb roomDb;


    public LocationsAdapter(Context context, List<CityData> locationList, RecyclerViewClickListener listener) {
        this.context = context;
        this.locationList = locationList;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowLocationsListingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_locations_listing, parent, false);

        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position) {
        CityData cityData = locationList.get(myViewHolder.getAdapterPosition());
        roomDb = RoomDb.getInstance(context);
        myViewHolder.binding.tvLocName.setText(cityData.getLocationName());
        myViewHolder.binding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CityData cityData1 = locationList.get(myViewHolder.getAdapterPosition());
                int cId = cityData1.getId();
                listener.onClick(cId, myViewHolder.getAdapterPosition(), locationList.get(myViewHolder.getAdapterPosition()));

            }
        });

        myViewHolder.binding.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CityData cityData1 = locationList.get(myViewHolder.getAdapterPosition());
                roomDb.cityDao().delete(cityData1);
                int pos = myViewHolder.getAdapterPosition();
                locationList.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, locationList.size());
            }
        });


    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }


    public interface RecyclerViewClickListener {
        void onClick(int tag, int pos, Object data);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RowLocationsListingBinding binding;

        public MyViewHolder(RowLocationsListingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

}
