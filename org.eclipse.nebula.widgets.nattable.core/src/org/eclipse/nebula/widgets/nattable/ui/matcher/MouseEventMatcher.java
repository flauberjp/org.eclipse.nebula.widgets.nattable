/*******************************************************************************
 * Copyright (c) 2012, 2020 Original authors and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Original authors and others - initial API and implementation
 ******************************************************************************/
package org.eclipse.nebula.widgets.nattable.ui.matcher;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.swt.events.MouseEvent;

public class MouseEventMatcher implements IMouseEventMatcher {

    public static final int LEFT_BUTTON = 1;
    public static final int RIGHT_BUTTON = 3;

    private final int stateMask;
    private final String regionName;
    private final int button;

    public MouseEventMatcher() {
        this(0, null, 0);
    }

    public MouseEventMatcher(String eventRegionName) {
        this(0, eventRegionName, 0);
    }

    public MouseEventMatcher(String eventRegion, int button) {
        this(0, eventRegion, button);
    }

    public MouseEventMatcher(int stateMask, String eventRegion) {
        this(stateMask, eventRegion, 0);
    }

    /**
     * Constructor
     *
     * @param stateMask
     *            the state of the keyboard modifier keys and mouse masks at the
     *            time the event was generated.
     * @param eventRegion
     *            the grid region in which the mouse event should be matched
     * @param button
     *            the button that was pressed or released, e.g.
     *            {@link MouseEventMatcher#LEFT_BUTTON},
     *            {@link MouseEventMatcher#RIGHT_BUTTON}
     *
     * @see org.eclipse.swt.events.MouseEvent#stateMask
     * @see org.eclipse.nebula.widgets.nattable.grid.GridRegion
     * @see org.eclipse.swt.events.MouseEvent#button
     */
    public MouseEventMatcher(int stateMask, String eventRegion, int button) {
        this.stateMask = stateMask;
        this.regionName = eventRegion;
        this.button = button;
    }

    @Override
    public boolean matches(NatTable natTable, MouseEvent event,
            LabelStack regionLabels) {
        if (regionLabels == null) {
            return false;
        }

        boolean stateMaskMatches;
        if (this.stateMask != 0) {
            stateMaskMatches = (event.stateMask == this.stateMask) ? true : false;
        } else {
            stateMaskMatches = event.stateMask == 0;
        }

        boolean eventRegionMatches;
        if (this.regionName != null) {
            eventRegionMatches = regionLabels.hasLabel(this.regionName);
        } else {
            eventRegionMatches = true;
        }

        boolean buttonMatches = this.button != 0 ? (this.button == event.button) : true;

        return stateMaskMatches && eventRegionMatches && buttonMatches;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MouseEventMatcher other = (MouseEventMatcher) obj;
        if (this.button != other.button)
            return false;
        if (this.regionName == null) {
            if (other.regionName != null)
                return false;
        } else if (!this.regionName.equals(other.regionName))
            return false;
        if (this.stateMask != other.stateMask)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.button;
        result = prime * result + ((this.regionName == null) ? 0 : this.regionName.hashCode());
        result = prime * result + this.stateMask;
        return result;
    }

    public int getStateMask() {
        return this.stateMask;
    }

    public String getEventRegion() {
        return this.regionName;
    }

    public int getButton() {
        return this.button;
    }

    public static MouseEventMatcher columnHeaderLeftClick(int mask) {
        return new MouseEventMatcher(mask, GridRegion.COLUMN_HEADER, LEFT_BUTTON);
    }

    public static MouseEventMatcher columnHeaderRightClick(int mask) {
        return new MouseEventMatcher(mask, GridRegion.COLUMN_HEADER, RIGHT_BUTTON);
    }

    public static MouseEventMatcher rowHeaderLeftClick(int mask) {
        return new MouseEventMatcher(mask, GridRegion.ROW_HEADER, LEFT_BUTTON);
    }

    public static MouseEventMatcher rowHeaderRightClick(int mask) {
        return new MouseEventMatcher(mask, GridRegion.ROW_HEADER, RIGHT_BUTTON);
    }

    public static MouseEventMatcher bodyLeftClick(int mask) {
        return new MouseEventMatcher(mask, GridRegion.BODY, LEFT_BUTTON);
    }

    public static MouseEventMatcher bodyRightClick(int mask) {
        return new MouseEventMatcher(mask, GridRegion.BODY, RIGHT_BUTTON);
    }

    public static MouseEventMatcher columnGroupHeaderLeftClick(int mask) {
        return new MouseEventMatcher(mask, GridRegion.COLUMN_GROUP_HEADER, LEFT_BUTTON);
    }

    public static MouseEventMatcher columnGroupHeaderRightClick(int mask) {
        return new MouseEventMatcher(mask, GridRegion.COLUMN_GROUP_HEADER, RIGHT_BUTTON);
    }

    public static MouseEventMatcher rowGroupHeaderLeftClick(int mask) {
        return new MouseEventMatcher(mask, GridRegion.ROW_GROUP_HEADER, LEFT_BUTTON);
    }

    public static MouseEventMatcher rowGroupHeaderRightClick(int mask) {
        return new MouseEventMatcher(mask, GridRegion.ROW_GROUP_HEADER, RIGHT_BUTTON);
    }
}
