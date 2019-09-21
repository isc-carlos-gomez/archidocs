package com.krloxz.archidocs.test;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.krloxz.archidocs.MutableArchidoctorConfig;

import io.cucumber.java.en.Given;

/**
 * @author Carlos Gomez
 */
public class SoftwarePiece {

  private String nameSpace;
  private Path sourcePath;

  @Given("a piece of software implementing a container")
  public void implementingContainer() {
    this.nameSpace = "com.krloxz.archidocs.test.petclinic";
    this.sourcePath = Paths.get("/Users/Carlos/Projects/Archidocs/repos/archidocs/src/test/java");
  }

  public MutableArchidoctorConfig enrichConfig(final MutableArchidoctorConfig config) {
    return config.setNamespace(this.nameSpace)
      .setSourcePath(this.sourcePath);
  }

}
