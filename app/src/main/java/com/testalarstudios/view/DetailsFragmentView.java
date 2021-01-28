package com.testalarstudios.view;

import android.content.Intent;

import com.testalarstudios.model.DataModel;

import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = OneExecutionStateStrategy.class)
public interface DetailsFragmentView extends BaseView {
}
