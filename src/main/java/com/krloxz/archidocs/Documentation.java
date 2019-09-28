package com.krloxz.archidocs;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

import com.structurizr.Workspace;
import com.structurizr.documentation.Section;
import com.structurizr.view.View;
import com.structurizr.view.ViewSet;

/**
 * @author Carlos Gomez
 */
public class Documentation {

  private final Workspace workspace;

  public Documentation(final Workspace workspace) {
    this.workspace = workspace;
  }

  public Set<Section> sections() {
    return this.workspace.getDocumentation().getSections();
  }

  @Deprecated
  protected Workspace getWorkspace() {
    return this.workspace;
  }

  /**
   * @param viewName
   * @return
   */
  public Optional<View> findView(final String viewName) {
    try {
      Method getViewWithKeyMethod = ViewSet.class.getDeclaredMethod("getViewWithKey", String.class);
      getViewWithKeyMethod.setAccessible(true);
      return Optional.ofNullable(
        (View) getViewWithKeyMethod.invoke(this.workspace.getViews(), viewName));
    } catch (Exception e) {
      throw new IllegalStateException("Not able to find the view: " + viewName, e);
    }
  }

}
