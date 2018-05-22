package com.example.n009654.aidldemoserver;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioTrack;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n009654.aidldemoserver.BlankFragment.OnFragmentInteractionListener;
import com.ffpy.demo.IMyAidlInterface;
import com.squareup.leakcanary.RefWatcher;

import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements  OnFragmentInteractionListener{

    private TextView tv;
    private Button jupe;

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(MainActivity.this,"this is："+uri,Toast.LENGTH_SHORT).show();
    }

    class LeakThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(6 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LeakThread leakThread = new LeakThread();
        leakThread.start();
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
                    tv.setText(""+mStub.getBeauty());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(getApplicationContext(),WebViewActivity.class));
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        replace();
        //初始化数据存储
        HashSet<String> set = new HashSet<>();
        set.add("11");
        set.add("25");
        set.add("38");
        set.add("42");
        set.add("38");
        Log.i("ccc",""+set);
        if (set.contains("42")){
            Toast.makeText(getApplicationContext(),"哈哈",Toast.LENGTH_SHORT).show();
        }
        jupe = (Button) findViewById(R.id.judp);
        jupe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //replace();
                Intent intent1 = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent1);
                // showContent();
            }
        });

    }

    private void showContent() {
        BlankFragment fragment1 = (BlankFragment)getSupportFragmentManager().findFragmentById(R.id.fv_content);
        if (fragment1==null){
            Toast.makeText(getApplicationContext(),"null",Toast.LENGTH_SHORT).show();
        }else {
            fragment1.show("ccccccccccccccccc");
        }
    }

    private void replace() {
        BlankFragment fragment = new BlankFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fv_content, fragment,"ccc");
        fragmentTransaction.commit();
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
        RefWatcher refWatcher = LeakApplication.getRefWatcher(this);//1
        refWatcher.watch(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.menu_main, menu);
        Toast.makeText(getApplicationContext(),"onCreateOptionMenu",Toast.LENGTH_SHORT).show();
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu01:
                Toast.makeText(this, "设置", Toast.LENGTH_LONG).show();
                break;

            case R.id.menu02:
                Toast.makeText(this, "测试", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

}
