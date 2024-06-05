package GUI;

import Builders.ContainerBuilder;
import Builders.LinguisticSummaryBuilder;
import Database.BlockGroup;
import Database.CSV;
import FuzzyCalculations.*;
import LinguisticSummarization.LinguisticSummary;
import LinguisticSummarization.LinguisticSummaryGenerator;
import LinguisticSummarization.LinguisticSummaryType;
import LinguisticSummarization.Subject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.stage.Stage;

import static GUI.LinguisticVariables.*;

public class SSForm1Controller {
    // ================ Code's Variables ================
    // Generator
    private LinguisticSummaryGenerator genForm1;
    private List<LinguisticSummary> summaries;

    // Variable for weights
    private List<Double> weights;
    private Double margin = 0.01;

    // Data
    private List<BlockGroup> data;
    String path = "dataBasePrep/prepared.csv";

    // LinguisticVariables
    private LinguisticVariables linguisticVariables;


    // ================ View's Variables ================
    // Subject
    @FXML
    private ComboBox<String> chosenSubject;

    // Quantifier and Conjunction
    @FXML
    private ComboBox<String> chosenQuantifier;
    @FXML
    private ComboBox<String> summarizatorConjunction;

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
    @FXML
    private TextArea qualityMeasuresTextArea;
    @FXML
    private CheckBox generateDetailedCheckBox;

    // Saving functionality
    @FXML
    private Button saveSummariesButton;
    @FXML
    private Button saveSummariesCSVButton;
    @FXML
    private Label status;

    // Weights
    @FXML
    private CheckBox defaultWeightsCheckBox;
    @FXML
    private Label descriptionOfWeights;
    @FXML
    private Label label_actualSum;
    @FXML
    private Label label_SumWeights;
    @FXML
    private Button equalsWeightsButton;
    @FXML
    private Button resetWeightsButton;
    @FXML
    private Spinner<Double> T_1Spinner;
    @FXML
    private Spinner<Double> T_2Spinner;
    @FXML
    private Spinner<Double> T_3Spinner;
    @FXML
    private Spinner<Double> T_4Spinner;
    @FXML
    private Spinner<Double> T_5Spinner;
    @FXML
    private Spinner<Double> T_6Spinner;
    @FXML
    private Spinner<Double> T_7Spinner;
    @FXML
    private Spinner<Double> T_8Spinner;
    @FXML
    private Spinner<Double> T_9Spinner;
    @FXML
    private Spinner<Double> T_10Spinner;
    @FXML
    private Spinner<Double> T_11Spinner;
    @FXML
    private Label label_T_1;
    @FXML
    private Label label_T_2;
    @FXML
    private Label label_T_3;
    @FXML
    private Label label_T_4;
    @FXML
    private Label label_T_5;
    @FXML
    private Label label_T_6;
    @FXML
    private Label label_T_7;
    @FXML
    private Label label_T_8;
    @FXML
    private Label label_T_9;
    @FXML
    private Label label_T_10;
    @FXML
    private Label label_T_11;

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
    private Button generateOptimalSummaryButton;
    @FXML
    private Button generateBestSummaryButton;
    @FXML
    private Label explainLabel;
    @FXML
    private Label chooseQuantifierLabel;






