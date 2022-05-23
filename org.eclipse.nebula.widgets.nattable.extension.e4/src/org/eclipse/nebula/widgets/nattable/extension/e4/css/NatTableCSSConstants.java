/*****************************************************************************
 * Copyright (c) 2015, 2020 CEA LIST and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Dirk Fauth <dirk.fauth@googlemail.com> - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.nebula.widgets.nattable.extension.e4.css;

import org.eclipse.nebula.widgets.nattable.config.IConfiguration;
import org.eclipse.nebula.widgets.nattable.extension.e4.painterfactory.CellPainterFactory;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;

/**
 * Constants class for NatTable CSS support.
 */
public final class NatTableCSSConstants {

    private NatTableCSSConstants() {
        // private default constructor for helper class
    }

    // CSS properties

    /**
     * CSS property for the NatTable background color. This has effect on the
     * area that does not show cells or cells with a transparent background.
     */
    public static final String BACKGROUND_COLOR = "background-color";
    /**
     * CSS property for the NatTable background image. This has effect on the
     * area that does not show cells or cells with a transparent background.
     */
    public static final String BACKGROUND_IMAGE = "background-image";
    /**
     * CSS property to specify the border color that is applied around the
     * NatTable. Triggers the usage of the NatTableBorderOverlayPainter to apply
     * borders on every side of the table.
     * <p>
     * Setting the value <i>auto</i> will use the configured grid line color as
     * the table border color.
     * </p>
     */
    public static final String TABLE_BORDER_COLOR = "table-border-color";
    /**
     * CSS property for enabling/disabling the automatic painter resolution
     * based on CSS properties.
     * <p>
     * Available values: <code>true, false</code>
     * </p>
     */
    public static final String PAINTER_RESOLUTION = "painter-resolution";
    /**
     * CSS property for configuring the painter.
     * <p>
     * The pattern for configuring a painter is
     * <i>[background-painter]?[decorator]*[content-painter]?</i>, although it
     * mostly doesn't make sense to not configure a content painter.
     * </p>
     * <p>
     * The possible values are listed as constants in {@link CellPainterFactory}
     * .
     * </p>
     */
    public static final String PAINTER = "painter";
    /**
     * CSS property for configuring the tree structure painter. Similar to
     * {@link #PAINTER} but specific for the tree structure configuration as it
     * registers a painter for TreeConfigAttributes.TREE_STRUCTURE_PAINTER.
     * <p>
     * <b>Note</b>: The value should always end with <i>tree</i> to configure a
     * valid tree structure painter. It is mainly intended to configure the
     * painter hierarchy (background and decorators) and whether to use inverted
     * default icons via {@link #INVERT_ICONS}.
     * </p>
     * <p>
     * <b>Note</b>: The content painter that should be wrapped by the tree
     * structure painter does not need to be added here aswell because it is
     * evaluated dynamically.
     * </p>
     */
    public static final String TREE_STRUCTURE_PAINTER = "tree-structure-painter";
    /**
     * CSS property for {@link CellStyleAttributes#BACKGROUND_COLOR}.
     */
    public static final String CELL_BACKGROUND_COLOR = "cell-background-color";
    /**
     * CSS property for configuring a background based on an image.
     */
    public static final String CELL_BACKGROUND_IMAGE = "cell-background-image";
    /**
     * CSS property for {@link CellStyleAttributes#FOREGROUND_COLOR}.
     */
    public static final String FOREGROUND_COLOR = "color";
    /**
     * CSS property for {@link CellStyleAttributes#HORIZONTAL_ALIGNMENT}.
     */
    public static final String HORIZONTAL_ALIGNMENT = "text-align";
    /**
     * CSS property for {@link CellStyleAttributes#VERTICAL_ALIGNMENT}.
     */
    public static final String VERTICAL_ALIGNMENT = "vertical-align";
    /**
     * CSS property for {@link CellStyleAttributes#FONT}.
     */
    public static final String FONT = "font";
    /**
     * CSS property for {@link CellStyleAttributes#FONT}.
     */
    public static final String FONT_FAMILY = "font-family";
    /**
     * CSS property for {@link CellStyleAttributes#FONT}.
     */
    public static final String FONT_SIZE = "font-size";
    /**
     * CSS property for {@link CellStyleAttributes#FONT}.
     */
    public static final String FONT_STYLE = "font-style";
    /**
     * CSS property for {@link CellStyleAttributes#FONT}.
     */
    public static final String FONT_WEIGHT = "font-weight";
    /**
     * CSS property for {@link CellStyleAttributes#IMAGE}. Triggers the usage of
     * the ImagePainter.
     */
    public static final String IMAGE = "image";
    /**
     * CSS property for {@link CellStyleAttributes#BORDER_STYLE}. Triggers the
     * usage of the LineBorderDecorator.
     */
    public static final String BORDER = "border";
    /**
     * CSS property for {@link CellStyleAttributes#BORDER_STYLE}. Triggers the
     * usage of the LineBorderDecorator.
     */
    public static final String BORDER_COLOR = "border-color";
    /**
     * CSS property for {@link CellStyleAttributes#BORDER_STYLE}. Triggers the
     * usage of the LineBorderDecorator.
     */
    public static final String BORDER_STYLE = "border-style";
    /**
     * CSS property for {@link CellStyleAttributes#BORDER_STYLE}. Triggers the
     * usage of the LineBorderDecorator.
     */
    public static final String BORDER_WIDTH = "border-width";
    /**
     * CSS property for {@link CellStyleAttributes#BORDER_STYLE}. Triggers the
     * usage of the LineBorderDecorator.
     * <p>
     * Available values: <code>centered, internal, external</code>
     * </p>
     *
     * @since 1.1
     */
    public static final String BORDER_MODE = "border-mode";
    /**
     * CSS property for {@link CellStyleAttributes#PASSWORD_ECHO_CHAR}. Does not
     * trigger the usage of the PasswordTextPainter. This needs to be done via
     * additional {@link IConfiguration} or <i>painter</i> CSS property.
     */
    public static final String PASSWORD_ECHO_CHAR = "password-echo-char";
    /**
     * CSS property for {@link CellStyleAttributes#TEXT_DECORATION}.
     * <p>
     * Available values: <code>none, underline, line-through</code>
     * </p>
     * <p>
     * Combinations are possible via space separated list.
     * </p>
     */
    public static final String TEXT_DECORATION = "text-decoration";
    /**
     * CSS property for the color of the freeze separator.
     */
    public static final String FREEZE_SEPARATOR_COLOR = "freeze-separator-color";
    /**
     * CSS property for the width of the freeze separator.
     *
     * @since 1.2
     */
    public static final String FREEZE_SEPARATOR_WIDTH = "freeze-separator-width";
    /**
     * CSS property for the color of the grid lines.
     */
    public static final String GRID_LINE_COLOR = "grid-line-color";
    /**
     * CSS property for the width of the grid lines.
     */
    public static final String GRID_LINE_WIDTH = "grid-line-width";
    /**
     * CSS property to specify whether grid lines should be rendered or not.
     * <p>
     * Available values: <code>true, false</code>
     * </p>
     */
    public static final String RENDER_GRID_LINES = "render-grid-lines";
    /**
     * CSS property to specify whether words should automatically or not.
     * Default is <code>false</code>.
     * <p>
     * Available values: <code>true, false</code>
     * </p>
     *
     * @since 1.1
     */
    public static final String WORD_WRAP = "word-wrap";
    /**
     * CSS property to specify whether text should automatically wrapped between
     * words or not. Default is <code>false</code>.
     * <p>
     * Available values: <code>true, false</code>
     * </p>
     */
    public static final String TEXT_WRAP = "text-wrap";
    /**
     * CSS property to specify whether text should be trimmed on rendering words
     * or not. Default is <code>true</code>.
     * <p>
     * Available values: <code>true, false</code>
     * </p>
     */
    public static final String TEXT_TRIM = "text-trim";
    /**
     * CSS property to specify whether text should be rendered horizontally or
     * vertically. Default is <code>horizontal</code>.
     * <p>
     * Available values: <code>horizontal, vertical</code>
     * </p>
     */
    public static final String TEXT_DIRECTION = "text-direction";
    /**
     * CSS property to specify an additional spacing between lines in a text. By
     * default this value is zero which means the line height is specified only
     * by the font height.
     *
     * @since 1.1
     */
    public static final String LINE_SPACING = "line-spacing";
    /**
     * CSS property to configure the column width. Available values are:
     * <ul>
     * <li>auto - configure automatic width calculation for content painters
     * </li>
     * <li>percentage - configure general percentage sizing</li>
     * <li>percentage value (e.g. 20%) - configure specific percentage sizing
     * </li>
     * <li>number value (e.g. 100px)- configure column width</li>
     * </ul>
     */
    public static final String COLUMN_WIDTH = "column-width";
    /**
     * CSS property to configure the row height. Available values are:
     * <ul>
     * <li>auto - configure automatic height calculation for content painters
     * </li>
     * <li>percentage - configure general percentage sizing</li>
     * <li>percentage value (e.g. 20%) - configure specific percentage sizing
     * </li>
     * <li>number value (e.g. 100px)- configure row height</li>
     * </ul>
     */
    public static final String ROW_HEIGHT = "row-height";
    /**
     * CSS property to configure the display converter. Possible values are:
     * <ul>
     * <li>boolean</li>
     * <li>character</li>
     * <li>date "[pattern]"</li>
     * <li>default</li>
     * <li>percentage</li>
     * <li>byte</li>
     * <li>short [format]</li>
     * <li>int [format]</li>
     * <li>long [format]</li>
     * <li>big-int</li>
     * <li>float [format] [min-fraction-digits] [max-fraction-digits]</li>
     * <li>double [format] [min-fraction-digits] [max-fraction-digits]</li>
     * <li>big-decimal [min-fraction-digits] [max-fraction-digits]</li>
     * </ul>
     */
    public static final String CONVERTER = "converter";
    /**
     * CSS property to configure a decoration. Consists of 4 values:
     * <ul>
     * <li>the URI for the decorator icon</li>
     * <li>number value for the spacing between base painter and decoration</li>
     * <li>the edge to paint the decoration
     * (top|right|bottom|left|top-right|top-left|bottom-right|bottom-left</li>
     * <li>true|false to configure decoration dependent rendering</li>
     * </ul>
     */
    public static final String DECORATION = "decoration";
    /**
     * CSS property to configure whether default decorator icons should be
     * inverted.
     * <p>
     * Available values: <code>true, false</code>
     * </p>
     */
    public static final String INVERT_ICONS = "invert-icons";
    /**
     * CSS property to specify the border style of the fill region.
     */
    public static final String FILL_REGION_BORDER = "fill-region-border";
    /**
     * CSS property to specify the border of the fill drag handle.
     */
    public static final String FILL_HANDLE_BORDER = "fill-handle-border";
    /**
     * CSS property to specify the color of the fill drag handle.
     */
    public static final String FILL_HANDLE_COLOR = "fill-handle-color";
    /**
     * CSS property to specify cell padding. Triggers usage of the
     * PaddingDecorator if painter resolution is enabled.
     */
    public static final String PADDING = "padding";
    /**
     * CSS property to specify the top padding of a cell. Triggers usage of the
     * PaddingDecorator if painter resolution is enabled.
     */
    public static final String PADDING_TOP = "padding-top";
    /**
     * CSS property to specify the right padding of a cell. Triggers usage of
     * the PaddingDecorator if painter resolution is enabled.
     */
    public static final String PADDING_RIGHT = "padding-right";
    /**
     * CSS property to specify the bottom padding of a cell. Triggers usage of
     * the PaddingDecorator if painter resolution is enabled.
     */
    public static final String PADDING_BOTTOM = "padding-bottom";
    /**
     * CSS property to specify the left padding of a cell. Triggers usage of the
     * PaddingDecorator if painter resolution is enabled.
     */
    public static final String PADDING_LEFT = "padding-left";
    /**
     * CSS property for configuring the colors to use with the
     * PercentageBarDecorator.
     */
    public static final String PERCENTAGE_DECORATOR_COLORS = "percentage-decorator-colors";
    /**
     * CSS property for specifying the font of a text cell editor on conversion
     * error.
     */
    public static final String CONVERSION_ERROR_FONT = "conversion-error-font";
    /**
     * CSS property for specifying the font family of a text cell editor on
     * conversion error.
     */
    public static final String CONVERSION_ERROR_FONT_FAMILY = "conversion-error-font-family";
    /**
     * CSS property for specifying the font size of a text cell editor on
     * conversion error.
     */
    public static final String CONVERSION_ERROR_FONT_SIZE = "conversion-error-font-size";
    /**
     * CSS property for specifying the font style of a text cell editor on
     * conversion error.
     */
    public static final String CONVERSION_ERROR_FONT_STYLE = "conversion-error-font-style";
    /**
     * CSS property for specifying the font weight of a text cell editor on
     * conversion error.
     */
    public static final String CONVERSION_ERROR_FONT_WEIGHT = "conversion-error-font-weight";
    /**
     * CSS property for specifying the background color of a text cell editor on
     * conversion error.
     */
    public static final String CONVERSION_ERROR_BACKGROUND_COLOR = "conversion-error-background-color";
    /**
     * CSS property for specifying the foreground color of a text cell editor on
     * conversion error.
     */
    public static final String CONVERSION_ERROR_FOREGROUND_COLOR = "conversion-error-color";
    /**
     * CSS property for specifying the font of a text cell editor on validation
     * error.
     */
    public static final String VALIDATION_ERROR_FONT = "validation-error-font";
    /**
     * CSS property for specifying the font family of a text cell editor on
     * validation error.
     */
    public static final String VALIDATION_ERROR_FONT_FAMILY = "validation-error-font-family";
    /**
     * CSS property for specifying the font size of a text cell editor on
     * validation error.
     */
    public static final String VALIDATION_ERROR_FONT_SIZE = "validation-error-font-size";
    /**
     * CSS property for specifying the font style of a text cell editor on
     * validation error.
     */
    public static final String VALIDATION_ERROR_FONT_STYLE = "validation-error-font-style";
    /**
     * CSS property for specifying the font weight of a text cell editor on
     * validation error.
     */
    public static final String VALIDATION_ERROR_FONT_WEIGHT = "validation-error-font-weight";
    /**
     * CSS property for specifying the background color of a text cell editor on
     * validation error.
     */
    public static final String VALIDATION_ERROR_BACKGROUND_COLOR = "validation-error-background-color";
    /**
     * CSS property for specifying the foreground color of a text cell editor on
     * validation error.
     */
    public static final String VALIDATION_ERROR_FOREGROUND_COLOR = "validation-error-color";
    /**
     * CSS property for the color of the hide indicator.
     *
     * @since 2.0
     */
    public static final String HIDE_INDICATOR_COLOR = "hide-indicator-color";
    /**
     * CSS property for the width of the hide indicator.
     *
     * @since 2.0
     */
    public static final String HIDE_INDICATOR_WIDTH = "hide-indicator-width";

