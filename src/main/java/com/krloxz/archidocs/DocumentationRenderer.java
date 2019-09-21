package com.krloxz.archidocs;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import com.structurizr.Workspace;
import com.structurizr.io.plantuml.PlantUMLWriter;

/**
 * @author Carlos Gomez
 */
public class DocumentationRenderer {

  public void render(final Workspace documentation, final ArchidoctorConfig config) {
    String diagram = null;
    try {
      PlantUMLWriter plantUMLWriter = new PlantUMLWriter();

      // if you're using dark background colours, you might need to explicitly set the foreground colour using skin
      // params
      // e.g. rectangleFontColor, rectangleFontColor<<Software System>>, etc
      plantUMLWriter.addSkinParam("rectangleFontColor", "#ffffff");
      plantUMLWriter.addSkinParam("rectangleStereotypeFontColor", "#ffffff");

      // can set direction, default is "top to bottom direction"
      // plantUMLWriter.setDirection("left to right direction");

      // write to a specific writer
      StringWriter stringWriter = new StringWriter();
      plantUMLWriter.write(documentation, stringWriter);

      diagram = stringWriter.toString();
    } catch (Exception e) {
      throw new IllegalStateException("Not able to generate the components diagram", e);
    }

    final StringBuilder snippet = new StringBuilder();
    snippet.append("== Components\n");
    snippet.append("[plantuml]\n");
    snippet.append("....\n");
    snippet.append(diagram);
    snippet.append("....\n");
    final Path newFile = config.outputDirectory().resolve("Components.adoc");
    try {
      // System.out.println(snippet.toString());
      Files.write(newFile, snippet.toString().getBytes());
    } catch (IOException e) {
      throw new IllegalStateException("Components file couldn't be created", e);
    }
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

}
