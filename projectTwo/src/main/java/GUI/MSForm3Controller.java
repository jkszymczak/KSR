package GUI;

import Builders.ContainerBuilder;
import Database.BlockGroup;
import Database.CSV;
import FuzzyCalculations.Container;
import FuzzyCalculations.FuzzyQuantifier;
import FuzzyCalculations.SummarizerQualifier;
import LinguisticSummarization.Subject;
import LinguisticSummarization.TwoSubjectSummarySecond;
import LinguisticSummarization.TwoSubjectSummaryThird;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static GUI.LinguisticVariablesFunctions.*;

public class MSForm3Controller {
    // ================ Code's Variables ================
    // Generator
    private TwoSubjectSummaryThird genForm3;
    private List<Pair<String, Double>> summaries;

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
    // Quantifier and Conjunction
    @FXML
    private ComboBox<String> chosenQuantifier;
    @FXML
    private ComboBox<String> summarizatorConjunction;
    @FXML
    private ComboBox<String> qualifierConjunction;

    // Subjects
    @FXML
    private ComboBox<String> chosenSubject1;
    @FXML
    private ComboBox<String> chosenSubject2;

    // Qualifier
    @FXML
    private Spinner<Integer> numberQualifierSpinner;
    @FXML
    private ComboBox<String> chosenQualifier_1;
    @FXML
    private ComboBox<String> chosenQualifier_2;
    @FXML
    private ComboBox<String> chosenQualifier_3;
    @FXML
    private ComboBox<String> chosenQualifier_4;
    @FXML
    private ComboBox<String> cb_chosenQualifierLabel_1;
    @FXML
    private ComboBox<String> cb_chosenQualifierLabel_2;
    @FXML
    private ComboBox<String> cb_chosenQualifierLabel_3;
    @FXML
    private ComboBox<String> cb_chosenQualifierLabel_4;
    @FXML
    private Label chosenQualifierLabel_2;
    @FXML
    private Label chosenQualifierLabel_3;
    @FXML
    private Label chosenQualifierLabel_4;

    // Summarizator
    @FXML
    private Spinner<Integer> numberSummarizerSpinner;
    @FXML
    private ComboBox<String> chosenSummarizer_1;
    @FXML
    private ComboBox<String> chosenSummarizer_2;
    @FXML
    private ComboBox<String> chosenSummarizer_3;
    @FXML
    private ComboBox<String> chosenSummarizer_4;
    @FXML
    private ComboBox<String> cb_chosenSummarizerLabel_1;
    @FXML
    private ComboBox<String> cb_chosenSummarizerLabel_2;
    @FXML
    private ComboBox<String> cb_chosenSummarizerLabel_3;
    @FXML
    private ComboBox<String> cb_chosenSummarizerLabel_4;
    @FXML
    private Label chosenSummarizerLabel_2;
    @FXML
    private Label chosenSummarizerLabel_3;
    @FXML
    private Label chosenSummarizerLabel_4;

    // Display of results
    @FXML
    private TextArea textArea;

    // Saving functionality
    @FXML
    private Button saveSummariesButton;
    @FXML
    private Button saveSummariesCSVButton;
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

