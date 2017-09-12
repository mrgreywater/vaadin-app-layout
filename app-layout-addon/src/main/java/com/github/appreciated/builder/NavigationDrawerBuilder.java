package com.github.appreciated.builder;

import com.github.appreciated.builder.elements.AbstractNavigationElement;
import com.github.appreciated.builder.elements.CustomNavigationElement;
import com.github.appreciated.builder.elements.NavigatorNavigationElement;
import com.github.appreciated.builder.elements.SectionNavigationElement;
import com.github.appreciated.layout.drawer.AbstractNavigationDrawer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerBuilder {

    DrawerVariant variant = DrawerVariant.LEFT;
    List<AbstractNavigationElement> navigationElements = new ArrayList<>();
    List<Component> appBarElements = new ArrayList<>();
    private boolean requiresNavigatior = false;
    private Navigator navigator;
    private String title;
    private AbstractNavigationDrawer instance = null;
    private NavigatorNavigationElement defaultNavigationElement;

    private NavigationDrawerBuilder() {
    }

    public static NavigationDrawerBuilder get() {
        return new NavigationDrawerBuilder();
    }

    public NavigationDrawerBuilder withVariant(DrawerVariant variant) {
        this.variant = variant;
        return this;
    }

    /**
     * This will cause parameters set with "withVariant(...)" to be ignored
     *
     * @param variant
     * @return
     */
    public NavigationDrawerBuilder withCustomVariant(AbstractNavigationDrawer variant) {
        this.instance = variant;
        return this;
    }

    public NavigationDrawerBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public NavigationDrawerBuilder withNavigator() {
        this.requiresNavigatior = true;
        return this;
    }

    /**
     * If you use this you will need to do the "navigator part" manually, this will simply add a component to the menu
     *
     * @param element
     * @return
     */
    public NavigationDrawerBuilder withNavigationElement(Component element) {
        this.navigationElements.add(new CustomNavigationElement(element));
        return this;
    }

    public NavigationDrawerBuilder withDefaultNavigationView(View element) {
        requiresNavigatior = true;
        defaultNavigationElement = new NavigatorNavigationElement("", null, element);
        return this;
    }

    public NavigationDrawerBuilder withDefaultNavigationView(Class<? extends View> element) {
        requiresNavigatior = true;
        defaultNavigationElement = new NavigatorNavigationElement("", null, element);
        return this;
    }


    public NavigationDrawerBuilder withSection(String name) {
        this.navigationElements.add(new SectionNavigationElement(name));
        return this;
    }

    public NavigationDrawerBuilder withNavigationElement(String view, Class<? extends View> element) {
        return withNavigationElement(view, null, element);
    }

    public NavigationDrawerBuilder withNavigationElement(String view, View element) {
        return withNavigationElement(view, null, element);
    }

    public NavigationDrawerBuilder withNavigationElement(String view, Resource icon, Class<? extends View> element) {
        requiresNavigatior = true;
        this.navigationElements.add(new NavigatorNavigationElement(view, icon, element));
        return this;
    }

    public NavigationDrawerBuilder withNavigationElement(String view, Resource icon, View element) {
        this.navigationElements.add(new NavigatorNavigationElement(view, icon, element));
        return this;
    }

    public NavigationDrawerBuilder withAppBarElement(Component element) {
        this.appBarElements.add(element);
        return this;
    }

    public AbstractNavigationDrawer build() {
        if (instance == null) {
            instance = variant.getInstance();
        }
        instance.setTitle(title);
        if (requiresNavigatior) {
            navigator = new Navigator(UI.getCurrent(), instance.getContentHolder());
            if (defaultNavigationElement == null) {
                defaultNavigationElement = navigationElements.stream()
                        .filter(element -> element instanceof NavigatorNavigationElement)
                        .map(element -> ((NavigatorNavigationElement) element)).findFirst().orElse(null);
            }
            defaultNavigationElement.addViewToNavigator(navigator);
            navigationElements.forEach(element -> {
                addViewComponent(element);
                if (element instanceof NavigatorNavigationElement) {
                    ((NavigatorNavigationElement) element).addViewToNavigator(navigator);
                }
            });
        }
        appBarElements.forEach(instance::addAppBarElement);
        return instance;
    }

    private void addViewComponent(AbstractNavigationElement element) {
        instance.addNavigationElement(element.getComponent());
    }

}