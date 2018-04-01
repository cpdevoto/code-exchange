package com.shrinedev.avs.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version implements Comparable<Version> {
  private final Pattern VERSION = Pattern.compile("^(\\d+)\\.(\\d+)\\.(\\d+)$");

  private final int majorVersion;
  private final int minorVersion;
  private final int buildNum;

  public Version(String tag) {
    Matcher m = VERSION.matcher(tag);
    if (m.find()) {
      this.majorVersion = Integer.parseInt(m.group(1));
      this.minorVersion = Integer.parseInt(m.group(2));
      this.buildNum = Integer.parseInt(m.group(3));
    } else {
      this.majorVersion = -1;
      this.minorVersion = -1;
      this.buildNum = -1;
    }
  }

  public int getMajorVersion() {
    return majorVersion;
  }



  public int getMinorVersion() {
    return minorVersion;
  }



  public int getBuildNum() {
    return buildNum;
  }



  @Override
  public int compareTo(Version v) {
    int result = this.majorVersion - v.majorVersion;
    if (result == 0) {
      result = this.minorVersion - v.minorVersion;
    }
    if (result == 0) {
      result = this.buildNum - v.buildNum;
    }
    return -1 * result;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + buildNum;
    result = prime * result + majorVersion;
    result = prime * result + minorVersion;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Version other = (Version) obj;
    if (buildNum != other.buildNum)
      return false;
    if (majorVersion != other.majorVersion)
      return false;
    if (minorVersion != other.minorVersion)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return majorVersion + "." + minorVersion + "." + buildNum;
  }
}
