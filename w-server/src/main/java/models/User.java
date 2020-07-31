package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by douglasdrouillard on 3/25/18.
 */

@Entity
@Table(name = "user_tbl")
public class User {
  @Id
  private Long id;

  private Boolean admin = false;

  private String subject;

  private String email;

  @Column(name = "preferred_username")
  private String preferredUsername;

  private String name;

  @Column(name = "given_name")
  private String givenName;

  @Column(name = "family_name")
  private String familyName;

  @ManyToMany
  @JoinTable(name = "user_favorite_tbl",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "merchant_id", referencedColumnName = "id"))
  private List<Merchant> userFavoriteMerchants;

  @ManyToMany(mappedBy = "merchantVipUsers")
  private List<Merchant> userVipMerchants;

  @OneToMany
  private List<Merchant> merchants;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getAdmin() {
    return admin;
  }

  public void setAdmin(Boolean admin) {
    this.admin = admin;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPreferredUsername() {
    return preferredUsername;
  }

  public void setPreferredUsername(String preferredUsername) {
    this.preferredUsername = preferredUsername;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGivenName() {
    return givenName;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public List<Merchant> getUserFavoriteMerchants() {
    return userFavoriteMerchants;
  }

  public void setUserFavoriteMerchants(List<Merchant> userFavoriteMerchants) {
    this.userFavoriteMerchants = userFavoriteMerchants;
  }

  public List<Merchant> getUserVipMerchants() {
    return userVipMerchants;
  }

  public void setUserVipMerchants(List<Merchant> userVipMerchants) {
    this.userVipMerchants = userVipMerchants;
  }

  public List<Merchant> getMerchants() {
    return merchants;
  }

  public void setMerchants(List<Merchant> merchants) {
    this.merchants = merchants;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", admin=" + admin + ", subject=" + subject + ", email="
        + email + ", preferredUsername=" + preferredUsername + ", name=" + name + ", givenName="
        + givenName + ", familyName=" + familyName + "]";
  }

}
