package com.web.travelot.service.impl;
import java.util.List;

import com.web.travelot.po.Ticket;
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
    public List<Attraction> listAttractionRandom(){
        return attractionMapper.listAttractionRandom();
    };
    @Override
    public List<Attraction> listAttractionById(Integer stateId){
        return attractionMapper.listAttractionById(stateId);
    };
    @Override
    public Attraction getAttractionById(Integer attractionId){
        return attractionMapper.getAttractionById(attractionId);
    };
    @Override
    public int saveAttraction(Attraction attraction){
        // if id exists, update, else save
        if (attraction.getAttractionId() != null) {
            return attractionMapper.updateAttraction(attraction);
        } else {
            return attractionMapper.saveAttraction(attraction);
        }
    };

    @Override
    public List<Ticket> listTicket(){
        return attractionMapper.listTicket();
    }
    @Override
    public List<Ticket> listTicketById(Integer attractionId){
        return attractionMapper.listTicketById(attractionId);
    }
    @Override
    public int saveTicket(Ticket ticket){
        // if id exists, update, else save
        if (ticket.getTicketId() != null) {
            return attractionMapper.updateTicket(ticket);
        } else {
            return attractionMapper.saveTicket(ticket);
        }
    };
}
