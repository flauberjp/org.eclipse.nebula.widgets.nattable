/*******************************************************************************
 * Copyright (c) 2019, 2020 Dirk Fauth.
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
package org.eclipse.nebula.widgets.nattable.group.performance.gui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.nebula.widgets.nattable.Messages;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Dialog that is used to specify a name for a header group, either for creation
 * or renaming a group.
 *
 * @since 1.6
 */
public class HeaderGroupNameDialog extends Dialog {

    private Button createButton;
    private Text groupNameText;
    private String groupName;
    private final HeaderGroupNameDialogLabels dialogLabels;

    public enum HeaderGroupNameDialogLabels {
        CREATE_COLUMN_GROUP(
                Messages.getString("ColumnGroups.createColumnGroupDialogTitle"), //$NON-NLS-1$
                Messages.getString("ColumnGroups.createButtonLabel"), //$NON-NLS-1$
                Messages.getString("ColumnGroups.createGroupLabel")) { //$NON-NLS-1$
        },
        CREATE_ROW_GROUP(
                Messages.getString("RowGroups.createRowGroupDialogTitle"), //$NON-NLS-1$
                Messages.getString("RowGroups.createButtonLabel"), //$NON-NLS-1$
                Messages.getString("RowGroups.createGroupLabel")) { //$NON-NLS-1$
        };

        public final String shellTitle;
        public final String createButtonLabel;
        public final String createGroupLabel;

        HeaderGroupNameDialogLabels(String shellTitle, String createButtonLabel, String createGroupLabel) {
            this.shellTitle = shellTitle;
            this.createButtonLabel = createButtonLabel;
            this.createGroupLabel = createGroupLabel;
        }
    }

    public HeaderGroupNameDialog(Shell parentShell, HeaderGroupNameDialogLabels dialogLabels) {
        super(parentShell);
        this.dialogLabels = dialogLabels;
        setShellStyle(SWT.CLOSE | SWT.BORDER | SWT.TITLE | SWT.APPLICATION_MODAL);
    }

    @Override
    public void create() {
        super.create();
        getShell().setText(this.dialogLabels.shellTitle);
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(1, false));
        GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);

        GridDataFactory.fillDefaults()
                .minSize(GUIHelper.convertHorizontalPixelToDpi(200, true), SWT.DEFAULT)
                .align(SWT.FILL, SWT.FILL)
                .grab(true, false)
                .applyTo(createInputPanel(composite));

        Composite buttonPanel = createButtonSection(composite);
        GridDataFactory.swtDefaults()
                .align(SWT.FILL, SWT.BOTTOM)
                .grab(true, true)
                .applyTo(buttonPanel);

        return composite;
    }

    private Composite createButtonSection(Composite composite) {
        Composite panel = new Composite(composite, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 0;
        layout.makeColumnsEqualWidth = false;
        layout.horizontalSpacing = 2;
        panel.setLayout(layout);

        this.createButton = createButton(
                panel,
                IDialogConstants.OK_ID,
                this.dialogLabels.createButtonLabel, false);
        GridDataFactory.swtDefaults()
                .align(SWT.RIGHT, SWT.BOTTOM)
                .grab(true, true)
                .applyTo(this.createButton);

        this.createButton.setEnabled(false);
        getShell().setDefaultButton(this.createButton);

        Button closeButton = createButton(
                panel,
                IDialogConstants.CANCEL_ID,
                Messages.getString("AbstractStyleEditorDialog.cancelButton"), false); //$NON-NLS-1$
        GridDataFactory.swtDefaults()
                .align(SWT.RIGHT, SWT.BOTTOM)
                .grab(false, false)
                .applyTo(closeButton);

        return panel;
    }

    private Composite createInputPanel(final Composite composite) {
        final Composite row = new Composite(composite, SWT.NONE);
        row.setLayout(new GridLayout(2, false));

        final Label createLabel = new Label(row, SWT.NONE);
        createLabel.setText(this.dialogLabels.createGroupLabel + ":"); //$NON-NLS-1$
        GridDataFactory.fillDefaults()
                .align(SWT.LEFT, SWT.CENTER)
                .applyTo(createLabel);

        this.groupNameText = new Text(row, SWT.SINGLE | SWT.BORDER);
        GridDataFactory.fillDefaults()
                .grab(true, false)
                .applyTo(this.groupNameText);

        this.groupNameText.addModifyListener(e -> HeaderGroupNameDialog.this.createButton.setEnabled(HeaderGroupNameDialog.this.groupNameText.getText().length() > 0));

        return row;
    }

    @Override
    protected void okPressed() {
        this.groupName = this.groupNameText.getText();
        super.okPressed();
    }

    /**
     *
     * @return The group name that was entered in the input field.
     */
    public String getGroupName() {
        return this.groupName;
    }
}
