package agh.ics.oop.gui.presenter;

import agh.ics.oop.exceptions.WrongSimulationParameterValueException;
import agh.ics.oop.model.*;
import agh.ics.oop.util.CSVFileWriter;
import agh.ics.oop.util.CrazyAnimalFactory;
import agh.ics.oop.util.GenomePattern;
import agh.ics.oop.util.RegularAnimalFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MenuPresenter {
    private static final int THREAD_POOL_SIZE = 4;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    @FXML
    public Button startButton;
    @FXML
    private TextField mapWidth;
    @FXML
    private TextField mapHeight;
    @FXML
    private TextField startingPlantCount;
    @FXML
    private TextField plantEnergy;
    @FXML
    private TextField plantGrowthPerDay;
    @FXML
    private TextField startingAnimalCount;
    @FXML
    private TextField startingAnimalEnergy;
    @FXML
    private TextField breedingRequiredEnergy;
    @FXML
    private TextField breedingConsumptionEnergy;
    @FXML
    private TextField minimumMutationNumber;
    @FXML
    private TextField maximumMutationNumber;
    @FXML
    private TextField genomeLength;
    @FXML
    private CheckBox crazyAnimalEnabled;
    @FXML
    private CheckBox toxicPlantsEnabled;
    @FXML
    private CheckBox saveToFileEnabled;
    @FXML
    private Label errorLabel = new Label("ALL GUCCI");

    public void validateInput() throws WrongSimulationParameterValueException {
        if (mapWidth.getText().isEmpty() || mapHeight.getText().isEmpty() || startingPlantCount.getText().isEmpty() ||
                plantEnergy.getText().isEmpty() || plantGrowthPerDay.getText().isEmpty() || startingAnimalCount.getText().isEmpty() ||
                startingAnimalEnergy.getText().isEmpty() || breedingRequiredEnergy.getText().isEmpty() || breedingConsumptionEnergy.getText().isEmpty() ||
                minimumMutationNumber.getText().isEmpty() || maximumMutationNumber.getText().isEmpty() || genomeLength.getText().isEmpty()) {
            throw new WrongSimulationParameterValueException("All fields must be filled");
        }
        try {
            if (Integer.parseInt(mapWidth.getText()) < 8 || Integer.parseInt(mapWidth.getText()) > 128) {
                throw new WrongSimulationParameterValueException("Map width must be between 8 and 128 inclusive");
            }
            if (Integer.parseInt(mapHeight.getText()) < 8 || Integer.parseInt(mapHeight.getText()) > 128) {
                throw new WrongSimulationParameterValueException("Map height must be between 8 and 128 inclusive");
            }
            if (Integer.parseInt(startingPlantCount.getText()) < 0) {
                throw new WrongSimulationParameterValueException("Starting plant count must be a positive number");
            }
            if (Integer.parseInt(plantEnergy.getText()) < 0) {
                throw new WrongSimulationParameterValueException("Plant energy must be a positive number");
            }
            if (Integer.parseInt(plantGrowthPerDay.getText()) < 0) {
                throw new WrongSimulationParameterValueException("Plant growth per day must be a positive number");
            }
            if (Integer.parseInt(startingAnimalCount.getText()) < 0) {
                throw new WrongSimulationParameterValueException("Starting animal count must be a positive number");
            }
            if (Integer.parseInt(startingAnimalEnergy.getText()) < 0) {
                throw new WrongSimulationParameterValueException("Starting animal energy must be a positive number");
            }
            if (Integer.parseInt(breedingRequiredEnergy.getText()) < 0) {
                throw new WrongSimulationParameterValueException("Breeding required energy must be a positive number");
            }
            if (Integer.parseInt(breedingConsumptionEnergy.getText()) < 0) {
                throw new WrongSimulationParameterValueException("Breeding consumption energy must be a positive number");
            }
            if (Integer.parseInt(minimumMutationNumber.getText()) < 0) {
                throw new WrongSimulationParameterValueException("Minimum mutation number must be a positive number");
            }
            if (Integer.parseInt(maximumMutationNumber.getText()) < 0) {
                throw new WrongSimulationParameterValueException("Maximum mutation number must be a positive number");
            }
            if (Integer.parseInt(genomeLength.getText()) < 1) {
                throw new WrongSimulationParameterValueException("Genome must have at least one gene");
            }
            if (Integer.parseInt(minimumMutationNumber.getText()) > Integer.parseInt(maximumMutationNumber.getText())) {
                throw new WrongSimulationParameterValueException("Minimum mutation number must be smaller or equal than maximum mutation number");
            }
            if (Integer.parseInt(genomeLength.getText()) < Integer.parseInt(maximumMutationNumber.getText())) {
                throw new WrongSimulationParameterValueException("Genome length must be greater or equal than maximum mutation number");
            }
        } catch (NumberFormatException e) {
            throw new WrongSimulationParameterValueException(e.getMessage() + " is not a number");
        }
    }

    public void startSimulation(ActionEvent actionEvent) throws IOException {
        try {
            errorLabel.setText("ALL GUCCI");
            validateInput();

            var genomePattern = new GenomePattern(
                    Integer.parseInt(minimumMutationNumber.getText()),
                    Integer.parseInt(maximumMutationNumber.getText()),
                    Integer.parseInt(genomeLength.getText())
            );
            var boundary = new Boundary(
                    new Vector2d(0, 0),
                    new Vector2d(Integer.parseInt(mapWidth.getText()), Integer.parseInt(mapHeight.getText())));
            var factory = switch(crazyAnimalEnabled.isSelected() ? 0 : 1) {
                case 0 -> new CrazyAnimalFactory(
                        Integer.parseInt(startingAnimalEnergy.getText()),
                        genomePattern,
                        Integer.parseInt(breedingConsumptionEnergy.getText()),
                        boundary
                );
                case 1 -> new RegularAnimalFactory(
                        Integer.parseInt(startingAnimalEnergy.getText()),
                        genomePattern,
                        Integer.parseInt(breedingConsumptionEnergy.getText()),
                        boundary
                );
                default ->
                        throw new IllegalStateException("Unexpected value: " + (crazyAnimalEnabled.isSelected() ? 0 : 1));
            };
            var map = switch(toxicPlantsEnabled.isSelected() ? 0 : 1) {
                case 0 -> new ToxicMap(
                        Integer.parseInt(mapWidth.getText()),
                        Integer.parseInt(mapHeight.getText()),
                        // TODO zmienic w konstrukotrze toxicmap te wartosci
                        // powinno przyjmowac boundary imo
                        0,
                        0
                );
                case 1 -> new NormalMap(
                        Integer.parseInt(mapWidth.getText()),
                        Integer.parseInt(mapHeight.getText())
                );
                default ->
                        throw new IllegalStateException("Unexpected value: " + (toxicPlantsEnabled.isSelected() ? 0 : 1));
            };

            Stage simulationStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
            BorderPane rootPane = loader.load();

            SimulationPresenter simulationPresenter = loader.getController();
            simulationPresenter.setWorldMap(map);

            configureSimulationScene(simulationStage, rootPane);

            simulationStage.show();

            map.addObserver(simulationPresenter);
            if (saveToFileEnabled.isSelected()) {
                map.addObserver(new CSVFileWriter(map.getId().toString()));
            }

            var simulation = new Simulation(
                    map,
                    factory,
                    Integer.parseInt(plantGrowthPerDay.getText()),
                    Integer.parseInt(breedingRequiredEnergy.getText()),
                    Integer.parseInt(breedingConsumptionEnergy.getText()),
                    Integer.parseInt(startingPlantCount.getText()),
                    Integer.parseInt(startingAnimalCount.getText())
            );

            executorService.submit(simulation);

        } catch (WrongSimulationParameterValueException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void configureSimulationScene(Stage simulationStage, BorderPane rootPane) {
        simulationStage.setTitle("Ugandan Simulator 2024");
        simulationStage.setScene(new Scene(rootPane));
        simulationStage.minWidthProperty().bind(rootPane.minWidthProperty());
        simulationStage.minHeightProperty().bind(rootPane.minHeightProperty());
        simulationStage.setOnCloseRequest(event -> simulationStage.close());
    }
}
