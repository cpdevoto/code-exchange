package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notice_tbl")
public class Notice {
  @Id
  private Long id;

  @ManyToOne
  @JoinColumn(name = "merchant_id")
  private Merchant merchant;

  @Column(name = "notice_scope_id")
  private Long scopeId;

  @Column(name = "notice_type_id")
  private Long typeId;

  private String title;

  private String text;

  private String photo;

  @Column(name = "created_at")
  private Date createdAt;

  @Column(name = "updated_at")
  private Date updatedAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public Long getTypeId() {
    return typeId;
  }

  public void setTypeId(Long typeId) {
    this.typeId = typeId;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Merchant getMerchant() {
    return merchant;
  }

  public void setMerchant(Merchant merchant) {
    this.merchant = merchant;
  }

  public Long getScopeId() {
    return scopeId;
  }

  public void setScopeId(Long scopeId) {
    this.scopeId = scopeId;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

}
