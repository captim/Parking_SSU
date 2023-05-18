package it_school.sumdu.edu.parkadmin.cars;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import it_school.sumdu.edu.parkadmin.R;

public class CarAddViewHolder extends RecyclerView.ViewHolder {

    CheckBox checkBox;
    TextView carNumberTextView;
    TextView carMarkTextView;
    TextView carModelTextView;

    public CarAddViewHolder(View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.checkbox);
        carNumberTextView = itemView.findViewById(R.id.carNumberTextView);
        carMarkTextView = itemView.findViewById(R.id.carMarkTextView);
        carModelTextView = itemView.findViewById(R.id.carModelTextView);
    }
}

