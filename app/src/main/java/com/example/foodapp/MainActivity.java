package com.example.foodapp;

        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.Spinner;
        import android.widget.TextView;
        import androidx.appcompat.app.AppCompatActivity;
        import java.util.ArrayList;
        import java.util.List;
        import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    TextView textView1;
    TextView textView2;
    Spinner sp_location;
    int userID;
    @BindView(R.id.btn_location)
    Button _btn_location;
    public static final String EXTRA_MESSAGE = "userID";
    public static final String EXTRA_MESSAGE1 = "restaurant_name";
    ArrayAdapter<String> dataAdapter1;
    ArrayAdapter<String> dataAdapter2;
    ListView lv_restaurant;
    int mode=0;
    SQLiteDatabase mDb;

    //Метод который вызывается при создании страницы
    @Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Подключаем базу данных
        mDb = ((MyApplication) this.getApplication()).getmDb();

        textView1 = (TextView) findViewById(R.id.tv_location);
        textView2 = (TextView) findViewById(R.id.tv_restaurant);
        sp_location = (Spinner) findViewById(R.id.sp_location);
        _btn_location = (Button) findViewById(R.id.btn_location);
        lv_restaurant = (ListView) findViewById(R.id.lv_restaurant);

        Intent intent2 = getIntent();
        mode = intent2.getIntExtra("mode", -1);
        userID = intent2.getIntExtra("userID", -1);
        if (mode == 2) {
            Intent intent3 = new Intent(this, RestaurantActivity.class);
            intent3.putExtra("userID", userID);
            startActivity(intent3);
        } else {
            try {
                Cursor c = mDb.rawQuery("SELECT location_name FROM location",
                        null);
                List InventoryList = new ArrayList<>();
                int i = 0;
                if (c.moveToFirst()) {
                    do {
                        InventoryList.add(i, c.getString(0));
                        i += 1;
                    } while (c.moveToNext());
                }
                c.close();

                dataAdapter1 = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, InventoryList);
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_location.setAdapter(dataAdapter1);
            } catch (Exception e) {
            }

            _btn_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Cursor c = mDb.rawQuery("SELECT rest_name FROM restaurant inner join" +
                                        " location on " +
                                        "restaurant.id_restaurant_location" +
                                        "=location.id_restaurant_location" +
                                        " where location_name=?",
                                new String[]{sp_location.getSelectedItem().toString()});
                        List InventoryList = new ArrayList<>();
                        int i = 0;
                        if (c.moveToFirst()) {
                            do {
                                InventoryList.add(i, c.getString(0));
                                i += 1;
                            } while (c.moveToNext());
                        }
                        c.close();

                        dataAdapter2 = new ArrayAdapter<String>(MainActivity.this,
                                android.R.layout.simple_spinner_item, InventoryList);
                        dataAdapter2.setDropDownViewResource
                                (android.R.layout.simple_spinner_dropdown_item);
                        lv_restaurant.setAdapter(dataAdapter2);
                        lv_restaurant.setVisibility(View.VISIBLE);
                        textView2.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                    }
                }
            });
            lv_restaurant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    String text = parent.getItemAtPosition(position).toString();
                    Cursor cursor = mDb.rawQuery("select id_rest from restaurant " +
                            "where rest_name = ?", new String[]{text});
                    Intent intent = new Intent(MainActivity.this,
                            ChooseActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, userID);
                    cursor.moveToFirst();
                    intent.putExtra(EXTRA_MESSAGE1, cursor.getInt(0));
                    cursor.close();
                    startActivity(intent);
                }
            });
        }
    }
}