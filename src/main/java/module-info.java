module com.board.config.boardconfiggui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.logging;
    requires com.sun.xml.bind;
    requires java.desktop;
    requires com.sun.istack.runtime;
    requires CodeGeneration;
    requires commons.collections;

    opens com.board.config.boardconfiggui to javafx.fxml;
    opens com.board.config.boardconfiggui.data.inputmodels.ipconfig to java.xml.bind;
    opens com.board.config.boardconfiggui.data.inputmodels.pinconfig to java.xml.bind;
    opens com.board.config.boardconfiggui.data.outputmodels to java.xml.bind, com.sun.xml.bind;
    opens com.board.config.boardconfiggui.data.outputmodels.clockconfig to java.xml.bind, com.sun.xml.bind;
    opens com.board.config.boardconfiggui.data.outputmodels.ipconfig to java.xml.bind, com.sun.xml.bind;
    opens com.board.config.boardconfiggui.data.outputmodels.genralconfig to java.xml.bind, com.sun.xml.bind;
    opens com.board.config.boardconfiggui.data.outputmodels.connectivityconfig to java.xml.bind, com.sun.xml.bind;
    opens com.board.config.boardconfiggui.data.outputmodels.pinconfig to java.xml.bind, com.sun.xml.bind;
    exports com.board.config.boardconfiggui;

    exports com.board.config.boardconfiggui.controllers;
    opens com.board.config.boardconfiggui.controllers to javafx.fxml;
}
