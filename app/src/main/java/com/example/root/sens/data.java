package com.example.root.sens;


import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.Record;
import com.example.root.sens.dto.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
                goals.add(new Goal(ActivityCategories.Søvn.toString(), 10));
                goals.add(new Goal(ActivityCategories.Stå.toString(), 10));
                goals.add(new Goal(ActivityCategories.Gang.toString(), 10));
                goals.add(new Goal(ActivityCategories.Træning.toString(), 15));
                goals.add(new Goal(ActivityCategories.Cykling.toString(), 12));
                goals.add(new Goal(ActivityCategories.Skridt.toString(),10));
                tempuser.getGoals().add(new GoalHistory(1, df.parse("01/07/2019"), goals));

                RealmList<Goal> goals2 = new RealmList<>();
                goals2.add(new Goal(ActivityCategories.Søvn.toString(), 10));
                goals2.add(new Goal(ActivityCategories.Stå.toString(), 10));
                goals2.add(new Goal(ActivityCategories.Gang.toString(), 10));
                goals2.add(new Goal(ActivityCategories.Træning.toString(), 15));
                goals2.add(new Goal(ActivityCategories.Cykling.toString(), 16));
                goals2.add(new Goal(ActivityCategories.Skridt.toString(),10));
                tempuser.getGoals().add(new GoalHistory(2, df.parse("11/10/2018"), goals2));
                realm.commitTransaction();
                tempdao.saveUser(tempuser);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        DateFormat sensDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                UserDAO tempdao = UserDAO.getInstance();
                User tempuser = tempdao.getUserLoggedIn();
                realm.beginTransaction();
                RealmList<Record> temp = new RealmList<Record>();
                temp.add(new Record(50, ActivityCategories.Søvn.toString()));
                temp.add(new Record(75, ActivityCategories.Stå.toString()));
                temp.add(new Record(100, ActivityCategories.Gang.toString()));
                temp.add(new Record(125, ActivityCategories.Træning.toString()));
                temp.add(new Record(150, ActivityCategories.Cykling.toString()));
                temp.add(new Record(500,ActivityCategories.Skridt.toString()));

                RealmList<Record> temp2 = new RealmList<Record>();
                temp2.add(new Record(60 * 8, ActivityCategories.Søvn.toString()));
                temp2.add(new Record(300, ActivityCategories.Stå.toString()));
                temp2.add(new Record(400, ActivityCategories.Gang.toString()));
                temp2.add(new Record(550, ActivityCategories.Træning.toString()));
                temp2.add(new Record(550, ActivityCategories.Cykling.toString()));
                temp2.add(new Record(500,ActivityCategories.Skridt.toString()));
                RealmList<Record> temp3 = new RealmList<Record>();
                temp3.add(new Record(60 * 8, ActivityCategories.Søvn.toString()));
                temp3.add(new Record(20, ActivityCategories.Stå.toString()));
                temp3.add(new Record(300, ActivityCategories.Gang.toString()));
                temp3.add(new Record(0, ActivityCategories.Træning.toString()));
                temp3.add(new Record(100, ActivityCategories.Cykling.toString()));
                temp3.add(new Record(500,ActivityCategories.Skridt.toString()));
                RealmList<DayData> tempList = new RealmList<>();
                tempList.add(new DayData(sensDf.parse("2018-12-16T23:00:00"), sensDf.parse("2018-12-17T23:00:00"), temp3));
                tempList.add(new DayData(sensDf.parse("2018-12-17T23:00:00"), sensDf.parse("2018-12-18T23:00:00"), temp2));
                tempList.add(new DayData(sensDf.parse("2019-01-08T23:00:00"), sensDf.parse("2019-01-09T23:00:00"), temp));
                mergeData(tempuser, tempList);
                realm.commitTransaction();
                tempdao.saveUser(tempuser);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    private static void mergeData(User tempuser, RealmList<DayData> tempList) {
        for(DayData newDayData : tempList){
            boolean found = false;
            for(DayData existingDayData : tempuser.getDayData()){
                if(newDayData.getEnd_time().equals(existingDayData.getEnd_time())  && newDayData.getStart_time().equals(existingDayData.getStart_time())){
                    for (Record newRecords : newDayData.getRecords()){
                        for(Record existingRecord : existingDayData.getRecords()){
                            if(newRecords.getType().equals(existingRecord.getType())){
                                existingRecord.setValue(newRecords.getValue());
                                break;
                            }
                        }
                    }
                    found = true;
                    break;
                }
            }
            if(!found){
                tempuser.getDayData().add(newDayData);
            }
        }
    }


}
