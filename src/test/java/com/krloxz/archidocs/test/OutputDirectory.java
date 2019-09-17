package com.krloxz.archidocs.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.picocontainer.Disposable;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

/**
 * @author Carlos Gomez
 */
public class OutputDirectory implements Disposable {

  private Path path;

  public Path getPath() {
    return this.path;
  }

  @Override
  public void dispose() {
    if (this.path != null) {
      deleteDirectory();
    }
  }

  @Given("an output directory")
  public void outputDirectory() throws IOException {
    this.path = Files.createTempDirectory("archidoctor");
  }

  @Then("the output directory has a snippet named {string}")
  public void hasSnippet(final String name) {
    assertThat(this.path).isDirectoryContaining("glob:**/" + name);
  }

  @Then("the output directory has a snippet titled {string}")
  public void snippetTitled(final String title) throws IOException {
    final Optional<Path> snippet = Files.list(this.path).findFirst();
    assertThat(snippet).isNotEmpty();
    final List<String> lines = Files.readAllLines(snippet.get());
    assertThat(lines.get(0)).isEqualTo("== " + title);
  }

  @Then("the output directory has a snippet containing a {string} diagram")
  public void snippedContainingDiagram(final String diagramType) throws IOException {
    final Optional<Path> snippet = Files.list(this.path).findFirst();
    assertThat(snippet).isNotEmpty();
    final List<String> lines = Files.readAllLines(snippet.get());
    assertThat(lines.get(1)).isEqualTo("[plantuml]");
    assertThat(lines.get(2)).isEqualTo("....");
    assertThat(lines.get(3)).isEqualTo("@startuml(id=components)");
    assertThat(lines.get(lines.size() - 2)).isEqualTo("@enduml");
    assertThat(lines.get(lines.size() - 1)).isEqualTo("....");
  }

  @Then("the output directory has a container snippet")
  public void containsComponentsSnippet() {
    assertThat(this.path).isDirectoryContaining("glob:**/components.adoc");

    try {
      final List<String> lines = Files.readAllLines(this.path.resolve("components.adoc"));
      assertThat(lines).hasSizeGreaterThan(6);
      assertThat(lines.get(0)).isEqualTo("== Components");
      assertThat(lines.get(1)).isEqualTo("[plantuml]");
      assertThat(lines.get(2)).isEqualTo("....");
      assertThat(lines.get(3)).isEqualTo("@startuml(id=components)");
      assertThat(lines.get(lines.size() - 2)).isEqualTo("@enduml");
      assertThat(lines.get(lines.size() - 1)).isEqualTo("....");
    } catch (IOException e) {
      throw new IllegalStateException("Not able to read the file: " + this.path, e);
    }
  }

  private void deleteDirectory() {
    try {
      Files.walk(this.path)
        .sorted(Comparator.reverseOrder())
        .map(Path::toFile)
        .forEach(File::delete);
    } catch (IOException e) {
      throw new IllegalStateException("Not able to delete output directory: " + this.path, e);
    }
  }

}
