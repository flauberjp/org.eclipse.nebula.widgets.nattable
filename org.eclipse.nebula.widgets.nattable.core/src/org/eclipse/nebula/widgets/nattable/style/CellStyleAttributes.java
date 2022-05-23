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
package org.eclipse.nebula.widgets.nattable.style;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

public final class CellStyleAttributes {

    private CellStyleAttributes() {
        // private default constructor for constants class
    }

    /**
     * Attribute for configuring the background color of a cell.
     */
    public static final ConfigAttribute<Color> BACKGROUND_COLOR = new ConfigAttribute<>();

    /**
     * Attribute for configuring the foreground color of a cell.
     */
    public static final ConfigAttribute<Color> FOREGROUND_COLOR = new ConfigAttribute<>();

    /**
     * Attribute for configuring the gradient sweeping background color. Is used
     * by the GradientBackgroundPainter.
     */
    public static final ConfigAttribute<Color> GRADIENT_BACKGROUND_COLOR = new ConfigAttribute<>();

    /**
     * Attribute for configuring the gradient sweeping foreground color. Is used
     * by the GradientBackgroundPainter.
     */
    public static final ConfigAttribute<Color> GRADIENT_FOREGROUND_COLOR = new ConfigAttribute<>();

    /**
     * Attribute for configuring the horizontal alignment of a cell.
     */
    public static final ConfigAttribute<HorizontalAlignmentEnum> HORIZONTAL_ALIGNMENT = new ConfigAttribute<>();

    /**
     * Attribute for configuring the vertical alignment of a cell.
     */
    public static final ConfigAttribute<VerticalAlignmentEnum> VERTICAL_ALIGNMENT = new ConfigAttribute<>();

    /**
     * Attribute for configuring the font to be used on rendering text. Is used
     * by all specialisations of the AbstractTextPainter.
     */
    public static final ConfigAttribute<Font> FONT = new ConfigAttribute<>();

    /**
     * Attribute for configuring the image to rendered. Is used by the
     * ImagePainter to determine the image to render dynamically.
     */
    public static final ConfigAttribute<Image> IMAGE = new ConfigAttribute<>();

    /**
     * Attribute for configuring the border style. Is used by the
     * LineBorderDecorator.
     */
    public static final ConfigAttribute<BorderStyle> BORDER_STYLE = new ConfigAttribute<>();

    /**
     * Attribute for configuring the echo character that should be used by
     * PasswordTextPainter and PasswordCellEditor.
     */
    public static final ConfigAttribute<Character> PASSWORD_ECHO_CHAR = new ConfigAttribute<>();

    /**
     * Attribute for configuring the text decoration (underline and/or
     * strikethrough). Is used by all specialisations of the AbstractTextPainter
     */
    public static final ConfigAttribute<TextDecorationEnum> TEXT_DECORATION = new ConfigAttribute<>();
}
