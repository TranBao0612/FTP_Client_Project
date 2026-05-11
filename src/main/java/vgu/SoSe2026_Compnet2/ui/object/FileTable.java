package vgu.SoSe2026_Compnet2.ui.object;

import vgu.SoSe2026_Compnet2.ui.UIComponent;
import vgu.SoSe2026_Compnet2.data.FileMetadata;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.SelectionMode;
import java.util.List;

public class FileTable extends TableView<FileMetadata> implements UIComponent {
    private TableColumn<FileMetadata, String> nameCol = new TableColumn<>("Name");
    private TableColumn<FileMetadata, String> typeCol = new TableColumn<>("Type");
    private TableColumn<FileMetadata, Long> sizeCol = new TableColumn<>("Size");
    private TableColumn<FileMetadata, String> lastModifiedCol = new TableColumn<>("Last Modified");

    public FileTable() {
        // Add columns and set cell value factories
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeCol.setCellValueFactory(data -> 
                new SimpleStringProperty(data.getValue().getType() == FileMetadata.FileType.DIRECTORY ? "Folder" : "File"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("sizeInByte"));
        lastModifiedCol.setCellValueFactory(new PropertyValueFactory<>("lastModified"));
        getColumns().addAll(nameCol, typeCol, sizeCol, lastModifiedCol);

        // Prohibited multi-row selection to avoid ambiguity in file interactions
        getSelectionModel().setCellSelectionEnabled(false);
        getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * Clears all items from the table.
     */
    public void clear() {
        getItems().clear();
    }

    /**
     * Loads the given list of FileMetadata into the table, replacing any existing items.
     * @param files
     */
    public void setFiles(List<FileMetadata> files) {
        getItems().clear();
        getItems().addAll(files);
    }


    /**
     * Does not set fixed size, instead set the column to be resizable & allow the table to grow with the parent container.
     */
    @Override
    public void setFixedSize() {
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Set CSS classes for styling: no borders and alignment for columns.
     */
    @Override
    public void setStyle() {
        getStyleClass().add("file-table");
        nameCol.getStyleClass().add("left-aligned-col");
        typeCol.getStyleClass().add("right-aligned-col");
        sizeCol.getStyleClass().add("right-aligned-col");
        lastModifiedCol.getStyleClass().add("left-aligned-col");
    }
    

}