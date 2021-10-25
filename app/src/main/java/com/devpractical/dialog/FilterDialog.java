package com.devpractical.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devpractical.R;
import com.devpractical.adapter.FilterListAdapter;
import com.devpractical.databinding.DialogPaynowAlertBinding;
import com.devpractical.dialog.BottomSheetBaseDialog;
import com.devpractical.dialog.FilterModel;

import java.util.ArrayList;

/**
 * Created by AA on 2/28/2018.
 */

public class FilterDialog extends BottomSheetBaseDialog implements View.OnClickListener {
     private DialogPaynowAlertBinding binding;
    private ButtonClickListener listener;
    private String selectedFilter = "";
    private ArrayList<FilterModel> filterModelList = new ArrayList<>();

    public FilterDialog(Context context, String selectedFilter, ButtonClickListener listener) {
        super(context);
        this.listener = listener;
        this.selectedFilter = selectedFilter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_paynow_alert, null, false);
        binding.tvAsc.setChecked(selectedFilter.equalsIgnoreCase("0"));
        binding.tvDec.setChecked(selectedFilter.equalsIgnoreCase("1"));
        initView();
        return binding.getRoot();
    }

    private void initView() {
        binding.tvApply.setOnClickListener(this);
        binding.tvDec.setOnClickListener(this);
        binding.tvAsc.setOnClickListener(this);
        binding.tvClear.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_apply:
                listener.onAddlicked(binding.tvAsc.isChecked() ? "0" : "1");
                dismissAllowingStateLoss();
                break;

            case R.id.tv_clear:
                listener.onClear();
                dismissAllowingStateLoss();
                break;


        }
    }


    public interface ButtonClickListener {

        void onAddlicked(String title);

        void onClear();

    }


}
