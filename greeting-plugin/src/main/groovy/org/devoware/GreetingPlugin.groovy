package org.devoware
import org.gradle.api.Plugin
import org.gradle.api.Project

class GreetingPlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    project.task('hello') << {
      println "Hello from the GreetingPlugin"
    }
    
  }

}
