package com.jiotrasportinc.tms.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Driver.
 */
@Entity
@Table(name = "driver")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "driver")
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "company")
    private String company;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "licence_number")
    private Long licenceNumber;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "company_joined_on")
    private LocalDate companyJoinedOn;

    @Column(name = "company_left_on")
    private LocalDate companyLeftOn;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Lob
    @Column(name = "licence_image")
    private byte[] licenceImage;

    @Column(name = "licence_image_content_type")
    private String licenceImageContentType;

    @Column(name = "remarks")
    private String remarks;

    @OneToMany(mappedBy = "driver")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LoadOrder> loadOrders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public Driver company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFirstName() {
        return firstName;
    }

    public Driver firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Driver lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Driver email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public Driver phoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getLicenceNumber() {
        return licenceNumber;
    }

    public Driver licenceNumber(Long licenceNumber) {
        this.licenceNumber = licenceNumber;
        return this;
    }

    public void setLicenceNumber(Long licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Driver dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDate getCompanyJoinedOn() {
        return companyJoinedOn;
    }

    public Driver companyJoinedOn(LocalDate companyJoinedOn) {
        this.companyJoinedOn = companyJoinedOn;
        return this;
    }

    public void setCompanyJoinedOn(LocalDate companyJoinedOn) {
        this.companyJoinedOn = companyJoinedOn;
    }

    public LocalDate getCompanyLeftOn() {
        return companyLeftOn;
    }

    public Driver companyLeftOn(LocalDate companyLeftOn) {
        this.companyLeftOn = companyLeftOn;
        return this;
    }

    public void setCompanyLeftOn(LocalDate companyLeftOn) {
        this.companyLeftOn = companyLeftOn;
    }

    public byte[] getImage() {
        return image;
    }

    public Driver image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Driver imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getLicenceImage() {
        return licenceImage;
    }

    public Driver licenceImage(byte[] licenceImage) {
        this.licenceImage = licenceImage;
        return this;
    }

    public void setLicenceImage(byte[] licenceImage) {
        this.licenceImage = licenceImage;
    }

    public String getLicenceImageContentType() {
        return licenceImageContentType;
    }

    public Driver licenceImageContentType(String licenceImageContentType) {
        this.licenceImageContentType = licenceImageContentType;
        return this;
    }

    public void setLicenceImageContentType(String licenceImageContentType) {
        this.licenceImageContentType = licenceImageContentType;
    }

    public String getRemarks() {
        return remarks;
    }

    public Driver remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Set<LoadOrder> getLoadOrders() {
        return loadOrders;
    }

    public Driver loadOrders(Set<LoadOrder> loadOrders) {
        this.loadOrders = loadOrders;
        return this;
    }

    public Driver addLoadOrder(LoadOrder loadOrder) {
        this.loadOrders.add(loadOrder);
        loadOrder.setDriver(this);
        return this;
    }

    public Driver removeLoadOrder(LoadOrder loadOrder) {
        this.loadOrders.remove(loadOrder);
        loadOrder.setDriver(null);
        return this;
    }

    public void setLoadOrders(Set<LoadOrder> loadOrders) {
        this.loadOrders = loadOrders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Driver)) {
            return false;
        }
        return id != null && id.equals(((Driver) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Driver{" +
            "id=" + getId() +
            ", company='" + getCompany() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber=" + getPhoneNumber() +
            ", licenceNumber=" + getLicenceNumber() +
            ", dob='" + getDob() + "'" +
            ", companyJoinedOn='" + getCompanyJoinedOn() + "'" +
            ", companyLeftOn='" + getCompanyLeftOn() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", licenceImage='" + getLicenceImage() + "'" +
            ", licenceImageContentType='" + getLicenceImageContentType() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
