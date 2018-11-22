package com.example.root.sens.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.sens.R;
import com.example.root.sens.view.activities.UserConfigActivity;

public class UserConfigNameInfoFragment extends Fragment implements ViewPager.OnPageChangeListener {
    UserDataPassListener mCallback;

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        //nothing
    }

    @Override
    public void onPageSelected(int i) {
        if(i==UserConfigActivity.NUM_PAGES-1) mCallback.passUserData("user_data");
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        //nothing
    }

    public interface UserDataPassListener{
            public void passUserData(String data);
        }

        @Override
        public void onAttach(Context context){
            super.onAttach(context);
            try{
                mCallback = (UserDataPassListener) context;
            } catch(ClassCastException e){
                throw new ClassCastException(context.toString()+ " must implement OnImageClickListener");
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(
                    R.layout.user_config_f_viewpager_name_info, container, false);
            TextView datePickerText = rootView.findViewById(R.id.tv_user_config_birth_date);

            datePickerText.setOnClickListener((View v) -> {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DialogFragment newFragment = new com.example.root.sens.fragment.DatePickerFragment();
                newFragment.show(fm, "datePicker");
            });

            return rootView;
        }
}
