package com.krloxz.archidocs.test;

import com.krloxz.archidocs.Archidoctor;

import io.cucumber.java.en.When;

/**
 * @author Carlos Gomez
 */
public class ArchidoctorDriver {

  private final Archidoctor archidoctor;
  private final SoftwarePiece app;
  private final OutputDirectory outputDirectory;

  public ArchidoctorDriver(final SoftwarePiece app, final OutputDirectory outputDirectory) {
    this.app = app;
    this.outputDirectory = outputDirectory;
    this.archidoctor = new Archidoctor();
  }

  @When("documentation is generated")
  public void generateDocumentation() {
    this.archidoctor.document(this.app.getNamespace(), this.outputDirectory.getPath(), this.app.getName());
  }

}
