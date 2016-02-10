package com.brioal.mediarecodertest;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
//TODO 笔记 知识点
public class MainActivity extends AppCompatActivity {
    private File file ;
    private MediaRecorder mRecorder ;
    private Button btn_play ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "MediaRecorder 测试类", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        btn_play = (Button) findViewById(R.id.main_btn_start);

    }

    @Override
    protected void onDestroy() {
        if (file != null && file.exists()) {
            mRecorder.stop();
            //释放资源
            mRecorder.release();
            mRecorder = null;
        }
        super.onDestroy();
    }

    public void record(View view) {
        if (file == null) {
            btn_play.setText("Stop");
            File extral = Environment.getExternalStorageDirectory();
            try {
                file = new File(extral.getCanonicalPath() + "/sound.amr");
                mRecorder = new MediaRecorder();
                //设置声音来源
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                //设置声音输出格式 必须在设置声音编码格式之前设置
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                //设置声音编码格式
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                //设置输出目录
                mRecorder.setOutputFile(file.getAbsolutePath());
                mRecorder.prepare();
                mRecorder.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            btn_play.setText("Start");
            mRecorder.stop();
            mRecorder.release();
        }


    }
}
