package com.example.foodapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import 	android.app.DownloadManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.SQLException;

public class ChooseActivity extends AppCompatActivity {

    Button _complexButton;
    Button _customButton;
    Button _downloadTXTButton;
    SQLiteDatabase mDb;
    int userID;
    TableLayout complex;
    SimpleCursorAdapter scAdapter;
    int ROWS = 6;
    int COLUMNS = 2;
    int restaurant_id=0;
    String food_id="";
    int rest_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        mDb = ((MyApplication) this.getApplication()).getmDb();
        complex = (TableLayout) findViewById(R.id.tl_complex);
        _customButton = (Button) findViewById(R.id.btn_custom_order);
        _complexButton = (Button) findViewById(R.id.btn_complex_order);
        _downloadTXTButton = (Button) findViewById(R.id.btn_download_txt);

        Intent intent = getIntent();
        userID = intent.getIntExtra("UserID", 1);
        restaurant_id = intent.getIntExtra(MainActivity.EXTRA_MESSAGE1,-1);

        Cursor cursor = mDb.rawQuery("SELECT category.name_category, menu_all.food_name" +
                        " FROM food_list INNER JOIN " +
                        "menu_all on food_list.id_food" +
                        " = menu_all.id_food inner join " +
                        "category on menu_all.id_category" +
                        " = category.id_category inner join restaurant on" +
                        " food_list.id_rest=restaurant.id_rest " +
                        "where restaurant.id_rest=? order by" +
                        " menu_all.id_category",
                new String[]{String.valueOf(restaurant_id)});
        cursor.moveToFirst();
        for (int i = 0; i < ROWS; i++) {

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < COLUMNS; j++) {
                TextView textView = new TextView(this);
                textView.setText(cursor.getString(j));
                tableRow.addView(textView, j);
            }
            cursor.moveToNext();
            complex.addView(tableRow, i);
        }

        cursor.close();
        final char dm = (char) 34;

        _complexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = mDb.rawQuery("SELECT category.name_category," +
                                " menu_all.id_food," +
                                " menu_all.cost, food_list.id_rest FROM food_list INNER JOIN " +
                                "menu_all on food_list.id_food" +
                                " = menu_all.id_food inner join " +
                                "category on menu_all.id_category" +
                                " = category.id_category inner join restaurant on" +
                                " food_list.id_rest=restaurant.id_rest " +
                                "where restaurant.id_rest=? order by" +
                                " menu_all.id_category",
                        new String[]{String.valueOf(restaurant_id)});
                cursor.moveToFirst();
                rest_id = cursor.getInt(3);
                do {
                    food_id=food_id+cursor.getString(1)+",";
                }while(cursor.moveToNext());
                food_id=food_id.substring(0,food_id.length()-1);
                cursor.moveToFirst();
                int cost = 180;
                mDb.execSQL("insert into orders(id_users,id_food,id_restaurant, cost) " +
                        "values (" + dm + userID + dm + "," + dm + food_id + dm + "," +
                        "" + dm + rest_id + dm + "," + dm + cost + dm + ")");
                cursor = mDb.rawQuery("select id_orders from orders " +
                        "where id_users=? and id_food=? and id_restaurant=? and cost=?",
                        new String[]{String.valueOf(userID),String.valueOf(food_id),
                                String.valueOf(rest_id),String.valueOf(cost)});
                Intent intent1 = new Intent(ChooseActivity.this,
                        OrderActivity.class);
                cursor.moveToFirst();
                intent1.putExtra("cost",cost);
                intent1.putExtra("ID", cursor.getInt(0));
                cursor.close();
                startActivity(intent1);
            }
        });

        _customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ChooseActivity.this, CustomOrder.class);
                intent2.putExtra("userID", userID);
                intent2.putExtra("restID", restaurant_id);
                startActivity(intent2);
            }
        });

        _downloadTXTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File foods = new File("Память устройства/Download/marks.txt");
                try {
                    FileOutputStream is = new FileOutputStream(foods);
                    OutputStreamWriter osw = new OutputStreamWriter(is);
                    Writer w = new BufferedWriter(osw);
                    Cursor cursor = mDb.rawQuery("SELECT category.name_category," +
                                    " menu_all.id_food," +
                                    " menu_all.cost, food_list.id_rest FROM food_list INNER JOIN " +
                                    "menu_all on food_list.id_food" +
                                    " = menu_all.id_food inner join " +
                                    "category on menu_all.id_category" +
                                    " = category.id_category inner join restaurant on" +
                                    " food_list.id_rest=restaurant.id_rest " +
                                    "where restaurant.id_rest=? order by" +
                                    " menu_all.id_category",
                            new String[]{String.valueOf(restaurant_id)});
                    cursor.moveToFirst();
                    Environment.getExternalStorageDirectory();
                    System.out.println(Environment.getExternalStorageDirectory());
                    w.write("haha");
                    w.close();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}