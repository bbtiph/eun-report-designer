package org.eun.back.service.mapper;

import org.eun.back.domain.JobInfo;
import org.eun.back.service.dto.JobInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link JobInfo} and its DTO {@link JobInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface JobInfoMapper extends EntityMapper<JobInfoDTO, JobInfo> {}
