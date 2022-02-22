package com.example.application.views.item;

import com.example.application.backend.common.RfidUseCase;
import com.example.application.backend.common.UploadExamplesI18N;
import com.example.application.backend.items.components.ItemCreation;
import com.example.application.backend.items.services.ItemService;
import com.example.application.backend.models.Item;
import com.example.application.backend.models.ItemImage;
import com.example.application.backend.readerConfig.services.ReaderConfigService;
import com.impinj.octane.OctaneSdkException;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;

import static com.example.application.utils.EnvironmentVariables.readerConfiguration;
import static com.vaadin.flow.component.Unit.PIXELS;
import static com.vaadin.flow.component.notification.Notification.Position.MIDDLE;

@PageTitle("New item")
public class ItemView extends VerticalLayout {

    private final ItemService itemService;
    private final ReaderConfigService readerConfigService;
    private TextField name;
    private TextField description;
    private TextArea information;
    private static TextField epc;
    private Button save;
    private Upload upload;
    private Image image;
    private ItemImage itemImage;

    public ItemView(ItemService itemService, ReaderConfigService readerConfigService) {
        this.itemService = itemService;
        this.readerConfigService = readerConfigService;
        add(new H2("Nuevo elemento etiquetado"));
        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.BASELINE);
        displayForm();
    }

    public void setEpc(String epcText) {
        epc.setValue(epcText);
    }

    private void displayForm() {
        instantiateInputs();
        VerticalLayout form = new VerticalLayout();
        form.setWidth("50%");
        form.setJustifyContentMode(JustifyContentMode.BETWEEN);
        form.add(name, description, information, epc, save);
        VerticalLayout imageLayout = new VerticalLayout();
        imageLayout.setWidth("50%");
        imageLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        configureImageLayout(imageLayout);
        imageLayout.add(upload);
        HorizontalLayout completeForm = new HorizontalLayout(form, imageLayout);
        completeForm.setJustifyContentMode(JustifyContentMode.BETWEEN);
        completeForm.setAlignItems(Alignment.BASELINE);
        add(completeForm);
    }

    private void instantiateInputs() {
        name = new TextField("Nombre comercial");
        name.setRequired(true);
        name.setRequiredIndicatorVisible(true);

        description = new TextField("Descripción");
        information = new TextArea("Información extra");
        information.setSizeFull();
        epc = new TextField("EPC (acerca la etiqueta al lector)");
        epc.setRequired(true);
        epc.setWidth(300, PIXELS);
        epc.setRequiredIndicatorVisible(true);
        save = new Button("Guardar");
        setSaveButtonBehaviour();

    }

    private void setSaveButtonBehaviour() {
        save.addClickListener(event -> {
            if (!isNameNullOrEmpty() && !isEpcNullOrEmpty()) {
                Item item = new Item();
                item.setName(name.getValue());
                item.setDescription(description.getValue());
                item.setInformation(information.getValue());
                item.setEPC(epc.getValue());
                item.setInShelf(false);
                item.setItemImage(itemImage);
                this.itemService.add(item);
                clearFields();
                Notification.show("El item ha sido ingresado correctamente", 5000, MIDDLE);
            } else {
                Notification.show("Completa los campos 'Nombre comercial' y 'EPC'", 5000, MIDDLE);
            }
        });
    }

    private boolean isNameNullOrEmpty() {
        return name.getValue() == null || name.getValue().trim().isEmpty();
    }

    private boolean isEpcNullOrEmpty() {
        return epc.getValue() == null || epc.getValue().trim().isEmpty();
    }

    private void configureImageLayout(VerticalLayout imageLayout) {
        MemoryBuffer buffer = new MemoryBuffer();
        upload = new Upload(buffer);
        int maxFileSizeInBytes = 1024 * 1024; // 1MB
        upload.setMaxFileSize(maxFileSizeInBytes);
        upload.setAcceptedFileTypes("application/png", ".png", "application/jpg", ".jpg");
        UploadExamplesI18N i18N = new UploadExamplesI18N();
        upload.setI18n(i18N);
        uploadSuccessful(buffer, imageLayout);
    }

    private void uploadSuccessful(MemoryBuffer buffer, VerticalLayout imageLayout) {
        upload.addSucceededListener(event -> {
            try {
                if (image != null) imageLayout.remove(image);
                itemImage = new ItemImage();
                itemImage.setData(IOUtils.toByteArray(buffer.getInputStream()));
                itemImage.setFilename(event.getFileName());
                itemImage.setMimeType(event.getMIMEType());
                StreamResource resource = new StreamResource(itemImage.getFilename(),
                        () -> new ByteArrayInputStream(itemImage.getData()));
                image = new Image(resource, itemImage.getFilename());
                image.setMaxHeight(250, PIXELS);
                image.setMaxWidth(250, PIXELS);
                imageLayout.add(image);
            } catch (Exception e) {
                Notification.show(e.getMessage(), 5000, MIDDLE);
            }
        });
    }

    private void clearFields() {
        UI.getCurrent().getPage().reload();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        RfidUseCase rfidUseCase = new ItemCreation(attachEvent.getUI(), this);
        try {
            if (readerConfiguration == null) readerConfiguration = readerConfigService.getLastReader();
            rfidUseCase.executeUseCase();
        } catch (OctaneSdkException e) {
            Notification.show(e.getMessage(), 5000, MIDDLE);
        }
    }
}