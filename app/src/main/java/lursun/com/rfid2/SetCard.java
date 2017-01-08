package lursun.com.rfid2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by admin on 2017/1/2.
 */
public class SetCard extends AppCompatActivity {
    static Handler handler=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_card);
        ListM.flag=false;
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                ((TextView)findViewById(R.id.cardview)).setText((String)msg.obj);
            }
        };
        ((TextView)findViewById(R.id.text)).setText("Please scanning IC card  No"+(CardList.mapvalue.size()+1)+"：");
    }
    public void SaveCard(View view){
        String s=((TextView)findViewById(R.id.cardview)).getText().toString();
        SQLite sqLite=new SQLite(getApplicationContext());
        SQLiteDatabase db= sqLite.getReadableDatabase();
        if(db.rawQuery("Select * from CardTable Where Card='"+s+"'",null).getCount()==0) {
            CardList.mapvalue.put(s, CardList.mapvalue.size() + 1);
            ContentValues cv = new ContentValues();
            cv.put("Card", s);
            cv.put("Number",CardList.mapvalue.size());
            db.insert("CardTable",null,cv);
            ((TextView)findViewById(R.id.text)).setText("Please scanning IC card  No"+(CardList.mapvalue.size()+1)+"：");
        }else {
            ((TextView)findViewById(R.id.cardview)).setText("Card has been recorded");
        }

    }

    @Override
    protected void onDestroy() {
        ListM.flag=true;
        super.onDestroy();
    }
}
