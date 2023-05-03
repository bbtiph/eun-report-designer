package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A OperationalBodyMember.
 */
@Entity
@Table(name = "operational_body_member")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OperationalBodyMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "position")
    private String position;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "department")
    private String department;

    @Column(name = "eun_contact_firstname")
    private String eunContactFirstname;

    @Column(name = "eun_contact_lastname")
    private String eunContactLastname;

    @Column(name = "cooperation_field")
    private String cooperationField;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ministries", "operationalBodyMembers", "organizations", "people" }, allowSetters = true)
    private Countries country;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OperationalBodyMember id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return this.personId;
    }

    public OperationalBodyMember personId(Long personId) {
        this.setPersonId(personId);
        return this;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPosition() {
        return this.position;
    }

    public OperationalBodyMember position(String position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public OperationalBodyMember startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public OperationalBodyMember endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDepartment() {
        return this.department;
    }

    public OperationalBodyMember department(String department) {
        this.setDepartment(department);
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEunContactFirstname() {
        return this.eunContactFirstname;
    }

    public OperationalBodyMember eunContactFirstname(String eunContactFirstname) {
        this.setEunContactFirstname(eunContactFirstname);
        return this;
    }

    public void setEunContactFirstname(String eunContactFirstname) {
        this.eunContactFirstname = eunContactFirstname;
    }

    public String getEunContactLastname() {
        return this.eunContactLastname;
    }

    public OperationalBodyMember eunContactLastname(String eunContactLastname) {
        this.setEunContactLastname(eunContactLastname);
        return this;
    }

    public void setEunContactLastname(String eunContactLastname) {
        this.eunContactLastname = eunContactLastname;
    }

    public String getCooperationField() {
        return this.cooperationField;
    }

    public OperationalBodyMember cooperationField(String cooperationField) {
        this.setCooperationField(cooperationField);
        return this;
    }

    public void setCooperationField(String cooperationField) {
        this.cooperationField = cooperationField;
    }

    public String getStatus() {
        return this.status;
    }

    public OperationalBodyMember status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Countries getCountry() {
        return this.country;
    }

    public void setCountry(Countries countries) {
        this.country = countries;
    }

    public OperationalBodyMember country(Countries countries) {
        this.setCountry(countries);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperationalBodyMember)) {
            return false;
        }
        return id != null && id.equals(((OperationalBodyMember) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperationalBodyMember{" +
            "id=" + getId() +
            ", personId=" + getPersonId() +
            ", position='" + getPosition() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", department='" + getDepartment() + "'" +
            ", eunContactFirstname='" + getEunContactFirstname() + "'" +
            ", eunContactLastname='" + getEunContactLastname() + "'" +
            ", cooperationField='" + getCooperationField() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
