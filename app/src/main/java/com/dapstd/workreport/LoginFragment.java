package com.dapstd.workreport;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        SharedPreferences preferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        Button nextBtn = rootView.findViewById(R.id.button_next);
        CheckBox rememberCheck = rootView.findViewById(R.id.remember_check);
        EditText emailEditText = rootView.findViewById(R.id.text_email);
        EditText passwordEditText = rootView.findViewById(R.id.text_password);
        nextBtn.setOnClickListener(v -> {
            if (!emailEditText.getText().equals("") && !passwordEditText.getText().equals("")) {
                mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                        .addOnCompleteListener(getActivity(), task -> {
                            if (task.isSuccessful()) {
                                if(rememberCheck.isChecked()) {
                                    preferences.edit().putString("email", emailEditText.getText().toString()).apply();
                                    preferences.edit().putString("password", passwordEditText.getText().toString()).apply();
                                }
                                startActivity(new Intent(getActivity(), WelcomeActivity.class));
                                getActivity().finish();
                            } else {
                                Toast.makeText(getActivity(), "Auth failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        return rootView;
    }
}