package rlaskfud0515.kr.hs.emirim.dbex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText edit_group_Name,edit_count; Button init,insert,select;
    EditText edit_result_name,edit_result_count;
    MyDBHelper myhelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_group_Name=(EditText)findViewById(R.id.edit_groupName);
        edit_count=(EditText)findViewById(R.id.edit_count);
        edit_result_name=(EditText)findViewById(R.id.edit_result_name);
        edit_result_count=(EditText)findViewById(R.id.edit_result_count);
        init = (Button)findViewById(R.id.but_init);
        insert = (Button)findViewById(R.id.but_insert);
        select = (Button)findViewById(R.id.but_select);

        //DB 생성
        myhelper=new MyDBHelper(this);
        //기존의 테이블이 존재하면 삭제하고 테이블을 새로 생성한다.
        init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB=myhelper.getWritableDatabase();
                myhelper.onUpgrade(sqlDB,1,2);
                sqlDB.close();

            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB=myhelper.getWritableDatabase();
                String sql="insert into idolTable values('"+edit_group_Name.getText()+"',"+edit_count.getText()+")"; //문자열 값은 작은 따옴표
                sqlDB.execSQL(sql);
                sqlDB.close();
                Toast.makeText(MainActivity.this, "저장됨", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MyDBHelper extends SQLiteOpenHelper{
        public MyDBHelper(Context context){
            super(context, "groupDB", null, 1);
        }
        //idolTable이라는 이름의 테이블 생성
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase){
            String sql="create table idolTable(idolName text not null primary key, idolCount integer)";
            sqLiteDatabase.execSQL(sql);

        }
        //이미 idolTable이 존재한다면 기존의 테이블을 삭제하고 새로 테이블을 만들 때 호출
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
            String sql="drop table if exist idolTable";
            sqLiteDatabase.execSQL(sql);
            onCreate(sqLiteDatabase);

        }
    }
}
