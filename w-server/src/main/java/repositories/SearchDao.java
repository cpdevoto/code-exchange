package repositories;

import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.google.common.collect.Lists;

import inputs.SearchInputs;
import utils.SearchUtils;

public class SearchDao {


  public List<String> search(SearchInputs inputs) {

    int limit = Integer.parseInt(inputs.getLimit());

    String search = SearchUtils.toTextSearchQuery(inputs.getSearch());

    StringBuilder sql = new StringBuilder(
        "SELECT m.name, ts_rank_cd(to_tsvector(m.name), q1) AS rank, lower(m.name) AS lower_name\n"
            + "FROM merchant_tbl m, to_tsquery('english', :search) q1 \n"
            + "WHERE to_tsvector(m.name) @@ q1\n"
            + "UNION\n"
            + "SELECT t.name, ts_rank_cd(to_tsvector(t.name), q2) AS rank, lower(t.name) AS lower_name\n"
            + "FROM tag_tbl t, to_tsquery('english', :search) q2\n"
            + "WHERE EXISTS(SELECT NULL FROM merchant_tag_tbl mt WHERE mt.tag_id = t.id) \n"
            + "AND to_tsvector(t.name) @@ q2\n"
            + "ORDER BY rank DESC, lower_name, name\n"
            + "LIMIT :limit");

    SqlQuery query = Ebean.createSqlQuery(sql.toString())
        .setParameter("search", search)
        .setParameter("limit", limit);

    List<SqlRow> rows = query.findList();

    List<String> results = Lists.newArrayList();
    rows.forEach(row -> {
      String result = row.getString("name");
      results.add(result);
    });
    return results;
  }


}
