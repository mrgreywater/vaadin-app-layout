package com.github.appreciated.app.layout.component.appbar;

import com.github.appreciated.app.layout.webcomponents.paperbadge.PaperBadge;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;

/**
 * A borderless button component which shows an indicator how many new notifications are available in the connected Notification holder.
 */
public class IconBadgeButton extends Div {

    private static int idCounter = 0;

    private final Button button;
    private final PaperBadge badge;

    public IconBadgeButton(VaadinIcon icon) {
        this(icon, null);
    }

    public IconBadgeButton(VaadinIcon icon, ComponentEventListener<ClickEvent<Button>> listener) {
        setId("menu-btn-" + idCounter++);
        setWidth("var(--app-layout-menu-button-height)");
        setHeight("var(--app-layout-menu-button-height)");
        button = new Button(icon.create());
        button.getElement().getStyle()
                .set("width", "100%")
                .set("height", "100%");
        getElement().setAttribute("id", "button" + idCounter++);
        badge = new PaperBadge(this, "0");
        badge.getElement().getStyle()
                .set("margin-top", "6px")
                .set("margin-left", "-6px");

        add(button);
        add(badge);
        if (listener != null) {
            button.addClickListener(listener);
        }
    }

    public void setBadgeCaption(String caption) {
        badge.setText(caption);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
    }

    public Button getButton() {
        return button;
    }

    public HasText getBadge() {
        return badge;
    }
}


