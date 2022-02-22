package com.example.application.backend.common;

import com.vaadin.flow.component.upload.UploadI18N;

import java.util.Arrays;

public class UploadExamplesI18N extends UploadI18N {
    public UploadExamplesI18N() {
        setDropFiles(new DropFiles()
                .setOne("Arrastrar archivo aquí")
                .setMany("Arrastrar archivos aquí"));
        setAddFiles(new AddFiles()
                .setOne("Subir archivo...")
                .setMany("Subir archivos..."));
        setCancel("Cancelar");
        setError(new Error()
                .setTooManyFiles("Demasiados archivos.")
                .setFileIsTooBig("El archivo es demasiado grande.")
                .setIncorrectFileType("Tipo de archivo incorrecto."));
        setUploading(new Uploading()
                .setStatus(new Uploading.Status()
                        .setConnecting("Conectando...")
                        .setStalled("Stalled")
                        .setProcessing("Procesando archivo...")
                        .setHeld("En cola"))
                .setRemainingTime(new Uploading.RemainingTime()
                        .setPrefix("tiempo restante: ")
                        .setUnknown("tiempo restante desconocido"))
                .setError(new Uploading.Error()
                        .setServerUnavailable("Subida fallida, por favor intente de nuevo más tarde")
                        .setUnexpectedServerError("Subida fallida debido a algún problema del servidor")
                        .setForbidden("Subida prohibida")));
        setUnits(new Units()
                .setSize(Arrays.asList("B", "kB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB")));
    }
}
