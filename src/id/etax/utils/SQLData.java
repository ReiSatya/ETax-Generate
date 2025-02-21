package id.etax.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import id.etax.conn.DBEngine;

public class SQLData {
	private static Logger log = Logger.getLogger(SQLData.class.getName());
//	String sql = "SELECT \"OUTLET_NAME\", \"OUTLET_PHONE\" FROM \"SP_OUTLET\" WHERE \"OUTLET_ID\" = '" + id + "'";
	
	public static boolean checkOutletFile(String path, String period) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM \"tax_log\" WHERE \"log_path_pdf\" = ? AND \"log_period\" = ?";
		try {
			
			System.out.println(sql);
			conn = DBEngine.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, path);
			ps.setString(2, period);
			rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("masuk true di check outletfile");
				result = true;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static boolean checkOutletFileSummary(String path, String period) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM \"tax_log_summary\" WHERE \"log_path_pdf\" = ? AND \"log_period\" = ?";
		try {
			
			System.out.println(sql);
			conn = DBEngine.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, path);
			ps.setString(2, period);
			rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("masuk true di check outletfile");
				result = true;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static String getCIF(String id) {
		String result = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT \"cif\" FROM \"nasabah\" WHERE \"nama\" = ?";
		try {
			conn = DBEngine.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getString("cif");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static void InsertLogSCH(String start, String finish, int total,
			int suc, int fail, String type) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO \"tax_log_sch\" (\"log_sch_sd\", \"log_sch_fd\", \"log_sch_total\", \"log_sch_success\", \"log_sch_failed\", \"log_sch_type\")"
				+ "VALUES('" + start + "', '" + finish + "', '" + total + "', '"
				+ suc + "', '" + fail + "', '" + type + "')";
		try {
			System.out.println(sql);
			conn = DBEngine.getConnection();
			ps = conn.prepareStatement(sql);
			int i = ps.executeUpdate();
			if (i > 0) {
				System.out.println("success");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getNamabank(String tes1) {
		String sql = "SELECT namabank FROM nasabah WHERE cif = ?";
		try {
			Connection conn = DBEngine.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, tes1);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("namabank");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//	public static List<String> getContentPDF() {
//		List<String> result = new ArrayList<String>();
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		String sql = "";
//		List<String> cid = new ArrayList<String>();
//		cid.add("CID001");
//		cid.add("CID004");
//		cid.add("CID005");
//		try {
//			for (int i = 0; i < cid.size(); i++) {
//				sql = "SELECT \"content_d\" FROM \"content\" WHERE \"content_id\" ='"
//						+ cid.get(i) + "' AND \"Type\" = 'PDF'";
//				conn = DBEngine.getConnection();
//				ps = conn.prepareStatement(sql);
//				rs = ps.executeQuery();
//				while (rs.next()) {
//					result.add(rs.getString("content_d"));
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.info(e.getMessage());
//			// TODO: handle exception
//		} finally {
//			try {
//				if (conn != null) {
//					conn.close();
//				}
//				if (ps != null) {
//					ps.close();
//				}
//				if (rs != null) {
//					rs.close();
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}
	public static boolean checkGenerateID(String id) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT \"log_nosurat\" FROM \"tax_log\" WHERE \"log_nosurat\" = '"
				+ id + "'";
		try {
			conn = DBEngine.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("masuk true di check invoice");
				result = true;
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			e.getStackTrace();
			// TODO: handle exception
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public static boolean checkInvoice(String id, String period) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM \"tax_etax\" sinv JOIN \"tax_log\" sL ON sinv.\"cif\" = sL.\"log_cif\" WHERE \"cif\" ='"
				+ id + "' AND \"log_period\" = '" + period + "'";
		try {
//			System.out.println(sql);
			conn = DBEngine.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("masuk true di check invoice");
				result = true;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static boolean checkSummary(String id, String period) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM \"tax_summary\" sinv JOIN \"tax_log_summary\" sL ON sinv.\"cif\" = sL.\"log_cif\" WHERE \"cif\" ='"
				+ id + "' AND \"log_period\" = '" + period + "'";
		try {
//			System.out.println(sql);
			conn = DBEngine.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("masuk true di check invoice");
				result = true;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

//	public static boolean checkSTGInvoice(String id, String invo) {
//		boolean result = false;
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		String sql = "SELECT * FROM \"tax_stg_etax\" WHERE \"cif\" ='"
//				+ id + "' AND \"tax_no\" = '" + invo + "'";
//		System.out.println(sql);
//		try {
//			conn = DBEngine.getConnection();
//			ps = conn.prepareStatement(sql);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				result = true;
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//		} finally {
//			try {
//				if (conn != null) {
//					conn.close();
//				}
//				if (ps != null) {
//					ps.close();
//				}
//				if (rs != null) {
//					rs.close();
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}
	public static void updateLog(String id, String date, String status,
			String message, String pdfPath, String period,
			String invoice) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "";
		try {
			if (status.equals("R")) {
				sql = "UPDATE \"tax_log\" SET  \"log_generate_date\"='" + date
						+ "', \"log_status\"='" + status
						+ "', \"log_message\"='" + message
						+ "', \"log_path_pdf\"='" + pdfPath
						+ "' , \"log_nosurat\" = '" + invoice + "'  "
						+ " WHERE \"log_cif\"='" + id
						+ "' AND \"log_period\" = '" + period + "'";
				System.out.println("status" +sql);
			} else if (status.equals("N")) {
				sql = "UPDATE \"tax_log\" SET  \"log_generate_date\"='" + date
						+ "', \"log_status\"='" + status
						+ "', \"log_message\"='" + message + "'"
						+ " WHERE \"log_cif\"='" + id
						+ "' AND \"log_period\" = '" + period + "'";
			}
			conn = DBEngine.getConnection();
			ps = conn.prepareStatement(sql);
			int i = ps.executeUpdate();
			if (i > 0) {
				System.out.println("Success");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void updateLogSummary(String id, String date, String status,
			String message, String pdfPath, String period) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "";
		try {
			if (status.equals("R")) {
				sql = "UPDATE \"tax_log\" SET  \"log_generate_date\"='" + date
						+ "', \"log_status\"='" + status
						+ "', \"log_message\"='" + message
						+ "', \"log_path_pdf\"='" + pdfPath + "'  "
						+ " WHERE \"log_cif\"='" + id
						+ "' AND \"log_period\" = '" + period + "'";
				System.out.println("status" +sql);
			} else if (status.equals("N")) {
				sql = "UPDATE \"tax_log\" SET  \"log_generate_date\"='" + date
						+ "', \"log_status\"='" + status
						+ "', \"log_message\"='" + message + "'"
						+ " WHERE \"log_cif\"='" + id
						+ "' AND \"log_period\" = '" + period + "'";
			}
			conn = DBEngine.getConnection();
			ps = conn.prepareStatement(sql);
			int i = ps.executeUpdate();
			if (i > 0) {
				System.out.println("Success");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean checkLog(String id, String period) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM \"tax_log\" WHERE \"log_cif\"='" + id
				+ "' AND \"log_period\"='" + period + "'";
		try {
			conn = DBEngine.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = true;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static boolean checkLogSummary(String id, String period) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM \"tax_log_summary\" WHERE \"log_cif\"='" + id
				+ "' AND \"log_period\"='" + period + "'";
		try {
			conn = DBEngine.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = true;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static void deleteSTG(String id, String period) {
		Connection conn = null;
		PreparedStatement ps = null;
		String result = "";
		String sql = "DELETE FROM \"tax_stg_etax\"  WHERE \"cif\" ='"
				+ id + "' AND \"tax_period\"='" + period + "' ;";
		try {
			conn = DBEngine.getConnection();
			ps = conn.prepareStatement(sql);

			int res = ps.executeUpdate();
			if (res > 0) {
				result = "success";
				System.out.println(result);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = e.getMessage();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void recordInvoice(String id, String period) {
		Connection conn = null;
		PreparedStatement ps = null;
		String result = "";
		String sql = "INSERT INTO \"tax_etax\" SELECT * FROM \"tax_stg_etax\" WHERE \"cif\" ='"
				+ id + "' AND \"tax_period\"='" + period + "' ;";
		try {
			conn = DBEngine.getConnection();
			ps = conn.prepareStatement(sql);

			int res = ps.executeUpdate();
			if (res > 0) {
				result = "success";
				System.out.println(result);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = e.getMessage();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void recordInvoiceSummary(String id, String period) {
		Connection conn = null;
		PreparedStatement ps = null;
		String result = "";
		String sql = "INSERT INTO \"tax_summary\" SELECT * FROM \"tax_stg_etax\" WHERE \"cif\" ='"
				+ id + "' AND \"tax_period\"='" + period + "' ;";
		try {
			conn = DBEngine.getConnection();
			ps = conn.prepareStatement(sql);

			int res = ps.executeUpdate();
			if (res > 0) {
				result = "success";
				System.out.println(result);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = e.getMessage();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String insertSTGInvoice(String cif, 
			String tax_npwp,
			String tax_nama, String tax_alamat, String tax_jenispenghasilan,
			String tax_jumlahbunga, String tax_tarif, String tax_pphpotong,
			String tax_dnln, String tax_period) {
		String result = "";
		String sql = "INSERT INTO \"tax_stg_etax\" (\"cif\", "
				+ "\"tax_npwp\", \"tax_nama\", \"tax_alamat\", "
				+ "\"tax_jenispenghasilan\", \"tax_jumlahbunga\", \"tax_tarif\", \"tax_pphpotong\",\"tax_dnln\",\"tax_period\") "
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		// String sql = "INSERT INTO \"SP_STG_INVOICE\" (\"OUTLET_ID\",
		// \"INVOICE_DATE\", \"BRAND\", "
		// +"\"INVOICE_DUE\", \"INVOICE_NO\", \"AMOUNT\", \"REMAINING_AMOUNT\",
		// \"TOTAL\", "
		// +"\"OUTLET_NAME\", \"OUTLET_ADDRESS\", \"TTD_FAD_MGR\",
		// \"COMPANY_NAME\",\"COMPANY_ADDRESS\") "
		// +" VALUES('"+outletID+"', '"+INVOICE_DATE+"', '"+BRAND+"',
		// '"+INVOICE_DUE+"','"+INVOICE_NO+"', "
		// + ""+AMOUNT+", "+REMAINING_AMOUNT+", "+TOTAL+", '"+OUTLET_NAME+"',
		// '"+OUTLET_ADDRESS+"', '"+TTD_FAD_MGR+"', "
		// + "'"+COMPANY_NAME+"','"+COMPANY_ADDRESS+"')";
//		System.out.println("insertSTGInvoice sql "+sql);
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBEngine.getConnection();
			ps = conn.prepareStatement(sql);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// System.out.println(date1);
			// System.out.println(date2);
			ps.setString(1, cif);
			ps.setString(2, tax_npwp);
			ps.setString(3, tax_nama);
			ps.setString(4, tax_alamat);
			ps.setString(5, tax_jenispenghasilan);
			ps.setString(6, tax_jumlahbunga);
			ps.setString(7, tax_tarif);
			ps.setString(8, tax_pphpotong);
			ps.setString(9, tax_dnln);
			ps.setString(10, tax_period);
			
			int res = ps.executeUpdate();
			if (res > 0) {
				result = "success";
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = e.getMessage();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("berhasil insertSTGInvoice");
		return result;
	}
	
	public static String insertSTGInvoiceSumm(String cif, 
			String tax_npwp,
			String tax_nama, String tax_alamat, String tax_jenispenghasilan,
			String tax_jumlahbunga, String tax_tarif, String tax_pphpotong,
			String tax_dnln, String tax_period, String tax_namabank) {
		String result = "";
		String sql = "INSERT INTO \"tax_stg_etax\" (\"cif\", "
				+ "\"tax_npwp\", \"tax_nama\", \"tax_alamat\", "
				+ "\"tax_jenispenghasilan\", \"tax_jumlahbunga\", \"tax_tarif\", \"tax_pphpotong\",\"tax_dnln\",\"tax_period\",\"tax_namabank\") "
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBEngine.getConnection();
			ps = conn.prepareStatement(sql);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// System.out.println(date1);
			// System.out.println(date2);
			ps.setString(1, cif);
			ps.setString(2, tax_npwp);
			ps.setString(3, tax_nama);
			ps.setString(4, tax_alamat);
			ps.setString(5, tax_jenispenghasilan);
			ps.setString(6, tax_jumlahbunga);
			ps.setString(7, tax_tarif);
			ps.setString(8, tax_pphpotong);
			ps.setString(9, tax_dnln);
			ps.setString(10, tax_period);
			ps.setString(11, tax_namabank);
			
			int res = ps.executeUpdate();
			if (res > 0) {
				result = "success";
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = e.getMessage();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("berhasil insertSTGInvoice");
		return result;
	}
	
	public static boolean isTableEmpty(String tableName) {
        String query = "SELECT COUNT(*) FROM " + tableName;
        try (Connection connection = DBEngine.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

	public static String inputLOG(String id, String date, String status,
			String message, String pdfPath, String period,
			String invoice) {
		Connection conn = null;
		String result = "";
		PreparedStatement ps = null;
		String sql = "";
		try {
			conn = DBEngine.getConnection();
			
			String getMaxIdSql = "SELECT MAX(\"log_id\") FROM \"tax_log\"";
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(getMaxIdSql);
	        int newLogId = 1;
	        if (rs.next()) {
	            newLogId = rs.getInt(1) + 1;
	        }
	        rs.close();
	        stmt.close();
			
			if (status.equals("R")) {
				sql = "INSERT INTO \"tax_log\" (\"log_id\", \"log_cif\", \"log_generate_date\", \"log_status\", \"log_message\", \"log_path_pdf\", \"log_period\", \"log_nosurat\" ) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
				System.out.println("isinya inputlog"+ id+" : "+date+" : "+status+" : "+message+" : "+pdfPath+" : "+period+" : "+invoice);
				System.out.println("sql inputLog" +sql);
				ps = conn.prepareStatement(sql);
				ps.setInt(1, newLogId);
				ps.setString(2, id);
				ps.setDate(3, java.sql.Date.valueOf(date));
				ps.setString(4, status);
				ps.setString(5, message);
				ps.setString(6, pdfPath);
				ps.setString(7, period);
				ps.setString(8, invoice);
			} else if (status.equals("N")) {
				sql = "INSERT INTO \"tax_log\" (\"log_id\",\"log_cif\", \"log_generate_date\", \"log_status\", \"log_message\", \"log_period\") VALUES(?, ?, ?, ?, ?)";
				System.out.println(sql);
				ps = conn.prepareStatement(sql);
				ps.setInt(1, newLogId);
				ps.setString(2, id);
				ps.setString(3, date);
				ps.setString(4, status);
				ps.setString(5, message);
				ps.setString(6, period);
			}
			log.info("dari input "+sql);
			int res = ps.executeUpdate();
			
			if (res > 0) {
				result = "success";
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static String inputLOGSummary(String id, String date, String status,
			String message, String pdfPath, String period) {
		Connection conn = null;
		String result = "";
		PreparedStatement ps = null;
		String sql = "";
		try {
			conn = DBEngine.getConnection();
			
			String getMaxIdSql = "SELECT MAX(\"log_id\") FROM \"tax_log_summary\"";
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(getMaxIdSql);
	        int newLogId = 1;
	        if (rs.next()) {
	            newLogId = rs.getInt(1) + 1;
	        }
	        rs.close();
	        stmt.close();
			
			if (status.equals("R")) {
				sql = "INSERT INTO \"tax_log_summary\" (\"log_id\", \"log_cif\", \"log_generate_date\", \"log_status\", \"log_message\", \"log_path_pdf\", \"log_period\") VALUES(?, ?, ?, ?, ?, ?, ?)";
				System.out.println("isinya inputlog"+ id+" : "+date+" : "+status+" : "+message+" : "+pdfPath+" : "+period);
				System.out.println("sql inputLog" +sql);
				ps = conn.prepareStatement(sql);
				ps.setInt(1, newLogId);
				ps.setString(2, id);
				ps.setDate(3, java.sql.Date.valueOf(date));
				ps.setString(4, status);
				ps.setString(5, message);
				ps.setString(6, pdfPath);
				ps.setString(7, period);
			} else if (status.equals("N")) {
				sql = "INSERT INTO \"tax_log_summary\" (\"log_id\",\"log_cif\", \"log_generate_date\", \"log_status\", \"log_message\", \"log_period\") VALUES(?, ?, ?, ?, ?)";
				System.out.println(sql);
				ps = conn.prepareStatement(sql);
				ps.setInt(1, newLogId);
				ps.setString(2, id);
				ps.setString(3, date);
				ps.setString(4, status);
				ps.setString(5, message);
				ps.setString(6, period);
			}
			log.info("dari input "+sql);
			int res = ps.executeUpdate();
			
			if (res > 0) {
				result = "success";
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static Map<String, String[]> getCommonParam(String[] key,
			boolean isClob) {
		log.info("key=" + Function.split(key) + ", isClob=" + isClob);
		return getParameter(
				"select \"parameter_name\", \"parameter_value\" from \"parameter\" WHERE ",
				"\"parameter_name\"", key, isClob);
	}

	private static Map<String, String[]> getParameter(String sql, String field,
			String[] key, boolean isClob) {
		log.info("sql=" + sql + ", field=" + field + ", key="
				+ Function.split(key) + ", isClob=" + isClob);
		if (isClob) {
			return DBEngine.execute(sql + Function.whereSQL(field, key),
					isClob);
		}
		return DBEngine.execute(sql + Function.whereSQL(field, key), isClob);
	}
	
	
}
