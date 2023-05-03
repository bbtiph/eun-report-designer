package org.eun.back.service.impl;

import java.util.Optional;
import org.eun.back.domain.EunTeam;
import org.eun.back.repository.EunTeamRepository;
import org.eun.back.service.EunTeamService;
import org.eun.back.service.dto.EunTeamDTO;
import org.eun.back.service.mapper.EunTeamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EunTeam}.
 */
@Service
@Transactional
public class EunTeamServiceImpl implements EunTeamService {

    private final Logger log = LoggerFactory.getLogger(EunTeamServiceImpl.class);

    private final EunTeamRepository eunTeamRepository;

    private final EunTeamMapper eunTeamMapper;

    public EunTeamServiceImpl(EunTeamRepository eunTeamRepository, EunTeamMapper eunTeamMapper) {
        this.eunTeamRepository = eunTeamRepository;
        this.eunTeamMapper = eunTeamMapper;
    }

    @Override
    public EunTeamDTO save(EunTeamDTO eunTeamDTO) {
        log.debug("Request to save EunTeam : {}", eunTeamDTO);
        EunTeam eunTeam = eunTeamMapper.toEntity(eunTeamDTO);
        eunTeam = eunTeamRepository.save(eunTeam);
        return eunTeamMapper.toDto(eunTeam);
    }

    @Override
    public EunTeamDTO update(EunTeamDTO eunTeamDTO) {
        log.debug("Request to update EunTeam : {}", eunTeamDTO);
        EunTeam eunTeam = eunTeamMapper.toEntity(eunTeamDTO);
        eunTeam = eunTeamRepository.save(eunTeam);
        return eunTeamMapper.toDto(eunTeam);
    }

    @Override
    public Optional<EunTeamDTO> partialUpdate(EunTeamDTO eunTeamDTO) {
        log.debug("Request to partially update EunTeam : {}", eunTeamDTO);

        return eunTeamRepository
            .findById(eunTeamDTO.getId())
            .map(existingEunTeam -> {
                eunTeamMapper.partialUpdate(existingEunTeam, eunTeamDTO);

                return existingEunTeam;
            })
            .map(eunTeamRepository::save)
            .map(eunTeamMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EunTeamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EunTeams");
        return eunTeamRepository.findAll(pageable).map(eunTeamMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EunTeamDTO> findOne(Long id) {
        log.debug("Request to get EunTeam : {}", id);
        return eunTeamRepository.findById(id).map(eunTeamMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EunTeam : {}", id);
        eunTeamRepository.deleteById(id);
    }
}
