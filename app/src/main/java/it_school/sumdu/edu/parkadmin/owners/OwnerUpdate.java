package it_school.sumdu.edu.parkadmin.owners;

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

import it_school.sumdu.edu.parkadmin.model.Owner;
import it_school.sumdu.edu.parkadmin.others.ContactRequest;
import it_school.sumdu.edu.parkadmin.R;
import it_school.sumdu.edu.parkadmin.others.Response;

import static it_school.sumdu.edu.parkadmin.stat.Constant.TITLE;

import java.util.Objects;

public class OwnerUpdate extends DialogFragment {

    private static OwnerListener ownerListener;

    private EditText nameEditText;
    private EditText statusEditText;
    private EditText phoneEditText;
    private EditText emailEditText;

    private String nameString = "";
    private String statusString = "";
    private String phoneString = "";
    private String emailString = "";

    private static Owner owner;

    public OwnerUpdate() {
    }

    public static OwnerUpdate newInstance(Owner own, String title, OwnerListener listener){
        owner = own;
        ownerListener = listener;
        OwnerUpdate ownerUpdate = new OwnerUpdate();
        Bundle args = new Bundle();
        args.putString("title", title);
        ownerUpdate.setArguments(args);

        ownerUpdate.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return ownerUpdate;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_update, container, false);
        assert getArguments() != null;
        String title = getArguments().getString(TITLE);
        Objects.requireNonNull(getDialog()).setTitle(title);

        nameEditText = view.findViewById(R.id.nameEditText);
        statusEditText = view.findViewById(R.id.statusEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        Button updateButton = view.findViewById(R.id.updateButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        nameEditText.setText(owner.getName());
        statusEditText.setText(owner.getStatus());
        phoneEditText.setText(owner.getPhone());
        emailEditText.setText(owner.getEmail());

        updateButton.setOnClickListener(view1 -> {
            nameString = nameEditText.getText().toString();
            statusString = statusEditText.getText().toString();
            phoneString = phoneEditText.getText().toString();
            emailString = emailEditText.getText().toString();

            owner.setName(nameString);
            owner.setStatus(statusString);
            owner.setPhone(phoneString);
            owner.setEmail(emailString);

            ContactRequest.OwnerRequest ownerRequest = new OwnerImplementation();
            ownerRequest.updateOwner(owner, new Response<Boolean>() {
                @Override
                public void onSuccess(Boolean data) {
                    Objects.requireNonNull(getDialog()).dismiss();
                    ownerListener.onOwnerListUpdate(data);
                    Toast.makeText(getContext(), "Owner updated successfully", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(String message) {
                    ownerListener.onOwnerListUpdate(false);
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

