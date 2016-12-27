package com.mingwei.swipeback;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by mingwei on 12/23/16.
 */
public class SimpleActivity extends BaseActivity {

    private TextView mText1;

    private Button mBtn2;

    private Button mBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_layout);
        mText1 = (TextView) findViewById(R.id.text1);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn3 = (Button) findViewById(R.id.btn3);

        mText1.setText("ActivityStack_size=" +
                ((SwipeBackApplication) getApplication()).getBackHelper().getSize());

        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHelper().addPreviousView();
            }
        });

        mBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), SimpleActivity.class));
            }
        });
    }
}
