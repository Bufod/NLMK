package com.example.myapplication;

import java.util.ArrayList;

public class RollArray {
    private ArrayList<Roll> rolls;
    private double avg;
    private String date_beg_born;
    RollArray(){
        rolls = new ArrayList<>();
    }
    RollArray(String date_beg_born){
        rolls = new ArrayList<>();
        this.date_beg_born = date_beg_born;
    }
    public double calcAvg(){
        double sum_temp = 0;
        if (rolls.isEmpty())
            return 0;
        for (int i = 0; i < rolls.size(); i++)
            sum_temp += rolls.get(i).vavg;
        avg = sum_temp/rolls.size();
        return avg;
    }

    public double getAvg() {
        return avg;
    }

    public String getDate_beg_born() {
        return date_beg_born;
    }
    public void addRoll(Roll roll){
        rolls.add(roll);
    }
    public Roll getRollInd(int ind){
        return rolls.get(ind);
    }
    public boolean isEmpty(){
        return rolls.isEmpty();
    }

    public void setDate_beg_born(String date_beg_born) {
        this.date_beg_born = date_beg_born;
    }
    public int size(){
        return rolls.size();
    }
    public void clear(){
        rolls.clear();
        avg = 0;
        date_beg_born = "";
    }
}
