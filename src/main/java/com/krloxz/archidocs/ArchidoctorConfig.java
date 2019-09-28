package com.krloxz.archidocs;

import java.nio.file.Path;
import java.util.Set;

import org.immutables.value.Value;

/**
 * @author Carlos Gomez
 */
@Value.Immutable
@Value.Modifiable
@Value.Style(typeModifiable = "Mutable*")
public interface ArchidoctorConfig {

  Path outputDirectory();

  Set<ContainerDescriptor> containers();

}