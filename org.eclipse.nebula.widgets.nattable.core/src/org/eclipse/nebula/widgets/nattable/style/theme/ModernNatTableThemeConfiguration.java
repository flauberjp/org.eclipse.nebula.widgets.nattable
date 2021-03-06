/*******************************************************************************
 * Copyright (c) 2014, 2021 Dirk Fauth and others.
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
package org.eclipse.nebula.widgets.nattable.style.theme;

import org.eclipse.nebula.widgets.nattable.group.painter.ColumnGroupHeaderTextPainter;
import org.eclipse.nebula.widgets.nattable.group.painter.RowGroupHeaderTextPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.BackgroundPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.VerticalTextPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.PaddingDecorator;
import org.eclipse.nebula.widgets.nattable.sort.painter.SortIconPainter;
import org.eclipse.nebula.widgets.nattable.sort.painter.SortableHeaderTextPainter;
import org.eclipse.nebula.widgets.nattable.style.BorderStyle;
import org.eclipse.nebula.widgets.nattable.style.BorderStyle.LineStyleEnum;
import org.eclipse.nebula.widgets.nattable.style.HorizontalAlignmentEnum;
import org.eclipse.nebula.widgets.nattable.tree.painter.IndentedTreeImagePainter;
import org.eclipse.nebula.widgets.nattable.tree.painter.TreeImagePainter;
import org.eclipse.nebula.widgets.nattable.ui.util.CellEdgeEnum;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;

/**
 * Theme configuration that overrides the stylings set in the
 * {@link DefaultNatTableThemeConfiguration} to give the NatTable a more modern
 * look &amp; feel.
 */
public class ModernNatTableThemeConfiguration extends DefaultNatTableThemeConfiguration {

    public ModernNatTableThemeConfiguration() {
        this.defaultHAlign = HorizontalAlignmentEnum.LEFT;

        // column header styling
        this.cHeaderHAlign = HorizontalAlignmentEnum.LEFT;
        this.cHeaderFont = GUIHelper.DEFAULT_FONT;

        // column header selection style
        this.cHeaderSelectionFont = GUIHelper.DEFAULT_FONT;

        // row header styling
        this.rHeaderFont = GUIHelper.DEFAULT_FONT;

        // row header selection style
        this.rHeaderSelectionFont = GUIHelper.DEFAULT_FONT;

        // no alternate row styling
        this.evenRowBgColor = GUIHelper.COLOR_WHITE;
        this.oddRowBgColor = GUIHelper.COLOR_WHITE;

        // default selection style
        this.defaultSelectionBgColor = GUIHelper.COLOR_LIST_SELECTION;
        this.defaultSelectionFgColor = GUIHelper.COLOR_WHITE;
        this.defaultSelectionFont = GUIHelper.DEFAULT_FONT;

        // selection anchor
        this.selectionAnchorSelectionBgColor = GUIHelper.COLOR_LIST_SELECTION;
        this.selectionAnchorSelectionFgColor = GUIHelper.COLOR_WHITE;
        this.selectionAnchorBorderStyle = new BorderStyle(1, GUIHelper.COLOR_BLUE, LineStyleEnum.SOLID);

        // column/row group header style
        this.cGroupHeaderHAlign = HorizontalAlignmentEnum.CENTER;

        FontData summaryRowFontData = GUIHelper.DEFAULT_FONT.getFontData()[0];
        summaryRowFontData.setStyle(SWT.BOLD);
        this.summaryRowFont = GUIHelper.getFont(summaryRowFontData);
        this.summaryRowHAlign = HorizontalAlignmentEnum.RIGHT;
        this.summaryRowBgColor = GUIHelper.COLOR_WIDGET_LIGHT_SHADOW;

        this.summaryRowSelectionFont = GUIHelper.getFont(summaryRowFontData);
        this.summaryRowSelectionBgColor = GUIHelper.COLOR_WIDGET_DARK_SHADOW;

        this.renderCornerGridLines = true;
        this.renderColumnHeaderGridLines = true;
    }

    @Override
    public void createPainterInstances() {
        super.createPainterInstances();

        this.defaultCellPainter = new BackgroundPainter(
                new PaddingDecorator(new TextPainter(false, false), 0, 5, 0, 5, false));

        this.cHeaderCellPainter = new PaddingDecorator(new TextPainter(), 0, 5, 0, 5);
        this.cHeaderSelectionCellPainter = new BackgroundPainter(
                new PaddingDecorator(new TextPainter(false, false), 0, 5, 0, 5, false));
        this.rHeaderSelectionCellPainter = new TextPainter();
        this.cGroupHeaderCellPainter = new ColumnGroupHeaderTextPainter();
        this.rGroupHeaderCellPainter = new BackgroundPainter(
                new PaddingDecorator(
                        new RowGroupHeaderTextPainter(
                                new VerticalTextPainter(false, false),
                                CellEdgeEnum.BOTTOM,
                                false,
                                2,
                                true),
                        0, 0, 2, 0, false));

        // sort header styling
        this.sortHeaderCellPainter =
                new BackgroundPainter(
                        new PaddingDecorator(
                                new SortableHeaderTextPainter(
                                        new TextPainter(false, false),
                                        CellEdgeEnum.RIGHT,
                                        new SortIconPainter(false),
                                        false,
                                        0,
                                        false),
                                0, 2, 0, 5, false));
        this.selectedSortHeaderCellPainter =
                new BackgroundPainter(
                        new PaddingDecorator(
                                new SortableHeaderTextPainter(
                                        new TextPainter(false, false),
                                        CellEdgeEnum.RIGHT,
                                        new SortIconPainter(false, true),
                                        false,
                                        0,
                                        false),
                                0, 2, 0, 5, false));

        TreeImagePainter treeImagePainter =
                new TreeImagePainter(
                        false,
                        GUIHelper.getImage("right"), //$NON-NLS-1$
                        GUIHelper.getImage("right_down"), //$NON-NLS-1$
                        null);
        this.treeStructurePainter =
                new BackgroundPainter(
                        new PaddingDecorator(
                                new IndentedTreeImagePainter(
                                        10,
                                        null,
                                        CellEdgeEnum.LEFT,
                                        treeImagePainter,
                                        false,
                                        2,
                                        true),
                                0, 5, 0, 5, false));

        TreeImagePainter treeSelectionImagePainter =
                new TreeImagePainter(
                        false,
                        GUIHelper.getImage("right_inv"), //$NON-NLS-1$
                        GUIHelper.getImage("right_down_inv"), //$NON-NLS-1$
                        null);
        this.treeStructureSelectionPainter =
                new BackgroundPainter(
                        new PaddingDecorator(
                                new IndentedTreeImagePainter(
                                        10,
                                        null,
                                        CellEdgeEnum.LEFT,
                                        treeSelectionImagePainter,
                                        false,
                                        2,
                                        true),
                                0, 5, 0, 5, false));

    }
}
