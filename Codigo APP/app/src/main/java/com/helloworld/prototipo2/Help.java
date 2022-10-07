package com.helloworld.prototipo2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.helloworld.prototipo2.objects.Memorias;

import java.io.FileInputStream;


public class Help extends AppCompatActivity {
    private ImageView imageView;
    private Gson gson= new Gson();
    private Memorias memoria= new Memorias();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        read();
        setContentView(R.layout.activity_help);

        imageView= findViewById(R.id.imageArquivo);
        TextView textDescricao= findViewById(R.id.textDescricao);
        TextView textTitulo= findViewById(R.id.textTitulo);
        Bitmap bitmap= memoria.getImagem();

        Log.d("BITMAP ", bitmap.toString());
        textTitulo.setText(memoria.getNome());
        textDescricao.setText(memoria.getDescricao());
        imageView.setImageBitmap(bitmap);

    }

    public void back(View View) {
        this.finish();
    }

    public void read() {
        String temp = "";
        try {
            FileInputStream fin = openFileInput("a");
            int c;

            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }

            memoria= gson.fromJson(temp, Memorias.class);

            Log.d("VALOR ARQUIVO", memoria.getNome().toString());

            Toast.makeText(Help.this, "file read " + temp, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}