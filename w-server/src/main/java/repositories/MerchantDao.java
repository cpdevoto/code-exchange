package repositories;

import java.sql.Array;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.google.common.collect.Lists;

import inputs.FetchMerchantInputs;
import inputs.FetchMerchantsInputs;
import inputs.FetchUserFavoriteMerchantsInputs;
import models.dto.MerchantDetailsDto;
import utils.GpsCoords;
import utils.SearchUtils;

public class MerchantDao {


  public List<MerchantDetailsDto> fetch_merchants(FetchMerchantsInputs inputs) {

    GpsCoords gpsCoords = new GpsCoords(inputs.getLat(), inputs.getLng());

    int limit = Integer.parseInt(inputs.getLimit());
    double radius = Double.parseDouble(inputs.getRadius());
    String search = SearchUtils.toTextSearchQuery(inputs.getSearch());

    StringBuilder sql = new StringBuilder(
        "SELECT m.id, m.name, a.address1, a.address2, a.city, a.\"state\", a.zip, m.latitude, m.longitude, "
            + "m.short_description, m.long_description, \n"
            + "m.logo, m.cover_photo, m.phone_number, m.messaging_enabled,\n"
            + "(SELECT ARRAY(SELECT t.name FROM tag_tbl t \n"
            + "JOIN merchant_tag_tbl mt ON mt.tag_id = t.id AND  \n"
            + "mt.merchant_id = m.id))AS tags,\n"
            + "(SELECT count(*) FROM user_favorite_tbl uf "
            + "WHERE uf.merchant_id = m.id) AS insiders,\n"
            + "earth_distance(ll_to_earth(:latitude, :longitude), "
            + "ll_to_earth(m.latitude, m.longitude)) AS distance_from  \n"
            + "FROM merchant_tbl m\n"
            + "CROSS JOIN to_tsquery('english', :search) q1\n"
            + "JOIN address_tbl a ON m.address_id = a.id\n"
            + "WHERE earth_box(ll_to_earth(:latitude, :longitude), :radius) @> "
            + "ll_to_earth(m.latitude, m.longitude)\n"
            + "AND earth_distance(ll_to_earth(:latitude, :longitude), "
            + "ll_to_earth(m.latitude, m.longitude)) <= :radius\n"
            + "AND to_tsvector(m.name) @@ q1 OR (m.id IN (SELECT mt.merchant_id "
            + "FROM merchant_tag_tbl mt JOIN tag_tbl t ON mt.tag_id = t.id "
            + "WHERE to_tsvector(t.name) @@ q1))\n"
            + "ORDER BY distance_from ASC, name ASC\n"
            + "LIMIT :limit");

    inputs.getSkip().ifPresent(skip -> sql.append(" OFFSET :offset"));

    SqlQuery query = Ebean.createSqlQuery(sql.toString())
        .setParameter("latitude", gpsCoords.getLatitude())
        .setParameter("longitude", gpsCoords.getLongitude())
        .setParameter("radius", radius)
        .setParameter("search", search)
        .setParameter("limit", limit);

    inputs.getSkip().ifPresent(skip -> query.setParameter("offset", Integer.parseInt(skip)));

    List<SqlRow> rows = query.findList();

    List<MerchantDetailsDto> merchants = Lists.newArrayList();
    rows.forEach(row -> {
      MerchantDetailsDto merchant = mapMerchantDetails(row, false);
      merchants.add(merchant);
    });
    return merchants;
  }


  public Optional<MerchantDetailsDto> fetch_merchant(long userId, long id,
      FetchMerchantInputs inputs) {

    GpsCoords gpsCoords = new GpsCoords(inputs.getLat(), inputs.getLng());

    StringBuilder sql = new StringBuilder(
        "SELECT m.id, m.name, a.address1, a.address2, a.city, a.\"state\", a.zip, m.latitude, "
            + "m.longitude, m.short_description, m.long_description, \n"
            + "m.logo, m.cover_photo, m.phone_number, m.messaging_enabled,\n"
            + "(SELECT ARRAY(SELECT t.name FROM tag_tbl t \n"
            + "JOIN merchant_tag_tbl mt ON mt.tag_id = t.id AND  \n"
            + "mt.merchant_id = m.id)) AS tags,\n"
            + "(SELECT count(*) FROM user_favorite_tbl uf WHERE uf.merchant_id = m.id) AS insiders,\n"
            + "(SELECT EXISTS (SELECT TRUE FROM merchant_vip_tbl mv "
            + "WHERE mv.merchant_id = m.id AND mv.user_id = :userId)) AS is_vip,\n"
            + "earth_distance(ll_to_earth(:latitude, :longitude), "
            + "ll_to_earth(m.latitude, m.longitude)) AS distance_from  \n"
            + "FROM merchant_tbl m\n"
            + "JOIN address_tbl a ON m.address_id = a.id\n"
            + "WHERE m.id = :id");

    SqlRow row = Ebean.createSqlQuery(sql.toString())
        .setParameter("latitude", gpsCoords.getLatitude())
        .setParameter("longitude", gpsCoords.getLongitude())
        .setParameter("id", id)
        .setParameter("userId", userId)
        .findUnique();

    if (row == null) {
      return Optional.empty();
    }
    MerchantDetailsDto merchant = mapMerchantDetails(row, true);
    return Optional.of(merchant);
  }


