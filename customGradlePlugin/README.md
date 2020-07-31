To build your own custom plugin in Eclipse:
  1. Install the Groovy-Eclipse plugin into your IDE (you can find the appropriate update site for your version of Eclipse here: https://github.com/groovy/groovy-eclipse/wiki).
  2. Copy the customPlugin/plugin project, and add apply plugin 'eclipse' to the build.gradle file.
  3. Run gradle cleanEclipse eclipse.
  4. Import the project into Eclipse
