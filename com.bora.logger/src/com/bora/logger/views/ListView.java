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
 * This ListView just shows the path file content as a list.
 * There are two actions which are (i):refresh the list, (x):clear the list.
 * This class is adapted from ListView example in PDE.
 */

public class ListView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.bora.exercises.listview.views.ListView";

	public static TableViewer viewer;
	private static String path = "log.txt";
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	
	/**
	 * The constructor.
	 */
	public ListView() {
	}	
		
	/**
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
				for (String line : Files.readAllLines(Paths.get(path))) {
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
	
	
	
	
	/**
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
	
	
	/**
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

	/**
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

	/**
	 * Contribute To Action Bars
	 */
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	/**
	 * Fill Local Pull Down
	 */
	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	
	/**
	 * Fill Context Menu
	 */
	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	/**
	 * Fill Local Toolbar
	 */
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
	}

	/**
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
		
		//Double Click Action
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
