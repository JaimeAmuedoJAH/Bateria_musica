package com.jah.bateria_musica;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SeekBar sbBarra;
    ImageView btnPlay, btnPause, btnStop, btnPlatilloIzq, btnBombo, btnPlatilloDer;
    ListView lvCanciones;
    Cancion[] arrCanciones;
    String[] arrTitulos;
    ArrayAdapter<String> adapter;
    static MediaPlayer mediaPlayer;
    SoundPool soundPool;
    static Runnable handlerTask;
    static Handler handler = new Handler();
    int sonido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initComponents();
        lvCanciones.setOnItemClickListener((adapterView, view, posicion, l) -> escucharCancion(posicion));
        btnPlatilloIzq.setOnClickListener(view -> soundPlatillo());
        btnPlatilloDer.setOnClickListener(view -> soundPlatillo());
        btnBombo.setOnClickListener(view -> soundBombo());
        btnPlay.setOnClickListener(view -> empezar());
        btnPause.setOnClickListener(view -> pausar());
        btnStop.setOnClickListener(view -> parar());
        sbBarra.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int posicion, boolean fromUser) {
                if(fromUser) mediaPlayer.seekTo(posicion);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initComponents() {
        sbBarra = findViewById(R.id.sbBarra);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);
        btnPlatilloIzq = findViewById(R.id.btnPlatilloIzq);
        btnPlatilloIzq.setImageResource(R.drawable.platilloizq);
        btnBombo = findViewById(R.id.btnBombo);
        btnBombo.setImageResource(R.drawable.bombo);
        btnPlatilloDer = findViewById(R.id.btnPlatilloDer);
        btnPlatilloDer.setImageResource(R.drawable.platilloder);
        lvCanciones = findViewById(R.id.lvCanciones);
        cargarArray();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrTitulos);
        lvCanciones.setAdapter(adapter);
        soundPool = new SoundPool.Builder().setMaxStreams(3).build();
        sonido = soundPool.load(this, R.raw.bombo, 1);
        sonido = soundPool.load(this, R.raw.platillo, 1);
    }

    private void pausar() {
        if(mediaPlayer.isPlaying()) mediaPlayer.pause();
    }

    private void parar() {
        if(mediaPlayer.isPlaying()) mediaPlayer.stop();
    }

    private void empezar() {
        if(!mediaPlayer.isPlaying()) mediaPlayer.start();
    }

    private void soundBombo() {
            soundPool.play(sonido, 1, 1, 0, 0, 1);
    }

    private void soundPlatillo() {
            soundPool.play(sonido, 1, 1, 0, 0, 1);
    }

    public static void startTimer(SeekBar sbBarra) {
        handlerTask = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    int position = mediaPlayer.getCurrentPosition();
                    sbBarra.setProgress(position);
                    sbBarra.setMax(mediaPlayer.getDuration());
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(handlerTask);
    }

    private void escucharCancion(int posicion) {
        mediaPlayer = new MediaPlayer();
        if(!mediaPlayer.isLooping()){
            mediaPlayer = MediaPlayer.create(this, arrCanciones[posicion].getCancion());
            mediaPlayer.start();
        }else{
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(this, arrCanciones[posicion].getCancion());
            mediaPlayer.start();
        }
        startTimer(sbBarra);
    }

    private void cargarArray() {
        arrCanciones = new Cancion[12];
        arrTitulos = new String[12];
        arrCanciones[0] = new Cancion("Ashes and blood", R.raw.to_ashes_and_blood);
        arrCanciones[1] = new Cancion("The line", R.raw.the_line);
        arrCanciones[2] = new Cancion("Cant hear it now", R.raw.can_t_hear_it_now);
        arrCanciones[3] = new Cancion("Heavy is the crown", R.raw.heavy_is_the_crown);
        arrCanciones[4] = new Cancion("Hellfire", R.raw.hellfire_);
        arrCanciones[5] = new Cancion("Enemy", R.raw.imagine_dragons);
        arrCanciones[6] = new Cancion("Ma meilleure ennemie", R.raw.ma_meilleure_ennemie);
        arrCanciones[7] = new Cancion("Paint the town blue", R.raw.paint_the_town_blue);
        arrCanciones[8] = new Cancion("Playground", R.raw.playground);
        arrCanciones[9] = new Cancion("Remember me", R.raw.remember_me);
        arrCanciones[10] = new Cancion("Sucker", R.raw.sucker);
        arrCanciones[11] = new Cancion("Wasteland", R.raw.wasteland);
        for(int i = 0;i < arrTitulos.length;i++){
            arrTitulos[i] = arrCanciones[i].getNombreCancion();
        }
    }
}