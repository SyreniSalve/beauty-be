package lt.sdacademy.beauty.service;

import lombok.extern.slf4j.Slf4j;
import lt.sdacademy.beauty.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


}
