package hfad.com.videoplayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends Activity {
        private MediaPlayer mediaPlayer;
        private SurfaceView surfaceView;

        @Override
        public void onPointerCaptureChanged(boolean hasCapture) {

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);


                mediaPlayer = new MediaPlayer();
                surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
                Button play = (Button)findViewById(R.id.play);
                final Button pause = (Button)findViewById(R.id.pause);
                Button stop=(Button)findViewById(R.id.stop);


                play.setOnClickListener(new OnClickListener() {

                        @SuppressLint("SdCardPath")
                        @Override
                        public void onClick(View v) {
                                mediaPlayer.reset();
                                try {
                                        mediaPlayer.setDataSource("/sdcard/Movies/1.mp4");//将需要播放的资源路径交给MediaPlayer实体
                                        mediaPlayer.setDisplay(surfaceView.getHolder());//让MediaPlayer去获取解析资源，是同步方法
                                        mediaPlayer.prepare();//调用prepare()方法准备，在prepare()方法后，可以直接开始播放
                                        mediaPlayer.start();//进入准备完成状态后，调用start()方法开始播放,
                                        pause.setText("暂停");
                                        pause.setEnabled(true);
                                }catch(IllegalArgumentException e) {
                                        e.printStackTrace();
                                }catch(SecurityException e) {
                                        e.printStackTrace();
                                }catch(IllegalStateException e) {
                                        e.printStackTrace();
                                }catch(IOException e) {
                                        e.printStackTrace();
                                }

                        }
                });

                stop.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                                if(mediaPlayer.isPlaying()){
                                        mediaPlayer.stop();
                                        pause.setEnabled(false);
                                }

                        }
                });

                pause.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                                if(mediaPlayer.isPlaying()){
                                        mediaPlayer.pause();
                                        ((Button)v).setText("继续");
                                }else{
                                        mediaPlayer.start();
                                        ((Button)v).setText("暂停");
                                }

                        }
                });

                mediaPlayer.setOnCompletionListener(new OnCompletionListener(){
                        //媒体播放完毕时会触发。但是当OnErrorLister返回false，或者MediaPlayer没有设置OnErrorListener时，这个监听也会被触发。
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                                Toast.makeText(MainActivity.this, "视频播放完毕！", Toast.LENGTH_SHORT).show();
                        }
                });

        }
        @Override
        protected void onDestroy() {
                if(mediaPlayer.isPlaying()){
                        mediaPlayer.stop();
                }
                mediaPlayer.release();
                super.onDestroy();
        }

}

