package com.anantadwi13.recyclerview;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recyclerView;
    ListMovieAdapter adapter;
    ArrayList<MovieModel> list;
    TextView tv;
    int x=0;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        btnAdd = findViewById(R.id.btnAdd);
        tv = findViewById(R.id.coba);
        btnAdd.setOnClickListener(this);

        if (savedInstanceState==null) {
            list = new ArrayList<>();
            list.add(new MovieModel(1, "COBA", "10 Januari", "Deskripsi"));
        }
        else
            list = savedInstanceState.getParcelableArrayList("EXTRA");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new ListMovieAdapter(list);
        recyclerView.setAdapter(adapter);
        ItemClickSupport clickSupport = new ItemClickSupport(recyclerView);
        clickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView rv, int position, View v) {
                Toast.makeText(MainActivity.this, "Posisi "+position, Toast.LENGTH_SHORT).show();
                Log.d("OKOK", "onItemClicked: Posisi ke "+position);
            }
        }).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView rv, int position, View v) {
                ListMovieAdapter adapternya = (ListMovieAdapter) rv.getAdapter();
                Toast.makeText(MainActivity.this, "Long Click "+adapternya.getMovie(position).getJudul(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //ItemClickSupport.removeFrom(recyclerView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("EXTRA",list);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        list = savedInstanceState.getParcelableArrayList("EXTRA");
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_list:
                break;
            case R.id.action_grid:
                break;
            case R.id.action_cardview:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAdd :
                tv.setText(String.valueOf(x));
                MovieModel movie = new MovieModel(1,"COBA"+x,"10 Januari","Deskripsi");
                adapter.addMovie(movie);
                x++;
                break;
        }
    }
}
