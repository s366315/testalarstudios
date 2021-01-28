package com.testalarstudios.view;

import com.testalarstudios.model.DataModel;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import moxy.viewstate.strategy.alias.AddToEnd;
import moxy.viewstate.strategy.alias.AddToEndSingle;
import moxy.viewstate.strategy.alias.OneExecution;

@AddToEnd
public interface MainFragmentView extends BaseView {
    void setData(List<DataModel> data);
}