    // context value keys

    /**
     * Context value key for background painter
     */
    public static final String CV_BACKGROUND_PAINTER = "bg-painter";
    /**
     * Context value key for decorator painter list
     */
    public static final String CV_DECORATOR_PAINTER = "decorator-painter";
    /**
     * Context value key for content painter
     */
    public static final String CV_CONTENT_PAINTER = "content-painter";
    /**
     * Context value key for content painter configuration values
     */
    public static final String CV_PAINTER_CONFIGURATION = "content-painter-config";
    /**
     * Context value key for border properties
     */
    public static final String CV_BORDER_CONFIGURATION = "border-config";
    /**
     * Context value key for conversion error font properties
     */
    public static final String CV_CONVERSION_ERROR_FONT_PROPERTIES = "conversion-error-font";
    /**
     * Context value key for validation error font properties
     */
    public static final String CV_VALIDATION_ERROR_FONT_PROPERTIES = "validation-error-font";

    // painter properties map key

    /**
     * Painter properties key to store whether a GradientBackgroundPainter
     * should sweep from top to bottom or from left to right.
     */
    public static final String GRADIENT_BACKGROUND_VERTICAL = "gradient-background-vertical";
    /**
     * Painter properties key to store the decorator spacing used by the
     * CellPainterDecorator.
     */
    public static final String DECORATOR_SPACING = "decorator-spacing";
    /**
     * Painter properties key to store the decorator cell edge used by the
     * CellPainterDecorator.
     */
    public static final String DECORATOR_EDGE = "decorator-edge";
    /**
     * Painter properties key to store the decorator image used by the
     * CellPainterDecorator.
     */
    public static final String DECORATOR_IMAGE = "decorator-image";
    /**
     * Painter properties key to store the value for decorator dependent flag
     * used by the CellPainterDecorator.
     */
    public static final String PAINT_DECORATION_DEPENDENT = "decoration-dependent";
    /**
     * Painter properties key to specify whether the cell/row height should be
     * calculated dependent on the content. Default is <code>false</code>.
     */
    public static final String CALCULATE_CELL_HEIGHT = "calculate-cell-height";
    /**
     * Painter properties key to specify whether the cell/column width should be
     * calculated dependent on the content. Default is <code>false</code>.
     */
    public static final String CALCULATE_CELL_WIDTH = "calculate-cell-width";
}
