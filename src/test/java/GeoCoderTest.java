import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoCoderTest {
    @Test
    void coord2Adress() {
        Coord coord = new Coord();
        coord.x = 8.59226;
        coord.y = 50.03298;
        coord.SRID = 4326;
        GeoCoder geoCoder = new GeoCoder();
        Adress adress = geoCoder.coord2Adress(coord);
        assertAll("Adresse",
                ()->assertEquals(adress.city,"Frankfurt"),
                ()->assertEquals(adress.housenumber,"1"),
                ()->assertEquals(adress.name,""),
                ()->assertEquals(adress.streetname,"Am LuftbrÃ¼ckendenkmal"),
                ()->assertEquals(adress.zipcode,"60549")
        );



    }
}