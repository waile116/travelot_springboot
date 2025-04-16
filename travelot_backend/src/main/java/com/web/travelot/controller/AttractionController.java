package com.web.travelot.controller;

import com.web.travelot.po.*;
import com.web.travelot.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/AttractionController")
public class AttractionController {
    @Autowired
    private AttractionService attractionService;

    @GetMapping("/listAttractionRandom")
    public CommonResult<List<Attraction>> listAttractionRandom() throws Exception{
        List<Attraction> result = attractionService.listAttractionRandom();
        return new CommonResult<>(200, "success", result);
    };

    @GetMapping("/listAttractionById/{stateId}")
    public CommonResult<List<Attraction>> listAttractionById(@PathVariable("stateId") Integer stateId) throws Exception{
        List<Attraction> result = attractionService.listAttractionById(stateId);
        return new CommonResult<>(200, "success", result);
    };

    @GetMapping("/getAttractionById/{attractionId}")
    public CommonResult<Attraction> getAttractionById(@PathVariable("attractionId") Integer attractionId) throws Exception{
        Attraction result = attractionService.getAttractionById(attractionId);
        return new CommonResult<>(200, "success", result);
    };

    @PostMapping("/saveAttraction/{id}/{stateId}/{name}/{desc}/{location}/{price}/{openTime}/{rating}")
    public CommonResult<Integer> saveAttraction(@PathVariable("id") Integer attractionId,
                                                @PathVariable("stateId") Integer stateId,
                                                @PathVariable("name") String name,
                                                @PathVariable("desc") String desc,
                                                @PathVariable("location") String location,
                                                @PathVariable("price") String price,
                                                @PathVariable("openTime") String openTime,
                                                @PathVariable("rating") Double rating,
                                                @RequestBody Map<String, String> requestBody) throws Exception {
        String img = requestBody.get("img");
        String mapImg = requestBody.get("mapImg");
        String mapLink = requestBody.get("mapLink");
        Attraction attraction = new Attraction();
        if(attractionId > -1) {
            attraction.setAttractionId(attractionId);
        }
        attraction.setStateId(stateId);
        attraction.setName(name);
        attraction.setDesc(desc);
        attraction.setLocation(location);
        attraction.setPrice(price);
        attraction.setOpenTime(openTime);
        attraction.setRating(rating);
        attraction.setMapLink(mapLink);
        attraction.setAttractionImg(img);
        attraction.setMapImg(mapImg);
        int result = attractionService.saveAttraction(attraction);
        return new CommonResult<>(200, "Insert attraction success", result);
    }

    @GetMapping("/listTicket")
    public CommonResult<List<Ticket>> listTicket() throws Exception{
        List<Ticket> result = attractionService.listTicket();
        return new CommonResult<>(200, "success", result);
    };
    @GetMapping("/listTicketById/{attractionId}")
    public CommonResult<List<Ticket>> listTicketById(@PathVariable("attractionId") Integer attractionId) throws Exception{
        List<Ticket> result = attractionService.listTicketById(attractionId);
        return new CommonResult<>(200, "success", result);
    };

    @GetMapping("/getTicketById/{ticketId}")
    public CommonResult<Ticket> getTicketById(@PathVariable("ticketId") Integer ticketId) throws Exception{
       Ticket result = attractionService.getTicketById(ticketId);
        return new CommonResult<>(200, "success", result);
    };

    @PostMapping("/saveTicket/{id}/{attractionId}/{name}/{desc}/{price}/{quantity}")
    public CommonResult<Integer> saveTicket(@PathVariable("id") Integer ticketId,
                                                @PathVariable("attractionId") Integer attractionId,
                                                @PathVariable("name") String name,
                                                @PathVariable("desc") String desc,
                                                @PathVariable("price") Integer price,
                                                @PathVariable("quantity") Integer quantity) throws Exception {
        Ticket ticket = new Ticket();
        if(ticketId > -1) {
            ticket.setTicketId(ticketId);
        }
        ticket.setAttractionId(attractionId);
        ticket.setName(name);
        ticket.setDesc(desc);
        ticket.setPrice(price);
        ticket.setQuantity(quantity);
        int result = attractionService.saveTicket(ticket);
        return new CommonResult<>(200, "Insert ticket success", result);
    }
}
