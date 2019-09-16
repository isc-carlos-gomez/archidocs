package com.krloxz.archidocs.story;

import java.io.File;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.krloxz.archidocs.test.ArchidoctorDriver;
import com.krloxz.archidocs.test.FakeApplication;
import com.krloxz.archidocs.test.OutputDirectory;

/**
 * Document Components
 * <p>
 * As a developer,<br>
 * I want to automate the generation of classes diagrams<br>
 * so that these diagrams are used to document component architectures
 *
 * @author Carlos Gomez
 */
class DocumentComponentsStory {

  private final FakeApplication app = new FakeApplication();
  private final OutputDirectory outputDirectory = new OutputDirectory();
  private final ArchidoctorDriver archidoctor = new ArchidoctorDriver(this.app, this.outputDirectory);

  @Test
  @Disabled("To be replaced by cucumber?")
  void documentSpringComponents(@TempDir final File tempDir) throws Exception {
    given(this.app.readyToDocument())
      .and(this.outputDirectory.locatedAt(tempDir));
    when(this.archidoctor.document());
    then(this.outputDirectory.hasComponentsSnippet());
  }

  private <T> Joint given(final T call) {
    return Joint.INSTANCE;
  }

  private <T> Joint when(final T call) {
    return Joint.INSTANCE;
  }

  private <T> Joint then(final T call) {
    return Joint.INSTANCE;
  }

  private static class Joint {

    private static final Joint INSTANCE = new Joint();

    public <T> Joint and(final T methodCall) {
      return INSTANCE;
    }

  }

}
