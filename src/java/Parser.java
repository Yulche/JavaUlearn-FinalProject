import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Parser {
    public static ArrayList<Building> parseCSV() throws IOException{
        var buildings = new ArrayList<Building>();
        var isTitle = true;
        List<String[]> rows = null;
        try {
            var reader = new CSVReader(new FileReader("База Санкт-Петербурга.csv", Charset.forName("windows-1251")));
            rows = reader.readAll();

        } catch (CsvException e) {
            e.printStackTrace();
        }
        for (var row : rows){
            if (isTitle){
                isTitle = false;
                continue;
            }
            int floorCount;
            if (Objects.equals(row[4], "") || Objects.equals(row[4], "Малоэтажные"))
                floorCount = 1;
            else if (Objects.equals(row[4], "Многоэтажные"))
                floorCount = 3;
            else floorCount = Integer.parseInt(row[4].split("-")[0]);
            buildings.add(new Building(row[0], row[1], row[2], row[3], floorCount, Integer.parseInt(row[5]), row[6], Integer.parseInt(row[7]), row[8]));
        }
        return buildings;
    }
}
