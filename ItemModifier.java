package org.DandelionSeed.javaEx;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class ItemModifier
{
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerException
	{

		String filePath = "D:\\workspace\\tos\\INTEGRATION_DEV\\process";
		String fileName = "CustomerToDIM_0.1.item";
		
		Map<String, Object> map = new ConcurrentHashMap<String, Object>();
		
		DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = fac.newDocumentBuilder();
		Document clientDoc = builder.parse(filePath + File.separator + fileName);
		clientDoc.normalize();
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		NodeList tdNodes = (NodeList) xpath.evaluate("//node", clientDoc, XPathConstants.NODESET);
		int tMapIdx = -1;
		
		for(int i = 0; i < tdNodes.getLength(); i++) {
			if("tMap".equals(tdNodes.item(i).getAttributes().getNamedItem("componentName").getNodeValue())) {
				//tMap node면 삭제를 위한 idx만 추출하고
				tMapIdx = i;
			}//if() end
		}//for() end
		
		Element root = clientDoc.getDocumentElement();
		
		if(tMapIdx >= 0) {
			Element tbdEle = (Element) tdNodes.item(tMapIdx);
			tdNodes.item(0).getParentNode().removeChild(tbdEle);
			
			NodeList connNodes = (NodeList) xpath.evaluate("//connection", clientDoc, XPathConstants.NODESET);
			for(int i = 0; i < connNodes.getLength(); i++) {
				tbdEle = (Element) connNodes.item(i);
				connNodes.item(i).getParentNode().removeChild(tbdEle);
			}//for() end
		}else {
			root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			root.setAttribute("xmlns:TalendMapper", "http://www.talend.org/mapper");
			
			NodeList subjobs = (NodeList) xpath.evaluate("//subjob", clientDoc, XPathConstants.NODESET);
			for(int i = 1; i < subjobs.getLength(); i++) {
				Element tbdEle = (Element) subjobs.item(i);
				tbdEle.getParentNode().removeChild(tbdEle);
			}//for() end
		}//if~else end
		
		NodeList metas = (NodeList) xpath.evaluate("//metadata", clientDoc, XPathConstants.NODESET);
		for(int i = 0; i < metas.getLength(); i++) {
			Element ele = (Element) metas.item(i);
			
			if("REJECT".equals(ele.getAttribute("name"))) {
				map.put("outnode", ele.getParentNode().getAttributes().getNamedItem("componentName").getNodeValue());
				map.put("outcompnt", ele.getParentNode().getFirstChild().getNextSibling().getAttributes().getNamedItem("value").getNodeValue());
			}else {
				//reject metadata node가 아니면 input or output
				if(ele.getNextSibling().getNextSibling() == null) {
					map.put("incompnt", ele.getAttribute("name"));
				}//if() end
			}//if~else() end
		}//for() end
		
		for(int i = 0; i < metas.getLength(); i++) {
			Element ele = (Element) metas.item(i);
			
			if("out".equals(ele.getAttribute("name")) || map.get("incompnt").equals(ele.getAttribute("name"))) {
				
				//target DB column list
				String dbName = ele.getAttribute("label");
				NodeList colList = (NodeList) ele.getChildNodes();
				Set<String[]> set= new HashSet<>();
				
				for(int j = 0; j < colList.getLength(); j++) {
					if(colList.item(j).getNodeType() == Node.ELEMENT_NODE) {
						String[] strArr = {colList.item(j).getAttributes().getNamedItem("name").getNodeValue(),
								colList.item(j).getAttributes().getNamedItem("type").getNodeValue()};
						set.add(strArr);
					}//if() end
				}//for() end
				if("out".equals(ele.getAttribute("name"))) {
					map.put("outcolmeta", ele);
					map.put("outcolumns", set);
				}else {
					map.put("incolumns", set);
				}//if~else() end
			}//if() end
			
		}//for() end
		
		String strTMapNode =
				"<node componentName=\"tMap\" componentVersion=\"2.1\" offsetLabelX=\"0\" offsetLabelY=\"0\" posX=\"608\" posY=\"160\">\r\n" + 
				"    <elementParameter field=\"TEXT\" name=\"UNIQUE_NAME\" value=\"tMap_1\" show=\"false\"/>\r\n" + 
				"    <elementParameter field=\"EXTERNAL\" name=\"MAP\" value=\"\"/>\r\n" + 
				"    <elementParameter field=\"CLOSED_LIST\" name=\"LINK_STYLE\" value=\"AUTO\"/>\r\n" + 
				"    <elementParameter field=\"DIRECTORY\" name=\"TEMPORARY_DATA_DIRECTORY\" value=\"\"/>\r\n" + 
				"    <elementParameter field=\"IMAGE\" name=\"PREVIEW\" value=\"_vAgAUI0WEeuAlZLtUPUwBw-tMap_1-PREVIEW.bmp\"/>\r\n" + 
				"    <elementParameter field=\"CHECK\" name=\"DIE_ON_ERROR\" value=\"true\" show=\"false\"/>\r\n" + 
				"    <elementParameter field=\"CHECK\" name=\"LKUP_PARALLELIZE\" value=\"false\" show=\"false\"/>\r\n" + 
				"    <elementParameter field=\"TEXT\" name=\"LEVENSHTEIN\" value=\"0\" show=\"false\"/>\r\n" + 
				"    <elementParameter field=\"TEXT\" name=\"JACCARD\" value=\"0\" show=\"false\"/>\r\n" + 
				"    <elementParameter field=\"CHECK\" name=\"ENABLE_AUTO_CONVERT_TYPE\" value=\"false\" show=\"false\"/>\r\n" + 
				"    <elementParameter field=\"TEXT\" name=\"ROWS_BUFFER_SIZE\" value=\"2000000\"/>\r\n" + 
				"    <elementParameter field=\"CHECK\" name=\"CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL\" value=\"true\"/>\r\n" + 
				"    <elementParameter field=\"TEXT\" name=\"CONNECTION_FORMAT\" value=\"row\"/>\r\n" + 
				"</node>";
		
		Node tMapNode = builder.parse(new InputSource(new StringReader(strTMapNode))).getDocumentElement();
		
		Node outColumnMetaNode = (Node) map.get("outcolmeta");
		
		Document tMapOwnerDoc = tMapNode.getOwnerDocument();
		Node importedoutColNode = tMapOwnerDoc.importNode(outColumnMetaNode, true);
		tMapNode.appendChild(importedoutColNode);
		
		String strNodeData =
				"<nodeData xsi:type=\"TalendMapper:MapperData\">\r\n" + 
				"  <uiProperties shellMaximized=\"true\"/>\r\n" + 
				"  <varTables sizeState=\"INTERMEDIATE\" name=\"Var\" minimized=\"true\"/>\r\n" + 
				"</nodeData>";
		Node nodeData = builder.parse(new InputSource(new StringReader(strNodeData))).getDocumentElement();
		
		//mapperTableEndtries part
		String strOutputTable = "<outputTables sizeState=\"INTERMEDIATE\" name=\"out\"/>";
		Node outputTable = builder.parse(new InputSource(new StringReader(strOutputTable))).getDocumentElement();
		Document outputTableOwner = outputTable.getOwnerDocument();
		
		Set<String[]> outSet = (Set<String[]>) map.get("outcolumns");
		Iterator<String[]> iter2 = outSet.iterator();
		while(iter2.hasNext()) {
			String[] thisIter = iter2.next();
			Element ele = clientDoc.createElement("mapperTableEntries");
			ele.setAttribute("name", thisIter[0]);
			ele.setAttribute("type", thisIter[1]);
			ele.setAttribute("expression", " ");
			//if(rs.getValue.equals(thisIter[0])
			Node importedEle = outputTableOwner.importNode(ele, true);
			outputTable.appendChild(importedEle);
		}//while() end
		
		String strInputTable = "<inputTables sizeState=\"INTERMEDIATE\" name=\"row1\" matchingMode=\"UNIQUE_MATCH\" lookupMode=\"LOAD_ONCE\"/>";
		Node inputTable = builder.parse(new InputSource(new StringReader(strInputTable))).getDocumentElement();
		
		Set<String[]> inSet = (Set<String[]>) map.get("incolumns");
		Iterator<String[]> iter = inSet.iterator();
		
		Document inputTableOwner = inputTable.getOwnerDocument();
		
		while(iter.hasNext()) {
			String[] thisIter = iter.next();
			Element ele = clientDoc.createElement("mapperTableEntries");
			ele.setAttribute("name", thisIter[0]);
			ele.setAttribute("type", thisIter[1]);
			
			Node importedEle = inputTableOwner.importNode(ele, true);
			inputTable.appendChild(importedEle);
		}//whlie() end
		
		Document nodeDataOwner = nodeData.getOwnerDocument();
		Node importedOutputTable = nodeDataOwner.importNode(outputTable, true);
		Node importedinputTable = nodeDataOwner.importNode(inputTable, true);
		
		nodeData.appendChild(importedOutputTable);
		nodeData.appendChild(importedinputTable);
		
		Node importedNodeData = tMapOwnerDoc.importNode(nodeData, true);
		
		tMapNode.appendChild(importedNodeData);
		Node importedTMapNode = clientDoc.importNode(tMapNode, true);
		root.appendChild(importedTMapNode);
		
		String strRow1Conn = 
				"<connection connectorName=\"FLOW\" label=\"row1\" lineStyle=\"0\" metaname=\"" + map.get("incompnt") +
				"\" offsetLabelX=\"0\" offsetLabelY=\"0\" source=\"" + map.get("incompnt") + "\" target=\"tMap_1\">\r\n" + 
				"  <elementParameter field=\"CHECK\" name=\"MONITOR_CONNECTION\" value=\"false\"/>\r\n" + 
				"  <elementParameter field=\"TEXT\" name=\"UNIQUE_NAME\" value=\"row1\" show=\"false\"/>\r\n" + 
				"</connection>";
		
		String strOutConn = 
				"<connection connectorName=\"FLOW\" label=\"out\" lineStyle=\"0\" metaname=\"out\" offsetLabelX=\"0\" offsetLabelY=\"0\"" +
				" source=\"tMap_1\" target=\"" + map.get("outcompnt") + "\">\r\n" + 
				"  <elementParameter field=\"CHECK\" name=\"MONITOR_CONNECTION\" value=\"false\"/>\r\n" + 
				"  <elementParameter field=\"TEXT\" name=\"UNIQUE_NAME\" value=\"out\" show=\"false\"/>\r\n" + 
				"</connection>";
		
		Node row1Conn = builder.parse(new InputSource(new StringReader(strRow1Conn))).getDocumentElement();
		Node outConn = builder.parse(new InputSource(new StringReader(strOutConn))).getDocumentElement();
		
		Node importedRow1Conn = clientDoc.importNode(row1Conn, true);
		Node importedOutConn = clientDoc.importNode(outConn, true);
		
		root.appendChild(importedRow1Conn);
		root.appendChild(importedOutConn);
		
		TransformerFactory tfFactory = TransformerFactory.newInstance();
		Transformer tf = tfFactory.newTransformer();
		DOMSource source = new DOMSource(clientDoc);
		StreamResult result = new StreamResult(System.out);
		tf.transform(source, result);
		
		
	}//main() end
	
}//class ItemModifier end
