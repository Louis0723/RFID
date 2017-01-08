package lursun.com.rfid2;

import android.os.Message;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by admin on 2017/1/4.
 */
public class TCP {
    ServerSocket ss=null;
    TCP(){
        try {
            ss = new ServerSocket(50000);
            work();
        }catch (Exception e){
            e=e;
        }
    }
    void stop(){
        try {
            ss.close();
        }catch (Exception e){
            e=e;
        }
    }
    void work(){
        new Thread()
        {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket socket = ss.accept();
                        job(socket);
                    }catch (Exception e){
                        e=e;
                    }
                }
            }
        }.start();
    }
    void job(Socket socket){
        try {
            byte value[] = new byte[10];
            Message message=new Message();
            InputStream is=socket.getInputStream();
            while (true) {

                while (true) {
                    is.skip(is.available());
                    is.read(value);
                    String s=""+value[6]+value[7]+value[8]+value[9];
                    if(ListM.flag) {
                        Integer table=new Integer(value[4]);
                        int t[]={table,CardList.mapvalue.get(s)};

                        if (!ListM.arrayTable.contains(new Integer(value[4]))) {
                            ListM.arrayList.add(t);
                            ListM.arrayTable.add(table);
                        }
                    }
                    else
                    {

                        message.obj=s;
                        SetCard.handler.sendMessage(message);
                    }

                    Thread.sleep(2000);

                }



            }
        }catch (Exception e){
            e=e;
        }
    }
}
