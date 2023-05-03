package org.eun.back.service.impl;

import java.util.Optional;
import org.eun.back.domain.OperationalBodyMember;
import org.eun.back.repository.OperationalBodyMemberRepository;
import org.eun.back.service.OperationalBodyMemberService;
import org.eun.back.service.dto.OperationalBodyMemberDTO;
import org.eun.back.service.mapper.OperationalBodyMemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OperationalBodyMember}.
 */
@Service
@Transactional
public class OperationalBodyMemberServiceImpl implements OperationalBodyMemberService {

    private final Logger log = LoggerFactory.getLogger(OperationalBodyMemberServiceImpl.class);

    private final OperationalBodyMemberRepository operationalBodyMemberRepository;

    private final OperationalBodyMemberMapper operationalBodyMemberMapper;

    public OperationalBodyMemberServiceImpl(
        OperationalBodyMemberRepository operationalBodyMemberRepository,
        OperationalBodyMemberMapper operationalBodyMemberMapper
    ) {
        this.operationalBodyMemberRepository = operationalBodyMemberRepository;
        this.operationalBodyMemberMapper = operationalBodyMemberMapper;
    }

    @Override
    public OperationalBodyMemberDTO save(OperationalBodyMemberDTO operationalBodyMemberDTO) {
        log.debug("Request to save OperationalBodyMember : {}", operationalBodyMemberDTO);
        OperationalBodyMember operationalBodyMember = operationalBodyMemberMapper.toEntity(operationalBodyMemberDTO);
        operationalBodyMember = operationalBodyMemberRepository.save(operationalBodyMember);
        return operationalBodyMemberMapper.toDto(operationalBodyMember);
    }

    @Override
    public OperationalBodyMemberDTO update(OperationalBodyMemberDTO operationalBodyMemberDTO) {
        log.debug("Request to update OperationalBodyMember : {}", operationalBodyMemberDTO);
        OperationalBodyMember operationalBodyMember = operationalBodyMemberMapper.toEntity(operationalBodyMemberDTO);
        operationalBodyMember = operationalBodyMemberRepository.save(operationalBodyMember);
        return operationalBodyMemberMapper.toDto(operationalBodyMember);
    }

    @Override
    public Optional<OperationalBodyMemberDTO> partialUpdate(OperationalBodyMemberDTO operationalBodyMemberDTO) {
        log.debug("Request to partially update OperationalBodyMember : {}", operationalBodyMemberDTO);

        return operationalBodyMemberRepository
            .findById(operationalBodyMemberDTO.getId())
            .map(existingOperationalBodyMember -> {
                operationalBodyMemberMapper.partialUpdate(existingOperationalBodyMember, operationalBodyMemberDTO);

                return existingOperationalBodyMember;
            })
            .map(operationalBodyMemberRepository::save)
            .map(operationalBodyMemberMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OperationalBodyMemberDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OperationalBodyMembers");
        return operationalBodyMemberRepository.findAll(pageable).map(operationalBodyMemberMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OperationalBodyMemberDTO> findOne(Long id) {
        log.debug("Request to get OperationalBodyMember : {}", id);
        return operationalBodyMemberRepository.findById(id).map(operationalBodyMemberMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OperationalBodyMember : {}", id);
        operationalBodyMemberRepository.deleteById(id);
    }
}
