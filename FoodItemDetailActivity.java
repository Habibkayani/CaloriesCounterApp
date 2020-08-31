package com.example.calscounter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.DataBaseHandler;
import model.Food;

public class FoodItemDetailActivity extends AppCompatActivity {
private TextView foodname,calories,datetaken;
private Button share;
private int foodId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_detail);
        foodname=findViewById(R.id.detsFoodName);
        calories=findViewById(R.id.detsCaloriesValues);
        datetaken=findViewById(R.id.detsDateText);
        share=findViewById(R.id.detsShareButton);
        Food food=(Food) getIntent().getSerializableExtra("userObj");
        foodname.setText(food.getFoodName());
        calories.setText(String.valueOf(food.getCalories()));
        datetaken.setText(food.getRecordDate());
         foodId =food.getFoodId();
         calories.setTextSize(34.9f);
         calories.setTextColor(Color.RED);
         share.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 sharecall();
             }
         });


    }
    public void sharecall(){
        StringBuilder builder =new StringBuilder();
        String name= foodname.getText().toString();
        String cals=calories.getText().toString();
        String datetext=datetaken.getText().toString();

        builder.append("Food:" + name +"\n");
        builder.append("Calories:" + cals +"\n");
        builder.append("Eaten on:" + datetext);

        Intent i=new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_SUBJECT ,"My Calories Intake");
        i.putExtra(Intent.EXTRA_EMAIL, new String[] {"recipient@example.com"});
        i.putExtra(Intent.EXTRA_TEXT,builder.toString());
        try {
            startActivity(Intent.createChooser(i, "Send Email...."));
        }catch (ActivityNotFoundException ex){
            Toast.makeText(getApplicationContext(),"Please Email Client Before Sending",Toast.LENGTH_LONG).show();
        }

        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       
        int id = item.getItemId();

 
        if (id == R.id.delete_item) {
            AlertDialog.Builder b=new AlertDialog.Builder(FoodItemDetailActivity.this);
            b.setTitle("Delete?");
            b.setMessage("Are You Sure You Want To Delete This Item?");
            b.setNegativeButton("NO",null);
            b.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DataBaseHandler dbaa=new DataBaseHandler(getApplicationContext());
                    dbaa.deleteFood(foodId);
                    Toast.makeText(getApplicationContext(),"Food Item Deleted ",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(FoodItemDetailActivity.this,displayfoods.class));
                 
                    FoodItemDetailActivity.this.finish();


                }
            });
                    b.show();

        }

        return super.onOptionsItemSelected(item);
    }
}