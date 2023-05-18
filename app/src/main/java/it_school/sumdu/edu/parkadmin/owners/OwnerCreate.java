package it_school.sumdu.edu.parkadmin.owners;

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

import static it_school.sumdu.edu.parkadmin.stat.Constant.*;

import java.util.Objects;


public class OwnerCreate extends DialogFragment {

    private static OwnerListener ownerListener;

    private EditText nameEditText;
    private EditText statusEditText;
    private EditText phoneEditText;
    private EditText emailEditText;

    private String nameString = "";
    private String statusString = "";
    private String phoneString = "";
    private String emailString = "";

    public OwnerCreate()
    {
    }

    public static OwnerCreate newInstance(String title, OwnerListener listener){
        ownerListener = listener;
        OwnerCreate ownerCreateDialogFragment = new OwnerCreate();
        Bundle args = new Bundle();
        args.putString("title", title);
        ownerCreateDialogFragment.setArguments(args);

        ownerCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return ownerCreateDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_owner_create, container, false);

        nameEditText = view.findViewById(R.id.nameEditText);
        statusEditText = view.findViewById(R.id.statusEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        Button createButton = view.findViewById(R.id.createButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        assert getArguments() != null;
        String title = getArguments().getString(TITLE);
        Objects.requireNonNull(getDialog()).setTitle(title);

        createButton.setOnClickListener(view1 -> {
            nameString = nameEditText.getText().toString();
            statusString = statusEditText.getText().toString();
            phoneString = phoneEditText.getText().toString();
            emailString = emailEditText.getText().toString();

            final Owner owner = new Owner(-1, nameString, statusString, phoneString, emailString);

            ContactRequest.OwnerRequest ownerRequest = new OwnerImplementation();
            ownerRequest.createOwner(owner, new Response<Boolean>() {
                @Override
                public void onSuccess(Boolean data) {
                    Objects.requireNonNull(getDialog()).dismiss();
                    ownerListener.onOwnerListUpdate(data);
                    Toast.makeText(getContext(), "Owner created successfully", Toast.LENGTH_LONG).show();
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

