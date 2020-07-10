package com.example.managerapp.Supplier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.managerapp.R;

public class Report extends AppCompatActivity {

    TextView txtStallId, txtNumberFood, txtDeliveredOrder, txtCanceledOrder;
    TextView txtRevenue, txtNumberRating, txtNumberComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_layout);

        txtStallId = (TextView)findViewById(R.id.txtStallId);
        txtNumberFood = (TextView)findViewById(R.id.txtNumberFood);
        txtDeliveredOrder = (TextView)findViewById(R.id.txtNumberDeliveredOrder);
        txtCanceledOrder = (TextView)findViewById(R.id.txtNumberCanceledOrder);

        txtRevenue = (TextView)findViewById(R.id.txtRevenue);
        txtNumberRating = (TextView)findViewById(R.id.txtNumberRating);
        txtNumberComment = (TextView)findViewById(R.id.txtNumberComment);

        txtNumberComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Report.this, Comment.class));
            }
        });

    }
}