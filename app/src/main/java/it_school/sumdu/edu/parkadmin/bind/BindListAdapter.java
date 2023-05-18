package it_school.sumdu.edu.parkadmin.bind;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import it_school.sumdu.edu.parkadmin.model.Car;
import it_school.sumdu.edu.parkadmin.others.ContactRequest;
import it_school.sumdu.edu.parkadmin.R;
import it_school.sumdu.edu.parkadmin.others.Response;

import java.util.List;

public class BindListAdapter extends RecyclerView.Adapter<BindViewHolder> {

    private final Context context;
    private final int ownerId;
    private final List<Car> carList;
    private final BindListener listener;

    public BindListAdapter(Context context, int ownerId, List<Car> carList, BindListener listener) {
        this.context = context;
        this.ownerId = ownerId;
        this.carList = carList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_binding_cars, parent, false);
        return new BindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BindViewHolder holder, int position) {
        final int itemPos = position;
        final Car car = carList.get(position);

        holder.carNumberTextView.setText(car.getNumber());
        holder.carMarkTextView.setText(car.getMark());
        holder.carModelTextView.setText(car.getModel());

        holder.deleteIcon.setOnClickListener(v -> removeBindCar(car.getId(), itemPos));
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    private void removeBindCar(int carId, final int itemPosition) {
        ContactRequest.BindRequest request = new BindImplementation();
        request.deleteBind(ownerId, carId, new Response<Boolean>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(Boolean data) {
                if(data){
                    carList.remove(itemPosition);
                    notifyDataSetChanged();
                    listener.onBindUpdated(true);
                } else
                    Toast.makeText(context, "Failed to remove car", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}

