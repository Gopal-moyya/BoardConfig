package com.board.config.boardconfiggui.common;

import com.board.config.boardconfiggui.data.enums.DeviceRole;
import com.board.config.boardconfiggui.data.outputmodels.BoardResult;
import com.board.config.boardconfiggui.data.repo.BoardResultsRepo;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Alert;

/**
 * Utility class containing helper methods.
 */
public class Utils {

    /**
     * Returns a list of slave device roles.
     * The roles include "SECONDARY_MASTER" and "SLAVE".
     *
     * @return List of strings representing slave device roles.
     */
    public static List<String> getSlaveDeviceRoles() {
        return Arrays.asList(DeviceRole.SECONDARY_MASTER.name().toLowerCase(), DeviceRole.SLAVE.name().toLowerCase());
    }

    /**
     * Save BoardResult to the given directory path
     */
    public static boolean saveData(String folderPath){

        File selectedDirectory = new File(folderPath);
        try {
            BoardResultsRepo boardResultsRepo = BoardResultsRepo.getInstance();
            BoardResult boardResult = boardResultsRepo.getBoardResult();

            JAXBContext context = JAXBContext.newInstance(BoardResult.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(boardResult, new File(selectedDirectory, "board_configuration.xml"));

            alertDialog(Alert.AlertType.INFORMATION,"File Information","File Saved successfully");
            return true;
        }catch (Exception e) {
            alertDialog(Alert.AlertType.ERROR,"File Information","Problem in saving Board Results");
        }

        return false;
    }

    /**
     * Displays an alert dialog with the specified alert type, title, and content.
     *
     * @param alertType The type of alert to be displayed (e.g., INFORMATION, WARNING, ERROR).
     * @param title     The title of the alert dialog.
     * @param content   The content text to be displayed in the alert dialog.
     */
    private static void alertDialog(Alert.AlertType alertType, String title, String content){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
