package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;
import org.eun.back.domain.enumeration.OrgStatus;

/**
 * A DTO for the {@link org.eun.back.domain.Organization} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationDTO implements Serializable {

    private Long id;

    private Long eunDbId;

    @NotNull
    private OrgStatus status;

    @NotNull
    private String officialName;

    private String description;

    private String type;

    private String address;

    private Integer latitude;

    private Integer longitude;

    private Integer maxAge;

    private Integer minAge;

    private Integer area;

    private String specialization;

    private String numberOfStudents;

    private Boolean hardwareUsed;

    private Boolean ictUsed;

    private String website;

    @Lob
    private byte[] image;

    private String imageContentType;
    private String telephone;

    private String fax;

    private String email;

    private String organisationNumber;

    private CountriesDTO country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEunDbId() {
        return eunDbId;
    }

    public void setEunDbId(Long eunDbId) {
        this.eunDbId = eunDbId;
    }

    public OrgStatus getStatus() {
        return status;
    }

    public void setStatus(OrgStatus status) {
        this.status = status;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(String numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public Boolean getHardwareUsed() {
        return hardwareUsed;
    }

    public void setHardwareUsed(Boolean hardwareUsed) {
        this.hardwareUsed = hardwareUsed;
    }

    public Boolean getIctUsed() {
        return ictUsed;
    }

    public void setIctUsed(Boolean ictUsed) {
        this.ictUsed = ictUsed;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganisationNumber() {
        return organisationNumber;
    }

    public void setOrganisationNumber(String organisationNumber) {
        this.organisationNumber = organisationNumber;
    }

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
        if (!(o instanceof OrganizationDTO)) {
            return false;
        }

        OrganizationDTO organizationDTO = (OrganizationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organizationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationDTO{" +
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
            ", telephone='" + getTelephone() + "'" +
            ", fax='" + getFax() + "'" +
            ", email='" + getEmail() + "'" +
            ", organisationNumber='" + getOrganisationNumber() + "'" +
            ", country=" + getCountry() +
            "}";
    }
}
