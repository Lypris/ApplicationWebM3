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
import fr.insa.trenchant_troullier_virquin.applicationwebm3.data.entity.Machine;
import fr.insa.trenchant_troullier_virquin.applicationwebm3.data.entity.Machine;
import fr.insa.trenchant_troullier_virquin.applicationwebm3.data.service.CrmService;

@Route(value = "machine", layout = MainLayout.class)
@PageTitle("Machine | M3 Application")
public class MachineView extends VerticalLayout {

    Grid<Machine> grid = new Grid<>(Machine.class);
    TextField filterText = new TextField();
    MachineForm form;
    CrmService service;

    public MachineView(CrmService service) {
        this.service = service;
        addClassName("machine-view");
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
        form = new MachineForm();
        form.setWidth("25em");
        form.addSaveListener(this::saveMachine);
        form.addDeleteListener(this::deleteMachine);
        form.addCloseListener(e -> closeEditor());
    }
    private void saveMachine(MachineForm.SaveEvent event) {
        service.saveMachine(event.getMachine());
        updateList();
        closeEditor();
    }

    private void deleteMachine(MachineForm.DeleteEvent event) {
        service.deleteMachine(event.getMachine());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("Machine-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("version");
        grid.removeColumnByKey("des");
        grid.removeColumnByKey("ref");
        grid.removeColumnByKey("puissance");
        grid.addColumn(Machine::getRef)
                .setHeader("Référence").setSortable(true);
        grid.addColumn(Machine::getDes)
                .setHeader("Description").setSortable(true);
        grid.addColumn(Machine::getPuissance)
                .setHeader("Puissance (kW)").setSortable(true);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editMachine(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filtrer les machines...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addMachineButton = new Button("Ajouter une machine");
        addMachineButton.addClickListener(click -> addMachine());
        var toolbar = new HorizontalLayout(filterText, addMachineButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
    public void editMachine(Machine Machine) {
        if (Machine == null) {
            closeEditor();
        } else {
            form.setMachine(Machine);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setMachine(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addMachine() {
        grid.asSingleSelect().clear();
        editMachine(new Machine());
    }

    private void updateList() {
        grid.setItems(service.findAllMachines(filterText.getValue()));
    }
}