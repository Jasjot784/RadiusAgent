package com.example.radiusagent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvHello;
    private RecyclerView mRecyclerView;
    JSONArray facilities,exclusions;
//    ArrayList<ArrayList<String>> excAll = new ArrayList<>();
    public static final String TAG = MainActivity.class.getSimpleName();
    private WordListAdapter mAdapter;
    HashMap<String,String> mapping = new HashMap<>();
    private Button btSubmit;
    String url = "https://my-json-server.typicode.com/iranjith4/ad-assignment/db";
    // Get the SharedPreferences object

    // Store the string value

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        btSubmit = findViewById(R.id.btSubmit);
        btSubmit.setOnClickListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Get the SharedPreferences editor to modify the values
        SharedPreferences.Editor editor = sharedPreferences.edit();


//        tvHello = findViewById(R.id.tvHello);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    editor.putString("store",response.toString());
                    editor.apply();
                    facilities = response.getJSONArray("facilities");
                    exclusions = response.getJSONArray("exclusions");
                    ArrayList<String> type_facility = new ArrayList<>();
                    ArrayList<JSONArray> options = new ArrayList<>();
                    for(int i = 0;i<facilities.length();i++){
                        type_facility.add(facilities.getJSONObject(i).getString("name"));
                        options.add(facilities.getJSONObject(i).getJSONArray("options"));
                    }
                    Log.d(TAG, "onResponse: "+type_facility);
                    Log.d(TAG, "onOptions: "+options);


//                    for(int i = 0;i<exclusions.length();i++){
//                        JSONArray jsonArray = exclusions.getJSONArray(i);
//                        for (int j = 0;j<jsonArray.length();j++){
//
//                        }
//
//
//                    }

                    mAdapter = new WordListAdapter(MainActivity.this, type_facility,options);
                    mRecyclerView.setAdapter(mAdapter);

                    mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);






    }

    @Override
    public void onClick(View view) {
        ArrayList<String> clickers = new ArrayList<>();
        ArrayList<String> opts = new ArrayList<>();
        ArrayList<ArrayList<String>> invalid = new ArrayList<>();
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            String selectedOption = mAdapter.getSelectedOption(i);
            clickers.add(selectedOption);
            Log.d(TAG, "Selected option for item " + i + ": " + selectedOption);
            // Perform any further processing with the selected options
        }

        for (int i = 0;i<facilities.length();i++){
            try {
                JSONArray options = facilities.getJSONObject(i).getJSONArray("options");
                for (int j = 0;j<options.length();j++){
                    mapping.put(options.getJSONObject(j).getString("id"),options.getJSONObject(j).getString("name"));
                    String selectName = options.getJSONObject(j).getString("name");
                    if (selectName.equals(clickers.get(i)))opts.add(options.getJSONObject(j).getString("id"));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
//        Log.d(TAG, "onClick: "+opts);

        for (int i = 0;i<exclusions.length();i++){
            try {
                JSONArray exc = exclusions.getJSONArray(i);
                ArrayList<String> comb = new ArrayList<>();
                for (int j = 0;j<exc.length();j++){
                    comb.add(exc.getJSONObject(j).getString("options_id"));

                }
                invalid.add(comb);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        Log.d(TAG, "onClick: "+invalid);

        for (ArrayList<String> invalidCombination : invalid) {
            boolean invalidCombinationFound = true;
            for (String optionId : invalidCombination) {
                if (!opts.contains(optionId)) {
                    invalidCombinationFound = false;
                    break;
                }
            }

            if (invalidCombinationFound) {
                // Invalid combination found
                Log.d(TAG, "Invalid combination: " + invalidCombination);
                Toast.makeText(this, "Cannot select "+mapping.get(invalidCombination.get(0))+" and "+mapping.get(invalidCombination.get(1))+" together", Toast.LENGTH_SHORT).show();
                break;
            }else{
                Intent intent = new Intent(MainActivity.this,ResultActivity.class);
                intent.putStringArrayListExtra("selects",clickers);
                startActivity(intent);
                break;

            }
        }

    }
}