    // ================ View's Functions ================
    // Init functions
    @FXML
    public void initialize() {
        // Read data from database
        data = CSV.readCSV(path);

        linguisticVariables = LinguisticVariables.getInstance(data);

        // Init menu
        initMenu();

        // Number of Summarizers Spinner
        numberSummarizerSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1));
        numberSummarizerSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            numberSummarizerConfirm();
        });

        // Weights Spinners
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory1 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory1.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory2 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory2.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory3 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory3.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory4 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory4.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory5 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory5.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory6 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory6.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory7 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory7.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory8 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory8.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory9 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory9.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory10 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory10.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory11 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory11.setAmountToStepBy(0.01);

        T_1Spinner.setValueFactory(doubleFactory1);
        T_2Spinner.setValueFactory(doubleFactory2);
        T_3Spinner.setValueFactory(doubleFactory3);
        T_4Spinner.setValueFactory(doubleFactory4);
        T_5Spinner.setValueFactory(doubleFactory5);
        T_6Spinner.setValueFactory(doubleFactory6);
        T_7Spinner.setValueFactory(doubleFactory7);
        T_8Spinner.setValueFactory(doubleFactory8);
        T_9Spinner.setValueFactory(doubleFactory9);
        T_10Spinner.setValueFactory(doubleFactory10);
        T_11Spinner.setValueFactory(doubleFactory11);
        setDefaultWeights();

        // Add listeners to weights spinners
        addSpinnerListener(T_1Spinner);
        addSpinnerListener(T_2Spinner);
        addSpinnerListener(T_3Spinner);
        addSpinnerListener(T_4Spinner);
        addSpinnerListener(T_5Spinner);
        addSpinnerListener(T_6Spinner);
        addSpinnerListener(T_7Spinner);
        addSpinnerListener(T_8Spinner);
        addSpinnerListener(T_9Spinner);
        addSpinnerListener(T_10Spinner);
        addSpinnerListener(T_11Spinner);
        calculateSumOfWeights();


        // Set up subject
        chosenSubject.getItems().addAll("All database", "<1H OCEAN", "INLAND", "NEAR OCEAN", "NEAR BAY", "ISLAND");
        chosenSubject.setValue(chosenSubject.getItems().getFirst());

        // Set up ComboBoxes for Quantifier and Conjunction
        chosenQuantifier.getItems().addAll("Absolute", "Relative");
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
        setWeightsPanelInvisible();
        setManySummarizatorInvisible();
        setDetailedTextAreaInvisible();
        advancedOptionsInvisible();
    }


    // Generating summaries
    @FXML
    public void generateOptimalSummary() {
        System.out.println("Generate Optimal Summary Clicked");
        generatePrepare();

        if (checkWeightsCorrectness()) {
            System.out.println("Weights are correct");
            summaries = genForm1.generateSummaries();
            for (LinguisticSummary summary : summaries) {
                System.out.println(summary);
                genForm1.calculateQualityMeasures(weights, summary);
            }
            LinguisticSummary optimalSummary = genForm1.calculateOptimalSummary(summaries);
            summaries = Collections.singletonList(optimalSummary);

            System.out.println("Optimal summary: " + optimalSummary);
            String text = textArea.getText();
            text += optimalSummary.toString() + "\n";
            textArea.setText(text);

            if (generateDetailedCheckBox.isSelected()) {
                String tempText = qualityMeasuresTextArea.getText();
                tempText += LinguisticSummary.toStringDetailed(summaries);
                qualityMeasuresTextArea.setText(tempText);
            }

            saveSummariesButton.setDisable(false);
            saveSummariesCSVButton.setDisable(false);
        } else {
            System.out.println("Weights are incorrect !\nDo not sum up to 1.0 !");
            textArea.setText("Weights are incorrect !\nDo not sum up to 1.0 !");
        }
    }

    @FXML
    public void generateBestSummaries() {
        System.out.println("Generate Best Summary Clicked");
        generatePrepare();

        if (checkWeightsCorrectness()) {
            System.out.println("Weights are correct");
            summaries = Collections.singletonList(genForm1.generateBest());
            generateAfter();
        } else {
            System.out.println("Weights are incorrect !\nDo not sum up to 1.0 !");
            textArea.setText("Weights are incorrect !\nDo not sum up to 1.0 !");
        }
    }

    @FXML
    public void generateAllSummaries() {
        System.out.println("Generate All Summaries Clicked");
        generatePrepare();

        if (checkWeightsCorrectness()) {
            System.out.println("Weights are correct");
            summaries = genForm1.generateSummaries();
            generateAfter();
        } else {
            System.out.println("Weights are incorrect !\nDo not sum up to 1.0 !");
            textArea.setText("Weights are incorrect !\nDo not sum up to 1.0 !");
        }
    }


    // Display detailed function
    @FXML
    public void onCheckBoxGenerateDetailedChangeState() {
        if (generateDetailedCheckBox.isSelected()) {
            setDetailedTextAreaVisible();
        } else {
            setDetailedTextAreaInvisible();
        }
    }

    // Weights functions
    @FXML
    public void onCheckBoxChangeState() {
        if (!defaultWeightsCheckBox.isSelected()) {
            setWeightsPanelInvisible();
        } else {
            setWeightsPanelVisible();
        }
    }

    @FXML
    public void setEqualsWeightsButtonClick() {
        setEqualsWeights();
    }

    @FXML
    public void setDefaultWeightsButtonClick() {
        setDefaultWeights();
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
                CSV.saveSummariesCSV(String.valueOf(file.toPath()), summaries);

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
        for (LinguisticSummary summary : summaries) {
            System.out.println(summary);
            String text = textArea.getText();
            text += summary.toString() + "\n";
            textArea.setText(text);
            genForm1.calculateQualityMeasures(weights, summary);
        }

        if (generateDetailedCheckBox.isSelected()) {
            String text = qualityMeasuresTextArea.getText();
            text += LinguisticSummary.toStringDetailed(summaries);
            qualityMeasuresTextArea.setText(text);
        }

        saveSummariesButton.setDisable(false);
        saveSummariesCSVButton.setDisable(false);
    }

    public void generatePrepare() {
        textArea.clear();
        qualityMeasuresTextArea.clear();

        String subjectStr = chosenSubject.getValue();
        Subject subject = connectSubject(subjectStr); // TODO make subject usefully

        String quantifierStr = chosenQuantifier.getValue();
        String summarizatorConj = summarizatorConjunction.getValue();

        weights = new ArrayList<>(Arrays.asList(T_1Spinner.getValue(), T_2Spinner.getValue(),
                T_3Spinner.getValue(), T_4Spinner.getValue(), T_5Spinner.getValue(), T_6Spinner.getValue(),
                T_7Spinner.getValue(), T_8Spinner.getValue(), T_9Spinner.getValue(), T_10Spinner.getValue(),
                T_11Spinner.getValue()));

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

        List<BlockGroup> subjectData;
        String subjectConj;
        if (subjectStr.equals("All database")) {
            subjectConj ="Block Groups";
            subjectData = data;
        } else {
            subjectConj = " Block Groups " + subject.label;
            subjectData = data.stream().filter(blockGroup -> blockGroup.getLabel().equals(subject.label)).toList();
        }

        genForm1 = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.First)
                .onSet(subjectData).withSummarizator(summarizator)
                .withQuantifier(quantifier)
                .withSubject(subjectConj)
                .withSummarizatorConjunction(summarizatorConj)
                .build();
    }

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
        return switch (quantifier) {
            case "Absolute" -> createAbsolute(data);
            case "Relative" -> createRelative();
            default -> null;
        };
    }

    public Subject connectSubject(String subjectStr) {
        return switch (subjectStr) {
            case "All database" -> Subject.ALL_DATABASE;
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

    public void setWeightsPanelVisible() {
        descriptionOfWeights.setVisible(true);
        resetWeightsButton.setVisible(true);
        equalsWeightsButton.setVisible(true);
        label_actualSum.setVisible(true);
        label_SumWeights.setVisible(true);
        T_1Spinner.setVisible(true);
        T_2Spinner.setVisible(true);
        T_3Spinner.setVisible(true);
        T_4Spinner.setVisible(true);
        T_5Spinner.setVisible(true);
        T_6Spinner.setVisible(true);
        T_7Spinner.setVisible(true);
        T_8Spinner.setVisible(true);
        T_9Spinner.setVisible(true);
        T_10Spinner.setVisible(true);
        T_11Spinner.setVisible(true);
        label_T_1.setVisible(true);
        label_T_2.setVisible(true);
        label_T_3.setVisible(true);
        label_T_4.setVisible(true);
        label_T_5.setVisible(true);
        label_T_6.setVisible(true);
        label_T_7.setVisible(true);
        label_T_8.setVisible(true);
        label_T_9.setVisible(true);
        label_T_10.setVisible(true);
        label_T_11.setVisible(true);
    }

    public void setWeightsPanelInvisible() {
        descriptionOfWeights.setVisible(false);
        resetWeightsButton.setVisible(false);
        equalsWeightsButton.setVisible(false);
        label_actualSum.setVisible(false);
        label_SumWeights.setVisible(false);
        T_1Spinner.setVisible(false);
        T_2Spinner.setVisible(false);
        T_3Spinner.setVisible(false);
        T_4Spinner.setVisible(false);
        T_5Spinner.setVisible(false);
        T_6Spinner.setVisible(false);
        T_7Spinner.setVisible(false);
        T_8Spinner.setVisible(false);
        T_9Spinner.setVisible(false);
        T_10Spinner.setVisible(false);
        T_11Spinner.setVisible(false);
        label_T_1.setVisible(false);
        label_T_2.setVisible(false);
        label_T_3.setVisible(false);
        label_T_4.setVisible(false);
        label_T_5.setVisible(false);
        label_T_6.setVisible(false);
        label_T_7.setVisible(false);
        label_T_8.setVisible(false);
        label_T_9.setVisible(false);
        label_T_10.setVisible(false);
        label_T_11.setVisible(false);
    }

    public void setDetailedTextAreaVisible() {
        qualityMeasuresTextArea.setVisible(true);
        textArea.setPrefWidth(525);
    }

    public void setDetailedTextAreaInvisible() {
        qualityMeasuresTextArea.setVisible(false);
        textArea.setPrefWidth(1050);
    }


    // Setting spinners values
    public void setEqualsWeights() {
        T_1Spinner.getValueFactory().setValue(1.0 / 11.0);
        T_2Spinner.getValueFactory().setValue(1.0 / 11.0);
        T_3Spinner.getValueFactory().setValue(1.0 / 11.0);
        T_4Spinner.getValueFactory().setValue(1.0 / 11.0);
        T_5Spinner.getValueFactory().setValue(1.0 / 11.0);
        T_6Spinner.getValueFactory().setValue(1.0 / 11.0);
        T_7Spinner.getValueFactory().setValue(1.0 / 11.0);
        T_8Spinner.getValueFactory().setValue(1.0 / 11.0);
        T_9Spinner.getValueFactory().setValue(1.0 / 11.0);
        T_10Spinner.getValueFactory().setValue(1.0 / 11.0);
        T_11Spinner.getValueFactory().setValue(1.0 / 11.0);
    }

    public void setDefaultWeights() {
        T_1Spinner.getValueFactory().setValue(0.3);
        T_2Spinner.getValueFactory().setValue(0.07);
        T_3Spinner.getValueFactory().setValue(0.07);
        T_4Spinner.getValueFactory().setValue(0.07);
        T_5Spinner.getValueFactory().setValue(0.07);
        T_6Spinner.getValueFactory().setValue(0.07);
        T_7Spinner.getValueFactory().setValue(0.07);
        T_8Spinner.getValueFactory().setValue(0.07);
        T_9Spinner.getValueFactory().setValue(0.07);
        T_10Spinner.getValueFactory().setValue(0.07);
        T_11Spinner.getValueFactory().setValue(0.07);
    }


    // Weights functions
    public boolean checkWeightsCorrectness() {
        double sum = T_1Spinner.getValue() + T_2Spinner.getValue() + T_3Spinner.getValue() + T_4Spinner.getValue() + T_5Spinner.getValue() + T_6Spinner.getValue() + T_7Spinner.getValue() + T_8Spinner.getValue() + T_9Spinner.getValue() + T_10Spinner.getValue() + T_11Spinner.getValue();
        System.out.println("Sum of weights = " + sum);
        return isAlmostOne(sum, margin);
    }

    public boolean isAlmostOne(double number, double margin) {
        return Math.abs(1.0 - number) <= margin;
    }

    private void addSpinnerListener(Spinner<Double> spinner) {
        spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            calculateSumOfWeights();
        });
    }

    private void calculateSumOfWeights() {
        double sum = T_1Spinner.getValue() + T_2Spinner.getValue() + T_3Spinner.getValue() + T_4Spinner.getValue() + T_5Spinner.getValue() + T_6Spinner.getValue() + T_7Spinner.getValue() + T_8Spinner.getValue() + T_9Spinner.getValue() + T_10Spinner.getValue() + T_11Spinner.getValue();
        label_SumWeights.setText(String.format("%.2f", sum));
    }


    // Advanced options
    public void advancedOptionsVisible() {
        titleLabel.setText("Generator of single-subject linguistic summaries in form 1");
        templateLabel.setText("Template: Q P are/have S. [T_1]");
        defaultWeightsCheckBox.setVisible(true);
        chooseSubjectLabel.setText("Choose subject");
        chosenSubject.setLayoutY(chosenSubject.getLayoutY() - 10);
        summarizatorConjLabel.setVisible(true);
        summarizatorConjunction.setVisible(true);
        numberSummarizersLabel.setText("Number of summarizers");
        chosenSummarizerLabel_1.setText("Choose summarizer 1");
        chosenSummarizerLabel_2.setText("Choose summarizer 2");
        chosenSummarizerLabel_3.setText("Choose summarizer 3");
        chosenSummarizerLabel_4.setText("Choose summarizer 4");
        generateDetailedCheckBox.setVisible(true);
        saveSummariesButton.setVisible(true);
        saveSummariesCSVButton.setVisible(true);
        saveLabel.setVisible(true);
        generateAllSummariesButton.setText("Generate All Summaries");
        generateBestSummaryButton.setText("Generate Best Summary");
        generateOptimalSummaryButton.setText("Generate Optimal Summary");
        explainLabel.setVisible(false);
        chooseQuantifierLabel.setText("Choose quantifier");
    }

    public void advancedOptionsInvisible() {
        titleLabel.setText("Generator of summaries like as in the template below");
        templateLabel.setText("Template: Q P are/have S. [T_1]\tE.g.: Most of the block groups have low income. [0.3]");
        defaultWeightsCheckBox.setVisible(false);
        chooseSubjectLabel.setText("Choose subject \n(this is P on Template)");
        chosenSubject.setLayoutY(chosenSubject.getLayoutY() + 10);
        summarizatorConjLabel.setVisible(false);
        summarizatorConjunction.setVisible(false);
        numberSummarizersLabel.setText("Number of features");
        chosenSummarizerLabel_1.setText("Choose attribute");
        chosenSummarizerLabel_2.setText("Additionally");
        chosenSummarizerLabel_3.setText("Additionally");
        chosenSummarizerLabel_4.setText("Additionally");
        generateDetailedCheckBox.setVisible(false);
        saveSummariesButton.setVisible(false);
        saveSummariesCSVButton.setVisible(false);
        saveLabel.setVisible(false);
        generateAllSummariesButton.setText("Generate All");
        generateBestSummaryButton.setText("Generate Best");
        generateOptimalSummaryButton.setText("Generate Optimal");
        explainLabel.setVisible(true);
        chooseQuantifierLabel.setText("Choose Absolute/Relative");
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

