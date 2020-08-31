package data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.calscounter.FoodItemDetailActivity;
import com.example.calscounter.R;

import java.util.ArrayList;
import java.util.List;

import model.Food;

public class CustomListViewAdapter extends ArrayAdapter<Food> {
    private Activity activity;
    private int layoutresources;
    private List<Food>foodList = new ArrayList<>();


    public CustomListViewAdapter(Activity act,int resource,ArrayList<Food> data) {
        super(act, resource, data);

        layoutresources = resource;
        activity = act;
        foodList = data;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Nullable
    @Override
    public Food getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public int getPosition(@Nullable Food item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;
        if (row == null || (row.getTag() == null)) {

            LayoutInflater inflater = LayoutInflater.from(activity);
            row = inflater.inflate(layoutresources, null);

            holder = new ViewHolder();
            holder.foodname = row.findViewById(R.id.name1);
            holder.foodcalories = row.findViewById(R.id.Calories1);
            holder.fooddate = row.findViewById(R.id.dateText1);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.food=getItem(position);
        holder.foodname.setText(holder.food.getFoodName());
        holder.fooddate.setText(holder.food.getRecordDate());
        holder.foodcalories.setText(String.valueOf(holder.food.getCalories()));
        final  ViewHolder fnalHolder=holder;

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(activity, FoodItemDetailActivity.class);
                Bundle b=new Bundle();
                b.putSerializable("userObj",fnalHolder.food);
                i.putExtras(b);
                activity.startActivity(i);

            }
        });


        return row;
    }

    public class ViewHolder {


        Food food;
        TextView foodname;
        TextView foodcalories;
        TextView fooddate;
    }
}
