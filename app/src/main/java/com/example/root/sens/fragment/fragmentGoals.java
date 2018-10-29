package com.example.root.sens.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.sens.R;
import com.github.lzyzsd.circleprogress.ArcProgress;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragmentGoals.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragmentGoals#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentGoals extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fragmentGoals() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentGoals.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentGoals newInstance(String param1, String param2) {
        fragmentGoals fragment = new fragmentGoals();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    RecyclerView recyclerView;
    String[] goalsArray = {"Løb", "Cykling", "Gang"};
    ArrayList<String> goals = new ArrayList<>(Arrays.asList(goalsArray));
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
            vh.text= goals.get(position);
            vh.progress.setBottomText(vh.text);
        }
    };

    class ListeelemViewholder extends RecyclerView.ViewHolder {
        String text;
        ArcProgress progress;

        public ListeelemViewholder(View itemView) {
            super(itemView);
        }
    }

    // Læs mere på https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-b9456d2b1aaf#.fjo359jbr
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP | ItemTouchHelper.DOWN,  // dragDirs
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) { // swipeDirs

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder vh, int actionState) {
            super.onSelectedChanged(vh, actionState);
            Log.d("Lande", "onSelectedChanged "+vh+" "+actionState);
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                vh.itemView.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
            }
        }

        @Override
        public boolean onMove(RecyclerView rv, RecyclerView.ViewHolder vh, RecyclerView.ViewHolder target) {
            int position = vh.getAdapterPosition();
            int tilPos = target.getAdapterPosition();
            String land = goals.remove(position);
            goals.add(tilPos, land);
            Log.d("Lande", "Flyttet: "+ goals);
            adapter.notifyItemMoved(position, tilPos);
            return true; // false hvis rykket ikke skal foretages
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            int position = viewHolder.getAdapterPosition();
            goals.remove(position);
            Log.d("Lande", "Slettet: "+ goals);
            adapter.notifyItemRemoved(position);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder vh) {
            super.clearView(recyclerView, vh);
            Log.d("Lande", "clearView "+vh);
            vh.itemView.animate().scaleX(1).scaleY(1).alpha(1);
        }
    };


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
