package com.github.appreciated.app.layout.component.applayout;

import com.github.appreciated.app.layout.component.applayout.LeftLayouts.*;

import java.lang.reflect.InvocationTargetException;

/**
 * Contains all the available Behaviours and their
 * specific details {@link Behaviour#Behaviour(Class, boolean, boolean, boolean, boolean)}
 */
public enum Behaviour {
    LEFT(Left.class, false, true, false, false),
    LEFT_RESPONSIVE(LeftResponsive.class, false, true, false, false),
    LEFT_RESPONSIVE_DOUBLE(LeftResponsiveDouble.class, false, true, false, false),
    LEFT_HYBRID(LeftHybrid.class, false, true, false, false),
    LEFT_HYBRID_SMALL(LeftHybridSmall.class, false, true, false, true),
    LEFT_RESPONSIVE_HYBRID(LeftResponsiveHybrid.class, false, true, false, false),
    LEFT_RESPONSIVE_HYBRID_NO_APP_BAR(LeftResponsiveHybridNoAppBar.class, false, false, false, false),
    LEFT_RESPONSIVE_HYBRID_OVERLAY_NO_APP_BAR(LeftResponsiveHybridOverlayNoAppBar.class, false, false, false, false),
    LEFT_OVERLAY(LeftOverlay.class, false, true, true, false),
    LEFT_RESPONSIVE_OVERLAY(LeftResponsiveOverlay.class, false, true, true, false),
    LEFT_RESPONSIVE_OVERLAY_NO_APP_BAR(LeftResponsiveOverlayNoAppBar.class, false, false, true, false),
    LEFT_RESPONSIVE_SMALL(LeftResponsiveSmall.class, false, true, false, true),
    LEFT_RESPONSIVE_SMALL_NO_APP_BAR(LeftResponsiveSmallNoAppBar.class, false, false, false, true),
    TOP(TopLayouts.Top.class, true, true, false, false),
    TOP_LARGE(TopLayouts.TopLarge.class, true, true, false, false);

    private Class<? extends AppLayout> className;

    private boolean hasAppBar;
    private boolean overlay;
    private boolean small;
    private boolean top;

    Behaviour(Class<? extends AppLayout> className, boolean top, boolean hasAppBar, boolean overlay, boolean small) {
        this.className = className;
        this.top = top;
        this.hasAppBar = hasAppBar;
        this.overlay = overlay;
        this.small = small;
    }

    public AppLayout getInstance() {
        try {
            return className.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean hasAppBar() {
        return hasAppBar;
    }

    public boolean isOverlay() {
        return overlay;
    }

    public boolean isSmall() {
        return small;
    }

    public boolean isTop() {
        return top;
    }
}
