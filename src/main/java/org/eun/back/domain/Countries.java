package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Countries.
 */
@Entity
@Table(name = "countries")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Countries implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "country_name")
    private String countryName;

    @OneToMany(mappedBy = "country")
    @JsonIgnoreProperties(value = { "country" }, allowSetters = true)
    private Set<ReportBlocks> reportBlocks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @OneToMany(mappedBy = "country")
    @JsonIgnoreProperties(value = { "organizationInMinistries", "country" }, allowSetters = true)
    private Set<Ministry> ministries = new HashSet<>();

    @OneToMany(mappedBy = "country")
    @JsonIgnoreProperties(value = { "country" }, allowSetters = true)
    private Set<OperationalBodyMember> operationalBodyMembers = new HashSet<>();

    @OneToMany(mappedBy = "country")
    @JsonIgnoreProperties(
        value = { "eventInOrganizations", "organizationInMinistries", "organizationInProjects", "personInOrganizations", "country" },
        allowSetters = true
    )
    private Set<Organization> organizations = new HashSet<>();

    @OneToMany(mappedBy = "country")
    @JsonIgnoreProperties(
        value = { "eunTeamMembers", "eventParticipants", "personInOrganizations", "personInProjects", "country" },
        allowSetters = true
    )
    private Set<Person> people = new HashSet<>();

    public Long getId() {
        return this.id;
    }

    public Countries id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public Countries countryName(String countryName) {
        this.setCountryName(countryName);
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Set<ReportBlocks> getReportBlocks() {
        return this.reportBlocks;
    }

    public void setReportBlocks(Set<ReportBlocks> reportBlocks) {
        if (this.reportBlocks != null) {
            this.reportBlocks.forEach(i -> i.setCountry(null));
        }
        if (reportBlocks != null) {
            reportBlocks.forEach(i -> i.setCountry(this));
        }
        this.reportBlocks = reportBlocks;
    }

    public Countries reportBlocks(Set<ReportBlocks> reportBlocks) {
        this.setReportBlocks(reportBlocks);
        return this;
    }

    public Countries addReportBlocks(ReportBlocks reportBlocks) {
        this.reportBlocks.add(reportBlocks);
        reportBlocks.setCountry(this);
        return this;
    }

    public Countries removeReportBlocks(ReportBlocks reportBlocks) {
        this.reportBlocks.remove(reportBlocks);
        reportBlocks.setCountry(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Countries)) {
            return false;
        }
        return id != null && id.equals(((Countries) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Countries{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            "}";
    }
}
