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

/**
 * editor browser function 정의 
 * 
 * @author hangum
 *
 */
public interface CypherEditorFunction  {
	/**
	 * save option
	 */
	public static final int LOAD_DATA				= 1;
	public static final int SAVE_OPTIONS				= 5;
    
	/**
	 * spatial browser browser handle name
	 */
    public static final String GRAPH_SERVICE_HANDLER = "TadpoleBrowserHandler";
    
}
