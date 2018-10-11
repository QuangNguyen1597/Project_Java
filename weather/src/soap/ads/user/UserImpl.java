package soap.ads.user;

import soap.object.*;
import java.sql.*;
import soap.ads.basic.*;
import soap.*;

public class UserImpl extends BasicImpl implements User {

    public UserImpl(ConnectionPool cp) {
        super(cp,"User");
    }

    public boolean addUser(UserObject item) {
        if(this.isExisting(item)){
             return false;
         }
         String sql = "INSERT INTO tbluser(";
         sql += "user_name, user_pass, ";
         sql += "user_fullname, user_birthday, ";
         sql += "user_mobilephone, user_homephone, ";
         sql += "user_address, user_email, ";
         sql += "user_officephone, ";
         sql += "user_job, user_position, ";
         sql += "user_applyyear, user_permission, user_logined";
         sql += ") ";
         sql += "VALUES(?,md5(?),?,?,?,?,?,?,?,?,?,?,?,?)";
         try {
             PreparedStatement pre = this.con.prepareStatement(sql);
             //Truyen gia tri cho tung tham so
             pre.setString(1,item.getUser_name());
             pre.setString(2,item.getUser_pass());
             pre.setString(3,item.getUser_fullname());
             pre.setString(4,item.getUser_birthday());
             pre.setString(5,item.getUser_mobilephone());
             pre.setString(6,item.getUser_homephone());
             pre.setString(7,item.getUser_address());
             pre.setString(8,item.getUser_email());
             pre.setString(9,item.getUser_officephone());
             pre.setString(10,item.getUser_job());
             pre.setString(11,item.getUser_position());
             pre.setShort(12,item.getUser_applyyear());
             pre.setByte(13,item.getUser_permission());
             pre.setShort(14,item.getUser_logined());

             return this.add(pre);

         } catch (SQLException ex) {
             ex.printStackTrace();
         }
       return false;

    }

    private boolean isExisting(UserObject item){
       boolean flag = false;
       String sql = "SELECT user_id FROM tbluser ";
       sql += "WHERE user_name='"+item.getUser_name()+"' ";
       ResultSet rs = this.gets(sql);
       if(rs != null){
           try {
               if (rs.next()) {
                   flag = true;
               }
                rs.close();
           } catch (SQLException ex) {
               ex.printStackTrace();
           }
       }
       return flag;
   }


    public boolean editUser(UserObject item) {
        String sql = "UPDATE tbluser SET ";
       sql += "user_pass=md5(?), ";
       sql += "user_fullname=?, user_birthday=?, ";
       sql += "user_mobilephone=?, user_homephone=?, ";
       sql += "user_address=?,user_email=?, ";
       sql += "user_officephone=?, ";
       sql += "user_job=?, user_position=?, ";
       sql += "user_applyyear=? ";
       sql += "WHERE user_id=? ";
       try {
           PreparedStatement pre = this.con.prepareStatement(sql);
           //Truyen gia tri cho tung tham so
           pre.setString(1,item.getUser_pass());
           pre.setString(2,item.getUser_fullname());
           pre.setString(3,item.getUser_birthday());
           pre.setString(4,item.getUser_mobilephone());
           pre.setString(5,item.getUser_homephone());
           pre.setString(6,item.getUser_address());
           pre.setString(7,item.getUser_email());
           pre.setString(8,item.getUser_officephone());
           pre.setString(9,item.getUser_job());
           pre.setString(10,item.getUser_position());
           pre.setShort(11,item.getUser_applyyear());
           pre.setInt(12, item.getUser_id());
           //Trả về KQ
           return this.edit(pre);

       } catch (SQLException ex) {
           ex.printStackTrace();
       }

        return false;

    }

    public boolean delUser(UserObject item) {
        if(this.checkRoling(item)){
            return false;
        }
        String sql = "DELETE FROM tbluser WHERE user_id=?";
        try {
           PreparedStatement pre = this.con.prepareStatement(sql);

           pre.setInt(1,item.getUser_id());
           return this.del(pre);

       } catch (SQLException ex) {
           ex.printStackTrace();
       }

        return false;

    }
    private boolean checkRoling(UserObject item){
       boolean flag = false;
       String sql = "SELECT article_id FROM tblarticle ";
       sql += "WHERE article_author_name='"+ item.getUser_name() +"' ";
       ResultSet rs = this.gets(sql);
       if(rs != null){
           try {
               if (rs.next()) {
                   flag = true;
               }
               rs.close();
           } catch (SQLException ex) {
               ex.printStackTrace();
           }
       }
       return flag;
   }


    public ResultSet getUser(int id) {
        String sql = "SELECT * FROM tbluser WHERE uer_id=?";
        return this.get(sql,id);

    }

    public ResultSet getUser(String username, String userpass) {
        String sql = "SELECT * FROM tbluser WHERE ";
         sql += "(user_name=?) AND (user_pass=md5(?))";
         return this.get(sql,username,userpass);

    }

    public ResultSet getUsers(UserObject similar, int at, byte total) {
        String sql = "SELECT * FROM tbluser ";
        sql += "";
        sql += "";
        sql += "ORDER BY user_name ASC ";
        sql += "LIMIT " + at + ", " + total;
        return this.gets(sql);


    }


    public static void main(String[] args) {

        ConnectionPool cp = new ConnectionPoolImpl();
        User u = new UserImpl(cp);

        UserObject nUser = new UserObject();

        //nUser.setUser_id(2);
        nUser.setUser_name("admin");
        nUser.setUser_pass("123456");
        nUser.setUser_email("quang15121997@gmail.com");

        //2 thong tin ben duoi k bat buoc
        nUser.setUser_fullname("Nguyen Van Quang");
        nUser.setUser_address("Ha Noi");

        boolean result = u.addUser(nUser);

       if(!result){
            System.out.println("\n\nKHÔNG THÀNH CÔNG \n\n");
        }


        ResultSet rs = u.getUsers(null,0,(byte)15);
        if(rs!= null){
            //Duyet va hien thi
            String row;
            try {
                while (rs.next()) {
                    row = "ID : " + rs.getInt("user_id");
                    row += "\tNAME : " + rs.getString("user_name");
                    row += "\tPASS : " + rs.getString("user_pass");
                    row += "\tEMAIL : " + rs.getString("user_email");
                    row += "\tFULLNAME : " + rs.getString("user_fullname");


                    System.out.println(row);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
   }

}
