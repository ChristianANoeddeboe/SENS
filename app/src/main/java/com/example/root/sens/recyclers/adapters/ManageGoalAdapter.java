package com.example.root.sens.recyclers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.root.sens.R;
import com.example.root.sens.activities.ManageGoalActivity;
import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.recyclers.itemmodels.HistoryItemModel;
import com.example.root.sens.recyclers.itemmodels.ManageGoalModel;

import java.util.List;

import io.realm.RealmList;

public class ManageGoalAdapter extends RecyclerView.Adapter<ManageGoalAdapter.ViewHolder>{
    private final static String TAG = ManageGoalAdapter.class.getSimpleName();
    private List<ManageGoalModel> dataSet;
    private LayoutInflater inflater;

    public ManageGoalAdapter(Context context, List<ManageGoalModel> dataSet){
        inflater = LayoutInflater.from(context);
        this.dataSet = dataSet;
    }
    /*
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView header,total;
        private SeekBar seekBar;

        public ViewHolder(View v) {
            super(v);
            header = v.findViewById(R.id.textView_set_goal_element_header);

        }

        public TextView getHeader() {
            return header;
        }

        public TextView getTotal() {
            return total;
        }

        public SeekBar getSeekBar() {
            return seekBar;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.set_goal_element, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.getHeader().setPadding(0, 30, 0, 0); //TODO: Should probably be declared in layout
        viewHolder.getHeader().setText(dataSet.get(i).getTextHeader());
        viewHolder.getTotal().setText(dataSet.get(i).getTextTotal());
        viewHolder.getSeekBar().setMax(dataSet.get(i).getMax()-dataSet.get(i).getCounter());

        RealmList<Goal> temp = UserDAO.getInstance().getNewestGoal().getGoals();
        for(Goal g : temp){
            if(g.getType().toString().equals(dataSet.get(i))){
                dataSet.get(i).getOldValues()[i] = g.getValue();
                viewHolder.getSeekBar().setProgress(g.getValue());
                viewHolder.getTotal().setText(SetGoalAdapter.generateProgressText(g.getValue()));
                dataSet.get(i).setProgess(g.getValue());
                break;
            }
        }


        viewHolder.getSeekBar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewHolder.getTotal().setText(SetGoalAdapter.generateProgressText(progress));
                dataSet.get(i).setProgess(progress);
                int countTemp = 0;
                for(int j = 0; j < dataSet.size(); j++){
                    countTemp += dataSet.get(j).getProgess();
                }
                for(int j = 0; j < dataSet.size(); j++){
                    dataSet.get(j).setMax((dataSet.get(j).getSEEKBAR_MAXCONST()-countTemp)-dataSet.get(j).getProgess());
                }

                dataSet.get(i).setChanged(true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
