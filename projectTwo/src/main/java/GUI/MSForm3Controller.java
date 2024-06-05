package GUI;

import Builders.ContainerBuilder;
import Database.BlockGroup;
import Database.CSV;
import FuzzyCalculations.Container;
import FuzzyCalculations.FuzzyQuantifier;
import FuzzyCalculations.SummarizerQualifier;
import LinguisticSummarization.Subject;
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

import static GUI.LinguisticVariables.*;

public class MSForm3Controller {
    // ================ Code's Variables ================
    // Generator
    private TwoSubjectSummaryThird genForm3;
    private List<Pair<String, Double>> summaries;

    // Data
    private List<BlockGroup> data;
    String path = "dataBasePrep/prepared.csv";

    // LinguisticVariables
    private LinguisticVariables linguisticVariables;


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
    private Label chosenQualifierLabel_1;
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
    private Label chosenSummarizerLabel_1;
    @FXML
    private Label chosenSummarizerLabel_2;
    @FXML
    private Label chosenSummarizerLabel_3;
    @FXML
    private Label chosenSummarizerLabel_4;

    // Display of results
    @FXML
    private TextArea textArea;

    // Swap
    @FXML
    private CheckBox generateWithSwapCheckBox;
    @FXML
    private CheckBox generateWithSwapSubjectsCheckBox;

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

    // Other
    @FXML
    private CheckBox advancedOptionsCheckBox;
    @FXML
    private Label titleLabel;
    @FXML
    private Label templateLabel;
    @FXML
    private Label chooseSubjectLabel;
    @FXML
    private Label summarizatorConjLabel;
    @FXML
    private Label numberSummarizersLabel;
    @FXML
    private Label numberQualifiersLabel;
    @FXML
    private Label saveLabel;
    @FXML
    private Button generateAllSummariesButton;
    @FXML
    private Button generateBestSummaryButton;
    @FXML
    private Label chooseQuantifierLabel;
    @FXML
    private Button swapButton;
    @FXML
    private Label qualifierConjLabel;
    @FXML
    private Button swapSubjectsButton;
    @FXML
    private Label chooseSubjectLabel2;


