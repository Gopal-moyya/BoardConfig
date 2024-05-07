package com.board.config.boardconfiggui.common;

import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.enums.DeviceRole;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Pin;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.PinConfig;
import com.board.config.boardconfiggui.data.outputmodels.BoardResult;
import com.board.config.boardconfiggui.data.outputmodels.connectivityconfig.ConnectivityConfig;
import com.board.config.boardconfiggui.data.outputmodels.connectivityconfig.ConnectivityIp;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.IpConfig;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.IpConfigIp;
import com.board.config.boardconfiggui.data.repo.BoardResultsRepo;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.*;
import java.util.logging.Logger;

import com.board.config.boardconfiggui.data.repo.InputConfigRepo;
import com.board.config.boardconfiggui.ui.models.PinType;
import javafx.scene.control.Alert;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import static com.board.config.boardconfiggui.data.Constants.EXTI;

/**
 * Utility class containing helper methods.
 */
public class Utils {
    private static final Logger logger = Logger.getLogger(Utils.class.getName());

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
            logger.info("Exception occurred while saving results." + e);
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

    /**
     * Validates the existence of specific XML files in the given folder.
     *
     * @param xmlFolderPath The path to the folder containing XML files.
     * @return {@code true} if both hardware configuration and pin muxing configuration files exist; otherwise, {@code false}.
     */
    public static boolean isInputConfigurationReached(String xmlFolderPath) {
        File hardwareConfigFile = new File(xmlFolderPath, Constants.HARDWARE_CONFIG_FILE_NAME);
        File pinMuxingConfigFile = new File(xmlFolderPath, Constants.PIN_CONFIG_FILE_NAME);

        return hardwareConfigFile.exists() && pinMuxingConfigFile.exists();
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
    public static boolean parseInputXmlFiles(String xmlFolderPath) {
        try {
            InputConfigRepo inputConfigRepo = InputConfigRepo.getInstance();
            JAXBContext ipConfigContext = JAXBContext.newInstance(com.board.config.boardconfiggui.data.inputmodels.ipconfig.IpConfig.class);
            Unmarshaller ipConfigUnmarshaller = ipConfigContext.createUnmarshaller();
            com.board.config.boardconfiggui.data.inputmodels.ipconfig.IpConfig ipConfig = (com.board.config.boardconfiggui.data.inputmodels.ipconfig.IpConfig) ipConfigUnmarshaller.unmarshal(new File(xmlFolderPath, Constants.HARDWARE_CONFIG_FILE_NAME));
            inputConfigRepo.setIpConfig(ipConfig);

            JAXBContext pinConfigContext = JAXBContext.newInstance(PinConfig.class);
            Unmarshaller pinConfigUnmarshaller = pinConfigContext.createUnmarshaller();
            PinConfig pinConfig = (PinConfig) pinConfigUnmarshaller.unmarshal(new File(xmlFolderPath, Constants.PIN_CONFIG_FILE_NAME));
            inputConfigRepo.setPinConfig(pinConfig);
        } catch (JAXBException e) {
            logger.warning("Input xml files parsing failed" + e);
            return false;
        }

        return true;
    }

    /**
     * <p>
     * Retrieves the BoardResult from an XML file located in the specified folder path.
     * * Creating a board config xml file to retrieves the data then checking the file availability.
     * </>
     *
     * <p>
     * if exists,
     * Create an Unmarshal for the BoardResult class and
     * unmarshal the BoardResult from the specified XML file
     * </>
     *
     * @param xmlFolderPath The path to the folder containing the XML file.
     * @return The parsed BoardResult object, or null if parsing fails or the file does not exist.
     */
    public static BoardResult getBoardConfigResult(String xmlFolderPath) {
        File outputFile = new File(xmlFolderPath, Constants.BOARD_CONFIG_FILE_NAME);
        if (BooleanUtils.isTrue(outputFile.exists())) {
            try {
                // Create an Unmarshal for the BoardResult class
                JAXBContext boardConfigContext = JAXBContext.newInstance(BoardResult.class);
                Unmarshaller boardConfigUnmarshaller = boardConfigContext.createUnmarshaller();

                // Unmarshal the BoardResult from the specified XML file
                return (BoardResult) boardConfigUnmarshaller.unmarshal(outputFile);
            } catch (JAXBException e) {
                // Log a warning if XML file parsing fails
                logger.warning("Output XML files parsing failed: " + e.getMessage());
            }
        }
        return null;
    }

    public static List<PinType> getPinTypesFromXml(Pin pin) {
        String pinValue = pin.getValues().replaceAll(" ", "");
        List<PinType> pinTypes = new ArrayList<>();
        PinType modesPinType = new PinType(Constants.MODES_TEXT);

        if (pinValue.contains("],")) {
            String[] splitValues = pinValue.split("],");
            for (String mode : splitValues) {

                if (mode.contains(":[")) {
                    String[] split = mode.split(":\\[");

                    String modeName = split[0].trim();
                    if(!modeName.contains(EXTI)) //Not adding EXTI in modes types.
                        modesPinType.
                                addChild(modeName);
                    List<PinType> pins = getModeValues(modeName, split[1]);
                    if(CollectionUtils.isNotEmpty(pins)){
                        pinTypes.addAll(pins);
                    }

                }
            }
        }
        pinTypes.add(modesPinType);
        return pinTypes;
    }

    private static List<PinType> getModeValues(String modeName, String value){
        List<PinType> pinTypes = new ArrayList<>();

        List<String> modeValues = new ArrayList<>();

        String pinValueString = value;

        if(pinValueString.contains("]")){
            pinValueString = pinValueString.replaceAll("]", "");
        }

        if(pinValueString.contains(",")){
            modeValues = Arrays.stream(pinValueString.split(",")).toList();
        }

        if(CollectionUtils.isEmpty(modeValues)){
            modeValues.add(pinValueString);
        }

        if(modeName.equals(Constants.GPIO)){
            PinType gpioType = new PinType(modeName);

            Map<String, List<String>> subValues = new HashMap<>();
            for(String name : modeValues){
                if(name.contains("_")){
                    String[] split = name.split("_");
                    List<String> strings = subValues.get(split[0]);
                    if(strings == null){
                        strings = new ArrayList<>();
                    }
                    strings.add(split[1]);
                    subValues.put(split[0], strings);
                }else{
                    subValues.put(name, new ArrayList<>());
                }
            }

            List<PinType> subPinTypes = new ArrayList<>();
            for(String key : subValues.keySet()){
                gpioType.addChild(key);
                if(key.equals(Constants.INPUT))
                    continue;
                PinType pinType1 = new PinType(key);
                pinType1.setChildren(subValues.get(key));
                subPinTypes.add(pinType1);
            }

            pinTypes.add(gpioType);
            pinTypes.addAll(subPinTypes);

        }else if(modeName.contains(EXTI)){

            PinType inputPinType = new PinType(Constants.INPUT);
            inputPinType.addChild(Constants.INPUT);
            inputPinType.addChild(modeName);
            pinTypes.add(inputPinType);

            Map<String, List<String>> subValues = new HashMap<>();
            for(String name : modeValues){
                if(name.contains("_")){
                    String[] split = name.split("_");
                    List<String> strings = subValues.get(split[0]);
                    if(strings == null){
                        strings = new ArrayList<>();
                    }
                    strings.add(split[2]);
                    subValues.put(split[0], strings);
                }
            }

            List<PinType> subPinTypes = new ArrayList<>();

            PinType extType = new PinType(modeName);
            for(String key : subValues.keySet()){
                extType.addChild(key);
                PinType pinType1 = new PinType(key);
                pinType1.setChildren(subValues.get(key));
                subPinTypes.add(pinType1);
            }

            pinTypes.add(extType);
            pinTypes.addAll(subPinTypes);

        }else if(modeName.equals(Constants.BY_PASS)){
            PinType bypassType = new PinType(modeName);
            bypassType.setChildren(modeValues);
            pinTypes.add(bypassType);
        }

        return pinTypes;
    }
}
