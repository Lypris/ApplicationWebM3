package fr.insa.trenchant_troullier_virquin.applicationwebm3.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.insa.trenchant_troullier_virquin.applicationwebm3.data.entity.Operateur;
import fr.insa.trenchant_troullier_virquin.applicationwebm3.data.entity.Produit;
import fr.insa.trenchant_troullier_virquin.applicationwebm3.data.service.CrmService;

@Route(value = "product", layout = MainLayout.class)
@PageTitle("Produits | M3 Application")
public class ProductView extends VerticalLayout {

    Grid<Produit> grid = new Grid<>(Produit.class);
    TextField filterText = new TextField();
    ProductForm form;
    CrmService service;

    public ProductView(CrmService service) {
        this.service = service;
        addClassName("product-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }
    private void configureForm() {
        form = new ProductForm();
        form.setWidth("35em");
        form.addSaveListener(this::saveProduct);
        form.addDeleteListener(this::deleteProduct);
        form.addCloseListener(e -> closeEditor());
    }
    private void saveProduct(ProductForm.SaveEvent event) {
        service.saveProduit(event.getProduit());
        updateList();
        closeEditor();
    }
    private void deleteProduct(ProductForm.DeleteEvent event) {
        service.deleteProduit(event.getProduit());
        updateList();
        closeEditor();
    }
    private void configureGrid() {
        grid.addClassName("operateur-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("version");
        grid.addColumn(Produit::getRef).setHeader("Référence");
        grid.addColumn(Produit::getDes).setHeader("Description");
        grid.addColumn(Produit::getPrix).setHeader("Prix");
        //TODO : Ajouter une colonne pour l'image
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editProduit(event.getValue()));
    }
    private void editProduit(Produit produit) {
        if (produit == null) {
            closeEditor();
        } else {
            form.setProduit(produit);
            form.setVisible(true);
            addClassName("editing");
        }
    }
    private void closeEditor() {
        form.setProduit(null);
        form.setVisible(false);
        removeClassName("editing");
    }
    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        Button addProductButton = new Button("Add product", click -> addProduct());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addProductButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
    private void updateList() {
        grid.setItems(service.findAllProduits(filterText.getValue()));
    }
    private void addProduct() {
        grid.asSingleSelect().clear();
        editProduit(new Produit());
    }

}
