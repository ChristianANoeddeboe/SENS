package com.example.root.sens.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.sens.DTO.Goal;
import com.example.root.sens.R;
import com.example.root.sens.adapters.OverviewAdapter;
import com.example.root.sens.data;

import java.util.ArrayList;


public class OverviewFragment extends Fragment {

    public OverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.goals_f_content, container, false);
        // Vi laver en arrayliste så vi kan fjerne/indsætte elementer
        recyclerView = v.findViewById(R.id.goalsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(new OverviewAdapter(this.getContext(),data.generateData()));

        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        //itemTouchHelper.attachToRecyclerView(recyclerView);


        // Inflate the layout for this fragment
        return v;
    }
/*
    RecyclerView.Adapter adapter = new RecyclerView.Adapter<ListeelemViewholder>() {
        class MyProgressFromatter implements CircleProgressBar.ProgressFormatter {
            @Override
            public CharSequence format(int progress, int max) {
                return "test";
            }
        }

        @Override
        public int getItemCount()  {
            return goals.size();
        }

        @Override
        public ListeelemViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.goals_list_element, parent, false);
            ListeelemViewholder vh = new ListeelemViewholder(view);
            vh.text = view.findViewById(R.id.TextView_goals_list_element_tekst);
            //vh.progress.setProgressFormatter(new MyProgressFromatter());

            return vh;
        }

        @Override
        public void onBindViewHolder(ListeelemViewholder vh, int position) {

           // vh.progress.setProgress(goals.get(position).getProgress());
        }
    };


    // set the ProgressFormatter as you want

    class ListeelemViewholder extends RecyclerView.ViewHolder {
        TextView text;
        //ArcProgress progress;
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
    */



}
