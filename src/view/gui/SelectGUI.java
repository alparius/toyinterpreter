package view.gui;

import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class SelectGUI {

    @FXML private ListView<String> prglist;
    private ArrayList<Controller> ctrls;
    private RunGUI gui;

    @FXML
    public void initialize() {

    }

    public void set_my_stuff(RunGUI gui, ArrayList<Controller> ctrls) {
        this.gui = gui;
        this.ctrls = ctrls;

        prglist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (newValue == null) return;
                int index = prglist.getSelectionModel().getSelectedIndex();
                gui.setAppCtrl(ctrls.get(index),index);
                gui.re_set();
            }
        });

        ArrayList<String> ctrls_string = new ArrayList<>();
        ctrls.forEach(v -> ctrls_string.add(v.toString()));
        ObservableList<String> prg_data = FXCollections.observableArrayList(ctrls_string);
        prglist.getItems().clear();
        prglist.getItems().setAll(prg_data);

    }
}
