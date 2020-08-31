package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Food;

public class DataBaseHandler extends SQLiteOpenHelper {

    private final ArrayList<Food> foodlist=new ArrayList<>();
    public DataBaseHandler(@Nullable Context context) {
        super(context, Contants.DB_NAME, null, Contants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_GROCERY_TABLE = "CREATE TABLE " + Contants.TABLE_NAME + "("
                + Contants.KEY_ID + " INTEGER PRIMARY KEY," + Contants.FOOD_NAME + " TEXT,"
                + Contants.FOOD_CALORIES_NAME + " TEXT,"
                + Contants.DATE_NAME + " LONG);";

        sqLiteDatabase.execSQL(CREATE_GROCERY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Contants.TABLE_NAME);

        onCreate(sqLiteDatabase);

    }
    //get total Item saved
    public int getTotalItems() {
        int totalItems=0;
        String countQuery = "SELECT * FROM " + Contants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        totalItems=cursor.getCount();
cursor.close();
        return totalItems;
    }
    //get total calories count
    public int TotalCalories() {
        int cals=0;
        String countQuery = "SELECT SUM( " + Contants.FOOD_CALORIES_NAME + " ) " + "From " + Contants.TABLE_NAME ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.moveToNext()) {
            cals = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return cals;
    }
    //delete food
    public void deleteFood(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Contants.TABLE_NAME, Contants.KEY_ID + " = ?",
                new String[]{String.valueOf(id)});

        db.close();

    }
    //add content to db -add food
    public void addFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contants.FOOD_NAME, food.getFoodName());
        values.put(Contants.FOOD_CALORIES_NAME, food.getCalories());
        values.put(Contants.DATE_NAME, java.lang.System.currentTimeMillis());

        //Insert the row
        db.insert(Contants.TABLE_NAME, null, values);

        Log.d("Saved!!", "Saved to DB");
        db.close();



    }
    //get all food



    public ArrayList<Food>getFood() {
        foodlist.clear();
        SQLiteDatabase  db  = this.getReadableDatabase();



        Cursor cursor = db.query(Contants.TABLE_NAME, new String[]{
                Contants.KEY_ID, Contants.FOOD_NAME, Contants.FOOD_CALORIES_NAME,
                Contants.DATE_NAME}, null, null, null, null, Contants.DATE_NAME + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setFoodId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contants.KEY_ID))));
                food.setFoodName(cursor.getString(cursor.getColumnIndex(Contants.FOOD_NAME)));
                food.setCalories(cursor.getInt(cursor.getColumnIndex(Contants.FOOD_CALORIES_NAME)));

                //convert timestamp to something readable
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Contants.DATE_NAME)))
                        .getTime());

                food.setRecordDate(formatedDate);

                // Add to the groceryList
               foodlist.add(food);

            } while (cursor.moveToNext());
        }

        return foodlist;
    }



}
