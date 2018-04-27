package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "merchant_tbl")
public class Merchant {
  @Id
  private Long id;

  private String name;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User users;

  @ManyToOne
  @JoinColumn(name = "address_id")
  private Address address;

  private Double latitude;

  private Double longitude;

  @Column(name = "short_description")
  private String shortDescription;

  @Column(name = "long_description")
  private String longDescription;

  private String logo;

  @Column(name = "cover_photo")
  private String coverPhoto;

  @Column(name = "phone_number")
  private String phoneNumber;

  private String email;

  @Column(name = "messaging_enabled")
  private Boolean messaging;

  @Column(name = "created_at")
  private Boolean createdAt;

  @Column(name = "updated_at")
  private Boolean updatedAt;

  @ManyToMany(mappedBy = "userFavoriteMerchants")
  private List<User> merchantFavoriteUsers;

  @ManyToMany
  @JoinTable(name = "merchant_vip_tbl",
      joinColumns = @JoinColumn(name = "merchant_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
  private List<User> merchantVipUsers;

  @ManyToMany(mappedBy = "tagMerchants")
  private List<Tag> merchantTags;

  @OneToMany
  private List<Notice> notices;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public Boolean getMessaging() {
    return messaging;
  }

  public void setMessaging(Boolean messaging) {
    this.messaging = messaging;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public User getUsers() {
    return users;
  }

  public void setUsers(User users) {
    this.users = users;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public String getLongDescription() {
    return longDescription;
  }

  public void setLongDescription(String longDescription) {
    this.longDescription = longDescription;
  }

  public String getCoverPhoto() {
    return coverPhoto;
  }

  public void setCoverPhoto(String coverPhoto) {
    this.coverPhoto = coverPhoto;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Boolean getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Boolean createdAt) {
    this.createdAt = createdAt;
  }

  public Boolean getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Boolean updatedAt) {
    this.updatedAt = updatedAt;
  }

  public List<User> getMerchantFavoriteUsers() {
    return merchantFavoriteUsers;
  }

  public void setMerchantFavoriteUsers(List<User> merchantFavoriteUsers) {
    this.merchantFavoriteUsers = merchantFavoriteUsers;
  }

  public List<User> getMerchantVipUsers() {
    return merchantVipUsers;
  }

  public void setMerchantVipUsers(List<User> merchantVipUsers) {
    this.merchantVipUsers = merchantVipUsers;
  }

  public List<Tag> getMerchantTags() {
    return merchantTags;
  }

  public void setMerchantTags(List<Tag> merchantTags) {
    this.merchantTags = merchantTags;
  }

  public List<Notice> getNotices() {
    return notices;
  }

  public void setNotices(List<Notice> notices) {
    this.notices = notices;
  }


}
