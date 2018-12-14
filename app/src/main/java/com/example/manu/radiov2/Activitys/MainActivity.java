package com.example.manu.radiov2.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Item;
import com.example.Program;
import com.example.manu.radiov2.Classes.StreamInfo.StreamingInfo;
import com.example.manu.radiov2.Interfaces.RadioAPI;
import com.example.manu.radiov2.adapter.DrawerAdapter;
import com.example.manu.radiov2.adapter.ProgramAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.example.manu.radiov2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnP,btnS;
    private DrawerLayout mDrawerLayout;
    private ArrayList<String> mopc = new ArrayList<>();
    private ListView lv,mDrawerList;
    private ImageView img;
    private TextView song,artist,album;
    private RadioAPI service;
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private boolean initialStage = true;
    private ArrayList<Item> items = new ArrayList<>();
    private final String STREAM = "http://procyon.shoutca.st:8232/;";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Declaracion de aspectos graficos
        lv = (ListView) findViewById(R.id.LVP);
        btnP = (ImageButton) findViewById(R.id.BPlay);
        btnS = (ImageButton) findViewById(R.id.BStop);
        song = (TextView) findViewById(R.id.Song);
        artist = (TextView) findViewById(R.id.Artist);
        album = (TextView) findViewById(R.id.Album);
        img = (ImageView) findViewById(R.id.AlbumImg);

        //animacion flash
        imgChange();
        //Menu desplegable
        setDrawerMenu();
        //Se prepara la radio
        mediaPlayer();
       //se obtiene la programacion
        conectionP();
    }

    //conexion con el stream en linea
    public void mediaPlayer(){
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
                        conectionM();

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
            }
        });
    }

    //Conexion para obtener la programacion
    public void conectionP(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("https://procyon.shoutca.st/recentfeed/yireh/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit2.create(RadioAPI.class);

        Call<Program> call2 = service.getProgram();
        call2.enqueue(new Callback<Program>() {
            @Override
            public void onResponse(Call<Program> call, Response<Program> response) {
                for (int i = 0; i< response.body().getItems().size();i++){
                    items.add(response.body().getItems().get(i));
                }
                ProgramAdapter adapter = new ProgramAdapter(getApplicationContext(),R.layout.adapter_view,items);
                lv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Program> call, Throwable t) {

            }
        });
    }

    //prepara el menu a ser mostrado a la izquierda de la pantalla
    public void setDrawerMenu(){
        ImageButton MenuBotton = (ImageButton) findViewById(R.id.Menu);
        mopc.add("¿Quienes Somos?");
        mopc.add("Redes Sociales");
        mopc.add("Plataforma Web");
        mopc.add("Programacion");
        mopc.add("Compartir Radio");
        mopc.add("Audios Grabados");
        mopc.add("Comentar fanpage");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new DrawerAdapter(this, R.layout.menu_adapter_view, mopc));

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),i+"",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,PopActivity.class));
            }
        });

        //ClickListener del boton con el icono del menu
        MenuBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    //Conexion para obtener la cancion actual
    public void conectionM(){
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
                LoadImageFromUrl(response.body().getData().get(0).getTrack().getImageurl());
                album.setText("Album: "+response.body().getData().get(0).getTrack().getAlbum());
                song.setText("Titulo: " + response.body().getData().get(0).getTrack().getTitle());
                artist.setText("Artista: "+response.body().getData().get(0).getTrack().getArtist());
            }

            @Override
            public void onFailure(Call<StreamingInfo> call, Throwable t) {
            }
        });
    }

    //Cargar imagen desde la url proporcionada
    private void LoadImageFromUrl(String imageurl) {
        Picasso.with(this).load(imageurl).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(img);
    }

    //pausar el stream
    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    //Hilo que maneja la conexion con el stream
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

    //Animaion de imagenFlash
    public void imgChange(){
        AnimationDrawable animation = new AnimationDrawable();
        animation.addFrame(getResources().getDrawable(R.drawable.image1), 5000);
        animation.addFrame(getResources().getDrawable(R.drawable.image2), 5000);
        animation.addFrame(getResources().getDrawable(R.drawable.image3), 5000);
        animation.setOneShot(false);

        ImageView imageAnim =  (ImageView) findViewById(R.id.flash);
        imageAnim.setBackgroundDrawable(animation);

        // start the animation!
        animation.start();
    }
}
