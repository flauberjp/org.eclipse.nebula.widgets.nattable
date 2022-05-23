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
 *     Dirk Fauth <dirk.fauth@googlemail.com> - Bug 455364
 ******************************************************************************/
package org.eclipse.nebula.widgets.nattable.tree;

import java.util.List;

public interface ITreeData<T> {

    /**
     * @param object
     *            The object that is represented by the tree node whose depth is
     *            requested.
     * @return the number of ancestors of the node for the given object. Root
     *         nodes have depth 0, other nodes depth is one greater than the
     *         depth of their parent node.
     */
    int getDepthOfData(T object);

    /**
     *
     * @param index
     *            The index of the tree node whose depth is requested.
     * @return the number of ancestors of the node at the specified index. Root
     *         nodes have depth 0, other nodes depth is one greater than the
     *         depth of their parent node.
     */
    int getDepthOfData(int index);

    /**
     * @param index
     *            The index for which the corresponding object in the tree
     *            structure is requested.
     * @return The object at the given index in the tree structure.
     */
    T getDataAtIndex(int index);

    /**
     * @param child
     *            The child whose index is requested.
     * @return The index of the given child object in the tree structure.
     */
    int indexOf(T child);

    /**
     * @param object
     *            The object which should be checked for children.
     * @return <code>true</code> if the object has children in the tree
     *         structure, <code>false</code> if it is a leaf.
     */
    boolean hasChildren(T object);

    /**
     * @param index
     *            The index of the object in the tree structure which should be
     *            checked for children.
     * @return <code>true</code> if the object has children in the tree
     *         structure, <code>false</code> if it is a leaf.
     */
    boolean hasChildren(int index);

    /**
     * Return the child objects below the given object if the object is a tree
     * node. It will only return the direct children and will not search for sub
     * children.
     *
     * @param object
     *            The object whose children are requested.
     * @return The children of the given object.
     */
    List<T> getChildren(T object);

    /**
     * Return the child objects below the given object if the object is a tree
     * node.
     *
     * @param object
     *            The object whose children are requested.
     * @param fullDepth
     *            to return only direct children or search for sub children
     * @return The children of the given object.
     */
    List<T> getChildren(T object, boolean fullDepth);

    /**
     * Return the child objects below the object at the given index if the
     * object is a tree node. It will only return the direct children and will
     * not search for sub children.
     *
     * @param index
     *            The index of the object whose children are requested.
     * @return The children of the object at the given index.
     */
    List<T> getChildren(int index);

    /**
     * @return The number of elements handled by this ITreeData.
     */
    int getElementCount();

    /**
     * Checks if a given index points to an element of the underlying list this
     * ITreeData is connected to. This check is necessary for example to support
     * mixing in a summary row together with a tree, where the summary row index
     * is not in scope of the underlying list.
     *
     * @param index
     *            The index to check.
     * @return <code>true</code> if the index points to an element of the
     *         underlying list, <code>false</code> if it doesn't.
     */
    boolean isValidIndex(int index);
}
