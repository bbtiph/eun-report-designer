package org.eun.back.service.impl;

import java.util.Optional;
import org.eun.back.domain.EunTeamMember;
import org.eun.back.repository.EunTeamMemberRepository;
import org.eun.back.service.EunTeamMemberService;
import org.eun.back.service.dto.EunTeamMemberDTO;
import org.eun.back.service.mapper.EunTeamMemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EunTeamMember}.
 */
@Service
@Transactional
public class EunTeamMemberServiceImpl implements EunTeamMemberService {

    private final Logger log = LoggerFactory.getLogger(EunTeamMemberServiceImpl.class);

    private final EunTeamMemberRepository eunTeamMemberRepository;

    private final EunTeamMemberMapper eunTeamMemberMapper;

    public EunTeamMemberServiceImpl(EunTeamMemberRepository eunTeamMemberRepository, EunTeamMemberMapper eunTeamMemberMapper) {
        this.eunTeamMemberRepository = eunTeamMemberRepository;
        this.eunTeamMemberMapper = eunTeamMemberMapper;
    }

    @Override
    public EunTeamMemberDTO save(EunTeamMemberDTO eunTeamMemberDTO) {
        log.debug("Request to save EunTeamMember : {}", eunTeamMemberDTO);
        EunTeamMember eunTeamMember = eunTeamMemberMapper.toEntity(eunTeamMemberDTO);
        eunTeamMember = eunTeamMemberRepository.save(eunTeamMember);
        return eunTeamMemberMapper.toDto(eunTeamMember);
    }

    @Override
    public EunTeamMemberDTO update(EunTeamMemberDTO eunTeamMemberDTO) {
        log.debug("Request to update EunTeamMember : {}", eunTeamMemberDTO);
        EunTeamMember eunTeamMember = eunTeamMemberMapper.toEntity(eunTeamMemberDTO);
        eunTeamMember = eunTeamMemberRepository.save(eunTeamMember);
        return eunTeamMemberMapper.toDto(eunTeamMember);
    }

    @Override
    public Optional<EunTeamMemberDTO> partialUpdate(EunTeamMemberDTO eunTeamMemberDTO) {
        log.debug("Request to partially update EunTeamMember : {}", eunTeamMemberDTO);

        return eunTeamMemberRepository
            .findById(eunTeamMemberDTO.getId())
            .map(existingEunTeamMember -> {
                eunTeamMemberMapper.partialUpdate(existingEunTeamMember, eunTeamMemberDTO);

                return existingEunTeamMember;
            })
            .map(eunTeamMemberRepository::save)
            .map(eunTeamMemberMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EunTeamMemberDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EunTeamMembers");
        return eunTeamMemberRepository.findAll(pageable).map(eunTeamMemberMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EunTeamMemberDTO> findOne(Long id) {
        log.debug("Request to get EunTeamMember : {}", id);
        return eunTeamMemberRepository.findById(id).map(eunTeamMemberMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EunTeamMember : {}", id);
        eunTeamMemberRepository.deleteById(id);
    }
}
