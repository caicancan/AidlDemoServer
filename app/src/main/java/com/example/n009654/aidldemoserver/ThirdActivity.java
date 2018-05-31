package com.example.n009654.aidldemoserver;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;


public class ThirdActivity extends AppCompatActivity {
    private static final String TAG = "ccc";

    /**
 * 添加了rxjava的学习
 * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //创建一个观察者,实现底下的三个方法
        final Observer<String> observer = new Observer<String>() {
            @Override public void onCompleted() {
                Log.i(TAG, "Completed"); }
                @Override public void onError(Throwable e)
                { Log.i(TAG, "Error");
                }
                @Override
                public void onNext(String s) {
                Log.i(TAG, s); }
        };
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "Item: " + s);
            }
            @Override public void onCompleted() {
                Log.d(TAG, "Completed!");
            }
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Error!");
            }
        };



       //创建被观察者observable,它有几种创建方式
        final Observable observable=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("哈哈哈");
            subscriber.onCompleted();
            }

        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //订阅相当于点击
            observable.subscribe(observer);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
