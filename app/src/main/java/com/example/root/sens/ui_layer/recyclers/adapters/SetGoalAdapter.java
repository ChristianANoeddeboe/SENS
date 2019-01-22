package com.example.root.sens.ui_layer.recyclers.adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.root.sens.ActivityCategories;
import com.example.root.sens.auxiliary.ProgressTextGenerator;
import com.example.root.sens.ui_layer.recyclers.itemmodels.SetGoalItemModel;
import com.example.root.sens.R;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class SetGoalAdapter extends RecyclerView.Adapter<SetGoalAdapter.ViewHolder> {
    private static final String TAG = SetGoalAdapter.class.getSimpleName();
    private SetGoalAdapterOnItemClickListener listener;
    private List<SetGoalItemModel> dataSet;
    private Snackbar snackbar;
    private RecyclerView recyclerView;

    public interface SetGoalAdapterOnItemClickListener {
        void onItemClick(View item, int position, ActivityCategories type);
    }

    /*
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */

    public SetGoalAdapter(SetGoalAdapterOnItemClickListener listener, List<SetGoalItemModel> dataSet){
        dataSet.sort(new Comparator<SetGoalItemModel>() {
            @Override
            public int compare(SetGoalItemModel o1, SetGoalItemModel o2) {
                if(o1.getType().getValue() < o2.getType().getValue()) return 1;
                return -1;
            }
        });
        this.dataSet = dataSet;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewPrimary;
        private final EditText editText;

        public ViewHolder(View v) {
            super(v);
            textViewPrimary = v.findViewById(R.id.textView_set_goal_element_header);
            editText = v.findViewById(R.id.text_field_set_goal_element);

        }

        public TextView getTextViewPrimary() {
            return textViewPrimary;
        }

        public EditText getEditText() {
            return editText;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = (viewType == 0) ?
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.set_goal_element, viewGroup, false) :
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.set_goal_element_steps,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ActivityCategories type = dataSet.get(position).getType();

        if(type != ActivityCategories.Skridt){
            viewHolder.getEditText().setOnClickListener((View v) -> {
                listener.onItemClick(viewHolder.getEditText(), position, type);
            });
            viewHolder.getEditText().setText(new ProgressTextGenerator().generateProgressText(Integer.toString(dataSet.get(position).getValue())));
        }
        if(type == ActivityCategories.Skridt){
            viewHolder.getEditText().setHint("0 Skridt");
            viewHolder.getEditText().setText("");
            viewHolder.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    if(Pattern.matches("[0-9]+", s.toString())){
                        int input;
                        try {
                            input = Integer.parseInt(s.toString());
                        } catch (NumberFormatException e) {
                            //s.clear();
                            s.replace(0, s.length(), Integer.MAX_VALUE+"");
                            input = Integer.parseInt(s.toString());
                            snackbar = Snackbar.make(recyclerView, R.string.TooManySteps, Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                        dataSet.get(position).setValue(input);
                    }
                }
            });

        }
        viewHolder.getTextViewPrimary().setText(dataSet.get(position).getPrimaryTxt());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public int getItemViewType(int position) {
        if(dataSet.get(position).getType() == ActivityCategories.Skridt){
            return 1;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public List<SetGoalItemModel> getDataSet(){
        return dataSet;
    }

}