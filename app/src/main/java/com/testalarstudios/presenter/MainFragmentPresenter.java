package com.testalarstudios.presenter;

import android.annotation.SuppressLint;

import com.pixplicity.easyprefs.library.Prefs;
import com.testalarstudios.App;
import com.testalarstudios.api.ApiService;
import com.testalarstudios.model.DataModel;
import com.testalarstudios.view.MainFragmentView;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@SuppressLint("CheckResult")
@InjectViewState
public class MainFragmentPresenter extends MvpPresenter<MainFragmentView> {

    private final ApiService api = App.getInstance().getApiService();
    private int pagesLoaded = 0;
    private ArrayList<DataModel> cache = new ArrayList<>();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        loadMore(1);
    }

    public void loadMore(int page) {

        if (!cache.isEmpty() && page < pagesLoaded) {
            getViewState().setData(cache);
            return;
        }

        if (cache.isEmpty()) getViewState().onLoading();

        api.fetchData(page, getCode()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            if (success.getStatus().equals("ok")) {
                                cache.addAll(success.getData());
                                getViewState().setData(success.getData());
                                ++pagesLoaded;
                            } else {
                                getViewState().onError(success.getStatus());
                            }
                        },
                        error -> getViewState().onError(error.getMessage())
                );
    }

    private String getCode() {
        return Prefs.getString("code", "");
    }
}
