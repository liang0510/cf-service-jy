package com.cf.kindergarten.util;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * @description JDBC数据链接
 * @author lc
 * @param
 * @return
 * @date 2019年1月12日11:11:15
 */
public class JDBCToChanjet {
    public List<HashMap> getConnection(String sql) {
        List<HashMap> list = new ArrayList<HashMap>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            Properties prop = new Properties();
            InputStream in = JDBCToChanjet.class.getResourceAsStream("/application.properties");
            prop.load(in);
            String ip = prop.getProperty("TIP").trim();
            String port = prop.getProperty("TPORT").trim();
            String dbname = prop.getProperty("TDBNAME").trim();
            String user = prop.getProperty("TUSER").trim();
            String pwd = prop.getProperty("TPWD").trim();

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://" + ip + ":" + port + ";databaseName=" + dbname + "";
            conn = DriverManager.getConnection(url, user, pwd);
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData(); //获得结果集结构信息,元数据
            int columnCount = md.getColumnCount();   //获得列数
            while (rs.next()) {
                HashMap rowData = new HashMap();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(rowData);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        } finally {
            try {
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Closed database successfully");
        }
        return list;
    }

    public List<HashMap> getConnectionSystem(String sql) {
        List<HashMap> list = new ArrayList<HashMap>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            Properties prop = new Properties();
            InputStream in = JDBCToChanjet.class.getResourceAsStream("/application.properties");
            prop.load(in);
            String ip = prop.getProperty("TIP").trim();
            String port = prop.getProperty("TPORT").trim();
            String dbname = prop.getProperty("TDBNAMES").trim();
            String user = prop.getProperty("TUSER").trim();
            String pwd = prop.getProperty("TPWD").trim();

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://" + ip + ":" + port + ";databaseName=" + dbname + "";
            conn = DriverManager.getConnection(url, user, pwd);
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData(); //获得结果集结构信息,元数据
            int columnCount = md.getColumnCount();   //获得列数
            while (rs.next()) {
                HashMap rowData = new HashMap();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(rowData);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        } finally {
            try {
                conn.close();
                rs.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Closed database successfully");
        }
        return list;
    }

    public int getConnectionUpdate(String sql)  {
        Connection conn = null;
        Statement st = null;
        int rs = 0;
        try {
            Properties prop = new Properties();
            InputStream in = JDBCToChanjet.class.getResourceAsStream("/application.properties");
            prop.load(in);
            String ip = prop.getProperty("TIP").trim();
            String port = prop.getProperty("TPORT").trim();
            String dbname = prop.getProperty("TDBNAME").trim();
            String user = prop.getProperty("TUSER").trim();
            String pwd = prop.getProperty("TPWD").trim();

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://" + ip + ":" + port + ";databaseName=" + dbname + "";
            conn = DriverManager.getConnection(url, user, pwd);
            st = conn.createStatement();
            rs = st.executeUpdate(sql);
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Closed database successfully");
        }
        return rs;
    }

    public int getConnectionInsert(String sql) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int iaccount = 0;
        try {
            Properties prop = new Properties();
            InputStream in = JDBCToChanjet.class.getResourceAsStream("/application.properties");
            prop.load(in);
            String ip = prop.getProperty("TIP").trim();
            String port = prop.getProperty("TPORT").trim();
            String dbname = prop.getProperty("TDBNAME").trim();
            String user = prop.getProperty("TUSER").trim();
            String pwd = prop.getProperty("TPWD").trim();

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://" + ip + ":" + port + ";databaseName=" + dbname + "";
            conn = DriverManager.getConnection(url, user, pwd);
            if (null != conn) {
                // 指定返回生成的主键
                pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                // 如果使用静态的SQL，则不需要动态插入参数
                //pstmt.setString(1, new Date().toLocaleString());
                pstmt.executeUpdate();
                // 检索由于执行此 Statement 对象而创建的所有自动生成的键
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    iaccount = Math.toIntExact(id);
                    System.out.println("数据主键：" + iaccount);
                }
            }

            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        } finally {
            try {
                conn.close();
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Closed database successfully");
        }
        return iaccount;
    }

}
