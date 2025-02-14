package com.web.travelot.service;
import com.web.travelot.po.State;
import java.util.List;

public interface StateService {
    public List<State> listState();
    public State getStateById(Integer stateId);
}
