package com.example.administrator.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.R;
import com.example.administrator.util.DBHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DBHelper dbHelper;
    //定义三个输入框
    private EditText name, number, desc;
    //定义两个按钮
    private Button submit, look;
    //定义一个RadioGroup
    private RadioGroup radio;
    private String nameStr, numberStr, descStr;
    private String sexStr = "男";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();
        Init();
        setListener();
    }

    /*界面的初始化工作*/
    private void Init() {
        //实例化DBHelper
        dbHelper = new DBHelper(getApplicationContext());
    }

    /*为控件设置事件监听*/
    private void setListener() {
        radio.setOnCheckedChangeListener(listener);
        submit.setOnClickListener(this);
        look.setOnClickListener(this);
    }

    //单选群组的监听
    private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio0) {
                RadioButton r = (RadioButton) group.findViewById(checkedId);
                sexStr = r.getText().toString();
            }
            if (checkedId == R.id.radio1) {
                RadioButton r = (RadioButton) group.findViewById(checkedId);
                sexStr = r.getText().toString();
            }
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button1:
                if (name.getText().toString().length() != 0) {
                    nameStr = name.getText().toString();
                } else {
                    showToast("姓名不能为空");
                    return;
                }
                if (number.getText().toString().length() != 0) {
                    numberStr = number.getText().toString();
                } else {
                    showToast("电话号码不能为空");
                    return;
                }
                if (desc.getText().toString().length() != 0) {
                    descStr = desc.getText().toString();
                } else {
                    showToast("备注不能为空");
                    return;
                }

                //实例化一个ContentValues， ContentValues是以键值对的形式，键是数据库的列名，值是要插入的值
                ContentValues values = new ContentValues();
                values.put("name", nameStr);
                values.put("sex", sexStr);
                values.put("number", numberStr);
                values.put("desc", descStr);

                //调用insert插入数据库
                long result = dbHelper.insert(values);
                if(result!=0){
                    showToast("插入成功");
                }
                //将三个输入框重置下
                reset();

                break;
            case R.id.button2:
                Intent intent = new Intent();
                intent.setClass(this, ResultActivity.class);
                startActivity(intent);
                break;
        }
    }

    //重置edittext
    private void reset(){
        name.setText("");
        number.setText("");
        desc.setText("");
    }
    //提示信息
    private void showToast(String showText) {
        Toast.makeText(getApplicationContext(), showText, Toast.LENGTH_SHORT).show();
    }

    /*实例化布局文件的控件*/
    private void findViewById() {
//根据id 获取到相对应的控件
        name = (EditText) findViewById(R.id.editText1);
        number = (EditText) findViewById(R.id.editText2);
        desc = (EditText) findViewById(R.id.editText3);
        submit = (Button) findViewById(R.id.button1);
        look = (Button) findViewById(R.id.button2);
        radio = (RadioGroup) findViewById(R.id.radioGroup1);
    }
}
