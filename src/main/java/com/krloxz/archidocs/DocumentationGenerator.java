package com.krloxz.archidocs;

import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.structurizr.Workspace;
import com.structurizr.analysis.ReferencedTypesSupportingTypesStrategy;
import com.structurizr.analysis.SpringComponentFinderStrategy;
import com.structurizr.documentation.Format;
import com.structurizr.documentation.StructurizrDocumentationTemplate;
import com.structurizr.model.Container;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.model.Tags;
import com.structurizr.view.ComponentView;
import com.structurizr.view.Shape;
import com.structurizr.view.Styles;
import com.structurizr.view.ViewSet;

/**
 * @author Carlos Gomez
 */
public class DocumentationGenerator {

  /**
   * @param string
   * @param namespace
   * @throws Exception
   */
  // TODO: return a Snippet
  public Documentation documentComponents(final ArchidoctorConfig config) throws Exception {
    final Workspace workspace = new Workspace("Spring Boot PetClinic",
        "This is a C4 representation of the Spring Boot PetClinic sample app (https://github.com/spring-projects/spring-petclinic/)");
    // workspace.setVersion(VERSION);
    final Model model = workspace.getModel();

    // create the basic model (the stuff we can't get from the code)
    final SoftwareSystem system = model.addSoftwareSystem("Spring PetClinic",
        "Allows employees to view and manage information regarding the veterinarians, the clients, and their pets.");
    final Person clinicEmployee = model.addPerson("Clinic Employee", "An employee of the clinic");
    clinicEmployee.uses(system, "Uses");

    final Container defaultContainer = system.addContainer(
        "Web Application",
        "Allows employees to view and manage information regarding the veterinarians, the clients, and their pets.",
        "Apache Tomcat 7.x");
    final Container relationalDatabase = system.addContainer(
        "Relational Database", "Stores information regarding the veterinarians, the clients, and their pets.",
        "HSQLDB");
    clinicEmployee.uses(defaultContainer, "Uses", "HTTP");
    defaultContainer.uses(relationalDatabase, "Reads from and writes to", "JDBC, port 9001");

    final SpringComponentFinderStrategy springComponentFinderStrategy = new SpringComponentFinderStrategy(
        new ReferencedTypesSupportingTypesStrategy(false));
    springComponentFinderStrategy.setIncludePublicTypesOnly(false);

    // and now automatically find all Spring @Controller, @Component, @Service and @Repository components
    // final ComponentFinder componentFinder = new ComponentFinder(
    // defaultContainer,
    // config.namespace().get(),
    // springComponentFinderStrategy,
    // new SourceCodeComponentFinderStrategy(config.sourcePath().get().toFile(), 150));
    //
    // componentFinder.exclude(".*Formatter.*");
    // componentFinder.findComponents();

    // connect the user to all of the Spring MVC controllers
    defaultContainer.getComponents().stream()
        .filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_MVC_CONTROLLER))
        .forEach(c -> clinicEmployee.uses(c, "Uses", "HTTP"));

    // connect all of the repository components to the relational database
    defaultContainer.getComponents().stream()
        .filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_REPOSITORY))
        .forEach(c -> c.uses(relationalDatabase, "Reads from and writes to", "JDBC"));

    // finally create some views
    final ViewSet views = workspace.getViews();
    // SystemContextView contextView = views.createSystemContextView(springPetClinic, "context",
    // "The System Context diagram for the Spring PetClinic system.");
    // contextView.addAllSoftwareSystems();
    // contextView.addAllPeople();
    //
    // ContainerView containerView = views.createContainerView(springPetClinic, "containers",
    // "The Containers diagram for the Spring PetClinic system.");
    // containerView.addAllPeople();
    // containerView.addAllSoftwareSystems();
    // containerView.addAllContainers();

    final ComponentView componentView = views.createComponentView(defaultContainer, "Components1",
        "The Components diagram for the Spring PetClinic web application.");
    componentView.addAllComponents();
    componentView.addAllPeople();
    componentView.add(relationalDatabase);

    // link the architecture model with the code
    /*
     * for (Component component : webApplication.getComponents()) { for (CodeElement codeElement : component.getCode())
     * { String sourcePath = codeElement.getUrl(); if (sourcePath != null) { codeElement.setUrl(sourcePath.replace(
     * sourceRoot.toURI().toString(), "https://github.com/spring-projects/spring-petclinic/tree/" + VERSION + "/")); } }
     * }
     *
     * // rather than creating a component model for the database, let's simply link to the DDL // (this is really just
     * an example of linking an arbitrary element in the model to an external resource)
     * relationalDatabase.setUrl("https://github.com/spring-projects/spring-petclinic/tree/" + VERSION +
     * "/src/main/resources/db/hsqldb");
     */

    // tag and style some elements
    system.addTags("Spring PetClinic");
    defaultContainer.getComponents().stream()
        .filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_MVC_CONTROLLER))
        .forEach(c -> c.addTags("Spring MVC Controller"));
    defaultContainer.getComponents().stream()
        .filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_SERVICE))
        .forEach(c -> c.addTags("Spring Service"));
    defaultContainer.getComponents().stream()
        .filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_REPOSITORY))
        .forEach(c -> c.addTags("Spring Repository"));
    relationalDatabase.addTags("Database");

    // Component vetController = webApplication.getComponentWithName("VetController");
    // Component vetRepository = webApplication.getComponentWithName("VetRepository");
    //
    // DynamicView dynamicView = views.createDynamicView(webApplication, "viewListOfVets",
    // "Shows how the \"view list of vets\" feature works.");
    // dynamicView.add(clinicEmployee, "Requests list of vets from /vets", vetController);
    // dynamicView.add(vetController, "Calls findAll", vetRepository);
    // dynamicView.add(vetRepository, "select * from vets", relationalDatabase);

    final Styles styles = views.getConfiguration().getStyles();
    styles.addElementStyle("Spring PetClinic").background("#6CB33E").color("#ffffff");
    styles.addElementStyle(Tags.PERSON).background("#519823").color("#ffffff").shape(Shape.Person);
    styles.addElementStyle(Tags.CONTAINER).background("#91D366").color("#ffffff");
    styles.addElementStyle("Database").shape(Shape.Cylinder);
    styles.addElementStyle("Spring MVC Controller").background("#D4F3C0").color("#000000");
    styles.addElementStyle("Spring Service").background("#6CB33E").color("#000000");
    styles.addElementStyle("Spring Repository").background("#95D46C").color("#000000");

    // StructurizrClient structurizrClient = new StructurizrClient(API_KEY, API_SECRET);
    // structurizrClient.putWorkspace(WORKSPACE_ID, workspace);

    generateViews(config, workspace, defaultContainer);
    generateSections(config, workspace, defaultContainer);
    return new Documentation(workspace);
  }

  private void generateViews(final ArchidoctorConfig config, final Workspace workspace,
      final Container defaultContainer) {
    final ViewSet views = workspace.getViews();
    config.containers().forEach(container -> {
      final String key = StringUtils.deleteWhitespace(container.name().orElse("Components"));
      final String description = container.name()
          .map(name -> String.format("The Components diagram for the %s Container.", name))
          .orElse("The Components diagram.");
      final ComponentView view = views.createComponentView(defaultContainer, key, description);
      view.addAllComponents();
      view.addAllPeople();
    });
  }

  private void generateSections(final ArchidoctorConfig config, final Workspace workspace,
      final Container defaultContainer) {
    final String sectionContent = config.containers()
        .stream()
        .map(container -> generateSubsection(container.name()))
        .collect(Collectors.joining());

    final StructurizrDocumentationTemplate template = new StructurizrDocumentationTemplate(workspace);
    template.addComponentsSection(defaultContainer, Format.Markdown, sectionContent);
  }

  private static String generateSubsection(final Optional<String> subsectionName) {
    final String subTitle = subsectionName
        .map(name -> String.format("=== %s", name))
        .orElse(StringUtils.EMPTY);

    final String viewKey = StringUtils.deleteWhitespace(subsectionName.orElse("Components"));
    final String embeddedView = String.format("![](embed:%s)\n", viewKey);
    return subTitle + embeddedView;
  }

}
