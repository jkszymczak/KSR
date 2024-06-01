package GUI;

import Builders.ContainerBuilder;
import Builders.FuzzyQuantifierBuilder;
import Builders.LinguisticSummaryBuilder;
import Builders.SummarizerQualifierBuilder;
import Database.BlockGroup;
import Database.CSV;
import FuzzyCalculations.*;
import LinguisticSummarization.LinguisticSummary;
import LinguisticSummarization.LinguisticSummaryGenerator;
import LinguisticSummarization.LinguisticSummaryType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SSForm1Controller {
    private LinguisticSummaryGenerator genForm1;
    private List<LinguisticSummary> summaries;
    private List<Double> weights;
    private Double margin = 0.01;
    private List<String> linguisticVariables;
    private List<BlockGroup> data;
    private Container bedroomToRoomRatio;
    private Container meanHouseholdType;
    private Container medianHouseAge;
    private Container medianIncome;
    private Container population;
    private Container totalRoomsCount;
    private Container medianHouseValue;
    private Container distanceLA;
    private Container distanceSF;
    @FXML
    private Spinner<Integer> numberSummarizerSpinner;
    @FXML
    private Button numberSummarizerConfirm;
    @FXML
    private ComboBox<String> chosenQuantifier;
    @FXML
    private ComboBox<String> summarizatorConjunction;
    @FXML
    private ComboBox<String> chosenSummarizer_1;
    @FXML
    private ComboBox<String> chosenSummarizer_2;
    @FXML
    private ComboBox<String> chosenSummarizer_3;
    @FXML
    private ComboBox<String> chosenSummarizer_4;
    @FXML
    private Label chosenSummarizerLabel_2;
    @FXML
    private Label chosenSummarizerLabel_3;
    @FXML
    private Label chosenSummarizerLabel_4;
    @FXML
    private ComboBox<String> cb_chosenSummarizerLabel_1;
    @FXML
    private ComboBox<String> cb_chosenSummarizerLabel_2;
    @FXML
    private ComboBox<String> cb_chosenSummarizerLabel_3;
    @FXML
    private ComboBox<String> cb_chosenSummarizerLabel_4;
    @FXML
    private TextArea textArea;
    @FXML
    private Label status;
    @FXML
    private Button saveSummariesButton;
    @FXML
    private Button saveSummariesCSVButton;
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
    @FXML
    private CheckBox defaultWeightsCheckBox;
    @FXML
    private Label descriptionOfWeights;
    @FXML
    private Button equalsWeightsButton;
    @FXML
    private Button resetWeightsButton;


    @FXML
    public void initialize() {
        numberSummarizerSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1));
        numberSummarizerSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            numberSummarizerConfirmClicked();
        });

        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory1 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory1.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory2 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory1.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory3 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory1.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory4 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory1.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory5 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory1.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory6 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory1.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory7 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory1.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory8 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory1.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory9 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory1.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory10 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory1.setAmountToStepBy(0.01);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleFactory11 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0., 1., 0.);
        doubleFactory1.setAmountToStepBy(0.01);

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

        String path = "dataBasePrep/prepared.csv";
        data = CSV.readCSV(path);
        linguisticVariables = new ArrayList<>(Arrays.asList("Bedroom to room ratio", "Median house age", "Mean household type",
                "Median income", "Population", "Total rooms count", "Median house value", "Distance LA", "Distance SF"));

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

        chosenQuantifier.getItems().addAll("Absolute", "Relative");
        summarizatorConjunction.getItems().addAll("are", "have", "are in");

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

        setWeightsPanelInvisible();
        setManySummarizatorInvisible();
    }

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

    public void generateAfter() {
        for (LinguisticSummary summary : summaries) {
            System.out.println(summary);
            String text = textArea.getText();
            text += summary.toString() + "\n";
            textArea.setText(text);
            genForm1.calculateQualityMeasures(weights, summary);
        }
        saveSummariesButton.setDisable(false);
        saveSummariesCSVButton.setDisable(false);
    }

    public void generatePrepare() {
        textArea.clear();
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

        genForm1 = LinguisticSummaryBuilder.builder()
                .withLinguisticSummaryType(LinguisticSummaryType.First)
                .onSet(data).withSummarizator(summarizator)
                .withQuantifier(quantifier)
                .withSubject("Block Groups")
                .withSummarizatorConjunction(summarizatorConj)
                .build();
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
        return switch (quantifier) {
            case "Absolute" -> createAbsolute(data);
            case "Relative" -> createRelative();
            default -> null;
        };
    }

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

    @FXML
    public void onCheckBoxChangeState() {
        if (!defaultWeightsCheckBox.isSelected()) {
            setWeightsPanelInvisible();
        } else {
            setWeightsPanelVisible();
        }
    }

    public void setWeightsPanelVisible() {
        descriptionOfWeights.setVisible(true);
        resetWeightsButton.setVisible(true);
        equalsWeightsButton.setVisible(true);
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

    @FXML
    public void setEqualsWeightsButtonClick() {
        setEqualsWeights();
    }

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

    @FXML
    public void setDefaultWeightsButtonClick() {
        setDefaultWeights();
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

    public boolean checkWeightsCorrectness() {
        double sum = T_1Spinner.getValue() + T_2Spinner.getValue() + T_3Spinner.getValue() + T_4Spinner.getValue() + T_5Spinner.getValue() + T_6Spinner.getValue() + T_7Spinner.getValue() + T_8Spinner.getValue() + T_9Spinner.getValue() + T_10Spinner.getValue() + T_11Spinner.getValue();
        System.out.println("Sum of weights = " + sum);
        return isAlmostOne(sum, margin);
    }

    public boolean isAlmostOne(double number, double margin) {
        return Math.abs(1.0 - number) <= margin;
    }

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

    @FXML
    public void numberSummarizerConfirmClicked() {
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

    static FuzzyQuantifier createAbsolute(List<BlockGroup> data) {
        return FuzzyQuantifierBuilder.builder()
                .onRange(0, data.size())
                .withType(QuantifierType.absolute)
                .createLabel().withLabel("few").createMembershipFunction().createTriangle(0, 0, 350).build().build()
                .createLabel().withLabel("hundreds").createMembershipFunction().createTrapezoidal(100, 300, 800, 1000).build().build()
                .createLabel().withLabel("around 1000").createMembershipFunction().createGaussian(1000, 300).build().build()
                .createLabel().withLabel("close to 2000-3000").createMembershipFunction().createTrapezoidal(1000, 2000, 3000, 4500).build().build()
                .createLabel().withLabel("approximately 5000").createMembershipFunction().createGaussian(5000, 800).build().build()
                .createLabel().withLabel("nearly 6000").createMembershipFunction().createGaussian(6000, 1000).build().build()
                .createLabel().withLabel("above 7500").createMembershipFunction().createTrapezoidal(7000, 8000, 11000, 11000).build().build()
                .end();
    }

    static FuzzyQuantifier createRelative() {
        return FuzzyQuantifierBuilder.builder()
                .onRange(0, 1)
                .withType(QuantifierType.relative)
                .createLabel().withLabel("almost_none").createMembershipFunction().createTrapezoidal(0, 0, 0.05, 0.2).build().build()
                .createLabel().withLabel("around 20 %").createMembershipFunction().createGaussian(0.2, 0.05).build().build()
                .createLabel().withLabel("nearly 1/3").createMembershipFunction().createTrapezoidal(0.25, 0.3, 0.36, 0.45).build().build()
                .createLabel().withLabel("approximately half").createMembershipFunction().createTrapezoidal(0.35, 0.45, 0.55, 0.65).build().build()
                .createLabel().withLabel("most").createMembershipFunction().createTrapezoidal(0.55, 0.7, 0.8, 1).build().build()
                .createLabel().withLabel("almost all").createMembershipFunction().createTrapezoidal(0.8, 0.95, 1, 1).build().build()
                .end();
    }

    // Bedroom to Room ratio
    static SummarizerQualifier createInsufficientShareOfBedrooms(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(1.0, 10.0)
                .createFuzzySet().onColumn(Columns.ratio_rooms_bedrooms)
                .withCandidates(data).withLabel("insufficient share of bedrooms")
                .createMembershipFunction().createTrapezoidal(0, 1, 1.5, 2.5).build()
                .build().end();

    }

    static SummarizerQualifier createLowBedroomProportion(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(1.0, 10.0)
                .createFuzzySet().onColumn(Columns.ratio_rooms_bedrooms)
                .withCandidates(data).withLabel("low bedroom proportion")
                .createMembershipFunction().createTriangle(1.5, 3, 4).build()
                .build().end();
    }

    static SummarizerQualifier createBalancedRoomDistribution(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(1.0, 10.0)
                .createFuzzySet().onColumn(Columns.ratio_rooms_bedrooms)
                .withCandidates(data).withLabel("balanced room distribution")
                .createMembershipFunction().createTrapezoidal(3, 4, 5, 6).build()
                .build().end();
    }

    static SummarizerQualifier createHighBedroomProportion(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(1.0, 10.0)
                .createFuzzySet().onColumn(Columns.ratio_rooms_bedrooms)
                .withCandidates(data).withLabel("high bedroom proportion")
                .createMembershipFunction().createGaussian(7, 1).build()
                .build().end();
    }

    static SummarizerQualifier createExcessiveShareOfBedrooms(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(1.0, 10.0)
                .createFuzzySet().onColumn(Columns.ratio_rooms_bedrooms)
                .withCandidates(data).withLabel("excessive share of bedrooms")
                .createMembershipFunction().createTrapezoidal(7, 9, 10, 10).build()
                .build().end();
    }

    // median household type
    static SummarizerQualifier createStudioApartmentsDominant(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(1.0, 20.0)
                .createFuzzySet().onColumn(Columns.ratio_population_households)
                .withCandidates(data)
                .withLabel("studio apartments dominant")
                .createMembershipFunction().createTrapezoidal(0, 0, 1, 2.5).build()
                .build().end();
    }

    static SummarizerQualifier createPredominantlySingleSmallFamily(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(1.0, 20.0)
                .createFuzzySet().onColumn(Columns.ratio_population_households)
                .withCandidates(data)
                .withLabel("predominantly single small family")
                .createMembershipFunction().createTrapezoidal(1.5, 2, 3, 4).build()
                .build().end();
    }

    static SummarizerQualifier createPredominantlySingleBigFamily(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(1.0, 20.0)
                .createFuzzySet().onColumn(Columns.ratio_population_households)
                .withCandidates(data)
                .withLabel("predominantly single big family")
                .createMembershipFunction().createTrapezoidal(3, 4, 6, 8).build()
                .build().end();
    }

    static SummarizerQualifier createMultiFamilyPrevalent(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(1.0, 20.0)
                .createFuzzySet().onColumn(Columns.ratio_population_households)
                .withCandidates(data)
                .withLabel("multi family prevalent")
                .createMembershipFunction().createTrapezoidal(5, 10, 21, 21).build()
                .build().end();
    }


    // Median house age
    static SummarizerQualifier createNewHouses(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0, 60)
                .createFuzzySet().onColumn(Columns.housing_median_age)
                .withCandidates(data).withLabel("new")
                .createMembershipFunction().createGaussian(7.5, 2).build()
                .build().end();
    }

    static SummarizerQualifier createMiddleAgedHouses(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0, 60)
                .createFuzzySet().onColumn(Columns.housing_median_age)
                .withCandidates(data).withLabel("middle aged")
                .createMembershipFunction().createTrapezoidal(8, 13, 30, 45).build()
                .build().end();
    }

    static SummarizerQualifier createRecentlyBuiltHouses(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0, 60)
                .createFuzzySet().onColumn(Columns.housing_median_age)
                .withCandidates(data).withLabel("recently built")
                .createMembershipFunction().createTrapezoidal(0, 0, 2, 7).build()
                .build().end();
    }

    static SummarizerQualifier createAgedHouses(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0, 60)
                .createFuzzySet().onColumn(Columns.housing_median_age)
                .withCandidates(data).withLabel("aged")
                .createMembershipFunction().createTrapezoidal(30, 45, 60, 60).build()
                .build().end();
    }

    // Median income methods
    static SummarizerQualifier createPoorArea(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0, 20)
                .createFuzzySet().onColumn(Columns.median_income)
                .withCandidates(data).withLabel("poor area")
                .createMembershipFunction().createTrapezoidal(0.49, 0.49, 1.5, 3).build()
                .build().end();
    }

    static SummarizerQualifier createLowIncomeArea(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0, 20)
                .createFuzzySet().onColumn(Columns.median_income)
                .withCandidates(data).withLabel("low income area")
                .createMembershipFunction().createTriangle(1.5, 3, 5.5).build()
                .build().end();
    }

    static SummarizerQualifier createBelowAverageIncome(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0, 20)
                .createFuzzySet().onColumn(Columns.median_income)
                .withCandidates(data).withLabel("below average income")
                .createMembershipFunction().createTrapezoidal(3.5, 5.5, 6.5, 8).build()
                .build().end();
    }

    static SummarizerQualifier createAboveAverageIncome(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0, 20)
                .createFuzzySet().onColumn(Columns.median_income)
                .withCandidates(data).withLabel("above average income")
                .createMembershipFunction().createTrapezoidal(6.5, 8.5, 9.5, 12.5).build()
                .build().end();
    }

    static SummarizerQualifier createWealthyArea(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0, 20)
                .createFuzzySet().onColumn(Columns.median_income)
                .withCandidates(data).withLabel("wealthy area")
                .createMembershipFunction().createTrapezoidal(0, 10, 13, 20).build()
                .build().end();
    }

    // population
    static SummarizerQualifier createPracticallyUnpopulated(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 29000.0)
                .createFuzzySet().onColumn(Columns.population)
                .withCandidates(data)
                .withLabel("practically unpopulated")
                .createMembershipFunction().createTrapezoidal(3, 3, 500, 2000).build()
                .build().end();
    }

    static SummarizerQualifier createLowPopulation(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 29000.0)
                .createFuzzySet().onColumn(Columns.population)
                .withCandidates(data)
                .withLabel("low population")
                .createMembershipFunction().createTrapezoidal(3, 3000, 6000, 8400).build()
                .build().end();
    }

    static SummarizerQualifier createModeratelyPopulated(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 29000.0)
                .createFuzzySet().onColumn(Columns.population)
                .withCandidates(data)
                .withLabel("moderately populated")
                .createMembershipFunction().createGaussian(10000, 2000).build()
                .build().end();
    }

    static SummarizerQualifier createHighlyPopulated(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 29000.0)
                .createFuzzySet().onColumn(Columns.population)
                .withCandidates(data)
                .withLabel("highly populated")
                .createMembershipFunction().createGaussian(14500, 3000).build()
                .build().end();
    }

    static SummarizerQualifier createHugePopulation(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 29000.0)
                .createFuzzySet().onColumn(Columns.population)
                .withCandidates(data)
                .withLabel("huge population")
                .createMembershipFunction().createTrapezoidal(15000, 18000, 23000, 26000).build()
                .build().end();
    }

    static SummarizerQualifier createManToMan(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 29000.0)
                .createFuzzySet().onColumn(Columns.population)
                .withCandidates(data)
                .withLabel("man to man")
                .createMembershipFunction().createTrapezoidal(22000, 27000, 30000, 30000).build()
                .build().end();
    }

    // total rooms count
    static SummarizerQualifier createFewRooms(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 33000.0)
                .createFuzzySet().onColumn(Columns.total_rooms)
                .withCandidates(data).withLabel("few rooms")
                .createMembershipFunction().createTrapezoidal(2, 2, 1000, 3000).build()
                .build().end();
    }

    static SummarizerQualifier createSparseRoomDistribution(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 33000.0)
                .createFuzzySet().onColumn(Columns.total_rooms)
                .withCandidates(data).withLabel("sparse room distribution")
                .createMembershipFunction().createTriangle(300, 4000, 7700).build()
                .build().end();
    }

    static SummarizerQualifier createModerateRoomsCount(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 33000.0)
                .createFuzzySet().onColumn(Columns.total_rooms)
                .withCandidates(data).withLabel("moderate rooms count")
                .createMembershipFunction().createTrapezoidal(3000, 7000, 11000, 15000).build()
                .build().end();
    }

    static SummarizerQualifier createManyRooms(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 33000.0)
                .createFuzzySet().onColumn(Columns.total_rooms)
                .withCandidates(data).withLabel("many rooms")
                .createMembershipFunction().createTrapezoidal(11000, 15000, 21000, 27000).build()
                .build().end();
    }

    static SummarizerQualifier createExtremelyHighRoomsCount(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(3.0, 33000.0)
                .createFuzzySet().onColumn(Columns.total_rooms)
                .withCandidates(data).withLabel("extremely high rooms count")
                .createMembershipFunction().createTrapezoidal(20000, 28000, 34000, 34000).build()
                .build().end();
    }

    // median house value
    static SummarizerQualifier createPracticallyWorthless(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(14999.0, 500001.0)
                .createFuzzySet().onColumn(Columns.median_house_value)
                .withCandidates(data)
                .withLabel("practically worthless")
                .createMembershipFunction().createTrapezoidal(14999, 14999, 40000, 100000).build()
                .build().end();
    }

    static SummarizerQualifier createLowValueHomes(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(14999.0, 500001.0)
                .createFuzzySet().onColumn(Columns.median_house_value)
                .withCandidates(data)
                .withLabel("low value homes")
                .createMembershipFunction().createTrapezoidal(40000, 110000, 150000, 180000).build()
                .build().end();
    }

    static SummarizerQualifier createModeratelyPricedHomes(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(14999.0, 500001.0)
                .createFuzzySet().onColumn(Columns.median_house_value)
                .withCandidates(data)
                .withLabel("moderately priced homes")
                .createMembershipFunction().createGaussian(200000, 25000).build()
                .build().end();
    }

    static SummarizerQualifier createAboveAverageHomeValue(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(14999.0, 500001.0)
                .createFuzzySet().onColumn(Columns.median_house_value)
                .withCandidates(data)
                .withLabel("above average home value")
                .createMembershipFunction().createTrapezoidal(210000, 250000, 270000, 320000).build()
                .build().end();
    }

    static SummarizerQualifier createHighValueResidentialAreas(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(14999.0, 500001.0)
                .createFuzzySet().onColumn(Columns.median_house_value)
                .withCandidates(data)
                .withLabel("high value residential areas")
                .createMembershipFunction().createTrapezoidal(280000, 320000, 400000, 430000).build()
                .build().end();
    }

    static SummarizerQualifier createLuxuryEstates(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(14999.0, 500001.0)
                .createFuzzySet().onColumn(Columns.median_house_value)
                .withCandidates(data)
                .withLabel("luxury estates")
                .createMembershipFunction().createTrapezoidal(390000, 450000, 500001, 500001).build()
                .build().end();
    }

    // distance LA
    static SummarizerQualifier createWithinCityBoundsLA(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0.42, 1020.0)
                .createFuzzySet().onColumn(Columns.distance_LA)
                .withCandidates(data)
                .withLabel("within city bounds(LA)")
                .createMembershipFunction().createTrapezoidal(0.42, 0.42, 30, 60).build()
                .build().end();
    }

    static SummarizerQualifier createSuburbanProximityLA(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0.42, 1020.0)
                .createFuzzySet().onColumn(Columns.distance_LA)
                .withCandidates(data)
                .withLabel("suburban proximity(LA)")
                .createMembershipFunction().createTrapezoidal(40, 50, 70, 90).build()
                .build().end();
    }

    static SummarizerQualifier createDistantSuburbsLA(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0.42, 1020.0)
                .createFuzzySet().onColumn(Columns.distance_LA)
                .withCandidates(data)
                .withLabel("distant suburbs(LA)")
                .createMembershipFunction().createTriangle(70, 100, 130).build()
                .build().end();
    }

    static SummarizerQualifier createNearbyCityLA(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0.42, 1020.0)
                .createFuzzySet().onColumn(Columns.distance_LA)
                .withCandidates(data)
                .withLabel("nearby city(LA)")
                .createMembershipFunction().createTrapezoidal(110, 130, 200, 250).build()
                .build().end();
    }

    static SummarizerQualifier createRuralFringeLA(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0.42, 1020.0)
                .createFuzzySet().onColumn(Columns.distance_LA)
                .withCandidates(data)
                .withLabel("rural fringe(LA)")
                .createMembershipFunction().createTrapezoidal(200, 250, 380, 600).build()
                .build().end();
    }

    static SummarizerQualifier createFarFromCityLA(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(0.42, 1020.0)
                .createFuzzySet().onColumn(Columns.distance_LA)
                .withCandidates(data)
                .withLabel("far from city(LA)")
                .createMembershipFunction().createTrapezoidal(350, 600, 1020, 1020).build()
                .build().end();
    }

    // distance SF
    static SummarizerQualifier createWithinCityBoundsSF(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(8.0, 1000.0)
                .createFuzzySet().onColumn(Columns.distance_SF)
                .withCandidates(data)
                .withLabel("within city bounds(SF)")
                .createMembershipFunction().createTrapezoidal(8, 10, 20, 30).build()
                .build().end();
    }

    static SummarizerQualifier createSuburbanProximitySF(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(8.0, 1000.0)
                .createFuzzySet().onColumn(Columns.distance_SF)
                .withCandidates(data)
                .withLabel("suburban proximity(SF)")
                .createMembershipFunction().createTrapezoidal(20, 35, 50, 70).build()
                .build().end();
    }

    static SummarizerQualifier createDistantSuburbsSF(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(8.0, 1000.0)
                .createFuzzySet().onColumn(Columns.distance_SF)
                .withCandidates(data)
                .withLabel("distant suburbs(SF)")
                .createMembershipFunction().createTriangle(60, 80, 100).build()
                .build().end();
    }

    static SummarizerQualifier createNearbyCitySF(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(8.0, 1000.0)
                .createFuzzySet().onColumn(Columns.distance_SF)
                .withCandidates(data)
                .withLabel("nearby city(SF)")
                .createMembershipFunction().createTrapezoidal(80, 100, 150, 200).build()
                .build().end();
    }

    static SummarizerQualifier createRuralFringeSF(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(8.0, 1000.0)
                .createFuzzySet().onColumn(Columns.distance_SF)
                .withCandidates(data)
                .withLabel("rural fringe(SF)")
                .createMembershipFunction().createTrapezoidal(150, 200, 350, 450).build()
                .build().end();
    }

    static SummarizerQualifier createFarFromCitySF(List<BlockGroup> data) {
        return SummarizerQualifierBuilder.builder()
                .onRange(8.0, 1000.0)
                .createFuzzySet().onColumn(Columns.distance_SF)
                .withCandidates(data)
                .withLabel("far from city(SF)")
                .createMembershipFunction().createTrapezoidal(300, 500, 1000, 1000).build()
                .build().end();
    }


    static SummarizerQualifier fullDataSet(List<BlockGroup> data) {
        FuzzySet fuzzySet = new FuzzySet(data, new SetMembership(), "all", Columns.population);
        return new SummarizerQualifier(fuzzySet);
    }
}

