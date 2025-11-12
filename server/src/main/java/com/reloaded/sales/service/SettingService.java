package com.reloaded.sales.service;

import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.Setting;
import com.reloaded.sales.repository.SettingRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SettingService {
  private final SettingRepository settingRepository;

  public SettingService(SettingRepository settingRepository) {
    this.settingRepository = settingRepository;
  }

  public Setting createSetting(Setting setting) {
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

  public Page<Setting> findSettingByKeyGroupNote(String key, String group, String note, Pageable paging) {
    Setting probe = Setting.builder()
      .settingKey(key)
      .settingNote(group)
      .settingGroup(note)
      .build();

    final ExampleMatcher.GenericPropertyMatchers match = new ExampleMatcher.GenericPropertyMatchers();
    ExampleMatcher matcher = ExampleMatcher
      .matchingAll()
      .withIgnoreNullValues()
      .withMatcher(Setting.Fields.settingKey , match.exact().ignoreCase())
      .withMatcher(Setting.Fields.settingGroup, match.exact().ignoreCase())
      .withMatcher(Setting.Fields.settingNote, match.contains().ignoreCase());

    Example<Setting> example = Example.of(probe, matcher);
    return settingRepository.findAll(example, paging);
  }
}
