package com.bitnine.tadpole.graph.core.editor.extension;

import java.util.Map;

import net.bitnine.agensgraph.graph.Edge;
import net.bitnine.agensgraph.graph.Vertex;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.bitnine.tadpole.graph.core.editor.extension.browserHandler.CypherEditorFunction;
import com.bitnine.tadpole.graph.core.editor.extension.browserHandler.CypherFunctionService;
import com.google.gson.Gson;
import com.hangum.tadpole.commons.libs.core.define.SystemDefine;
import com.hangum.tadpole.engine.query.dao.system.UserDBDAO;
import com.hangum.tadpole.engine.sql.util.resultset.QueryExecuteResultDTO;
import com.hangum.tadpole.rdb.core.editors.main.MainEditor;
import com.hangum.tadpole.rdb.core.extensionpoint.definition.AMainEditorExtension;

/**
 * Agens graph ui
 * 
 * @author hangum
 *
 */
public class AgensGraphEditor extends AMainEditorExtension {
	private static final Logger logger = Logger.getLogger(AgensGraphEditor.class);

	protected Composite compositeBody;
	/** graph가 들어갈 브라우저 */
	protected Browser browserGraph;
	/** browser.browserFunction의 서비스 헨들러 */
	protected BrowserFunction editorService;

	/**
	 * @wbp.parser.entryPoint
	 */
	public AgensGraphEditor() {
	}

	@Override
	public void createPartControl(Composite parent, MainEditor mainEditor) {
		this.mainEditor = mainEditor;

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gl_composite = new GridLayout(1, false);
		gl_composite.verticalSpacing = 2;
		gl_composite.horizontalSpacing = 2;
		gl_composite.marginHeight = 2;
		gl_composite.marginWidth = 2;
		composite.setLayout(gl_composite);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		compositeBody = new Composite(composite, SWT.NONE);
		compositeBody.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		compositeBody.setLayout(new GridLayout(1, false));

		browserGraph = new Browser(compositeBody, SWT.BORDER);
		browserGraph.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		initUI();
	}

	@Override
	public void initExtension(UserDBDAO userDB) {
		if (userDB == null) {
			super.setEnableExtension(false);
			return;
		}

		super.initExtension(userDB);
		super.setEnableExtension(true);
	}

	/**
	 * UI가 처음 호출될때 초기화 합니다.
	 */
	public static final String DUMY_DATA = String.format("?%s=%s", SystemDefine.MAJOR_VERSION, SystemDefine.RELEASE_DATE);

	private void initUI() {
		try {
			browserGraph.setUrl("/resources/graph/TadpoleAgensGraph.html" + DUMY_DATA);
			registerBrowserFunctions();
		} catch (Exception e) {
			logger.error("initialize map initialize error", e);
		}
	}

	/**
	 * 쿼리를 agens graph에 맞게 조작한다.
	 */
	@Override
	public String sqlCostume(String strSQL) {

		//		Cypher 쿼리가 있다면 영역을 넓혀준다.
		try {
			super.setEnableExtension(true);

			// 컬럼이 있다면 mainEditor의 화면중에, 지도 부분의 영역을 30%만큼 조절합니다.
			mainEditor.getSashFormExtension().getDisplay().asyncExec(new Runnable() {
				public void run() {
					int[] intWidgetSizes = mainEditor.getSashFormExtension().getWeights();
					if (intWidgetSizes[0] != 100) {
						mainEditor.getSashFormExtension().setWeights(new int[] { 50, 50 });
					}
				}
			});
		} catch (Exception e) {
			super.setEnableExtension(false);
		}

		return strSQL;
	}

	/**
	 * register browser function
	 * 
	 */
	protected void registerBrowserFunctions() {
		try {
			editorService = new CypherFunctionService(browserGraph, CypherEditorFunction.GRAPH_SERVICE_HANDLER);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void resultSetClick(int selectIndex, Map<Integer, Object> mapColumns) {
		logger.debug("===> 쿼리 결과를 클릭했다..");
	}

	@Override
	public void resultSetDoubleClick(int selectIndex, Map<Integer, Object> mapColumns) {
		logger.debug("===> 쿼리 결과를 더블클릭했다..");
	}

	@Override
	public void queryEndedExecute(QueryExecuteResultDTO rsDAO) {
		logger.debug("===> 쿼리 결과를 끝냈다.");

		browserGraph.evaluate("clearAllLayersMap();");

		drawGraphData(rsDAO, "");

	}

	private void drawGraphData(final QueryExecuteResultDTO rsDAO, final String strUserOptions) {
		browserGraph.getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {

				AgensGraph graph = new AgensGraph();
				Node node = null;
				GEdge edge = null;

				for (Map<Integer, Object> column : rsDAO.getDataList().getData()) {

					for (int i = 0; i < column.size(); i++) {

						logger.debug("Column Size is " + column.size() + "");

						Object obj = column.get(i);

						logger.debug("Object is " + obj.toString() + ", " + obj.getClass().toString());

						if (obj instanceof Vertex) {
							node = new Node();

							Vertex vertex = (Vertex) obj;
							String getValue = vertex.getValue();
							getValue = StringUtils.replace(getValue, vertex.getLabel(), "");
							getValue = StringUtils.replace(getValue, "][", ",");
							getValue = StringUtils.replace(getValue, "[", ",");
							getValue = StringUtils.replace(getValue, "]", ",");
							String[] ids = StringUtils.split(getValue, ",");

							node.setId(ids[0]);
							String label = vertex.getProperty().getString("name");
							if (StringUtils.isBlank(label)) {
								node.setLabel(vertex.getLabel() + "_" + node.getId());
							} else {
								node.setLabel(label);
							}

							node.setX(Math.random() * 0.8);
							node.setY(Math.random() * 0.8);
						
							node.setColor(AgensUtils.getRandomRGB());
							node.setSize(500);
							graph.addNode(node);

							System.out.println("Vertex is " + vertex.toString());
						}
						if (obj instanceof Edge) {

							edge = new GEdge();

							Edge relation = (Edge) obj;

							String getValue = relation.getValue();
							getValue = StringUtils.replace(getValue, relation.getLabel(), "");
							getValue = StringUtils.replace(getValue, "][", ",");
							getValue = StringUtils.replace(getValue, "[", ",");
							getValue = StringUtils.replace(getValue, "]", ",");
							
							String[] ids = StringUtils.split(getValue, ",");
							edge.setId(ids[0]);
							edge.setLabel(relation.getLabel());
							edge.setSource(ids[1]);
							edge.setTarget(ids[2]);
							
							edge.setColor(AgensUtils.getRandomRGB("100"));
							edge.setSize(0.5);

							logger.debug("Relation is " + relation.toString() + ", edge is " + edge.toString());

							graph.addEdge(edge);
						}
					}
				}

				Gson gson = new Gson();
				String strGraphJson = gson.toJson(graph);

				logger.debug("##### Graph ####====>" + strGraphJson);
				browserGraph.evaluate(String.format("drawingGraphData('%s');", strGraphJson));

			}
		});
	}

}
