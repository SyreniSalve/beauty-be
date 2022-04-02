package lt.sdacademy.beauty.service;

import lombok.extern.slf4j.Slf4j;
import lt.sdacademy.beauty.model.entity.Role;
import lt.sdacademy.beauty.model.entity.RoleEntity;
import lt.sdacademy.beauty.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class RoleService {

    private RoleRepository roleRepository;

//    public RoleEntity addNewRole(Role role) {
//
//    }
}
