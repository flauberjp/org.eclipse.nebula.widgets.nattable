/*****************************************************************************
 * Copyright (c) 2015, 2020 CEA LIST.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Dirk Fauth <dirk.fauth@googlemail.com> - Initial API and implementation
 *****************************************************************************/
package org.eclipse.nebula.widgets.nattable.command;

import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.util.CalculatedValueCache;

/**
 * {@link ILayerCommandHandler} for the {@link DisposeResourcesCommand}.
 * Disposes the {@link CalculatedValueCache}.
 * <p>
 * Note that this command handler does not consume the
 * {@link DisposeResourcesCommand}. So it is possible to register multiple
 * instances of this command handler for different instances of the
 * {@link CalculatedValueCache}.
 * </p>
 *
 * @since 1.4
 */
public class DisposeCalculatedValueCacheCommandHandler implements ILayerCommandHandler<DisposeResourcesCommand> {

    protected CalculatedValueCache valueCache;

    /**
     *
     * @param valueCache
     *            The {@link CalculatedValueCache} that should be affected by
     *            this command handler.
     */
    public DisposeCalculatedValueCacheCommandHandler(CalculatedValueCache valueCache) {
        this.valueCache = valueCache;
    }

    @Override
    public boolean doCommand(ILayer targetLayer, DisposeResourcesCommand command) {
        this.valueCache.killCache();
        this.valueCache.dispose();
        // do not consume the command, since other resources might need to get
        // disposed aswell
        return false;
    }

    @Override
    public Class<DisposeResourcesCommand> getCommandClass() {
        return DisposeResourcesCommand.class;
    }
}
