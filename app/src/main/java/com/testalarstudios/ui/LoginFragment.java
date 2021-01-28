package com.testalarstudios.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.testalarstudios.R;
import com.testalarstudios.databinding.FragmentLoginBinding;
import com.testalarstudios.presenter.LoginFragmentPresenter;
import com.testalarstudios.view.LoginFragmentView;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class LoginFragment extends MvpAppCompatFragment implements LoginFragmentView {

    @InjectPresenter
    public LoginFragmentPresenter presenter;

    private FragmentLoginBinding vb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vb = FragmentLoginBinding.bind(view);

        vb.btnSignin.setOnClickListener(btnSignin -> {
            vb.inputLayoutUsername.setError(null);
            vb.inputLayoutPassword.setError(null);
            String username = vb.inputUsername.getText().toString();
            String password = vb.inputPassword.getText().toString();
            buttonDisable();
            presenter.signIn(username, password);
        });
    }

    @Override
    public void usernameError() {
        vb.inputLayoutUsername.setError("Обязательно к заполнению");
        buttonEnable();
    }

    @Override
    public void passwordError() {
        vb.inputLayoutPassword.setError("Обязательно к заполнению");
        buttonEnable();
    }

    private void buttonEnable() {
        vb.btnSignin.setEnabled(true);
    }

    private void buttonDisable() {
        vb.btnSignin.setEnabled(false);
    }

    @Override
    public void onLoading() {
        vb.progress.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String message) {
        buttonEnable();
        vb.progress.getRoot().setVisibility(View.GONE);
        Snackbar.make(vb.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess() {
        buttonEnable();
        vb.progress.getRoot().setVisibility(View.GONE);
        NavController nav = Navigation.findNavController(getView());
        nav.navigate(R.id.action_loginFragment_to_mainFragment);
    }
}