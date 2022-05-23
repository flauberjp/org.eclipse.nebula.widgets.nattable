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
package org.eclipse.nebula.widgets.nattable.filterrow.combobox;

import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.edit.editor.IComboBoxDataProvider;
import org.eclipse.nebula.widgets.nattable.filterrow.TextMatchingMode;
import org.eclipse.nebula.widgets.nattable.filterrow.config.FilterRowConfigAttributes;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.ImagePainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.CellPainterDecorator;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.ui.util.CellEdgeEnum;
import org.eclipse.swt.graphics.GC;

/**
 * Special filter row configuration that configures the
 * FilterRowComboBoxCellEditor as editor for the filter row with its necessary
 * configurations regarding matching mode, converter and painter.
 */
public class ComboBoxFilterRowConfiguration extends AbstractRegistryConfiguration {

    /**
     * The ICellEditor that should be used for the filter cells. Usually it
     * should be the FilterRowComboBoxCellEditor.
     */
    protected ICellEditor cellEditor;

    /**
     * The ImagePainter that will be registered as the painter of the combobox
     * cells in the filter row.
     */
    protected ImagePainter filterIconPainter;

    /**
     * The empty default constructor needed for specialising.
     * <p>
     * <b>Note: On using this constructor you need to ensure that the local
     * member variables for <i>cellEditor</i> and <i>filterIconPainter</i> need
     * to be set manually. Otherwise this configuration will not work
     * correctly!</b>
     */
    public ComboBoxFilterRowConfiguration() {
    }

    /**
     * Create a ComboBoxFilterRowConfiguration that uses the default
     * FilterRowComboBoxCellEditor showing the maximum number of 10 items at
     * once and the ComboBoxFilterIconPainter with the default filter icon.
     *
     * @param comboBoxDataProvider
     *            The IComboBoxDataProvider that is used to fill the filter row
     *            comboboxes.
     */
    public ComboBoxFilterRowConfiguration(IComboBoxDataProvider comboBoxDataProvider) {
        this.cellEditor = new FilterRowComboBoxCellEditor(comboBoxDataProvider, 10);
        this.filterIconPainter = new ComboBoxFilterIconPainter(comboBoxDataProvider);
    }

    /**
     * Create a ComboBoxFilterRowConfiguration with the given filter cell editor
     * and filter icon painter.
     *
     * @param cellEditor
     *            The {@link ICellEditor} that should be used in the filter row.
     * @param filterIconPainter
     *            The {@link ImagePainter} that should be used to render a
     *            filter icon in the filter row.
     * @since 2.0
     */
    public ComboBoxFilterRowConfiguration(ICellEditor cellEditor, ImagePainter filterIconPainter) {
        this.cellEditor = cellEditor;
        this.filterIconPainter = filterIconPainter;
    }

    @Override
    public void configureRegistry(IConfigRegistry configRegistry) {

        configRegistry.registerConfigAttribute(
                EditConfigAttributes.CELL_EDITOR,
                this.cellEditor,
                DisplayMode.NORMAL,
                GridRegion.FILTER_ROW);

        configRegistry.registerConfigAttribute(
                FilterRowConfigAttributes.TEXT_MATCHING_MODE,
                TextMatchingMode.REGULAR_EXPRESSION);

        ICellPainter cellPainter = new CellPainterDecorator(new TextPainter() {
            {
                this.paintFg = false;
            }

            // override the preferred width and height to be 0, as otherwise
            // the String that is generated in the background for multiple
            // selection will be taken into account for auto resizing

            @Override
            public int getPreferredWidth(ILayerCell cell, GC gc, IConfigRegistry configRegistry) {
                return 0;
            }

            @Override
            public int getPreferredHeight(ILayerCell cell, GC gc, IConfigRegistry configRegistry) {
                return 0;
            }
        }, CellEdgeEnum.RIGHT, this.filterIconPainter);

        configRegistry.registerConfigAttribute(
                CellConfigAttributes.CELL_PAINTER,
                cellPainter,
                DisplayMode.NORMAL,
                GridRegion.FILTER_ROW);
    }

}
