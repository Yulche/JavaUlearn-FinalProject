import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Task {
    private final Handler handler;

    public Task() throws SQLException {
        handler = Handler.getItem();
    }

    public void drawBarChart() throws SQLException {
        var categoryDataset = new DefaultCategoryDataset();
        var floors = handler.getFloorList();
        for (var floor : floors) {
            categoryDataset.setValue(handler.getNumberByFloor(floor), floor, "Этаж");
            System.out.printf("%s %s-этажных домов\n", handler.getNumberByFloor(floor), floor);
        }
        var chart = ChartFactory.createBarChart3D(
                "Количество домов по этажам",
                "Количество этажей",
                "Количество домов",
                categoryDataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );
        chart.setBackgroundPaint(Color.white);
        chart.getTitle().setPaint(Color.black);
        var plot = chart.getCategoryPlot();
        var bar = (BarRenderer) plot.getRenderer();
        bar.setItemMargin(0);
        var frame = new JFrame("Диаграмма домов по количеству этажей");
        var panel = new ChartPanel(chart);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public void printHighwayBuildings() throws SQLException {
        var buildings = handler.getBuildingByPrefix(9881);
        for (var building : buildings)
            System.out.println(building);
    }

    public void printAverageUniversityPrefix() throws SQLException {
        var prefixes = handler.getUniversityPrefix();
        var sum = 0;
        for (var prefix : prefixes) {
            sum = sum + prefix;
        }
        System.out.println(sum / prefixes.size());
    }
}
