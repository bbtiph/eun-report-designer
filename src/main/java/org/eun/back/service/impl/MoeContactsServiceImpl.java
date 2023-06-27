package org.eun.back.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.*;
import org.eun.back.domain.MoeContacts;
import org.eun.back.repository.MoeContactsRepository;
import org.eun.back.service.MoeContactsService;
import org.eun.back.service.dto.MoeContactsDTO;
import org.eun.back.service.mapper.MoeContactsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing {@link MoeContacts}.
 */
@Service
@Transactional
public class MoeContactsServiceImpl implements MoeContactsService {

    private final Logger log = LoggerFactory.getLogger(MoeContactsServiceImpl.class);

    private final MoeContactsRepository moeContactsRepository;

    private final MoeContactsMapper moeContactsMapper;

    public MoeContactsServiceImpl(MoeContactsRepository moeContactsRepository, MoeContactsMapper moeContactsMapper) {
        this.moeContactsRepository = moeContactsRepository;
        this.moeContactsMapper = moeContactsMapper;
    }

    @Override
    public MoeContactsDTO save(MoeContactsDTO moeContactsDTO) {
        log.debug("Request to save MoeContacts : {}", moeContactsDTO);
        MoeContacts moeContacts = moeContactsMapper.toEntity(moeContactsDTO);
        moeContacts = moeContactsRepository.save(moeContacts);
        return moeContactsMapper.toDto(moeContacts);
    }

    @Override
    public MoeContactsDTO update(MoeContactsDTO moeContactsDTO) {
        log.debug("Request to update MoeContacts : {}", moeContactsDTO);
        MoeContacts moeContacts = moeContactsMapper.toEntity(moeContactsDTO);
        moeContacts = moeContactsRepository.save(moeContacts);
        return moeContactsMapper.toDto(moeContacts);
    }

    @Override
    public Optional<MoeContactsDTO> partialUpdate(MoeContactsDTO moeContactsDTO) {
        log.debug("Request to partially update MoeContacts : {}", moeContactsDTO);

        return moeContactsRepository
            .findById(moeContactsDTO.getId())
            .map(existingMoeContacts -> {
                moeContactsMapper.partialUpdate(existingMoeContacts, moeContactsDTO);

                return existingMoeContacts;
            })
            .map(moeContactsRepository::save)
            .map(moeContactsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MoeContactsDTO> findAll() {
        log.debug("Request to get all MoeContacts");
        return moeContactsRepository.findAll().stream().map(moeContactsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MoeContactsDTO> findOne(Long id) {
        log.debug("Request to get MoeContacts : {}", id);
        return moeContactsRepository.findById(id).map(moeContactsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MoeContacts : {}", id);
        moeContactsRepository.deleteById(id);
    }

    @Override
    public void upload(MultipartFile file) {
        try (InputStream fileInputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);

            int i = 0;
            for (Row row : sheet) {
                i++;
                if (i > 2) {
                    try {
                        if (row.getCell(0) == null || row.getCell(1) == null) {
                            break;
                        }
                        MoeContacts moeContacts = new MoeContacts();
                        moeContacts.setCountryCode(row.getCell(0) != null ? row.getCell(0).toString() : "");
                        Cell cell = row.getCell(1);
                        String cellValue = "";

                        if (cell != null) {
                            if (cell.getCellType() == CellType.FORMULA) {
                                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                                CellValue evaluatedCellValue = evaluator.evaluate(cell);
                                switch (evaluatedCellValue.getCellType()) {
                                    case STRING:
                                        cellValue = evaluatedCellValue.getStringValue();
                                        break;
                                    case NUMERIC:
                                        cellValue = String.valueOf(evaluatedCellValue.getNumberValue());
                                        break;
                                    case BOOLEAN:
                                        cellValue = String.valueOf(evaluatedCellValue.getBooleanValue());
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                cellValue = cell.toString();
                            }
                        }

                        moeContacts.setCountryName(cellValue);
                        moeContacts.setMinistryName(row.getCell(2) != null ? row.getCell(2).toString() : "");
                        moeContacts.setMinistryEnglishName(row.getCell(3) != null ? row.getCell(3).toString() : "");
                        moeContacts.setPostalAddress(row.getCell(4) != null ? row.getCell(4).toString() : "");
                        moeContacts.setShippingAddress(row.getCell(5) != null ? row.getCell(5).toString() : "");
                        moeContacts.setContactEunFirstName(row.getCell(7) != null ? row.getCell(7).toString() : "");
                        moeContacts.setContactEunLastName(row.getCell(8) != null ? row.getCell(8).toString() : "");
                        MoeContacts moeContactsRes = moeContactsRepository.findByCountryCodeAndMinistryEnglishName(
                            moeContacts.getCountryCode(),
                            moeContacts.getMinistryEnglishName()
                        );
                        if (moeContactsRes != null) {
                            moeContacts.setId(moeContactsRes.getId());
                        }
                        moeContactsRepository.save(moeContacts);
                    } catch (Exception e) {
                        log.error("Error ", e);
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error ", e);
        }
    }
}
