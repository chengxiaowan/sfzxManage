package com.yocto.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.sun.org.apache.xpath.internal.XPathAPI;

/**
 * @Description: 进行XML文件处理的通用处理类.
 * @Create DateTime: 2014-11-14 上午9:24:24
 * 
 * @author wangxiaohui
 */
public class XmlUtil {
	public static org.w3c.dom.Element[] editCrmElements(String sheetInfoTemp) {
		org.w3c.dom.Element[] elements = new org.w3c.dom.Element[1];
		try {
			StringBuffer returnXmlStr = new StringBuffer();
			returnXmlStr.append(sheetInfoTemp);
			javax.xml.parsers.DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
			org.w3c.dom.Document doc = builder.parse(new ByteArrayInputStream(returnXmlStr.toString().getBytes("UTF-8")));
			elements[0] = doc.getDocumentElement();
			return elements;
		} catch (ParserConfigurationException e) {
			return null;
		} catch (SAXException e) {
			return null;
		} catch (IOException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static String parseNewWorkSheet(org.w3c.dom.Element[] elements, String xmlPath) throws Exception {
		String str = null;
		if (elements != null && TextUtil.isNotNull(xmlPath)) {
			Node businessTypeNode = XPathAPI.selectSingleNode((Node) elements[0], xmlPath);
			if (businessTypeNode != null) {
				Node txtNode = businessTypeNode.getFirstChild();
				if (txtNode != null) {
					str = txtNode.getNodeValue().trim();
				}
			}
		}
		return TextUtil.transNull(str).trim();
	}

	public static Node selectSingleNode(String express, Object source) {// 查找节点，并返回第一个符合条件节点
		Node result = null;
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		try {
			result = (Node) xpath.evaluate(express, source, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return result;
	}
}
