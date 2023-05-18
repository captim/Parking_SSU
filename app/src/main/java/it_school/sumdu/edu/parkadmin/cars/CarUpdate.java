package it_school.sumdu.edu.parkadmin.cars;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import it_school.sumdu.edu.parkadmin.model.Car;
import it_school.sumdu.edu.parkadmin.others.ContactRequest;
import it_school.sumdu.edu.parkadmin.R;
import it_school.sumdu.edu.parkadmin.others.Response;

import static it_school.sumdu.edu.parkadmin.stat.Constant.TITLE;

import java.util.Objects;

public class CarUpdate extends DialogFragment {

    private static CarListener carListener;

    private EditText carNumberEditText;
    private EditText carMarkEditText;
    private EditText carModelEditText;

    private static Car car;

    public CarUpdate() {
    }

    public static CarUpdate newInstance(Car car1, String title, CarListener listener){
        car = car1;
        carListener = listener;
        CarUpdate carUpdate = new CarUpdate();
        Bundle args = new Bundle();
        args.putString("title", title);
        carUpdate.setArguments(args);

        carUpdate.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return carUpdate;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_update, container, false);
        assert getArguments() != null;
        String title = getArguments().getString(TITLE);
        Objects.requireNonNull(getDialog()).setTitle(title);

        carNumberEditText = view.findViewById(R.id.carNumberEditText);
        carMarkEditText = view.findViewById(R.id.carMarkEditText);
        carModelEditText = view.findViewById(R.id.carModelEditText);
        Button updateButton = view.findViewById(R.id.updateButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        carNumberEditText.setText(car.getNumber());
        carMarkEditText.setText(car.getMark());
        carModelEditText.setText(car.getModel());

        updateButton.setOnClickListener(view1 -> {
            String carNumber = carNumberEditText.getText().toString();
            String carMark = carMarkEditText.getText().toString();
            String carModel = carModelEditText.getText().toString();

            car.setNumber(carNumber);
            car.setMark(carMark);
            car.setModel(carModel);

            ContactRequest.CarRequest carRequest = new CarImplementation();
            carRequest.updateCar(car, new Response<Boolean>() {
                @Override
                public void onSuccess(Boolean data) {
                    Objects.requireNonNull(getDialog()).dismiss();
                    carListener.onCarListUpdate(data);
                    Toast.makeText(getContext(), "Car updated successfully", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(String message) {
                    carListener.onCarListUpdate(false);
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                }
            });

        });

        cancelButton.setOnClickListener(view12 -> getDialog().dismiss());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}

