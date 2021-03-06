package soap.ads.user;

import java.sql.*;
import soap.object.*;
import soap.*;

public interface User extends ShareControl{

    public boolean addUser(UserObject item);
    public boolean editUser(UserObject item);
    public boolean delUser(UserObject item);

    //Lây ket qua

    public ResultSet getUser(int id);
    public ResultSet getUser(String username, String userpass);
    public ResultSet getUsers(UserObject similar, int at, byte total);

}
