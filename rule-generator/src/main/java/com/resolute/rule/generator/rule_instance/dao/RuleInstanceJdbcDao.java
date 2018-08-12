package com.resolute.rule.generator.rule_instance.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resolute.common.util.dao.DaoUtils;
import com.resolute.common.util.dao.DataAccessException;
import com.resolute.common.util.dao.JdbcStatementFactory;
import com.resolute.rule.generator.common.model.EntityIndex;
import com.resolute.rule.generator.common.model.RuleInstances;
import com.resolute.rule.generator.rule_instance.model.RuleCandidateForGeneration;
import com.resolute.rule.generator.rule_instance.model.RuleTemplate;
import com.resolute.rule.generator.rule_instance.model.RuleTemplateInputPoint;
import com.resolute.rule.generator.rule_instance.model.Tag;
import com.resolutebi.node.traversal.NodeTraversalExpression;
import com.resolutebi.tag.query.TagQueryExpression;

import static java.util.Objects.requireNonNull;

class RuleInstanceJdbcDao implements RuleInstanceDao {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private final JdbcStatementFactory statementFactory;
  private final GetHelper getHelper;

  static RuleInstanceJdbcDao create(DataSource dataSource) {
    requireNonNull(dataSource, "dataSource cannot be null");
    return new RuleInstanceJdbcDao(dataSource);

  }

  private RuleInstanceJdbcDao(DataSource dataSource) {
    this.statementFactory = JdbcStatementFactory.getInstance(dataSource);
    this.getHelper = new GetHelper();
  }


  @Override
  public RuleInstances get(int customerId, int ruleTemplateId) {
    return statementFactory.newStatement()
        .withErrorMessage("A problem occurred while attempting to retrieve rule instances")
        .executeMultipleStatementsAndReturnValue(conn -> {
          List<EntityIndex> equipment = getHelper.getEquipment(conn, customerId, ruleTemplateId);
          List<EntityIndex> candidates = getHelper.getCandidates(conn, customerId, ruleTemplateId);
          return RuleInstances.builder()
              .withEquipment(equipment)
              .withCandidates(candidates)
              .build();
        });

  }

  @Override
  public void generate(int customerId, int ruleTemplateId, List<Integer> equipmentIds) {
    DateFormat formatter = DateFormat.getDateTimeInstance(
        DateFormat.LONG,
        DateFormat.LONG);
    try {
      System.out.println("GENERATING " + equipmentIds.size() + " RULE INSTANCE(S): "
          + formatter.format(new Date()));
      statementFactory.newStatement()
          .withErrorMessage("A problem occurred while attempting to generate rule instances")
          .executeTransaction(conn -> {
            RuleTemplate template = getHelper.getRuleTemplate(conn, ruleTemplateId);

            List<RuleCandidateForGeneration> candidates =
                getHelper.getCandidatesForGeneration(conn, customerId, template);

            Map<Integer, RuleCandidateForGeneration> candidateMap = candidates.stream()
                .collect(Collectors.toMap(RuleCandidateForGeneration::getId, Function.identity()));

            String templateJson;
            try {
              templateJson = MAPPER.writeValueAsString(template);
            } catch (JsonProcessingException e) {
              throw new DataAccessException(e);
            }
            try (PreparedStatement stmt =
                conn.prepareStatement("SELECT insert_rule_instance(?, ?, ?)")) {
              for (int equipmentId : equipmentIds) {
                RuleCandidateForGeneration candidate = candidateMap.get(equipmentId);
                if (candidate == null) {
                  throw new DataAccessException("Equipment with id " + equipmentId +
                      " is not a valid candidate for the application of rule template " +
                      template.getId() + " for customer " + customerId);
                }
                String candidateJson;
                try {
                  candidateJson = MAPPER.writeValueAsString(candidate);
                } catch (JsonProcessingException e) {
                  throw new DataAccessException(e);
                }
                stmt.setInt(1, customerId);
                stmt.setString(2, templateJson);
                stmt.setString(3, candidateJson);
                stmt.addBatch();
              }
              stmt.executeBatch();
            }
          });
    } finally {
      System.out.println("FINISHED GENERATING " + equipmentIds.size() + " RULE INSTANCE(S): "
          + formatter.format(new Date()));
    }
  }

  private String listToCommaSeparatedString(List<Integer> list) {
    StringBuilder buf = new StringBuilder();
    boolean firstLoop = true;
    for (int id : list) {
      if (firstLoop) {
        firstLoop = false;
      } else {
        buf.append(", ");
      }
      buf.append(id);
    }
    return buf.toString();
  }

