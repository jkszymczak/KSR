package GUI;

import Builders.ContainerBuilder;
import Database.BlockGroup;
import Database.CSV;
import FuzzyCalculations.Container;
import FuzzyCalculations.FuzzyQuantifier;
import FuzzyCalculations.SummarizerQualifier;
import LinguisticSummarization.*;
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

public class MSForm1Controller {
    // ================ Code's Variables ================
    // Generator
    private TwoSubjectSummaryFirst genForm1;
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

    // Subjects
    @FXML
    private ComboBox<String> chosenSubject1;
    @FXML
    private ComboBox<String> chosenSubject2;

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
    private Label saveLabel;
    @FXML
    private Button generateAllSummariesButton;
    @FXML
    private Button generateBestSummaryButton;
    @FXML
    private Label chooseQuantifierLabel;
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

        // init Menu
        initMenu();

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

        summarizatorConjunction.getItems().addAll("are", "have", "are in");
        summarizatorConjunction.setValue(summarizatorConjunction.getItems().getFirst());

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
        setManySummarizatorInvisible();
        advancedOptionsInvisible();
    }


    // Generating summaries
    @FXML
    public void generateBestSummaries() {
        if (generateWithSwapSubjectsCheckBox.isSelected()) {
            generateBestSummariesWithSwapSubjects();
        } else {
            generateBestSummariesNormal();
        }
    }

    @FXML
    public void generateAllSummaries() {
        if (generateWithSwapSubjectsCheckBox.isSelected()) {
            generateAllSummariesWithSwapSubjects();
        } else {
            generateAllSummariesNormal();
        }
    }


    // Swap
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
        String summarizatorConj = summarizatorConjunction.getValue();

        String subject1Str = chosenSubject1.getValue();
        String subject2Str = chosenSubject2.getValue();
        Subject subject1 = connectSubject(subject1Str);
        Subject subject2 = connectSubject(subject2Str);

        List<String> summarizatorLabels = new ArrayList<>();
        List<String> linguisticVariables = new ArrayList<>();
        switch (numberSummarizerSpinner.getValue()) {
            case 1:
                summarizatorLabels.add(cb_chosenSummarizerLabel_1.getValue());
                linguisticVariables.add(chosenSummarizer_1.getValue());
                break;
            case 2:
                summarizatorLabels.add(cb_chosenSummarizerLabel_1.getValue());
                summarizatorLabels.add(cb_chosenSummarizerLabel_2.getValue());
                linguisticVariables.add(chosenSummarizer_1.getValue());
                linguisticVariables.add(chosenSummarizer_2.getValue());
                break;
            case 3:
                summarizatorLabels.add(cb_chosenSummarizerLabel_1.getValue());
                summarizatorLabels.add(cb_chosenSummarizerLabel_2.getValue());
                summarizatorLabels.add(cb_chosenSummarizerLabel_3.getValue());
                linguisticVariables.add(chosenSummarizer_1.getValue());
                linguisticVariables.add(chosenSummarizer_2.getValue());
                linguisticVariables.add(chosenSummarizer_3.getValue());
                break;
            case 4:
                summarizatorLabels.add(cb_chosenSummarizerLabel_1.getValue());
                summarizatorLabels.add(cb_chosenSummarizerLabel_2.getValue());
                summarizatorLabels.add(cb_chosenSummarizerLabel_3.getValue());
                summarizatorLabels.add(cb_chosenSummarizerLabel_4.getValue());
                linguisticVariables.add(chosenSummarizer_1.getValue());
                linguisticVariables.add(chosenSummarizer_2.getValue());
                linguisticVariables.add(chosenSummarizer_3.getValue());
                linguisticVariables.add(chosenSummarizer_4.getValue());
                break;
            default:
                break;
        }


        SummarizerQualifier summarizator = connectSummarizerQualifier(linguisticVariables.getFirst(), summarizatorLabels.getFirst());
        for (int i = 1; i < summarizatorLabels.size(); i++) {
            summarizator = summarizator.and(connectSummarizerQualifier(linguisticVariables.get(i), summarizatorLabels.get(i)));
        }

        FuzzyQuantifier quantifier = connectQuantifier(quantifierStr);

        genForm1 = new TwoSubjectSummaryFirst(subject1, subject2, quantifier, summarizator, data, summarizatorConj);
    }


    // Swap Subjects functions
    public void generateBestSummariesNormal() {
        System.out.println("Generate Best Summary (Normal mode) Clicked");

        generatePrepare();
        summaries = Collections.singletonList(genForm1.generateBest());
        generateAfter();
    }

    public void generateBestSummariesWithSwapSubjects() {
        System.out.println("Generate Best Summary (Swap Subjects mode) Clicked");

        List<Pair<String, Double>> summaries_1;
        List<Pair<String, Double>> summaries_2;

        generatePrepare();
        summaries_1 = Collections.singletonList(genForm1.generateBest());

        swapP1withP2();
        generatePrepare();
        summaries_2 = Collections.singletonList(genForm1.generateBest());
        swapP1withP2();

        summaries = new ArrayList<>();
        for (int i = 0; i < summaries_1.size(); i++) {
            summaries.add(summaries_1.get(i));
            summaries.add(summaries_2.get(i));
        }

        generateAfter();
    }


    public void generateAllSummariesNormal() {
        System.out.println("Generate All Summaries (Normal mode) Clicked");

        generatePrepare();
        summaries = genForm1.generateSummaries();
        generateAfter();
    }

    public void generateAllSummariesWithSwapSubjects() {
        System.out.println("Generate All Summaries (Swap Subjects mode) Clicked");

        List<Pair<String, Double>> summaries_1;
        List<Pair<String, Double>> summaries_2;

        generatePrepare();
        summaries_1 = genForm1.generateSummaries();

        swapP1withP2();
        generatePrepare();
        summaries_2 = genForm1.generateSummaries();
        swapP1withP2();

        summaries = new ArrayList<>();
        for (int i = 0; i < summaries_1.size(); i++) {
            summaries.add(summaries_1.get(i));
            summaries.add(summaries_2.get(i));
        }

        generateAfter();
    }


    // connected functions
    private SummarizerQualifier connectSummarizerQualifier(String linguisticVariable, String linguisticVariableLabel) {
        return switch (linguisticVariable) {
            case "Bedroom to room ratio" ->
                    linguisticVariables.getBedroomToRoomRatio().getLabel(linguisticVariableLabel);
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
        titleLabel.setText("Generator of multi-subject linguistic summaries in form 1");
        templateLabel.setText("Template: Q P1 compare to P2  are/have S. [T_1]");
        chooseSubjectLabel.setText("Choose subject 1");
        chosenSubject1.setLayoutX(482);
        chooseSubjectLabel.setLayoutX(319);
        chooseSubjectLabel2.setText("Choose subject 2");
        chosenSubject2.setLayoutX(482);
        chooseSubjectLabel2.setLayoutX(319);
        summarizatorConjLabel.setVisible(true);
        summarizatorConjunction.setVisible(true);
        numberSummarizersLabel.setText("Number of summarizers");
        chosenSummarizerLabel_1.setText("Choose summarizer 1");
        chosenSummarizerLabel_2.setText("Choose summarizer 2");
        chosenSummarizerLabel_3.setText("Choose summarizer 3");
        chosenSummarizerLabel_4.setText("Choose summarizer 4");
        saveSummariesButton.setVisible(true);
        saveSummariesCSVButton.setVisible(true);
        saveLabel.setVisible(true);
        generateAllSummariesButton.setText("Generate All Summaries");
        generateBestSummaryButton.setText("Generate Best Summary");
        chooseQuantifierLabel.setVisible(true);
        chosenQuantifier.setVisible(true);
        swapSubjectsButton.setText("Swap P1 with P2");
        generateWithSwapSubjectsCheckBox.setText("Generate with Swap: P1 with P2");
    }

    public void advancedOptionsInvisible() {
        titleLabel.setText("Generator of summaries like as in the template below");
        templateLabel.setText("Template: Q P1 compare to P2  are/have S. [T_1]\tE.g.: Many Block Groups <1H OCEAN compare to Block Groups INLAND are aged. [0.3]");
        chooseSubjectLabel.setText("Choose subject \n(this is P1 on Template)");
        chosenSubject1.setLayoutX(174);
        chooseSubjectLabel.setLayoutX(11);
        chooseSubjectLabel2.setText("Choose subject \n(this is P2 on Template)");
        chosenSubject2.setLayoutX(174);
        chooseSubjectLabel2.setLayoutX(11);
        summarizatorConjLabel.setVisible(false);
        summarizatorConjunction.setVisible(false);
        numberSummarizersLabel.setText("Number of features");
        chosenSummarizerLabel_1.setText("Choose attribute");
        chosenSummarizerLabel_2.setText("Additionally");
        chosenSummarizerLabel_3.setText("Additionally");
        chosenSummarizerLabel_4.setText("Additionally");
        saveSummariesButton.setVisible(false);
        saveSummariesCSVButton.setVisible(false);
        saveLabel.setVisible(false);
        generateAllSummariesButton.setText("Generate All");
        generateBestSummaryButton.setText("Generate Best");
        chooseQuantifierLabel.setVisible(false);
        chosenQuantifier.setVisible(false);
        swapSubjectsButton.setText("Swap Subjects");
        generateWithSwapSubjectsCheckBox.setText("Generate with Swap Subjects");
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

