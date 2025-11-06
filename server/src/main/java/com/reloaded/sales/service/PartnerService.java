package com.reloaded.sales.service;

import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.Partner;
import com.reloaded.sales.repository.PartnerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;

@Service
@Transactional
public class PartnerService {
  final PartnerRepository partnerRepository;

  public PartnerService(PartnerRepository partnerRepository) {
    this.partnerRepository = partnerRepository;
  }

  public Partner create(Partner partner) {
    return partnerRepository.save(partner);
  }

  public Partner update(Partner changes) {
    Partner entity = partnerRepository
      .findById(changes.getPartnerId())
      .orElseThrow(() -> new NotFound("Partner not found"));

    BeanUtils.copyProperties(changes, entity, "id");

    return partnerRepository.save(entity);
  }

  public void delete(Integer id) {
    Partner partner = partnerRepository.findById(id)
      .orElseThrow(() -> new NotFound("Partner not found"));

    partnerRepository.delete(partner);
  }

  public Partner getById(Integer id) {
    return partnerRepository.findById(id).orElseThrow(() -> new NotFound("partner"));
  }

  public Page<Partner> findAllByNameLocationCode(String name, String location, String code, Pageable paging) {
    Partner probe = Partner.builder()
      .name(name)
      .location(location)
      .code(code)
      .partnerState(1).build();

    GenericPropertyMatchers match = new GenericPropertyMatchers();
    ExampleMatcher matcher = ExampleMatcher
      .matchingAll()
      .withIgnoreNullValues()
      .withMatcher(Partner.Fields.name, match.contains().ignoreCase())
      .withMatcher(Partner.Fields.location, match.contains().ignoreCase())
      .withMatcher(Partner.Fields.code, match.contains().ignoreCase())
      .withMatcher(Partner.Fields.partnerState, match.exact());

    Example<Partner> example = Example.of(probe, matcher);

    return partnerRepository.findAll(example, paging);
  }

}
