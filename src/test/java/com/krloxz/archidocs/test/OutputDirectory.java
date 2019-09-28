package com.krloxz.archidocs.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;

import org.picocontainer.Disposable;

import com.krloxz.archidocs.MutableArchidoctorConfig;

/**
 * Encapsulates an output directory where Archidoctor will save the generated documentation.
 *
 * @author Carlos Gomez
 */
public class OutputDirectory implements Disposable {

  private final Path path;

  public OutputDirectory() {
    this.path = createDirectory();
  }

  public void hasFile(final String fileName) throws IOException {
    assertThat(this.path).isDirectoryContaining("glob:**/" + fileName);
  }

  public String readContent(final String fileName) throws IOException {
    final Optional<Path> snippet = Files.list(this.path).findFirst();
    assertThat(snippet).isNotEmpty();
    return new String(Files.readAllBytes(snippet.get()), StandardCharsets.UTF_8);
  }

  @Override
  public void dispose() {
    if (this.path != null) {
      deleteDirectory();
    }
  }

  public MutableArchidoctorConfig enrichConfig(final MutableArchidoctorConfig config) {
    return config.setOutputDirectory(this.path);
  }

  private Path createDirectory() {
    try {
      return Files.createTempDirectory("archidoctor");
    } catch (final IOException e) {
      throw new IllegalStateException("Not able to create the output directory", e);
    }
  }

  private void deleteDirectory() {
    try {
      Files.walk(this.path)
          .sorted(Comparator.reverseOrder())
          .map(Path::toFile)
          .forEach(File::delete);
    } catch (final IOException e) {
      throw new IllegalStateException("Not able to delete the output directory: " + this.path, e);
    }
  }

}
