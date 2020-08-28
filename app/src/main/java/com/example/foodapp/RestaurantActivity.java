package com.example.foodapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public class RestaurantActivity extends AppCompatActivity {

    SQLiteDatabase mDb;
    int userID;
    TableLayout orders;
    Button btn_delete;
    Button btn_refresh;
    final char dm = (char) 34;

    @Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);


        //Подключаем базу данных
        mDb = ((MyApplication) this.getApplication()).getmDb();

        orders = (TableLayout) findViewById(R.id.tl_complex);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_refresh = (Button) findViewById(R.id.btn_refresh);

        Intent intent = getIntent();
        userID = intent.getIntExtra("userID", -1);
        System.out.println(userID);
        Cursor cursor = mDb.rawQuery("select Name from users where id_users=?",
                new String[]{String.valueOf(userID)});
        cursor.moveToFirst();
        String user_name = cursor.getString(0);
        cursor.close();
        cursor = mDb.rawQuery("SELECT id_orders, id_food, users.Name, cost from orders " +
                        "inner join users on orders.id_users=users.id_users " +
                        "inner join restaurant on rest_name=?",
                new String[]{user_name});
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams
                    (TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < 4; j++) {
                TextView textView = new TextView(this);
                if (cursor.getColumnIndex("id_food") == j) {
                    String str_name = "";
                    String[] str_id = cursor.getString(j).split(",");
                    for (int k = 0; k < str_id.length; k++) {
                        Cursor cursor_str = mDb.rawQuery("select food_name from menu_all " +
                                "where id_food=?", new String[]{str_id[k]});
                        cursor_str.moveToFirst();
                        str_name = str_name + cursor_str.getString(0) + ",";
                        cursor_str.close();
                    }
                    str_name = str_name.substring(0, str_name.length() - 1);
                    textView.setText(str_name);
                    tableRow.addView(textView, j);
                } else {
                    textView.setText(cursor.getString(j));
                    tableRow.addView(textView, j);
                }

            }
            cursor.moveToNext();
            orders.addView(tableRow, i);
        }
        cursor.close();
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = mDb.rawQuery("select * from orders order by id_orders",
                        new String[]{});
                cursor.moveToFirst();
                mDb.execSQL("DELETE from orders where id_orders=" + dm
                        + cursor.getString(0) + dm + "");
            }
        });
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor = mDb.rawQuery("select Name from users where id_users=?",
                        new String[]{String.valueOf(userID)});
                cursor.moveToFirst();
                String user_name = cursor.getString(0);
                cursor.close();
                cursor = mDb.rawQuery("SELECT id_orders, id_food, users.Name, cost " +
                                "from orders inner join users on orders.id_users=users.id_users" +
                                " inner join restaurant on rest_name=?",
                        new String[]{user_name});
                cursor.moveToFirst();
                for (int i = 0; i < orders.getChildCount(); i = 0) {
                    View child = orders.getChildAt(0);
                    if (child != null && child instanceof TableRow) {
                        TableRow row = (TableRow) child;
                        row.removeAllViews();
                        orders.removeViewAt(i);
                    }
                }
                for (int i = 0; i < cursor.getCount(); i++) {
                    TableRow tableRow = new TableRow(RestaurantActivity.this);
                    tableRow.setLayoutParams(new TableLayout.LayoutParams
                            (TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));

                    for (int j = 0; j < 4; j++) {
                        TextView textView = new TextView(RestaurantActivity.this);
                        if (cursor.getColumnIndex("id_food") == j) {
                            String str_name = "";
                            String[] str_id = cursor.getString(j).split(",");
                            for (int k = 0; k < str_id.length; k++) {
                                Cursor cursor_str = mDb.rawQuery("select food_name" +
                                        " from menu_all where id_food=?", new String[]{str_id[k]});
                                cursor_str.moveToFirst();
                                str_name = str_name + cursor_str.getString(0) + ",";
                                cursor_str.close();
                            }
                            str_name = str_name.substring(0, str_name.length() - 1);
                            textView.setText(str_name);
                            tableRow.addView(textView, j);
                        } else {
                            textView.setText(cursor.getString(j));
                            tableRow.addView(textView, j);
                        }

                    }
                    cursor.moveToNext();
                    orders.addView(tableRow, i);
                }
                cursor.close();
            }
        });
    }
}