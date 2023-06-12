package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.ReportBlocksContentData} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReportBlocksContentDataDTO implements Serializable {

    private Long id;

    private String data;

    //    private ReportBlocksContentDTO reportBlocksContent;

    private CountriesDTO country;

    private String newContentData;

    public String getNewContentData() {
        return newContentData;
    }

    public void setNewContentData(String newContentData) {
        this.newContentData = newContentData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    //    public ReportBlocksContentDTO getReportBlocksContent() {
    //        return reportBlocksContent;
    //    }
    //
    //    public void setReportBlocksContent(ReportBlocksContentDTO reportBlocksContent) {
    //        this.reportBlocksContent = reportBlocksContent;
    //    }

    public CountriesDTO getCountry() {
        return country;
    }

    public void setCountry(CountriesDTO country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportBlocksContentDataDTO)) {
            return false;
        }

        ReportBlocksContentDataDTO reportBlocksContentDataDTO = (ReportBlocksContentDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reportBlocksContentDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportBlocksContentDataDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
//            ", reportBlocksContent=" + getReportBlocksContent() +
            ", country=" + getCountry() +
            "}";
    }
}
