package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.model.GrassField;
import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.WorldElement;
import agh.ics.oop.model.WorldMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MenuPresenter {

    private static final int THREAD_POOL_SIZE = 4;
    private final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    @FXML
    public TextField moves;
    @FXML
    public Button simulationStartButton;
    public void onSimulationStartClicked(ActionEvent actionEvent) throws IOException {


        String [] moves = this.moves.getText().split(" ");
        System.out.println(Arrays.toString(moves));

        Stage simulationStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();

        SimulationPresenter simulationPresenter = loader.getController();
        WorldMap<WorldElement, Vector2D> map = new GrassField(10);

        map.registerObserver(simulationPresenter);
        simulationPresenter.setWorldMap(map);

        Simulation simulation = new Simulation(
                List.of(new Vector2D(2, 2), new Vector2D(3, 4)),
                OptionsParser.parse(moves),
                map
        );

        configureSimulationScene(simulationStage, viewRoot);

        simulationStage.show();

        executorService.submit(simulation);
    }

    private void configureSimulationScene(Stage simulationStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        simulationStage.setScene(scene);
        simulationStage.setTitle("Simulation");
        simulationStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        simulationStage.minHeightProperty().bind(viewRoot.minHeightProperty());
        simulationStage.setOnCloseRequest(event -> simulationStage.close());
    }

}
