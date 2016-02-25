package com.example.administrator.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.R;
import com.example.administrator.entity.Person;
import com.example.administrator.util.DBHelper;

import java.util.ArrayList;
import java.util.List;
/*
       1.复用view
       2.使用viewholder
       3.listview高度设置成match-parent
       4.
 */
public class ResultActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    List<Person> bookList = new ArrayList<>();
    DBHelper dbHelper;
    private DbAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        findViewById();
        Init();
        setListener();
    }

    /*界面的初始化工作*/
    private void Init() {
        bookList = queryData();
        //实例化DbAdapter
        adapter = new DbAdapter(getApplication());
        listView.setAdapter(adapter);
    }

    /*为控件设置事件监听*/
    private void setListener() {
        listView.setOnItemClickListener(this);
    }

    /*实例化布局文件的控件*/
    private void findViewById() {
//获取ListView
        listView = (ListView) findViewById(R.id.listView1);
    }

    //查询数据库，将每一行的数据封装成一个person 对象，然后将对象添加到List中
    private List<Person> queryData() {
        List<Person> list = new ArrayList<>();
        dbHelper = new DBHelper(this);

        //调用query()获取Cursor
        Cursor c = dbHelper.queryAll();
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            String sex = c.getString(c.getColumnIndex("sex"));
            String number = c.getString(c.getColumnIndex("number"));
            String desc = c.getString(c.getColumnIndex("desc"));
            Log.e("queryData",id+" "+name+" "+sex+" "+number+" "+desc);
            Person p = new Person();
            p.setId(id);
            p.setName(name);
            p.setSex(sex);
            p.setNumber(number);
            p.setDesc(desc);

            list.add(p);
        }
        return list;
    }

    //自定义DbAdapter
    public class DbAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;

        public DbAdapter(Context context) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
        }

        //刷新适配器
        public void refresh() {
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return bookList.size();
        }

        @Override
        public Object getItem(int position) {
            return bookList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.activity_listview_item, null);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.sex = (TextView) convertView.findViewById(R.id.sex);
                holder.number = (TextView) convertView.findViewById(R.id.number);
                holder.desc = (TextView) convertView.findViewById(R.id.desc);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Person p = bookList.get(position);
            holder.name.setText(p.getName());
            holder.sex.setText(p.getSex());
            holder.number.setText(p.getNumber());
            holder.desc.setText(p.getDesc());

            return convertView;
        }


        public class ViewHolder {
            public TextView name;
            public TextView sex;
            public TextView number;
            public TextView desc;

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final Person p = bookList.get(position);
        final long temp = id;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("真的要删除该记录？").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //调用delete（）删除某条数据
                int result = dbHelper.delete(p.getId());
                if (result != 0) {
                    bookList.remove(p);
                }
                //重新刷新适配器
                adapter.refresh();
            }
        }).setNegativeButton("否", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();


        // 关闭数据库
        dbHelper.close();
    }

    //提示信息
    private void showToast(String showText) {
        Toast.makeText(getApplicationContext(), showText, Toast.LENGTH_SHORT).show();
    }
}
