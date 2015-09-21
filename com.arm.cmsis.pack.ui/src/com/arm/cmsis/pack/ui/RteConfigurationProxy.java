/*******************************************************************************
* Copyright (c) 2014 ARM Ltd.
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*******************************************************************************/

package com.arm.cmsis.pack.ui;

import java.util.Collection;

import org.eclipse.core.runtime.ListenerList;

import com.arm.cmsis.pack.data.ICpDeviceItem;
import com.arm.cmsis.pack.data.ICpItem;
import com.arm.cmsis.pack.data.ICpPackFilter;
import com.arm.cmsis.pack.enums.EEvaluationResult;
import com.arm.cmsis.pack.events.IRteConfigurationProxy;
import com.arm.cmsis.pack.events.RteEvent;
import com.arm.cmsis.pack.events.IRteEventListener;
import com.arm.cmsis.pack.events.IRteEventProxy;
import com.arm.cmsis.pack.info.ICpConfigurationInfo;
import com.arm.cmsis.pack.info.ICpDeviceInfo;
import com.bora.logger.file.Log;
import com.arm.cmsis.pack.rte.IRteConfiguration;
import com.arm.cmsis.pack.rte.IRteDependencyItem;
import com.arm.cmsis.pack.rte.components.IRteComponent;
import com.arm.cmsis.pack.rte.components.IRteComponentItem;

/**
 *
 */
public class RteConfigurationProxy implements IRteConfigurationProxy {

	/**
	 * "Real" configuration: can be RteConfiguration or another proxy 
	 */
	private IRteConfiguration fConfiguration = null; 
	private ListenerList fRteListeners = new ListenerList();
	
	/**
	 *  Constructs proxy for given configuration object 
	 */
	public RteConfigurationProxy(IRteConfiguration configuration) {
		fConfiguration = configuration;
		if(fConfiguration != null)
			fConfiguration.setRteEventProxy(this);
		
		Log.writeCurrentConstructor("RteConfigurationProxy(IRteConfiguration configuration)");
	}


	@Override
	public void setRteEventProxy(IRteEventProxy rteEventProxy) {
		// TODO: provide a layer for tier proxies 
		if(fConfiguration != null)
			fConfiguration.setRteEventProxy(rteEventProxy);
		
		Log.writeCurrentMethod(rteEventProxy);
	}


	@Override
	public IRteEventProxy getRteEventProxy() {
		
		Log.writeCurrentMethod();
		
		if(fConfiguration != null)
			return fConfiguration.getRteEventProxy();
		return null;
	}

	@Override
	public void clear() {
		
		Log.writeCurrentMethod();
		
		if(fConfiguration != null)
			fConfiguration.clear();
	}


	@Override
	public ICpPackFilter getPackFilter() {
		
		Log.writeCurrentMethod();
		
		if(fConfiguration != null)
			return fConfiguration.getPackFilter();
		return null;
	}


	@Override
	public ICpDeviceItem getDevice() {
		
		Log.writeCurrentMethod();
		
		if(fConfiguration != null)
			return fConfiguration.getDevice();
		return null;
	}


	@Override
	public ICpDeviceInfo getDeviceInfo() {
		
		Log.writeCurrentMethod();
		
		if(fConfiguration != null)
			return fConfiguration.getDeviceInfo();
		return null; 
	}


	@Override
	public void setDeviceInfo(ICpDeviceInfo deviceInfo) {
		
		Log.writeCurrentMethod();
		
		if(fConfiguration != null)
			fConfiguration.setDeviceInfo(deviceInfo);
	}


	@Override
	public IRteComponentItem getComponents() {
		
		Log.writeCurrentMethod();
		
		if(fConfiguration != null)
			return fConfiguration.getComponents();
		return null;
	}


	@Override
	public EEvaluationResult evaluateDependencies() {
		
		Log.writeCurrentMethod();
		
		if(fConfiguration != null)
			return fConfiguration.evaluateDependencies();
		return EEvaluationResult.UNDEFINED;
	}

	@Override
	public Collection<IRteComponent> getSelectedComponents() {
		
		Log.writeCurrentMethod();
		
		if(fConfiguration != null)
			return fConfiguration.getSelectedComponents();
		return null;
	}


	@Override
	public Collection<IRteComponent> getUsedComponents() {
		
		Log.writeCurrentMethod();
		
		if(fConfiguration != null)
			return fConfiguration.getUsedComponents();
		return null;
	}


	@Override
	public void selectComponent(IRteComponent component, int nInstances) {
		
		Log.writeCurrentMethod(component, nInstances);
		
		if(fConfiguration != null)
			fConfiguration.selectComponent(component, nInstances);
	}


