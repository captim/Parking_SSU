package it_school.sumdu.edu.parkadmin.owners;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import it_school.sumdu.edu.parkadmin.others.ContactRequest;
import it_school.sumdu.edu.parkadmin.bind.BindActivity;
import it_school.sumdu.edu.parkadmin.R;
import it_school.sumdu.edu.parkadmin.others.Response;
import it_school.sumdu.edu.parkadmin.model.Owner;
import it_school.sumdu.edu.parkadmin.stat.Constant;

import java.util.List;

public class OwnerListAdapter extends RecyclerView.Adapter<OwnerViewHolder> {

    private final Context context;
    private final List<Owner> ownerList;
    private final OwnerListener listener;

    OwnerListAdapter(Context context, List<Owner> ownerList, OwnerListener listener) {
        this.context = context;
        this.ownerList = ownerList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_owner_card_view, parent, false);
        return new OwnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerViewHolder holder, int position) {
        final Owner owner = ownerList.get(position);

        holder.nameTextView.setText(owner.getName());
        holder.statusTextView.setText(owner.getStatus());
        holder.emailTextView.setText(owner.getEmail());
        holder.phoneTextView.setText(owner.getPhone());

        holder.editImageView.setOnClickListener(v -> {
            OwnerUpdate dialogFragment = OwnerUpdate.newInstance(owner, "Update Owner", inUpdated -> listener.onOwnerListUpdate(inUpdated));
            dialogFragment.show(((OwnerListActivity) context).getSupportFragmentManager(), Constant.UPDATE_OWNER);
        });

        holder.deleteImageView.setOnClickListener(v -> showConfirmationDialog(owner.getId()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BindActivity.class);
            intent.putExtra(Constant.OWNER_ID, owner.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return ownerList.size();
    }

    private void showConfirmationDialog(final int ownerId) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure, You wanted to delete this owner?");
        alertDialogBuilder.setPositiveButton("Yes",
                (arg0, arg1) -> {
                    ContactRequest.OwnerRequest query = new OwnerImplementation();
                    query.deleteOwner(ownerId, new Response<Boolean>() {
                        @Override
                        public void onSuccess(Boolean data) {
                            if(data) {
                                Toast.makeText(context, "Owner deleted successfully", Toast.LENGTH_SHORT).show();
                                listener.onOwnerListUpdate(true);
                            }
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

