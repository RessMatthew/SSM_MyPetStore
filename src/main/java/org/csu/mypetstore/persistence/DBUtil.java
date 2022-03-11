package org.csu.mypetstore.persistence;

import java.sql.*;

public class DBUtil {
    //准备
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/mypetstore";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "xiewantong.123";

    //加载驱动，获得连接
    public static Connection getConnection() throws Exception{

        Class.forName(DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        return connection;

    }

    //关闭所创建的连接。在DAO里创建了什么就删掉什么
    public static void  closeConnection(Connection connection) throws Exception{
        if(connection!=null){
            connection.close();
        }
    }

    //下面两个东西根据我的理解作用是拼接SQL字段。这里函数的作用是close它们
    public static void  closeStatement(Statement statement) throws Exception{
        if(statement!=null){
            statement.close();
        }
    }
    public static void  closePreparedStatement(PreparedStatement preparedStatement) throws Exception{
        if(preparedStatement!=null){
            preparedStatement.close();
        }
    }

    public static void  closeResultSet(ResultSet resultSet) throws Exception{
        if(resultSet!=null){
            resultSet.close();
        }
    }

//    public static void main(String[] args) {
//        try {
//            Connection connection = DBUtil.getConnection();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }

}
