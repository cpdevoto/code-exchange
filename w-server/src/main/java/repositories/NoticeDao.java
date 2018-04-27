package repositories;

import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.google.common.collect.Lists;

import inputs.FetchMerchantNoticesInputs;
import inputs.FetchUserNoticesInputs;
import models.dto.MerchantDto;
import models.dto.NoticeDto;
import models.dto.NoticeScope;
import models.dto.NoticeType;
import utils.GpsCoords;

public class NoticeDao {


  public List<NoticeDto> fetch_user_notices(long userId, FetchUserNoticesInputs inputs) {

    int limit = Integer.parseInt(inputs.getLimit());

    GpsCoords gpsCoords = new GpsCoords(inputs.getLat(), inputs.getLng());

    StringBuilder sql = new StringBuilder(
        "SELECT n.title, n.\"text\", n.photo, n.notice_type_id, n.notice_scope_id, n.created_at, m.id AS merchant_id, \n"
            + "m.name AS merchant_name, m.logo AS merchant_logo,\n"
            + "m.phone_number AS merchant_phone_number, m.messaging_enabled AS merchant_messaging_enabled, \n"
            + "earth_distance(ll_to_earth(:latitude, :longitude), ll_to_earth(m.latitude, m.longitude)) \n"
            + "AS merchant_distance_from, m.latitude AS merchant_latitude, m.longitude AS merchant_longitude\n"
            + "FROM notice_tbl n JOIN merchant_tbl m ON n.merchant_id = m.id\n"
            + "WHERE m.user_id = :userId  OR (m.id IN (SELECT uf.merchant_id FROM user_favorite_tbl uf WHERE uf.user_id = :userId) AND\n"
            + "(n.notice_scope_id = :allUsersScope OR m.id IN (SELECT mv.merchant_id FROM merchant_vip_tbl mv \n"
            + "WHERE mv.user_id = :userId AND mv.merchant_id = m.id)))\n"
            + "ORDER BY created_at DESC\n"
            + "LIMIT :limit");

    inputs.getSkip().ifPresent(skip -> sql.append(" OFFSET :offset"));


    SqlQuery query = Ebean.createSqlQuery(sql.toString())
        .setParameter("latitude", gpsCoords.getLatitude())
        .setParameter("longitude", gpsCoords.getLongitude())
        .setParameter("userId", userId)
        .setParameter("allUsersScope", NoticeScope.ALL_USERS.getId())
        .setParameter("limit", limit);

    inputs.getSkip().ifPresent(skip -> query.setParameter("offset", Integer.parseInt(skip)));

    List<SqlRow> rows = query.findList();

    List<NoticeDto> notices = Lists.newArrayList();
    rows.forEach(row -> {
      NoticeDto notice = mapNotice(row, true);
      notices.add(notice);
    });
    return notices;
  }

  public List<NoticeDto> fetch_merchant_notices(long userId, long id,
      FetchMerchantNoticesInputs inputs) {

    int limit = Integer.parseInt(inputs.getLimit());

    GpsCoords gpsCoords = new GpsCoords(inputs.getLat(), inputs.getLng());

    StringBuilder sql = new StringBuilder(
        "SELECT n.title, n.\"text\", n.photo, n.notice_type_id, n.notice_scope_id, n.created_at, m.id AS merchant_id, \n"
            + "m.name AS merchant_name, m.logo AS merchant_logo,\n"
            + "m.phone_number AS merchant_phone_number, m.messaging_enabled AS merchant_messaging_enabled, \n"
            + "earth_distance(ll_to_earth(:latitude, :longitude), ll_to_earth(m.latitude, m.longitude)) \n"
            + "AS merchant_distance_from, m.latitude AS merchant_latitude, m.longitude AS merchant_longitude\n"
            + "FROM notice_tbl n JOIN merchant_tbl m ON n.merchant_id = m.id\n"
            + "WHERE m.id  = :id AND (m.user_id = :userId  OR (\n"
            + "n.notice_scope_id = :allUsersScope OR m.id IN (SELECT mv.merchant_id FROM merchant_vip_tbl mv \n"
            + "WHERE mv.user_id = :userId AND mv.merchant_id = m.id)))\n"
            + "ORDER BY created_at DESC\n"
            + "LIMIT :limit");

    inputs.getSkip().ifPresent(skip -> sql.append(" OFFSET :offset"));


    SqlQuery query = Ebean.createSqlQuery(sql.toString())
        .setParameter("latitude", gpsCoords.getLatitude())
        .setParameter("longitude", gpsCoords.getLongitude())
        .setParameter("userId", userId)
        .setParameter("id", id)
        .setParameter("allUsersScope", NoticeScope.ALL_USERS.getId())
        .setParameter("limit", limit);

    inputs.getSkip().ifPresent(skip -> query.setParameter("offset", Integer.parseInt(skip)));

    List<SqlRow> rows = query.findList();

    List<NoticeDto> notices = Lists.newArrayList();
    rows.forEach(row -> {
      NoticeDto notice = mapNotice(row, true);
      notices.add(notice);
    });
    return notices;
  }

  private NoticeDto mapNotice(SqlRow row, boolean b) {
    String distanceFrom = String.format("%,.1f", row.getDouble("merchant_distance_from"));
    String latitude = String.format("%,.6f", row.getDouble("merchant_latitude"));
    String longitude = String.format("%,.6f", row.getDouble("merchant_longitude"));


    MerchantDto merchant = MerchantDto.builder()
        .withId(row.getLong("merchant_id"))
        .withName(row.getString("merchant_name"))
        .withLogo(row.getString("merchant_logo"))
        .withPhoneNumber(row.getString("merchant_phone_number"))
        .withMessaging(row.getBoolean("merchant_messaging_enabled"))
        .withDistanceFrom(distanceFrom)
        .withLatitude(latitude)
        .withLongitude(longitude)
        .build();

    NoticeDto notice = NoticeDto.builder()
        .withTitle(row.getString("title"))
        .withText(row.getString("text"))
        .withPhoto(row.getString("photo"))
        .withType(NoticeType.get(row.getLong("notice_type_id")).getName().toLowerCase())
        .withScopeId(row.getLong("notice_scope_id"))
        .withCreatedAt(row.getDate("created_at"))
        .withMerchant(merchant)
        .build();

    return notice;
  }
}
