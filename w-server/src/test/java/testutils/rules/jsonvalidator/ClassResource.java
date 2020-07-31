package testutils.rules.jsonvalidator;

class ClassResource {
  private final Class<?> fileLocatorClass;
  private final String fileName;

  ClassResource(Class<?> fileLocatorClass, String fileName) {
    this.fileLocatorClass = fileLocatorClass;
    this.fileName = fileName;
  }

  Class<?> getFileLocatorClass() {
    return fileLocatorClass;
  }

  String getFileName() {
    return fileName;
  }

}
