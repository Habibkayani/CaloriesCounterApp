package com.example.calscounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.CustomListViewAdapter;
import data.DataBaseHandler;
import model.Food;
import util.Util;

public class displayfoods extends AppCompatActivity {

   private DataBaseHandler db;
    private ArrayList<Food> dbFood =new ArrayList<>();
    private CustomListViewAdapter foodAdapter;
    private ListView listView;
    private Food myfood;
    private TextView totalcals,totalfood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayfoods);

        listView=findViewById(R.id.list);
        totalcals=findViewById(R.id.totalamountTextView);
        totalfood=findViewById(R.id.totalItemTextView);
        refreshdata();

    }

    private void refreshdata() {

dbFood.clear();
db=new DataBaseHandler(getApplicationContext());
    ArrayList<Food> foodfromdb=db.getFood();
int calsvalue=db.TotalCalories();
int totalitems=db.getTotalItems();

String formattedcals= Util.formatnumber(calsvalue);
String formattedtotalitems= Util.formatnumber(totalitems);
totalcals.setText("Total Calories: "+formattedcals);
        totalfood.setText("Total Calories: "+formattedtotalitems);


        for (int i=0;i<foodfromdb.size();i++){

         String name= foodfromdb.get(i).getFoodName();
            int cals= foodfromdb.get(i).getCalories();
            String date= foodfromdb.get(i).getRecordDate();
            int FoodId= foodfromdb.get(i).getFoodId();

            Log.v("Food IDS: ",String.valueOf(FoodId));

            myfood=new Food();
            myfood.setFoodName(name);
            myfood.setCalories(cals);
            myfood.setRecordDate(date);
            myfood.setFoodId(FoodId);
            dbFood.add(myfood);
        }
        db.close();
 
        foodAdapter =new CustomListViewAdapter(displayfoods.this,R.layout.list_row,dbFood);
        listView.setAdapter(foodAdapter);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}