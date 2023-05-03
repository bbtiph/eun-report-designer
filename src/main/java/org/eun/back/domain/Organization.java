package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.eun.back.domain.enumeration.OrgStatus;

/**
 * A Organization.
 */
@Entity
@Table(name = "organization")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "eun_db_id")
    private Long eunDbId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrgStatus status;

    @NotNull
    @Column(name = "official_name", nullable = false)
    private String officialName;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private Integer latitude;

    @Column(name = "longitude")
    private Integer longitude;

    @Column(name = "max_age")
    private Integer maxAge;

    @Column(name = "min_age")
    private Integer minAge;

    @Column(name = "area")
    private Integer area;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "number_of_students")
    private String numberOfStudents;

    @Column(name = "hardware_used")
    private Boolean hardwareUsed;

    @Column(name = "ict_used")
    private Boolean ictUsed;

    @Column(name = "website")
    private String website;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "email")
    private String email;

    @Column(name = "organisation_number")
    private String organisationNumber;

    @OneToMany(mappedBy = "organization")
    @JsonIgnoreProperties(value = { "event", "organization" }, allowSetters = true)
    private Set<EventInOrganization> eventInOrganizations = new HashSet<>();

    @OneToMany(mappedBy = "organization")
    @JsonIgnoreProperties(value = { "ministry", "organization" }, allowSetters = true)
    private Set<OrganizationInMinistry> organizationInMinistries = new HashSet<>();

    @OneToMany(mappedBy = "organization")
    @JsonIgnoreProperties(value = { "project", "organization" }, allowSetters = true)
    private Set<OrganizationInProject> organizationInProjects = new HashSet<>();

    @OneToMany(mappedBy = "organization")
    @JsonIgnoreProperties(value = { "person", "organization" }, allowSetters = true)
    private Set<PersonInOrganization> personInOrganizations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "ministries", "operationalBodyMembers", "organizations", "people" }, allowSetters = true)
    private Countries country;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Organization id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEunDbId() {
        return this.eunDbId;
    }

    public Organization eunDbId(Long eunDbId) {
        this.setEunDbId(eunDbId);
        return this;
    }

    public void setEunDbId(Long eunDbId) {
        this.eunDbId = eunDbId;
    }

    public OrgStatus getStatus() {
        return this.status;
    }

    public Organization status(OrgStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(OrgStatus status) {
        this.status = status;
    }

    public String getOfficialName() {
        return this.officialName;
    }

    public Organization officialName(String officialName) {
        this.setOfficialName(officialName);
        return this;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getDescription() {
        return this.description;
    }

    public Organization description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }

    public Organization type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return this.address;
    }

    public Organization address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLatitude() {
        return this.latitude;
    }

    public Organization latitude(Integer latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return this.longitude;
    }

    public Organization longitude(Integer longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getMaxAge() {
        return this.maxAge;
    }

    public Organization maxAge(Integer maxAge) {
        this.setMaxAge(maxAge);
        return this;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMinAge() {
        return this.minAge;
    }

    public Organization minAge(Integer minAge) {
        this.setMinAge(minAge);
        return this;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getArea() {
        return this.area;
    }

    public Organization area(Integer area) {
        this.setArea(area);
        return this;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getSpecialization() {
        return this.specialization;
    }

    public Organization specialization(String specialization) {
        this.setSpecialization(specialization);
        return this;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getNumberOfStudents() {
        return this.numberOfStudents;
    }

    public Organization numberOfStudents(String numberOfStudents) {
        this.setNumberOfStudents(numberOfStudents);
        return this;
    }

    public void setNumberOfStudents(String numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public Boolean getHardwareUsed() {
        return this.hardwareUsed;
    }

    public Organization hardwareUsed(Boolean hardwareUsed) {
        this.setHardwareUsed(hardwareUsed);
        return this;
    }

    public void setHardwareUsed(Boolean hardwareUsed) {
        this.hardwareUsed = hardwareUsed;
    }

    public Boolean getIctUsed() {
        return this.ictUsed;
    }

    public Organization ictUsed(Boolean ictUsed) {
        this.setIctUsed(ictUsed);
        return this;
    }

    public void setIctUsed(Boolean ictUsed) {
        this.ictUsed = ictUsed;
    }

    public String getWebsite() {
        return this.website;
    }

    public Organization website(String website) {
        this.setWebsite(website);
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Organization image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public Organization imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Organization telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return this.fax;
    }

    public Organization fax(String fax) {
        this.setFax(fax);
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return this.email;
    }

    public Organization email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganisationNumber() {
        return this.organisationNumber;
    }

    public Organization organisationNumber(String organisationNumber) {
        this.setOrganisationNumber(organisationNumber);
        return this;
    }

    public void setOrganisationNumber(String organisationNumber) {
        this.organisationNumber = organisationNumber;
    }

    public Set<EventInOrganization> getEventInOrganizations() {
        return this.eventInOrganizations;
    }

    public void setEventInOrganizations(Set<EventInOrganization> eventInOrganizations) {
        if (this.eventInOrganizations != null) {
            this.eventInOrganizations.forEach(i -> i.setOrganization(null));
        }
        if (eventInOrganizations != null) {
            eventInOrganizations.forEach(i -> i.setOrganization(this));
        }
        this.eventInOrganizations = eventInOrganizations;
    }

    public Organization eventInOrganizations(Set<EventInOrganization> eventInOrganizations) {
        this.setEventInOrganizations(eventInOrganizations);
        return this;
    }

    public Organization addEventInOrganization(EventInOrganization eventInOrganization) {
        this.eventInOrganizations.add(eventInOrganization);
        eventInOrganization.setOrganization(this);
        return this;
    }

    public Organization removeEventInOrganization(EventInOrganization eventInOrganization) {
        this.eventInOrganizations.remove(eventInOrganization);
        eventInOrganization.setOrganization(null);
        return this;
    }

    public Set<OrganizationInMinistry> getOrganizationInMinistries() {
        return this.organizationInMinistries;
    }

    public void setOrganizationInMinistries(Set<OrganizationInMinistry> organizationInMinistries) {
        if (this.organizationInMinistries != null) {
            this.organizationInMinistries.forEach(i -> i.setOrganization(null));
        }
        if (organizationInMinistries != null) {
            organizationInMinistries.forEach(i -> i.setOrganization(this));
        }
        this.organizationInMinistries = organizationInMinistries;
    }

    public Organization organizationInMinistries(Set<OrganizationInMinistry> organizationInMinistries) {
        this.setOrganizationInMinistries(organizationInMinistries);
        return this;
    }

    public Organization addOrganizationInMinistry(OrganizationInMinistry organizationInMinistry) {
        this.organizationInMinistries.add(organizationInMinistry);
        organizationInMinistry.setOrganization(this);
        return this;
    }

    public Organization removeOrganizationInMinistry(OrganizationInMinistry organizationInMinistry) {
        this.organizationInMinistries.remove(organizationInMinistry);
        organizationInMinistry.setOrganization(null);
        return this;
    }

    public Set<OrganizationInProject> getOrganizationInProjects() {
        return this.organizationInProjects;
    }

    public void setOrganizationInProjects(Set<OrganizationInProject> organizationInProjects) {
        if (this.organizationInProjects != null) {
            this.organizationInProjects.forEach(i -> i.setOrganization(null));
        }
        if (organizationInProjects != null) {
            organizationInProjects.forEach(i -> i.setOrganization(this));
        }
        this.organizationInProjects = organizationInProjects;
    }

    public Organization organizationInProjects(Set<OrganizationInProject> organizationInProjects) {
        this.setOrganizationInProjects(organizationInProjects);
        return this;
    }

    public Organization addOrganizationInProject(OrganizationInProject organizationInProject) {
        this.organizationInProjects.add(organizationInProject);
        organizationInProject.setOrganization(this);
        return this;
    }

    public Organization removeOrganizationInProject(OrganizationInProject organizationInProject) {
        this.organizationInProjects.remove(organizationInProject);
        organizationInProject.setOrganization(null);
        return this;
    }

    public Set<PersonInOrganization> getPersonInOrganizations() {
        return this.personInOrganizations;
    }

    public void setPersonInOrganizations(Set<PersonInOrganization> personInOrganizations) {
        if (this.personInOrganizations != null) {
            this.personInOrganizations.forEach(i -> i.setOrganization(null));
        }
        if (personInOrganizations != null) {
            personInOrganizations.forEach(i -> i.setOrganization(this));
        }
        this.personInOrganizations = personInOrganizations;
    }

    public Organization personInOrganizations(Set<PersonInOrganization> personInOrganizations) {
        this.setPersonInOrganizations(personInOrganizations);
        return this;
    }

    public Organization addPersonInOrganization(PersonInOrganization personInOrganization) {
        this.personInOrganizations.add(personInOrganization);
        personInOrganization.setOrganization(this);
        return this;
    }

    public Organization removePersonInOrganization(PersonInOrganization personInOrganization) {
        this.personInOrganizations.remove(personInOrganization);
        personInOrganization.setOrganization(null);
        return this;
    }

    public Countries getCountry() {
        return this.country;
    }

    public void setCountry(Countries countries) {
        this.country = countries;
    }

    public Organization country(Countries countries) {
        this.setCountry(countries);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        return id != null && id.equals(((Organization) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organization{" +
            "id=" + getId() +
            ", eunDbId=" + getEunDbId() +
            ", status='" + getStatus() + "'" +
            ", officialName='" + getOfficialName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", address='" + getAddress() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", maxAge=" + getMaxAge() +
            ", minAge=" + getMinAge() +
            ", area=" + getArea() +
            ", specialization='" + getSpecialization() + "'" +
            ", numberOfStudents='" + getNumberOfStudents() + "'" +
            ", hardwareUsed='" + getHardwareUsed() + "'" +
            ", ictUsed='" + getIctUsed() + "'" +
            ", website='" + getWebsite() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", fax='" + getFax() + "'" +
            ", email='" + getEmail() + "'" +
            ", organisationNumber='" + getOrganisationNumber() + "'" +
            "}";
    }
}
