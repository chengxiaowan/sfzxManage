package com.yocto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.xpath.internal.XPathAPI;
import com.yocto.util.TextUtil;

public class Test {

	private static org.w3c.dom.Element[] editCrmElements(String sheetInfoTemp) {
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

	private static String parseNewWorkSheet(org.w3c.dom.Element[] elements, String xmlPath) throws Exception {
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

	public static List<Map<String, Object>> getMap(List<Map<String, Object>> list) {
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		String mark = "";
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
			if (mark.indexOf(Integer.valueOf(i).toString()) == -1) {
				map.put("name", list.get(i).get("name").toString());
				/*map.put("roleName", list.get(i).get("roleName").toString());
				map.put("userId", list.get(i).get("user_id").toString());*/
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("monthOrjd", list.get(i).get("monthOrjd").toString());
				map1.put("targetDetail", list.get(i).get("targetDetail").toString());
				list2.add(map1);
			}

			for (int j = i + 1; j < list.size() - 1; j++) {
				if (list.get(i).get("name").toString().equals(list.get(j).get("name").toString())) {
					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("monthOrjd", list.get(j).get("monthOrjd").toString());
					map1.put("targetDetail", list.get(j).get("targetDetail").toString());
					list2.add(map1);
					mark = mark + j + ",";
				}
			}
			if (list2.size() > 0) {
				map.put("xinxi", list2);
				list1.add(map);
			}
		}
		return list1;
	}

	public static void toQuchong(List<Integer> a) {
		for (int i = 0; i < a.size() - 1; i++) {
			for (int j = i + 1; j < a.size(); j++) {
				if (a.get(i) == a.get(j)) {
					a.remove(i);
					i--;
					break;
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		// List<Integer> list = new ArrayList<Integer>();
		// list.add(1);
		// list.add(1);
		// list.add(2);
		// list.add(2);
		// list.add(2);
		// list.add(3);
		// toQuchong(list);
		// System.out.println(list);
		/*String[] ids = new String[0];
		System.out.println(ids.length);*/

		String s = "{\"phoneNumber\":\"18912318756\",\"purePhoneNumber\":\"18912318756\",\"countryCode\":\"86\",\"watermark\":{\"timestamp\":1539161755,\"appid\":\"wx37a09ca1562e52ba\"}}";
		Gson gson = new Gson();
		Type type = new TypeToken<Map<Object, Object>>() {
		}.getType();
		Map<Object, Object> map1 = gson.fromJson(s, type);
		System.out.println(map1.get("purePhoneNumber"));
	}
}
