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
package org.eclipse.nebula.widgets.nattable.test.fixture.command;

import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.ConfigRegistry;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.resize.command.InitializeAutoResizeRowsCommand;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.test.fixture.layer.DataLayerFixture;
import org.eclipse.nebula.widgets.nattable.util.GCFactory;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class AutoResizeRowCommandFixture extends
        InitializeAutoResizeRowsCommand {

    public AutoResizeRowCommandFixture() {
        super(new DataLayerFixture(), 2, new ConfigRegistry(), new GCFactory(
                new Image(Display.getDefault(), new Rectangle(0, 0, 100, 100))));

        ConfigRegistry configRegistry = (ConfigRegistry) getConfigRegistry();
        configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE,
                new Style());
        configRegistry.registerConfigAttribute(
                CellConfigAttributes.CELL_PAINTER, new TextPainter());
    }

}
