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
 *     Dirk Fauth <dirk.fauth@googlemail.com> - Bug 444839
 ******************************************************************************/
package org.eclipse.nebula.widgets.nattable.sort;

import java.util.Comparator;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.sort.command.SortCommandHandler;

/**
 * Interface providing sorting functionality.
 */
public interface ISortModel {

    /**
     * @return List of column indexes that are sorted.
     */
    public List<Integer> getSortedColumnIndexes();

    /**
     * @param columnIndex
     *            The column index to check.
     * @return <code>true</code> if the column with the given index is sorted at
     *         the moment.
     */
    public boolean isColumnIndexSorted(int columnIndex);

    /**
     * @param columnIndex
     *            The column index for which the sort direction is requested.
     * @return the direction in which the column with the given index is
     *         currently sorted
     */
    public SortDirectionEnum getSortDirection(int columnIndex);

    /**
     * @param columnIndex
     *            The column index for which the sort order is requested.
     * @return when multiple columns are sorted, this returns the order of the
     *         column index in the sort
     *         <p>
     *         Example: If column indexes 3, 6, 9 are sorted (in that order) the
     *         sort order for index 6 is 1.
     */
    public int getSortOrder(int columnIndex);

    /**
     * @param columnIndex
     *            The index of the column for which the row objects should be
     *            sorted.
     * @return The collection of Comparators used to sort row objects by column
     *         values.
     */
    @SuppressWarnings("rawtypes")
    public List<Comparator> getComparatorsForColumnIndex(int columnIndex);

    /**
     * @param columnIndex
     *            The index of the column for which the {@link Comparator} is
     *            requested.
     * @return The {@link Comparator} that is used for sorting the values of a
     *         specified column. Needed in case of data model wrapping, e.g.
     *         GroupBy
     */
    public Comparator<?> getColumnComparator(int columnIndex);

    /**
     * This method is called by the {@link SortCommandHandler} in response to a
     * sort command. It is responsible for sorting the requested column.
     *
     * @param columnIndex
     *            The column index to sort.
     * @param sortDirection
     *            The sort direction to apply.
     * @param accumulate
     *            flag indicating if the column should added to a previous sort.
     */
    public void sort(int columnIndex, SortDirectionEnum sortDirection, boolean accumulate);

    /**
     * Remove all sorting
     */
    public void clear();

}
