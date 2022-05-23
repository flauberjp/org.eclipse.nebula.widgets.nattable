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
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.ui.NatEventData;
import org.eclipse.swt.events.MouseEvent;

/**
 * Matches mouse clicks on cells to which a specified configuration label has
 * been applied.
 */
public class CellLabelMouseEventMatcher extends MouseEventMatcher {

    private final String labelToMatch;

    public CellLabelMouseEventMatcher(String regionName, int button, String labelToMatch) {
        super(regionName, button);
        this.labelToMatch = labelToMatch;
    }

    public CellLabelMouseEventMatcher(int stateMask, String regionName, int button, String labelToMatch) {
        super(stateMask, regionName, button);
        this.labelToMatch = labelToMatch;
    }

    @Override
    public boolean matches(NatTable natTable, MouseEvent event, LabelStack regionLabels) {
        NatEventData eventData = NatEventData.createInstanceFromEvent(event);
        LabelStack customLabels = natTable.getConfigLabelsByPosition(
                eventData.getColumnPosition(), eventData.getRowPosition());

        return super.matches(natTable, event, regionLabels)
                && customLabels.contains(this.labelToMatch);
    }

}
