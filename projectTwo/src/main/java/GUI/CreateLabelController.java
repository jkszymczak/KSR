package GUI;

import Builders.ContainerBuilder;
import Database.BlockGroup;
import Database.CSV;
import FuzzyCalculations.Columns;
import FuzzyCalculations.Container;
import LinguisticSummarization.TwoSubjectSummaryFirst;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static GUI.LinguisticVariablesFunctions.*;

public class CreateLabelController {
    // ================ Code's Variables ================
    // Generator
    private TwoSubjectSummaryFirst genForm1;
    private List<Pair<String, Double>> summaries;

    Pair<Double, Double> domain;

    // Data
    private List<BlockGroup> data;
    String path = "dataBasePrep/prepared.csv";

    // LinguisticVariables
    private List<String> linguisticVariables;
    private Container bedroomToRoomRatio;
    private Container meanHouseholdType;
    private Container medianHouseAge;
    private Container medianIncome;
    private Container population;
    private Container totalRoomsCount;
    private Container medianHouseValue;
    private Container distanceLA;
    private Container distanceSF;


    // ================ View's Variables ================
    // Base Info
    @FXML
    private ComboBox<String> chosenAttribute;
    @FXML
    private ComboBox<String> chosenFunction;
    @FXML
    private TextField enteredName;
    @FXML
    private Label domainShowLabel;
    @FXML
    private Label domainLabel;
    @FXML
    private Button createLabelButton;

    // Function's Parameters
    @FXML
    private Label functionParamLabel;
    @FXML
    private Label paramA;
    @FXML
    private Label paramB;
    @FXML
    private Label paramC;
    @FXML
    private Label paramD;
    @FXML
    private Label paramA_Value;
    @FXML
    private Label paramB_Value;
    @FXML
    private Label paramC_Value;
    @FXML
    private Label paramD_Value;
    @FXML
    private Slider paramA_Slider;
    @FXML
    private Slider paramB_Slider;
    @FXML
    private Slider paramC_Slider;
    @FXML
    private Slider paramD_Slider;

    // status
    @FXML
    private Label status;

    // Menu
    @FXML
    private MenuItem ss_form1;
    @FXML
    private MenuItem ss_form2;
    @FXML
    private MenuItem ms_form1;
    @FXML
    private MenuItem ms_form2;
    @FXML
    private MenuItem ms_form3;
    @FXML
    private MenuItem ms_form4;
    @FXML
    private MenuItem create_label;


