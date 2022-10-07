package com.helloworld.prototipo2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.helloworld.prototipo2.objects.Memorias;

import java.io.FileOutputStream;
import java.io.IOException;


public class CreateMem extends AppCompatActivity {
    private EditText text_descricao, text_titulo;
    private String descricao, titulo;
    private ImageView imageView;
    private TextView addImgText;
    private Memorias memoria;
    private Gson gson= new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mem);

        text_descricao = findViewById(R.id.inputTextoMemoria);
        text_titulo= findViewById(R.id.MemoriaTitulo);
        addImgText= findViewById(R.id.TextoAddImg);
        imageView = findViewById(R.id.imageView);
        memoria= new Memorias();

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });
    }

    public void help(View View){
        Intent Intent = new Intent(this,Help.class);
        startActivity(Intent);
    }

    public void save(View View){

        descricao = text_descricao.getText().toString();
        titulo= text_titulo.getText().toString();

        memoria.setNome(titulo);
        memoria.setDescricao(descricao);

        try {
            FileOutputStream fOut = openFileOutput(titulo, Context.MODE_PRIVATE);
            String json= gson.toJson(memoria);
            fOut.write(json.getBytes());
            fOut.close();
            Toast.makeText(getBaseContext(),"file saved", Toast.LENGTH_SHORT).show();
            Log.d("ARQUIVO: ", getApplicationContext().getFilesDir().getPath() + "/" + titulo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void back(View View){
        this.finish();
    }

    private void imageChooser()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(

            new ActivityResultContracts.StartActivityForResult(), result -> {

                if (result.getResultCode() == Activity.RESULT_OK) {

                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {

                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap= null;

                        try {
                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        memoria.setImagem(selectedImageBitmap);
                        imageView.setImageBitmap(memoria.getImagem());
                        addImgText.setVisibility(View.GONE);
                    }
                }
            });
}