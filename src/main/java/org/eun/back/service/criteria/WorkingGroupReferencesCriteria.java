package org.eun.back.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link org.eun.back.domain.WorkingGroupReferences} entity. This class is used
 * in {@link org.eun.back.web.rest.WorkingGroupReferencesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /working-group-references?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkingGroupReferencesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter countryCode;

    private StringFilter countryName;

    private StringFilter countryRepresentativeFirstName;

    private StringFilter countryRepresentativeLastName;

    private StringFilter countryRepresentativeMail;

    private StringFilter countryRepresentativePosition;

    private LocalDateFilter countryRepresentativeStartDate;

    private LocalDateFilter countryRepresentativeEndDate;

    private StringFilter countryRepresentativeMinistry;

    private StringFilter countryRepresentativeDepartment;

    private StringFilter contactEunFirstName;

    private StringFilter contactEunLastName;

    private StringFilter type;

    private BooleanFilter isActive;

    private StringFilter createdBy;

    private StringFilter lastModifiedBy;

    private LocalDateFilter createdDate;

    private LocalDateFilter lastModifiedDate;

    private LongFilter sheetNum;

    private Boolean distinct;

    public WorkingGroupReferencesCriteria() {}

    public WorkingGroupReferencesCriteria(WorkingGroupReferencesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.countryCode = other.countryCode == null ? null : other.countryCode.copy();
        this.countryName = other.countryName == null ? null : other.countryName.copy();
        this.countryRepresentativeFirstName =
            other.countryRepresentativeFirstName == null ? null : other.countryRepresentativeFirstName.copy();
        this.countryRepresentativeLastName =
            other.countryRepresentativeLastName == null ? null : other.countryRepresentativeLastName.copy();
        this.countryRepresentativeMail = other.countryRepresentativeMail == null ? null : other.countryRepresentativeMail.copy();
        this.countryRepresentativePosition =
            other.countryRepresentativePosition == null ? null : other.countryRepresentativePosition.copy();
        this.countryRepresentativeStartDate =
            other.countryRepresentativeStartDate == null ? null : other.countryRepresentativeStartDate.copy();
        this.countryRepresentativeEndDate = other.countryRepresentativeEndDate == null ? null : other.countryRepresentativeEndDate.copy();
        this.countryRepresentativeMinistry =
            other.countryRepresentativeMinistry == null ? null : other.countryRepresentativeMinistry.copy();
        this.countryRepresentativeDepartment =
            other.countryRepresentativeDepartment == null ? null : other.countryRepresentativeDepartment.copy();
        this.contactEunFirstName = other.contactEunFirstName == null ? null : other.contactEunFirstName.copy();
        this.contactEunLastName = other.contactEunLastName == null ? null : other.contactEunLastName.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.sheetNum = other.sheetNum == null ? null : other.sheetNum.copy();
        this.distinct = other.distinct;
    }

    @Override
    public WorkingGroupReferencesCriteria copy() {
        return new WorkingGroupReferencesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCountryCode() {
        return countryCode;
    }

    public StringFilter countryCode() {
        if (countryCode == null) {
            countryCode = new StringFilter();
        }
        return countryCode;
    }

    public void setCountryCode(StringFilter countryCode) {
        this.countryCode = countryCode;
    }

    public StringFilter getCountryName() {
        return countryName;
    }

    public StringFilter countryName() {
        if (countryName == null) {
            countryName = new StringFilter();
        }
        return countryName;
    }

    public void setCountryName(StringFilter countryName) {
        this.countryName = countryName;
    }

    public StringFilter getCountryRepresentativeFirstName() {
        return countryRepresentativeFirstName;
    }

    public StringFilter countryRepresentativeFirstName() {
        if (countryRepresentativeFirstName == null) {
            countryRepresentativeFirstName = new StringFilter();
        }
        return countryRepresentativeFirstName;
    }

    public void setCountryRepresentativeFirstName(StringFilter countryRepresentativeFirstName) {
        this.countryRepresentativeFirstName = countryRepresentativeFirstName;
    }

    public StringFilter getCountryRepresentativeLastName() {
        return countryRepresentativeLastName;
    }

    public StringFilter countryRepresentativeLastName() {
        if (countryRepresentativeLastName == null) {
            countryRepresentativeLastName = new StringFilter();
        }
        return countryRepresentativeLastName;
    }

    public void setCountryRepresentativeLastName(StringFilter countryRepresentativeLastName) {
        this.countryRepresentativeLastName = countryRepresentativeLastName;
    }

    public StringFilter getCountryRepresentativeMail() {
        return countryRepresentativeMail;
    }

    public StringFilter countryRepresentativeMail() {
        if (countryRepresentativeMail == null) {
            countryRepresentativeMail = new StringFilter();
        }
        return countryRepresentativeMail;
    }

    public void setCountryRepresentativeMail(StringFilter countryRepresentativeMail) {
        this.countryRepresentativeMail = countryRepresentativeMail;
    }

    public StringFilter getCountryRepresentativePosition() {
        return countryRepresentativePosition;
    }

    public StringFilter countryRepresentativePosition() {
        if (countryRepresentativePosition == null) {
            countryRepresentativePosition = new StringFilter();
        }
        return countryRepresentativePosition;
    }

    public void setCountryRepresentativePosition(StringFilter countryRepresentativePosition) {
        this.countryRepresentativePosition = countryRepresentativePosition;
    }

    public LocalDateFilter getCountryRepresentativeStartDate() {
        return countryRepresentativeStartDate;
    }

    public LocalDateFilter countryRepresentativeStartDate() {
        if (countryRepresentativeStartDate == null) {
            countryRepresentativeStartDate = new LocalDateFilter();
        }
        return countryRepresentativeStartDate;
    }

    public void setCountryRepresentativeStartDate(LocalDateFilter countryRepresentativeStartDate) {
        this.countryRepresentativeStartDate = countryRepresentativeStartDate;
    }

    public LocalDateFilter getCountryRepresentativeEndDate() {
        return countryRepresentativeEndDate;
    }

    public LocalDateFilter countryRepresentativeEndDate() {
        if (countryRepresentativeEndDate == null) {
            countryRepresentativeEndDate = new LocalDateFilter();
        }
        return countryRepresentativeEndDate;
    }

    public void setCountryRepresentativeEndDate(LocalDateFilter countryRepresentativeEndDate) {
        this.countryRepresentativeEndDate = countryRepresentativeEndDate;
    }

    public StringFilter getCountryRepresentativeMinistry() {
        return countryRepresentativeMinistry;
    }

    public StringFilter countryRepresentativeMinistry() {
        if (countryRepresentativeMinistry == null) {
            countryRepresentativeMinistry = new StringFilter();
        }
        return countryRepresentativeMinistry;
    }

    public void setCountryRepresentativeMinistry(StringFilter countryRepresentativeMinistry) {
        this.countryRepresentativeMinistry = countryRepresentativeMinistry;
    }

    public StringFilter getCountryRepresentativeDepartment() {
        return countryRepresentativeDepartment;
    }

    public StringFilter countryRepresentativeDepartment() {
        if (countryRepresentativeDepartment == null) {
            countryRepresentativeDepartment = new StringFilter();
        }
        return countryRepresentativeDepartment;
    }

    public void setCountryRepresentativeDepartment(StringFilter countryRepresentativeDepartment) {
        this.countryRepresentativeDepartment = countryRepresentativeDepartment;
    }

    public StringFilter getContactEunFirstName() {
        return contactEunFirstName;
    }

    public StringFilter contactEunFirstName() {
        if (contactEunFirstName == null) {
            contactEunFirstName = new StringFilter();
        }
        return contactEunFirstName;
    }

    public void setContactEunFirstName(StringFilter contactEunFirstName) {
        this.contactEunFirstName = contactEunFirstName;
    }

    public StringFilter getContactEunLastName() {
        return contactEunLastName;
    }

    public StringFilter contactEunLastName() {
        if (contactEunLastName == null) {
            contactEunLastName = new StringFilter();
        }
        return contactEunLastName;
    }

    public void setContactEunLastName(StringFilter contactEunLastName) {
        this.contactEunLastName = contactEunLastName;
    }

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public BooleanFilter isActive() {
        if (isActive == null) {
            isActive = new BooleanFilter();
        }
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateFilter getCreatedDate() {
        return createdDate;
    }

    public LocalDateFilter createdDate() {
        if (createdDate == null) {
            createdDate = new LocalDateFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(LocalDateFilter createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public LocalDateFilter lastModifiedDate() {
        if (lastModifiedDate == null) {
            lastModifiedDate = new LocalDateFilter();
        }
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LongFilter getSheetNum() {
        return sheetNum;
    }

    public LongFilter sheetNum() {
        if (sheetNum == null) {
            sheetNum = new LongFilter();
        }
        return sheetNum;
    }

    public void setSheetNum(LongFilter sheetNum) {
        this.sheetNum = sheetNum;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WorkingGroupReferencesCriteria that = (WorkingGroupReferencesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(countryCode, that.countryCode) &&
            Objects.equals(countryName, that.countryName) &&
            Objects.equals(countryRepresentativeFirstName, that.countryRepresentativeFirstName) &&
            Objects.equals(countryRepresentativeLastName, that.countryRepresentativeLastName) &&
            Objects.equals(countryRepresentativeMail, that.countryRepresentativeMail) &&
            Objects.equals(countryRepresentativePosition, that.countryRepresentativePosition) &&
            Objects.equals(countryRepresentativeStartDate, that.countryRepresentativeStartDate) &&
            Objects.equals(countryRepresentativeEndDate, that.countryRepresentativeEndDate) &&
            Objects.equals(countryRepresentativeMinistry, that.countryRepresentativeMinistry) &&
            Objects.equals(countryRepresentativeDepartment, that.countryRepresentativeDepartment) &&
            Objects.equals(contactEunFirstName, that.contactEunFirstName) &&
            Objects.equals(contactEunLastName, that.contactEunLastName) &&
            Objects.equals(type, that.type) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(sheetNum, that.sheetNum) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            countryCode,
            countryName,
            countryRepresentativeFirstName,
            countryRepresentativeLastName,
            countryRepresentativeMail,
            countryRepresentativePosition,
            countryRepresentativeStartDate,
            countryRepresentativeEndDate,
            countryRepresentativeMinistry,
            countryRepresentativeDepartment,
            contactEunFirstName,
            contactEunLastName,
            type,
            isActive,
            createdBy,
            lastModifiedBy,
            createdDate,
            lastModifiedDate,
            sheetNum,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkingGroupReferencesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (countryCode != null ? "countryCode=" + countryCode + ", " : "") +
            (countryName != null ? "countryName=" + countryName + ", " : "") +
            (countryRepresentativeFirstName != null ? "countryRepresentativeFirstName=" + countryRepresentativeFirstName + ", " : "") +
            (countryRepresentativeLastName != null ? "countryRepresentativeLastName=" + countryRepresentativeLastName + ", " : "") +
            (countryRepresentativeMail != null ? "countryRepresentativeMail=" + countryRepresentativeMail + ", " : "") +
            (countryRepresentativePosition != null ? "countryRepresentativePosition=" + countryRepresentativePosition + ", " : "") +
            (countryRepresentativeStartDate != null ? "countryRepresentativeStartDate=" + countryRepresentativeStartDate + ", " : "") +
            (countryRepresentativeEndDate != null ? "countryRepresentativeEndDate=" + countryRepresentativeEndDate + ", " : "") +
            (countryRepresentativeMinistry != null ? "countryRepresentativeMinistry=" + countryRepresentativeMinistry + ", " : "") +
            (countryRepresentativeDepartment != null ? "countryRepresentativeDepartment=" + countryRepresentativeDepartment + ", " : "") +
            (contactEunFirstName != null ? "contactEunFirstName=" + contactEunFirstName + ", " : "") +
            (contactEunLastName != null ? "contactEunLastName=" + contactEunLastName + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (isActive != null ? "isActive=" + isActive + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (sheetNum != null ? "sheetNum=" + sheetNum + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
