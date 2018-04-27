package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tag_tbl")
public class Tag {

  @Id
  private Long id;

  @Column(name = "tag_group_id")
  private Long tagGroupId;

  private String name;

  @ManyToMany
  @JoinTable(name = "merchant_tag_tbl",
      joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "merchant_id", referencedColumnName = "id"))
  List<Merchant> tagMerchants;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getTagGroupId() {
    return tagGroupId;
  }

  public void setTagGroupId(Long tagGroupId) {
    this.tagGroupId = tagGroupId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Merchant> getTagMerchants() {
    return tagMerchants;
  }

  public void setTagMerchants(List<Merchant> tagMerchants) {
    this.tagMerchants = tagMerchants;
  }

}
