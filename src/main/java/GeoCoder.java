import java.sql.*;
import java.util.Properties;

public class GeoCoder {

    public Adress coord2Adress(Coord coord) {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:postgresql://localhost/vgisU2";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "Arno1234");

        Adress resultAdress = new Adress();
        try (Connection conn = DriverManager.getConnection(url, props);
             PreparedStatement ps = creatprepStatement(conn)
        ) {
            ps.setDouble(1, coord.x);
            ps.setDouble(2, coord.y);
            ps.setInt(3,coord.SRID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            resultAdress.city = rs.getString("addr:city")==null?"":rs.getString("addr:city");
            resultAdress.housenumber = rs.getString("type")==null?"":rs.getString("type");
            resultAdress.name = rs.getString("name")==null?"":rs.getString("name");
            resultAdress.streetname = rs.getString("addr:stree")==null?"":rs.getString("addr:stree");
            resultAdress.zipcode = rs.getString("addr:postc")==null?"":rs.getString("addr:postc");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultAdress;
    }

    private static PreparedStatement creatprepStatement(Connection con) throws SQLException {
        String sql = "SELECT name, type, \"addr:stree\", \"addr:postc\", \"addr:city\" FROM frankfurt_germany_osm_housenumbers ORDER BY geom <-> st_setsrid(ST_Point(?, ?),?) LIMIT 1;";
        return con.prepareStatement(sql);
    }

}
