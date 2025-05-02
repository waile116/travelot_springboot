package com.web.travelot.service;
import com.web.travelot.po.Attraction;
import com.web.travelot.po.Ticket;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface AttractionService {
    @Cacheable(value="attraction", key="")
    public List<Attraction> listAttractionRandom();
    @Cacheable(value="attraction", key="#stateId")
    public List<Attraction> listAttractionById(Integer stateId);
    public Attraction getAttractionById(Integer attractionId);
    public Attraction saveAttraction(Attraction attraction);

    public List<Ticket> listTicket();
    public List<Ticket> listTicketById(Integer attractionId);
    public Ticket getTicketById(Integer ticketId);

    public Ticket saveTicket(Ticket ticket);
}