package com.doradosystems.mis.worker.ddeservice.shell;

import static com.doradosystems.mis.worker.config.DdeServiceConfiguration.RETRIEVE_CLAIMS_PROCESS;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;

import org.devoware.validators.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doradosystems.mis.agent.model.Claim;
import com.doradosystems.mis.agent.model.ClaimRetrievalTask;
import com.doradosystems.mis.agent.model.NationalProviderIdentifier;
import com.doradosystems.mis.agent.model.Operator;
import com.doradosystems.mis.worker.config.DdeServiceConfiguration;
import com.doradosystems.mis.worker.ddeservice.DdeServiceException;
import com.doradosystems.mis.worker.ddeservice.DdeServiceTimeoutException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

public class ShellBasedRetrieveClaimsService {
  private static final Logger log = LoggerFactory.getLogger(ShellBasedRetrieveClaimsService.class);

  private final String operatorProcessPath;
  private final ObjectMapper mapper;
  private final Duration serviceTimeout;

  @Inject
  public ShellBasedRetrieveClaimsService(@Nonnull DdeServiceConfiguration config,
      @Nonnull ObjectMapper mapper) {
    this.operatorProcessPath = requireNonNull(config).getProcessPath(RETRIEVE_CLAIMS_PROCESS);
    this.mapper = requireNonNull(mapper);
    this.serviceTimeout = config.getServiceTimeout();
  }

  @Nonnull
  public List<Claim> getClaims(@Nonnull ClaimRetrievalTask claimRetrievalTask)
      throws DdeServiceException {
    requireNonNull(claimRetrievalTask);
    final List<Claim> claims = Lists.newArrayList();
    for (NationalProviderIdentifier npi : claimRetrievalTask.getNationalProviderIdentifiers()) {
      claims.addAll(getClaims(claimRetrievalTask.getOperator(), npi));
    };
    return claims;
  }

  @Nonnull
  private List<Claim> getClaims(Operator operator, NationalProviderIdentifier npi)
      throws DdeServiceException {
    
    final String encoded = encodeRequest(operator, npi);
    final List<Claim> claims = invokeProcess(encoded);
    return claims;
  }

  private List<Claim> invokeProcess(final String encoded) throws DdeServiceException {
    final List<Claim> claims = Lists.newArrayList();
    final ExceptionHolder exceptionHolder = new ExceptionHolder();
    Process p = null;
    try {
      final ProcessBuilder pb = new ProcessBuilder(operatorProcessPath, encoded);
      pb.redirectErrorStream(true);
      final Process process = p = pb.start();
      
      long start = System.currentTimeMillis();
      try (final Scanner scanner = new Scanner(process.getInputStream())) {
        // We need to force the process to timeout or it could block indefinitely
        new Thread(() -> {
          try {
            Thread.sleep(serviceTimeout.toMilliseconds());
            if (process.isAlive()) {
              exceptionHolder.exception = new DdeServiceTimeoutException();
              process.destroyForcibly();
            }
          } catch (Exception e) {
            log.error("A problem occurred while a attempting to close the input stream for the claim retrieval service.", e);
          }
        }, Thread.currentThread().getName() + "-timer").start();

        while (scanner.hasNext() != false) {
          String json = scanner.nextLine();
          List<Claim> list = mapper.readValue(json, new TypeReference<List<Claim>>() {});
          claims.addAll(list);
        }
      }
      long waitInterval = Math.max(0, serviceTimeout.toMilliseconds() - System.currentTimeMillis() + start);
      process.waitFor(waitInterval, TimeUnit.MILLISECONDS);
      if (exceptionHolder.exception != null) {
        throw exceptionHolder.exception;
      }
    } catch (IOException | InterruptedException e) {
      throw new DdeServiceException(e.getMessage() != null ? e.getMessage() : e.getClass().getName(), e);
    } finally {
      if (p != null && p.isAlive()) {
        p.destroyForcibly();
      }
    }
    return claims;
  }

  private String encodeRequest(Operator operator, NationalProviderIdentifier npi)
      throws DdeServiceException {
    try {
      Map<String, String> request = new HashMap<String, String>();
      request.put("username", operator.getUserName());
      request.put("password", operator.getPassword());
      request.put("npi", String.valueOf(npi.getIdNumber()));
      request.put("region_code", npi.getRegionCode());
      String payload = mapper.writeValueAsString(request);
      byte[] message = payload.getBytes("UTF-8");
      String encoded = DatatypeConverter.printBase64Binary(message);
      return encoded;
    } catch (JsonProcessingException | UnsupportedEncodingException e) {
      throw new DdeServiceException(e.getMessage() != null ? e.getMessage() : e.getClass().getName(), e);
    }
  }

  private static class ExceptionHolder {
    private volatile DdeServiceException exception = null;
    private ExceptionHolder () {}
  }
}