  private class GetHelper {
    private List<EntityIndex> getEquipment(Connection conn, int customerId, int ruleTemplateId)
        throws SQLException {
      final String sql =
          "SELECT nn.id, nn.fully_qualified_name\n" +
              "  FROM (SELECT e.id\n" +
              "  FROM (SELECT node_id \n" +
              "    FROM ad_rule_instances \n" +
              "    WHERE ad_rule_template_id = ? AND active = true) AS sub1\n" +
              "  JOIN equipment e ON e.id = sub1.node_id\n" +
              "  JOIN customers c ON e.customer_id = c.id\n" +
              "  WHERE customer_id = ?) AS sub2\n" +
              "JOIN node_names nn ON sub2.id = nn.id\n" +
              "ORDER BY nn.fully_qualified_name";
      return statementFactory.newStatement()
          .withSql(sql)
          .prepareStatement(stmt -> {
            stmt.setInt(1, ruleTemplateId);
            stmt.setInt(2, customerId);
          })
          .executeQueryWithConnection(conn,
              resultSet -> DaoUtils.processList(resultSet, (idx, rs) -> {
                return EntityIndex.builder()
                    .withId(rs.getInt("id"))
                    .withText(rs.getString("fully_qualified_name"))
                    .build();
              }));
    }


    private List<EntityIndex> getCandidates(Connection conn, int customerId, int ruleTemplateId)
        throws SQLException {
      RuleTemplate template = getRuleTemplate(conn, ruleTemplateId);
      final String sql = getCandidatesSql(template, false);
      System.out.println(sql);
      return statementFactory.newStatement()
          .withSql(sql)
          .prepareStatement(stmt -> {
            stmt.setInt(1, customerId);
            stmt.setInt(2, ruleTemplateId);
          })
          .executeQueryWithConnection(conn,
              resultSet -> DaoUtils.processList(resultSet, (idx, rs) -> {
                return EntityIndex.builder()
                    .withId(rs.getInt("id"))
                    .withText(rs.getString("fully_qualified_name"))
                    .build();
              }));
    }

    private List<RuleCandidateForGeneration> getCandidatesForGeneration(Connection conn,
        int customerId, RuleTemplate template)
        throws SQLException {
      final String sql = getCandidatesSql(template, true);
      System.out.println(sql);
      return statementFactory.newStatement()
          .withSql(sql)
          .prepareStatement(stmt -> {
            stmt.setInt(1, customerId);
            stmt.setInt(2, template.getId());
          })
          .executeQueryWithConnection(conn,
              resultSet -> DaoUtils.processList(resultSet, (idx, rs) -> {
                String json = rs.getString("point_ids");
                List<List<Integer>> pointIds;
                try {
                  pointIds = MAPPER.readValue(json, new TypeReference<List<List<Integer>>>() {});
                } catch (IOException e) {
                  throw new DataAccessException(e);
                }
                return RuleCandidateForGeneration.builder()
                    .withId(rs.getInt("id"))
                    .withText(rs.getString("fully_qualified_name"))
                    .withPointIds(pointIds)
                    .build();
              }));
    }

    private RuleTemplate getRuleTemplate(Connection conn, int ruleTemplateId)
        throws SQLException {
      return statementFactory.newStatement()
          .withSql("SELECT get_rule_template(?) AS json")
          .prepareStatement(stmt -> {
            stmt.setInt(1, ruleTemplateId);
          })
          .executeQueryWithConnection(conn,
              resultSet -> DaoUtils.processObject(resultSet, (idx, rs) -> {
                String json = rs.getString("json");
                if (json == null) {
                  return null;
                }
                try {
                  return MAPPER.readValue(json, RuleTemplate.class);
                } catch (IOException e) {
                  throw new DataAccessException(e);
                }
              }));

    }

    private String getCandidatesSql(RuleTemplate template, boolean includePointIds) {
      StringBuilder buf = new StringBuilder();
      buf.append("SELECT base.id, base.fully_qualified_name");
      if (includePointIds) {
        buf.append(", '' || TO_JSON(ARRAY[");
        boolean firstLoop = true;
        for (int i = 1; i <= template.getInputPoints().size(); i++) {
          if (firstLoop) {
            firstLoop = false;
          }
          buf.append("input_point_" + i + ".point_ids");
        }
        buf.append("]) AS point_ids");
      }
      buf.append(
          "\nFROM (\n" +
              getBaseSql(template) + "\n" +
              ") AS base\n");


      String lastAlias = "base";
      if (template.getNodeFilterExpression() != null) {
        lastAlias = "node_filter";
        TagQueryExpression exp = TagQueryExpression.parse(template.getNodeFilterExpression());
        buf.append(
            "JOIN (SELECT node_id AS id FROM (\n" +
                exp.toSql() + "\n" +
                ") AS nf) AS node_filter ON base.id = node_filter.id\n");
      }

      int inputPointIdx = 1;
      for (RuleTemplateInputPoint inputPoint : template.getInputPoints()) {
        if (!inputPoint.getRequired() && !includePointIds) {
          continue;
        }
        String nextAlias = "input_point_" + inputPointIdx++;
        buf.append("JOIN (\n" +
            getInputPointSql(inputPoint, includePointIds) + "\n" +
            ") AS " + nextAlias + " ON " + lastAlias + ".id = " + nextAlias + ".id\n");
        lastAlias = nextAlias;
      }

      buf.append("ORDER BY base.fully_qualified_name");
      return buf.toString();
    }

