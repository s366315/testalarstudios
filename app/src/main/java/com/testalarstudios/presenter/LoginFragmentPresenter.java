package com.testalarstudios.presenter;

import android.annotation.SuppressLint;

import com.pixplicity.easyprefs.library.Prefs;
import com.testalarstudios.App;
import com.testalarstudios.api.ApiService;
import com.testalarstudios.view.LoginFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@SuppressLint("CheckResult")
@InjectViewState
public class LoginFragmentPresenter extends MvpPresenter<LoginFragmentView> {

    private final ApiService api = App.getInstance().getApiService();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        if (Prefs.contains("code")) getViewState().onSuccess();
    }

    public void signIn(String username, String password) {

        if (!isInputVerified(username, password)) return;

        getViewState().onLoading();
        api.auth(username, password).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            if (success.getStatus().equals("ok")) {
                                Prefs.putString("code", success.getCode());
                                getViewState().onSuccess();
                            } else {
                                getViewState().onError(success.getStatus());
                            }
                        },
                        error -> getViewState().onError(error.getMessage())
                );
    }

    private boolean isInputVerified(String username, String password) {

        if (username.isEmpty()) getViewState().usernameError();
        if (password.isEmpty()) getViewState().passwordError();

        return !username.isEmpty() && !password.isEmpty();
    }
}
