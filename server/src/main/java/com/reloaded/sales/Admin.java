/*
 * /*
 *  * Copyright 2026 Martin Doychinov
 *  * Licensed under the Apache License, Version 2.0
 *  */
 */
package com.reloaded.sales;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Admin {

  public static void main(String[] args) throws Exception {
    String encoded = new BCryptPasswordEncoder().encode("Test123!@#");
    System.out.println(encoded);
  }

}
