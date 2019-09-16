package com.krloxz.archidocs.test;

import io.cucumber.java.en.Given;

/**
 * @author Carlos Gomez
 */
public class FakeApplication {

  private String nameSpace;

  /**
   * @return
   */
  @Given("an application ready to be documented")
  public Void readyToDocument() {
    this.nameSpace = "com.krloxz.archidocs.test.petclinic";
    return null;
  }

  /**
   * @return
   */
  public String getNamespace() {
    return this.nameSpace;
  }

}
