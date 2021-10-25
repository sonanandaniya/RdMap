package com.devpractical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devpractical.R;
import com.devpractical.databinding.RowFiltertypeListBinding;
import com.devpractical.dialog.FilterModel;

import java.util.List;


public class FilterListAdapter extends RecyclerView.Adapter<FilterListAdapter.FilterTypeListHolder> {

    private Context context;
    private List<FilterModel> filterModelList;
    private RecyclerViewClickListener listener;
    int id = -1;

    public FilterListAdapter(Context context, List<FilterModel> filterModelList, RecyclerViewClickListener listener) {
        this.context = context;
        this.filterModelList = filterModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FilterTypeListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilterTypeListHolder(LayoutInflater.from(context).inflate(R.layout.row_filtertype_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FilterTypeListHolder holder, int position) {

        // holder.binding.setName(userList.get(position).getFullName());


       /* if (userList.get(position).isSelected()) {
            holder.binding.tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_orange, 0, 0, 0);
        } else {
            holder.binding.tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.uncheck, 0, 0, 0);

        }*/

        if (id == position) {
            holder.binding.tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check, 0, 0, 0);
        } else {
            holder.binding.tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.uncheck, 0, 0, 0);
        }

     /*   switch (position) {
            case 0:
                holder.binding.tvTitle.setText(context.getString(R.string.ascending));
                break;

            case 1:
                holder.binding.tvTitle.setText(context.getString(R.string.descending));
                break;

        }
*/

        holder.binding.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = holder.getAdapterPosition();

                listener.onClick(1, holder.getAdapterPosition(), filterModelList.get(holder.getAdapterPosition()),
                        filterModelList.get(holder.getAdapterPosition()).getTitle());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        // return userList.size();
        return filterModelList.size();
    }


    public interface RecyclerViewClickListener {
        void onClick(int tag, int pos, Object data, String title);
    }


    public class FilterTypeListHolder extends RecyclerView.ViewHolder {

        private RowFiltertypeListBinding binding;

        public FilterTypeListHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            // ButterKnife.bind(this, itemView);


        }

     /*   @OnClick(R.id.tv_title)
        void onTitleClick() {

            id = getAdapterPosition();

            if (!userList.get(getAdapterPosition()).isSelected()) {
                userList.get(getAdapterPosition()).setSelected(true);
            } else {
                userList.get(getAdapterPosition()).setSelected(false);
            }
            notifyDataSetChanged();

        }*/


    }
}
