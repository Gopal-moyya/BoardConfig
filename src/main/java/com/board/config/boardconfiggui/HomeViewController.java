package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.common.Utils;
import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.inputmodels.ipconfig.IpConfig;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.PinConfig;
import com.board.config.boardconfiggui.data.repo.BoardResultsRepo;
import com.board.config.boardconfiggui.data.repo.InputConfigRepo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import org.apache.commons.lang3.ObjectUtils;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class HomeViewController {

    private static final Logger logger = Logger.getLogger(HomeViewController.class.getName());

    private String xmlFolderPath = null;

    @FXML
    public Pane contentArea;


    @FXML
    public void initialize() {
        loadDataView();
    }


    public void onConfigureClick(String xmlFolderPath , String boardName) {

        this.xmlFolderPath = xmlFolderPath;

        if (parseInputXmlFiles(xmlFolderPath)) {
            Parent fxml = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("board-config.fxml"));
            BoardConfigController boardConfigController = new BoardConfigController(xmlFolderPath, boardName, this);
            loader.setController(boardConfigController);
            try {
                fxml = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } else {
            Utils.alertDialog(Alert.AlertType.ERROR, "No Files found", null,"Configuration files are not available in the selected Directory.");
        }
    }

    public void onOutputGenerateClick() {
        loadDataView();
    }

    private void loadDataView() {
        Parent fxml = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("load-data-view.fxml"));
        LoadDataController loadDataController = new LoadDataController(this, xmlFolderPath);
        loader.setController(loadDataController);
        try {
            fxml = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    /**
     * Initializes the input configuration repository with data from XML files.
     * Reads IP configuration from the hardware configuration file and PIN configuration
     * from the PIN muxing file.
     *
     * This method initializes the input configuration repository with data obtained
     * from XML files using JAXB (Java Architecture for XML Binding).
     *
     * @return false if an error occurs during unmarshalling of XML files, otherwise true.
     */
    private static boolean parseInputXmlFiles(String xmlFolderPath) {
        try {
            InputConfigRepo inputConfigRepo = InputConfigRepo.getInstance();
            JAXBContext ipConfigContext = JAXBContext.newInstance(IpConfig.class);
            Unmarshaller ipConfigUnmarshaller = ipConfigContext.createUnmarshaller();
            IpConfig ipConfig = (IpConfig) ipConfigUnmarshaller.unmarshal(new File(xmlFolderPath, Constants.HARDWARE_CONFIG_FILE_NAME));
            inputConfigRepo.setIpConfig(ipConfig);

            JAXBContext pinConfigContext = JAXBContext.newInstance(PinConfig.class);
            Unmarshaller pinConfigUnmarshaller = pinConfigContext.createUnmarshaller();
            PinConfig pinConfig = (PinConfig) pinConfigUnmarshaller.unmarshal(new File(xmlFolderPath, Constants.PIN_CONFIG_FILE_NAME));
            inputConfigRepo.setPinConfig(pinConfig);
        } catch (JAXBException e) {
            logger.warning("Input xml files parsing failed" + e);
            return false;
        }

        BoardResultsRepo.getInstance().createBoardResult();
        return true;
    }
}