package com.example.foodapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class CustomOrder extends AppCompatActivity {

    int userID;
    Spinner spinner_drinks;
    Spinner spinner_soups;
    Spinner spinner_deserts;
    Spinner spinner_main_dishes;
    Spinner spinner_garnires;
    Spinner spinner_other;
    Spinner spinner_salads;
    Button button_order;
    int restaurant_id;
    SQLiteDatabase mDb;
    public static final String EXTRA_MESSAGE = "userID";
    public static final String EXTRA_MESSAGE1 = "drinks";
    public static final String EXTRA_MESSAGE2 = "soups";
    public static final String EXTRA_MESSAGE3 = "deserts";
    public static final String EXTRA_MESSAGE4 = "salads";
    public static final String EXTRA_MESSAGE5 = "main_dishes";
    final char dm = (char) 34;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customorder);

        Intent intent = getIntent();
        userID = intent.getIntExtra("userID", -1);
        restaurant_id = intent.getIntExtra("restID",-1);

        spinner_drinks = (Spinner) findViewById(R.id.sp_drinks);
        spinner_deserts = (Spinner) findViewById(R.id.sp_deserts);
        spinner_soups = (Spinner) findViewById(R.id.sp_soups);
        spinner_main_dishes = (Spinner) findViewById(R.id.sp_main_dishes);
        spinner_garnires = (Spinner) findViewById(R.id.sp_garnires);
        spinner_other = (Spinner) findViewById(R.id.sp_other);
        spinner_salads = (Spinner) findViewById(R.id.sp_salads);
        button_order = (Button) findViewById(R.id.bt_custom);
        mDb = ((MyApplication) this.getApplication()).getmDb();

        try {
            Cursor c = mDb.rawQuery("SELECT food_name FROM menu_all where id_category=1",
                    null);
            List InventoryList = new ArrayList<>();
            InventoryList.add(0,"Ничего");
            int i = 1;
            if (c.moveToFirst()) {
                do {
                    InventoryList.add(i, c.getString(0));
                    i += 1;
                } while (c.moveToNext());
            }
            c.close();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, InventoryList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_drinks.setAdapter(dataAdapter);
        } catch (Exception e) {
        }
        try {
            Cursor c = mDb.rawQuery("SELECT food_name FROM menu_all where  id_category=2",
                    null);
            List InventoryList = new ArrayList<>();
            InventoryList.add(0,"Ничего");
            int i = 1;
            if (c.moveToFirst()) {
                do {
                    InventoryList.add(i, c.getString(0));
                    i += 1;
                } while (c.moveToNext());
            }
            c.close();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, InventoryList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_soups.setAdapter(dataAdapter);
        } catch (Exception e) {
        }
        try {
            Cursor c = mDb.rawQuery("SELECT food_name FROM menu_all where  id_category=3",
                    null);
            List InventoryList = new ArrayList<>();
            InventoryList.add(0,"Ничего");
            int i = 1;
            if (c.moveToFirst()) {
                do {
                    InventoryList.add(i, c.getString(0));
                    i += 1;
                } while (c.moveToNext());
            }
            c.close();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, InventoryList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_deserts.setAdapter(dataAdapter);
        } catch (Exception e) {
        }
        try {
            Cursor c = mDb.rawQuery("SELECT food_name FROM menu_all where  id_category=4",
                    null);
            List InventoryList = new ArrayList<>();
            InventoryList.add(0,"Ничего");
            int i = 1;
            if (c.moveToFirst()) {
                do {
                    InventoryList.add(i, c.getString(0));
                    i += 1;
                } while (c.moveToNext());
            }
            c.close();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, InventoryList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_salads.setAdapter(dataAdapter);
        } catch (Exception e) {
        }
        try {
            Cursor c = mDb.rawQuery("SELECT food_name FROM menu_all where  id_category=5",
                    null);
            List InventoryList = new ArrayList<>();
            InventoryList.add(0,"Ничего");
            int i = 1;
            if (c.moveToFirst()) {
                do {
                    InventoryList.add(i, c.getString(0));
                    i += 1;
                } while (c.moveToNext());
            }
            c.close();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, InventoryList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_main_dishes.setAdapter(dataAdapter);
        } catch (Exception e) {
        }
        try {
            Cursor c = mDb.rawQuery("SELECT food_name FROM menu_all where  id_category=7",
                    null);
            List InventoryList = new ArrayList<>();
            InventoryList.add(0,"Ничего");
            int i = 1;
            if (c.moveToFirst()) {
                do {
                    InventoryList.add(i, c.getString(0));
                    i += 1;
                } while (c.moveToNext());
            }
            c.close();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, InventoryList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_garnires.setAdapter(dataAdapter);
        } catch (Exception e) {
        }
        try {
            Cursor c = mDb.rawQuery("SELECT food_name FROM menu_all where  id_category=6",
                    null);
            List InventoryList = new ArrayList<>();
            InventoryList.add(0,"Ничего");
            int i = 1;
            if (c.moveToFirst()) {
                do {
                    InventoryList.add(i, c.getString(0));
                    i += 1;
                } while (c.moveToNext());
            }
            c.close();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, InventoryList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_other.setAdapter(dataAdapter);
        } catch (Exception e) {
        }
        button_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = mDb.rawQuery("SELECT id_food,cost FROM menu_all" +
                                " where menu_all.food_name=? or menu_all.food_name=? or" +
                                " menu_all.food_name=? or menu_all.food_name=? or" +
                                " menu_all.food_name=? or menu_all.food_name=? or " +
                                "menu_all.food_name=?",
                        new String[]{
                                spinner_drinks.getSelectedItem().toString(),
                                spinner_deserts.getSelectedItem().toString(),
                                spinner_garnires.getSelectedItem().toString(),
                                spinner_main_dishes.getSelectedItem().toString(),
                                spinner_other.getSelectedItem().toString(),
                                spinner_salads.getSelectedItem().toString(),
                                spinner_soups.getSelectedItem().toString()
                });
                cursor.moveToFirst();
                int cost=0;
                String food_id="";
                do {
                    cost=cost+cursor.getInt(1);
                    food_id=food_id+cursor.getString(0)+",";
                }while(cursor.moveToNext());
                food_id=food_id.substring(0,food_id.length()-1);
                mDb.execSQL("insert into orders(id_users,id_food,id_restaurant, cost) values (" +
                        "" + dm + userID + dm + "," + dm + food_id + dm + "," + dm + restaurant_id
                        + dm + "," + dm + cost + dm + ")");
                cursor = mDb.rawQuery("select id_orders from orders where id_users=? " +
                        "and id_food=? and id_restaurant=? and cost=?",
                        new String[]{
                                String.valueOf(userID),
                                food_id,
                                String.valueOf(restaurant_id),
                                String.valueOf(cost)
                });
                Intent intent1 = new Intent(CustomOrder.this, OrderActivity.class);
                cursor.moveToFirst();
                intent1.putExtra("cost",cost);
                intent1.putExtra("ID",cursor.getInt(0));
                cursor.close();
                startActivity(intent1);
            }
        });
    }
}