	@Override
	public void selectActiveChild(IRteComponentItem item, String childName) {
		
		Log.writeCurrentMethod(item, childName);
		
		if(fConfiguration != null)
			fConfiguration.selectActiveChild(item, childName);
	}


	@Override
	public void selectActiveVariant(IRteComponentItem item, String variant) {
		
		Log.writeCurrentMethod(item, variant);
		
		if(fConfiguration != null)
			fConfiguration.selectActiveVariant(item, variant);
	}


	@Override
	public void selectActiveVendor(IRteComponentItem item, String vendor) {
		
		Log.writeCurrentMethod(item, vendor);
		
		if(fConfiguration != null)
			fConfiguration.selectActiveVendor(item, vendor);
	}


	@Override
	public void selectActiveVersion(IRteComponentItem item, String version) {
		
		Log.writeCurrentMethod(item, version);
		
		if(fConfiguration != null)
			fConfiguration.selectActiveVersion(item, version);
	}


	@Override
	public void processRteEvent(RteEvent event) {
		
		Log.writeCurrentMethod(event);
		
		fireRteEvent(event);
	}

	@Override
	public void addRteEventListener(IRteEventListener listener) {
		
		Log.writeCurrentMethod(listener);
		
		fRteListeners.add(listener);
	}


	@Override
	public void removeRteEventListener(IRteEventListener listener) {
		
		Log.writeCurrentMethod(listener);
		
		fRteListeners.remove(listener);
	}

	@Override
	public void fireRteEvent(RteEvent event) {
		
		Log.writeCurrentMethod(event);
		
		for (Object obj : fRteListeners.getListeners()) {
			IRteEventListener listener = (IRteEventListener) obj;
			try {
				listener.handleRteEvent(event);
			} catch (Exception ex) {
				removeRteEventListener(listener);
			}
		} 
		
	}

	@Override
	public EEvaluationResult getEvaluationResult(IRteComponentItem item) {
		
		Log.writeCurrentMethod(item);
		
		if(fConfiguration != null)
			return fConfiguration.getEvaluationResult(item);
		return EEvaluationResult.UNDEFINED;
	}


	@Override
	public EEvaluationResult getEvaluationResult() {
		
		Log.writeCurrentMethod();
		
		if(fConfiguration != null)
			return fConfiguration.getEvaluationResult();
		return EEvaluationResult.UNDEFINED;
	}


	@Override
	public void setEvaluationResult(EEvaluationResult result) {
		
		Log.writeCurrentMethod(result);
		
		if(fConfiguration != null)		
			fConfiguration.setEvaluationResult(result);
		
	}

	@Override
	public Collection<? extends IRteDependencyItem> getDependencyItems() {
		
		Log.writeCurrentMethod();
		
		if(fConfiguration != null)
			return fConfiguration.getDependencyItems();
		return null;
	}


	@Override
	public EEvaluationResult resolveDependencies() {
		
		Log.writeCurrentMethod();
		
		if(fConfiguration != null)
			return fConfiguration.resolveDependencies();
		return null;
	}


	@Override
	public ICpConfigurationInfo getConfigurationInfo() {
		
		Log.writeCurrentMethod();
		
		if(fConfiguration != null)
			return fConfiguration.getConfigurationInfo();
		return null;
	}

	
	@Override
	public void setConfigurationInfo(ICpConfigurationInfo info) {
		
		Log.writeCurrentMethod(info);
		
		if(fConfiguration != null)
			fConfiguration.setConfigurationInfo(info);
	}


	@Override
	public ICpItem getToolchainInfo() {
		
		Log.writeCurrentMethod();
		
		if(fConfiguration != null)
			return fConfiguration.getToolchainInfo();
		return null;
	}


	@Override
	public void setToolchainInfo(ICpItem toolchainInfo) {
		
		Log.writeCurrentMethod(toolchainInfo);
		
		if(fConfiguration != null)
			fConfiguration.setToolchainInfo(toolchainInfo);
	}


	@Override
	public void setFilterAttributes(ICpDeviceInfo deviceInfo, ICpItem toolchainInfo) {
		
		Log.writeCurrentMethod(deviceInfo, toolchainInfo);
		
		if(fConfiguration != null)
			fConfiguration.setFilterAttributes(deviceInfo, toolchainInfo);
	}


	@Override
	public void apply() {
		
		Log.writeCurrentMethod();
		
		if(fConfiguration != null)
			fConfiguration.apply();
	}
}