    // ================ View's Functions ================
    // Init functions
    @FXML
    public void initialize() {
        // Read data from database
        data = CSV.readCSV(path);

        // Init menu
        initMenu();

        // Init linguisticVariables' names
        linguisticVariables = new ArrayList<>(Arrays.asList("Bedroom to room ratio", "Median house age", "Mean household type",
                "Median income", "Population", "Total rooms count", "Median house value", "Distance LA", "Distance SF"));

        // Made containers for linguisticVariables
        initContainers();

        // Set up ComboBoxes
        chosenAttribute.getItems().addAll(linguisticVariables);
        chosenAttribute.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateFunctionDomain();
        });

        chosenFunction.getItems().addAll("Triangle", "Trapezoidal", "Gaussian");
        chosenFunction.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateFunctionParameters();
        });

        // Init sliders
        paramA_Slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            paramA_Value.setText(String.format("%.2f", newValue.doubleValue()));
        });
        paramB_Slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            paramB_Value.setText(String.format("%.2f", newValue.doubleValue()));
        });
        paramC_Slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            paramC_Value.setText(String.format("%.2f", newValue.doubleValue()));
        });
        paramD_Slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            paramD_Value.setText(String.format("%.2f", newValue.doubleValue()));
        });

        setFunctionParametersInvisible();
        setDomainInvisible();
    }


    // ================ Code's Functions ================
    // Init ComboBoxes


    // TODO : Make possible to add new labels into linguistic variables
    // Init containers for linguistic variables
    private void initContainers() {
        bedroomToRoomRatio = ContainerBuilder.builder()
                .withSummarizerQualifier(createInsufficientShareOfBedrooms(data))
                .withSummarizerQualifier(createLowBedroomProportion(data))
                .withSummarizerQualifier(createBalancedRoomDistribution(data))
                .withSummarizerQualifier(createHighBedroomProportion(data))
                .withSummarizerQualifier(createExcessiveShareOfBedrooms(data))
                .build();

        meanHouseholdType = ContainerBuilder.builder()
                .withSummarizerQualifier(createStudioApartmentsDominant(data))
                .withSummarizerQualifier(createPredominantlySingleSmallFamily(data))
                .withSummarizerQualifier(createPredominantlySingleBigFamily(data))
                .withSummarizerQualifier(createMultiFamilyPrevalent(data))
                .build();

        medianHouseAge = ContainerBuilder.builder()
                .withSummarizerQualifier(createNewHouses(data))
                .withSummarizerQualifier(createMiddleAgedHouses(data))
                .withSummarizerQualifier(createRecentlyBuiltHouses(data))
                .withSummarizerQualifier(createAgedHouses(data))
                .build();

        medianIncome = ContainerBuilder.builder()
                .withSummarizerQualifier(createPoorArea(data))
                .withSummarizerQualifier(createLowIncomeArea(data))
                .withSummarizerQualifier(createBelowAverageIncome(data))
                .withSummarizerQualifier(createAboveAverageIncome(data))
                .withSummarizerQualifier(createWealthyArea(data))
                .build();

        population = ContainerBuilder.builder()
                .withSummarizerQualifier(createPracticallyUnpopulated(data))
                .withSummarizerQualifier(createLowPopulation(data))
                .withSummarizerQualifier(createModeratelyPopulated(data))
                .withSummarizerQualifier(createHighlyPopulated(data))
                .withSummarizerQualifier(createHugePopulation(data))
                .withSummarizerQualifier(createManToMan(data))
                .build();

        totalRoomsCount = ContainerBuilder.builder()
                .withSummarizerQualifier(createFewRooms(data))
                .withSummarizerQualifier(createSparseRoomDistribution(data))
                .withSummarizerQualifier(createModerateRoomsCount(data))
                .withSummarizerQualifier(createManyRooms(data))
                .withSummarizerQualifier(createExtremelyHighRoomsCount(data))
                .build();

        medianHouseValue = ContainerBuilder.builder()
                .withSummarizerQualifier(createPracticallyWorthless(data))
                .withSummarizerQualifier(createLowValueHomes(data))
                .withSummarizerQualifier(createModeratelyPricedHomes(data))
                .withSummarizerQualifier(createAboveAverageHomeValue(data))
                .withSummarizerQualifier(createHighValueResidentialAreas(data))
                .withSummarizerQualifier(createLuxuryEstates(data))
                .build();

        distanceLA = ContainerBuilder.builder()
                .withSummarizerQualifier(createWithinCityBoundsLA(data))
                .withSummarizerQualifier(createSuburbanProximityLA(data))
                .withSummarizerQualifier(createDistantSuburbsLA(data))
                .withSummarizerQualifier(createNearbyCityLA(data))
                .withSummarizerQualifier(createRuralFringeLA(data))
                .withSummarizerQualifier(createFarFromCityLA(data))
                .build();

        distanceSF = ContainerBuilder.builder()
                .withSummarizerQualifier(createWithinCityBoundsSF(data))
                .withSummarizerQualifier(createSuburbanProximitySF(data))
                .withSummarizerQualifier(createDistantSuburbsSF(data))
                .withSummarizerQualifier(createNearbyCitySF(data))
                .withSummarizerQualifier(createRuralFringeSF(data))
                .withSummarizerQualifier(createFarFromCitySF(data))
                .build();
    }


    public void updateFunctionDomain() {
        setDomainVisible();

        linguisticVariables = new ArrayList<>(Arrays.asList("Bedroom to room ratio", "Median house age", "Mean household type",
                "Median income", "Population", "Total rooms count", "Median house value", "Distance LA", "Distance SF"));

        domain = new Pair<>(0.0, 0.0);
        String chosenLinguisticVariable = chosenAttribute.getValue();
        switch (chosenLinguisticVariable) {
            case "Bedroom to room ratio":
                domain = bedroomToRoomRatio.getLabel("insufficient share of bedrooms").getRange();
                break;
            case "Median house age":
                domain = medianHouseAge.getLabel("new").getRange();
                break;
            case "Mean household type":
                domain = meanHouseholdType.getLabel("studio apartments dominant").getRange();
                break;
            case "Median income":
                domain = medianIncome.getLabel("poor area").getRange();
                break;
            case "Population":
                domain = population.getLabel("practically unpopulated").getRange();
                break;
            case "Total rooms count":
                domain = totalRoomsCount.getLabel("few rooms").getRange();
                break;
            case "Median house value":
                domain = medianHouseValue.getLabel("practically worthless").getRange();
                break;
            case "Distance LA":
                domain = distanceLA.getLabel("within city bounds(LA)").getRange();
                break;
            case "Distance SF":
                domain = distanceSF.getLabel("within city bounds(SF)").getRange();
                break;
            default:
                break;
        }
        domainShowLabel.setText("From " + domain.first + " to " + domain.second);

        if (chosenFunction.getValue() != null) {
            updateFunctionParameters();
        }
    }


    public void updateFunctionParameters() {
        if (!domainShowLabel.isVisible()) return;

        paramA_Slider.setMin(domain.first);
        paramA_Slider.setMax(domain.second);

        paramB_Slider.setMin(domain.first);
        paramB_Slider.setMax(domain.second);

        paramC_Slider.setMin(domain.first);
        paramC_Slider.setMax(domain.second);

        paramD_Slider.setMin(domain.first);
        paramD_Slider.setMax(domain.second);

        paramA_Slider.setBlockIncrement(domain.second - domain.first / 100);
        paramB_Slider.setBlockIncrement(domain.second - domain.first / 100);
        paramC_Slider.setBlockIncrement(domain.second - domain.first / 100);
        paramD_Slider.setBlockIncrement(domain.second - domain.first / 100);

        setFunctionParametersInvisible();
        setFunctionParametersVisible();
        String functionShape = chosenFunction.getValue();
        switch (functionShape) {
            case "Triangle":
                paramC.setVisible(true);
                paramC_Slider.setVisible(true);
                paramC_Value.setVisible(true);

                paramA.setText("Start: ");
                paramB.setText("Peek: ");
                paramC.setText("End: ");

                paramA_Slider.setValue(domain.first);
                paramB_Slider.setValue((domain.second + domain.first) / 2);
                paramC_Slider.setValue(domain.second);
                break;
            case "Trapezoidal":
                paramC.setVisible(true);
                paramC_Slider.setVisible(true);
                paramC_Value.setVisible(true);
                paramD.setVisible(true);
                paramD_Slider.setVisible(true);
                paramD_Value.setVisible(true);

                paramA.setText("Start: ");
                paramB.setText("Start Peek: ");
                paramC.setText("End Peek: ");
                paramD.setText("End: ");

                paramA_Slider.setValue(domain.first);
                paramB_Slider.setValue((domain.second + domain.first) / 4);
                paramC_Slider.setValue((domain.second + domain.first) / 4 * 3);
                paramD_Slider.setValue(domain.second);
                break;
            case "Gaussian":
                paramA.setText("C: ");
                paramB.setText("Sigma: ");

                paramA_Slider.setValue(domain.first);
                paramB_Slider.setValue(domain.second);
                break;
        }

        if (domain.first == 0) {
            paramA_Slider.setValue(0.0001);
        }
    }

    public void setFunctionParametersInvisible() {
        functionParamLabel.setVisible(false);
        paramA.setVisible(false);
        paramB.setVisible(false);
        paramC.setVisible(false);
        paramD.setVisible(false);
        paramA_Value.setVisible(false);
        paramB_Value.setVisible(false);
        paramC_Value.setVisible(false);
        paramD_Value.setVisible(false);
        paramA_Slider.setVisible(false);
        paramB_Slider.setVisible(false);
        paramC_Slider.setVisible(false);
        paramD_Slider.setVisible(false);
    }

    public void setFunctionParametersVisible() {
        functionParamLabel.setVisible(true);
        paramA.setVisible(true);
        paramB.setVisible(true);
        paramA_Value.setVisible(true);
        paramB_Value.setVisible(true);
        paramA_Slider.setVisible(true);
        paramB_Slider.setVisible(true);
    }

    public void setDomainInvisible() {
        domainLabel.setVisible(false);
        domainShowLabel.setVisible(false);
    }

    public void setDomainVisible() {
        domainLabel.setVisible(true);
        domainShowLabel.setVisible(true);
    }


    // Init menu
    public void initMenu() {
        ss_form1.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ss_form1.fxml"));
                Pane newContent = loader.load();

                Scene scene = new Scene(newContent);
                Stage stage = (Stage) ss_form1.getParentPopup().getOwnerWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ss_form2.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ss_form2.fxml"));
                Pane newContent = loader.load();

                Scene scene = new Scene(newContent);
                Stage stage = (Stage) ss_form2.getParentPopup().getOwnerWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ms_form1.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ms_form1.fxml"));
                Pane newContent = loader.load();

                Scene scene = new Scene(newContent);
                Stage stage = (Stage) ms_form1.getParentPopup().getOwnerWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ms_form2.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ms_form2.fxml"));
                Pane newContent = loader.load();

                Scene scene = new Scene(newContent);
                Stage stage = (Stage) ms_form2.getParentPopup().getOwnerWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ms_form3.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ms_form3.fxml"));
                Pane newContent = loader.load();

                Scene scene = new Scene(newContent);
                Stage stage = (Stage) ms_form3.getParentPopup().getOwnerWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ms_form4.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ms_form4.fxml"));
                Pane newContent = loader.load();

                Scene scene = new Scene(newContent);
                Stage stage = (Stage) ms_form4.getParentPopup().getOwnerWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        create_label.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("create_label.fxml"));
                Pane newContent = loader.load();

                Scene scene = new Scene(newContent);
                Stage stage = (Stage) create_label.getParentPopup().getOwnerWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

