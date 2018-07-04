package com.example.myfriends.retrofit_api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView myListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myListView=(ListView)findViewById(R.id.hero_ListId);

        //first step to create the instance of the retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //to get the api class object that helps to get the object of Json
        Api api=retrofit.create(Api.class);

        //this will call the method of the Api interface
        Call<List<Hero>> call=api.getHeroes();

        //last step to get the value from the json object and perform the certain operation
        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {

                //body method returns the all the data of inside the json object after that you can extract the data using the key value
                List<Hero> hero=response.body();    //get the list of heroes

                ArrayList<String> heroList=new ArrayList<String>();

                for(int i=0;i<hero.size();i++)
                {
                    heroList.add(hero.get(i).getName());
                }

                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,heroList);
                myListView.setAdapter(arrayAdapter);

            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {

                Toast.makeText(MainActivity.this,"Error is"+t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }
}
