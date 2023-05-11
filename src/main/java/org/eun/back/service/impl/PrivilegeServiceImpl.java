package org.eun.back.service.impl;

import java.util.List;
import java.util.Optional;
import org.eun.back.domain.Privilege;
import org.eun.back.repository.PrivilegeRepository;
import org.eun.back.service.PrivilegeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Privilege}.
 */
@Service
@Transactional
public class PrivilegeServiceImpl implements PrivilegeService {

    private final Logger log = LoggerFactory.getLogger(PrivilegeServiceImpl.class);

    private final PrivilegeRepository privilegeRepository;

    public PrivilegeServiceImpl(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public Privilege save(Privilege privilege) {
        log.debug("Request to save Privilege : {}", privilege);
        return privilegeRepository.save(privilege);
    }

    @Override
    public Privilege update(Privilege privilege) {
        log.debug("Request to update Privilege : {}", privilege);
        return privilegeRepository.save(privilege);
    }

    @Override
    public Optional<Privilege> partialUpdate(Privilege privilege) {
        log.debug("Request to partially update Privilege : {}", privilege);

        return privilegeRepository
            .findById(privilege.getId())
            .map(existingPrivilege -> {
                if (privilege.getName() != null) {
                    existingPrivilege.setName(privilege.getName());
                }

                return existingPrivilege;
            })
            .map(privilegeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Privilege> findAll() {
        log.debug("Request to get all Privileges");
        return privilegeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Privilege> findOne(Long id) {
        log.debug("Request to get Privilege : {}", id);
        return privilegeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Privilege : {}", id);
        privilegeRepository.deleteById(id);
    }
}
