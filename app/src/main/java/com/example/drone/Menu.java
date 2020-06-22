package com.example.drone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends Activity {

    public String mode_state="mode:";
    public String follow_state="100";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final Button stablize_bt=(Button)findViewById(R.id.stablize);
        final Button loiter_bt=(Button)findViewById(R.id.loiter);
        final Button circle_bt=(Button)findViewById(R.id.circle);
        final Button land_bt=(Button)findViewById(R.id.land);
        final Button follow_bt=(Button)findViewById(R.id.follow);
        final Button back_bt=(Button)findViewById(R.id.back);

        stablize_bt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode_state="1";
                Intent intent = new Intent();
                intent.setClass(Menu.this, MainActivity.class);
                intent.putExtra("mode_state",mode_state);
                startActivity(intent);
            }
        });
        loiter_bt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode_state="2";
                Intent intent = new Intent();
                intent.setClass(Menu.this, MainActivity.class);
                intent.putExtra("mode_state",mode_state);
                startActivity(intent);
            }
        });
        circle_bt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode_state="3";
                Intent intent = new Intent();
                intent.setClass(Menu.this, MainActivity.class);
                intent.putExtra("mode_state",mode_state);
                startActivity(intent);
            }
        });
        land_bt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode_state="4";
                Intent intent = new Intent();
                intent.setClass(Menu.this, MainActivity.class);
                intent.putExtra("mode_state",mode_state);
                startActivity(intent);
            }
        });
        follow_bt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode_state="5";
                Intent intent = new Intent();
                intent.setClass(Menu.this, MainActivity.class);
                intent.putExtra("mode_state",mode_state);
                startActivity(intent);
            }
        });
        /*back_bt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(Menu.this , MainActivity.class);
                startActivity(intent1);
            }
        });*/
        }
    }