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
package org.eclipse.nebula.widgets.nattable.style.editor;

import org.eclipse.nebula.widgets.nattable.Messages;
import org.eclipse.nebula.widgets.nattable.style.HorizontalAlignmentEnum;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * Component that lets the user select an alignment.
 */
public class HorizontalAlignmentPicker extends Composite {

    private final Combo combo;

    public HorizontalAlignmentPicker(Composite parent, HorizontalAlignmentEnum alignment) {
        super(parent, SWT.NONE);
        setLayout(new RowLayout());

        this.combo = new Combo(this, SWT.READ_ONLY | SWT.DROP_DOWN);
        this.combo.setItems(new String[] {
                Messages.getString("HorizontalAlignmentPicker.center"), //$NON-NLS-1$
                Messages.getString("HorizontalAlignmentPicker.left"), //$NON-NLS-1$
                Messages.getString("HorizontalAlignmentPicker.right") //$NON-NLS-1$
        });

        update(alignment);
    }

    private void update(HorizontalAlignmentEnum alignment) {
        if (alignment.equals(HorizontalAlignmentEnum.CENTER)) {
            this.combo.select(0);
        } else if (alignment.equals(HorizontalAlignmentEnum.LEFT)) {
            this.combo.select(1);
        } else if (alignment.equals(HorizontalAlignmentEnum.RIGHT)) {
            this.combo.select(2);
        } else {
            throw new IllegalArgumentException("bad alignment: " + alignment); //$NON-NLS-1$
        }
    }

    public HorizontalAlignmentEnum getSelectedAlignment() {
        int idx = this.combo.getSelectionIndex();
        if (idx == 0) {
            return HorizontalAlignmentEnum.CENTER;
        } else if (idx == 1) {
            return HorizontalAlignmentEnum.LEFT;
        } else if (idx == 2) {
            return HorizontalAlignmentEnum.RIGHT;
        } else {
            throw new IllegalStateException("shouldn't happen"); //$NON-NLS-1$
        }
    }

    public void setSelectedAlignment(HorizontalAlignmentEnum horizontalAlignment) {
        if (horizontalAlignment == null) {
            throw new IllegalArgumentException("null"); //$NON-NLS-1$
        }
        update(horizontalAlignment);
    }
}
