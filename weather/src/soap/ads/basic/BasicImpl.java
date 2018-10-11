package soap.ads.basic;

import java.sql.*;
import soap.ConnectionPool;
import soap.ConnectionPoolImpl;

public class BasicImpl implements Basic{

    private ConnectionPool cp;

    protected Connection con;

    private String objectName;

    public BasicImpl(ConnectionPool cp, String objectName) {
        this.objectName= objectName;
        if(cp==null){
            this.cp = new ConnectionPoolImpl();
        }
        else{
            this.cp = cp;
        }
        //Xin kết nối để làm việc
        try {
            this.con = this.cp.getConnection(this.objectName);
            if(this.con.getAutoCommit()){
                this.con.setAutoCommit(false);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private boolean exe(PreparedStatement pre){
       if(pre != null){
           try {
               int results = pre.executeUpdate();
               if(results == 0){
                   this.con.rollback();
                   return false;
               }
               this.con.commit();
               return true;
           } catch (SQLException ex) {
               ex.printStackTrace();
               try {
                   this.con.rollback();
               } catch (SQLException ex1) {
                   ex1.printStackTrace();
               }
           }
           //Dọn dẹp sau cùng
           finally{
               pre = null;
           }
       }
       return false;
   }

    public boolean add(PreparedStatement pre) {
        return this.exe(pre);
    }

    public boolean edit(PreparedStatement pre) {
        return this.exe(pre);
    }

    public boolean del(PreparedStatement pre) {
        return this.exe(pre);
    }


    public ResultSet get(String sql, int value) {
        try {
           PreparedStatement pre = this.con.prepareStatement(sql);
           if(value > 0){
               pre.setInt(1,value);
           }
           return pre.executeQuery();
       } catch (SQLException ex) {
           ex.printStackTrace();
           //Trở về trang thái an toàn
           try {
               this.con.rollback();
           } catch (SQLException ex1) {
               ex1.printStackTrace();
           }
       }
       return null;

    }

    public ResultSet get(String sql, String name, String pass) {
        try {
            PreparedStatement pre = this.con.prepareStatement(sql);

            //truyen gia tri
            pre.setString(1, name);
            pre.setString(2, pass);

            return pre.executeQuery();

        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                this.con.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
        }

        return null;

    }

    public ResultSet gets(String sql) {
        return this.get(sql,0);
    }

    public ResultSet[] gets(String[] sqls) {
        ResultSet[] tmp = new ResultSet[sqls.length];
        for(int i=0; i< sqls.length; i++){
            tmp[i] = this.get(sqls[i],0);
        }
        return tmp;

    }

    public ConnectionPool getCP() {
        return this.cp;
    }

    public void releaseConnection() {
        try {
            this.cp.releaseConnection(this.con, this.objectName);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }



}
