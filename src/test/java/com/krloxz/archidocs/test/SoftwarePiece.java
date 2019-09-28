package com.krloxz.archidocs.test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.krloxz.archidocs.ContainerDescriptor;
import com.krloxz.archidocs.MutableArchidoctorConfig;

import io.cucumber.java.en.Given;

/**
 * Encapsulates a piece of software that will be documented by Archidoctor.
 * 
 * @author Carlos Gomez
 */
public class SoftwarePiece {

  private static final Map<String, String> NAMESPACES = ImmutableMap.of(
      "Web Application", "com.krloxz.archidocs.test.system.web",
      "REST Services", "com.krloxz.archidocs.test.system.rest");
  private static final Path DEFAULT_SOURCE_PATH = Paths.get(
      "/Users/Carlos/Projects/Archidocs/repos/archidocs/src/test/java");
  private final Set<ContainerDescriptor> containers = Sets.newHashSet();

  @Given("a piece of software implementing a container")
  public void implementingContainer() {
    this.containers.add(
        ContainerDescriptor.builder()
            .namespace("com.krloxz.archidocs.test.petclinic")
            .sourcePath(DEFAULT_SOURCE_PATH)
            .build());
  }

  @Given("a piece of software implementing two containers")
  public void implementingTwoContainers() {
    // TODO: setup properties for system documentation and merge with #implementingConatiner()
  }

  @Given("one container is named {string}")
  public void containerNamed(final String name) {
    this.containers.add(
        ContainerDescriptor.builder()
            .name(name)
            .namespace(NAMESPACES.get(name))
            .sourcePath(DEFAULT_SOURCE_PATH)
            .build());
  }

  public MutableArchidoctorConfig enrichConfig(final MutableArchidoctorConfig config) {
    return config.addAllContainers(this.containers);
  }

}
