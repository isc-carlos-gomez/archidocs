package com.krloxz.archidocs;

/**
 * @author Carlos Gomez
 */
public class Archidoctor {

  /**
   * @param config
   */
  public void document(final ArchidoctorConfig config) {
    try {
      final Documentation documentation = new DocumentationGenerator().documentComponents(config);
      new DocumentationRenderer().render(documentation, config);
    } catch (Exception e) {
      throw new IllegalStateException("Not able to generate documentation: " + config, e);
    }
  }

}
