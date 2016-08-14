package com.doradosystems.mis.scheduler.output;

import java.io.IOException;

public interface OutputChannel {

  public void write (String message) throws IOException;
  
}
