package com.example.root.sens.view_layer.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.example.root.sens.ActivityCategories;
import com.example.root.sens.R;
import com.example.root.sens.auxiliary.ResourceManagement;
import com.example.root.sens.managers.IUserManager;
import com.example.root.sens.managers.UserManager;
import com.example.root.sens.auxiliary.JavaScriptInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class GoalInfoFragment extends Fragment {
    WebView webView;
    ActivityCategories goalType;
    Date startDate, endDate, pickedDate;
    TextView subTitle;
    SimpleDateFormat simpleDateFormatDDMM = new SimpleDateFormat("d'.' MMM", new Locale("da"));
    SimpleDateFormat simpleDateFormatDDMMM = new SimpleDateFormat("d'.' MMMM", new Locale("da"));


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph_card, container, false);
        goalType = ActivityCategories.valueOf(getArguments().getString("goalType"));
        subTitle = rootView.findViewById(R.id.textViewGraphView);
        webView = rootView.findViewById(R.id.webViewGraph);

        // Getting DemoData
        int color = ContextCompat.getColor(getContext(), new ResourceManagement().getGoalColor(goalType));
        pickedDate = (Date) getArguments().getSerializable("date");
        if (pickedDate == null) {
            pickedDate = new Date();
        }

        ( (TextView) rootView.findViewById(R.id.goalInfoBox_TextView_title) ).setText(goalType.toString());
        rootView.findViewById(R.id.typeGoalInfo_LinearLayout_header).setBackgroundColor(color);

        rootView.findViewById(R.id.goalchart_cardview).setOnClickListener((View v) ->
                getActivity().onBackPressed());

        rootView.findViewById(R.id.constraintLayoutGraphCard).setOnClickListener((View v) ->
                getActivity().onBackPressed());

        // Configuration of buttons
        Button oneWeek = rootView.findViewById(R.id.goalInfo_Button_1week);
        oneWeek.setOnClickListener((View v) -> showWebView(7));
        oneWeek.setTextColor(color);

        Button twoWeeks = rootView.findViewById(R.id.goalInfo_Button_2weeks);
        twoWeeks.setOnClickListener((View v) -> showWebView(14));
        twoWeeks.setTextColor(color);

        Button oneMonth = rootView.findViewById(R.id.goalInfo_Button_1month);
        oneMonth.setOnClickListener((View v) -> showWebView(30));
        oneMonth.setTextColor(color);

        // Initializing webview
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        showWebView(7);

        return rootView;
    }

    String yAxisDescription;

    private void showWebView(int numberOfDays) {
        @SuppressLint("ResourceType") String color = "#" + getResources().getString(new ResourceManagement().getGoalColor(goalType)).substring(3).toUpperCase();
        webView.loadUrl("file:///android_asset/graph.html");
        JavaScriptInterface javaScriptInterface = new JavaScriptInterface(
                generateData(numberOfDays), color);
        javaScriptInterface.setyAxisDescription(yAxisDescription);

        webView.addJavascriptInterface(javaScriptInterface, "Android");
        subTitle.setText("Viser DemoData for " + numberOfDays + " dage!\nDen " +
                simpleDateFormatDDMMM.format(startDate) + " til " +
                simpleDateFormatDDMMM.format(endDate) + ".");
    }

    private JsonArray generateData(int numberOfDays) {
        // Using Java Calender to manage time, since
        // it's easier manipulated than Date.
        Calendar cal = Calendar.getInstance();
        cal.setTime(pickedDate);
        cal.add(Calendar.DATE, -numberOfDays + 1);

        JsonArray jsonArray = new JsonArray();

        IUserManager userManager = new UserManager();

        int maxTime = 0;

        startDate = cal.getTime();
        for (int i = 0; i < numberOfDays; i++) {
            Date date = cal.getTime();
            String dateString = simpleDateFormatDDMM.format(date);

            Map<ActivityCategories, Float> tempDayData = userManager.getDayData(date);
            float activityTime = tempDayData.get(goalType) == null ? 0 : tempDayData.get(goalType);
            if(activityTime > maxTime){
                maxTime = (int) activityTime;
            }

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("label", dateString);
            jsonObject.addProperty("y", activityTime);
            jsonArray.add(jsonObject);

            // subtracting one day from the calender.
            cal.add(Calendar.DATE, 1);
        }
        cal.add(Calendar.DATE, -1);
        endDate = cal.getTime();

        if(maxTime > 180 && goalType != ActivityCategories.Skridt ){
            JsonArray newJsonArray = new JsonArray();
            for(JsonElement jsonElement : jsonArray){
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                JsonObject newJsonObject = new JsonObject();
                newJsonObject.addProperty("label", jsonObject.get("label").getAsString());
                newJsonObject.addProperty("y", (jsonObject.get("y").getAsFloat()/60));
                newJsonArray.add(newJsonObject);
            }
            jsonArray = newJsonArray;
            yAxisDescription = "Bevægelse i timer";
        } else {
            yAxisDescription = "Bevægelse i minutter";
        }
        if (goalType == ActivityCategories.Skridt){
            yAxisDescription = "Antal skridt";
        }

        return jsonArray;
    }
}
