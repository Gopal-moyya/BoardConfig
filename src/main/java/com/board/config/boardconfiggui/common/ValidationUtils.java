package com.board.config.boardconfiggui.common;

import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.outputmodels.BoardResult;
import com.board.config.boardconfiggui.data.outputmodels.Param;
import com.board.config.boardconfiggui.data.outputmodels.clockconfig.ClockConfig;
import com.board.config.boardconfiggui.data.outputmodels.clockconfig.ClockConfigParam;
import com.board.config.boardconfiggui.data.outputmodels.connectivityconfig.ConnectivityConfig;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.DeviceConfiguration;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.IpConfig;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.IpConfigIp;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.SignalParam;
import com.board.config.boardconfiggui.data.outputmodels.pinconfig.PinConfig;
import com.board.config.boardconfiggui.data.outputmodels.pinconfig.PinConfigParam;
import com.board.config.boardconfiggui.data.outputmodels.pinconfig.PinConfigPort;
import com.board.config.boardconfiggui.data.repo.BoardResultsRepo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValidationUtils {

    public static String validateData() {
        BoardResultsRepo boardResultsRepo = BoardResultsRepo.getInstance();
        BoardResult boardResult = boardResultsRepo.getBoardResult();
        return validateBoardResult(boardResult);
    }

    private static String validateBoardResult(BoardResult boardResult) {
        StringBuilder missingConfig = new StringBuilder();
        if (boardResult == null) {
            missingConfig.append("Board result is missing.");
            return missingConfig.toString();
        }

//        String message = validateConnectivityConfig(boardResult.getConnectivityConfig());
//        if (StringUtils.isNotEmpty(message)) {
//            missingConfig.append(message);
//        }

        String message = validatePinConfig(boardResult.getPinConfig());
        if (StringUtils.isNotEmpty(message)) {
            missingConfig.append(message);
        }

        message = validateIpConfig(boardResult.getIpConfig());
        if (StringUtils.isNotEmpty(message)) {
            missingConfig.append(message);
        }

        message = validateClockConfig(boardResult.getClockConfig());
        if (StringUtils.isNotEmpty(message)) {
            missingConfig.append(message);
        }

        return missingConfig.toString();
    }

    private static String validateConnectivityConfig(ConnectivityConfig connectivityConfig) {
        if (connectivityConfig == null) {
            return "ConnectivityConfig: Connectivity config is missing. \n";
        }

        if (connectivityConfig.getNumberOfIps() < 0) {
            return "ConnectivityConfig: Number of IPs selected are zero. \n";
        }

        if (connectivityConfig.getNumberOfIps() != connectivityConfig.getIp().size()) {
            return "ConnectivityConfig: Number of IPs param and selected IPs are not same. \n";
        }

        return null;
    }

    private static String validatePinConfig(PinConfig pinConfig) {
        if (pinConfig == null || CollectionUtils.isEmpty(pinConfig.getPorts())) {
            return "PinConfig: Pin config is missing. \n";
        }

        StringBuilder missingConfig = new StringBuilder();

        for (PinConfigPort port : pinConfig.getPorts()) {
            if (CollectionUtils.isEmpty(port.getConfigParams())) {
                missingConfig.append("PinConfig: Pins are not configured for ")
                        .append(port.getName()).append(".\n");
                continue;
            }

            port.getConfigParams().stream()
                    .map(ValidationUtils::validatePinConfigParam)
                    .filter(StringUtils::isNotEmpty)
                    .forEach(message -> missingConfig.append("PinConfig ")
                            .append(port.getName())
                            .append(": ")
                            .append(message)
                            .append(".\n"));
        }

        return missingConfig.toString();
    }

    private static String validatePinConfigParam(PinConfigParam pinConfigParam) {
        boolean isNotByPassMode = pinConfigParam.isByPassMode() != null && !pinConfigParam.isByPassMode();
        boolean isIntEnable = pinConfigParam.isIntEnable() != null && pinConfigParam.isIntEnable();

        if (isNotByPassMode) {
            if (StringUtils.isEmpty(pinConfigParam.getDirection())) {
                return "Direction is missing for pin " + pinConfigParam.getPin();
            }

            if (Constants.DIRECTION_OUTPUT.equals(pinConfigParam.getDirection())
                    && StringUtils.isEmpty(pinConfigParam.getValue())) {
                return "Output value is missing for pin " + pinConfigParam.getPin();
            }
        } else if (isIntEnable) {
            if (StringUtils.isEmpty(pinConfigParam.getIntType()) || StringUtils.isEmpty(pinConfigParam.getIntValue())) {
                return "IntType or IntValue is missing for pin " + pinConfigParam.getPin();
            }
        }

        return null;
    }

    private static String validateIpConfig(IpConfig ipConfig) {
        if (ipConfig == null || CollectionUtils.isEmpty(ipConfig.getIps())) {
            return "IPConfig: IP config is missing. \n";
        }

        StringBuilder missingConfig = new StringBuilder();

        for (IpConfigIp ipConfigIp : ipConfig.getIps()) {
            if (CollectionUtils.isEmpty(ipConfigIp.getPorts())) {
                missingConfig.append("IPConfig: Ports are not configured for ")
                        .append(ipConfigIp.getName()).append(".\n");
            } else {
                // Validate IpConfig signal params
                List<SignalParam> ipSignalParams = ipConfigIp.getPorts().stream()
                        .flatMap(port -> {
                            if (CollectionUtils.isNotEmpty(port.getSignalParams())) {
                                return port.getSignalParams().stream();
                            } else {
                                return Stream.empty();
                            }
                        })
                        .collect(Collectors.toList());

                if (CollectionUtils.isNotEmpty(ipSignalParams)) {
                    String message = validateIpConfigSignalParams(ipConfigIp.getName(), ipSignalParams);
                    if (StringUtils.isNotEmpty(message)) {
                        missingConfig.append(message);
                    }
                }
            }

            // Validate IpConfig params
            if (CollectionUtils.isEmpty(ipConfigIp.getParams())) {
                missingConfig.append("IPConfig: Required params like sysclk, i2cFreq, sdrFreg are missing for ")
                        .append(ipConfigIp.getName()).append(".\n");
            } else {
                String message = validateIpConfigParams(ipConfigIp.getName(), ipConfigIp.getParams());
                if (StringUtils.isNotEmpty(message)) {
                    missingConfig.append(message);
                }
            }

            //Validate device Descriptor
            if (ipConfigIp.getDeviceDescriptor() != null) {
                String message = validateIpConfigDeviceConfiguration(ipConfigIp.getName(),
                        ipConfigIp.getDeviceDescriptor().getDeviceConfigurations());

                if (StringUtils.isNotEmpty(message)) {
                    missingConfig.append(message);
                }
            }
        }

        return missingConfig.toString();
    }

    private static String validateIpConfigSignalParams(String ipName, List<SignalParam> signalParams) {
        // ToDo: Need to check the required signals
        List<String> signalsRequired = Arrays.asList(ipName + "_SCL", ipName + "_SDA");

        StringBuilder missingConfig = new StringBuilder();

        signalsRequired.stream()
                .filter(signal -> signalParams.stream().noneMatch(param -> param.getName().equals(signal)))
                .forEach(signal -> missingConfig.append("IPConfig: SignalParam ")
                        .append(signal)
                        .append(" is missing for ")
                        .append(ipName)
                        .append(".\n"));

        return missingConfig.toString();
    }

    private static String validateIpConfigParams(String ipName, List<Param> params) {
        List<String> paramsRequired = Constants.I3C_IP_CONFIG_PARAMS_REQUIRED;

        StringBuilder missingConfig = new StringBuilder();

        paramsRequired.stream()
                .filter(paramRequired -> params.stream().noneMatch(p -> p.getName().equals(paramRequired) && StringUtils.isNotEmpty(p.getValue())))
                .forEach(paramRequired -> missingConfig.append("IPConfig: Param ")
                        .append(paramRequired)
                        .append(" is missing for ")
                        .append(ipName)
                        .append(".\n"));

        return missingConfig.toString();
    }

    private static String validateIpConfigDeviceConfiguration(String ipName, List<DeviceConfiguration> deviceConfigurations) {
        if (CollectionUtils.isEmpty(deviceConfigurations)) {
            return "IPConfig DeviceConfiguration: Device configurations are missing for " + ipName + ". \n";
        }

        StringBuilder missingConfig = new StringBuilder();

        List<String> paramsRequired = Constants.I3C_DEVICE_CONFIG_PARAMS_REQUIRED;
        List<String> ibiParamsRequired = Constants.I3C_CONFIG_IBI_DEVICE_PARAMS_REQUIRED;

        for (DeviceConfiguration deviceConfiguration : deviceConfigurations) {
            if (CollectionUtils.isEmpty(deviceConfiguration.getParams())) {
                missingConfig.append("IPConfig DeviceConfiguration: Required params are missing for device ")
                        .append(deviceConfiguration.getName() == null ? "unknown" : deviceConfiguration.getName())
                        .append(" in ").append(ipName)
                        .append(".\n");
                continue;
            }

            List<Param> params = deviceConfiguration.getParams();
            boolean isIbiDevice = params.stream()
              .anyMatch(param -> param != null && StringUtils.equals(param.getName(),Constants.IS_IBI_DEVICE) && StringUtils.equals(param.getValue(), Constants.ONE));

            List<String> combinedParamsRequired = new ArrayList<>(paramsRequired);
            if (isIbiDevice) {
                combinedParamsRequired.addAll(ibiParamsRequired);
            }

            for (String paramRequired : combinedParamsRequired) {
                if (params.stream().noneMatch(p -> p.getName().equals(paramRequired) && StringUtils.isNotEmpty(p.getValue()))) {
                    missingConfig.append("IPConfig DeviceConfiguration: Param ")
                            .append(paramRequired)
                            .append(" is missing for device ")
                            .append(deviceConfiguration.getName() == null ? "unknown" : deviceConfiguration.getName())
                            .append(" in ").append(ipName).append(".\n");
                }
            }
        }

        return missingConfig.toString();
    }

    private static String validateClockConfig(ClockConfig clockConfig) {
        if (clockConfig == null || CollectionUtils.isEmpty(clockConfig.getConfigParams())) {
            return "ClockConfig: Clock config is missing.";
        }

        List<String> paramsRequired = Constants.CLOCK_CONFIG_PARAMS_REQUIRED;
        List<ClockConfigParam> configParams = clockConfig.getConfigParams();

        StringBuilder missingConfig = new StringBuilder();

        paramsRequired.stream()
                .filter(paramRequired -> configParams.stream().noneMatch(p -> p.getName().equals(paramRequired) && StringUtils.isNotEmpty(p.getValue())))
                .forEach(paramRequired -> missingConfig.append("ClockConfig: Param ")
                        .append(paramRequired)
                        .append(" is missing.\n"));

        return missingConfig.toString();
    }
}
