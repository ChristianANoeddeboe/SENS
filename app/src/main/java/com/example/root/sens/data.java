package com.example.root.sens;


import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.ActivityCategories;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.Record;
import com.example.root.sens.dto.Sensor;
import com.example.root.sens.dto.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmList;

public class data {
    public static void initializeData() {
        Realm realm = Realm.getDefaultInstance();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            try {

                UserDAO tempdao = UserDAO.getInstance();
                User tempuser = tempdao.getUserLoggedIn();
                realm.beginTransaction();
                RealmList<Goal> goals = new RealmList<>();
                goals.add(new Goal(ActivityCategories.Resting.toString(), 60 * 8));
                goals.add(new Goal(ActivityCategories.Standing.toString(), 50));
                goals.add(new Goal(ActivityCategories.Walking.toString(), 300));
                goals.add(new Goal(ActivityCategories.Exercise.toString(), 350));
                goals.add(new Goal(ActivityCategories.Cycling.toString(), 120));
                tempuser.getGoals().add(new GoalHistory(1, df.parse("11/18/2018"), goals));

                RealmList<Goal> goals2 = new RealmList<>();
                goals2.add(new Goal(ActivityCategories.Resting.toString(), 60 * 8));
                goals2.add(new Goal(ActivityCategories.Standing.toString(), 300));
                goals2.add(new Goal(ActivityCategories.Walking.toString(), 400));
                goals2.add(new Goal(ActivityCategories.Exercise.toString(), 550));
                goals2.add(new Goal(ActivityCategories.Cycling.toString(), 360));
                tempuser.getGoals().add(new GoalHistory(2, df.parse("11/10/2018"), goals2));
                realm.commitTransaction();
                tempdao.saveUser(tempuser);
            } catch (ParseException e) {
                e.printStackTrace();
            }



        //"2018-11-07T23:00:00"
        DateFormat sensDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


            try {
                UserDAO tempdao = UserDAO.getInstance();
                User tempuser = tempdao.getUserLoggedIn();
                realm.beginTransaction();
                RealmList<Record> temp = new RealmList<Record>();
                temp.add(new Record(50, ActivityCategories.Resting.toString()));
                temp.add(new Record(75, ActivityCategories.Standing.toString()));
                temp.add(new Record(100, ActivityCategories.Walking.toString()));
                temp.add(new Record(125, ActivityCategories.Exercise.toString()));
                temp.add(new Record(150, ActivityCategories.Cycling.toString()));
                tempuser.getDayData().add(new DayData(sensDf.parse("2018-11-25T23:00:00"), sensDf.parse("2018-11-26T23:00:00"), temp));
                RealmList<Record> temp2 = new RealmList<Record>();
                temp2.add(new Record(60 * 8, ActivityCategories.Resting.toString()));
                temp2.add(new Record(300, ActivityCategories.Standing.toString()));
                temp2.add(new Record(400, ActivityCategories.Walking.toString()));
                temp2.add(new Record(550, ActivityCategories.Exercise.toString()));
                temp2.add(new Record(550, ActivityCategories.Cycling.toString()));
                tempuser.getDayData().add(new DayData(sensDf.parse("2018-11-17T23:00:00"), sensDf.parse("2018-11-18T23:00:00"), temp2));
                RealmList<Record> temp3 = new RealmList<Record>();
                temp3.add(new Record(60 * 8, ActivityCategories.Resting.toString()));
                temp3.add(new Record(20, ActivityCategories.Standing.toString()));
                temp3.add(new Record(300, ActivityCategories.Walking.toString()));
                temp3.add(new Record(0, ActivityCategories.Exercise.toString()));
                temp3.add(new Record(100, ActivityCategories.Cycling.toString()));
                tempuser.getDayData().add(new DayData(sensDf.parse("2018-11-16T23:00:00"), sensDf.parse("2018-11-17T23:00:00"), temp3));
                realm.commitTransaction();
                tempdao.saveUser(tempuser);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


}
