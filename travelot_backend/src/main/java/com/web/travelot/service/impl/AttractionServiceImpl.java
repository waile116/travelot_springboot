package com.web.travelot.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.web.travelot.po.Attraction;
import com.web.travelot.service.AttractionService;
import com.web.travelot.mapper.AttractionMapper;

@Service
public class AttractionServiceImpl implements AttractionService{
    @Autowired
    private AttractionMapper attractionMapper;

    @Override
    public List<Attraction> listAttractionById(Integer stateId){
        return attractionMapper.listAttractionById(stateId);
    };

}
