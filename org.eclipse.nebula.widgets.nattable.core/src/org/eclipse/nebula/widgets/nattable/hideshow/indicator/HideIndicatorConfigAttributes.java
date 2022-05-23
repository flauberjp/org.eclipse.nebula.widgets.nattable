/*****************************************************************************
 * Copyright (c) 2018, 2020 Dirk Fauth.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Dirk Fauth <dirk.fauth@googlemail.com> - Initial API and implementation
 *****************************************************************************/
package org.eclipse.nebula.widgets.nattable.hideshow.indicator;

import org.eclipse.nebula.widgets.nattable.style.ConfigAttribute;
import org.eclipse.swt.graphics.Color;

/**
 * Configuration attributes for the hide indicator rendering.
 *
 * @see HideIndicatorOverlayPainter
 *
 * @since 1.6
 */
public final class HideIndicatorConfigAttributes {

    /**
     * Configuration attribute for configuring the line width of the hide
     * indicator.
     */
    public static final ConfigAttribute<Integer> HIDE_INDICATOR_LINE_WIDTH = new ConfigAttribute<>();

    /**
     * Configuration attribute for configuring the color of the hide indicator.
     */
    public static final ConfigAttribute<Color> HIDE_INDICATOR_COLOR = new ConfigAttribute<>();

    private HideIndicatorConfigAttributes() {
        // empty constructor for constants class
    }
}
