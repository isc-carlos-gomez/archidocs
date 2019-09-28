package com.krloxz.archidocs;

import java.nio.file.Path;
import java.util.Optional;

import org.immutables.value.Value;

/**
 * @author Carlos Gomez
 */
@Value.Immutable
public abstract class ContainerDescriptor {

  public abstract Optional<String> name();

  public abstract String namespace();

  public abstract Path sourcePath();

  public static ImmutableContainerDescriptor.Builder builder() {
    return ImmutableContainerDescriptor.builder();
  }

}
