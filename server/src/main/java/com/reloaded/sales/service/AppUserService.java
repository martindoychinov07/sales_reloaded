package com.reloaded.sales.service;

import com.reloaded.sales.dto.AppUserDto;
import com.reloaded.sales.exception.AlreadyReported;
import com.reloaded.sales.exception.NotFound;
import com.reloaded.sales.model.AppUser;
import com.reloaded.sales.repository.AppUserRepository;
import com.reloaded.sales.security.AppUserDetails;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class AppUserService implements UserDetailsService {

  private final AppUserRepository appUserRepository;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public AppUserService(AppUserRepository appUserRepository) {
    this.appUserRepository = appUserRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public AppUserDetails loadUserByUsername(String username) {
    AppUser appUser = appUserRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    return new AppUserDetails(appUser);
  }

  public AppUser getUserById(Integer id) {
    return appUserRepository.findById(id).orElseThrow(() -> new NotFound("user"));
  }

  public AppUser createAppUser(AppUserDto user) {
    if (appUserRepository.findByUsername(user.getUsername()).isPresent()) {
      throw new AlreadyReported("User already exists");
    }
    if (user.getNewPassword() == null
      || user.getNewPassword().isBlank()
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

    if (changes.getNewPassword() != null
      && !changes.getNewPassword().isBlank()
    ) {
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
      throw new AlreadyReported("User is not logged in");
    }
    return auth.getName();
  }

  @Transactional(readOnly = true)
  public Page<AppUser> findAllAppUserByUsernameUserRoleFullname(
    String username,
    String userRole,
    String fullname,
    Pageable paging
  ) {
    AppUser probe = AppUser.builder()
      .username(username)
      .userRole(userRole)
      .fullname(fullname)
      .build();

    final ExampleMatcher.GenericPropertyMatchers match = new ExampleMatcher.GenericPropertyMatchers();
    ExampleMatcher matcher = ExampleMatcher
      .matchingAll()
      .withIgnoreNullValues()
      .withMatcher(AppUser.Fields.username, match.contains().ignoreCase())
      .withMatcher(AppUser.Fields.userRole, match.contains().ignoreCase())
      .withMatcher(AppUser.Fields.fullname, match.contains().ignoreCase());

    Example<AppUser> example = Example.of(probe, matcher);
    return appUserRepository.findAll(example, paging);
  }

}
