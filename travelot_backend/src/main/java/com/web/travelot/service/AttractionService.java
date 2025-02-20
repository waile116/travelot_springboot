package com.web.travelot.service;
import com.web.travelot.po.Attraction;
import java.util.List;

public interface AttractionService {
    public List<Attraction> listAttractionRandom();
    public List<Attraction> listAttractionById(Integer stateId);
}