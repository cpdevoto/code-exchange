package com.resolute.rule.generator.rule_template;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.resolute.rule.generator.common.model.EntityIndex;
import com.resolute.rule.generator.rule_template.dao.RuleTemplateDao;

import static java.util.Objects.requireNonNull;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/rule-templates")
public class RuleTemplateResource {

  private final RuleTemplateDao dao;

  public RuleTemplateResource(RuleTemplateDao dao) {
    this.dao = requireNonNull(dao, "dao cannot be null");
  }

  @PermitAll
  @GET
  public Response get() {
    List<EntityIndex> customers = dao.get();

    return Response.ok().entity(customers).header("Access-Control-Allow-Origin", "*").build();
  }

}
