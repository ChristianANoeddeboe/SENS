package com.example.root.sens;


import com.example.root.sens.dao.UserDAO;
import com.example.root.sens.dao.interfaces.IUserDao;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.Goal;
import com.example.root.sens.dto.GoalHistory;
import com.example.root.sens.dto.Record;
import com.example.root.sens.dto.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;

public class DemoData {
    public static void initializeData() {
        Realm realm = Realm.getDefaultInstance();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        try {
            IUserDao tempdao = UserDAO.getInstance();
            tempdao.deleteData();
            User tempuser = tempdao.getUserLoggedIn();
            realm.beginTransaction();
            RealmList<Goal> goals = new RealmList<>();
            goals.add(new Goal(ActivityCategories.Søvn.toString(), 480));
            goals.add(new Goal(ActivityCategories.Stå.toString(), 400));
            goals.add(new Goal(ActivityCategories.Gang.toString(), 400));
            goals.add(new Goal(ActivityCategories.Træning.toString(), 0));
            goals.add(new Goal(ActivityCategories.Cykling.toString(), 0));
            goals.add(new Goal(ActivityCategories.Skridt.toString(),3000));
            tempuser.getGoals().add(new GoalHistory(df.parse("01/17/2019"), goals));

            RealmList<Goal> goals2 = new RealmList<>();
            goals2.add(new Goal(ActivityCategories.Søvn.toString(), 350));
            goals2.add(new Goal(ActivityCategories.Stå.toString(), 100));
            goals2.add(new Goal(ActivityCategories.Gang.toString(), 0));
            goals2.add(new Goal(ActivityCategories.Træning.toString(), 150));
            goals2.add(new Goal(ActivityCategories.Cykling.toString(), 160));
            goals2.add(new Goal(ActivityCategories.Skridt.toString(),1000));
            tempuser.getGoals().add(new GoalHistory(df.parse("01/09/2019"), goals2));

            RealmList<Goal> goals3 = new RealmList<>();
            goals3.add(new Goal(ActivityCategories.Søvn.toString(), 600));
            goals3.add(new Goal(ActivityCategories.Stå.toString(), 0));
            goals3.add(new Goal(ActivityCategories.Gang.toString(), 500));
            goals3.add(new Goal(ActivityCategories.Træning.toString(), 550));
            goals3.add(new Goal(ActivityCategories.Cykling.toString(), 0));
            goals3.add(new Goal(ActivityCategories.Skridt.toString(),3500));
            tempuser.getGoals().add(new GoalHistory(df.parse("12/25/2018"), goals3));

            realm.commitTransaction();
            tempdao.saveUser(tempuser);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat sensDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        IUserDao tempdao = UserDAO.getInstance();
        User tempuser = tempdao.getUserLoggedIn();
        realm.beginTransaction();
        RealmList<DayData> tempList = new RealmList<>();
        Calendar cal = Calendar.getInstance();
        for(int i = 0; i < 30; i++){
            RealmList<Record> temp = new RealmList<Record>();
            if(i < 8) {
                temp.add(new Record(500, ActivityCategories.Søvn.toString()));
                temp.add(new Record(400, ActivityCategories.Stå.toString()));
                temp.add(new Record(400, ActivityCategories.Gang.toString()));
                temp.add(new Record(0, ActivityCategories.Træning.toString()));
                temp.add(new Record(0, ActivityCategories.Cykling.toString()));
                temp.add(new Record(3500, ActivityCategories.Skridt.toString()));
            }else if (i > 8 && i< 16){
                temp.add(new Record(300, ActivityCategories.Søvn.toString()));
                temp.add(new Record(100, ActivityCategories.Stå.toString()));
                temp.add(new Record(100, ActivityCategories.Gang.toString()));
                temp.add(new Record(125, ActivityCategories.Træning.toString()));
                temp.add(new Record(150, ActivityCategories.Cykling.toString()));
                temp.add(new Record(500, ActivityCategories.Skridt.toString()));
            }else{
                temp.add(new Record(600, ActivityCategories.Søvn.toString()));
                temp.add(new Record(0, ActivityCategories.Stå.toString()));
                temp.add(new Record(500, ActivityCategories.Gang.toString()));
                temp.add(new Record(550, ActivityCategories.Træning.toString()));
                temp.add(new Record(0, ActivityCategories.Cykling.toString()));
                temp.add(new Record(3500, ActivityCategories.Skridt.toString()));
            }
            Date d1 = cal.getTime(); // 22
            cal.add(Calendar.DATE,-1);
            Date d2 = cal.getTime(); // 21
            d1.setHours(23);
            d1.setMinutes(0);
            d1.setSeconds(0);
            d2.setHours(23);
            d2.setMinutes(0);
            d2.setSeconds(0);
            tempList.add(new DayData(d2, d1,temp));
        }
        mergeData(tempuser, tempList);
        realm.commitTransaction();
        tempdao.saveUser(tempuser);
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
