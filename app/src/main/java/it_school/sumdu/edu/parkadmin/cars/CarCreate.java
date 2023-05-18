package it_school.sumdu.edu.parkadmin.cars;

import android.annotation.SuppressLint;
import android.app.Dialog;
import androidx.fragment.app.DialogFragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import it_school.sumdu.edu.parkadmin.model.Car;
import it_school.sumdu.edu.parkadmin.others.ContactRequest;
import it_school.sumdu.edu.parkadmin.R;
import it_school.sumdu.edu.parkadmin.others.Response;
import it_school.sumdu.edu.parkadmin.stat.Constant;

public class CarCreate extends DialogFragment {

    private EditText carNumberEditText;
    private EditText carMarkEditText;
    private EditText carModelEditText;

    private static CarListener carListener;

    public CarCreate() {
    }

    public static CarCreate newInstance(String title, CarListener listener){
        carListener = listener;
        CarCreate carCreate = new CarCreate();
        Bundle args = new Bundle();
        args.putString("title", title);
        carCreate.setArguments(args);

        carCreate.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return carCreate;
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_create, container, false);

        carNumberEditText = view.findViewById(R.id.carNumberEditText);
        carMarkEditText = view.findViewById(R.id.carMarkEditText);
        carModelEditText = view.findViewById(R.id.carModelEditText);
        Button createButton = view.findViewById(R.id.createButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        assert getArguments() != null;
        String title = getArguments().getString(Constant.TITLE);
        Objects.requireNonNull(getDialog()).setTitle(title);

        createButton.setOnClickListener(view1 -> {
            String carNumber = carNumberEditText.getText().toString();
            String carMark = carMarkEditText.getText().toString();
            String carModel = carModelEditText.getText().toString();

            final Car car = new Car(-1, carNumber, carMark, carModel);

            ContactRequest.CarRequest request = new CarImplementation();
            request.createCar(car, new Response<Boolean>() {
                @Override
                public void onSuccess(Boolean data) {
                    Objects.requireNonNull(getDialog()).dismiss();
                    carListener.onCarListUpdate(true);
                    Toast.makeText(getContext(), "Car created successfully", Toast.LENGTH_LONG).show();
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

