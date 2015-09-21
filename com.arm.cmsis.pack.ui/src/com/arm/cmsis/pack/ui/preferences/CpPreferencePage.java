package com.arm.cmsis.pack.ui.preferences;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.bora.logger.file.Log;
import com.arm.cmsis.pack.ui.CpPlugInUI;

public class CpPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public CpPreferencePage() {
		Log.writeCurrentConstructor("CpPreferencePage()");
	}


	@Override
	public void init(IWorkbench workbench) {
		Log.writeCurrentMethod(workbench);
		
		setPreferenceStore(CpPlugInUI.getDefault().getPreferenceStore());
	}


	@Override
	protected void createFieldEditors() {
		Log.writeCurrentMethod();
		
		addField(new DirectoryFieldEditor(CpPlugInUI.CMSIS_PACK_ROOT_PREFERENCE, "CMSIS Pack &root folder",getFieldEditorParent()));		 
			        		 
	}


	@Override
	public Image getImage() {
		Log.writeCurrentMethod();
		
		return CpPlugInUI.getImage(CpPlugInUI.ICON_RTEMANAGER);
	}
}
