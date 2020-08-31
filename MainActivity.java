package com.example.calscounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DataBaseHandler;
import model.Food;

public class MainActivity extends AppCompatActivity {

    private EditText foodname,foodcalories;
    private Button submitt;
    DataBaseHandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new DataBaseHandler(MainActivity.this);
        foodname=findViewById(R.id.foodEditText);
        foodcalories=findViewById(R.id.caloriesEditText);
        submitt=findViewById(R.id.submittButton);
        submitt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savetodb();
            }
        });







    }

    private void savetodb() {

        Food food=new Food();
        String name=foodname.getText().toString().trim();
        String calname=foodcalories.getText().toString().trim();
        int cals= Integer.parseInt(calname);

        if(name.equals("") || calname.equals("")){
            Toast.makeText(getApplicationContext(),"No empty field allowed",Toast.LENGTH_LONG).show();
        }
        else{

            food.setFoodName(name);
            food.setCalories(cals);
            db.addFood(food);
            db.close();

            foodname.setText("");
            foodcalories.setText("");
            startActivity(new Intent(MainActivity.this,displayfoods.class));
        }
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