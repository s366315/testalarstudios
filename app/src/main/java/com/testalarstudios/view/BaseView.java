package com.testalarstudios.view;

import moxy.MvpView;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = OneExecutionStateStrategy.class)
public interface BaseView extends MvpView {
    void onLoading();

    void onError(String message);
}
