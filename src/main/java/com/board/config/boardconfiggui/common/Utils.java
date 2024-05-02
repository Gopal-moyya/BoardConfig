package com.board.config.boardconfiggui.common;

import com.board.config.boardconfiggui.data.enums.DeviceRole;
import com.board.config.boardconfiggui.data.outputmodels.BoardResult;
import com.board.config.boardconfiggui.data.outputmodels.connectivityconfig.ConnectivityConfig;
import com.board.config.boardconfiggui.data.outputmodels.connectivityconfig.ConnectivityIp;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.IpConfig;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.IpConfigIp;
import com.board.config.boardconfiggui.data.repo.BoardResultsRepo;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javafx.scene.control.Alert;
import org.apache.commons.collections.CollectionUtils;

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

            IpConfig ipConfig = boardResult.getIpConfig();
            if(Objects.nonNull(ipConfig)) {

                List<IpConfigIp> ipConfigIps = ipConfig.getIps();

                if(CollectionUtils.isNotEmpty(ipConfigIps)) {
                    List<ConnectivityIp> connectivityIps = new ArrayList<>();
                    for (IpConfigIp ipConfigIp : ipConfigIps) {
                        connectivityIps.add(new ConnectivityIp(ipConfigIp.getName()));
                    }

                    ConnectivityConfig connectivityConfig = new ConnectivityConfig(connectivityIps, CollectionUtils.size(connectivityIps));
                    boardResult.setConnectivityConfig(connectivityConfig);
                }

            }

            JAXBContext context = JAXBContext.newInstance(BoardResult.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(boardResult, new File(selectedDirectory, "board_configuration.xml"));

            alertDialog(Alert.AlertType.INFORMATION,"File Information",null, "File Saved successfully");
            return true;
        }catch (Exception e) {
            alertDialog(Alert.AlertType.ERROR,"File Information", null, "Problem in saving Board Results");
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
    public static void alertDialog(Alert.AlertType alertType, String title, String header, String content){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
