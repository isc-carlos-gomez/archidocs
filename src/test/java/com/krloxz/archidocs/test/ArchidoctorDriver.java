package com.krloxz.archidocs.test;

import com.krloxz.archidocs.Archidoctor;

import io.cucumber.java.en.When;

/**
 * @author Carlos Gomez
 */
public class ArchidoctorDriver {

  private final Archidoctor archidoctor = new Archidoctor();
  private final FakeApplication app;
  private final OutputDirectory outputDirectory;

  public ArchidoctorDriver(final FakeApplication app, final OutputDirectory outputDirectory) {
    this.app = app;
    this.outputDirectory = outputDirectory;
  }

  /**
   * @param app
   * @param outputDirectory
   * @return
   */
  @When("the application is documented")
  public Void document() {
    this.archidoctor.document(this.app.getNamespace(), this.outputDirectory.getPath());
    return null;
  }

}
