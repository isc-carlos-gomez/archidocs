Feature: Document Containers
  As a developer,
  I want to automate the generation of component diagrams
  so that these diagrams are used to document container architectures
  
  Scenario: Spring Components
    Given an application ready to be documented
    And an output directory
    When the application is documented
    Then the output directory has a components snippet
