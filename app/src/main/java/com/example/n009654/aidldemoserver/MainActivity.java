package com.example.n009654.aidldemoserver;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import com.ffpy.demo.IMyAidlInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv;
    private Button jupe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jupe = (Button) findViewById(R.id.judp);
        jupe.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = new Intent();
                    //由于是隐式启动Service 所以要添加对应的action，A和之前服务端的一样。
                   intent.setAction("com.ffpy.demo.HelloService");
                    //android 5.0以后直设置action不能启动相应的服务，需要设置packageName或者Component。
                    intent.setPackage("com.ffpy.demo"); //packageName 需要和服务端的一致.
                   bindService(intent,serviceConnection,BIND_AUTO_CREATE);
        tv = (TextView) findViewById(R.id.tv);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mStub.show();
                    tv.setText(""+mStub.getBeauty().getName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(getApplicationContext(),WebViewActivity.class));
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    IMyAidlInterface mStub;
    private ServiceConnection serviceConnection = new ServiceConnection() {
           @Override
             public void onServiceConnected(ComponentName name, IBinder service) {
               //调用asInterface()方法获得IMyAidlInterface实例
                              mStub = IMyAidlInterface.Stub.asInterface(service);
                           if (mStub == null) {
                               Log.e("MainActivity", "the mStub is null");
                               } else {        //当mStub不为空就调用其add方法进行计算，并显示到TextView上面。
                                    try {
                                            int value = mStub.add(1, 8);
                                            tv.setText(value + "");
                                        } catch (RemoteException e) {
                                           e.printStackTrace();
                                        }
                                }
                       }

                   @Override
            public void onServiceDisconnected(ComponentName name) {

                        }
        };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.judp:

                break;
        }
    }
}
