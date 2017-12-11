import java.sql.*;
import java.util.Properties;

public class GeoCoder {

    public Adress Coord2Adress(Coord coord) {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:postgresql://localhost/vgisU3";
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
            resultAdress.city = rs.getString("addr:city");
            resultAdress.housenumber = rs.getString("type");
            resultAdress.name = rs.getString("name");
            resultAdress.streetname = rs.getString("addr:stree");
            resultAdress.zipcode = rs.getString("addr:postc");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultAdress;
    }

    private static PreparedStatement creatprepStatement(Connection con) throws SQLException {
        String sql = "SELECT name, type, \"addr:stree\", \"addr:postc\", \"addr:city\" FROM frankfurt_germany_osm_housenumbers ORDER BY geom <-> st_setsrid(ST_GeomFromText('POINT(? ?)'),?) LIMIT 1;";
        return con.prepareStatement(sql);
    }

}
