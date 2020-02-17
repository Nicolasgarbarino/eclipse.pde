/*******************************************************************************
 *
 * Contributors:
 * Steven Spungin <steven@spungin.tv> - Bug 431735, Bug 437890, Bug 440469
 ******************************************************************************/

package org.eclipse.e4.tools.emf.editor3x;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.tools.emf.ui.common.ModelEditorPreferences;
import org.eclipse.e4.tools.emf.ui.common.Plugin;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class ModelEditorPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	private BooleanFieldEditor fAutoGeneratedId;
	private BooleanFieldEditor fShowXMIId;
	private ColorFieldEditor fColorValueNotRendered;
	private ColorFieldEditor fColorValueNotVisible;
	private ColorFieldEditor fColorValueNotVisibleAndRendered;
	private BooleanFieldEditor fShowSearch;
	private BooleanFieldEditor fRememberColumns;
	private BooleanFieldEditor fRememberFilters;

	public ModelEditorPreferencePage() {
	}

	public ModelEditorPreferencePage(String title) {
		super(title);
	}

	public ModelEditorPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, Plugin.ID));
	}

	@Override
	protected Control createContents(Composite parent) {
		final Composite result = new Composite(parent, SWT.NONE);
		result.setLayout(new GridLayout());

		{
			final Group group = new Group(result, SWT.NONE);
			group.setText(Messages.ModelEditorPreferencePage_Color);
			group.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false, 2, 1));
			group.setLayout(new GridLayout(2, false));

			{
				fColorValueNotRendered = new ColorFieldEditor(ModelEditorPreferences.NOT_RENDERED_COLOR,
					Messages.ModelEditorPreferencePage_NotRenderedColor, group);
				fColorValueNotRendered.setPage(this);
				fColorValueNotRendered.setPreferenceStore(getPreferenceStore());
				fColorValueNotRendered.load();
			}

			{
				fColorValueNotVisible = new ColorFieldEditor(ModelEditorPreferences.NOT_VISIBLE_COLOR,
					Messages.ModelEditorPreferencePage_NotVisibleColor, group);
				fColorValueNotVisible.setPage(this);
				fColorValueNotVisible.setPreferenceStore(getPreferenceStore());
				fColorValueNotVisible.load();
			}

			{
				fColorValueNotVisibleAndRendered = new ColorFieldEditor(
					ModelEditorPreferences.NOT_VISIBLE_AND_RENDERED_COLOR,
					Messages.ModelEditorPreferencePage_NotVisibleAndNotRenderedColor, group);
				fColorValueNotVisibleAndRendered.setPage(this);
				fColorValueNotVisibleAndRendered.setPreferenceStore(getPreferenceStore());
				fColorValueNotVisibleAndRendered.load();
			}
		}

		{
			final Group group = new Group(result, SWT.NONE);
			group.setText(Messages.ModelEditorPreferencePage_FormTab);
			group.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false, 2, 1));
			group.setLayout(new GridLayout(2, false));

			{
				final Composite container = new Composite(group, SWT.NONE);
				container.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false, 2, 1));
				fAutoGeneratedId = new BooleanFieldEditor(ModelEditorPreferences.AUTO_CREATE_ELEMENT_ID,
					Messages.ModelEditorPreferencePage_GeneratedID, container);
				fAutoGeneratedId.setPage(this);
				fAutoGeneratedId.setPreferenceStore(getPreferenceStore());
				fAutoGeneratedId.load();
			}

			{
				final Composite container = new Composite(group, SWT.NONE);
				container.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false, 2, 1));
				fShowXMIId = new BooleanFieldEditor(ModelEditorPreferences.SHOW_XMI_ID, Messages.ModelEditorPreferencePage_ShowID, container);
				fShowXMIId.setPage(this);
				fShowXMIId.setPreferenceStore(getPreferenceStore());
				fShowXMIId.load();
			}

			{
				final Composite container = new Composite(group, SWT.NONE);
				container.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false, 2, 1));
				fShowSearch = new BooleanFieldEditor(ModelEditorPreferences.TAB_FORM_SEARCH_SHOW,
					Messages.ModelEditorPreferencePage_SearchableTree
						+ Messages.ModelEditorPreferencePage_ForcesReadOnlyXMITab + Messages.ModelEditorPreferencePage_RequiresReopeningModel, container);
				fShowSearch.setPage(this);
				fShowSearch.setPreferenceStore(getPreferenceStore());
				fShowSearch.load();
			}

		}

		{
			final Group group = new Group(result, SWT.NONE);
			group.setText(Messages.ModelEditorPreferencePage_ListTabe);
			group.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false, 2, 1));
			group.setLayout(new GridLayout(2, false));

			{
				final Composite container = new Composite(group, SWT.NONE);
				container.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false, 2, 1));

				fRememberColumns = new BooleanFieldEditor(ModelEditorPreferences.LIST_TAB_REMEMBER_COLUMNS,
					Messages.ModelEditorPreferencePage_RememberColumnSettings, container);
				fRememberColumns.setPage(this);
				fRememberColumns.setPreferenceStore(getPreferenceStore());
				fRememberColumns.load();

				fRememberFilters = new BooleanFieldEditor(ModelEditorPreferences.LIST_TAB_REMEMBER_FILTERS,
					Messages.ModelEditorPreferencePage_RememberFilterSettings, container);
				fRememberFilters.setPage(this);
				fRememberFilters.setPreferenceStore(getPreferenceStore());
				fRememberFilters.load();
			}
		}

		return result;
	}

	@Override
	public boolean performOk() {
		fAutoGeneratedId.store();
		fShowXMIId.store();
		fColorValueNotRendered.store();
		fColorValueNotVisible.store();
		fColorValueNotVisibleAndRendered.store();
		fShowSearch.store();
		fRememberColumns.store();
		fRememberFilters.store();
		return super.performOk();
	}

	@Override
	protected void performDefaults() {
		fAutoGeneratedId.loadDefault();
		fShowXMIId.loadDefault();
		fColorValueNotRendered.loadDefault();
		fColorValueNotVisible.loadDefault();
		fColorValueNotVisibleAndRendered.loadDefault();
		fShowSearch.loadDefault();
		fRememberColumns.loadDefault();
		fRememberFilters.loadDefault();
		super.performDefaults();
	}
}
