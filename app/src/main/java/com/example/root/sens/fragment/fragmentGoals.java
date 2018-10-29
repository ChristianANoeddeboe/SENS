package com.example.root.sens.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.R;
import com.example.root.sens.data;
import com.example.root.sens.mål;
import com.github.lzyzsd.circleprogress.ArcProgress;

import java.util.ArrayList;


public class fragmentGoals extends Fragment {

    public fragmentGoals() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    RecyclerView recyclerView;
    ArrayList<mål> goals = data.goalsData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goals, container, false);
        // Vi laver en arrayliste så vi kan fjerne/indsætte elementer
        recyclerView = v.findViewById(R.id.goalsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        // Inflate the layout for this fragment
        return v;
    }

    RecyclerView.Adapter adapter = new RecyclerView.Adapter<ListeelemViewholder>() {
        @Override
        public int getItemCount()  {
            return goals.size();
        }

        @Override
        public ListeelemViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.goals_list_element, parent, false);
            ListeelemViewholder vh = new ListeelemViewholder(view);
            vh.progress = view.findViewById(R.id.arcProgress);

            return vh;
        }

        @Override
        public void onBindViewHolder(ListeelemViewholder vh, int position) {
            vh.progress.setBottomText(goals.get(position).getTitel());
            vh.progress.setProgress(goals.get(position).getProgress());
        }
    };

    class ListeelemViewholder extends RecyclerView.ViewHolder {
        ArcProgress progress;
        public ListeelemViewholder(View itemView) {
            super(itemView);
        }
    }

    // Læs mere på https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-b9456d2b1aaf#.fjo359jbr
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP,ItemTouchHelper.DOWN) { // swipeDirs

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder vh, int actionState) {
            super.onSelectedChanged(vh, actionState);
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                vh.itemView.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
            }
        }

        @Override
        public boolean onMove(RecyclerView rv, RecyclerView.ViewHolder vh, RecyclerView.ViewHolder target) {
            int position = vh.getAdapterPosition();
            int tilPos = target.getAdapterPosition();
            mål land = goals.remove(position);
            goals.add(tilPos, land);
            adapter.notifyItemMoved(position, tilPos);
            return true; // false hvis rykket ikke skal foretages
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {



        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder vh) {
            super.clearView(recyclerView, vh);
            vh.itemView.animate().scaleX(1).scaleY(1).alpha(1);
        }

    };



}
