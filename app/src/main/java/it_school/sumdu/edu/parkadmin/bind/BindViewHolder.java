package it_school.sumdu.edu.parkadmin.bind;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import it_school.sumdu.edu.parkadmin.R;

class BindViewHolder extends RecyclerView.ViewHolder {

    TextView carNumberTextView;
    TextView carMarkTextView;
    TextView carModelTextView;
    ImageView deleteIcon;

    public BindViewHolder(View itemView) {
        super(itemView);

        carNumberTextView = itemView.findViewById(R.id.carNumberTextView);
        carMarkTextView = itemView.findViewById(R.id.carMarkTextView);
        carModelTextView = itemView.findViewById(R.id.carModelTextView);
        deleteIcon = itemView.findViewById(R.id.deleteImageView);
    }
}
