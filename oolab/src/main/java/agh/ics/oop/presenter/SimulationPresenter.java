package agh.ics.oop.presenter;

import agh.ics.oop.annotations.Observer;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

@Observer
public class SimulationPresenter implements MapChangeListener {

    private static final int CELL_WIDTH = 50;
    private static final int CELL_HEIGHT = 50;
    public GridPane mapGrid;

    private WorldMap<WorldElement, Vector2D> worldMap;

    @FXML
    public Label movementDescriptionLabel;

    public void setWorldMap(WorldMap<WorldElement, Vector2D> worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public void mapChanged(WorldMap<WorldElement, Vector2D> worldMap, String message) {
        Platform.runLater(this::drawMap);
        Platform.runLater(() -> movementDescriptionLabel.setText(message));
    }

    void drawMap() {
        clearGrid();

        writeAxis();

        writeObjects();
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void writeAxis() {
        Boundary boundary = worldMap.getCurrentBounds();

        Label mainLabel = new Label("y/x");
        GridPane.setHalignment(mainLabel, HPos.CENTER);

        mapGrid.add(mainLabel, 0, 0, 1, 1);
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));

        /* Iterate through columns (x values) */
        for (int i = boundary.leftLowerCorner().getX(), gridColumnIndex = 1; i <= boundary.rightUpperCorner().getX(); i++, gridColumnIndex++) {
            Label label = new Label(Integer.toString(i));
            mapGrid.add(label, gridColumnIndex, 0, 1, 1);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
            GridPane.setHalignment(label, HPos.CENTER);
        }

        /* Iterate through rows (y values) */
        for (int i = boundary.rightUpperCorner().getY(), gridRowIndex = 1; i >= boundary.leftLowerCorner().getY(); i--, gridRowIndex++) {
            Label label = new Label(Integer.toString(i));
            mapGrid.add(label, 0, gridRowIndex, 1, 1);
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }

    private void writeObjects() {
        Boundary boundary = worldMap.getCurrentBounds();

        for (WorldElement element : worldMap.getElements()) {
            Vector2D position = element.getPosition();

            Label label = new Label(element.toString());
            GridPane.setHalignment(label, HPos.CENTER);

            /*
                Elements list = animalList.concat(grassList).
                If animal is on position, add animal label,
                else if grass is on position and animal is not on position, add grass label.
                Those conditions can be checked with comparing objects classes
            */
            if (element.getClass().equals(worldMap.objectAt(position).getClass())) {
                mapGrid.add(label, position.getX() - boundary.leftLowerCorner().getX() + 1, boundary.rightUpperCorner().getY() - position.getY() + 1, 1, 1);
            }
        }
    }
}
