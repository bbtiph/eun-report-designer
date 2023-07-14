package org.eun.back.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.*;
import java.util.stream.Collectors;
import org.eun.back.domain.ReportBlocks;
import org.eun.back.repository.ReportBlocksRepository;
import org.eun.back.service.CountriesService;
import org.eun.back.service.ReportBlocksContentService;
import org.eun.back.service.ReportBlocksService;
import org.eun.back.service.ReportService;
import org.eun.back.service.dto.CountriesDTO;
import org.eun.back.service.dto.ReportBlocksContentDTO;
import org.eun.back.service.dto.ReportBlocksContentDataDTO;
import org.eun.back.service.dto.ReportBlocksDTO;
import org.eun.back.service.mapper.ReportBlocksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReportBlocks}.
 */
@Service
@Transactional
public class ReportBlocksServiceImpl implements ReportBlocksService {

    private final Logger log = LoggerFactory.getLogger(ReportBlocksServiceImpl.class);

    private final ReportBlocksRepository reportBlocksRepository;

    private final ReportBlocksContentService reportBlocksContentService;

    private final CountriesService countriesService;

    private final ReportService reportService;

    private final ReportBlocksMapper reportBlocksMapper;

    public ReportBlocksServiceImpl(
        ReportBlocksRepository reportBlocksRepository,
        ReportBlocksContentService reportBlocksContentService,
        CountriesService countriesService,
        ReportService reportService,
        ReportBlocksMapper reportBlocksMapper
    ) {
        this.reportBlocksRepository = reportBlocksRepository;
        this.reportBlocksContentService = reportBlocksContentService;
        this.countriesService = countriesService;
        this.reportService = reportService;
        this.reportBlocksMapper = reportBlocksMapper;
    }

    @Override
    public ReportBlocksDTO save(ReportBlocksDTO reportBlocksDTO) {
        log.debug("Request to save ReportBlocks : {}", reportBlocksDTO);
        if (reportBlocksDTO.getPriorityNumber() == null) {
            reportBlocksDTO.setPriorityNumber(
                reportBlocksRepository.findMaxPriorityNumberByReportId(reportBlocksDTO.getReport().getId()) + 1
            );
        }
        ReportBlocks reportBlocks = reportBlocksMapper.toEntity(reportBlocksDTO);
        reportBlocks = reportBlocksRepository.save(reportBlocks);
        return reportBlocksMapper.toDto(reportBlocks);
    }

    @Override
    public ReportBlocksDTO update(ReportBlocksDTO reportBlocksDTO) {
        log.debug("Request to update ReportBlocks : {}", reportBlocksDTO);
        Iterator<ReportBlocksContentDTO> iterator = reportBlocksDTO.getReportBlocksContents().iterator();
        while (iterator.hasNext()) {
            ReportBlocksContentDTO reportBlocksContentDTO = iterator.next();
            if (reportBlocksContentDTO.getDeleted() != null && reportBlocksContentDTO.getDeleted()) {
                iterator.remove();
                reportBlocksContentService.delete(reportBlocksContentDTO.getId());
            } else if (reportBlocksContentDTO.getNewContentData() != null && reportBlocksContentDTO.getNewContentData().equals("true")) {
                for (CountriesDTO countriesDTO : reportBlocksDTO.getCountryIds()) {
                    ReportBlocksContentDataDTO reportBlocksContentDataDTONew = new ReportBlocksContentDataDTO();
                    reportBlocksContentDataDTONew.setCountry(countriesDTO);
                    reportBlocksContentDataDTONew.setNewContentData("true");
                    if (reportBlocksContentDTO.getType().equals("text")) {
                        reportBlocksContentDataDTONew.setData(generateDefaultTextContentData());
                    } else {
                        reportBlocksContentDataDTONew.setData(generateDefaultTableContentData(reportBlocksContentDTO.getTemplate()));
                    }
                    reportBlocksContentDataDTONew.setId(null);
                    reportBlocksContentDTO.getReportBlocksContentData().add(reportBlocksContentDataDTONew);
                }
                reportBlocksContentDTO.setId(null);
            }
        }

        reportBlocksDTO = updateBlockContentDataByCountry(reportBlocksDTO);

        ReportBlocks reportBlocks = reportBlocksMapper.toEntity(reportBlocksDTO);
        reportBlocks = reportBlocksRepository.save(reportBlocks);
        return reportBlocksMapper.toDto(reportBlocks);
    }

    private ReportBlocksDTO updateBlockContentDataByCountry(ReportBlocksDTO reportBlocksDTO) {
        Set<CountriesDTO> countryIds = reportBlocksDTO.getCountryIds();
        Set<CountriesDTO> existingCountries = new HashSet<>();

        for (ReportBlocksContentDTO contentDTO : reportBlocksDTO.getReportBlocksContents()) {
            Set<ReportBlocksContentDataDTO> contentData = contentDTO.getReportBlocksContentData();
            Set<CountriesDTO> contentDataCountries = new HashSet<>();

            for (ReportBlocksContentDataDTO dataDTO : contentData) {
                CountriesDTO country = dataDTO.getCountry();
                contentDataCountries.add(country);

                if (countryIds.contains(country)) {
                    existingCountries.add(country);
                }
            }

            Set<CountriesDTO> missingCountries = new HashSet<>(countryIds);
            missingCountries.removeAll(contentDataCountries);

            for (CountriesDTO missingCountry : missingCountries) {
                ReportBlocksContentDataDTO newContentData = new ReportBlocksContentDataDTO();
                newContentData.setCountry(missingCountry);
                newContentData.setData(
                    contentDTO.getType().equals("text")
                        ? generateDefaultTextContentData()
                        : generateDefaultTableContentData(contentDTO.getTemplate())
                );
                contentData.add(newContentData);
            }
        }

        Set<CountriesDTO> missingCountries = new HashSet<>(countryIds);
        missingCountries.removeAll(existingCountries);

        return reportBlocksDTO;
    }

