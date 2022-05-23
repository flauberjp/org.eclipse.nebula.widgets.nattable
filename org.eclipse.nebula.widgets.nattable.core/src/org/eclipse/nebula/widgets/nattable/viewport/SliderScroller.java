/*******************************************************************************
 * Copyright (c) 2013, 2021 Edwin Park and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Edwin Park - initial API and implementation
 *******************************************************************************/
package org.eclipse.nebula.widgets.nattable.viewport;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Slider;

public class SliderScroller implements IScroller<Slider> {

    private Slider slider;

    public SliderScroller(Slider slider) {
        this.slider = slider;
    }

    @Override
    public Slider getUnderlying() {
        return this.slider;
    }

    @Override
    public boolean isDisposed() {
        return this.slider.isDisposed();
    }

    @Override
    public void addListener(int eventType, Listener listener) {
        this.slider.addListener(eventType, listener);
    }

    @Override
    public void removeListener(int eventType, Listener listener) {
        this.slider.removeListener(eventType, listener);
    }

    @Override
    public int getSelection() {
        return this.slider.getSelection();
    }

    @Override
    public void setSelection(int value) {
        this.slider.setSelection(value);
    }

    @Override
    public int getMaximum() {
        return this.slider.getMaximum();
    }

    @Override
    public void setMaximum(int value) {
        this.slider.setMaximum(value);
        this.slider.update();
    }

    @Override
    public int getPageIncrement() {
        return this.slider.getPageIncrement();
    }

    @Override
    public void setPageIncrement(int value) {
        this.slider.setPageIncrement(value);
    }

    @Override
    public int getThumb() {
        return this.slider.getThumb();
    }

    @Override
    public void setThumb(int value) {
        this.slider.setThumb(value);
    }

    @Override
    public int getIncrement() {
        return this.slider.getIncrement();
    }

    @Override
    public void setIncrement(int value) {
        this.slider.setIncrement(value);
    }

    @Override
    public boolean getEnabled() {
        return this.slider.getEnabled();
    }

    @Override
    public void setEnabled(boolean b) {
        this.slider.setEnabled(b);
    }

    @Override
    public boolean getVisible() {
        return this.slider.getVisible();
    }

    @Override
    public void setVisible(boolean b) {
        boolean visible = this.slider.isVisible();
        this.slider.setVisible(b);
        // if the slider becomes invisible we fire a resize event to trigger
        // re-calculation of percentage sized columns to take the slider
        // space
        if (!b && visible
                && !isDisposed()
                && !this.slider.getParent().isDisposed()) {
            this.slider.getParent().notifyListeners(SWT.Resize, null);
        }
    }

}
