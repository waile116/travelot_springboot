package com.web.travelot.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.web.travelot.po.State;
import com.web.travelot.service.StateService;
import com.web.travelot.mapper.StateMapper;

@Service
public class StateServiceImpl implements StateService{
    @Autowired
    private StateMapper stateMapper;

    @Override
    public List<State> listState(){
        return stateMapper.listState();
    };

    @Override
    public State getStateById(Integer stateId){
        return stateMapper.getStateById(stateId);
    };
}
