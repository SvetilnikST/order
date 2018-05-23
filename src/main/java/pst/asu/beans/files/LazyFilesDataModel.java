package pst.asu.beans.files;


import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import pst.asu.beans.department.DepartmentDAOBean;
import pst.asu.beans.department.TblDepartmentEntity;
import pst.asu.beans.files.domain.FilesEntity;
import pst.asu.beans.user.UserBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Dummy implementation of LazyDataModel that uses a list to mimic a real datasource like a database.
 */
//@Named
//@ManagedBean(name = "OrderDataModel")
//@RequestScoped
@ManagedBean(name = "dtLazyFilesDataModel")
@javax.faces.bean.ViewScoped
public class LazyFilesDataModel extends LazyDataModel<FilesEntity> {

//    private final String PATH_TO_VIDEO = "http://www.pst.vitebsk.energo.net/files/video/";
    private final String PATH_TO_VIDEO = "/order/show.xhtml?id=2&filename=";
    private final String PATH_TO_AUDIO = "http://www.pst.vitebsk.energo.net/files/";

    @EJB
    private FilesDAOBean filesDAOBean;

    @EJB
    private DepartmentDAOBean departmentDAOBean;

    @Inject
    private UserBean userBean;

//    public OrderEntity selectedOrder;

    @Inject
    private FilesEntity filesEntity;

    private String type;

    private Boolean button1 = true;
    private Boolean button2 = true;
    private Boolean button3 = true;

    private Timestamp dateFrom;
    private Timestamp dateLast;
    private Date dateFrom1;
    private Date dateLast1;
    private List<TblDepartmentEntity> departmentList;
    private String selectedDepartment;

    private List<String> hours;
    private List<String> minutes;
    private String hourFrom;
    private String hourLast;
    private String minuteFrom;
    private String minuteLast;

    private String button1Style = "background-color:#449d44; color: #fff; border-color: #398439; text-shadow: none;";
    private String button2Style = "background-color:#c9302c; color: #fff; border-color: #ac2925; text-shadow: none;";
    private String button3Style = "background-color:#31b0d5; color: #fff; border-color: #269abc; text-shadow: none;";
    private String styleYellow = "background-color:#f0ad4e; color: #fff; border-color: #269abc; text-shadow: none;";

    private String filename;

//    public void initType() {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        ExternalContext externalContext = facesContext.getExternalContext();
//        Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();
//        type = parameterMap.get("type");
//
//        if (type == null) {
//            String message = "Bad request. Please use a link from within the system.";
//            FacesContext.getCurrentInstance().addMessage(null,
//                    new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
//            return;
//        }
//        if(!type.equals("audio") && !type.equals("video") ){
//            type="audio";
//        }
//    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();
        type = parameterMap.get("type");

