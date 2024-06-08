package GUI;

import Builders.ContainerBuilder;
import Builders.SummarizerQualifierBuilder;
import Database.BlockGroup;
import Database.CSV;
import FuzzyCalculations.Columns;
import FuzzyCalculations.Container;
import FuzzyCalculations.SummarizerQualifier;
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

import static GUI.LinguisticVariables.*;

public class CreateLabelController {
    // ================ Code's Variables ================
    Pair<Double, Double> domain;
    Columns column;

    // Data
    private List<BlockGroup> data;
    String path = "dataBasePrep/prepared.csv";

    // LinguisticVariables
    private LinguisticVariables linguisticVariables;


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

        linguisticVariables = LinguisticVariables.getInstance(data);

        // Init menu
        initMenu();

        // Set up ComboBoxes
        chosenAttribute.getItems().addAll(linguisticVariables.getLinguisticVariables());
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


    @FXML
    public void createLabelButtonClicked() {
        SummarizerQualifier newSummarizerQualifier;
        double a;
        double b;
        double c;
        double d;
        String name = enteredName.getText();
        String functionShape = chosenFunction.getValue();
        switch (functionShape) {
            case "Triangle":
                a = paramA_Slider.getValue();
                b = paramB_Slider.getValue();
                c = paramC_Slider.getValue();

                newSummarizerQualifier = SummarizerQualifierBuilder.builder()
                        .onRange(domain.first, domain.second)
                        .createFuzzySet().onColumn(column)
                        .withCandidates(data).withLabel(name)
                        .createMembershipFunction().createTriangle(a, b, c).build()
                        .build().end();
                break;
            case "Trapezoidal":
                a = paramA_Slider.getValue();
                b = paramB_Slider.getValue();
                c = paramC_Slider.getValue();
                d = paramD_Slider.getValue();

                newSummarizerQualifier = SummarizerQualifierBuilder.builder()
                        .onRange(domain.first, domain.second)
                        .createFuzzySet().onColumn(column)
                        .withCandidates(data).withLabel(name)
                        .createMembershipFunction().createTrapezoidal(a, b, c, d).build()
                        .build().end();
                break;
            case "Gaussian":
                a = paramA_Slider.getValue();
                b = paramB_Slider.getValue();

                newSummarizerQualifier = SummarizerQualifierBuilder.builder()
                        .onRange(domain.first, domain.second)
                        .createFuzzySet().onColumn(column)
                        .withCandidates(data).withLabel(name)
                        .createMembershipFunction().createGaussian(a, b).build()
                        .build().end();
                break;
            default:
                newSummarizerQualifier = null;
        }

        String chosenLinguisticVariable = chosenAttribute.getValue();
        switch (chosenLinguisticVariable) {
            case "Bedroom to room ratio":
                linguisticVariables.getBedroomToRoomRatio().addLabel(newSummarizerQualifier);
                break;
            case "Median house age":
                linguisticVariables.getMedianHouseAge().addLabel(newSummarizerQualifier);
                break;
            case "Mean household type":
                linguisticVariables.getMeanHouseholdType().addLabel(newSummarizerQualifier);
                break;
            case "Median income":
                linguisticVariables.getMedianIncome().addLabel(newSummarizerQualifier);
                break;
            case "Population":
                linguisticVariables.getPopulation().addLabel(newSummarizerQualifier);
                break;
            case "Total rooms count":
                linguisticVariables.getTotalRoomsCount().addLabel(newSummarizerQualifier);
                break;
            case "Median house value":
                linguisticVariables.getMedianHouseValue().addLabel(newSummarizerQualifier);
                break;
            case "Distance LA":
                linguisticVariables.getDistanceLA().addLabel(newSummarizerQualifier);
                break;
            case "Distance SF":
                linguisticVariables.getDistanceSF().addLabel(newSummarizerQualifier);
                break;
            default:
                break;
        }

        status.setText("Label created successfully");
    }

    // ================ Code's Functions ================
    // Init ComboBoxes



    public void updateFunctionDomain() {
        setDomainVisible();
        domain = new Pair<>(0.0, 0.0);
        String chosenLinguisticVariable = chosenAttribute.getValue();
        switch (chosenLinguisticVariable) {
            case "Bedroom to room ratio":
                domain = linguisticVariables.getBedroomToRoomRatio().getLabel("insufficient share of bedrooms").getRange();
                column = Columns.ratio_rooms_bedrooms;
                break;
            case "Median house age":
                domain = linguisticVariables.getMedianHouseAge().getLabel("new").getRange();
                column = Columns.housing_median_age;
                break;
            case "Mean household type":
                domain = linguisticVariables.getMeanHouseholdType().getLabel("studio apartments dominant").getRange();
                column = Columns.households;
                break;
            case "Median income":
                domain = linguisticVariables.getMedianIncome().getLabel("poor area").getRange();
                column = Columns.median_income;
                break;
            case "Population":
                domain = linguisticVariables.getPopulation().getLabel("practically unpopulated").getRange();
                column = Columns.population;
                break;
            case "Total rooms count":
                domain = linguisticVariables.getTotalRoomsCount().getLabel("few rooms").getRange();
                column = Columns.total_rooms;
                break;
            case "Median house value":
                domain = linguisticVariables.getMedianHouseValue().getLabel("practically worthless").getRange();
                column = Columns.median_house_value;
                break;
            case "Distance LA":
                domain = linguisticVariables.getDistanceLA().getLabel("within city bounds(LA)").getRange();
                column = Columns.distance_LA;
                break;
            case "Distance SF":
                domain = linguisticVariables.getDistanceSF().getLabel("within city bounds(SF)").getRange();
                column = Columns.distance_SF;
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
                paramA.setText("Mean: ");
                paramB.setText("Sigma: ");

                paramA_Slider.setMin(domain.first);
                paramA_Slider.setMax(domain.second);
                paramA_Slider.setValue(domain.first);

                paramB_Slider.setMin(domain.first);
                paramB_Slider.setMax(domain.second);
                paramB_Slider.setValue(domain.first);
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

