package com.web.travelot.controller;

import com.web.travelot.po.CommonResult;
import com.web.travelot.po.State;
import com.web.travelot.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/StateController")
public class StateController {
    @Autowired
    private StateService stateService;

    @GetMapping("/listState")
    public CommonResult<List<State>> listState() throws Exception{
        List<State> result = stateService.listState();
        return new CommonResult<>(200, "success", result);
    };

    @GetMapping("/getStateById/{stateId}")
    public CommonResult<State> getStateById(@PathVariable("stateId") Integer stateId) throws Exception{
        State result = stateService.getStateById(stateId);
        return new CommonResult<>(200, "success", result);
    };
}
