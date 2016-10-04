/*
 * define java browfunction code
 */
var editorService = {
	/** save option */
	LOAD_DATA           : "1",
	SAVE_OPTION			: "5"
};

$(document).ready(onLoad);

/**
 * save option
 * 
 * @param options
 */
function saveOption() {
	console.log('******************saveOption()************************');
	try {
		TadpoleBrowserHandler(editorService.SAVE_OPTION, JSON.stringify(options));
    } catch(e) {
		console.log(e);
	}
}

/*
 * initial event
 */
var tadpole_sigma;
var tadpole_graph;
function onLoad() {
	console.log('******************onLoad()************************');
	try {
		tadpole_graph = {
			      nodes: [],
			      edges: []
			    };
		
		tadpole_sigma = new sigma({
		  graph: tadpole_graph,
		  renderer: {
		    container: document.getElementById('graph-container'),
		    type: 'canvas'
		  },
		  settings: {
		    edgeLabelSize: 'proportional'
		  }
		});
	} catch (err) {
		console.log(err);
	}
}

function drawingGraphData(txtGraphJSON, txtUserOption) {
	console.log('******************drawingGraphData************************');
	try {	
		tadpole_sigma.refresh();

		sigma.parsers.jsonData(txtGraphJSON, tadpole_sigma, updateGraph);
		
	} catch (err) {
		console.log(err);
	}
	tadpole_sigma.refresh();
};

function updateGraph(sig, g){

	try {
		// Initialize the dragNodes plugin:
		var dragListener = sigma.plugins.dragNodes(sig, sig.renderers[0]);
		
		dragListener.bind('startdrag', function(event) {
		  console.log(event);
		});
		dragListener.bind('drag', function(event) {
		  console.log(event);
		});
		dragListener.bind('drop', function(event) {
		  console.log(event);
		});
		dragListener.bind('dragend', function(event) {
		  console.log(event);
		});
		
		
	} catch (err) {
		console.log(err);
	}
	
	sig.refresh();


}

/*
* clear map
*/
function clearAllLayersMap() {
	console.log('******************clearAllLayersMap************************');
	try {
		//resultLayer.clearLayers();
		//selectedLayer.clearLayers();
	} catch(err) {
		console.log("Rise exception(clearAllLayersMap function) : " + err);
	}
};

/*
* clear selected object
*/
function clearClickedLayersMap() {
	console.log('******************clearClickedLayersMap************************');
	try {
		selectedLayer.clearLayers();
	} catch(err) {
		console.log("Rise exception(cleareClickedLayersMap function) : " + err);
	}
};


function agensDataLoad(command, callback) {
	console.log('******************agensDataLoad************************');
	try {
		return TadpoleBrowserHandler(editorService.LOAD_DATA, JSON.stringify(command), callback);
    } catch(e) {
		console.log(e);
	}
}


function agensDataLoadCallback(returnData) {
	console.log('******************agensDataLoadCallback************************');
	try {
		var result = JSON.parse(returnData);
		console.log('수신결과:' + result);
	} catch (err) {
		console.log(err);
	}
}




/**
* add map data
*
* @param geoJSON initial map data
*/
function drawMapAddData(txtGeoJSON) {
	console.log('******************drawMapAddData************************');
	try {
		var geoJSON = jQuery.parseJSON(txtGeoJSON);
		resultLayer.addData(geoJSON);
		if (options.autoZoom)
			map.fitBounds(resultLayer.getBounds());
	} catch(err) {
		console.log(err);
	}
};

/**
* click event
*/
function onClickPoint(txtGeoJSON, txtToopTip) {
	console.log('******************onClickPoint************************');
	try {
		/* console.log("==> geojsonFeature: \n" + geoJSON ); */
		var geoJSON = jQuery.parseJSON(txtGeoJSON);
		selectedLayer.addData(geoJSON);
		bounds = L.geoJson(geoJSON).getBounds();
		map.fitBounds(bounds);
		if (bounds.getSouthWest() == bounds.getNorthEast()) { // 점인 경우 약간 축소 처리
			map.setZoom(map.getMaxZoom() - 2);
		}
		
		if(txtToopTip != "") selectedLayer.bindPopup(txtToopTip).openPopup();
	} catch(err) {
		console.log(err);
	}
};