        if (type == null) {
            String message = "Bad request. Please use a link from within the system.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            return;
        }
        if (!type.equals("audio") && !type.equals("video")) {
            type = "audio";
        }
    }

    public String getFilename() {
        if (type.equals("audio")) {
            this.filename = PATH_TO_AUDIO;
        }
        return filename;
    }

    public void makeUrl(String name) {

        if (type.equals("audio")) {
            this.filename = PATH_TO_AUDIO + name;
        }
        if (type.equals("video")) {
            this.filename = PATH_TO_VIDEO + name;

            PrimeFaces.current().executeScript("PF('Video').show();");
//            RequestContext context = RequestContext.getCurrentInstance();
//
//            context.execute("PF('Video').show();");
        }

//        this.filename = "/order/show.xhtml?id=2&filename="+name;
    }

    public String makeUrlAudio(String name) {

        if (type.equals("audio")) {
            this.filename = PATH_TO_AUDIO + name;
        }

        return filename;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private int Bol2Int(Boolean myBoolean) {
        return (myBoolean) ? 1 : 0;
    }

    public List<String> getHours() {
        return hours;
    }

    public void setHours(List<String> hours) {
        this.hours = hours;
    }

    public List<String> getMinutes() {
        return minutes;
    }

    public void setMinutes(List<String> minutes) {
        this.minutes = minutes;
    }

    public String getHourFrom() {
        return hourFrom;
    }

    public void setHourFrom(String hourFrom) {
        this.hourFrom = hourFrom;
    }

    public String getHourLast() {
        return hourLast;
    }

    public void setHourLast(String hourLast) {
        this.hourLast = hourLast;
    }

    public String getMinuteFrom() {
        return minuteFrom;
    }

    public void setMinuteFrom(String minuteFrom) {
        this.minuteFrom = minuteFrom;
    }

    public String getMinuteLast() {
        return minuteLast;
    }

    public void setMinuteLast(String minuteLast) {
        this.minuteLast = minuteLast;
    }

    public String getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(String selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }

    /////////////////////////////////////////////////////////////////////////////

    public Date getDateFrom1() {
        return dateFrom1;
    }

    public void setDateFrom1(Date dateFrom1) {
        this.dateFrom1 = dateFrom1;
    }

    public Date getDateLast1() {
        return dateLast1;
    }

    public void setDateLast1(Date dateLast1) {
        this.dateLast1 = dateLast1;
    }

    //общая функция для переключения разрешений директора или гл.инженера
    public void togleMarkPermissionDirector(FilesEntity filesEntity) {
        Boolean mark;

        if (userBean.doRightVerify("markDirector") || userBean.doRightVerify("markGlingener")) {
            mark = filesEntity.getMarkDirector();
            mark = permission(mark);
            filesEntity.setMarkDirector(mark);
            filesDAOBean.update(filesEntity);
        }
    }

    //общая функция для переключения разрешений директора или гл.инженера
    public void togleMarkPermissionEngineer(FilesEntity filesEntity) {
        Boolean mark;

        if (userBean.doRightVerify("markTB")) {
            mark = filesEntity.getMarkEngineer();
            mark = permission(mark);
            filesEntity.setMarkEngineer(mark);
            filesDAOBean.update(filesEntity);
        }
    }

    //общая функция для переключения разрешений директора или гл.инженера
    public void togleMarkPermissionDelete(FilesEntity filesEntity) {
        Boolean mark;
//это имеют право делать только те, у кого есть права директора или гл.инженера. или тб
        if (userBean.doRightVerify("markTB")
                || userBean.doRightVerify("markDirector")
                || userBean.doRightVerify("markGlingener")) {
            mark = filesEntity.getMarkToDelete();
            mark = permission(mark);
            filesEntity.setMarkToDelete(mark);
            filesDAOBean.update(filesEntity);
        }
    }


    //
//    //локальная функция для переключения разрешений директора или гл инженера
    private Boolean permission(Boolean mark) {
        if (mark == null || !mark) {
            mark = true;
        } else if (mark) {
            mark = false;
        }
        return mark;
    }


    /////////////////////////////////////////////////////////////////////////////


    public List<TblDepartmentEntity> getDepartmentList() {
        return departmentList;
    }

    public Timestamp getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Timestamp dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Timestamp getDateLast() {
        return dateLast;
    }

    public void setDateLast(Timestamp dateLast) {
        this.dateLast = dateLast;
    }

    public String getButton1Style() {
        String ret = "";
        if (button1) {
            ret = button1Style;
        }
        return ret;
    }

    public void setButton1Style(String button1Style) {
        this.button1Style = button1Style;
    }

    public String getButton2Style() {
        String ret = "";
        if (button2) {
            ret = button2Style;
        }
        return ret;
    }

    public void setButton2Style(String button2Style) {
        this.button2Style = button2Style;
    }

    public String getButton3Style() {
        String ret = "";
        if (button3) {
            ret = button3Style;
        }
        return ret;
    }

    public void setButton3Style(String button3Style) {
        this.button3Style = button3Style;
    }

    public String getStyleYellow() {
        return styleYellow;
    }

    public void setStyleYellow(String styleYellow) {
        this.styleYellow = styleYellow;
    }

    public void onDateSelect(SelectEvent event) {
        int a = 0;
    }


    public void buttonAction1(ActionEvent actionEvent) {
        String buttonId = actionEvent.getComponent().getId();
//        switch (actionEvent.getSource().toString())
        if (buttonId.equals("btn1")) {
            button1 = !button1;
        }
        if (buttonId.equals("btn2")) {
            button2 = !button2;
        }
        if (buttonId.equals("btn3")) {
            button3 = !button3;
        }
    }

    public Boolean getButton1() {
        return button1;
    }

    public void setButton1(Boolean button1) {
        this.button1 = button1;
    }

    public Boolean getButton2() {
        return button2;
    }

    public void setButton2(Boolean button2) {
        this.button2 = button2;
    }

    public Boolean getButton3() {
        return button3;
    }

    public void setButton3(Boolean button3) {
        this.button3 = button3;
    }

    private List<String> selectedOptionsState;

    public List<String> getSelectedOptionsState() {
        return selectedOptionsState;
    }

    public void setSelectedOptionsState(List<String> selectedOptionsState) {
        this.selectedOptionsState = selectedOptionsState;
    }

    @Override
    public List<FilesEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        filters.put("typeFilter", type);

        setRowCount(filesDAOBean.getTotalCount(filters));

        List<FilesEntity> rez = filesDAOBean.load(first, pageSize, sortField, sortOrder, filters);

        return rez;
    }
}
