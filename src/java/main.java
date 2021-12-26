import java.io.IOException;
import java.sql.SQLException;

public class main {
    public static void main(String[] args) {
        try {
            var handler = Handler.getItem();
            var task = new Task();
            /*нужно для заполнения БД*/
            /*handler.fillDatabase(Parser.parseCSV());*/
            System.out.println("#1");
            task.drawBarChart();
            System.out.println("\n#2");
            task.printHighwayBuildings();
            System.out.println("\n#3");
            task.printAverageUniversityPrefix();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
