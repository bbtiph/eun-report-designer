package org.eun.back.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A WorkingGroupReferences.
 */
@Entity
@Table(name = "working_group_references")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkingGroupReferences implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_representative_first_name")
    private String countryRepresentativeFirstName;

    @Column(name = "country_representative_last_name")
    private String countryRepresentativeLastName;

    @Column(name = "country_representative_mail")
    private String countryRepresentativeMail;

    @Column(name = "country_representative_position")
    private String countryRepresentativePosition;

    @Column(name = "country_representative_start_date")
    private LocalDate countryRepresentativeStartDate;

    @Column(name = "country_representative_end_date")
    private LocalDate countryRepresentativeEndDate;

    @Column(name = "country_representative_ministry")
    private String countryRepresentativeMinistry;

    @Column(name = "country_representative_department")
    private String countryRepresentativeDepartment;

    @Column(name = "contact_eun_first_name")
    private String contactEunFirstName;

    @Column(name = "contact_eun_last_name")
    private String contactEunLastName;

    @Column(name = "type")
    private String type;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "last_modified_date")
    private LocalDate lastModifiedDate;

    @Column(name = "sheet_num")
    private Long sheetNum;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WorkingGroupReferences id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public WorkingGroupReferences countryCode(String countryCode) {
        this.setCountryCode(countryCode);
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public WorkingGroupReferences countryName(String countryName) {
        this.setCountryName(countryName);
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryRepresentativeFirstName() {
        return this.countryRepresentativeFirstName;
    }

    public WorkingGroupReferences countryRepresentativeFirstName(String countryRepresentativeFirstName) {
        this.setCountryRepresentativeFirstName(countryRepresentativeFirstName);
        return this;
    }

    public void setCountryRepresentativeFirstName(String countryRepresentativeFirstName) {
        this.countryRepresentativeFirstName = countryRepresentativeFirstName;
    }

    public String getCountryRepresentativeLastName() {
        return this.countryRepresentativeLastName;
    }

    public WorkingGroupReferences countryRepresentativeLastName(String countryRepresentativeLastName) {
        this.setCountryRepresentativeLastName(countryRepresentativeLastName);
        return this;
    }

    public void setCountryRepresentativeLastName(String countryRepresentativeLastName) {
        this.countryRepresentativeLastName = countryRepresentativeLastName;
    }

    public String getCountryRepresentativeMail() {
        return this.countryRepresentativeMail;
    }

    public WorkingGroupReferences countryRepresentativeMail(String countryRepresentativeMail) {
        this.setCountryRepresentativeMail(countryRepresentativeMail);
        return this;
    }

    public void setCountryRepresentativeMail(String countryRepresentativeMail) {
        this.countryRepresentativeMail = countryRepresentativeMail;
    }

    public String getCountryRepresentativePosition() {
        return this.countryRepresentativePosition;
    }

    public WorkingGroupReferences countryRepresentativePosition(String countryRepresentativePosition) {
        this.setCountryRepresentativePosition(countryRepresentativePosition);
        return this;
    }

    public void setCountryRepresentativePosition(String countryRepresentativePosition) {
        this.countryRepresentativePosition = countryRepresentativePosition;
    }

    public LocalDate getCountryRepresentativeStartDate() {
        return this.countryRepresentativeStartDate;
    }

    public WorkingGroupReferences countryRepresentativeStartDate(LocalDate countryRepresentativeStartDate) {
        this.setCountryRepresentativeStartDate(countryRepresentativeStartDate);
        return this;
    }

    public void setCountryRepresentativeStartDate(LocalDate countryRepresentativeStartDate) {
        this.countryRepresentativeStartDate = countryRepresentativeStartDate;
    }

    public LocalDate getCountryRepresentativeEndDate() {
        return this.countryRepresentativeEndDate;
    }

    public WorkingGroupReferences countryRepresentativeEndDate(LocalDate countryRepresentativeEndDate) {
        this.setCountryRepresentativeEndDate(countryRepresentativeEndDate);
        return this;
    }

    public void setCountryRepresentativeEndDate(LocalDate countryRepresentativeEndDate) {
        this.countryRepresentativeEndDate = countryRepresentativeEndDate;
    }

    public String getCountryRepresentativeMinistry() {
        return this.countryRepresentativeMinistry;
    }

    public WorkingGroupReferences countryRepresentativeMinistry(String countryRepresentativeMinistry) {
        this.setCountryRepresentativeMinistry(countryRepresentativeMinistry);
        return this;
    }

    public void setCountryRepresentativeMinistry(String countryRepresentativeMinistry) {
        this.countryRepresentativeMinistry = countryRepresentativeMinistry;
    }

    public String getCountryRepresentativeDepartment() {
        return this.countryRepresentativeDepartment;
    }

    public WorkingGroupReferences countryRepresentativeDepartment(String countryRepresentativeDepartment) {
        this.setCountryRepresentativeDepartment(countryRepresentativeDepartment);
        return this;
    }

    public void setCountryRepresentativeDepartment(String countryRepresentativeDepartment) {
        this.countryRepresentativeDepartment = countryRepresentativeDepartment;
    }

    public String getContactEunFirstName() {
        return this.contactEunFirstName;
    }

    public WorkingGroupReferences contactEunFirstName(String contactEunFirstName) {
        this.setContactEunFirstName(contactEunFirstName);
        return this;
    }

    public void setContactEunFirstName(String contactEunFirstName) {
        this.contactEunFirstName = contactEunFirstName;
    }

    public String getContactEunLastName() {
        return this.contactEunLastName;
    }

    public WorkingGroupReferences contactEunLastName(String contactEunLastName) {
        this.setContactEunLastName(contactEunLastName);
        return this;
    }

    public void setContactEunLastName(String contactEunLastName) {
        this.contactEunLastName = contactEunLastName;
    }

    public String getType() {
        return this.type;
    }

    public WorkingGroupReferences type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public WorkingGroupReferences isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public WorkingGroupReferences createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public WorkingGroupReferences lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public WorkingGroupReferences createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public WorkingGroupReferences lastModifiedDate(LocalDate lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getSheetNum() {
        return this.sheetNum;
    }

    public WorkingGroupReferences sheetNum(Long sheetNum) {
        this.setSheetNum(sheetNum);
        return this;
    }

    public void setSheetNum(Long sheetNum) {
        this.sheetNum = sheetNum;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkingGroupReferences)) {
            return false;
        }
        return id != null && id.equals(((WorkingGroupReferences) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkingGroupReferences{" +
            "id=" + getId() +
            ", countryCode='" + getCountryCode() + "'" +
            ", countryName='" + getCountryName() + "'" +
            ", countryRepresentativeFirstName='" + getCountryRepresentativeFirstName() + "'" +
            ", countryRepresentativeLastName='" + getCountryRepresentativeLastName() + "'" +
            ", countryRepresentativeMail='" + getCountryRepresentativeMail() + "'" +
            ", countryRepresentativePosition='" + getCountryRepresentativePosition() + "'" +
            ", countryRepresentativeStartDate='" + getCountryRepresentativeStartDate() + "'" +
            ", countryRepresentativeEndDate='" + getCountryRepresentativeEndDate() + "'" +
            ", countryRepresentativeMinistry='" + getCountryRepresentativeMinistry() + "'" +
            ", countryRepresentativeDepartment='" + getCountryRepresentativeDepartment() + "'" +
            ", contactEunFirstName='" + getContactEunFirstName() + "'" +
            ", contactEunLastName='" + getContactEunLastName() + "'" +
            ", type='" + getType() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", sheetNum=" + getSheetNum() +
            "}";
    }
}
