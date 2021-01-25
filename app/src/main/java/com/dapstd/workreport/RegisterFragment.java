package com.dapstd.workreport;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    private FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFregment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        Button nextBtn = rootView.findViewById(R.id.button_next);
        EditText emailEditText = rootView.findViewById(R.id.text_email);
        EditText passwordEditText = rootView.findViewById(R.id.text_password);
        EditText repeatPasswordEditText = rootView.findViewById(R.id.text_password_repeat);
        nextBtn.setOnClickListener(v -> {
            if (!emailEditText.getText().equals("") && !passwordEditText.getText().equals("")) {
                if(passwordEditText.getText().toString().equals(repeatPasswordEditText.getText().toString())) {
                    mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                            .addOnCompleteListener(getActivity(), task -> {
                                if (task.isSuccessful()) {
                                    LoginFragment loginFragment = new LoginFragment();
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .setReorderingAllowed(true)
                                            .replace(R.id.fragment, loginFragment, null)
                                            .commit();
                                } else {
                                    Toast.makeText(getActivity(), "createUserWithEmail failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }else{
                    Toast.makeText(getActivity(), "Password do not match",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }
}