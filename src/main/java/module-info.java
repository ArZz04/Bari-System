module org.arzzcorp.barisystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.json;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.net.http;

    // Abre y exporta los paquetes necesarios
    opens org.arzzcorp.barisystem to javafx.fxml;
    opens org.arzzcorp.barisystem.components to javafx.fxml; // Para UserBox
    opens org.arzzcorp.barisystem.components.generic to javafx.fxml; // Para MenuSide
    opens org.arzzcorp.barisystem.components.views to javafx.fxml;

    opens org.arzzcorp.barisystem.controllers to javafx.fxml;

    exports org.arzzcorp.barisystem;
    exports org.arzzcorp.barisystem.components; // Para que FXML pueda instanciar UserBox
    exports org.arzzcorp.barisystem.components.generic; // Para que FXML pueda instanciar MenuSide
    exports org.arzzcorp.barisystem.controllers;
}