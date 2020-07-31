package com.doradosystems.mis.worker.ddeservice.shell;

import static com.doradosystems.mis.worker.config.DdeServiceConfiguration.RETRIEVE_CLAIMS_PROCESS;
import static java.nio.file.attribute.PosixFilePermission.GROUP_READ;
import static java.nio.file.attribute.PosixFilePermission.OTHERS_READ;
import static java.nio.file.attribute.PosixFilePermission.OWNER_EXECUTE;
import static java.nio.file.attribute.PosixFilePermission.OWNER_READ;
import static java.nio.file.attribute.PosixFilePermission.OWNER_WRITE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

import org.devoware.homonculus.validators.util.Duration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.doradosystems.mis.agent.model.Claim;
import com.doradosystems.mis.agent.model.ClaimRetrievalTask;
import com.doradosystems.mis.agent.model.NationalProviderIdentifier;
import com.doradosystems.mis.agent.model.Operator;
import com.doradosystems.mis.worker.config.DdeServiceConfiguration;
import com.doradosystems.mis.worker.ddeservice.DdeServiceException;
import com.doradosystems.mis.worker.ddeservice.DdeServiceTimeoutException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;

public class ShellBasedRetrieveClaimsServiceTest {

  @Rule
  public TemporaryFolder temp = new TemporaryFolder();
  
  private File script1;
  private File script2;
  private File script3;
  private ObjectMapper mapper;
  private ClaimRetrievalTask task;

  @Before
  public void setup() throws IOException {
    script1 = generateFileFromClasspath("fake-retrieve-claims.sh", "fake-retrieve-claims.sh");
    script2 = generateFileFromClasspath("fake-retrieve-claims2.sh", "fake-retrieve-claims2.sh");
    script3 = generateFileFromClasspath("fake-retrieve-claims3.sh", "fake-retrieve-claims3.sh");
    mapper = new ObjectMapper();
    task = ClaimRetrievalTask.builder()
        .withOperator(Operator.builder()
            .withId(1)
            .withUserName("WY20385")
            .withPassword("password")
            .build())
        .withNationalProviderIdentifier(NationalProviderIdentifier.builder()
            .withId(1)
            .withIdNumber(1497876965)
            .withRegionCode("FISPH4-1")
            .build())
        .build();
  }

  @Test
  public void test_retrieve_claims_with_valid_result() throws DdeServiceException {
    DdeServiceConfiguration config = mock(DdeServiceConfiguration.class);
    when(config.getProcessPath(RETRIEVE_CLAIMS_PROCESS)).thenReturn(script1.getAbsolutePath());
    when(config.getServiceTimeout()).thenReturn(Duration.minutes(1));
    
    ShellBasedRetrieveClaimsService service = new ShellBasedRetrieveClaimsService(config, mapper);
    
    List<Claim> claims = service.getClaims(task);
    assertNotNull(claims);
    assertThat(claims.size(), equalTo(17));
  }

  @Test(expected=DdeServiceTimeoutException.class)
  public void test_retrieve_claims_timeout() throws DdeServiceException {
    DdeServiceConfiguration config = mock(DdeServiceConfiguration.class);
    when(config.getProcessPath(RETRIEVE_CLAIMS_PROCESS)).thenReturn(script3.getAbsolutePath());
    when(config.getServiceTimeout()).thenReturn(Duration.seconds(5));
    
    ShellBasedRetrieveClaimsService service = new ShellBasedRetrieveClaimsService(config, mapper);
    
    service.getClaims(task);
  }

  @Test(expected=DdeServiceException.class)
  public void test_retrieve_claims_where_script_returns_bad_result() throws DdeServiceException {
    DdeServiceConfiguration config = mock(DdeServiceConfiguration.class);
    when(config.getProcessPath(RETRIEVE_CLAIMS_PROCESS)).thenReturn(script2.getAbsolutePath());
    when(config.getServiceTimeout()).thenReturn(Duration.minutes(1));
    
    ShellBasedRetrieveClaimsService service = new ShellBasedRetrieveClaimsService(config, mapper);
    
    service.getClaims(task);
  }

  private File generateFileFromClasspath(String fileName, String fileClassPath) throws IOException {
    String fileContents = getFileContents(fileClassPath);
    File root = temp.getRoot();
    File outputFile = new File(root, fileName);
    try (PrintWriter out = new PrintWriter(new FileWriter(outputFile))) {
      out.print(fileContents);
    }
    Files.setPosixFilePermissions(outputFile.toPath(),
        ImmutableSet.of(OWNER_READ, OWNER_WRITE, OWNER_EXECUTE, GROUP_READ, OTHERS_READ));
    return outputFile;
  }

  private String getFileContents(String classPath) throws IOException {
    String classSource;
    try (InputStream in =
        ShellBasedRetrieveClaimsServiceTest.class.getClassLoader().getResourceAsStream(classPath);
        Scanner s = new Scanner(in)) {
      s.useDelimiter("\\A");
      classSource = s.hasNext() ? s.next() : "";
      s.close();
    }
    return classSource;
  }


}
