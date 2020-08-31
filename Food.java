package model;

import java.io.Serializable;

public class Food implements Serializable {
    private  static final long serialVersionUID=10L;
    private String foodName;
    private int foodId;
    private  int calories;
    private String recordDate;

    public Food(String food,int cals,int fid,String date)
    {
        foodName =food;
        calories =cals;
        recordDate =date;
        foodId = fid;

    }
    public Food(){

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
}
