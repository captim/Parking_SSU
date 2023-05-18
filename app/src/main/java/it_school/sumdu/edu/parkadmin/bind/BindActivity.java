package it_school.sumdu.edu.parkadmin.bind;

import static it_school.sumdu.edu.parkadmin.stat.Constant.OWNER_ID;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it_school.sumdu.edu.parkadmin.R;
import it_school.sumdu.edu.parkadmin.cars.CarAddActivity;
import it_school.sumdu.edu.parkadmin.model.Count;
import it_school.sumdu.edu.parkadmin.model.Car;
import it_school.sumdu.edu.parkadmin.model.Owner;
import it_school.sumdu.edu.parkadmin.others.ContactRequest;
import it_school.sumdu.edu.parkadmin.others.CountImplementation;
import it_school.sumdu.edu.parkadmin.others.Response;
import it_school.sumdu.edu.parkadmin.owners.OwnerImplementation;


public class BindActivity extends AppCompatActivity implements BindListener {

    private TextView nameTextView;
    private TextView statusTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private ImageView actionAddCar;

    private RecyclerView recyclerView;
    private TextView noDataFoundTextView;
    private TextView ownerCountTextView;
    private TextView carCountTextView;
    private TextView bindCountTextView;

    private int ownerId;
    private final List<Car> bindList = new ArrayList<>();
    private BindListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_info);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initialization();

        ownerId = getIntent().getIntExtra(OWNER_ID, -1);

        adapter = new BindListAdapter(this, ownerId, bindList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        showOwnerInfo();

        actionAddCar.setOnClickListener(v -> {
            Intent intent = new Intent(BindActivity.this, CarAddActivity.class);
            intent.putExtra(OWNER_ID, ownerId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showTableRowCount();
        showBindList();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBindUpdated(boolean isUpdated) {
        showTableRowCount();
    }

    private void showOwnerInfo() {
        ContactRequest.OwnerRequest request = (ContactRequest.OwnerRequest) new OwnerImplementation();
        request.readOwner(ownerId, new Response<Owner>() {
            @Override
            public void onSuccess(Owner owner) {
                nameTextView.setText(owner.getName());
                statusTextView.setText(owner.getStatus());
                emailTextView.setText(owner.getEmail());
                phoneTextView.setText(owner.getPhone());
            }

            @Override
            public void onFailure(String message) {
                showToast(message);
            }
        });
    }

    private void showBindList() {
        ContactRequest.BindRequest request = new BindImplementation();
        request.readAllBindByOwner(ownerId, new Response<List<Car>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(List<Car> data) {
                recyclerView.setVisibility(View.VISIBLE);
                noDataFoundTextView.setVisibility(View.GONE);

                bindList.clear();
                bindList.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                recyclerView.setVisibility(View.GONE);
                noDataFoundTextView.setVisibility(View.VISIBLE);
                noDataFoundTextView.setText(message);
            }
        });

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

    private void initialization() {
        nameTextView = findViewById(R.id.nameTextView);
        statusTextView = findViewById(R.id.ownerStatusTextView);
        emailTextView = findViewById(R.id.emailTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        actionAddCar = findViewById(R.id.action_add_car);

        recyclerView = findViewById(R.id.recyclerView);
        noDataFoundTextView = findViewById(R.id.noDataFoundTextView);

        ownerCountTextView = findViewById(R.id.ownerCount);
        carCountTextView = findViewById(R.id.carCount);
        bindCountTextView = findViewById(R.id.bindCount);
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}

