package com.example.drone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class input extends Activity {

    private Button btsure;
    private EditText usertall=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        usertall=(EditText)findViewById(R.id.Tall);
        btsure=(Button)findViewById(R.id.sure);
        btsure.setOnClickListener(new sure());
    }
    class sure implements View.OnClickListener {
        public void onClick(View v){
            String user_tall=usertall.getText().toString();
            Intent i=new Intent();
            Bundle bundle=new Bundle();
            bundle.putString("user_tall",user_tall);
            i.putExtras(bundle);
            i.setClass(input.this,MainActivity.class);
            startActivity(i);
        }
    }
}