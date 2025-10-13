package com.reloaded.sales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.reloaded.sales.model.Partner;
import com.reloaded.sales.service.PartnerService;
import com.reloaded.sales.DTO.PartnerEditRequest;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/partner")
public class PartnerController {
    @Autowired
    PartnerService partnerService;

    @PostMapping("/add")
    public ResponseEntity<String> addPartner(@RequestBody Partner partner) {
        return partnerService.createPartner(partner);
    }

    @PatchMapping("/edit")
    public ResponseEntity<String> editPartner(@RequestBody PartnerEditRequest partnerEditRequest) {
        return partnerService.editPartner(partnerEditRequest);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePartner(@RequestBody String name) {
        return partnerService.deletePartner(name);
    }

    @GetMapping("/all")
    public List<Partner> getAllPartners(@RequestParam int page, @RequestParam int size) {
        return partnerService.getAllPartners(page, size);
    }

    @GetMapping("/name")
    public List<Partner> getByName(@RequestParam String name, @RequestParam int page, @RequestParam int size) {
        return partnerService.getByName(name, page, size);
    }

    @GetMapping("/location")
    public List<Partner> getByLocation(@RequestParam String location, @RequestParam int page, @RequestParam int size) {
        return partnerService.getByLocation(location, page, size);
    }

    @GetMapping("/idTags")
    public List<Partner> getByIdTags(@RequestParam String idTags,@RequestParam int page, @RequestParam int size) {
        return partnerService.getByIdTags(idTags, page, size);
    }

//    @PostMapping("/name")
//    public ResponseEntity<String> changePartnerName(@RequestBody PartnerEditRequest partnerEditRequest) {
//        return partnerService.changePartnerName(partnerEditRequest);
//    }
}
