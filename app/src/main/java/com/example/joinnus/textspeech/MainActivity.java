package com.example.joinnus.textspeech;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {


    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.txtBot0)
    TextView txtBot0;
    @Bind(R.id.User)
    TextView User;
    @Bind(R.id.txtBot1)
    TextView txtBot1;
    @Bind(R.id.User1)
    TextView User1;
    @Bind(R.id.txtBot2)
    TextView txtBot2;
    @Bind(R.id.User2)
    TextView User2;
    @Bind(R.id.txtBot3)
    TextView txtBot3;
    @Bind(R.id.User3)
    TextView User3;
    @Bind(R.id.fab1)
    FloatingActionButton fab1;
    @Bind(R.id.btnstop)
    FloatingActionButton btnstop;
    @Bind(R.id.btnplay)
    FloatingActionButton btnplay;
    /*@Bind(R.id.ctv_text)
    TextView ctv_text;*/
    /*@Bind(R.id.btnGrabar)
    Button btnGrabar;
    @Bind(R.id.btnStopRecord)
    Button btnStopRecord;
    @Bind(R.id.PlayGrabacion)
    Button PlayGrabacion;*/
    private TextToSpeech textToSpeech;
    private SoundPool soundPool;
    private int idMusic;
    public int next = 0;
    public int usernext = 0;
    private static final String LOG_TAG = "Grabadora";
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;
    private MediaPlayer playerRecord;
    private MediaRecorder mediaRecorder;
    private String fichero0 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audio0.3gp";
    private String fichero1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audio1.3gp";
    private String fichero2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audio2.3gp";
    private String fichero3 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audio3.3gp";
    boolean loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().setMaxStreams(10).build();
        } else {
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }
        idMusic = soundPool.load(getApplicationContext(), R.raw.papu, 0);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.papu);
        mediaPlayer2 = MediaPlayer.create(getApplicationContext(), R.raw.pista2);
        //EditText editText = ; // your version of EditText object.


        textToSpeech = new TextToSpeech(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.fab)
    public void playSpeech() {

       // detenerGrabacion();
        //textToSpeech.setLanguage(Locale.US);
        if (fab1.isEnabled()) {
            fab.setVisibility(View.GONE);
            fab1.setVisibility(View.VISIBLE);
        }

        //soundPool.play(idMusic,1,1,1,0,1);

        //mediaPlayer.start();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            txtBot0.setTextColor(Color.GREEN);
            textToSpeech.speak(txtBot0.getText().toString(), TextToSpeech.QUEUE_ADD, null, null);
            next++;
        } else {

            switch (next) {
                case 0:
                    new Thread(new Runnable() {
                        public void run() {
                            mediaPlayer2.setVolume(0.3f,0.3f);
                            mediaPlayer2.start();
                        }
                    }).start();
                    txtBot0.setTextColor(Color.GREEN);
                    textToSpeech.speak(txtBot0.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                    next++;
                    break;
                 case 1:
                     detenerGrabacion();
                     new Thread(new Runnable() {
                         public void run() {

                             mediaPlayer2.start();
                         }
                     }).start();
                    txtBot1.setTextColor(Color.GREEN);
                    textToSpeech.speak(txtBot1.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                    next++;
                    break;
                case 2:
                    detenerGrabacion() ;
                    fab.setVisibility(View.GONE);
                    fab1.setVisibility(View.GONE);
                    btnplay.setVisibility(View.VISIBLE);
                   /* txtBot2.setTextColor(Color.GREEN);
                    textToSpeech.speak(txtBot2.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                    next++;*/
                    break;
                /*case 3:

                    txtBot3.setTextColor(Color.GREEN);
                    textToSpeech.speak(txtBot3.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                    next++;
                    break;*/
            }

        }


        /*while (textToSpeech.isSpeaking()) {
              fab.setEnabled(false);
                fab1.setEnabled(true);
        }*/
        // fab.setEnabled(true);
        /*if(!fab.isEnabled()){
            fab.setEnabled(true);
        }*/

    }

    @OnClick(R.id.btnplay)
    public void playRecord(){

        if (btnstop.isEnabled()){
            btnplay.setVisibility(View.GONE);
            btnstop.setVisibility(View.VISIBLE);
        }

        new Thread(new Runnable() {
            public void run() {
                mediaPlayer2.setVolume(0.2f,0.2f);
                mediaPlayer2.start();
            }
        }).start();

        textToSpeech.speak(txtBot0.getText().toString(), TextToSpeech.QUEUE_ADD, null);

    }

    @OnClick(R.id.btnstop)
    public void stopplay(){



        if (btnplay.isEnabled()){
            btnstop.setVisibility(View.GONE);
            btnplay.setVisibility(View.VISIBLE);
        }

        new Thread(new Runnable() {
            public void run() {
                mediaPlayer2.start();
            }
        }).start();

        reproducir(fichero0);

    }

    @OnClick(R.id.fab1)
    public void playNex() {

        if (fab.isEnabled()) {
            fab1.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
        }


        switch (usernext) {
            case 0:
                new Thread(new Runnable() {
                    public void run() {
                        mediaPlayer2.start();
                    }
                }).start();
                User.setTextColor(Color.BLUE);
                grabar(fichero0);
                usernext++;
                break;
            case 1:
                new Thread(new Runnable() {
                    public void run() {
                        mediaPlayer2.start();
                    }
                }).start();
                User1.setTextColor(Color.BLUE);
                grabar(fichero1);
                usernext++;

                break;
            /*case 2:

                User2.setTextColor(Color.BLUE);
                grabar(fichero2);
                usernext++;
                break;
            case 3:

                User3.setTextColor(Color.BLUE);
                grabar(fichero3);



                usernext++;
                break;*/
        }

    }

    @Override
    public void onInit(int status) {
        if (status == textToSpeech.SUCCESS) {
            int lenguage = textToSpeech.setLanguage(Locale.ENGLISH);
            if (lenguage == TextToSpeech.LANG_MISSING_DATA || lenguage == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getApplicationContext(), "SUCCES", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }

        } else {

        }

    }

    // @OnClick(R.id.btnGrabar)
    public void grabar(String audiRecord) {

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setOutputFile(audiRecord);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Fallo en grabación");
        }
        mediaRecorder.start();


    }

    //  @OnClick(R.id.btnStopRecord)
    public void detenerGrabacion() {
        mediaRecorder.stop();
        mediaRecorder.release();
    }

    // @OnClick(R.id.PlayGrabacion)
    public void reproducir(String ficher) {

        new Thread(new Runnable() {
            public void run() {
                mediaPlayer2.setVolume(0.1f, 0.1f);
                mediaPlayer2.start();

            }
        }).start();

        playerRecord = new MediaPlayer();
        try {
            playerRecord.setDataSource(ficher);
            playerRecord.prepare();
            playerRecord.setVolume(1.0f, 1.0f);
            playerRecord.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Fallo en reproducción");
        }
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




}

