package com.testalarstudios.view;

import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = OneExecutionStateStrategy.class)
public interface LoginFragmentView extends BaseView {
    void onSuccess();
    void usernameError();
    void passwordError();
}
