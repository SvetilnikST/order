package pst.asu.beans.files;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pst.asu.beans.department.TblDepartmentEntity;
import pst.asu.beans.files.domain.FilesEntity;
import pst.asu.beans.order.OrderDAOBean;
import pst.asu.beans.order.domain.OrderEntity;
import pst.asu.beans.user.UserBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
public class FileBean implements Serializable {

        private final String PATH_TO_TMP_FILE = "d:/4/tmp/";
    private final String PATH_TO_SAVE_AUDIO_FILE = "d:/4/audio/";
    private final String PATH_TO_SAVE_VIDEO_FILE = "d:/4/video/";
//    private final String PATH_TO_TMP_FILE = "/var/www/vhost/www/html/files/tmp/";
//    private final String PATH_TO_SAVE_AUDIO_FILE = "/var/www/vhost/www/html/files/";
//    private final String PATH_TO_SAVE_VIDEO_FILE = "/var/www/vhost/www/html/files/video/";

    private FilesEntity filesEntity;

    @EJB
    private FilesDAOBean filesDAOBean;

    @EJB
    private OrderDAOBean orderDAOBean;

    @Inject
    private UserBean userBean;
    private Integer id;
    private String name;
    private List<String> fileNameToShow = new ArrayList<>();
    private List<String> fileNameToSave = new ArrayList<>();
    TblDepartmentEntity departmentEntity;
    private String officer;
    private Timestamp created_at;
    private Boolean canBeDelete;
    private String comment;
    private Date dateIssuance;
    private Boolean markDirector;
    private Boolean markEngineer;
    private Boolean markToDelete;
    private OrderEntity orderEntity;
    private Integer type;
    private List<OrderEntity> orderEntityList;


    @PostConstruct
    public void init() {
        //TODO потом перенести в правильное место
        orderEntityList = orderDAOBean.readList();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();
        String paramType = parameterMap.get("type");
        if (paramType.equals("audio")) {
            type = 10;
        }
        if (paramType.equals("video")) {
            type = 20;
        }
        }

    public List<OrderEntity> getOrderEntityList() {
        return orderEntityList;
    }

