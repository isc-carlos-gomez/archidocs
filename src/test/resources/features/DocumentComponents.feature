Feature: Document Components
  As a developer,
  I want to automate documentation of Components
  so that architecture documentation can be quickly updated

  Scenario: Components Section
    Given a piece of software implementing a container
    When documentation is generated
    Then the documentation has a "Components" section

  Scenario: Title Components Section
    Given a piece of software implementing a container
    When the "Components" section is generated
    Then the section is titled "Components"

  Scenario: Embed Components View
    Given a piece of software implementing a container
    When the "Components" section is generated
    Then the section has a "Components" view embedded

#  Scenario: Components Sub-sections
#    Given a piece of software implementing two containers
#      And one container is named "Web Application"
#      And one container is named "REST Services"
#    When the Components section is generated
#    Then the section has the subtitle "Web Application"
#      And the section has the subtitle "REST Services"
#
#  Scenario: Embed Component Views into Sub-sections
#    Given a piece of software implementing two containers
#      And one container is named "Web Application"
#      And one container is named "REST Services"
#    When the Components section is generated
#    Then the section has a "Web Application" Components view embedded
#      And the section has a "REST Services" Components view embedded
