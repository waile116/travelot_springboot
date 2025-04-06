package com.web.travelot.service;
import com.web.travelot.po.Attraction;
import com.web.travelot.po.Ticket;

import java.util.List;

public interface AttractionService {
    public List<Attraction> listAttractionRandom();
    public List<Attraction> listAttractionById(Integer stateId);
    public Attraction getAttractionById(Integer attractionId);
    public int saveAttraction(Attraction attraction);
    public List<Ticket> listTicket();

    public List<Ticket> listTicketById(Integer attractionId);
    public int saveTicket(Ticket ticket);
}