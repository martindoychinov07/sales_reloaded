package com.reloaded.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class UserDto {
    private long id;
    private String username;
    private String role;
    private long code;
}