    // ================ View's Functions ================
    // Init functions
    @FXML
    public void initialize() {
        // Read data from database
        data = CSV.readCSV(path);

        // Init summaries
        summaries = new ArrayList<>();

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


        linguisticVariables = LinguisticVariables.getInstance(data);

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
        chosenQualifier_1.getItems().addAll(linguisticVariables.getLinguisticVariables());
        chosenQualifier_1.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateSubComboBox(newValue, cb_chosenQualifierLabel_1);
        });

        chosenQualifier_2.getItems().addAll(linguisticVariables.getLinguisticVariables());
        chosenQualifier_2.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateSubComboBox(newValue, cb_chosenQualifierLabel_2);
        });

        chosenQualifier_3.getItems().addAll(linguisticVariables.getLinguisticVariables());
        chosenQualifier_3.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateSubComboBox(newValue, cb_chosenQualifierLabel_3);
        });

        chosenQualifier_4.getItems().addAll(linguisticVariables.getLinguisticVariables());
        chosenQualifier_4.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateSubComboBox(newValue, cb_chosenQualifierLabel_4);
        });


        // Set up ComboBoxes for Summarizers
        chosenSummarizer_1.getItems().addAll(linguisticVariables.getLinguisticVariables());
        chosenSummarizer_1.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateSubComboBox(newValue, cb_chosenSummarizerLabel_1);
        });

        chosenSummarizer_2.getItems().addAll(linguisticVariables.getLinguisticVariables());
        chosenSummarizer_2.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateSubComboBox(newValue, cb_chosenSummarizerLabel_2);
        });

        chosenSummarizer_3.getItems().addAll(linguisticVariables.getLinguisticVariables());
        chosenSummarizer_3.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateSubComboBox(newValue, cb_chosenSummarizerLabel_3);
        });

        chosenSummarizer_4.getItems().addAll(linguisticVariables.getLinguisticVariables());
        chosenSummarizer_4.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            updateSubComboBox(newValue, cb_chosenSummarizerLabel_4);
        });

        // Set items invisible
        setManyQualifierInvisible();
        setManySummarizatorInvisible();
        advancedOptionsInvisible();
    }


    // Generating summaries
    @FXML
    public void generateBestSummaries() {
        if (generateWithSwapCheckBox.isSelected() && generateWithSwapSubjectsCheckBox.isSelected()) {
            multiSwapBest();
        } else if (generateWithSwapCheckBox.isSelected()) {
            generateBestSummariesWithSwap();
        } else if (generateWithSwapSubjectsCheckBox.isSelected()) {
            generateBestSummariesWithSwapSubjects();
        } else {
            generateBestSummariesNormal();
        }
    }

    @FXML
    public void generateAllSummaries() {
        if (generateWithSwapCheckBox.isSelected() && generateWithSwapSubjectsCheckBox.isSelected()) {
            multiSwap();
        } else if (generateWithSwapCheckBox.isSelected()) {
            generateAllSummariesWithSwap();
        } else if (generateWithSwapSubjectsCheckBox.isSelected()) {
            generateAllSummariesWithSwapSubjects();
        } else {
            generateAllSummariesNormal();
        }
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

    @FXML
    public void swapP1withP2() {
        String subject1Str = chosenSubject1.getValue();
        String subject2Str = chosenSubject2.getValue();
        chosenSubject1.setValue(subject2Str);
        chosenSubject2.setValue(subject1Str);
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


    // Advanced options
    @FXML
    public void advancedOptions() {
        if (advancedOptionsCheckBox.isSelected()) {
            advancedOptionsVisible();
        } else {
            advancedOptionsInvisible();
        }
    }


    // ================ Code's Functions ================
    // Init ComboBoxes
    private void updateSubComboBox(String mainItem, ComboBox<String> comboBox) {
        if (mainItem != null) {
            comboBox.getItems().clear();
            switch (mainItem) {
                case "Bedroom to room ratio":
                    comboBox.getItems().addAll(linguisticVariables.getBedroomToRoomRatio().getLabels().keySet());
                    break;
                case "Median house age":
                    comboBox.getItems().addAll(linguisticVariables.getMedianHouseAge().getLabels().keySet());
                    break;
                case "Mean household type":
                    comboBox.getItems().addAll(linguisticVariables.getMeanHouseholdType().getLabels().keySet());
                    break;
                case "Median income":
                    comboBox.getItems().addAll(linguisticVariables.getMedianIncome().getLabels().keySet());
                    break;
                case "Population":
                    comboBox.getItems().addAll(linguisticVariables.getPopulation().getLabels().keySet());
                    break;
                case "Total rooms count":
                    comboBox.getItems().addAll(linguisticVariables.getTotalRoomsCount().getLabels().keySet());
                    break;
                case "Median house value":
                    comboBox.getItems().addAll(linguisticVariables.getMedianHouseValue().getLabels().keySet());
                    break;
                case "Distance LA":
                    comboBox.getItems().addAll(linguisticVariables.getDistanceLA().getLabels().keySet());
                    break;
                case "Distance SF":
                    comboBox.getItems().addAll(linguisticVariables.getDistanceSF().getLabels().keySet());
                    break;
                default:
                    break;
            }
        }
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


    // Swap functions
    public void generateBestSummariesNormal() {
        System.out.println("Generate Best Summary (Normal mode) Clicked");

        generatePrepare();
        summaries = Collections.singletonList(genForm3.generateBest());
        generateAfter();
    }

    public void generateBestSummariesWithSwap() {
        System.out.println("Generate Best Summary (Swap mode) Clicked");
        List<Pair<String, Double>> summaries_1;
        List<Pair<String, Double>> summaries_2;

        generatePrepare();
        summaries_1 = Collections.singletonList(genForm3.generateBest());

        swapWwithS();
        generatePrepare();
        summaries_2 = Collections.singletonList(genForm3.generateBest());
        swapWwithS();

        summaries = new ArrayList<>();
        for (int i = 0; i < summaries_1.size(); i++) {
            summaries.add(summaries_1.get(i));
            summaries.add(summaries_2.get(i));
        }

        generateAfter();
    }

    public void generateBestSummariesWithSwapSubjects() {
        System.out.println("Generate Best Summary (Swap Subjects mode) Clicked");

        List<Pair<String, Double>> summaries_1;
        List<Pair<String, Double>> summaries_2;

        generatePrepare();
        summaries_1 = Collections.singletonList(genForm3.generateBest());

        swapP1withP2();
        generatePrepare();
        summaries_2 = Collections.singletonList(genForm3.generateBest());
        swapP1withP2();

        summaries = new ArrayList<>();
        for (int i = 0; i < summaries_1.size(); i++) {
            summaries.add(summaries_1.get(i));
            summaries.add(summaries_2.get(i));
        }

        generateAfter();
    }

    public void multiSwapBest() {
        System.out.println("Generate Best Summary (Both mode) Clicked");

        List<Pair<String, Double>> summaries_1;
        List<Pair<String, Double>> summaries_2;
        List<Pair<String, Double>> summaries_3;
        List<Pair<String, Double>> summaries_4;

        generatePrepare();
        summaries_1 = Collections.singletonList(genForm3.generateBest());

        swapWwithS();
        generatePrepare();
        summaries_2 = Collections.singletonList(genForm3.generateBest());
        swapWwithS();

        swapP1withP2();

        generatePrepare();
        summaries_3 = Collections.singletonList(genForm3.generateBest());

        swapWwithS();
        generatePrepare();
        summaries_4 = Collections.singletonList(genForm3.generateBest());
        swapWwithS();

        swapP1withP2();

        summaries = new ArrayList<>();
        for (int i = 0; i < summaries_1.size(); i++) {
            summaries.add(summaries_1.get(i));
            summaries.add(summaries_2.get(i));
            summaries.add(summaries_3.get(i));
            summaries.add(summaries_4.get(i));
        }

        generateAfter();
    }

    public void generateAllSummariesNormal() {
        System.out.println("Generate All Summaries (Normal mode) Clicked");

        generatePrepare();
        summaries = genForm3.generateSummaries();
        generateAfter();
    }

    public void generateAllSummariesWithSwap() {
        System.out.println("Generate All Summaries (Swap mode) Clicked");
        List<Pair<String, Double>> summaries_1;
        List<Pair<String, Double>> summaries_2;

        generatePrepare();
        summaries_1 = genForm3.generateSummaries();

        swapWwithS();
        generatePrepare();
        summaries_2 = genForm3.generateSummaries();
        swapWwithS();

        summaries = new ArrayList<>();
        for (int i = 0; i < summaries_1.size(); i++) {
            summaries.add(summaries_1.get(i));
            summaries.add(summaries_2.get(i));
        }

        generateAfter();
    }

    public void generateAllSummariesWithSwapSubjects() {
        System.out.println("Generate All Summaries (Swap Subjects mode) Clicked");
        List<Pair<String, Double>> summaries_1;
        List<Pair<String, Double>> summaries_2;

        generatePrepare();
        summaries_1 = genForm3.generateSummaries();

        swapP1withP2();
        generatePrepare();
        summaries_2 = genForm3.generateSummaries();
        swapP1withP2();

        summaries = new ArrayList<>();
        for (int i = 0; i < summaries_1.size(); i++) {
            summaries.add(summaries_1.get(i));
            summaries.add(summaries_2.get(i));
        }

        generateAfter();
    }

    public void multiSwap() {
        System.out.println("Generate All Summaries (Both mode) Clicked");
        List<Pair<String, Double>> summaries_1;
        List<Pair<String, Double>> summaries_2;
        List<Pair<String, Double>> summaries_3;
        List<Pair<String, Double>> summaries_4;

        generatePrepare();
        summaries_1 = genForm3.generateSummaries();

        swapWwithS();
        generatePrepare();
        summaries_2 = genForm3.generateSummaries();
        swapWwithS();

        swapP1withP2();

        generatePrepare();
        summaries_3 = genForm3.generateSummaries();

        swapWwithS();
        generatePrepare();
        summaries_4 = genForm3.generateSummaries();
        swapWwithS();

        swapP1withP2();

        summaries = new ArrayList<>();
        for (int i = 0; i < summaries_1.size(); i++) {
            summaries.add(summaries_1.get(i));
            summaries.add(summaries_2.get(i));
            summaries.add(summaries_3.get(i));
            summaries.add(summaries_4.get(i));
        }

        generateAfter();
    }


    // Connected functionality
    private SummarizerQualifier connectSummarizerQualifier(String linguisticVariable, String linguisticVariableLabel) {
        return switch (linguisticVariable) {
            case "Bedroom to room ratio" -> linguisticVariables.getBedroomToRoomRatio().getLabel(linguisticVariableLabel);
            case "Median house age" -> linguisticVariables.getMedianHouseAge().getLabel(linguisticVariableLabel);
            case "Mean household type" -> linguisticVariables.getMeanHouseholdType().getLabel(linguisticVariableLabel);
            case "Median income" -> linguisticVariables.getMedianIncome().getLabel(linguisticVariableLabel);
            case "Population" -> linguisticVariables.getPopulation().getLabel(linguisticVariableLabel);
            case "Total rooms count" -> linguisticVariables.getTotalRoomsCount().getLabel(linguisticVariableLabel);
            case "Median house value" -> linguisticVariables.getMedianHouseValue().getLabel(linguisticVariableLabel);
            case "Distance LA" -> linguisticVariables.getDistanceLA().getLabel(linguisticVariableLabel);
            case "Distance SF" -> linguisticVariables.getDistanceSF().getLabel(linguisticVariableLabel);
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


    // Advanced options
    public void advancedOptionsVisible() {
        titleLabel.setText("Generator of multi-subject linguistic summaries in form 3");
        templateLabel.setText("Template: Q P1, which are W, compare to P2 are/have S. [T_1]");
        chooseSubjectLabel.setText("Choose subject 1");
        chosenSubject1.setLayoutX(482);
        chooseSubjectLabel.setLayoutX(319);
        chooseSubjectLabel2.setText("Choose subject 2");
        chosenSubject2.setLayoutX(482);
        chooseSubjectLabel2.setLayoutX(319);
        summarizatorConjLabel.setVisible(true);
        summarizatorConjunction.setVisible(true);
        qualifierConjunction.setVisible(true);
        qualifierConjLabel.setVisible(true);
        numberSummarizersLabel.setText("Number of summarizers");
        chosenSummarizerLabel_1.setText("Choose summarizer 1");
        chosenSummarizerLabel_2.setText("Choose summarizer 2");
        chosenSummarizerLabel_3.setText("Choose summarizer 3");
        chosenSummarizerLabel_4.setText("Choose summarizer 4");
        numberQualifiersLabel.setText("Number of qualifiers");
        chosenQualifierLabel_1.setText("Choose qualifier 1");
        chosenQualifierLabel_2.setText("Choose qualifier 2");
        chosenQualifierLabel_3.setText("Choose qualifier 3");
        chosenQualifierLabel_4.setText("Choose qualifier 4");
        saveSummariesButton.setVisible(true);
        saveSummariesCSVButton.setVisible(true);
        saveLabel.setVisible(true);
        generateAllSummariesButton.setText("Generate All Summaries");
        generateBestSummaryButton.setText("Generate Best Summary");
        chooseQuantifierLabel.setVisible(true);
        chosenQuantifier.setVisible(true);
        swapSubjectsButton.setText("Swap P1 with P2");
        generateWithSwapSubjectsCheckBox.setText("Generate with Swap: P1 with P2");
        generateWithSwapCheckBox.setText("Generate with Swap: W with S");
        swapButton.setText("Swap W with S");
    }

    public void advancedOptionsInvisible() {
        titleLabel.setText("Generator of summaries like as in the template below");
        templateLabel.setText("Template: Q P1, which are W, compare to P2 are/have S. [T_1]\tE.g.: Almost none BG <1H OCEAN which are man to man compared to BG INLAND are aged. [1.0]");
        chooseSubjectLabel.setText("Choose subject \n(this is P1 on Template)");
        chosenSubject1.setLayoutX(174);
        chooseSubjectLabel.setLayoutX(11);
        chooseSubjectLabel2.setText("Choose subject \n(this is P2 on Template)");
        chosenSubject2.setLayoutX(174);
        chooseSubjectLabel2.setLayoutX(11);
        summarizatorConjLabel.setVisible(false);
        summarizatorConjunction.setVisible(false);
        qualifierConjunction.setVisible(false);
        qualifierConjLabel.setVisible(false);
        numberSummarizersLabel.setText("Number of features 2");
        chosenSummarizerLabel_1.setText("Choose attribute 2");
        chosenSummarizerLabel_2.setText("Additionally");
        chosenSummarizerLabel_3.setText("Additionally");
        chosenSummarizerLabel_4.setText("Additionally");
        numberQualifiersLabel.setText("Number of features 1");
        chosenQualifierLabel_1.setText("Choose attribute 1");
        chosenQualifierLabel_2.setText("Additionally");
        chosenQualifierLabel_3.setText("Additionally");
        chosenQualifierLabel_4.setText("Additionally");
        saveSummariesButton.setVisible(false);
        saveSummariesCSVButton.setVisible(false);
        saveLabel.setVisible(false);
        generateAllSummariesButton.setText("Generate All");
        generateBestSummaryButton.setText("Generate Best");
        chooseQuantifierLabel.setVisible(false);
        chosenQuantifier.setVisible(false);
        swapSubjectsButton.setText("Swap Subjects");
        generateWithSwapSubjectsCheckBox.setText("Generate with Swap Subjects");
        generateWithSwapCheckBox.setText("Generate with Swap Attributes");
        swapButton.setText("Swap Attributes");
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

