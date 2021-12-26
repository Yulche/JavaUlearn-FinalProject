import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Handler {
    private static final String url = "jdbc:sqlite:identifier.sqlite";
    private static Handler item;
    private final Connection connection;

    private Handler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(url);
    }

    public static Handler getItem() throws SQLException {
        if (item == null)
            item = new Handler();
        return item;
    }

    public void addBuilding(Building building) {
        try {
            var buildingStatement = connection.prepareStatement("""
                    INSERT INTO Buildings(number, address, snapshot, appellation, floorsCount, type, id, constructionYear)
                    VALUES (?,?,?,?,?,?,?,?)
                    """);
            buildingStatement.setObject(1, building.number);
            buildingStatement.setObject(2, building.address);
            buildingStatement.setObject(3, building.snapshot);
            buildingStatement.setObject(4, building.appellation);
            buildingStatement.setObject(5, building.floorsCount);
            buildingStatement.setObject(6, building.type);
            buildingStatement.setObject(7, building.id);
            buildingStatement.setObject(8, building.constructionYear);
            buildingStatement.execute();

            var prefixStatement = connection.prepareStatement("""
                    INSERT INTO Prefixes(number, prefixCode)
                    VALUES (?,?)
                    """);
            prefixStatement.setObject(1, building.number);
            prefixStatement.setObject(2, building.prefixCode);
            prefixStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fillDatabase(List<Building> buildings) {
        for (var building : buildings) {
            addBuilding(building);
        }
    }

    public ArrayList<Integer> getFloorList() throws SQLException {
        var floors = new ArrayList<Integer>();
        var statement = connection.prepareStatement("""
                SELECT DISTINCT Buildings.floorsCount
                FROM Buildings
                WHERE floorsCount > 0
                ORDER BY floorsCount
                """);
        var results = statement.executeQuery();
        while (results.next())
            floors.add(results.getInt("floorsCount"));
        return floors;
    }

    public ArrayList<String> getBuildingByPrefix(int prefix) throws SQLException {
        var buildings = new ArrayList<String>();
        var statement = connection.prepareStatement("""
                SELECT Buildings.number
                FROM Buildings, Prefixes
                WHERE address LIKE '%Шлиссельбургское шоссе%'
                AND Buildings.number = Prefixes.number AND prefixCode = ?
                """);
        statement.setObject(1, prefix);
        var results = statement.executeQuery();
        while (results.next())
            buildings.add(results.getString(1));
        return buildings;
    }

    public ArrayList<Integer> getUniversityPrefix() throws SQLException {
        var prefixes = new ArrayList<Integer>();
        var statement = connection.createStatement();
        var results = statement.executeQuery("""
                SELECT prefixCode
                FROM Buildings, Prefixes
                WHERE appellation LIKE '%университет%' AND floorsCount > 5 AND constructionYear NOT NULL
                AND Buildings.number = Prefixes.number
                                """);
        while (results.next())
            prefixes.add(results.getInt("prefixCode"));
        return prefixes;
    }

    public int getNumberByFloor(int floor) throws SQLException {
        var statement = connection.prepareStatement("""
                SELECT COUNT(Buildings.number)
                FROM Buildings, Prefixes
                WHERE floorsCount = ?
                AND Buildings.number = Prefixes.number
                """);
        statement.setObject(1, floor);
        var result = statement.executeQuery();
        return result.getInt(1);
    }
}
