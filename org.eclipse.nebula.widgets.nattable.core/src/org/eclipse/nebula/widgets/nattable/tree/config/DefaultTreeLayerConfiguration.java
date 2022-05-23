/*******************************************************************************
 * Copyright (c) Sep 7, 2012, 2020 Edwin Park and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Edwin Park - initial API and implementation
 *******************************************************************************/
package org.eclipse.nebula.widgets.nattable.tree.config;

import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IConfiguration;
import org.eclipse.nebula.widgets.nattable.export.ExportConfigAttributes;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.HorizontalAlignmentEnum;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.tree.TreeLayer;
import org.eclipse.nebula.widgets.nattable.tree.action.TreeExpandCollapseAction;
import org.eclipse.nebula.widgets.nattable.tree.painter.TreeImagePainter;
import org.eclipse.nebula.widgets.nattable.ui.action.NoOpMouseAction;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.matcher.CellPainterMouseEventMatcher;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;

/**
 * Default configuration for {@link TreeLayer}.
 */
public class DefaultTreeLayerConfiguration implements IConfiguration {

    public static final String TREE_COLLAPSED_CONFIG_TYPE = "TREE_COLLAPSED"; //$NON-NLS-1$
    public static final String TREE_EXPANDED_CONFIG_TYPE = "TREE_EXPANDED"; //$NON-NLS-1$
    public static final String TREE_LEAF_CONFIG_TYPE = "TREE_LEAF"; //$NON-NLS-1$
    public static final String TREE_DEPTH_CONFIG_TYPE = "TREE_DEPTH_"; //$NON-NLS-1$

    private TreeLayer treeLayer;

    public DefaultTreeLayerConfiguration(TreeLayer treeLayer) {
        this.treeLayer = treeLayer;
    }

    @Override
    public void configureLayer(ILayer layer) {
        // no layer configuration needed here
    }

    @Override
    public void configureRegistry(IConfigRegistry configRegistry) {
        Style style = new Style();
        style.setAttributeValue(
                CellStyleAttributes.HORIZONTAL_ALIGNMENT,
                HorizontalAlignmentEnum.LEFT);
        configRegistry.registerConfigAttribute(
                CellConfigAttributes.CELL_STYLE,
                style,
                DisplayMode.NORMAL,
                TreeLayer.TREE_COLUMN_CELL);
        configRegistry.registerConfigAttribute(
                ExportConfigAttributes.EXPORT_FORMATTER,
                new TreeExportFormatter(this.treeLayer.getModel()),
                DisplayMode.NORMAL,
                TreeLayer.TREE_COLUMN_CELL);
    }

    @Override
    public void configureUiBindings(UiBindingRegistry uiBindingRegistry) {
        TreeExpandCollapseAction treeExpandCollapseAction = new TreeExpandCollapseAction();
        CellPainterMouseEventMatcher treeImagePainterMouseEventMatcher =
                new CellPainterMouseEventMatcher(
                        GridRegion.BODY,
                        MouseEventMatcher.LEFT_BUTTON,
                        TreeImagePainter.class);

        uiBindingRegistry.registerFirstSingleClickBinding(
                treeImagePainterMouseEventMatcher,
                treeExpandCollapseAction);

        // Obscure any mouse down bindings for this image painter
        uiBindingRegistry.registerFirstMouseDownBinding(
                treeImagePainterMouseEventMatcher,
                new NoOpMouseAction());
    }

}
