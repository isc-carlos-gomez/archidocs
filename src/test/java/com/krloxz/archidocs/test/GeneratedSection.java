package com.krloxz.archidocs.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import com.krloxz.archidocs.Archidoctor;
import com.krloxz.archidocs.MutableArchidoctorConfig;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * @author Carlos Gomez
 */
public class GeneratedSection {

  private final Archidoctor archidoctor;
  private final List<String> sectionLines;
  private final SoftwarePiece softwarePiece;
  private final OutputDirectory outputDirectory;

  public GeneratedSection(final SoftwarePiece softwarePiece, final OutputDirectory outputDirectory) {
    this.archidoctor = new Archidoctor();
    this.sectionLines = Lists.newArrayList();
    this.softwarePiece = softwarePiece;
    this.outputDirectory = outputDirectory;
  }

  @When("the {string} section is generated")
  public void generateSection(final String sectionType) throws IOException {
    MutableArchidoctorConfig config = MutableArchidoctorConfig.create();
    config = this.softwarePiece.enrichConfig(config);
    config = this.outputDirectory.enrichConfig(config);
    this.archidoctor.document(config.toImmutable());
    this.sectionLines.addAll(this.outputDirectory.readLines(sectionType + ".adoc"));
  }

  @Then("the section has a {string} view embedded")
  public void hasView(final String viewType) throws IOException {
    assertThat(this.sectionLines.get(1)).isEqualTo("[plantuml]");
    assertThat(this.sectionLines.get(2)).isEqualTo("....");
    assertThat(this.sectionLines.get(3)).isEqualTo("@startuml(id=%s)", viewType);
    assertThat(this.sectionLines.get(this.sectionLines.size() - 2)).isEqualTo("@enduml");
    assertThat(this.sectionLines.get(this.sectionLines.size() - 1)).isEqualTo("....");
  }

  @Then("the section is titled {string}")
  public void isTitled(final String title) throws IOException {
    assertThat(this.sectionLines.get(0)).isEqualTo("== " + title);
  }

}
