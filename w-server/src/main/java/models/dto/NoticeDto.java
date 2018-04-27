package models.dto;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.function.Consumer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import models.Notice;

@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = NoticeDto.Builder.class)
public class NoticeDto {
  private final String title;
  private final String text;
  private final String photo;
  private final String type;
  private final Long scopeId;
  private final Date createdAt;
  private final MerchantDto merchant;

  @JsonCreator
  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(Notice notice) {
    return new Builder(notice);
  }

  public static Builder builder(NoticeDto notice) {
    return new Builder(notice);
  }

  private NoticeDto(Builder builder) {
    this.title = builder.title;
    this.text = builder.text;
    this.photo = builder.photo;
    this.type = builder.type;
    this.scopeId = builder.scopeId;
    this.createdAt = builder.createdAt;
    this.merchant = builder.merchant;
  }

  public String getTitle() {
    return title;
  }

  public String getText() {
    return text;
  }

  public String getPhoto() {
    return photo;
  }

  public String getType() {
    return type;
  }

  @JsonIgnore
  public Long getScopeId() {
    return scopeId;
  }

  @JsonFormat(shape = JsonFormat.Shape.NUMBER)
  public Date getCreatedAt() {
    return createdAt;
  }

  public MerchantDto getMerchant() {
    return merchant;
  }

  @JsonPOJOBuilder
  public static class Builder {
    private String title;
    private String text;
    private String photo;
    private String type;
    private Long scopeId;
    private Date createdAt;
    private MerchantDto merchant;

    private Builder() {}

    private Builder(Notice notice) {
      requireNonNull(notice, "notice cannot be null");
      this.title = notice.getTitle();
      this.text = notice.getText();
      this.photo = notice.getPhoto();
      NoticeType type = NoticeType.get(notice.getTypeId());
      if (type != null) {
        this.type = type.getName();
      }
      this.scopeId = notice.getScopeId();
      this.createdAt = notice.getCreatedAt();
    }

    private Builder(NoticeDto notice) {
      requireNonNull(notice, "notice cannot be null");
      this.title = notice.title;
      this.text = notice.text;
      this.photo = notice.photo;
      this.type = notice.type;
      this.scopeId = notice.scopeId;
      this.createdAt = notice.createdAt;
      this.merchant = notice.merchant;
    }

    public Builder with(Consumer<Builder> consumer) {
      requireNonNull(consumer, "consumer cannot be null");
      consumer.accept(this);
      return this;
    }

    public Builder withTitle(String title) {
      requireNonNull(title, "title cannot be null");
      this.title = title;
      return this;
    }

    public Builder withText(String text) {
      requireNonNull(text, "text cannot be null");
      this.text = text;
      return this;
    }

    public Builder withPhoto(String photo) {
      this.photo = photo;
      return this;
    }

    public Builder withType(String type) {
      requireNonNull(type, "type cannot be null");
      this.type = type;
      return this;
    }

    @JsonIgnore
    public Builder withScopeId(long scopeId) {
      this.scopeId = scopeId;
      return this;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Builder withCreatedAt(Date createdAt) {
      requireNonNull(createdAt, "createdAt cannot be null");
      this.createdAt = createdAt;
      return this;
    }

    public Builder withMerchant(MerchantDto merchant) {
      requireNonNull(merchant, "merchant cannot be null");
      this.merchant = merchant;
      return this;
    }

    public NoticeDto build() {
      requireNonNull(title, "title cannot be null");
      requireNonNull(text, "text cannot be null");
      requireNonNull(type, "type cannot be null");
      requireNonNull(createdAt, "createdAt cannot be null");
      requireNonNull(merchant, "merchant cannot be null");
      return new NoticeDto(this);
    }
  }
}
