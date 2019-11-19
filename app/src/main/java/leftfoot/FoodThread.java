package leftfoot;

import android.util.Log;

import com.example.soldout.client.TcpConnection;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class FoodThread extends Thread {

    public FoodData foodData;
    private String text;

    public FoodThread(String text){
        super();

        this.text = text;
        this.foodData = null;
    }

    @Override
    public void run() {

        try {

            //ソケット作成
            Socket socket = new Socket(TcpConnection.ip, TcpConnection.port);

            //出力ストリーム作成
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            //リクエスト送信
            objectOutputStream.writeObject(text);   //ストリーム書き込み
            objectOutputStream.flush();             //書き込み内容送信

            //入力ストリーム作成
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            //返信待ち
            Object received;
            if((received = objectInputStream.readObject()) != null){
                //型確認
                if (received instanceof FoodData){
                    this.foodData = (FoodData) received;
                    Log.d("user", "received: \n" + this.foodData.toString());

                }else{
                    Log.d("user", "received: not FoodData");
                }
            }

            //閉じる
            socket.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