    private String getBaseSql(RuleTemplate template) {
      // PARAMS
      // 1. customerId
      // 2. ruleTemplateId

      String templateTagIds = listToCommaSeparatedString(
          template.getTemplateTags().stream().map(Tag::getId).collect(Collectors.toList()));

      return "  SELECT DISTINCT e.id, nn.fully_qualified_name \n" +
          "  FROM equipment_tbl e\n" +
          "  JOIN node_tbl n\n" +
          "    ON e.id = n.id\n" +
          "  JOIN node_name_tbl nn\n" +
          "    ON e.id = nn.id\n" +
          "  JOIN node_tag_tbl nt\n" +
          "    ON e.id = nt.node_id\n" +
          "  WHERE n.customer_id = ?\n" +
          "    AND nt.tag_id = ANY (ARRAY[" + templateTagIds + "])\n" +
          "    AND e.id NOT IN (\n" +
          "      SELECT e.id AS num_recs\n" +
          "      FROM equipment_tbl e\n" +
          "      JOIN node_tbl n\n" +
          "        ON e.id = n.id\n" +
          "      JOIN ad_rule_instance_tbl ar\n" +
          "        ON e.id = ar.node_id\n" +
          "      WHERE ar.ad_rule_template_id = ?\n" +
          "        AND ar.active = true\n"
          + "  )";
    }

    private String getInputPointSql(RuleTemplateInputPoint inputPoint, boolean includePointIds) {
      StringBuilder buf = new StringBuilder();
      buf.append("SELECT current_object.start_id AS id");
      if (includePointIds) {
        buf.append(", ARRAY_AGG(input_point_tags.point_id) AS point_ids");
      }
      buf.append("\nFROM (\n");
      if (inputPoint.getCurrentObjectExpression() == null) {
        buf.append(
            "  SELECT e.id AS start_id, e.id AS end_id FROM equipment_tbl e\n");
      } else {
        NodeTraversalExpression exp =
            NodeTraversalExpression.parse(inputPoint.getCurrentObjectExpression());
        buf.append("  " + exp.toSql() + "\n");
      }
      buf.append(") AS current_object\n");
      if (includePointIds && !inputPoint.getRequired()) {
        buf.append("LEFT OUTER ");
      }
      buf.append("JOIN (\n");
      buf.append(getInputPointTagSql(inputPoint) + "\n");
      buf.append(") AS input_point_tags ON input_point_tags.parent_id = current_object.end_id\n");
      if (includePointIds) {
        buf.append("GROUP BY current_object.start_id");
      }
      return buf.toString();
    }

    private String getInputPointTagSql(RuleTemplateInputPoint inputPoint) {
      // First we need to sort the input point tags
      List<Integer> sortedTags =
          inputPoint.getTags().stream().map(Tag::getId).sorted().collect(Collectors.toList());
      String sortedTagIds = listToCommaSeparatedString(sortedTags);
      String sql =
          "WITH point_tags AS\n" +
              "  ( SELECT nc.parent_id,\n" +
              "           mp.id AS point_id,\n" +
              "           ARRAY_AGG(nt.tag_id\n" +
              "                     ORDER BY nt.tag_id ASC) AS tag_ids\n" +
              "   FROM node_closure_tbl nc\n" +
              "   JOIN mappable_point_tbl mp ON nc.child_id = mp.id\n" +
              "   AND mp.raw_point_id IS NOT NULL\n" +
              "   AND nc.depth = 1\n" +
              "   JOIN node_tag_tbl nt ON mp.id = nt.node_id\n" +
              "   JOIN tag_tbl t ON nt.tag_id = t.id AND t.tag_group_id = 7\n" +
              "   GROUP BY nc.parent_id,\n" +
              "            mp.id)\n" +
              "SELECT pt.parent_id,\n" +
              "       pt.point_id,\n" +
              "       pt.tag_ids\n" +
              "FROM point_tags pt\n";
      if (!inputPoint.getArray()) {
        sql +=
            "JOIN\n" +
                "  ( SELECT pt2.parent_id,\n" +
                "           count(pt2.point_id) AS point_count\n" +
                "   FROM point_tags pt2\n" +
                "   WHERE pt2.tag_ids = '{" + sortedTagIds + "}'\n" +
                "   GROUP BY pt2.parent_id) AS count_query ON pt.parent_id = count_query.parent_id\n";
      }
      sql += "WHERE pt.tag_ids = '{" + sortedTagIds + "}'\n";
      if (!inputPoint.getArray()) {
        sql +=
            "  AND count_query.point_count = 1\n";
      }

      return sql;
    }
  }
}
