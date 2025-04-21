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

    exports org.arzzcorp.barisystem;
    exports org.arzzcorp.barisystem.components; // Para que FXML pueda instanciar UserBox
}