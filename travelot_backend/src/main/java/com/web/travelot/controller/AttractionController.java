package com.web.travelot.controller;

import com.web.travelot.po.Attraction;
import com.web.travelot.po.CommonResult;
import com.web.travelot.po.Restaurant;
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

    @RequestMapping("/listAttractionRandom")
    public CommonResult<List<Attraction>> listAttractionRandom() throws Exception{
        List<Attraction> result = attractionService.listAttractionRandom();
        return new CommonResult<>(200, "success", result);
    };

    @RequestMapping("/listAttractionById/{stateId}")
    public CommonResult<List<Attraction>> listAttractionById(@PathVariable("stateId") Integer stateId) throws Exception{
        List<Attraction> result = attractionService.listAttractionById(stateId);
        return new CommonResult<>(200, "success", result);
    };

    @RequestMapping("/getAttractionById/{attractionId}")
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
        attraction.setAttractionImg(img);
        int result = attractionService.saveAttraction(attraction);
        return new CommonResult<>(200, "Insert attraction success", result);
    }
}
