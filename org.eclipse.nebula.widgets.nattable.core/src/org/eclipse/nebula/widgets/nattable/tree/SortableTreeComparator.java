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
package org.eclipse.nebula.widgets.nattable.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.sort.ISortModel;
import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.nebula.widgets.nattable.util.ComparatorChain;

public class SortableTreeComparator<T> implements Comparator<T> {

    private final Comparator<T> treeComparator;
    private final ISortModel sortModel;

    public SortableTreeComparator(Comparator<T> treeComparator, ISortModel sortModel) {
        this.treeComparator = treeComparator;
        this.sortModel = sortModel;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public int compare(T o1, T o2) {
        int treeComparatorResult = this.treeComparator.compare(o1, o2);
        if (treeComparatorResult == 0) {
            return 0;
        } else {
            List<Integer> sortedColumnIndexes = this.sortModel.getSortedColumnIndexes();
            if (sortedColumnIndexes != null && !sortedColumnIndexes.isEmpty()) {
                List<Comparator<T>> comparators = new ArrayList<>();
                for (int sortedColumnIndex : sortedColumnIndexes) {
                    // get comparator for column index... somehow
                    List<Comparator> columnComparators =
                            this.sortModel.getComparatorsForColumnIndex(sortedColumnIndex);

                    if (!columnComparators.isEmpty()) {
                        SortDirectionEnum sortDirection = this.sortModel.getSortDirection(sortedColumnIndex);
                        for (Comparator columnComparator : columnComparators) {
                            if (sortDirection == SortDirectionEnum.ASC) {
                                comparators.add(columnComparator);
                            } else if (sortDirection == SortDirectionEnum.DESC) {
                                comparators.add(Collections.reverseOrder(columnComparator));
                            }
                        }
                    }
                }
                comparators.add(this.treeComparator);
                return new ComparatorChain<T>(comparators).compare(o1, o2);
            } else {
                return treeComparatorResult;
            }
        }
    }

}
