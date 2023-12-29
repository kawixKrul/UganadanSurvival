package agh.ics.oop.gui.presenter;

import agh.ics.oop.interfaces.MapChangeListener;
import agh.ics.oop.interfaces.WorldElement;
import agh.ics.oop.interfaces.WorldMap;
import agh.ics.oop.model.Boundary;
import agh.ics.oop.model.Simulation;
import agh.ics.oop.model.Vector2d;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class SimulationPresenter implements MapChangeListener {
    private Simulation simulation;
    private WorldMap map;
    private static final int CEll_SIZE = 20;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label descriptionLabel;

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
        this.map = simulation.getMap();
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap();
            descriptionLabel.setText(message);
        });
    }

    public void drawMap() {
        clearGrid();

    };

    public void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    public void writeMap() {
        Boundary boundary = map.getCurrentBounds();
        Label main = new Label("Map");
        GridPane.setHalignment(main, HPos.CENTER);

        mapGrid.add(main, 0, 0, 1, 1);
        mapGrid.getRowConstraints().add(new RowConstraints(CEll_SIZE));
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CEll_SIZE));

        for (int i = 0, idx = 1; i < boundary.upperRight().getX(); i++, idx++) {
            Label label = new Label(String.valueOf(i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, idx, 0, 1, 1);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CEll_SIZE));
        }

        for (int i = 0, idx = 1; i < boundary.lowerLeft().getY(); i--, idx++) {
            Label label = new Label(String.valueOf(i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, 0, idx, 1, 1);
            mapGrid.getRowConstraints().add(new RowConstraints(CEll_SIZE));
        }

        for (WorldElement element: map.getElements()) {
            Vector2d position = element.getPosition();
            Image img = element.getClassImage();
            ImageView imgView = new ImageView(img);
            imgView.setFitHeight(CEll_SIZE);
            imgView.setFitWidth(CEll_SIZE);
            Button button = new Button();
            button.setGraphic(imgView);
            button.setOnAction(e -> {
                descriptionLabel.setText(element.toString());
            });
            GridPane.setHalignment(button, HPos.CENTER);
            mapGrid.add(button,
                    position.getX() + 1,
                    boundary.upperRight().getY()-position.getY() + 1,
                    1,
                    1);
        }
    }

}
