package com.cedrus.spring.demo.orm;

import com.cedrus.spring.demo.orm.annotation.CedrusColumn;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cedrus
 * @date 2019/4/20
 */
public class JdbcTest {

    private static final String URL = "";
    private static final String USER_NAME = "";
    private static final String PASSWORD = "";


    private static List<?> query(Object object){
        List<Object> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try{

            Class entityClass = object.getClass();

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);

            String sql  = "select * from t_menber";
            psmt = conn.prepareStatement(sql);

            Map<String,String> mapper = new HashMap<>();
            Map<String,String> getColumnNameByFieldName = new HashMap<>();

            Field[] fields = entityClass.getFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if(field.isAnnotationPresent(CedrusColumn.class)){
                    mapper.put(field.getName(),fieldName);
                    getColumnNameByFieldName.put(fieldName,field.getName());
                }else{
                    mapper.put(fieldName,fieldName);
                    getColumnNameByFieldName.put(fieldName,fieldName);
                }
            }


            rs =  psmt.executeQuery();
            int columnCounts = rs.getMetaData().getColumnCount();
            while(rs.next()){
                Object instance = entityClass.newInstance();
                for (int i = 1; i <= columnCounts; i++) {
                    String columnName = rs.getMetaData().getColumnName(i);
                    Field field = entityClass.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(instance,rs.getObject(mapper.get(columnName)));
                }
                result.add(instance);
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(null!=rs){
                    rs.close();
                }
                if(null!=psmt){
                    psmt.close();
                }
                if(conn!=null){
                    conn.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return null;
    }



}
