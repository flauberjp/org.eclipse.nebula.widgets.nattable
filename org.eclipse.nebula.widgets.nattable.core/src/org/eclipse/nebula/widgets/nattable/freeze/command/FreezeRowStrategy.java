/*******************************************************************************
 * Copyright (c) 2013, 2020 Dirk Fauth and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Dirk Fauth <dirk.fauth@googlemail.com> - initial API and implementation
 *******************************************************************************/
package org.eclipse.nebula.widgets.nattable.freeze.command;

import org.eclipse.nebula.widgets.nattable.coordinate.PositionCoordinate;
import org.eclipse.nebula.widgets.nattable.freeze.FreezeLayer;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;

public class FreezeRowStrategy implements IFreezeCoordinatesProvider {

    private final FreezeLayer freezeLayer;
    private final ViewportLayer viewportLayer;

    private final int rowPosition;

    public FreezeRowStrategy(FreezeLayer freezeLayer, int rowPosition) {
        this(freezeLayer, null, rowPosition);
    }

    /**
     * @param freezeLayer
     *            The {@link FreezeLayer} for the {@link PositionCoordinate}.
     * @param viewportLayer
     *            The {@link ViewportLayer} needed to calculate the viewport
     *            relative position.
     * @param rowPosition
     *            The row position based on the CompositeFreezeLayer.
     * @since 1.5
     */
    public FreezeRowStrategy(FreezeLayer freezeLayer, ViewportLayer viewportLayer, int rowPosition) {
        this.freezeLayer = freezeLayer;
        this.viewportLayer = viewportLayer;
        this.rowPosition = rowPosition;
    }

    @Override
    public PositionCoordinate getTopLeftPosition() {
        int rowPosition = 0;
        if (this.viewportLayer != null) {
            rowPosition = this.viewportLayer.getScrollableLayer().getRowPositionByY(this.viewportLayer.getOrigin().getY());
            if (rowPosition > 0 && rowPosition >= this.rowPosition) {
                rowPosition = this.rowPosition;
            }
        }
        return new PositionCoordinate(this.freezeLayer, -1, rowPosition);
    }

    @Override
    public PositionCoordinate getBottomRightPosition() {
        return new PositionCoordinate(this.freezeLayer, -1, this.rowPosition);
    }

}
