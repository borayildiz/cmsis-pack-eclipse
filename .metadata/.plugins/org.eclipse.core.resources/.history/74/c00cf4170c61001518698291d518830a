package com.bora.logger.views;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

import com.bora.logger.file.Log;

/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class ListView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.bora.exercises.listview.views.ListView";

	public static TableViewer viewer;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	
	/**
	 * The constructor.
	 */
	public ListView() {
	}	
	

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	
	/*
	 * View Content Provider
	 */
	class ViewContentProvider implements IStructuredContentProvider {
		
		//Input Changed
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		
		//Dispose
		public void dispose() {
		}
		
		//Get Elements
		public Object[] getElements(Object parent) {
			
			ArrayList<String> inputList = new ArrayList<>();
			

			try {
				for (String line : Files.readAllLines(Paths.get("log.txt"))) {
					inputList.add(line);	
				}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

						
			String[] inputArray = new String[inputList.size()];
			inputArray = inputList.toArray(inputArray);
			return inputArray;
		}
	}
	
	
	
	
	/*
	 * View Label Provider
	 */
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		
		//Get Column Text
		public String getColumnText(Object obj, int index){ 
		
			return getText(obj);
		}
		
		//Get Column Image
		public Image getColumnImage(Object obj, int index){ 
		
			return getImage(obj);
		}
		
		//Get Image
		public Image getImage(Object obj){
		
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);				
		}
	}
	
	
	/*
	 * Name Sorter
	 */
	class NameSorter extends ViewerSorter {
	}

	
	
	/**
	 * CreatePartControl:
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(null);
		viewer.setInput(getViewSite());

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "com.bora.exercises.listview.viewer");
		getSite().setSelectionProvider(viewer);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	/*
	 * Hook Context Menu
	 */
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ListView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	/*
	 * Contribute To Action Bars
	 */
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	/*
	 * Fill Local Pull Down
	 */
	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	
	/*
	 * Fill Context Menu
	 */
	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	/*
	 * Fill Local Toolbar
	 */
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
	}

	/*
	 * Make Actions
	 */
	private void makeActions() {
		//Action1: Refresh the listview
		action1 = new Action() {
			public void run() {				
				viewer.refresh();
				
			}
		};
		action1.setText("Info");
		action1.setToolTipText("Info");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		
		//Action2: clear the log and refresh the listview
		action2 = new Action() {
			public void run() {
				
				Log.clear();
				viewer.refresh();
			}
		};
		action2.setText("Clear");
		action2.setToolTipText("Clear");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				showMessage("Double-click detected on "+obj.toString());
			}
		};
	}

	//Hook DoubleClick Action
	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	
	//Show Message
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"ListView",
			message);			
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
