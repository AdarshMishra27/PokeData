package com.amworks.pokedata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PokemonDeatil extends AppCompatActivity {

    ImageView photo;
    TextView idT,nameT,heightT,weightT,typeT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_deatil);

        idT=findViewById(R.id.idPoke);
        nameT=findViewById(R.id.namePoke);
        heightT=findViewById(R.id.heightPoke);
        weightT=findViewById(R.id.weightPoke);
        typeT=findViewById(R.id.typePoke);
        photo=findViewById(R.id.photoPokemon);

        Intent intent = getIntent();

        String name=intent.getStringExtra("name");
        int height=intent.getIntExtra("height",0);
        int id=intent.getIntExtra("id",0);
        String type=intent.getStringExtra("type");
        String imageURL=intent.getStringExtra("imageURL");
        int weight=intent.getIntExtra("weight",0);

        idT.setText("ID:#"+String.valueOf(id));
        heightT.setText("Height:"+String.valueOf(height));
        weightT.setText("Weight:"+String.valueOf(weight));
        nameT.setText("Name:"+name);
        typeT.setText("Type:"+type);
        Glide.with(this).load(imageURL).into(photo);

    }
}