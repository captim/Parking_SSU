package it_school.sumdu.edu.parkadmin.owners;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import it_school.sumdu.edu.parkadmin.model.Owner;
import it_school.sumdu.edu.parkadmin.others.ContactRequest;
import it_school.sumdu.edu.parkadmin.others.CountImplementation;
import it_school.sumdu.edu.parkadmin.cars.CarListActivity;
import it_school.sumdu.edu.parkadmin.R;
import it_school.sumdu.edu.parkadmin.others.Response;
import it_school.sumdu.edu.parkadmin.model.Count;
import it_school.sumdu.edu.parkadmin.stat.Constant;

import java.util.ArrayList;
import java.util.List;

public class OwnerListActivity extends AppCompatActivity implements OwnerListener {

    private RecyclerView recyclerView;
    private TextView noDataFoundTextView;
    private FloatingActionButton fab;
    private TextView ownerCountTextView;
    private TextView carCountTextView;
    private TextView bindCountTextView;

    private final List<Owner> ownerList = new ArrayList<>();
    private OwnerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialization();

        adapter = new OwnerListAdapter(this, ownerList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        showOwnerList();

        fab.setOnClickListener(view -> {
            OwnerCreate ownerCreate = OwnerCreate.newInstance("Create Owner", OwnerListActivity.this);
            ownerCreate.show(getSupportFragmentManager(), Constant.CREATE_OWNER);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showTableRowCount();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_show_car){
            startActivity(new Intent(this, CarListActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOwnerListUpdate(boolean isUpdated) {
        if(isUpdated) {
            showOwnerList();
            showTableRowCount();
        }
    }

    private void showOwnerList() {
        ContactRequest.OwnerRequest query = new OwnerImplementation();
        query.readAllOwner(new Response<List<Owner>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(List<Owner> data) {
                recyclerView.setVisibility(View.VISIBLE);
                noDataFoundTextView.setVisibility(View.GONE);

                ownerList.clear();
                ownerList.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                recyclerView.setVisibility(View.GONE);
                noDataFoundTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showTableRowCount() {
        ContactRequest.CountRequest query = new CountImplementation();
        query.getCount(new Response<Count>() {
            @Override
            public void onSuccess(Count data) {
                ownerCountTextView.setText(getString(R.string.owner_count, data.getOwnerRow()));
                carCountTextView.setText(getString(R.string.car_count, data.getCarRow()));
                bindCountTextView.setText(getString(R.string.bind_count, data.getBindRow()));
            }

            @Override
            public void onFailure(String message) {
                ownerCountTextView.setText(getString(R.string.count_error));
                carCountTextView.setText(message);
                bindCountTextView.setText("");
            }
        });
    }

    private void initialization(){
        recyclerView = findViewById(R.id.recyclerView);
        noDataFoundTextView = findViewById(R.id.noDataFoundTextView);
        fab = findViewById(R.id.fab);

        ownerCountTextView = findViewById(R.id.ownerCount);
        carCountTextView = findViewById(R.id.carCount);
        bindCountTextView = findViewById(R.id.bindCount);
    }
}

