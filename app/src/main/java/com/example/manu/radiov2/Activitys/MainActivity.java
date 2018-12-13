package com.example.manu.radiov2.Activitys;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.manu.radiov2.Classes.AProgram;
import com.example.manu.radiov2.Classes.StreamingInfo;
import com.example.manu.radiov2.Classes.Track;
import com.example.manu.radiov2.Interfaces.RadioAPI;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.example.manu.radiov2.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnP,btnS;
    private TextView song,artist,album;
    private RadioAPI service;
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private boolean initialStage = true;
    private final String STREAM = "http://procyon.shoutca.st:8232/;";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaracion de aspectos graficos
        btnP = (ImageButton) findViewById(R.id.BPlay);
        btnS = (ImageButton) findViewById(R.id.BStop);
        song = (TextView) findViewById(R.id.Song);
        artist = (TextView) findViewById(R.id.Artist);
        album = (TextView) findViewById(R.id.Album);

        //Declaracion del media player
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        progressDialog = new ProgressDialog(this);
        //Listener para el boton Play(Pone a escuchar el stream)
        btnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playPause) {
                    //btn.setText("Pause Streaming");

                    if (initialStage) {
                        new Player().execute(STREAM);
                    } else {
                        if (!mediaPlayer.isPlaying())
                            mediaPlayer.start();
                    }

                    playPause = true;

                }
            }
        });
        //Listener para el boton Stop(Pone en pausa el stream)
        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playPause){
                    //btn.setText("Launch Streaming");

                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }

                    playPause = false;
                }
                conection();
            }
        });
    }


    public void conection(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://procyon.shoutca.st/rpc/yireh/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(RadioAPI.class);

        Call<StreamingInfo> call = service.getInfo();
        call.enqueue(new Callback<StreamingInfo>() {
            @Override
            public void onResponse(Call<StreamingInfo> call, Response<StreamingInfo> response) {
                Log.d("Token",response.body().getData().get(0).getTrack().getTitle());
                album.setText("Album: "+response.body().getData().get(0).getTrack().getAlbum());
                song.setText("Titulo: " + response.body().getData().get(0).getTrack().getTitle());
                artist.setText("Artista: "+response.body().getData().get(0).getTrack().getArtist());
            }

            @Override
            public void onFailure(Call<StreamingInfo> call, Throwable t) {
                System.out.print("manco");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;

            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage = true;
                        playPause = false;
                       // btn.setText("Launch Streaming");
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mediaPlayer.prepare();
                prepared = true;

            } catch (Exception e) {
                Log.e("MyAudioStreamingApp", e.getMessage());
                prepared = false;
            }

            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }

            mediaPlayer.start();
            initialStage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Buffering...");
            progressDialog.show();
        }
    }
}
