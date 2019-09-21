package com.krloxz.archidocs.test;

import java.io.IOException;

import com.krloxz.archidocs.Archidoctor;
import com.krloxz.archidocs.MutableArchidoctorConfig;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * @author Carlos Gomez
 */
public class GeneratedDocumentation {

  private final Archidoctor archidoctor;
  private final SoftwarePiece softwarePiece;
  private final OutputDirectory outputDirectory;

  public GeneratedDocumentation(final SoftwarePiece softwarePiece, final OutputDirectory outputDirectory) {
    this.archidoctor = new Archidoctor();
    this.softwarePiece = softwarePiece;
    this.outputDirectory = outputDirectory;
  }

  @When("documentation is generated")
  public void generateDocumentation() {
    MutableArchidoctorConfig config = MutableArchidoctorConfig.create();
    config = this.softwarePiece.enrichConfig(config);
    config = this.outputDirectory.enrichConfig(config);
    this.archidoctor.document(config.toImmutable());
  }

  @Then("the documentation has a {string} section")
  public void hasSection(final String sectionType) throws IOException {
    this.outputDirectory.hasFile(sectionType + ".adoc");
  }

}
