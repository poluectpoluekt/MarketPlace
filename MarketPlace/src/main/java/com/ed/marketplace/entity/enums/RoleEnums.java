package com.ed.marketplace.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnums {
    ADMIN("ROLE_ADMIN"), USER("ROLE_CUSTOMER");

    public String role;

}
