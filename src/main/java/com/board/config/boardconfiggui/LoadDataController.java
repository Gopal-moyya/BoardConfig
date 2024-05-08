package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.common.Utils;
import com.board.config.boardconfiggui.controllers.SelectBoardNameController;
import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.outputmodels.BoardResult;
import com.board.config.boardconfiggui.data.outputmodels.genralconfig.GeneralConfig;
import com.board.config.boardconfiggui.data.outputmodels.genralconfig.Option;
import com.board.config.boardconfiggui.data.repo.BoardResultsRepo;
import com.invecas.CodeGenerator;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class LoadDataController  {
    private static final Logger logger = Logger.getLogger(LoadDataController.class.getName());

    @FXML
    private TextField xmlPathField;
    @FXML
    private TextField repoPathField;
    @FXML
    private TextField toolChainPathField;
    @FXML
    private TextField outputPathField;
    @FXML
    private Button submitBtn;

    private final HomeViewController homeViewController;
    private String xmlFolderPath,
            repositoryFolderPath,
            toolChainFolderPath,
            outputLocationFolderPath;

    private BoardResultsRepo boardResultsRepo;


    public LoadDataController(HomeViewController homeViewController, String xmlFolderPath) {
        this.homeViewController = homeViewController;
        this.xmlFolderPath = xmlFolderPath;
    }

    @FXML
    public void initialize() {
        submitBtn.setDisable(true);
        boardResultsRepo = BoardResultsRepo.getInstance();

        if (StringUtils.isNotEmpty(xmlFolderPath)) {
            xmlPathField.setText(xmlFolderPath);
        }
        if (StringUtils.isNotEmpty(repositoryFolderPath)) {
            repoPathField.setText(repositoryFolderPath);
        }

        if (StringUtils.isNotEmpty(toolChainFolderPath)) {
            toolChainPathField.setText(toolChainFolderPath);
        }

        if (StringUtils.isNotEmpty(outputLocationFolderPath)) {
            outputPathField.setText(outputLocationFolderPath);
        }
    }

    @FXML
    protected void onButtonClick(Event event) {
        Node sourceNode = (Node) event.getSource();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(Constants.SELECT_FOLDER);

        String directoryPath = getInitialDirectoryPath(sourceNode.getUserData().toString());
        if (StringUtils.isNotEmpty(directoryPath)) {
            File defaultDirectory = new File(directoryPath);
            directoryChooser.setInitialDirectory(defaultDirectory);
        }

        File selectedFolder = directoryChooser.showDialog(null);
        if (selectedFolder != null) {
            switch (sourceNode.getUserData().toString()){
                case Constants.XML_BUTTON_TYPE:
                    xmlPathField.setText(selectedFolder.getAbsolutePath());
                    this.xmlFolderPath = xmlPathField.getText();
                    break;
                case Constants.REPO_BUTTON_TYPE:
                    repoPathField.setText(selectedFolder.getAbsolutePath());
                    this.repositoryFolderPath = repoPathField.getText();
                    break;
                case Constants.TOOL_CHAIN_BUTTON_TYPE:
                    toolChainPathField.setText(selectedFolder.getAbsolutePath());
                    this.toolChainFolderPath = toolChainPathField.getText();
                    break;
                case Constants.OUTPUT_BUTTON_TYPE:
                    outputPathField.setText(selectedFolder.getAbsolutePath());
                    this.outputLocationFolderPath = outputPathField.getText();
                    break;
                default:
                    break;
            }
        }

        boolean allFields = !xmlPathField.getText().isEmpty() && !repoPathField.getText().isEmpty() && !toolChainPathField.getText().isEmpty() && !outputPathField.getText().isEmpty();
        if (allFields){
            submitBtn.setDisable(false);
        }
    }

    public void onSubmit() {
        Map<String, Object> folderPaths = new LinkedHashMap<>();
        folderPaths.put("xml",xmlPathField.getText());
        folderPaths.put("repository",repoPathField.getText());
        folderPaths.put("toolchain",toolChainPathField.getText().replace("\\","/"));
        String outputPath = outputPathField.getText();
        if (!outputPath.isEmpty()){
            folderPaths.put("output",outputPath);
        }
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        try {
            CodeGenerator generator = new CodeGenerator(folderPaths);
            generator.initiate();
            successAlert.setTitle("Success Message");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Code generated successful!");
            successAlert.getDialogPane().getStyleClass().add("success-dialog");
            successAlert.showAndWait();

        }catch (Exception e){
            successAlert.setTitle("Failure Message");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Something went wrong..!" + e.getMessage());
            successAlert.showAndWait();
        }
    }

    public void onConfigureClick(ActionEvent actionEvent) {

        // checking the directory path is selected or not
        if (StringUtils.isEmpty(xmlFolderPath)) {
            logger.info("The Selected folder path is null or empty" + xmlFolderPath);
            Utils.alertDialog(Alert.AlertType.INFORMATION, "Select directory",null, "Please select the xml directory.");
            return;
        }

        // checking the input configuration files availability from selected directory
        if (!Utils.isInputConfigurationReached(xmlFolderPath)) {
            Utils.alertDialog(Alert.AlertType.ERROR, "No files found", null, "Configuration files are not available in the selected directory.");
            return;
        }

        // checking the parsing status of input configuration files
        if(BooleanUtils.isFalse(Utils.parseInputXmlFiles(xmlFolderPath))) {
            Utils.alertDialog(Alert.AlertType.ERROR, "Parsing failed", null,"An issue occurred while parsing the input XML files.");
            return;
        }

        //Fetching the BoardResult object if the Board configuration file is available.
        BoardResult boardResult = Utils.getBoardConfigResult(xmlFolderPath);
        if (Objects.isNull(boardResult)) {
            //Creating new instance
            boardResultsRepo.createBoardResult();
        } else {
            //Set the board results to repo
            boardResultsRepo.setBoardResult(boardResult);
        }

        // Opening the Board name dialog
        openBoardNameDialog();

    }

    private void openBoardNameDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("select-board-name.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            SelectBoardNameController controller = loader.getController();
            controller.setDialogStage(stage);

            GeneralConfig generalConfig = boardResultsRepo.getBoardResult().getGeneralConfig();
            if (ObjectUtils.isNotEmpty(generalConfig)) {
                String boardName = generalConfig.getOption().getValue();
                controller.setTxtBoardName(boardName);
            }

            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle(Constants.GENERAL_CONFIGURATION);
            stage.showAndWait();

            if (controller.isContinueSelected()) {
                String boardName = controller.getBoardName();

                //set the board name information
                generalConfig = new GeneralConfig(new Option(boardName));
                boardResultsRepo.getBoardResult().setGeneralConfig(generalConfig);

                homeViewController.onConfigureClick(xmlFolderPath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the initial directory path based on the specified source node type.
     *
     * @param sourceNodeType The type of source node
     *                       <p>
     *                       the node types are:
     *                       <ul>
     *                           <li>xmlBtn</li>
     *                           <li>repoBtn</li>
     *                           <li>toolChainBtn</li>
     *                           <li>outputBtn</li>
     *                       </ul>
     * @return The initial directory path corresponding to the source node type, or null if not found.
     */
    private String getInitialDirectoryPath(String sourceNodeType) {
        if (StringUtils.isEmpty(sourceNodeType)) {
            return null;
        }

        return switch (sourceNodeType) {
            case Constants.XML_BUTTON_TYPE -> xmlFolderPath;
            case Constants.REPO_BUTTON_TYPE -> repositoryFolderPath;
            case Constants.TOOL_CHAIN_BUTTON_TYPE -> toolChainFolderPath;
            case Constants.OUTPUT_BUTTON_TYPE -> outputLocationFolderPath;
            default -> null;
        };
    }

}
