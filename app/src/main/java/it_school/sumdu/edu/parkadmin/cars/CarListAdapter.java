package it_school.sumdu.edu.parkadmin.cars;

import static it_school.sumdu.edu.parkadmin.stat.Constant.UPDATE_CAR;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it_school.sumdu.edu.parkadmin.R;
import it_school.sumdu.edu.parkadmin.model.Car;
import it_school.sumdu.edu.parkadmin.others.ContactRequest;
import it_school.sumdu.edu.parkadmin.others.Response;
import it_school.sumdu.edu.parkadmin.owners.OwnerListActivity;
import it_school.sumdu.edu.parkadmin.owners.OwnerUpdate;
import it_school.sumdu.edu.parkadmin.stat.Constant;

public class CarListAdapter extends RecyclerView.Adapter<CarViewHolder> {

    private final Context context;
    private final List<Car> carList;
    private final CarListener listener;

    public CarListAdapter(Context context, List<Car> carList, CarListener listener) {
        this.context = context;
        this.carList = carList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car_card_view, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        final Car car = carList.get(position);

        holder.carNumberTextView.setText(car.getNumber());
        holder.carMarkTextView.setText(car.getMark());
        holder.carModelTextView.setText(car.getModel());

        holder.editIcon.setOnClickListener(v -> {
            CarUpdate dialogFragment = CarUpdate.newInstance(car, "Update Car", inUpdated -> listener.onCarListUpdate(inUpdated));
            dialogFragment.show(((CarListActivity) context).getSupportFragmentManager(), Constant.UPDATE_CAR);
        });

        holder.deleteIcon.setOnClickListener(v -> showConfirmationDialog(car.getId()));

    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    private void showConfirmationDialog(final int carId) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure, You wanted to delete this car?");
        alertDialogBuilder.setPositiveButton("Yes",
                (arg0, arg1) -> {
                    ContactRequest.CarRequest request = new CarImplementation();
                    request.deleteCar(carId, new Response<Boolean>() {
                        @Override
                        public void onSuccess(Boolean data) {
                            listener.onCarListUpdate(data);
                            Toast.makeText(context, "Car is deleted successfully", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(String message) {
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        }
                    });

                });

        alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

