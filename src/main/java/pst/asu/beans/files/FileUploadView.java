package pst.asu.beans.files;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static java.nio.file.Files.createTempFile;

@ManagedBean
public class FileUploadView {

    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() {
        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        UploadedFile file11 = event.getFile();

        Path path = Paths.get("/opt/1todel/");
        Path file = Files.createTempFile(Paths.get("/opt/1todel") ,"",".mp4");//  FilenameUtils.getBaseName(uploadedFile.getName());

        try (InputStream input = file11.getInputstream();) {
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
        }
//        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}