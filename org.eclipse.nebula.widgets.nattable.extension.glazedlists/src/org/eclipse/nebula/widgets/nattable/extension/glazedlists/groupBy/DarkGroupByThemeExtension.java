/*******************************************************************************
 * Copyright (c) 2014, 2020 Dirk Fauth and others.
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
package org.eclipse.nebula.widgets.nattable.extension.glazedlists.groupBy;

import org.eclipse.nebula.widgets.nattable.style.theme.DarkNatTableThemeConfiguration;
import org.eclipse.nebula.widgets.nattable.style.theme.IThemeExtension;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;

/**
 * {@link IThemeExtension} for the GroupBy feature that matches the
 * {@link DarkNatTableThemeConfiguration}.
 */
public class DarkGroupByThemeExtension extends ModernGroupByThemeExtension {

    public DarkGroupByThemeExtension() {
        this.groupByHeaderBgColor = GUIHelper.COLOR_BLACK;

        this.groupByBgColor = GUIHelper.COLOR_WIDGET_DARK_SHADOW;

        this.groupByHintBgColor = GUIHelper.COLOR_BLACK;
        this.groupByHintFgColor = GUIHelper.COLOR_WIDGET_DARK_SHADOW;
    }
}
