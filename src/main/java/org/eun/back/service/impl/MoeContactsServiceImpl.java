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
                // Обработка данных в каждой строке
                i++;
                if (i > 1 && i < 34) {
                    try {
                        MoeContacts moeContacts = new MoeContacts();
                        moeContacts.setCountryCode(row.getCell(0) != null ? row.getCell(0).toString() : "");
                        //                        moeContacts.setCountryName(row.getCell(1) !=null ? row.getCell(1).toString() : "");
                        moeContacts.setMinistryName(row.getCell(2) != null ? row.getCell(2).toString() : "");
                        moeContacts.setMinistryEnglishName(row.getCell(3) != null ? row.getCell(3).toString() : "");
                        moeContacts.setPostalAddress(row.getCell(4) != null ? row.getCell(4).toString() : "");
                        moeContacts.setShippingAddress(row.getCell(5) != null ? row.getCell(5).toString() : "");
                        moeContacts.setContactEunFirstName(row.getCell(7) != null ? row.getCell(7).toString() : "");
                        moeContacts.setContactEunLastName(row.getCell(8) != null ? row.getCell(8).toString() : "");
                        moeContactsRepository.save(moeContacts);
                    } catch (Exception e) {}
                }

                if (i > 30) {
                    break;
                }
                //                yourService.saveData(cell1.getStringCellValue(), cell2.getStringCellValue());
            }
        } catch (IOException e) {
            System.out.println("error>> " + e);
            log.error("Error ", e);
        }
        //        HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
        //
        //        HSSFSheet sheet=wb.getSheetAt(0);
        //        HSSFRow row;
        //        HSSFCell cell;
        //
        //        Iterator rows = sheet.rowIterator();
        //
        //        while (rows.hasNext())
        //        {
        //            row=(HSSFRow) rows.next();
        //            Iterator cells = row.cellIterator();
        //
        //            while (cells.hasNext())
        //            {
        //                cell=(HSSFCell) cells.next();
        //
        //                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
        //                {
        //                    System.out.print(cell.getStringCellValue()+" ");
        //                }
        //                else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
        //                {
        //                    System.out.print(cell.getNumericCellValue()+" ");
        //                }
        //                else
        //                {
        //                    //U Can Handel Boolean, Formula, Errors
        //                }
        //            }
        //            System.out.println();
        //        }
    }
}
