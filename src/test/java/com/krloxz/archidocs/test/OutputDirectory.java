package com.krloxz.archidocs.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

import org.picocontainer.Disposable;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

/**
 * @author Carlos Gomez
 */
public class OutputDirectory implements Disposable {

  private Path path;

  @Override
  public void dispose() {
    if (this.path == null) {
      return;
    }
    try {
      Files.walk(this.path)
        .sorted(Comparator.reverseOrder())
        .map(Path::toFile)
        .forEach(File::delete);
    } catch (IOException e) {
      throw new IllegalStateException("Not able to delete output directory: " + this.path, e);
    }
  }

  @And("an output directory")
  public void outputDirectory() throws IOException {
    this.path = Files.createTempDirectory("archidoctor");
  }

  /**
   * @return
   */
  public Path getPath() {
    return this.path;
  }

  /**
   * @param directory
   * @return
   */
  public Void locatedAt(final File directory) {
    this.path = directory.toPath();
    return null;
  }

  /**
   * @return
   */
  @Then("the output directory has a components snippet")
  public Void hasComponentsSnippet() {
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

    return null;
  }

}
