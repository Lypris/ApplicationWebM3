package fr.insa.trenchant_troullier_virquin.applicationwebm3.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.provider.ListDataProvider;
import fr.insa.trenchant_troullier_virquin.applicationwebm3.data.entity.Produit;
import fr.insa.trenchant_troullier_virquin.applicationwebm3.data.entity.TypeOperation;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.grid.dnd.GridDragEndEvent;
import com.vaadin.flow.component.grid.dnd.GridDragStartEvent;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.html.Div;

import java.util.ArrayList;
import java.util.List;

public class DialogDefOpp extends Dialog {

    private TypeOperation draggedItem;
    BeanValidationBinder<Produit> binder = new BeanValidationBinder<>(Produit.class);

    public DialogDefOpp(List<TypeOperation> typeOperations) {
        Grid<TypeOperation> grid1 = setupGrid("Opérations disponibles");
        Grid<TypeOperation> grid2 = setupGrid("Opérations définies");
        ArrayList<TypeOperation> typeoperations = new ArrayList<>(typeOperations);
        ArrayList<TypeOperation> typeoperationsDefini = new ArrayList<>();
        GridListDataView<TypeOperation> dataView1 = grid1.setItems(typeoperations);
        GridListDataView<TypeOperation> dataView2 = grid2.setItems(typeoperationsDefini);


        grid1.setDropMode(GridDropMode.ON_GRID);
        grid1.setRowsDraggable(true);
        grid1.addDragStartListener(this::handleDragStart);
        grid1.addDropListener(e -> {
            dataView2.removeItem(draggedItem);
            dataView1.addItem(draggedItem);
        });
        grid1.addDragEndListener(this::handleDragEnd);

        grid2.setDropMode(GridDropMode.ON_GRID);
        grid2.setRowsDraggable(true);
        grid2.addDragStartListener(this::handleDragStart);
        grid2.addDropListener(e -> {
            dataView1.removeItem(draggedItem);
            dataView2.addItem(draggedItem);
        });
        grid2.addDragEndListener(this::handleDragEnd);

        Div container = new Div(grid1, grid2);
        setContainerStyles(container);

        add(container);
        configureFooter();
    }

    private void configureFooter() {
        Button saveButton = new Button("Enregistrer", (e) -> this.close()); //TODO : Enregistrer les opérations
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);
        saveButton.getStyle().set("margin-right", "auto");
        this.getFooter().add(saveButton);

        Button cancelButton = new Button("Cancel", (e) -> this.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.getFooter().add(cancelButton);
    }

    private static Grid<TypeOperation> setupGrid(String header) {
        Grid<TypeOperation> grid = new Grid<>(TypeOperation.class);
        grid.removeAllColumns();
        grid.addColumn(TypeOperation::getDes).setHeader(header);
        setGridStyles(grid);
        return grid;
    }

    private void handleDragStart(GridDragStartEvent<TypeOperation> e) {
        draggedItem = e.getDraggedItems().get(0);
    }

    private void handleDragEnd(GridDragEndEvent<TypeOperation> e) {
        draggedItem = null;
    }

    private static void setGridStyles(Grid<TypeOperation> grid) {
        grid.getStyle().set("width", "300px").set("height", "300px")
                .set("margin-left", "0.5rem").set("margin-top", "0.5rem")
                .set("align-self", "unset");
    }

    private static void setContainerStyles(Div container) {
        container.getStyle().set("display", "flex").set("flex-direction", "row")
                .set("flex-wrap", "wrap");
    }
    public void setProduit(Produit produit) {
        //permet de récupérer les opérations définies pour un produit
        //TODO : Récupérer les opérations définies
    }
    private void save() {
        //TODO : Enregistrer les opérations définies pour un produit

    }

}
