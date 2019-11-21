package com.jiotrasportinc.tms.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import com.jiotrasportinc.tms.domain.enumeration.StatusEnum;

import com.jiotrasportinc.tms.domain.enumeration.COVEREDBY;

import com.jiotrasportinc.tms.domain.enumeration.LoadType;

import com.jiotrasportinc.tms.domain.enumeration.SizeEnum;

/**
 * A LoadOrder.
 */
@Entity
@Table(name = "load_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "loadorder")
public class LoadOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "shipment_number")
    private String shipmentNumber;

    @Column(name = "bol")
    private String bol;

    @Column(name = "pickup")
    private LocalDate pickup;

    @Column(name = "jhi_drop")
    private LocalDate drop;

    @Column(name = "pickup_location")
    private String pickupLocation;

    @Column(name = "drop_location")
    private String dropLocation;

    @Column(name = "current_location")
    private String currentLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @Column(name = "detention")
    private Long detention;

    @Column(name = "chasis_in_time")
    private Instant chasisInTime;

    @Lob
    @Column(name = "pod")
    private byte[] pod;

    @Column(name = "pod_content_type")
    private String podContentType;

    @Column(name = "hazmat")
    private Boolean hazmat;

    @Column(name = "recieved_by")
    private String recievedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "covered_by")
    private COVEREDBY coveredBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "load_type")
    private LoadType loadType;

    @Enumerated(EnumType.STRING)
    @Column(name = "container_size")
    private SizeEnum containerSize;

    @Column(name = "numbers_of_container")
    private Integer numbersOfContainer;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    @JsonIgnoreProperties("loadOrders")
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties("loadOrders")
    private Driver driver;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public LoadOrder orderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDescription() {
        return description;
    }

    public LoadOrder description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public LoadOrder shipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
        return this;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public String getBol() {
        return bol;
    }

    public LoadOrder bol(String bol) {
        this.bol = bol;
        return this;
    }

    public void setBol(String bol) {
        this.bol = bol;
    }

    public LocalDate getPickup() {
        return pickup;
    }

    public LoadOrder pickup(LocalDate pickup) {
        this.pickup = pickup;
        return this;
    }

    public void setPickup(LocalDate pickup) {
        this.pickup = pickup;
    }

    public LocalDate getDrop() {
        return drop;
    }

    public LoadOrder drop(LocalDate drop) {
        this.drop = drop;
        return this;
    }

    public void setDrop(LocalDate drop) {
        this.drop = drop;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public LoadOrder pickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
        return this;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public LoadOrder dropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
        return this;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public LoadOrder currentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
        return this;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public LoadOrder status(StatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Long getDetention() {
        return detention;
    }

    public LoadOrder detention(Long detention) {
        this.detention = detention;
        return this;
    }

    public void setDetention(Long detention) {
        this.detention = detention;
    }

    public Instant getChasisInTime() {
        return chasisInTime;
    }

    public LoadOrder chasisInTime(Instant chasisInTime) {
        this.chasisInTime = chasisInTime;
        return this;
    }

    public void setChasisInTime(Instant chasisInTime) {
        this.chasisInTime = chasisInTime;
    }

    public byte[] getPod() {
        return pod;
    }

    public LoadOrder pod(byte[] pod) {
        this.pod = pod;
        return this;
    }

    public void setPod(byte[] pod) {
        this.pod = pod;
    }

    public String getPodContentType() {
        return podContentType;
    }

    public LoadOrder podContentType(String podContentType) {
        this.podContentType = podContentType;
        return this;
    }

    public void setPodContentType(String podContentType) {
        this.podContentType = podContentType;
    }

    public Boolean isHazmat() {
        return hazmat;
    }

    public LoadOrder hazmat(Boolean hazmat) {
        this.hazmat = hazmat;
        return this;
    }

    public void setHazmat(Boolean hazmat) {
        this.hazmat = hazmat;
    }

    public String getRecievedBy() {
        return recievedBy;
    }

    public LoadOrder recievedBy(String recievedBy) {
        this.recievedBy = recievedBy;
        return this;
    }

    public void setRecievedBy(String recievedBy) {
        this.recievedBy = recievedBy;
    }

    public COVEREDBY getCoveredBy() {
        return coveredBy;
    }

    public LoadOrder coveredBy(COVEREDBY coveredBy) {
        this.coveredBy = coveredBy;
        return this;
    }

    public void setCoveredBy(COVEREDBY coveredBy) {
        this.coveredBy = coveredBy;
    }

    public LoadType getLoadType() {
        return loadType;
    }

    public LoadOrder loadType(LoadType loadType) {
        this.loadType = loadType;
        return this;
    }

    public void setLoadType(LoadType loadType) {
        this.loadType = loadType;
    }

    public SizeEnum getContainerSize() {
        return containerSize;
    }

    public LoadOrder containerSize(SizeEnum containerSize) {
        this.containerSize = containerSize;
        return this;
    }

    public void setContainerSize(SizeEnum containerSize) {
        this.containerSize = containerSize;
    }

    public Integer getNumbersOfContainer() {
        return numbersOfContainer;
    }

    public LoadOrder numbersOfContainer(Integer numbersOfContainer) {
        this.numbersOfContainer = numbersOfContainer;
        return this;
    }

    public void setNumbersOfContainer(Integer numbersOfContainer) {
        this.numbersOfContainer = numbersOfContainer;
    }

    public String getComments() {
        return comments;
    }

    public LoadOrder comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LoadOrder customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public LoadOrder driver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoadOrder)) {
            return false;
        }
        return id != null && id.equals(((LoadOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LoadOrder{" +
            "id=" + getId() +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", description='" + getDescription() + "'" +
            ", shipmentNumber='" + getShipmentNumber() + "'" +
            ", bol='" + getBol() + "'" +
            ", pickup='" + getPickup() + "'" +
            ", drop='" + getDrop() + "'" +
            ", pickupLocation='" + getPickupLocation() + "'" +
            ", dropLocation='" + getDropLocation() + "'" +
            ", currentLocation='" + getCurrentLocation() + "'" +
            ", status='" + getStatus() + "'" +
            ", detention=" + getDetention() +
            ", chasisInTime='" + getChasisInTime() + "'" +
            ", pod='" + getPod() + "'" +
            ", podContentType='" + getPodContentType() + "'" +
            ", hazmat='" + isHazmat() + "'" +
            ", recievedBy='" + getRecievedBy() + "'" +
            ", coveredBy='" + getCoveredBy() + "'" +
            ", loadType='" + getLoadType() + "'" +
            ", containerSize='" + getContainerSize() + "'" +
            ", numbersOfContainer=" + getNumbersOfContainer() +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
