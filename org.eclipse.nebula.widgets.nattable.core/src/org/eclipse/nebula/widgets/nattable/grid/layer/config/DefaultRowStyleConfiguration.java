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
package org.eclipse.nebula.widgets.nattable.grid.layer.config;

import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.grid.cell.AlternatingRowConfigLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.graphics.Color;

/**
 * Sets up alternate row coloring. Applied by
 * {@link DefaultGridLayerConfiguration}
 */
// fields are public by design to make it easy for adapters to customize styling
@SuppressWarnings("java:S1104")
public class DefaultRowStyleConfiguration extends AbstractRegistryConfiguration {

    public Color evenRowBgColor = GUIHelper.COLOR_WIDGET_BACKGROUND;
    public Color oddRowBgColor = GUIHelper.COLOR_WHITE;

    @Override
    public void configureRegistry(IConfigRegistry configRegistry) {
        configureOddRowStyle(configRegistry);
        configureEvenRowStyle(configRegistry);
    }

    protected void configureOddRowStyle(IConfigRegistry configRegistry) {
        Style cellStyle = new Style();
        cellStyle.setAttributeValue(
                CellStyleAttributes.BACKGROUND_COLOR,
                this.oddRowBgColor);
        configRegistry.registerConfigAttribute(
                CellConfigAttributes.CELL_STYLE,
                cellStyle,
                DisplayMode.NORMAL,
                AlternatingRowConfigLabelAccumulator.EVEN_ROW_CONFIG_TYPE);
    }

    protected void configureEvenRowStyle(IConfigRegistry configRegistry) {
        Style cellStyle = new Style();
        cellStyle.setAttributeValue(
                CellStyleAttributes.BACKGROUND_COLOR,
                this.evenRowBgColor);
        configRegistry.registerConfigAttribute(
                CellConfigAttributes.CELL_STYLE,
                cellStyle,
                DisplayMode.NORMAL,
                AlternatingRowConfigLabelAccumulator.ODD_ROW_CONFIG_TYPE);
    }
}
