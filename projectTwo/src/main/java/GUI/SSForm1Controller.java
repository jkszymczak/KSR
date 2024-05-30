package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SSForm1Controller {
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
    public void initialize() {
        numberSummarizerSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1));
    }

    @FXML
    public void numberSummarizerConfirmClicked() {
        System.out.println("Number Summarizer Confirm Clicked");
        Integer spinnerValue = numberSummarizerSpinner.getValue();
        switch (spinnerValue) {
            case 1:
                System.out.println("1");
                chosenSummarizer_2.setVisible(false);
                chosenSummarizer_3.setVisible(false);
                chosenSummarizer_4.setVisible(false);
                chosenSummarizerLabel_2.setVisible(false);
                chosenSummarizerLabel_3.setVisible(false);
                chosenSummarizerLabel_4.setVisible(false);
                break;
            case 2:
                System.out.println("2");
                chosenSummarizer_2.setVisible(true);
                chosenSummarizer_3.setVisible(false);
                chosenSummarizer_4.setVisible(false);
                chosenSummarizerLabel_2.setVisible(true);
                chosenSummarizerLabel_3.setVisible(false);
                chosenSummarizerLabel_4.setVisible(false);
                break;
            case 3:
                System.out.println("3");
                chosenSummarizer_2.setVisible(true);
                chosenSummarizer_3.setVisible(true);
                chosenSummarizer_4.setVisible(false);
                chosenSummarizerLabel_2.setVisible(true);
                chosenSummarizerLabel_3.setVisible(true);
                chosenSummarizerLabel_4.setVisible(false);
                break;
            case 4:
                System.out.println("4");
                chosenSummarizer_2.setVisible(true);
                chosenSummarizer_3.setVisible(true);
                chosenSummarizer_4.setVisible(true);
                chosenSummarizerLabel_2.setVisible(true);
                chosenSummarizerLabel_3.setVisible(true);
                chosenSummarizerLabel_4.setVisible(true);
                break;
            default:
                System.out.println("Error of Spinner Value");
                break;
        }
    }
}

