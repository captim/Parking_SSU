package it_school.sumdu.edu.parkadmin.cars;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import it_school.sumdu.edu.parkadmin.others.ContactRequest;
import it_school.sumdu.edu.parkadmin.bind.BindImplementation;
import it_school.sumdu.edu.parkadmin.R;
import it_school.sumdu.edu.parkadmin.others.Response;
import it_school.sumdu.edu.parkadmin.model.Bind;

import java.util.List;

public class CarAddListAdapter extends RecyclerView.Adapter<CarAddViewHolder> {

    private final Context context;
    private final int ownerId;
    private final List<Bind> bindList;

    public CarAddListAdapter(Context context, int ownerId, List<Bind> bindList) {
        this.context = context;
        this.ownerId = ownerId;
        this.bindList = bindList;
    }

    @NonNull
    @Override
    public CarAddViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bind_card_view, parent, false);
        return new CarAddViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarAddViewHolder holder, int position) {
        final Bind car = bindList.get(position);

        holder.carNumberTextView.setText(car.getNumber());
        holder.carMarkTextView.setText(car.getMark());
        holder.carModelTextView.setText(car.getModel());
        holder.checkBox.setChecked(car.isTaken());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                bindCar(car.getId());
            else
                removeBindCar(car.getId());
        });
    }

    @Override
    public int getItemCount() {
        return bindList.size();
    }

    private void bindCar(int carId) {
        ContactRequest.BindRequest request = new BindImplementation();
        request.createBind(ownerId, carId, new Response<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void removeBindCar(int carId) {
        ContactRequest.BindRequest request = new BindImplementation();
        request.deleteBind(ownerId, carId, new Response<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
