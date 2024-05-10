package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.common.Utils;
import com.board.config.boardconfiggui.controllers.SelectBoardNameController;
import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.outputmodels.BoardResult;
import com.board.config.boardconfiggui.data.outputmodels.genralconfig.GeneralConfig;
import com.board.config.boardconfiggui.data.outputmodels.genralconfig.Option;
import com.board.config.boardconfiggui.data.repo.BoardResultsRepo;
import com.board.config.boardconfiggui.ui.models.ConfigPathsModel;
import com.invecas.CodeGenerator;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
    private final ConfigPathsModel configPathsModel;

    private BoardResultsRepo boardResultsRepo;


    public LoadDataController(HomeViewController homeViewController, ConfigPathsModel configPathsModel)
    {
        this.homeViewController = homeViewController;
        this.configPathsModel = configPathsModel;

    }

    @FXML
    public void initialize() {
        boolean allFields = StringUtils.isNotEmpty(configPathsModel.getXmlPath()) &&
                StringUtils.isNotEmpty(configPathsModel.getRepoPath()) &&
                StringUtils.isNotEmpty(configPathsModel.getToolchainPath())
                && StringUtils.isNotEmpty(configPathsModel.getOutputPath());

        submitBtn.setDisable(!allFields);

        boardResultsRepo = BoardResultsRepo.getInstance();

        if (StringUtils.isNotEmpty(configPathsModel.getXmlPath())) {
            xmlPathField.setText(configPathsModel.getXmlPath());
        }
        if (StringUtils.isNotEmpty(configPathsModel.getRepoPath())) {
            repoPathField.setText(configPathsModel.getRepoPath());
        }

        if (StringUtils.isNotEmpty(configPathsModel.getToolchainPath())) {
            toolChainPathField.setText(configPathsModel.getToolchainPath());
        }

        if (StringUtils.isNotEmpty(configPathsModel.getOutputPath())) {
            outputPathField.setText(configPathsModel.getOutputPath());
        }
    }

    @FXML
    protected void handleTextFieldAction(Event event)
    {
        Node sourceNode = (Node) event.getSource();
        switch (sourceNode.getId().toString()){
            case "xmlPathField":
                configPathsModel.setXmlPath(xmlPathField.getText());
                break;
            case "repoPathField":
                configPathsModel.setRepoPath(repoPathField.getText());
                break;
            case "toolChainPathField":
                configPathsModel.setToolchainPath(toolChainPathField.getText());
                break;
            case "outputPathField":
                configPathsModel.setOutputPath(outputPathField.getText());
                break;
            default:
                break;
        }
        boolean allFields = StringUtils.isNotEmpty(configPathsModel.getXmlPath()) &&
                StringUtils.isNotEmpty(configPathsModel.getRepoPath()) &&
                StringUtils.isNotEmpty(configPathsModel.getToolchainPath())
                && StringUtils.isNotEmpty(configPathsModel.getOutputPath());

        submitBtn.setDisable(!allFields);
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
                    configPathsModel.setXmlPath(xmlPathField.getText());
                    break;
                case Constants.REPO_BUTTON_TYPE:
                    repoPathField.setText(selectedFolder.getAbsolutePath());
                    configPathsModel.setRepoPath(repoPathField.getText());
                    break;
                case Constants.TOOL_CHAIN_BUTTON_TYPE:
                    toolChainPathField.setText(selectedFolder.getAbsolutePath());
                    configPathsModel.setToolchainPath(toolChainPathField.getText());
                    break;
                case Constants.OUTPUT_BUTTON_TYPE:
                    outputPathField.setText(selectedFolder.getAbsolutePath());
                    configPathsModel.setOutputPath(outputPathField.getText());
                    break;
                default:
                    break;
            }
        }

        boolean allFields = StringUtils.isNotEmpty(configPathsModel.getXmlPath()) &&
                StringUtils.isNotEmpty(configPathsModel.getRepoPath()) &&
                StringUtils.isNotEmpty(configPathsModel.getToolchainPath())
                && StringUtils.isNotEmpty(configPathsModel.getOutputPath());

        submitBtn.setDisable(!allFields);
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

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Task<Void> codeGenerationTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                CodeGenerator generator = new CodeGenerator(folderPaths,homeViewController);
                generator.initiate();
                return null;
            }

            @Override
            public void succeeded() {
                super.succeeded();
                Platform.runLater(() -> {
                    homeViewController.stopAnimation();
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Code generated successfully!");
                    alert.showAndWait();
                });
            }

            @Override
            public void failed() {
                super.failed();
                Platform.runLater(() -> {
                    homeViewController.stopAnimation();
                    alert.setTitle("Failure Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Something went wrong..! " );
                    alert.showAndWait();
                });
            }
        };

        homeViewController.showAnimation();
        new Thread(codeGenerationTask).start();

    }

    public void onConfigureClick(ActionEvent actionEvent) {

        // checking the directory path is selected or not
        if (StringUtils.isEmpty(configPathsModel.getXmlPath())) {
            logger.info("The Selected folder path is null or empty" + configPathsModel.getXmlPath());
            Utils.alertDialog(Alert.AlertType.INFORMATION, "Select directory",null, "Please select the xml directory.");
            return;
        }

        // checking the input configuration files availability from selected directory
        if (!Utils.isInputConfigurationReached(configPathsModel.getXmlPath())) {
            Utils.alertDialog(Alert.AlertType.ERROR, "No files found", null, "Configuration files are not available in the selected directory.");
            return;
        }

        // checking the parsing status of input configuration files
        if(BooleanUtils.isFalse(Utils.parseInputXmlFiles(configPathsModel.getXmlPath()))) {
            Utils.alertDialog(Alert.AlertType.ERROR, "Parsing failed", null,"An issue occurred while parsing the input XML files.");
            return;
        }

        //Fetching the BoardResult object if the Board configuration file is available.
        BoardResult boardResult = Utils.getBoardConfigResult(configPathsModel.getXmlPath());
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
                String boardName = generalConfig.getOption(Constants.BOARD).getValue();
                Option coreOption = generalConfig.getOption(Constants.CORE);
                controller.setTxtBoardName(boardName);
                controller.setCoreName(Objects.isNull(coreOption) ? null : coreOption.getValue());
            }

            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle(Constants.GENERAL_CONFIGURATION);
            stage.showAndWait();

            if (controller.isContinueSelected()) {
                String boardName = controller.getBoardName();

                //set the board name information
                generalConfig = new GeneralConfig();
                generalConfig.addConfig(new Option(Constants.BOARD, boardName));
                generalConfig.addConfig(new Option(Constants.CHIPLET, controller.getChipletName()));
                generalConfig.addConfig(new Option(Constants.CORE, controller.getCoreName()));
                boardResultsRepo.getBoardResult().setGeneralConfig(generalConfig);

                homeViewController.onConfigureClick(configPathsModel.getXmlPath());
                homeViewController.savePathData(configPathsModel);
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
            case Constants.XML_BUTTON_TYPE -> configPathsModel.getXmlPath();
            case Constants.REPO_BUTTON_TYPE -> configPathsModel.getRepoPath();
            case Constants.TOOL_CHAIN_BUTTON_TYPE -> configPathsModel.getToolchainPath();
            case Constants.OUTPUT_BUTTON_TYPE -> configPathsModel.getOutputPath();
            default -> null;
        };
    }

}
