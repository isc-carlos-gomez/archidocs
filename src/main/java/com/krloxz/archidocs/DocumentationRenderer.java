package com.krloxz.archidocs;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.structurizr.documentation.Section;
import com.structurizr.io.plantuml.PlantUMLWriter;
import com.structurizr.view.View;

/**
 * @author Carlos Gomez
 */
public class DocumentationRenderer {

  private final PlantUMLWriter plantUMLWriter;
  private Documentation doc;
  private ArchidoctorConfig config;

  public DocumentationRenderer() {
    this.plantUMLWriter = newPlantUmlWriter();
  }

  public void render(final Documentation doc, final ArchidoctorConfig config) {
    this.doc = doc;
    this.config = config;
    doc.sections().stream()
        .sorted((s1, s2) -> s1.getOrder() - s2.getOrder())
        .forEach(this::renderSection);
  }

  private void renderSection(final Section section) {
    final StringBuilder snippet = new StringBuilder();
    snippet.append(String.format("== %s\n", section.getTitle()));

    final Pattern pattern = Pattern.compile("!\\[\\]\\(embed\\:(\\S+)\\)");
    final Matcher matcher = pattern.matcher(section.getContent());
    final StringBuffer sb = new StringBuffer();
    while (matcher.find()) {
      final String viewName = matcher.group(1);
      matcher.appendReplacement(sb, renderView(viewName));
    }
    matcher.appendTail(sb);

    snippet.append(sb);

    final Path newFile = this.config.outputDirectory().resolve("Components.adoc");
    try {
      // System.out.println(snippet.toString());
      Files.write(newFile, snippet.toString().getBytes());
    } catch (final IOException e) {
      throw new IllegalStateException("Components file couldn't be created", e);
    }

  }

  private String renderView(final String viewName) {
    final View view = this.doc.findView(viewName)
        .orElseThrow(() -> new IllegalStateException("Expected view not found: " + viewName));
    final StringWriter diagram = new StringWriter();
    this.plantUMLWriter.write(view, diagram);
    final StringBuilder snippet = new StringBuilder();
    snippet.append("[plantuml]\n");
    snippet.append("....\n");
    snippet.append(diagram);
    snippet.append("....\n");
    return snippet.toString();
  }

  private String buildTitle(final String containerName) {
    if (containerName == null) {
      return "Container";
    }
    return containerName + " Container";
  }

  private String newFileName(final String containerName) {
    if (containerName == null) {
      return "default-container.adoc";
    }
    return containerName.toLowerCase().replace(' ', '-') + "-container.adoc";
  }

  private static PlantUMLWriter newPlantUmlWriter() {
    final PlantUMLWriter plantUMLWriter = new PlantUMLWriter();

    // if you're using dark background colours, you might need to explicitly set the foreground colour using skin
    // params
    // e.g. rectangleFontColor, rectangleFontColor<<Software System>>, etc
    plantUMLWriter.addSkinParam("rectangleFontColor", "#ffffff");
    plantUMLWriter.addSkinParam("rectangleStereotypeFontColor", "#ffffff");

    // can set direction, default is "top to bottom direction"
    // plantUMLWriter.setDirection("left to right direction");
    return plantUMLWriter;
  }

}
