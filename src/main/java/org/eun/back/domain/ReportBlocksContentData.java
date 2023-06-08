package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A ReportBlocksContentData.
 */
@Entity
@Table(name = "report_blocks_content_data")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReportBlocksContentData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data")
    private String data;

    @ManyToOne(cascade = CascadeType.ALL)
    //    @JsonBackReference
    @JsonIgnoreProperties(value = { "reportBlocks", "reportBlocksContentData", "reportBlocksContent" }, allowSetters = true)
    private ReportBlocksContent reportBlocksContent;

    @ManyToOne
    @JsonIgnoreProperties(value = { "reportBlockIds" }, allowSetters = true)
    private Countries country;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReportBlocksContentData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return this.data;
    }

    public ReportBlocksContentData data(String data) {
        this.setData(data);
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ReportBlocksContent getReportBlocksContent() {
        return this.reportBlocksContent;
    }

    public void setReportBlocksContent(ReportBlocksContent reportBlocksContent) {
        this.reportBlocksContent = reportBlocksContent;
    }

    public ReportBlocksContentData reportBlocksContent(ReportBlocksContent reportBlocksContent) {
        this.setReportBlocksContent(reportBlocksContent);
        return this;
    }

    public Countries getCountry() {
        return this.country;
    }

    public void setCountry(Countries countries) {
        this.country = countries;
    }

    public ReportBlocksContentData country(Countries countries) {
        this.setCountry(countries);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportBlocksContentData)) {
            return false;
        }
        return id != null && id.equals(((ReportBlocksContentData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportBlocksContentData{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            "}";
    }
}
