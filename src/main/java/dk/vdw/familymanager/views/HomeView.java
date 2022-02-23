package dk.vdw.familymanager.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import dk.vdw.familymanager.model.Contact;
import dk.vdw.familymanager.services.ContactService;

@Route("")
public class HomeView extends VerticalLayout {
    private final ContactService contactService;
    private final TextField filterText = new TextField();
    private Grid grid = new Grid(Contact.class);

    public HomeView(ContactService contactService) {
        this.contactService = contactService;
        setClassName("home-view");
        setSizeFull();
        configureGrid();
        add(getToolbar(),grid);
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email");
        grid.setPageSize(20);
        grid.setItems(contactService.findAll());

    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add contact");

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        grid.setItems(contactService.findContactByFirstnameOrLastname(filterText.getValue()));
    }
}
