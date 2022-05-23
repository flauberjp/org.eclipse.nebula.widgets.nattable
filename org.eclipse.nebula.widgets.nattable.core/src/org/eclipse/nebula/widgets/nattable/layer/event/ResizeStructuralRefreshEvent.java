/*******************************************************************************
 * Copyright (c) 2012, 2020 Dirk Fauth and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Dirk Fauth <dirk.fauth@googlemail.com> - initial API and implementation
 ******************************************************************************/
package org.eclipse.nebula.widgets.nattable.layer.event;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;

/**
 * Special {@link StructuralRefreshEvent} that returns empty lists for column
 * and row diffs to avoid complete resetting of changes made to the NatTable by
 * the user (e.g. resetting changed column order like reported in
 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=384795).
 *
 * <p>
 * This event should only be fired be the {@link DataLayer} if columns or rows
 * are configured to use percentage sizing.
 */
public class ResizeStructuralRefreshEvent extends StructuralRefreshEvent {

    public ResizeStructuralRefreshEvent(ILayer layer) {
        super(layer);
    }

    protected ResizeStructuralRefreshEvent(ResizeStructuralRefreshEvent event) {
        super(event);
    }

    @Override
    public Collection<StructuralDiff> getColumnDiffs() {
        return new ArrayList<>();
    }

    @Override
    public Collection<StructuralDiff> getRowDiffs() {
        return new ArrayList<>();
    }
}