    private String generateDefaultTextContentData() {
        return "{\"data\":\"\"}";
    }

    private String generateDefaultTableContentData(String jsonTemplate) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ObjectNode templateNode = (ObjectNode) objectMapper.readTree(jsonTemplate);
            ObjectNode contentNode = objectMapper.createObjectNode();
            ArrayNode rowsArrayNode = objectMapper.createArrayNode();
            ArrayNode columnsArrayNode = (ArrayNode) templateNode.get("columns");
            for (int i = 0; i < columnsArrayNode.size(); i++) {
                ObjectNode rowNode = objectMapper.createObjectNode();
                rowNode.put("index", columnsArrayNode.get(i).get("index").asInt());
                rowNode.put("data", "");
                rowsArrayNode.add(rowNode);
            }
            contentNode.putArray("rows").addAll(rowsArrayNode);
            String contentJson = objectMapper.writeValueAsString(contentNode);

            return contentJson;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{\"rows\": []}";
    }

    @Override
    public Optional<ReportBlocksDTO> partialUpdate(ReportBlocksDTO reportBlocksDTO) {
        log.debug("Request to partially update ReportBlocks : {}", reportBlocksDTO);

        return reportBlocksRepository
            .findById(reportBlocksDTO.getId())
            .map(existingReportBlocks -> {
                reportBlocksMapper.partialUpdate(existingReportBlocks, reportBlocksDTO);

                return existingReportBlocks;
            })
            .map(reportBlocksRepository::save)
            .map(reportBlocksMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportBlocksDTO> findAll() {
        log.debug("Request to get all ReportBlocks");
        List<ReportBlocksDTO> reportBlocksDTOS = reportBlocksRepository
            .findAll()
            .stream()
            .map(reportBlocksMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
        return reportBlocksDTOS
            .stream()
            .map(reportBlocks -> {
                for (CountriesDTO country : reportBlocks.getCountryIds()) {
                    CountriesDTO countriesDTO = countriesService.findOne(country.getId()).get();
                    country.setCountryName(countriesDTO.getCountryName());
                    country.setCountryCode(countriesDTO.getCountryCode());
                }
                reportBlocks.getReport().setReportName(reportService.findOne(reportBlocks.getReport().getId()).get().getReportName());
                return reportBlocks;
            })
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<ReportBlocksDTO> findAllByReport(Long reportId, Long countryId) {
        log.debug("Request to get all ReportBlocks");
        return reportBlocksRepository
            .findAllWithEagerRelationshipsByReport(reportId, countryId)
            .stream()
            .map(reportBlocksMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<ReportBlocksDTO> findAllWithEagerRelationships(Pageable pageable) {
        return reportBlocksRepository.findAllWithEagerRelationships(pageable).map(reportBlocksMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReportBlocksDTO> findOne(Long id) {
        log.debug("Request to get ReportBlocks : {}", id);
        Optional<ReportBlocksDTO> reportBlocksDTO = reportBlocksRepository.findOneWithEagerRelationships(id).map(reportBlocksMapper::toDto);
        for (CountriesDTO country : reportBlocksDTO.get().getCountryIds()) {
            CountriesDTO countriesDTO = countriesService.findOne(country.getId()).get();
            country.setCountryName(countriesDTO.getCountryName());
            country.setCountryName(countriesDTO.getCountryCode());
        }
        return reportBlocksDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReportBlocksDTO> findOneWithCountry(Long id, Long countryId) {
        Optional<ReportBlocksDTO> reportBlocksDTO = reportBlocksRepository.findOneWithEagerRelationships(id).map(reportBlocksMapper::toDto);
        for (ReportBlocksContentDTO reportBlocksContentDTO : reportBlocksDTO.get().getReportBlocksContents()) {
            if (reportBlocksContentDTO.getReportBlocksContentData() != null) {
                Iterator<ReportBlocksContentDataDTO> iterator = reportBlocksContentDTO.getReportBlocksContentData().iterator();
                while (iterator.hasNext()) {
                    ReportBlocksContentDataDTO reportBlocksContentDataDTO = iterator.next();
                    try {
                        if (
                            reportBlocksContentDataDTO.getCountry() != null &&
                            !reportBlocksContentDataDTO.getCountry().getId().equals(countryId)
                        ) {
                            iterator.remove();
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }
        return reportBlocksDTO;
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReportBlocks : {}", id);
        reportBlocksRepository.deleteById(id);
    }
}
