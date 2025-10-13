package com.reloaded.sales.service;

import com.reloaded.sales.exception.PartnerAlreadyExistsException;
import com.reloaded.sales.exception.PartnerNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.reloaded.sales.model.Partner;
import com.reloaded.sales.DTO.PartnerEditRequest;
import com.reloaded.sales.repository.PartnerRepository;

@Transactional
@Service
public class PartnerService {
    @Autowired
    PartnerRepository partnerRepository;

    public ResponseEntity<String> createPartner(Partner partner)
            throws PartnerAlreadyExistsException {
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
    public List<Partner> getAllPartners(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return partnerRepository.findByRatingNot(0, pageable);
    }

    @Transactional(readOnly = true)
    public List<Partner> getByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return partnerRepository.findByName(name, pageable);
    }

    @Transactional(readOnly = true)
    public List<Partner> getByLocation(String location, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return partnerRepository.findByLocation(location, pageable);
    }

    @Transactional(readOnly = true)
    public List<Partner> getByIdTags(String idTags, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return partnerRepository.findByIdTags(idTags, pageable);
    }
}
