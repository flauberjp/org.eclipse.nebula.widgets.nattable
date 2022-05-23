/*****************************************************************************
 * Copyright (c) 2018, 2020 Dirk Fauth.
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
package org.eclipse.nebula.widgets.nattable.hierarchical;

import java.util.Arrays;

/**
 * Wrapper class to support an object model of hierarchical classes in NatTable.
 * Via {@link HierarchicalHelper} a collection of nested classes can be
 * de-normalized into a simple collection that can then be used inside a
 * NatTable e.g. via HierarchicalTreeLayer.
 *
 * @see HierarchicalHelper
 *
 * @since 1.6
 */
public class HierarchicalWrapper {

    private final Object[] levelObjects;

    /**
     * Creates a new {@link HierarchicalWrapper} with the given number of
     * levels. The level objects need to be set afterwards via
     * {@link #setObject(int, Object)}.
     *
     * @param level
     *            The number of levels this {@link HierarchicalWrapper}
     *            supports.
     */
    public HierarchicalWrapper(int level) {
        this.levelObjects = new Object[level];
    }

    /**
     * Copy constructor to create a new {@link HierarchicalWrapper} out of the
     * given object.
     *
     * @param toCopy
     *            The {@link HierarchicalWrapper} that should be copied.
     * @since 2.0
     */
    public HierarchicalWrapper(HierarchicalWrapper toCopy) {
        this.levelObjects = Arrays.copyOf(toCopy.levelObjects, toCopy.levelObjects.length);
    }

    /**
     * Get the model object for the given level out of this wrapper.
     *
     * @param level
     *            The level for which the model object is requested.
     * @return The object for the given level.
     * @throws IllegalArgumentException
     *             if an object is requested for a deeper level than this
     *             wrapper supports.
     */
    public Object getObject(int level) {
        if (level >= this.levelObjects.length) {
            throw new IllegalArgumentException("Requested a deeper level than available"); //$NON-NLS-1$
        }
        return this.levelObjects[level];
    }

    /**
     * Set the given model object for the given level.
     *
     * @param level
     *            The level on which the object should be set.
     * @param object
     *            The object to set to the given level in this wrapper.
     * @throws IllegalArgumentException
     *             if it is tried to set an object to a level that is not
     *             supported by this wrapper.
     */
    public void setObject(int level, Object object) {
        if (level >= this.levelObjects.length) {
            throw new IllegalArgumentException("Level " + level + " is not supported by this instance"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        this.levelObjects[level] = object;
    }

    /**
     *
     * @return The number of levels provided by this wrapper.
     */
    public int getLevels() {
        return this.levelObjects.length;
    }
}
