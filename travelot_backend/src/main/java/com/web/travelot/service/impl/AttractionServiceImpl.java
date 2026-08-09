package com.web.travelot.service.impl;
import java.util.List;

import com.web.travelot.po.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import com.web.travelot.po.Attraction;
import com.web.travelot.service.AttractionService;
import com.web.travelot.mapper.AttractionMapper;

@Service
public class AttractionServiceImpl implements AttractionService{
    @Autowired
    private AttractionMapper attractionMapper;

    @Override
    @Cacheable(value="list_attraction", key="")
    public List<Attraction> listAttractionRandom(){
        return attractionMapper.listAttractionRandom();
    };
    @Override
    @Cacheable(value="list_attraction", key="#stateId")
    public List<Attraction> listAttractionById(Integer stateId){
        return attractionMapper.listAttractionById(stateId);
    };
    @Override
    @Cacheable(value="attraction", key="#attractionId")
    public Attraction getAttractionById(Integer attractionId){
        return attractionMapper.getAttractionById(attractionId);
    };
    @Override
    @Caching(
            put = {
                    @CachePut(value = "attraction", key = "#attraction.attractionId")
            },
            evict = {
                    @CacheEvict(value = "list_attraction", key = "#attraction.stateId"),
                    @CacheEvict(value = "list_attraction", key = "\"\"")
            }
    )
    public Attraction saveAttraction(Attraction attraction){
        // if id exists, update, else save
        if (attraction.getAttractionId() != null) {
            attractionMapper.updateAttraction(attraction);
        } else {
           attractionMapper.saveAttraction(attraction);
        }

        return attractionMapper.getAttractionById(attraction.getAttractionId());
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
    public Ticket getTicketById(Integer ticketId){
        return attractionMapper.getTicketById(ticketId);
    }
    @Override
    public Ticket saveTicket(Ticket ticket){
        // if id exists, update, else save
        if (ticket.getTicketId() != null) {
            attractionMapper.updateTicket(ticket);
        } else {
            attractionMapper.saveTicket(ticket);
        }
        return attractionMapper.getTicketById(ticket.getTicketId());
    };
}