  public List<MerchantDetailsDto> fetch_user_favorite_merchants(long userId,
      FetchUserFavoriteMerchantsInputs inputs) {

    int limit = Integer.parseInt(inputs.getLimit());

    GpsCoords gpsCoords = new GpsCoords(inputs.getLat(), inputs.getLng());

    StringBuilder sql = new StringBuilder(
        "SELECT m.id, m.name, a.address1, a.address2, a.city, a.\"state\", a.zip, m.latitude, "
            + "m.longitude, m.short_description, m.long_description, \n"
            + "m.logo, m.cover_photo, m.phone_number, m.messaging_enabled,\n"
            + "(SELECT ARRAY(SELECT t.name FROM tag_tbl t \n"
            + "JOIN merchant_tag_tbl mt ON mt.tag_id = t.id AND  \n"
            + "mt.merchant_id = m.id)) AS tags,\n"
            + "(SELECT count(*) FROM user_favorite_tbl uf WHERE uf.merchant_id = m.id) AS insiders,\n"
            + "(SELECT EXISTS (SELECT TRUE FROM merchant_vip_tbl mv "
            + "WHERE mv.merchant_id = m.id AND mv.user_id = :userId)) AS is_vip,\n"
            + "earth_distance(ll_to_earth(:latitude, :longitude), "
            + "ll_to_earth(m.latitude, m.longitude)) AS distance_from  \n"
            + "FROM merchant_tbl m\n"
            + "JOIN address_tbl a ON m.address_id = a.id\n"
            + "WHERE m.id IN (SELECT merchant_id FROM user_favorite_tbl WHERE user_id = :userId)\n"
            + "ORDER BY distance_from ASC, name ASC\n"
            + "LIMIT :limit");

    inputs.getSkip().ifPresent(skip -> sql.append(" OFFSET :offset"));

    SqlQuery query = Ebean.createSqlQuery(sql.toString())
        .setParameter("latitude", gpsCoords.getLatitude())
        .setParameter("longitude", gpsCoords.getLongitude())
        .setParameter("userId", userId)
        .setParameter("limit", limit);

    inputs.getSkip().ifPresent(skip -> query.setParameter("offset", Integer.parseInt(skip)));

    List<SqlRow> rows = query.findList();

    List<MerchantDetailsDto> merchants = Lists.newArrayList();
    rows.forEach(row -> {
      MerchantDetailsDto merchant = mapMerchantDetails(row, true);
      merchants.add(merchant);
    });
    return merchants;
  }

  private MerchantDetailsDto mapMerchantDetails(SqlRow row, boolean mapVip) {
    Array rawTagArray = (Array) row.get("tags");
    String[] tagArray = toStringArray(rawTagArray);
    List<String> tags = Arrays.stream(tagArray).collect(Collectors.toList());
    String distanceFrom = String.format("%,.1f", row.getDouble("distance_from"));
    String latitude = String.format("%,.6f", row.getDouble("latitude"));
    String longitude = String.format("%,.6f", row.getDouble("longitude"));
    MerchantDetailsDto merchant = MerchantDetailsDto.builder()
        .withId(row.getLong("id"))
        .withName(row.getString("name"))
        .withAddress1(row.getString("address1"))
        .withAddress2(row.getString("address2"))
        .withCity(row.getString("city"))
        .withState(row.getString("state"))
        .withZip(row.getString("zip"))
        .withLogo(row.getString("logo"))
        .withCoverPhoto(row.getString("cover_photo"))
        .withShortDescription(row.getString("short_description"))
        .withLongDescription(row.getString("long_description"))
        .withTags(tags)
        .withPhoneNumber(row.getString("phone_number"))
        .withMessaging(row.getBoolean("messaging_enabled"))
        .withInsiders(row.getInteger("insiders"))
        .withDistanceFrom(distanceFrom)
        .withLatitude(latitude)
        .withLongitude(longitude)
        .with(b -> {
          if (mapVip) {
            b.withIsVIP(row.getBoolean("is_vip"));
          }
        })

        .build();
    return merchant;
  }

  private String[] toStringArray(Array rawTagArray) {
    try {
      return (String[]) rawTagArray.getArray();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
