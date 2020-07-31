package com.shrinedev.avs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.shrinedev.avs.model.ArtifactVersions;
import com.shrinedev.avs.model.Version;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ArtifactVersionSleuth {

  public static void main(String[] args) {
    String artifactoryKey = System.getProperty("artifactory.key");
    if (artifactoryKey == null) {
      System.out.println("ERROR: Expected a -Dartifactory.key=<KEY> command-line switch");
      System.exit(1);
    }

    String imageName = System.getProperty("artifactory.image");
    if (artifactoryKey == null) {
      System.out.println("ERROR: Expected a -Dartifactory.image=<IMAGE> command-line switch");
      System.exit(1);
    }

    String templateFile = System.getProperty("template");
    if (templateFile == null) {
      templateFile = imageName + ".tpl";
    }

    OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build();

    HttpUrl url = HttpUrl
        .parse("https://shrinedevelopment.jfrog.io/shrinedevelopment/api/docker/docker-develop/v2/"
            + imageName + "/tags/list")
        .newBuilder()
        .build();

    Request request = new Request.Builder()
        .url(url)
        .addHeader("X-JFrog-Art-Api", artifactoryKey)
        .build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        System.out.println(
            "ERROR: A problem occurred while attempting to retrieve the artifact version for "
                + imageName + ": " + response.code());
        System.exit(1);
      }
      ObjectMapper mapper = new ObjectMapper();
      String json = response.body().string();
      ArtifactVersions versions = mapper.readValue(json, ArtifactVersions.class);

      Optional<Version> latest = versions.latest();
      if (!latest.isPresent() || latest.get().getMajorVersion() == -1) {
        System.out.println("ERROR: Could not find the latest version of artifact " + imageName);
        System.exit(1);
      }

      String fileContents = readFile(templateFile, Charsets.UTF_8);
      String output =
          fileContents.replaceAll("\\{\\{\\s*version\\s*\\}\\}", latest.get().toString());

      String outputFile;
      int idx = templateFile.lastIndexOf('.');
      if (idx == -1) {
        outputFile = templateFile + ".yaml";
      } else {
        outputFile = templateFile.substring(0, idx) + ".yaml";
      }
      try (PrintWriter out = new PrintWriter(outputFile)) {
        out.println(output);
      }
    } catch (IOException e) {
      System.out.println(
          "ERROR: A problem occurred while attempting to retrieve the artifact version for "
              + imageName + ": " + e.getMessage());
      System.exit(1);
    }


  }

  private static String readFile(String path, Charset encoding)
      throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, encoding);
  }

}
