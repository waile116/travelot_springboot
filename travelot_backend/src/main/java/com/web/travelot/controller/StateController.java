package com.web.travelot.controller;

import com.web.travelot.po.State;
import com.web.travelot.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/StateController")
public class StateController {
    @Autowired
    private StateService stateService;

    @RequestMapping("/listState")
    public List<State> listState() throws Exception{
        return stateService.listState();
    };

    @RequestMapping("/getStateById/{stateId}")
    public State getStateById(@PathVariable("stateId") Integer stateId) throws Exception{
        return stateService.getStateById(stateId);
    };
}
