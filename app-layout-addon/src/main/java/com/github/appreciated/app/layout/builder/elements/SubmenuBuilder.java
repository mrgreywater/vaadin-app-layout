package com.github.appreciated.app.layout.builder.elements;

import com.github.appreciated.app.layout.builder.entities.DefaultBadgeHolder;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import java.util.ArrayList;
import java.util.List;

public class SubmenuBuilder {

    private final String title;
    private final Resource resource;
    private List<AbstractNavigationElement> submenuElements = new ArrayList<>();

    public SubmenuBuilder(String title, Resource resource) {
        this.title = title;
        this.resource = resource;
    }

    public static SubmenuBuilder get(String title) {
        return new SubmenuBuilder(title, null);
    }

    public static SubmenuBuilder get(Resource icon) {
        return new SubmenuBuilder(null, icon);
    }

    public static SubmenuBuilder get(String title, Resource icon) {
        return new SubmenuBuilder(title, icon);
    }

    public SubmenuBuilder withNavigationElement(String caption) {
        return this.withNavigationElement(caption, (Resource) null);
    }


    public SubmenuBuilder withNavigationElement(String caption, Resource icon) {
        this.submenuElements.add(new NavigatorNavigationElement(caption, icon, (View) null));
        return this;
    }

    public SubmenuBuilder withNavigationElement(String caption, Resource icon, DefaultBadgeHolder badgeHolder) {
        this.submenuElements.add(new NavigatorNavigationElement(caption, icon, badgeHolder, (View) null));
        return this;
    }

    public SubmenuBuilder withNavigationElement(String caption, View element) {
        return this.withNavigationElement(caption, null, element);
    }

    public SubmenuBuilder withNavigationElement(String caption, Resource icon, View element) {
        this.submenuElements.add(new NavigatorNavigationElement(caption, icon, element));
        return this;
    }

    public SubmenuBuilder withNavigationElement(String caption, Resource icon, DefaultBadgeHolder badgeHolder, View element) {
        this.submenuElements.add(new NavigatorNavigationElement(caption, icon, badgeHolder, element));
        return this;
    }


    public SubmenuBuilder withNavigationElement(String caption, Class<? extends View> element) {
        return this.withNavigationElement(caption, null, element);
    }

    public SubmenuBuilder withNavigationElement(String caption, Resource icon, Class<? extends View> element) {
        this.submenuElements.add(new NavigatorNavigationElement(caption, icon, element));
        return this;
    }

    public SubmenuBuilder withNavigationElement(String caption, Resource icon, DefaultBadgeHolder badgeHolder, Class<? extends View> element) {
        this.submenuElements.add(new NavigatorNavigationElement(caption, icon, badgeHolder, element));
        return this;
    }

    public SubmenuBuilder withClickableElement(Resource icon, Button.ClickListener listener) {
        return withClickableElement(null, icon, listener);
    }

    public SubmenuBuilder withClickableElement(String caption, Button.ClickListener listener) {
        return withClickableElement(caption, null, listener);
    }

    public SubmenuBuilder withClickableElement(String caption, Resource icon, Button.ClickListener listener) {
        this.submenuElements.add(new CustomNavigatorNavigationElement(caption, icon, listener));
        return this;
    }

    public SubmenuBuilder withNavigationElement(Component element) {
        this.submenuElements.add(new CustomNavigationElement(element));
        return this;
    }

    public SubmenuNavigationElement build() {
        return new SubmenuNavigationElement(title, resource, submenuElements);
    }
}
