package com.example.manu.radiov2.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manu.radiov2.Classes.StreamInfo.StreamingInfo;
import com.example.manu.radiov2.Interfaces.RadioAPI;
import com.example.manu.radiov2.Services.PlayerService;
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
    private static ProgressDialog progressDialog;
    private boolean initialStage = true;
    private ArrayList<Integer> items = new ArrayList<>();

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
        final Intent intent = new Intent(this,PlayerService.class);
        progressDialog = new ProgressDialog(this);

        //Listener para el boton Play(Pone a escuchar el stream)
        btnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playPause) {

                    //btn.setText("Pause Streaming");
                    progressDialog.setMessage("Cargando...");
                    progressDialog.show();
                    startService(intent);
                    conectionM();

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

                    stopService(intent);

                    playPause = false;
                }
            }
        });
    }

    //clase que se llama en el service, para poder cancelar la barra de progreso
    public static void cancelDialog(){
        if (progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    //Conexion para obtener la programacion
    public void conectionP(){
        items.add(R.drawable.program1);
        items.add(R.drawable.program2);
        items.add(R.drawable.program3);
        items.add(R.drawable.program4);
        items.add(R.drawable.program5);
        items.add(R.drawable.program6);
        items.add(R.drawable.program7);
        ProgramAdapter adapter = new ProgramAdapter(this,R.layout.program_adapter_view,items);
        lv.setAdapter(adapter);
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

    //Animaion de imagenFlash
    public void imgChange(){
        AnimationDrawable animation = new AnimationDrawable();
        animation.addFrame(getResources().getDrawable(R.drawable.image11), 5000);
        animation.addFrame(getResources().getDrawable(R.drawable.image22), 5000);
        animation.addFrame(getResources().getDrawable(R.drawable.image33), 5000);
        animation.setOneShot(false);

        ImageView imageAnim =  (ImageView) findViewById(R.id.flash);
        imageAnim.setBackgroundDrawable(animation);

        // start the animation!
        animation.start();
    }
}
