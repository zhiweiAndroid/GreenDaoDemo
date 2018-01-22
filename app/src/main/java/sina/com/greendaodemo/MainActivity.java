package sina.com.greendaodemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afa.tourism.greendao.gen.UserDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private UserDao dao;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private EditText et1;
    private EditText et2;
    private EditText et3;
    private EditText et4;
    private List<User> listsUser=new ArrayList<>();
    private List<User> tempUserList=new ArrayList<>();
    private ListView lv;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = AFaApplication.getApplication().getDaoSession().getUserDao();
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);
        lv = (ListView) findViewById(R.id.lv);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        adapter = new MyAdapter(this,tempUserList);
        lv.setAdapter(adapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                insertDB();
                break;
            case R.id.btn2:
                deleteuerById(1);
                break;
            case R.id.btn3:
                updateData(0);
                break;
            case R.id.btn4:
                queryData();
                break;

        }
    }

    // 增
    private void insertDB() {
        String name = et1.getText().toString().trim();
        String age = et2.getText().toString().trim();
        String sex = et3.getText().toString().trim();
        String salary = et4.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(age)) {
            Toast.makeText(this, "年龄不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(sex)) {
            Toast.makeText(this, "性别不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(salary)) {
            Toast.makeText(this, "薪水不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(null, name, age, sex, salary);
        dao.insert(user);
        listsUser = dao.loadAll();
        tempUserList.clear();
        for (int i = 0; i < listsUser.size(); i++) {
            tempUserList.add(listsUser.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    //删
    private void deleteuerById(long id) {
        dao.deleteByKey(id);
    }

    //改
    private void updateData(int i) {
        String name = et1.getText().toString().trim();
        String age = et2.getText().toString().trim();
        String sex = et3.getText().toString().trim();
        String salary = et4.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(age)) {
            Toast.makeText(this, "年龄不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(sex)) {
            Toast.makeText(this, "性别不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(salary)) {
            Toast.makeText(this, "薪水不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (listsUser != null && !listsUser.isEmpty()) {
            User updateData = new User(listsUser.get(i).getId(), name, age, sex, salary);
            dao.update(updateData);
        }

    }

    //查
    private void queryData() {
        listsUser = dao.loadAll();
        String userName = "";
        for (int i = 0; i < listsUser.size(); i++) {
            userName += listsUser.get(i).getName() + ",";
        }
        Toast.makeText(this, "查询全部数据==>" + userName, Toast.LENGTH_SHORT).show();
    }

    private class MyAdapter extends BaseAdapter {
        private List<User> datas;
        private Context context;

        public MyAdapter(Context context, List<User> datas) {
            this.context = context;
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas != null ? datas.size() : 0;
        }

        @Override
        public Object getItem(int i) {
            return datas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertview, ViewGroup viewGroup) {
            View view = null;
            MyHolder holder = null;
            if (convertview == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
                holder = new MyHolder(view);
                view.setTag(holder);
            } else {
                view = convertview;
                holder= (MyHolder) view.getTag();
            }
            User user = datas.get(position);
            if (user!=null){
                Long id = user.getId();
                String name = user.getName();
                String age = user.getAge();
                String sex = user.getSex();
                String salary = user.getSalary();
                holder.tId.setText(""+id);
                holder.tName.setText(name);
                holder.tAge.setText(age);
                holder.tSex.setText(sex);
                holder.tSalary.setText(salary);
            }

            return view;
        }

    }

    static class MyHolder {
        TextView tId;
        TextView tName;
        TextView tAge;
        TextView tSex;
        TextView tSalary;

        public MyHolder(View view) {
            tId = view.findViewById(R.id.tv_id);
            tName = view.findViewById(R.id.tv_name);
            tAge = view.findViewById(R.id.tv_age);
            tSex = view.findViewById(R.id.tv_sex);
            tSalary = view.findViewById(R.id.tv_salary);
        }
    }


}
