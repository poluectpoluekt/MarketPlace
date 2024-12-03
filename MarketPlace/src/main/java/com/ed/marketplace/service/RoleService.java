package com.ed.marketplace.service;

import com.ed.marketplace.entity.Role;
import com.ed.marketplace.exception.RoleNotFoundException;
import com.ed.marketplace.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleService {

    private RoleRepository roleRepository;

    public Role getRole(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() -> new RoleNotFoundException(roleName));
    }
}
