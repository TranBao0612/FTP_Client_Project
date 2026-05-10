package vgu.SoSe2026_Compnet2.ui.object;

import vgu.SoSe2026_Compnet2.ui.UIComponent;
import vgu.SoSe2026_Compnet2.data.FileMetadata;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.beans.property.*;
import java.util.List;

public class FileTable extends TableView<FileMetadata> implements UIComponent {
    private TableColumn<FileMetadata, String> nameCol = new TableColumn<>("Name");
    private TableColumn<FileMetadata, String> typeCol = new TableColumn<>("Type");
    private TableColumn<FileMetadata, Long> sizeCol = new TableColumn<>("Size");
    private TableColumn<FileMetadata, String> lastModifiedCol = new TableColumn<>("Last Modified");

    public FileTable() {
        // Add columns and set cell value factories
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().name));
        typeCol.setCellValueFactory(data -> 
                new SimpleStringProperty(data.getValue().type == FileMetadata.FileType.DIRECTORY ? "Folder" : "File"));
        sizeCol.setCellValueFactory(data -> new SimpleLongProperty(data.getValue().sizeInByte).asObject());
        lastModifiedCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().lastModified));
        getColumns().addAll(nameCol, typeCol, sizeCol, lastModifiedCol);
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
     * Does not set fixed size, instead set the column to be resizable.
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
        nameCol.getStyleClass().add("left-aligned_col");
        typeCol.getStyleClass().add("right-aligned_col");
        sizeCol.getStyleClass().add("right-aligned_col");
        lastModifiedCol.getStyleClass().add("left-aligned_col");
    }
    

}