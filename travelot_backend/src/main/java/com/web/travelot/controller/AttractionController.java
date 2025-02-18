package com.web.travelot.controller;

import com.web.travelot.po.Attraction;
import com.web.travelot.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/AttractionController")
public class AttractionController {
    @Autowired
    private AttractionService attractionService;

    @RequestMapping("/listAttractionById/{stateId}")
    public List<Attraction> listAttractionById(@PathVariable("stateId") Integer stateId) throws Exception{
        return attractionService.listAttractionById(stateId);
    };
}
