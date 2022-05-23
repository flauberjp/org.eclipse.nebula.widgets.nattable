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
package org.eclipse.nebula.widgets.nattable.test.fixture.layer;

import org.eclipse.nebula.widgets.nattable.layer.IUniqueIndexLayer;
import org.eclipse.nebula.widgets.nattable.reorder.ColumnReorderLayer;

/**
 * This fixture provides a base vanilla ColumnReorderLayer. In order to keep a
 * test's scope narrow, this class exposes the reorderColumnPositions method,
 * this way we can reorder during testing without having to use commands.
 */
public class BaseColumnReorderLayerFixture extends ColumnReorderLayer {

    public BaseColumnReorderLayerFixture(IUniqueIndexLayer underlyingLayer) {
        super(underlyingLayer);
    }

    @Override
    public void reorderColumnPosition(int fromColumnPosition, int toColumnPosition) {
        super.reorderColumnPosition(fromColumnPosition, toColumnPosition);
    }
}
