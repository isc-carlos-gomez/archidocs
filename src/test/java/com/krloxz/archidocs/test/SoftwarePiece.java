package com.krloxz.archidocs.test;

import io.cucumber.java.en.Given;

/**
 * @author Carlos Gomez
 */
public class SoftwarePiece {

  private String nameSpace;
  private String name;

  public String getNamespace() {
    return this.nameSpace;
  }

  public String getName() {
    return this.name;
  }

  @Given("a piece of software implementing a container")
  public void implementingContainer() {
    this.nameSpace = "com.krloxz.archidocs.test.petclinic";
  }

  @Given("the name {string} is assigned to the container")
  public void named(final String name) {
    this.name = name;
  }

}