        // Number of Qualifiers Spinner
        numberQualifierSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1));
        numberQualifierSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            numberQualifierConfirm();
        });

        // Number of Summarizers Spinner
        numberSummarizerSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1));
        numberSummarizerSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            numberSummarizerConfirm();
        });


        // Init linguisticVariables' names
        linguisticVariables = new ArrayList<>(Arrays.asList("Bedroom to room ratio", "Median house age", "Mean household type",
                "Median income", "Population", "Total rooms count", "Median house value", "Distance LA", "Distance SF"));

        // Made containers for linguisticVariables
        initContainers();

        // Set up ComboBoxes for Subjects
        chosenSubject1.getItems().addAll("<1H OCEAN", "INLAND", "NEAR OCEAN", "NEAR BAY", "ISLAND");
        chosenSubject2.getItems().addAll("<1H OCEAN", "INLAND", "NEAR OCEAN", "NEAR BAY", "ISLAND");

        // Set up ComboBoxes for Quantifier and Conjunction
        chosenQuantifier.getItems().addAll("Relative");
        chosenQuantifier.setValue(chosenQuantifier.getItems().getFirst());

        qualifierConjunction.getItems().addAll("which are", "which have", "which are in");
        qualifierConjunction.setValue(qualifierConjunction.getItems().getFirst());

        summarizatorConjunction.getItems().addAll("are", "have", "are in");
        summarizatorConjunction.setValue(summarizatorConjunction.getItems().getFirst());


        // Set up ComboBoxes for Qualifiers
        chosenQualifier_1.getItems().addAll(linguisticVariables);
        chosenQualifier_1.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateSubComboBox(newValue, cb_chosenQualifierLabel_1);
        });

        chosenQualifier_2.getItems().addAll(linguisticVariables);
        chosenQualifier_2.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateSubComboBox(newValue, cb_chosenQualifierLabel_2);
        });

        chosenQualifier_3.getItems().addAll(linguisticVariables);
        chosenQualifier_3.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateSubComboBox(newValue, cb_chosenQualifierLabel_3);
        });

        chosenQualifier_4.getItems().addAll(linguisticVariables);
        chosenQualifier_4.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateSubComboBox(newValue, cb_chosenQualifierLabel_4);
        });


        // Set up ComboBoxes for Summarizers
        chosenSummarizer_1.getItems().addAll(linguisticVariables);
        chosenSummarizer_1.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateSubComboBox(newValue, cb_chosenSummarizerLabel_1);
        });

        chosenSummarizer_2.getItems().addAll(linguisticVariables);
        chosenSummarizer_2.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateSubComboBox(newValue, cb_chosenSummarizerLabel_2);
        });

        chosenSummarizer_3.getItems().addAll(linguisticVariables);
        chosenSummarizer_3.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateSubComboBox(newValue, cb_chosenSummarizerLabel_3);
        });

        chosenSummarizer_4.getItems().addAll(linguisticVariables);
        chosenSummarizer_4.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateSubComboBox(newValue, cb_chosenSummarizerLabel_4);
        });

        // Set items invisible
        setManyQualifierInvisible();
        setManySummarizatorInvisible();
    }


    // Generating summaries


    @FXML
    public void generateBestSummaries() {
        System.out.println("Generate Best Summary Clicked");

        generatePrepare();
        summaries = Collections.singletonList(genForm3.generateBest());
        generateAfter();
    }

    @FXML
    public void generateAllSummaries() {
        System.out.println("Generate All Summaries Clicked");

        generatePrepare();
        summaries = genForm3.generateSummaries();
        generateAfter();
    }


    // Display functions
    @FXML
    public void swapWwithS() {
        List<String> qualifierLabels = new ArrayList<>();
        List<String> linguisticVariablesQualifier = new ArrayList<>();
        Integer numberQualifier = numberQualifierSpinner.getValue();
        Integer numberSummarizer = numberSummarizerSpinner.getValue();

        switch (numberQualifier) {
            case 1:
                qualifierLabels.add(cb_chosenQualifierLabel_1.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_1.getValue());
                break;
            case 2:
                qualifierLabels.add(cb_chosenQualifierLabel_1.getValue());
                qualifierLabels.add(cb_chosenQualifierLabel_2.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_1.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_2.getValue());
                break;
            case 3:
                qualifierLabels.add(cb_chosenQualifierLabel_1.getValue());
                qualifierLabels.add(cb_chosenQualifierLabel_2.getValue());
                qualifierLabels.add(cb_chosenQualifierLabel_3.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_1.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_2.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_3.getValue());
                break;
            case 4:
                qualifierLabels.add(cb_chosenQualifierLabel_1.getValue());
                qualifierLabels.add(cb_chosenQualifierLabel_2.getValue());
                qualifierLabels.add(cb_chosenQualifierLabel_3.getValue());
                qualifierLabels.add(cb_chosenQualifierLabel_4.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_1.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_2.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_3.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_4.getValue());
                break;
            default:
                break;
        }

        List<String> summarizatorLabels = new ArrayList<>();
        List<String> linguisticVariablesSummarizator = new ArrayList<>();
        switch (numberSummarizer) {
            case 1:
                summarizatorLabels.add(cb_chosenSummarizerLabel_1.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_1.getValue());
                break;
            case 2:
                summarizatorLabels.add(cb_chosenSummarizerLabel_1.getValue());
                summarizatorLabels.add(cb_chosenSummarizerLabel_2.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_1.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_2.getValue());
                break;
            case 3:
                summarizatorLabels.add(cb_chosenSummarizerLabel_1.getValue());
                summarizatorLabels.add(cb_chosenSummarizerLabel_2.getValue());
                summarizatorLabels.add(cb_chosenSummarizerLabel_3.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_1.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_2.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_3.getValue());
                break;
            case 4:
                summarizatorLabels.add(cb_chosenSummarizerLabel_1.getValue());
                summarizatorLabels.add(cb_chosenSummarizerLabel_2.getValue());
                summarizatorLabels.add(cb_chosenSummarizerLabel_3.getValue());
                summarizatorLabels.add(cb_chosenSummarizerLabel_4.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_1.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_2.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_3.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_4.getValue());
                break;
            default:
                break;
        }

        numberQualifierSpinner.getValueFactory().setValue(numberSummarizer);
        numberSummarizerSpinner.getValueFactory().setValue(numberQualifier);
        numberQualifierConfirm();
        numberSummarizerConfirm();
        numberQualifier = numberQualifierSpinner.getValue();
        numberSummarizer = numberSummarizerSpinner.getValue();

        switch (numberSummarizer) {
            case 1:
                chosenSummarizer_1.setValue(linguisticVariablesQualifier.get(0));
                cb_chosenSummarizerLabel_1.setValue(qualifierLabels.get(0));
                break;
            case 2:
                chosenSummarizer_1.setValue(linguisticVariablesQualifier.get(0));
                chosenSummarizer_2.setValue(linguisticVariablesQualifier.get(1));
                cb_chosenSummarizerLabel_1.setValue(qualifierLabels.get(0));
                cb_chosenSummarizerLabel_2.setValue(qualifierLabels.get(1));
                break;
            case 3:
                chosenSummarizer_1.setValue(linguisticVariablesQualifier.get(0));
                chosenSummarizer_2.setValue(linguisticVariablesQualifier.get(1));
                chosenSummarizer_3.setValue(linguisticVariablesQualifier.get(2));
                cb_chosenSummarizerLabel_1.setValue(qualifierLabels.get(0));
                cb_chosenSummarizerLabel_2.setValue(qualifierLabels.get(1));
                cb_chosenSummarizerLabel_3.setValue(qualifierLabels.get(2));
                break;
            case 4:
                chosenSummarizer_1.setValue(linguisticVariablesQualifier.get(0));
                chosenSummarizer_2.setValue(linguisticVariablesQualifier.get(1));
                chosenSummarizer_3.setValue(linguisticVariablesQualifier.get(2));
                chosenSummarizer_4.setValue(linguisticVariablesQualifier.get(3));
                cb_chosenSummarizerLabel_1.setValue(qualifierLabels.get(0));
                cb_chosenSummarizerLabel_2.setValue(qualifierLabels.get(1));
                cb_chosenSummarizerLabel_3.setValue(qualifierLabels.get(2));
                cb_chosenSummarizerLabel_4.setValue(qualifierLabels.get(3));
                break;
            default:
                break;
        }

        switch (numberQualifier) {
            case 1:
                chosenQualifier_1.setValue(linguisticVariablesSummarizator.get(0));
                cb_chosenQualifierLabel_1.setValue(summarizatorLabels.get(0));
                break;
            case 2:
                chosenQualifier_1.setValue(linguisticVariablesSummarizator.get(0));
                chosenQualifier_2.setValue(linguisticVariablesSummarizator.get(1));
                cb_chosenQualifierLabel_1.setValue(summarizatorLabels.get(0));
                cb_chosenQualifierLabel_2.setValue(summarizatorLabels.get(1));
                break;
            case 3:
                chosenQualifier_1.setValue(linguisticVariablesSummarizator.get(0));
                chosenQualifier_2.setValue(linguisticVariablesSummarizator.get(1));
                chosenQualifier_3.setValue(linguisticVariablesSummarizator.get(2));
                cb_chosenQualifierLabel_1.setValue(summarizatorLabels.get(0));
                cb_chosenQualifierLabel_2.setValue(summarizatorLabels.get(1));
                cb_chosenQualifierLabel_3.setValue(summarizatorLabels.get(2));
                break;
            case 4:
                chosenQualifier_1.setValue(linguisticVariablesSummarizator.get(0));
                chosenQualifier_2.setValue(linguisticVariablesSummarizator.get(1));
                chosenQualifier_3.setValue(linguisticVariablesSummarizator.get(2));
                chosenQualifier_4.setValue(linguisticVariablesSummarizator.get(3));
                cb_chosenQualifierLabel_1.setValue(summarizatorLabels.get(0));
                cb_chosenQualifierLabel_2.setValue(summarizatorLabels.get(1));
                cb_chosenQualifierLabel_3.setValue(summarizatorLabels.get(2));
                cb_chosenQualifierLabel_4.setValue(summarizatorLabels.get(3));
                break;
            default:
                break;
        }
    }


    // Saving functionality
    @FXML
    public void saveSummariesButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file to save the results");
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Saved. The file has been created: " + file.getAbsolutePath());
                    status.setText("Saved. The file has been created: " + file.getAbsolutePath());
                } else {
                    System.out.println("Saved. The file already exists: " + file.getAbsolutePath());
                    status.setText("Saved. The file already exists: " + file.getAbsolutePath());
                }
                // Here, place the code that saves the results to the selected file
                String content = textArea.getText();
                Files.writeString(file.toPath(), content);
            } catch (IOException e) {
                System.out.println("An error occurred while creating the file: " + e.getMessage());
                status.setText("An error occurred while creating the file: " + e.getMessage());
            }
        }
    }

    @FXML
    public void saveSummariesCSVButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file to save the results");
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Saved. The file has been created: " + file.getAbsolutePath());
                    status.setText("Saved. The file has been created: " + file.getAbsolutePath());
                } else {
                    System.out.println("Saved. The file already exists: " + file.getAbsolutePath());
                    status.setText("Saved. The file already exists: " + file.getAbsolutePath());
                }
                // Here, place the code that saves the results to the selected file
               CSV.save_pairs(String.valueOf(file.toPath()), summaries);
            } catch (IOException e) {
                System.out.println("An error occurred while creating the file: " + e.getMessage());
                status.setText("An error occurred while creating the file: " + e.getMessage());
            }
        }
    }


    // Number of Qualifiers
    @FXML
    public void numberQualifierConfirm() {
        System.out.println("Number of Qualifiers Confirm Clicked");
        Integer spinnerValue = numberQualifierSpinner.getValue();
        switch (spinnerValue) {
            case 1:
                System.out.println("1");
                setManyQualifierInvisible();
                break;
            case 2:
                System.out.println("2");
                chosenQualifier_2.setVisible(true);
                chosenQualifier_3.setVisible(false);
                chosenQualifier_4.setVisible(false);
                chosenQualifierLabel_2.setVisible(true);
                chosenQualifierLabel_3.setVisible(false);
                chosenQualifierLabel_4.setVisible(false);
                cb_chosenQualifierLabel_2.setVisible(true);
                cb_chosenQualifierLabel_3.setVisible(false);
                cb_chosenQualifierLabel_4.setVisible(false);
                break;
            case 3:
                System.out.println("3");
                chosenQualifier_2.setVisible(true);
                chosenQualifier_3.setVisible(true);
                chosenQualifier_4.setVisible(false);
                chosenQualifierLabel_2.setVisible(true);
                chosenQualifierLabel_3.setVisible(true);
                chosenQualifierLabel_4.setVisible(false);
                cb_chosenQualifierLabel_2.setVisible(true);
                cb_chosenQualifierLabel_3.setVisible(true);
                cb_chosenQualifierLabel_4.setVisible(false);
                break;
            case 4:
                System.out.println("4");
                chosenQualifier_2.setVisible(true);
                chosenQualifier_3.setVisible(true);
                chosenQualifier_4.setVisible(true);
                chosenQualifierLabel_2.setVisible(true);
                chosenQualifierLabel_3.setVisible(true);
                chosenQualifierLabel_4.setVisible(true);
                cb_chosenQualifierLabel_2.setVisible(true);
                cb_chosenQualifierLabel_3.setVisible(true);
                cb_chosenQualifierLabel_4.setVisible(true);
                break;
            default:
                System.out.println("Error of Spinner Value");
                break;
        }
    }


    // Number of Summarizers
    @FXML
    public void numberSummarizerConfirm() {
        System.out.println("Number Summarizer Confirm Clicked");
        Integer spinnerValue = numberSummarizerSpinner.getValue();
        switch (spinnerValue) {
            case 1:
                System.out.println("1");
                setManySummarizatorInvisible();
                break;
            case 2:
                System.out.println("2");
                chosenSummarizer_2.setVisible(true);
                chosenSummarizer_3.setVisible(false);
                chosenSummarizer_4.setVisible(false);
                chosenSummarizerLabel_2.setVisible(true);
                chosenSummarizerLabel_3.setVisible(false);
                chosenSummarizerLabel_4.setVisible(false);
                cb_chosenSummarizerLabel_2.setVisible(true);
                cb_chosenSummarizerLabel_3.setVisible(false);
                cb_chosenSummarizerLabel_4.setVisible(false);
                break;
            case 3:
                System.out.println("3");
                chosenSummarizer_2.setVisible(true);
                chosenSummarizer_3.setVisible(true);
                chosenSummarizer_4.setVisible(false);
                chosenSummarizerLabel_2.setVisible(true);
                chosenSummarizerLabel_3.setVisible(true);
                chosenSummarizerLabel_4.setVisible(false);
                cb_chosenSummarizerLabel_2.setVisible(true);
                cb_chosenSummarizerLabel_3.setVisible(true);
                cb_chosenSummarizerLabel_4.setVisible(false);
                break;
            case 4:
                System.out.println("4");
                chosenSummarizer_2.setVisible(true);
                chosenSummarizer_3.setVisible(true);
                chosenSummarizer_4.setVisible(true);
                chosenSummarizerLabel_2.setVisible(true);
                chosenSummarizerLabel_3.setVisible(true);
                chosenSummarizerLabel_4.setVisible(true);
                cb_chosenSummarizerLabel_2.setVisible(true);
                cb_chosenSummarizerLabel_3.setVisible(true);
                cb_chosenSummarizerLabel_4.setVisible(true);
                break;
            default:
                System.out.println("Error of Spinner Value");
                break;
        }
    }


    // ================ Code's Functions ================
    // Init ComboBoxes
    private void updateSubComboBox(String mainItem, ComboBox<String> comboBox) {
        if (mainItem != null) {
            comboBox.getItems().clear();
            switch (mainItem) {
                case "Bedroom to room ratio":
                    comboBox.getItems().addAll(bedroomToRoomRatio.getLabels().keySet());
                    break;
                case "Median house age":
                    comboBox.getItems().addAll(medianHouseAge.getLabels().keySet());
                    break;
                case "Mean household type":
                    comboBox.getItems().addAll(meanHouseholdType.getLabels().keySet());
                    break;
                case "Median income":
                    comboBox.getItems().addAll(medianIncome.getLabels().keySet());
                    break;
                case "Population":
                    comboBox.getItems().addAll(population.getLabels().keySet());
                    break;
                case "Total rooms count":
                    comboBox.getItems().addAll(totalRoomsCount.getLabels().keySet());
                    break;
                case "Median house value":
                    comboBox.getItems().addAll(medianHouseValue.getLabels().keySet());
                    break;
                case "Distance LA":
                    comboBox.getItems().addAll(distanceLA.getLabels().keySet());
                    break;
                case "Distance SF":
                    comboBox.getItems().addAll(distanceSF.getLabels().keySet());
                    break;
                default:
                    break;
            }
        }
    }


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


    // Generating summaries
    public void generateAfter() {
        for (Pair<String, Double> summary : summaries) {
            System.out.println(summary);
            String text = textArea.getText();
            text += summary.toWrite() + "\n";
            textArea.setText(text);
        }
        saveSummariesButton.setDisable(false);
        saveSummariesCSVButton.setDisable(false);
    }

    public void generatePrepare() {
        textArea.clear();
        String quantifierStr = chosenQuantifier.getValue();
        String qualifierConj = qualifierConjunction.getValue();
        String summarizatorConj = summarizatorConjunction.getValue();

        String subject1Str = chosenSubject1.getValue();
        String subject2Str = chosenSubject2.getValue();
        Subject subject1 = connectSubject(subject1Str);
        Subject subject2 = connectSubject(subject2Str);


        List<String> qualifierLabels = new ArrayList<>();
        List<String> linguisticVariablesQualifier = new ArrayList<>();
        switch (numberQualifierSpinner.getValue()) {
            case 1:
                qualifierLabels.add(cb_chosenQualifierLabel_1.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_1.getValue());
                break;
            case 2:
                qualifierLabels.add(cb_chosenQualifierLabel_1.getValue());
                qualifierLabels.add(cb_chosenQualifierLabel_2.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_1.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_2.getValue());
                break;
            case 3:
                qualifierLabels.add(cb_chosenQualifierLabel_1.getValue());
                qualifierLabels.add(cb_chosenQualifierLabel_2.getValue());
                qualifierLabels.add(cb_chosenQualifierLabel_3.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_1.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_2.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_3.getValue());
                break;
            case 4:
                qualifierLabels.add(cb_chosenQualifierLabel_1.getValue());
                qualifierLabels.add(cb_chosenQualifierLabel_2.getValue());
                qualifierLabels.add(cb_chosenQualifierLabel_3.getValue());
                qualifierLabels.add(cb_chosenQualifierLabel_4.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_1.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_2.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_3.getValue());
                linguisticVariablesQualifier.add(chosenQualifier_4.getValue());
                break;
            default:
                break;
        }

        SummarizerQualifier qualifier = connectSummarizerQualifier(linguisticVariablesQualifier.getFirst(), qualifierLabels.getFirst());
        for (int i = 1; i < qualifierLabels.size(); i++) {
            qualifier = qualifier.and(connectSummarizerQualifier(linguisticVariablesQualifier.get(i), qualifierLabels.get(i)));
        }


        List<String> summarizatorLabels = new ArrayList<>();
        List<String> linguisticVariablesSummarizator = new ArrayList<>();
        switch (numberSummarizerSpinner.getValue()) {
            case 1:
                summarizatorLabels.add(cb_chosenSummarizerLabel_1.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_1.getValue());
                break;
            case 2:
                summarizatorLabels.add(cb_chosenSummarizerLabel_1.getValue());
                summarizatorLabels.add(cb_chosenSummarizerLabel_2.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_1.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_2.getValue());
                break;
            case 3:
                summarizatorLabels.add(cb_chosenSummarizerLabel_1.getValue());
                summarizatorLabels.add(cb_chosenSummarizerLabel_2.getValue());
                summarizatorLabels.add(cb_chosenSummarizerLabel_3.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_1.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_2.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_3.getValue());
                break;
            case 4:
                summarizatorLabels.add(cb_chosenSummarizerLabel_1.getValue());
                summarizatorLabels.add(cb_chosenSummarizerLabel_2.getValue());
                summarizatorLabels.add(cb_chosenSummarizerLabel_3.getValue());
                summarizatorLabels.add(cb_chosenSummarizerLabel_4.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_1.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_2.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_3.getValue());
                linguisticVariablesSummarizator.add(chosenSummarizer_4.getValue());
                break;
            default:
                break;
        }


        SummarizerQualifier summarizator = connectSummarizerQualifier(linguisticVariablesSummarizator.getFirst(), summarizatorLabels.getFirst());
        for (int i = 1; i < summarizatorLabels.size(); i++) {
            summarizator = summarizator.and(connectSummarizerQualifier(linguisticVariablesSummarizator.get(i), summarizatorLabels.get(i)));
        }

        FuzzyQuantifier quantifier = connectQuantifier(quantifierStr);

        genForm3 = new TwoSubjectSummaryThird(subject1, subject2, quantifier, summarizator, qualifier, data, summarizatorConj, qualifierConj);
    }

    private SummarizerQualifier connectSummarizerQualifier(String linguisticVariable, String linguisticVariableLabel) {
        return switch (linguisticVariable) {
            case "Bedroom to room ratio" -> bedroomToRoomRatio.getLabel(linguisticVariableLabel);
            case "Median house age" -> medianHouseAge.getLabel(linguisticVariableLabel);
            case "Mean household type" -> meanHouseholdType.getLabel(linguisticVariableLabel);
            case "Median income" -> medianIncome.getLabel(linguisticVariableLabel);
            case "Population" -> population.getLabel(linguisticVariableLabel);
            case "Total rooms count" -> totalRoomsCount.getLabel(linguisticVariableLabel);
            case "Median house value" -> medianHouseValue.getLabel(linguisticVariableLabel);
            case "Distance LA" -> distanceLA.getLabel(linguisticVariableLabel);
            case "Distance SF" -> distanceSF.getLabel(linguisticVariableLabel);
            default -> null;
        };
    }

    private FuzzyQuantifier connectQuantifier(String quantifier) {
        return quantifier.equals("Relative") ? createRelative() : null;
    }

    public Subject connectSubject(String subjectStr) {
        return switch (subjectStr) {
            case "<1H OCEAN" -> Subject.SUB_HOUR_OCEAN;
            case "INLAND" -> Subject.INLAND;
            case "NEAR OCEAN" -> Subject.NEAR_OCEAN;
            case "NEAR BAY" -> Subject.NEAR_BAY;
            case "ISLAND" -> Subject.ISLAND;
            default -> null;
        };
    }


    // Visible functions
    public void setManyQualifierInvisible() {
        chosenQualifier_2.setVisible(false);
        chosenQualifier_3.setVisible(false);
        chosenQualifier_4.setVisible(false);
        chosenQualifierLabel_2.setVisible(false);
        chosenQualifierLabel_3.setVisible(false);
        chosenQualifierLabel_4.setVisible(false);
        cb_chosenQualifierLabel_2.setVisible(false);
        cb_chosenQualifierLabel_3.setVisible(false);
        cb_chosenQualifierLabel_4.setVisible(false);
    }

    public void setManySummarizatorInvisible() {
        chosenSummarizer_2.setVisible(false);
        chosenSummarizer_3.setVisible(false);
        chosenSummarizer_4.setVisible(false);
        chosenSummarizerLabel_2.setVisible(false);
        chosenSummarizerLabel_3.setVisible(false);
        chosenSummarizerLabel_4.setVisible(false);
        cb_chosenSummarizerLabel_2.setVisible(false);
        cb_chosenSummarizerLabel_3.setVisible(false);
        cb_chosenSummarizerLabel_4.setVisible(false);
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

