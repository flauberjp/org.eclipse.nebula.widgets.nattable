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

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.data.ReflectiveColumnPropertyAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Specialization of {@link ReflectiveColumnPropertyAccessor} to access fields
 * in a {@link HierarchicalWrapper}.
 *
 * @since 1.6
 */
public class HierarchicalReflectiveColumnPropertyAccessor extends ReflectiveColumnPropertyAccessor<HierarchicalWrapper> {

    private static final Logger LOG = LoggerFactory.getLogger(HierarchicalReflectiveColumnPropertyAccessor.class);

    /**
     * @param propertyNames
     *            of the members of the row bean
     */
    public HierarchicalReflectiveColumnPropertyAccessor(String... propertyNames) {
        super(propertyNames);
    }

    /**
     * @param propertyNames
     *            of the members of the row bean
     */
    public HierarchicalReflectiveColumnPropertyAccessor(List<String> propertyNames) {
        super(propertyNames);
    }

    @Override
    public Object getDataValue(HierarchicalWrapper rowObj, int columnIndex) {
        String propertyName = getColumnProperty(columnIndex);
        String[] split = propertyName.split(HierarchicalHelper.PROPERTY_SEPARATOR_REGEX);
        Object levelObject = rowObj.getObject(split.length - 1);

        if (levelObject != null) {
            try {
                PropertyDescriptor propertyDesc = getPropertyDescriptor(levelObject, split[split.length - 1]);
                Method readMethod = propertyDesc.getReadMethod();
                return readMethod.invoke(levelObject);
            } catch (Exception e) {
                LOG.warn("Error on getting data value", e); //$NON-NLS-1$
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public void setDataValue(HierarchicalWrapper rowObj, int columnIndex, Object newValue) {
        String propertyName = getColumnProperty(columnIndex);
        String[] split = propertyName.split(HierarchicalHelper.PROPERTY_SEPARATOR_REGEX);
        Object levelObject = rowObj.getObject(split.length - 1);

        if (levelObject != null) {
            try {
                PropertyDescriptor propertyDesc = getPropertyDescriptor(levelObject, split[split.length - 1]);
                Method writeMethod = propertyDesc.getWriteMethod();
                if (writeMethod == null) {
                    throw new RuntimeException(
                            "Setter method not found in backing bean for value at column index: " + columnIndex); //$NON-NLS-1$
                }
                writeMethod.invoke(levelObject, newValue);
            } catch (IllegalArgumentException ex) {
                LOG.error("Data type being set does not match the data type of the setter method in the backing bean", ex); //$NON-NLS-1$
            } catch (Exception e) {
                LOG.error("Error while setting data value", e); //$NON-NLS-1$
                throw new RuntimeException("Error while setting data value"); //$NON-NLS-1$
            }
        }
    }
}
