package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {

    TextView tv_orderID;
    Button btn_onMain;
    int order_id;
    int cost=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        tv_orderID = (TextView) findViewById(R.id.tv_location);
        btn_onMain = (Button) findViewById(R.id.btn_onMain);

        Intent intent = getIntent();
        cost=intent.getIntExtra("cost",-1);
        order_id = intent.getIntExtra("ID",-1);
        tv_orderID.setText("Номер вашего заказа: "+order_id+"\n"+"Цена: "+cost+"\n Запомните его и предъявите его в столовой.");
        btn_onMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(OrderActivity.this,LoginActivity.class);
                startActivity(intent2);
            }
        });
    }
}