package org.eun.back.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import org.eun.back.domain.enumeration.GdprStatus;

/**
 * A DTO for the {@link org.eun.back.domain.Person} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonIndicatorDTO implements Serializable {

    private Long id;

    private String firstname;

    private String lastname;

    private String mobile;
    private String socialNetworkContacts;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSocialNetworkContacts() {
        return socialNetworkContacts;
    }

    public void setSocialNetworkContacts(String socialNetworkContacts) {
        this.socialNetworkContacts = socialNetworkContacts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonIndicatorDTO)) {
            return false;
        }

        PersonIndicatorDTO personDTO = (PersonIndicatorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "PersonIndicatorDTO{" +
            "id=" +
            id +
            ", firstname='" +
            firstname +
            '\'' +
            ", lastname='" +
            lastname +
            '\'' +
            ", mobile='" +
            mobile +
            '\'' +
            ", socialNetworkContacts='" +
            socialNetworkContacts +
            '\'' +
            ", status='" +
            status +
            '\'' +
            '}'
        );
    }
}
