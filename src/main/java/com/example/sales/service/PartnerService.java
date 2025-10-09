package com.example.sales.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import com.example.sales.exception.*;
import com.example.sales.model.Partner;
import com.example.sales.DTO.PartnerEditRequest;
import com.example.sales.repository.PartnerRepository;

@Transactional
@Service
public class PartnerService {
    @Autowired
    PartnerRepository partnerRepository;

    public ResponseEntity<String> createPartner(Partner partner)
            throws PartnerAlreadyExistsException{
        if (partner == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Partner is not valid");
        }

        partnerRepository.save(partner);

        return ResponseEntity.ok("Partner created successfully");
    }

    public ResponseEntity<String> editPartner(PartnerEditRequest partnerEditRequest)
            throws PartnerNotFoundException, PartnerAlreadyExistsException {
        Partner partner = partnerRepository.findById(partnerEditRequest.getPartner().getId())
                .orElseThrow(() -> new PartnerNotFoundException("Partner not found"));

        BeanUtils.copyProperties(partnerEditRequest.getPartner(), partner);
        partnerRepository.save(partner);

        return ResponseEntity.ok("Partner edited successfully");
    }

    public ResponseEntity<String> deletePartner(String name) throws PartnerNotFoundException {
        Partner partner = partnerRepository.findByName(name);

        if (partner == null) {
            throw new PartnerNotFoundException("No such partner found");
        }

        partner.setRating(0);

        partnerRepository.save(partner);

        return ResponseEntity.ok("Partner deleted successfully");
    }

    @Transactional(readOnly = true)
    public List<Partner> getAllPartners() {
        return partnerRepository.findByRatingNot(0);
    }
}
