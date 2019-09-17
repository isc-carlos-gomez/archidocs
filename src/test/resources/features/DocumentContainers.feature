Feature: Document Containers
  As a developer,
  I want to automate documentation of containers
  so that documentation keeps current, accurate, reliable
    and useful to communicate software architecture

  Background:
    Given an output directory

  Scenario: Container Snippet File
    Given a piece of software implementing a container
    When documentation is generated
    Then the output directory has a snippet named "default-container.adoc"

  Scenario: Container Snippet Title
    Given a piece of software implementing a container
    When documentation is generated
    Then the output directory has a snippet titled "Container"

  Scenario: Container Snippet Diagram
    Given a piece of software implementing a container
    When documentation is generated
    Then the output directory has a snippet containing a "component" diagram

  Scenario: Named Container Snippet File
    Given a piece of software implementing a container
      And the name "Web Application" is assigned to the container
    When documentation is generated
    Then the output directory has a snippet named "web-application-container.adoc"

  Scenario: Named Container Snippet Title
    Given a piece of software implementing a container
      And the name "Web Application" is assigned to the container
    When documentation is generated
    Then the output directory has a snippet titled "Web Application Container"
