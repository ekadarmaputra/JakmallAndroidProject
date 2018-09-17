package com.example.ekadarmaputra.jakmall_androidproject.Ui.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ekadarmaputra.jakmall_androidproject.Adapter.MyJokesAdapter;
import com.example.ekadarmaputra.jakmall_androidproject.Model.JSONResponse;
import com.example.ekadarmaputra.jakmall_androidproject.Model.JokesList;
import com.example.ekadarmaputra.jakmall_androidproject.R;
import com.example.ekadarmaputra.jakmall_androidproject.Retrofit.Api.ApiClient;
import com.example.ekadarmaputra.jakmall_androidproject.Retrofit.Api.ApiInterface;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import android.support.design.widget.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private View parentView;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButtonAdd, floatingActionButtonLoad;

    private ArrayList<JokesList> jokesLists;
    private MyJokesAdapter adapter;
    String listJSON;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * Array List for Binding Data from JSON to this List
         */
        jokesLists = new ArrayList<>();
        parentView = findViewById(R.id.parentLayout);

        /**
         * Getting List and Setting List Adapter
         */
        listView = (ListView) findViewById(R.id.listViewJokes);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * Show the dialog after item of listview is clicking
                 */
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Just for fun")
                        .setMessage(jokesLists.get(position).getJoke())
                        .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("MainActivity","Quit from dialog...");
                            }
                        }).show();
            }
        });

        /**
         * Just to know onClick and Printing Hello Toast in Center.
         */
        Toast toast = Toast.makeText(getApplicationContext(), R.string.string_click_to_load, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        final ApiInterface apiInterface = ApiClient.getApi();
        final Call<JSONResponse> call = apiInterface.getJokesJSON(3);

        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                int statusCode = response.code();
                jokesLists = response.body().getJokesList();
                adapter = new MyJokesAdapter(MainActivity.this,jokesLists);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error: ", t.toString());
            }
        });

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButtonAdd = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButtonLoad = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);

        floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            public int position;

            @Override
            public void onClick(@NonNull final View view) {
                Call<JSONResponse> call= apiInterface.getJokesJSON(1);
                call.enqueue(new Callback<JSONResponse>() {
                    @Override
                    public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                        ArrayList<JokesList> jokesList = response.body().getJokesList();
                        System.out.println("Id :"+jokesList.get(0).getId());

                    }

                    @Override
                    public void onFailure(Call<JSONResponse> call, Throwable t) {

                    }
                });

            }
        });

        assert floatingActionButtonLoad != null;
        floatingActionButtonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull final View view) {

                /**
                 * Checking Internet Connection
                 */
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getActiveNetworkInfo()!=null) {
                    final ProgressDialog dialog;
                    /**
                     * Progress Dialog for User Interaction
                     */
                    dialog = new ProgressDialog(MainActivity.this);
                    dialog.setTitle(getString(R.string.string_getting_json_title));
                    dialog.setMessage(getString(R.string.string_getting_json_message));
                    dialog.show();

                    //Creating an object of our api interface
                    ApiInterface api = ApiClient.getApi();

                    //Calling JSON
                    Call<JSONResponse> call = api.getJokesJSON(3);

                    /**
                     * Enqueue Callback will be call when get response
                     */
                    call.enqueue(new Callback<JSONResponse>() {
                        @Override
                        public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                            //Dismiss Dialog
                            dialog.dismiss();

                            if(response.isSuccessful()) {
                                /**
                                 * Got Successfully
                                 */

                                jokesLists = response.body().getJokesList();


                                /**
                                 * Binding that List to Adapter
                                 */
                                adapter = new MyJokesAdapter(MainActivity.this, jokesLists);
                                listView.setAdapter(adapter);

                            } else {
                                Snackbar.make(parentView, R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JSONResponse> call, Throwable t) {
                            dialog.dismiss();
                        }
                    });

                } else {
                    Snackbar.make(parentView, R.string.string_internet_connection_not_available, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public JokesList getItemJSON(int position){
        return jokesLists.get(position);
    }
}
