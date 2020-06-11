package view.gui;

import controller.Controller;
import exceptions.ExecutionStackEmptyException;
import exceptions.RepoException;
import exceptions.StopExecution;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import utils.Pair;

import java.util.ArrayList;

public class RunGUI {

    @FXML private TableView<Pair<Integer,Integer>> heaptable;
    @FXML private TableColumn<Pair<Integer,Integer>,Integer> heap_address;
    @FXML private TableColumn<Pair<Integer,Integer>,Integer> heap_value;

    @FXML private ListView<Integer> outputlist;

    @FXML private TableView<Pair<Integer,String>> filetable;
    @FXML private TableColumn<Pair<Integer,String>,Integer> file_id;
    @FXML private TableColumn<Pair<Integer,String>,String> file_name;

    @FXML private TableView<Pair<String,String>> proctable;
    @FXML private TableColumn<Pair<String,String>,String> proc_name;
    @FXML private TableColumn<Pair<String,String>,String> proc_body;

    @FXML private TextField nrthreads;

    @FXML private ListView<Integer> threadlist;

    @FXML private TableView<Pair<String,Integer>> symboltable;
    @FXML private TableColumn<Pair<String,Integer>,String> sym_name;
    @FXML private TableColumn<Pair<String,Integer>,Integer> sym_value;

    @FXML private ListView<String> exestacklist;

    @FXML private Button nextstep;

    private ArrayList<Boolean> stopped;
    private int index;
    private Controller appCtrl;

    public void setAppCtrl(Controller appCtrl, int index) {
        this.appCtrl = appCtrl;
        this.index = index;
        if (appCtrl.activeButton() && (! stopped.get(index)))
            nextstep.setDisable(false);
        else
            nextstep.setDisable(true);
    }


    @FXML
    public void initialize() {
        threadlist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                if (newValue == null)
                    return;
                displaythread(newValue);
            }
        });

        this.stopped = new ArrayList<>();
        for (int i=1; i<=9; i++)
            stopped.add(false);

        nextstep.setDisable(true);

        heap_address.setCellValueFactory(new PropertyValueFactory<Pair<Integer,Integer>, Integer>("first"));
        heap_value.setCellValueFactory(new PropertyValueFactory<Pair<Integer,Integer>, Integer>("second"));

        file_id.setCellValueFactory(new PropertyValueFactory<Pair<Integer,String>, Integer>("first"));
        file_name.setCellValueFactory(new PropertyValueFactory<Pair<Integer,String>, String>("second"));

        proc_name.setCellValueFactory(new PropertyValueFactory<Pair<String,String>, String>("first"));
        proc_body.setCellValueFactory(new PropertyValueFactory<Pair<String,String>, String>("second"));

        sym_name.setCellValueFactory(new PropertyValueFactory<Pair<String,Integer>, String>("first"));
        sym_value.setCellValueFactory(new PropertyValueFactory<Pair<String,Integer>, Integer>("second"));
    }


    @FXML
    public void re_set() {
        // setup heap table
        ArrayList<Pair<Integer,Integer>> myheaptable = appCtrl.getHeapTable();
        heaptable.getItems().clear();
        heaptable.getItems().setAll(myheaptable);

        // setup output list
        ArrayList<Integer> myoutlist = appCtrl.getOutputList();
        ObservableList<Integer> out_data = FXCollections.observableArrayList(myoutlist);
        outputlist.getItems().clear();
        outputlist.getItems().setAll(out_data);

        // setup filetable
        ArrayList<Pair<Integer,String>> myfiletable = appCtrl.getFileTable();

        filetable.getItems().clear();
        filetable.getItems().setAll(myfiletable);

        // setup proctable
        ArrayList<Pair<String,String>> myproctable = appCtrl.getProcTable();

        proctable.getItems().clear();
        proctable.getItems().setAll(myproctable);

        // setup thread counter
        nrthreads.setText(appCtrl.nrofthreads().toString());

        // setup thread list
        ArrayList<Integer> mythreadlist = appCtrl.getThreadList();
        ObservableList<Integer> thread_data = FXCollections.observableArrayList(mythreadlist);
        threadlist.getItems().clear();
        threadlist.getItems().setAll(thread_data);

        threadlist.getSelectionModel().clearSelection();
        threadlist.getSelectionModel().selectFirst();
        //if (threadlist.getItems().size() == 1) {
        //    threadlist.getSelectionModel().selectFirst();
        //}
    }



    public void displaythread(Integer threadnr) {
        // setup symboltable
        ArrayList<Pair<String,Integer>> mysymtable = appCtrl.getSymTable(threadnr);
        symboltable.getItems().clear();
        symboltable.getItems().setAll(mysymtable);

        // setup exestack list
        ArrayList<String> myexestack = appCtrl.getExestackList(threadnr);
        ObservableList<String> exestack_data = FXCollections.observableArrayList(myexestack);
        exestacklist.getItems().clear();
        exestacklist.getItems().setAll(exestack_data);
    }



    @FXML
    public void nextstep(ActionEvent actionEvent) {
        try {
            appCtrl.oneStep();
            re_set();
        }
        catch(StopExecution | RepoException | ExecutionStackEmptyException e) {
            nextstep.setDisable(true);
            this.stopped.set(index,true);

            Label t = new Label(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK);
            alert.getDialogPane().setContent(t);
            alert.showAndWait();
        }
    }

}