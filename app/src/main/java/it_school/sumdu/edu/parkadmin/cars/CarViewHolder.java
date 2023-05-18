package it_school.sumdu.edu.parkadmin.cars;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import it_school.sumdu.edu.parkadmin.R;

public class CarViewHolder extends RecyclerView.ViewHolder {

    TextView carNumberTextView;
    TextView carMarkTextView;
    TextView carModelTextView;
    ImageView editIcon;
    ImageView deleteIcon;

    public CarViewHolder(View itemView) {
        super(itemView);

        carNumberTextView = itemView.findViewById(R.id.carNumberTextView);
        carMarkTextView = itemView.findViewById(R.id.carMarkTextView);
        carModelTextView = itemView.findViewById(R.id.carModelTextView);
        editIcon = itemView.findViewById(R.id.editImageView);
        deleteIcon = itemView.findViewById(R.id.deleteImageView);
    }
}
