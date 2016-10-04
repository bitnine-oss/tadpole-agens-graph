/*******************************************************************************
 * Copyright 2016 hangum
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.bitnine.tadpole.graph.core.editor.extension.browserHandler;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;

import com.bitnine.tadpole.graph.core.editor.extension.AgensGraphEditor;

/**
 * Cypher browser function
 * 
 * @author hangum
 *
 */
public class CypherFunctionService extends BrowserFunction {
	private static final Logger logger = Logger.getLogger(CypherFunctionService.class);
	private Browser browser;

	public CypherFunctionService(Browser browser, String name) {
		super(browser, name);
		this.browser = browser;
	}
	
	@Override
	public Object function(Object[] arguments) {
		
		int intActionId =  NumberUtils.toInt(arguments[0].toString());
		if(logger.isDebugEnabled()) logger.debug("==========>  CypherFunctionService called " + arguments[2]+" : "  + arguments[0] +" : " + arguments[1]);
		
		switch (intActionId) {
		case CypherEditorFunction.SAVE_OPTIONS:
			saveOptions((String) arguments[1]);
			break;
			
		case CypherEditorFunction.LOAD_DATA:
			return loadData((String) arguments[1], (String) arguments[2]);
			//break;
			
		default:
				logger.error("Undefined Action ID : " + intActionId);
				return null;
		}
		
		return null;
	}
	
	protected String loadData(final String command, final String callback) {
		String result = "";
		try {
						
			//result = "{\"results\":[{\"columns\":[\"count\"],\"data\":[{\"row\":[2],\"meta\":[null]}]}],\"errors\":[]}";
					
		} catch (Exception e) {
			logger.error("loadData exception", e);
		}
		return result;
	}
	
	/**
	 * save options
	 */
	protected void saveOptions(String userData) {
//		try {
//			SpatialGetPreferenceData.updateUserOptions(userData);
//		} catch (Exception e) {
//			logger.error("Spatial save options", e);
//		}
	}
}
