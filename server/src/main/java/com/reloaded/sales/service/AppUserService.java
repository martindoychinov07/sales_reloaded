/*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 */
package com.reloaded.sales.service;

import com.reloaded.sales.dto.AppUserDto;
import com.reloaded.sales.dto.filter.AppUserFilter;
import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.AppUser;
import com.reloaded.sales.repository.AppUserRepository;
import com.reloaded.sales.security.AppUserDetails;
import com.reloaded.sales.util.ServiceUtils;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.reloaded.sales.util.ServiceUtils.anyLike;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

  private final AppUserRepository appUserRepository;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Override
  @Transactional(readOnly = true)
  public AppUserDetails loadUserByUsername(String username) {
    AppUser appUser = appUserRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    return new AppUserDetails(appUser);
  }

  @Transactional(readOnly = true)
  public AppUser getUserById(Integer id) {
    return appUserRepository.findById(id).orElseThrow(() -> new NotFound("user"));
  }

  public AppUser createAppUser(AppUserDto user) {
    if (user.getNewPassword() == null
      || Strings.isBlank(user.getNewPassword())
    ) {
      throw new NotFound("password");
    }

    AppUser entity = new AppUser();
    entity.setUsername(user.getUsername());
    entity.setUserRole(user.getUserRole());
    entity.setFullname(user.getFullname());
    entity.setUserExpireDate(user.getUserExpireDate());
    entity.setUserLockDate(user.getUserLockDate());
    entity.setPassword(passwordEncoder.encode(user.getNewPassword()));

    return appUserRepository.save(entity);
  }

  public AppUser updateAppUser(AppUserDto changes) {
    AppUser entity = appUserRepository
      .findById(changes.getUserId())
      .orElseThrow(() -> new NotFound("User not found"));

    if (Strings.isNotBlank(changes.getNewPassword())) {
      entity.setPassword(passwordEncoder.encode(changes.getNewPassword()));
    }

    BeanUtils.copyProperties(changes, entity, AppUser.Fields.userId, AppUser.Fields.password);

    return appUserRepository.save(entity);
  }

  public void deleteAppUser(Integer id) {
    AppUser appUser = appUserRepository.findById(id)
      .orElseThrow(() -> new NotFound("User not found"));

    appUser.setUserLockDate(new Date().toInstant());
    appUserRepository.save(appUser);
  }

  public void changePassword(String newPassword) {
    String username = getCurrentUsername();

    AppUser user = appUserRepository.findByUsername(username)
      .orElseThrow(() -> new NotFound("User not found"));

    user.setPassword(passwordEncoder.encode(newPassword));
    appUserRepository.save(user);
  }

  private String getCurrentUsername() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || auth.getName() == null) {
      throw new NotFound("User is not logged in");
    }
    return auth.getName();
  }

  final List<Sort.Order> defaultSort = List.of(
    Sort.Order.asc(AppUser.Fields.userId)
  );

  @Transactional(readOnly = true)
  public Page<AppUser> findAllAppUser(AppUserFilter filter) {
    PageRequest paging = ServiceUtils.paging(filter, defaultSort);

    Specification<AppUser> spec = (root, query, cb) -> cb.conjunction();

    spec = spec.and(anyLike(filter.getUsername(), r -> r.get(AppUser.Fields.username)));
    spec = spec.and(anyLike(filter.getFullname(), r -> r.get(AppUser.Fields.fullname)));
    spec = spec.and(anyLike(filter.getUserRole(), r -> r.get(AppUser.Fields.userRole)));

    return appUserRepository.findAll(spec, paging);
  }

}
