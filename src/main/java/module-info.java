module com.board.config.boardconfiggui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.logging;

    opens com.board.config.boardconfiggui to javafx.fxml;
    opens com.board.config.boardconfiggui.data.inputmodels.ipconfig to java.xml.bind;
    opens com.board.config.boardconfiggui.data.inputmodels.pinconfig to java.xml.bind;
    exports com.board.config.boardconfiggui;

    exports com.board.config.boardconfiggui.controllers;
    opens com.board.config.boardconfiggui.controllers to javafx.fxml;
}
