package com.resolute.rule.generator.rule_instance;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.resolute.rule.generator.common.model.RuleInstances;
import com.resolute.rule.generator.rule_instance.dao.RuleInstanceDao;

import static java.util.Objects.requireNonNull;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/customers/{customerId}/rule-templates/{ruleTemplateId}/rule-instances")
public class RuleInstanceResource {

  private final RuleInstanceDao dao;

  public RuleInstanceResource(RuleInstanceDao dao) {
    this.dao = requireNonNull(dao, "dao cannot be null");
  }

  @PermitAll
  @GET
  public Response get(@PathParam("customerId") int customerId,
      @PathParam("ruleTemplateId") int ruleTemplateId) {
    RuleInstances instances = dao.get(customerId, ruleTemplateId);

    return Response.ok().entity(instances).header("Access-Control-Allow-Origin", "*").build();
  }

  @PermitAll
  @POST
  public Response get(@PathParam("customerId") int customerId,
      @PathParam("ruleTemplateId") int ruleTemplateId, List<Integer> equipmentIds) {

    dao.generate(customerId, ruleTemplateId, equipmentIds);
    return Response.ok().header("Access-Control-Allow-Origin", "*").build();
  }
}
