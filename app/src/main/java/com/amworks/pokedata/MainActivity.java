package com.amworks.pokedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomRecyclerAdapter customRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PokemonData> pokeData;   //List for storing Details of Pokemon

    RequestQueue rq;
    int range[]={0,20};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu

        //Shared Preferences for storing user's choice
        SharedPreferences sharedPref = getSharedPreferences("Memory",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String sortBy="Sort By:";

        switch (item.getItemId()) {
            case R.id.Id:
                Collections.sort(pokeData, new Comparator<PokemonData>() {
                    @Override
                    public int compare(PokemonData o1, PokemonData o2) {
                        return o1.getId()-o2.getId();
                    }
                });
                editor.putString(sortBy,String.valueOf(R.id.Id));
                editor.apply();
                return true;

            case R.id.Name:
                Collections.sort(pokeData, new Comparator<PokemonData>(){
                    @Override
                    public int compare(PokemonData o1, PokemonData o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                editor.putString(sortBy,String.valueOf(R.id.Name));
                editor.apply();
                return true;
            case R.id.r1:
                range[0]=0;
                range[1]=30;
                editor.putString(sortBy,String.valueOf(R.id.Id));
                editor.apply();
                pokeData.clear();
                sendRequest(range);
                return true;
            case R.id.r2:
                range[0]=30;
                range[1]=60;
                editor.putString(sortBy,String.valueOf(R.id.Id));
                editor.apply();
                pokeData.clear();
                sendRequest(range);
                return true;
            case R.id.r3:
                range[0]=60;
                range[1]=90;
                editor.putString(sortBy,String.valueOf(R.id.Id));
                editor.apply();
                pokeData.clear();
                sendRequest(range);
                return true;
            case R.id.r4:
                range[0]=90;
                range[1]=120;
                editor.putString(sortBy,String.valueOf(R.id.Id));
                editor.apply();
                pokeData.clear();
                sendRequest(range);
                return true;
            case R.id.r5:
                range[0]=120;
                range[1]=151;
                editor.putString(sortBy,String.valueOf(R.id.Id));
                editor.apply();
                pokeData.clear();
                sendRequest(range);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pokeData=new ArrayList<>();

        //testing for custom data
//        pokeData.add(new PokemonData("bulbasaur",50,1,"grass poison","https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",20));

        rq=Volley.newRequestQueue(this);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        sendRequest(range);

    }

    private int[] range(int x)
    {
        int ret[]=new int[2];
        switch (x)
        {
            case 1:
                ret[0]=0;
                ret[1]=30;
            return ret;
            case 2:
                ret[0]=30;
                ret[1]=60;
                return ret;
            case 3:
                ret[0]=60;
                ret[1]=90;
                return ret;
            case 4:
                ret[0]=90;
                ret[1]=120;
                return ret;
            case 5:
                ret[0]=120;
                ret[1]=151;
                return ret;
        }
        return ret;
    }

    private void sendRequest(int []arr)
    {
        for(int i=arr[0];i<arr[1];i++) {

            String url = "https://pokeapi.co/api/v2/pokemon/";
            url = url + (i + 1);
            String finalUrl = url;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, finalUrl,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            PokemonData newPokemon=new PokemonData();
                            newPokemon.setPokeURL(finalUrl);
                            try {
                                JSONObject objName= (JSONObject) response.getJSONArray("forms").get(0);
                                String name=objName.getString("name");
                                newPokemon.setName(name);    //NAME
                                int height=response.getInt("height");
                                newPokemon.setHeight(height);    //HEIGHT
                                int weight=response.getInt("weight");
                                newPokemon.setWeight(weight);  //WEIGHT
                                JSONArray types=response.getJSONArray("types");
                                String whichType="";
                                for(int i=0;i<types.length();i++)
                                {
                                    JSONObject type= (JSONObject) types.get(i);
                                    whichType+=type.getJSONObject("type").getString("name")+" ";
                                }
                                newPokemon.setType(whichType);   //TYPE
                                int id=response.getInt("id");
                                newPokemon.setId(id);    //ID
                                JSONObject img=response.getJSONObject("sprites");
                                String imgURL=img.getString("front_default");
                                newPokemon.setImageURL(imgURL);   //Image

                                Log.d("poke_data_success",height+" "+weight+" "+id+" \n"+imgURL+"\n"+"   PokeName:"+newPokemon.getName());  //testing for errors in parsing
                            } catch (JSONException e) {
                                Log.d("errorLoading","Error in parsing (for individual pokemon)!!!");
                            }

                            pokeData.add(newPokemon);

                            /////////////////
                            SharedPreferences sharedPref=getSharedPreferences("Memory",MODE_PRIVATE);

                            String memory=sharedPref.getString("Sort By:","");

                            Log.d("memory","..qwerty..........="+memory);  //testing

                            if(memory.equals(String.valueOf(R.id.Id)))
                            {
                                Collections.sort(pokeData, new Comparator<PokemonData>() {
                                    @Override
                                    public int compare(PokemonData o1, PokemonData o2) {
                                        return o1.getId()-o2.getId();
                                    }
                                });
                                Log.d("memory","inside main-Id");
                            }
                            else if(memory.equals(String.valueOf(R.id.Name)))
                            {
                                Collections.sort(pokeData, new Comparator<PokemonData>(){
                                    @Override
                                    public int compare(PokemonData o1, PokemonData o2) {
                                        return o1.getName().compareTo(o2.getName());
                                    }
                                });
                                Log.d("memory","inside main-Name");
                            }
                            else {
                                Log.d("memory","Error,memory="+memory+"\n..................");
                            }
                            ///////////////////////
                            customRecyclerAdapter=new CustomRecyclerAdapter(MainActivity.this,pokeData,recyclerView);
                            recyclerView.setAdapter(customRecyclerAdapter);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("errorLoading","Oops, Something went Wrong!");
                }
            });

            rq.add(jsonObjectRequest);

        }
    }

    }

