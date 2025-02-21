package id.etax.conn;

import id.etax.model.Database;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class DBEngine {
	private static Logger log = Logger.getLogger(DBEngine.class.getName());
	private static Connection conn;
	private static PreparedStatement ps;
	private static ResultSet rs;

	public static Connection getConnection() {
		 Connection conn = null;
	        try {
	            conn = DriverManager.getConnection(Database.getUrl(), Database.getUser(), Database.getPwd());
//	            System.out.println("Connected to the PostgreSQL server successfully.");
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            log.info("Conntection DB: "+e.getMessage());
	        }

	        return conn;
	    }

	public static Map<String, String[]> execute(String sql, boolean isClob) {
		Map resultMap = new HashMap();
		conn = getConnection();
		if (conn != null) {
			try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (!rs.next()) {
					log.info("No data found");
				} else {
					int columnCount = rs.getMetaData().getColumnCount();
					do {
						String[] row = new String[columnCount - 1];
						for (int i = 0; i < row.length; ++i) {
							if (isClob) {
								Clob clob = rs.getClob(i + 2);
								if (clob != null)
									row[i] = clob.getSubString(1L,
											(int) clob.length());
							} else {
								row[i] = rs.getString(i + 2);
							}
						}
						resultMap.put(rs.getString(1), row);
					}

					while (rs.next());
					log.info("[OK] " + resultMap.size() + " Rows and "
							+ columnCount + " Column");
				}
			} catch (SQLException e) {
				log.error("Exception!!!", e);
			} catch (Exception e) {
				log.error("Exception!!!", e);
			}
		}
		return resultMap;
	}

	public static List<String[]> execute(String sql) {
		List resultList = new ArrayList();
		conn = getConnection();
		if (conn != null) {
			try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (!rs.next()) {
					log.info("No data found");
				} else {
					int columnCount = rs.getMetaData().getColumnCount();
					do {
						String[] row = new String[columnCount];
						for (int i = 0; i < row.length; ++i) {
							row[i] = rs.getString(i + 1);
						}
						resultList.add(row);
					}

					while (rs.next());
					log.info("[OK] " + resultList.size() + " Rows and "
							+ columnCount + " Column");
				}
			} catch (SQLException e) {
				log.error("Exception!!!", e);
			} catch (Exception e) {
				log.error("Exception!!!", e);
			} finally {
				close();
			}
		}
		return resultList;
	}

	public static void execute(String sql, String[] parameter) {
		conn = getConnection();
		if (conn == null)
			return;
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < parameter.length; ++i) {
				ps.setObject(i + 1, parameter[i]);
			}
			ps.executeUpdate();
			log.info("[OK]");
		} catch (SQLException e) {
			log.error("Exception!!!", e);
		} catch (Exception e) {
			log.error("Exception!!!", e);
		} finally {
			close();
		}
	}

	private static void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.error("Exception!!!", e);
			} catch (Exception e) {
				log.error("Exception!!!", e);
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				log.error("Exception!!!", e);
			} catch (Exception e) {
				log.error("Exception!!!", e);
			}
		}
		if (conn == null)
			return;
		try {
			conn.close();
		} catch (SQLException e) {
			log.error("Exception!!!", e);
		} catch (Exception e) {
			log.error("Exception!!!", e);
		}
	}

	public static List<String[]> executeList(String sql, String[] parameter) {
		List resultList = new ArrayList();
		conn = getConnection();
		if (conn != null) {
			try {
				ps = conn.prepareStatement(sql);
				for (int i = 0; i < parameter.length; ++i) {
					ps.setObject(i + 1, parameter[i]);
				}
				rs = ps.executeQuery();
				if (!rs.next()) {
					log.info("No data found");
				} else {
					int columnCount = rs.getMetaData().getColumnCount();
					do {
						String[] row = new String[columnCount];
						for (int i = 0; i < row.length; ++i) {
							row[i] = rs.getString(i + 1);
						}
						resultList.add(row);
					}

					while (rs.next());
					log.info("[OK] " + resultList.size() + " Rows and "
							+ columnCount + " Column");
				}
			} catch (SQLException e) {
				log.error("Exception!!!", e);
			} catch (Exception e) {
				log.error("Exception!!!", e);
			} finally {
				close();
			}
		}
		return resultList;
	}
}