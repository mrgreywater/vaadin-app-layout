package com.github.appreciated.app.layout.component.router;

import com.github.appreciated.app.layout.component.applayout.AppLayout;
import com.github.appreciated.app.layout.design.ThemeHelper;
import com.github.appreciated.app.layout.navigation.UpNavigationHelper;
import com.github.appreciated.app.layout.session.UIAttributes;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.webcomponent.WebComponentConfigurationRegistry;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.ThemeDefinition;
import com.vaadin.flow.theme.ThemeUtil;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.Optional;

/**
 * Base class implementing router layout. Extending class is supposed to call {@link #init(AppLayout)} to initialize
 * the {@link AppLayout}. This can be extended directly if there is another parent layout defining a viewport.
 */
public class AppLayoutRouterLayoutBase<T extends AppLayout> extends Composite<Div> implements RouterLayout {
    private static final long serialVersionUID = 1L;

    private final ThemeHelper helper;
    private HasElement currentContent;
    private T layout;

    public AppLayoutRouterLayoutBase() {
        getContent().setSizeFull();
        getContent().getElement().getStyle().set("overflow", "auto");
        getContent().getElement().addAttachListener(attachEvent -> {

            UI.getCurrent();

        });
        // Workaround to hopefully fix the issues with Springboot.
        helper = new ThemeHelper();
    }

    public static <V extends AppLayoutRouterLayoutBase> V getCurrent(Class<V> mainAppLayoutClass) {
        return (V) UIAttributes.get(AppLayoutRouterLayoutBase.class);
    }

    @Override
    protected Div initContent() {
        return new Div();
    }

    public void init(T layout) {
        setLayout(layout);
        if (currentContent != null) {
            showRouterLayoutContent(currentContent);
        }
    }

    public void setLayout(T layout) {
        getContent().removeAll();
        this.layout = layout;
        getContent().add(layout);
        UIAttributes.set(AppLayout.class, layout);
        UIAttributes.set(AppLayoutRouterLayoutBase.class, this);
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        currentContent = content;
        if (layout == null) {
            throw new IllegalStateException("init has AppLayoutRouterLayoutBase::init has not yet been called");
        }
        layout.setAppLayoutContent(content);
        if (layout.isUpNavigationEnabled()) {
            setUpNavigation(content);
        }
        layout.setPercentageHeight(
                currentContent.getElement().getStyle().get("height") != null &&
                        currentContent.getElement().getStyle().get("height").endsWith("%")
        );
    }

    private void setUpNavigation(HasElement content) {
        if (content instanceof Component) {
            layout.showUpNavigation(UpNavigationHelper.routeHasUpNavigation(((Component) content).getClass()));
        } else {
            layout.showUpNavigation(false);
        }
    }

    private Theme getApplicationTheme() {
        // todo
        return null;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        attachEvent.getUI().addAfterNavigationListener(event -> {
            UI.getCurrent();
        });
        Theme theme = getApplicationTheme();
        if (theme != null && theme.value() != Lumo.class) {
            attachEvent.getUI().getPage().addStyleSheet("./frontend/com/github/appreciated/app-layout/app-layout-styles-material.css");
        } else {
            attachEvent.getUI().getPage().addStyleSheet("./frontend/com/github/appreciated/app-layout/app-layout-styles-lumo.css");
        }
        getUI().ifPresent(ui -> ui.addAfterNavigationListener(event -> {
            closeDrawerIfNotPersistent();
        }));
    }

    public void closeDrawerIfNotPersistent() {
        layout.closeDrawerIfNotPersistent();
    }

    public void closeDrawer() {
        layout.closeDrawer();
    }

    public void toggleDrawer() {
        layout.toggleDrawer();
    }

    public void openDrawer() {
        layout.openDrawer();
    }

    public T getAppLayout() {
        return layout;
    }

    public final AppLayout createAppLayoutInstance() {
        return null;
    }
}
