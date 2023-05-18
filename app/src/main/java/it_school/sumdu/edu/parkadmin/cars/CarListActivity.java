package it_school.sumdu.edu.parkadmin.cars;

import android.annotation.SuppressLint;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import it_school.sumdu.edu.parkadmin.others.ContactRequest;
import it_school.sumdu.edu.parkadmin.others.CountImplementation;
import it_school.sumdu.edu.parkadmin.R;
import it_school.sumdu.edu.parkadmin.others.Response;
import it_school.sumdu.edu.parkadmin.model.*;
import it_school.sumdu.edu.parkadmin.stat.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarListActivity extends AppCompatActivity implements CarListener {

    private RecyclerView recyclerView;
    private TextView noDataFoundTextView;
    private TextView ownerCountTextView;
    private TextView carCountTextView;
    private TextView bindCountTextView;
    private FloatingActionButton fab;

    private final List<Car> carList = new ArrayList<>();
    private CarListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initialization();

        adapter = new CarListAdapter(this, carList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        showTableRowCount();
        showCarList();

        fab.setOnClickListener(view -> {
            CarCreate dialogFragment = CarCreate.newInstance("Create Car", CarListActivity.this);
            dialogFragment.show(getSupportFragmentManager(), Constant.CREATE_CAR);

        });
    }

    @Override
    public void onCarListUpdate(boolean isUpdate) {
        if(isUpdate) {
            showTableRowCount();
            showCarList();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void showTableRowCount() {
        ContactRequest.CountRequest request = new CountImplementation();
        request.getCount(new Response<Count>() {
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

    private void showCarList(){
        ContactRequest.CarRequest request = new CarImplementation();
        request.readAllCar(new Response<List<Car>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(List<Car> data) {
                recyclerView.setVisibility(View.VISIBLE);
                noDataFoundTextView.setVisibility(View.GONE);

                carList.clear();
                carList.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                recyclerView.setVisibility(View.GONE);
                noDataFoundTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initialization(){
        recyclerView = findViewById(R.id.recyclerView);
        noDataFoundTextView = findViewById(R.id.noDataFoundTextView);

        ownerCountTextView = findViewById(R.id.ownerCount);
        carCountTextView = findViewById(R.id.carCount);
        bindCountTextView = findViewById(R.id.bindCount);

        fab = findViewById(R.id.fab);
    }

}
