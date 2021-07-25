package utility;

import java.io.IOException;
import java.sql.*;

import org.json.simple.JSONObject;

import com.aventstack.extentreports.Status;
import oneWorld.Automation.DriverScript;

public class PostGresUtitity {

    private Connection con = null;

    public  PostGresUtitity() throws IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        String dbName = "php_one_world_db";
        String userName = "php_ow_stage_ro";
        String password = "cGhwUmVhZE9ubHkh";
        String hostname = "php-one-world.c8eqyu2mllyp.us-east-1.rds.amazonaws.com";
        String port = "5432";
        String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
        try {
            con = DriverManager.getConnection(jdbcUrl);
            System.out.println("DB Connected");
        } catch (java.sql.SQLException e) {
            System.err.println("Unable to connect DB: " + e.getMessage());
        }
    }

    public JSONObject executeQuery(String query) throws SQLException {
        Statement stmt;
        ResultSet result = null;
        JSONObject obj=null;
        try {
            System.out.println("Executing Query...");
            stmt = con.createStatement();
            result = stmt.executeQuery(query);
            ResultSetMetaData rsmd = result.getMetaData();
            while(result.next()) {
                int numColumns = rsmd.getColumnCount();
                 obj = new JSONObject();
                for (int i=1; i<=numColumns; i++) {
                    String column_name = rsmd.getColumnName(i);
                    obj.put(column_name, result.getObject(column_name));
                }
            }
        } catch (Exception e) {
            System.err.println("Exception Generated While ExecuteQuery " + query);
            System.out.println(e);
            DriverScript.nodes.log(Status.PASS, "While executing query ran into error " + e.getMessage());
        }
        finally {
            PostGresUtitity.closeConnection(con);
        }
        return obj;
    }

    public static void closeConnection(Connection con) throws SQLException {
        if (con != null) {
            con.close();
        }
    }

    public String getValue(JSONObject result, String key){
        return result.get(key).toString();
    }

    public static void main(String[] args) throws Exception {
        PostGresUtitity postGresUtitity = new PostGresUtitity();
        JSONObject result = postGresUtitity.executeQuery("select\n" +
                "case\n" +
                "when ag.display_name is null\n" +
                "then concat(u.first_name, ' ', u.last_name)\n" +
                "else ag.display_name\n" +
                "end as AgentName\n" +
                "from php_stage_beta1.\"Agents\" ag\n" +
                "left join php_stage_beta1.\"Users\" u on u.id = ag.agent_id\n" +
                "where ag.php_code in ('66609');");
        System.out.println(postGresUtitity.getValue(result,"agentname"));
    }
}