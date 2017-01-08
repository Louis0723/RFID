package lursun.com.rfid2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Button> tableButton=new ArrayList<Button>();

    TCP tcp=new TCP();

    public Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        ListM.flag=true;
        SQLite sqLite=new SQLite(getApplicationContext());
        SQLiteDatabase db=sqLite.getReadableDatabase();
        Cursor c=db.rawQuery("Select Card,Number From CardTable Order by Number",null);
        if(c.moveToFirst()) {
            do {
                CardList.mapvalue.put(c.getString(0), c.getInt(1));
            } while (c.moveToNext());
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LayoutInflater inflater=getLayoutInflater();
        LinearLayout root=(LinearLayout) findViewById(R.id.root);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        for(int i=0;i<=4;i++){
            LinearLayout ll=(LinearLayout) inflater.inflate(R.layout.tmp_row,null);
            for(int j=0,d=i*10;j<10;j++) {
                ((Button) ((LinearLayout) ll.getChildAt(j)).getChildAt(0)).setText(d + Integer.parseInt(((Button) ((LinearLayout) ll.getChildAt(j)).getChildAt(0)).getText().toString()) + "");
                tableButton.add((Button) ((LinearLayout) ll.getChildAt(j)).getChildAt(1));
            }
            root.addView(ll,param);
        }

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                int table[]=(int[])msg.obj;
                for(int i=0;i<50;i++){

                    if(table[i]!=Integer.parseInt(tableButton.get(i).getText().toString().equals("")?"0":tableButton.get(i).getText().toString()))
                    {
                        if(table[i]==0)
                        {
                            tableButton.get(i).setText("");
                            tableButton.get(i).setBackgroundColor(Color.rgb(170,170,255));
                        }else {
                            tableButton.get(i).setText(table[i]+"");
                            tableButton.get(i).setBackgroundColor(Color.rgb(255,255,170));
                        }
                    }

                }
            }
        };
        reshow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent=new Intent();
            intent.setClass(MainActivity.this,SetCard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                MainActivity.this.startActivity(intent);
            }catch (Exception e){
                e=e;
            }


        }

        return super.onOptionsItemSelected(item);
    }
    public void reshow(){
        new Thread()
        {
            @Override
            public void run() {

                Message msg = new Message();
                int table[] = new int[50];
                while (true) {
                    if(ListM.flag) {
                        for(int i=50;--i>=0;)
                        {
                            table[i]=0;
                        }
                        for (int[] value : ListM.arrayList) {
                            try {
                                table[value[0] - 1] = value[1];
                            }catch (Exception e){
                                e=e;
                            }
                        }
                        msg.obj = table;
                        handler.sendMessage(msg);
                        ListM.arrayList.clear();
                        ListM.arrayTable.clear();
                        try {
                            Thread.sleep(5000);
                        } catch (Exception e) {
                            e = e;
                        }
                    }
                }
            }
        }.start();

    }

}