    public void setOrderEntityList(List<OrderEntity> orderEntityList) {
        this.orderEntityList = orderEntityList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TblDepartmentEntity getDepartmentEntity() {
        return departmentEntity;
    }

    public void setDepartmentEntity(TblDepartmentEntity departmentEntity) {
        this.departmentEntity = departmentEntity;
    }

    public String getOfficer() {
        return officer;
    }

    public void setOfficer(String officer) {
        this.officer = officer;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Boolean getCanBeDelete() {
        return canBeDelete;
    }

    public void setCanBeDelete(Boolean canBeDelete) {
        this.canBeDelete = canBeDelete;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDateIssuance() {
        return dateIssuance;
    }

    public void setDateIssuance(Date dateIssuance) {
        this.dateIssuance = dateIssuance;
    }

    public Boolean getMarkDirector() {
        return markDirector;
    }

    public void setMarkDirector(Boolean markDirector) {
        this.markDirector = markDirector;
    }

    public Boolean getMarkEngineer() {
        return markEngineer;
    }

    public void setMarkEngineer(Boolean markEngineer) {
        this.markEngineer = markEngineer;
    }

    public Boolean getMarkToDelete() {
        return markToDelete;
    }

    public void setMarkToDelete(Boolean markToDelete) {
        this.markToDelete = markToDelete;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<String> getFileNameToShow() {
        return fileNameToShow;
    }

    public void setFileNameToShow(List<String> fileNameToShow) {
        this.fileNameToShow = fileNameToShow;
    }

    public List<String> getFileNameToSave() {
        return fileNameToSave;
    }

    public void setFileNameToSave(List<String> fileNameToSave) {
        this.fileNameToSave = fileNameToSave;
    }

    public void load(FilesEntity filesEntity) {
//        this.setId(filesEntity.getId());
//        this.setName(filesEntity.getName());
//        this.setDepartmentEntity(filesEntity.getDepartmentEntity());
//        this.setOfficer(filesEntity.getOfficer());
//        this.setCreated_at(filesEntity.getCreated_at());
//        this.setCanBeDelete(filesEntity.getCanBeDelete());
//        this.setComment(filesEntity.getComment());
//        this.setDateIssuance(filesEntity.getDateIssuance());
//        this.setMarkDirector(filesEntity.getMarkDirector());
//        this.setMarkEngineer(filesEntity.getMarkEngineer());
//        this.setMarkToDelete(filesEntity.getMarkToDelete());
//        this.setOrderEntity(filesEntity.getOrderEntity());
//        this.setType(filesEntity.getType());
    }

    private Timestamp timestampFromDate(Date fromDate) {
        return new Timestamp(fromDate.getTime());
    }

    private Timestamp timstampFromInt(int valueToTimestamp) {
        Long temp = valueToTimestamp * 1000L;
        return new Timestamp(temp);
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf(".") );
        } catch (Exception e) {
            return "";
        }
    }

    private String getFileSizeString(Long fileSize) {
        String stFileSize = "";
        double fileSizeTmp = 0;
        //кило  мега  гиг
        if (fileSize > 1024 * 1024 * 1024) {
            fileSizeTmp = fileSize / (1024 * 1024 * 1024.0);
            stFileSize = String.format("%.1f Гб", fileSizeTmp);
        } else if (fileSize > 1024 * 1024) {
            fileSizeTmp = fileSize / (1024 * 1024.0);
            stFileSize = String.format("%.1f Мб", fileSizeTmp);
        } else if (fileSize > 1024) {
            fileSizeTmp = fileSize / (1024.0);
            stFileSize = String.format("%.1f Кб", fileSizeTmp);
        } else {
            stFileSize = String.format("%.1f Б", fileSizeTmp);
        }
        return stFileSize;
    }


    public void handleFileUpload(FileUploadEvent event) throws IOException {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        UploadedFile file11 = event.getFile();

        Path path = Paths.get(PATH_TO_TMP_FILE);
        String fileExtension = getFileExtension(file11.getFileName());

        if (type == 20) {
            path = Paths.get(PATH_TO_TMP_FILE + "video/");
        }

        String filenameReal = "";
        String filenameTemp = "";


        Path file = Files.createTempFile(path, "", fileExtension);//  FilenameUtils.getBaseName(uploadedFile.getName());
        filenameTemp = file.getFileName().toString();
        Long fileSize = file11.getSize();
        String stFileSize = getFileSizeString(fileSize);

        try (InputStream input = file11.getInputstream();) {
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            filenameReal = file11.getFileName() + " (" + stFileSize + ")";

            this.fileNameToShow.add(filenameReal);
            this.fileNameToSave.add(filenameTemp);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String save() throws IOException {
//        Path pathTmp = Paths.get(PATH_TO_TMP_FILE);
//        Path videoPath = Paths.get(PATH_TO_SAVE_VIDEO_FILE);
        CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
        };
        String fileNames = "";
        String filePathToSave = "";
        String tmpPath=PATH_TO_TMP_FILE ;
        if (type == 10) {
            //аудиофайлы
            filePathToSave = PATH_TO_SAVE_AUDIO_FILE;
        } else if (type == 20) {
            //Видеофайлы
            filePathToSave = PATH_TO_SAVE_VIDEO_FILE;
            tmpPath = PATH_TO_TMP_FILE + "video/";
        } else {

            //это жесткое выкидывание из ошибки - не определен тип
            return "listFiles.xhtml?faces-redirect=true&type=video";
        }

        for (String fileNameToProcess : fileNameToSave) {
            Path FROM = Paths.get(tmpPath + fileNameToProcess);
            Path TO = Paths.get(filePathToSave + fileNameToProcess);
            Files.move(FROM, TO);
            fileNames = fileNames + fileNameToProcess + ",";
        }

        fileNames = fileNames.substring(0, fileNames.length() - 1);
        FilesEntity filesEntity = new FilesEntity();
        filesEntity.setName(fileNames);
        filesEntity.setOfficer(this.getOfficer());
        filesEntity.setComment(this.getComment());
        filesEntity.setCanBeDelete(true);
        filesEntity.setMarkToDelete(true);


        filesEntity.setDateIssuance(timestampFromDate(this.dateIssuance));

        filesEntity.setType(this.getType());
        filesEntity.setDepartmentEntity(userBean.getDepartmentEntity());
        filesEntity.setOrderEntity(this.getOrderEntity());

        filesDAOBean.create(filesEntity);

        String stType = "";
        if (type == 10) {
            stType = "audio";
        } else if (type == 20) {
            stType = "video";
        }

        return "listFiles.xhtml?faces-redirect=true&type=" + stType;
    }

    public String remove() throws IOException {
        String tmpPath=PATH_TO_TMP_FILE ;

        String stType = "";
        if (type == 10) {
            stType = "audio";
        } else if (type == 20) {
            stType = "video";
            tmpPath = PATH_TO_TMP_FILE + "video/";
        }

        for (String fileNameToProcess : fileNameToSave) {
            Path FROM = Paths.get(tmpPath + fileNameToProcess);
            Files.delete(FROM);
        }
        return "listFiles.xhtml?faces-redirect=true&type=" + stType;
    }
}
