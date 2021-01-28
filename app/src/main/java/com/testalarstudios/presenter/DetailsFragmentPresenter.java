package com.testalarstudios.presenter;

import com.testalarstudios.view.DetailsFragmentView;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class DetailsFragmentPresenter extends MvpPresenter<DetailsFragmentView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

    }
}
