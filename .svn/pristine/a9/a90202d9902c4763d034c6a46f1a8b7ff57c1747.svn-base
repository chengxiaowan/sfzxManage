package com.yocto.controller.business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.yocto.util.ObjectExcelRead;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;

public class TestController {
	public void importExcel() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String connUrl = "jdbc:mysql://121.40.107.201:3306/yq?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&zeroDateTimeBehavior=convertToNull";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(connUrl, "yocto", "yocto123/*-");
			int n = 0;
			// String filePath = "C:\\Users\\wxh\\Desktop\\7-22 36kr";
			// String fileName = "36kr.xlsx";
			String filePath = "C:\\Users\\wxh\\Desktop\\6-16 386企业项目整理";
			String fileName = "企业服务项目整理.xlsx";
			List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath, fileName, 1, 0, 14); // 执行读EXCEL操作,读出的数据导入List 1:从第2行开始；0:从第A列开始；0:第0个sheet
			Set<String> noProduct = new HashSet<String>();// 当前没有的产品
			Set<String> noCompany = new HashSet<String>();// 当前没有的公司
			Set<String> company = new HashSet<String>();// 所有的公司
			int nopNum = 0;
			int nocNum = 0;
			int cNum = 0;
			stmt = conn.createStatement();
			/**
			 * var0:产品分类 var1:产品名称 var2:合作客户 var3:公司名称var4:公司网址
			 */
			for (int i = 0; i < listPd.size(); i++) {
				String productName = TextUtil.transNull(listPd.get(i).getString("var1")).trim();// 产品名称
				// if (TextUtil.isNotNull(productName)) {
				// if (getProductByName(conn, productName, true) == 0) {
				// noProduct.add(productName);
				// nopNum++;
				// // 增加产品信息
				// // String sql = "insert into product_info (name,flag) values ('" + productName + "',1)";
				// // int row = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
				// // rs = stmt.getGeneratedKeys();
				// // if (row > 0 && rs.next()) {
				// // int productId = rs.getInt(row);
				// // System.out.println("添加产品成功：" + productId);
				// // }
				// }
				// }
				// String customerName = TextUtil.transNull(listPd.get(i).getString("var2")).trim();// 合作客户
				// if (TextUtil.isNotNull(customerName)) {
				// String[] nameArr = customerName.split("、");
				// if (null != nameArr && nameArr.length > 0) {
				// for (String str : nameArr) {
				// if (TextUtil.isNotNull(str)) {
				// if (getCompanyByName(conn, str.trim(), true) == 0) {
				// noCompany.add(str.trim());
				// nocNum++;
				// // 增加公司信息
				// // int companyId = addCompany(conn, str.trim(), str.trim(), "");
				// // System.out.println("添加公司成功1：" + companyId);
				// }
				// company.add(str.trim());
				// cNum++;
				// }
				// }
				// }
				// }
				String companyName = TextUtil.transNull(listPd.get(i).getString("var3")).trim();// 公司名称
				addProductRelationship(conn, productName, companyName);
				// String homePage = TextUtil.transNull(listPd.get(i).getString("var4")).trim();// 公司网址
				// if (TextUtil.isNotNull(companyName)) {
				// if (getCompanyByName(conn, companyName, true) == 0) {
				// noCompany.add(companyName);
				// nocNum++;
				// // 增加公司信息
				// // int companyId = addCompany(conn, companyName, companyName, homePage);
				// // System.out.println("添加公司成功2：" + companyId);
				// }
				// company.add(companyName);
				// cNum++;
				// }
				// System.out.println("产品名称:" + productName);
				// System.out.println("合作客户:" + customerName);
				// System.out.println("公司名称:" + companyName);
				// System.out.println("公司网址:" + homePage);
			}
			// System.out.println("数据库中当前没有的产品,共" + nopNum + "个:" + noProduct);
			// System.out.println("");
			// System.out.println("数据库中当前没有的公司,共" + nocNum + "个:" + noCompany);
			// System.out.println("excel中所有的公司,共" + cNum + "个:" + company);
			// System.out.println("");
			// PageData pd = new PageData();
			// for (String companyName : company) {
			// ArrayList<String> productList = new ArrayList<String>();
			// for (int i = 0; i < listPd.size(); i++) {
			// String customerName = TextUtil.transNull(listPd.get(i).getString("var2"));// 合作客户
			// String[] nameArr = customerName.split("、");
			// if (null != nameArr && nameArr.length > 0) {
			// for (String str : nameArr) {
			// if (companyName.trim().equals(str.trim())) {
			// String productName = TextUtil.transNull(listPd.get(i).getString("var1"));// 产品名称
			// productList.add(productName);
			// break;
			// }
			// }
			// }
			// }
			// pd.put(companyName, productList);
			// }
			// // System.out.println(pd);
			// Set<?> set = pd.entrySet();
			// Iterator<?> i = set.iterator();
			// System.out.println("公司使用的产品:");
			// while (i.hasNext()) {
			// Map.Entry<String, ArrayList<String>> entry1 = (Map.Entry<String, ArrayList<String>>) i.next();
			// String companyName = entry1.getKey();
			// ArrayList<String> productList = entry1.getValue();
			// System.out.println("公司名称:" + companyName + productList);
			// if (TextUtil.isNotNull(companyName) && null != productList && productList.size() > 0) {
			// // addCompanyProduct(conn, companyName, productList);
			// }
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
				if (null != stmt) {
					stmt.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 检查产品是否存在并返回ID
	 * 
	 * @param conn
	 * @param productName
	 * @return
	 */
	private int getProductByName(Connection conn, String productName, boolean isVague) {
		int id = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT id FROM `product_info` where ";
			String str = isVague ? "`name` like '%" + productName + "%' LIMIT 1" : "`name` = '" + productName + "' LIMIT 1";
			// System.out.println("getProductByName_SQL:" + sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql + str);
			if (null != rs && rs.next()) {
				id = rs.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
				if (null != stmt) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	/**
	 * 检查公司是否存在并返回ID
	 * 
	 * @param conn
	 * @param companyName
	 * @param type
	 * @return
	 */
	private int getCompanyByName(Connection conn, String companyName, boolean isVague) {
		int id = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT id FROM `company_info` where ";
			String str = isVague ? "`name` like '%" + companyName + "%' LIMIT 1" : "`name` = '" + companyName + "' LIMIT 1";
			// System.out.println("getCompanyByName_SQL:" + sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql + str);
			if (null != rs && rs.next()) {
				id = rs.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
				if (null != stmt) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	/**
	 * 新增公司信息
	 * 
	 * @param conn
	 * @param companyName
	 * @param logo
	 * @param url
	 * @param setUpTime
	 * @param companySize
	 * @param operationStatus
	 * @return
	 */
	private int addCompany(Connection conn, String companyName, String nickName, String url) {
		int id = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "INSERT INTO `company_info` (`name`,`nickName`,`url`,flag) VALUES ('" + companyName + "','" + nickName + "','" + url + "',1)";
			System.out.println("addCompany_SQL:" + sql);
			stmt = conn.createStatement();
			int row = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (row > 0 && rs.next()) {
				id = rs.getInt(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
				if (null != stmt) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	/**
	 * 保存公司所用产品的信息
	 * 
	 * @param conn
	 * @param productId
	 * @param relateId
	 * @param type
	 * @return
	 */
	private int addCompanyProduct(Connection conn, String companyName, ArrayList<String> productList) {
		int n = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			int companyId = getCompanyByName(conn, companyName, false);
			if (companyId > 0) {
				for (String productName : productList) {
					int productId = getProductByName(conn, productName, false);
					if (productId > 0) {
						String sql = "INSERT INTO `company_product` (`productId`, `companyId`, `flag`) VALUES (" + productId + ", " + companyId + ", " + 1 + ")";
						System.out.println("addProductRelationship_SQL:" + sql);
						stmt = conn.createStatement();
						n = stmt.executeUpdate(sql);
					} else {
						productId = getProductByName(conn, productName, true);
						if (productId > 0) {
							String sql = "INSERT INTO `company_product` (`productId`, `companyId`, `flag`) VALUES (" + productId + ", " + companyId + ", " + 1 + ")";
							System.out.println("addProductRelationship_SQL:" + sql);
							stmt = conn.createStatement();
							n = stmt.executeUpdate(sql);
						}
					}
				}
			} else {
				companyId = getCompanyByName(conn, companyName, true);
				if (companyId > 0) {
					for (String productName : productList) {
						int productId = getProductByName(conn, productName, false);
						if (productId > 0) {
							String sql = "INSERT INTO `company_product` (`productId`, `companyId`, `flag`) VALUES (" + productId + ", " + companyId + ", " + 1 + ")";
							System.out.println("addProductRelationship_SQL:" + sql);
							stmt = conn.createStatement();
							n = stmt.executeUpdate(sql);
						} else {
							productId = getProductByName(conn, productName, true);
							if (productId > 0) {
								String sql = "INSERT INTO `company_product` (`productId`, `companyId`, `flag`) VALUES (" + productId + ", " + companyId + ", " + 1 + ")";
								System.out.println("addProductRelationship_SQL:" + sql);
								stmt = conn.createStatement();
								n = stmt.executeUpdate(sql);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
				if (null != stmt) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return n;
	}

	/**
	 * 保存产品和公司的关联关系
	 * 
	 * @param conn
	 * @param productId
	 * @param relateId
	 * @param type
	 * @return
	 */
	private int addProductRelationship(Connection conn, String productName, String companyName) {
		int n = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {

			int productId = getProductByName(conn, productName, false);
			int companyId = getCompanyByName(conn, companyName, false);
			if (productId > 0 && companyId > 0) {
				String sql = "INSERT INTO `product_relationship` (`productId`, `relateId`, `type`, `flag`) VALUES (" + productId + ", " + companyId + ", 0,1)";
				System.out.println("addProductRelationship_SQL:" + sql);
				stmt = conn.createStatement();
				n = stmt.executeUpdate(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
				if (null != stmt) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return n;
	}

	public static void main(String[] args) {

		System.out.println("     s  /  dddddd  ".trim().replaceAll(" ", ""));
	}
}
