/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.service;

import com.reloaded.sales.dto.filter.SettingFilter;
import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.Setting;
import com.reloaded.sales.repository.SettingRepository;
import com.reloaded.sales.util.ServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.reloaded.sales.util.ServiceUtils.anyLike;

@Service
@Transactional
@RequiredArgsConstructor
public class SettingService {

  private final SettingRepository settingRepository;

  public Setting createSetting(Setting setting) {
    setting.setSettingId(null);
    return settingRepository.save(setting);
  }

  public Setting updateSetting(Setting changes) {
    Setting entity = settingRepository
      .findById(changes.getSettingId())
      .orElseThrow(() -> new NotFound("Setting not found"));

    BeanUtils.copyProperties(changes, entity,Setting.Fields.settingId);

    return settingRepository.save(entity);
  }

  public void deleteSetting(Integer id) {
    Setting setting = settingRepository.findById(id)
      .orElseThrow(() -> new NotFound("Setting not found"));

    settingRepository.delete(setting);
  }

  public Setting getSettingById(Integer id) {
    return settingRepository.findById(id).orElseThrow(() -> new NotFound("setting"));
  }

  @Transactional(readOnly = true)
  public Setting getSettingByKey(String key) {
    return settingRepository.findBySettingKey(key).orElseThrow(() -> new NotFound("setting"));
  }

  final List<Sort.Order> defaultSort = List.of(
    Sort.Order.asc(Setting.Fields.settingId)
  );

  @Transactional(readOnly = true)
  public Page<Setting> findSetting(SettingFilter filter) {
    PageRequest paging = ServiceUtils.paging(filter, defaultSort);

    Specification<Setting> spec = (root, query, cb) -> cb.conjunction();

    spec = spec.and(anyLike(filter.getSettingKey(), r -> r.get(Setting.Fields.settingKey)));
    spec = spec.and(anyLike(filter.getSettingGroup(), r -> r.get(Setting.Fields.settingGroup)));
    spec = spec.and(anyLike(filter.getSettingNote(), r -> r.get(Setting.Fields.settingNote)));

    return settingRepository.findAll(spec, paging);
  }

}
