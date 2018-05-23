package pst.asu.beans.order;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import pst.asu.beans.department.TblDepartmentEntity;
import pst.asu.beans.order.domain.OrderEntity;
import pst.asu.beans.user.UserBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@ManagedBean
@ViewScoped
public class OrderBean {
    private OrderEntity orderEntity;

    @EJB
    private OrderDAOBean orderDAOBean;

    @Inject
    private UserBean userBean;
    private Integer id;
    @NotEmpty
    private String number;
    @NotEmpty
    private String name;
    //    private int department;
    TblDepartmentEntity departmentEntity;
    @NotEmpty
    private String officer;
    @NotNull
    private Date dateIssuance;
    @NotNull
    private Date startWork;
    @NotNull
    private Date finishWork;
    private Date created_at;
    private Date updated_at;
    private Boolean during;
    private Date completeness;
    private Short markDirector;
    private Short markEngineer;
    private Short status;

    @PostConstruct
    public void init() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();
        String param = parameterMap.get("idOrder");

        if (param != null) {
            id = Integer.parseInt(param);

            orderEntity = orderDAOBean.read(id);

            if (orderEntity == null) {
                String message = "Внимание, Ошибка. Записи с таким номером не существует.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                //TODO сделать редирект
            } else {
                load(orderEntity);
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    //    public int getDepartment() {
//        return department;
//    }
//
//    public void setDepartment(int department) {
//        this.department = department;
//    }

    public String getOfficer() {
        return officer;
    }

    public void setOfficer(String officer) {
        this.officer = officer;
    }

//    public int getCreated_at() {
//        return created_at;
//    }
//
//    public void setCreated_at(int created_at) {
//        this.created_at = created_at;
//    }
//
//    public int getUpdated_at() {
//        return updated_at;
//    }
//
//    public void setUpdated_at(int updated_at) {
//        this.updated_at = updated_at;
//    }


    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Boolean getDuring() {
        return during;
    }

    public void setDuring(Boolean during) {
        this.during = during;
    }

    public Short getMarkDirector() {
        return markDirector;
    }

    public void setMarkDirector(Short markDirector) {
        this.markDirector = markDirector;
    }

    public Short getMarkEngineer() {
        return markEngineer;
    }

    public void setMarkEngineer(Short markEngineer) {
        this.markEngineer = markEngineer;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getDateIssuance() {
        return dateIssuance;
    }

    public void setDateIssuance(Date dateIssuance) {
        this.dateIssuance = dateIssuance;
    }

    public Date getStartWork() {
        return startWork;
    }

    public void setStartWork(Date startWork) {
        this.startWork = startWork;
    }

    public Date getFinishWork() {
        return finishWork;
    }

    public void setFinishWork(Date finishWork) {
        this.finishWork = finishWork;
    }

    public Date getCompleteness() {
        return completeness;
    }

    public void setCompleteness(Date completeness) {
        this.completeness = completeness;
    }

    public String save() {
        orderEntity = orderDAOBean.read(this.id);
        if (orderEntity == null) {
            orderEntity = new OrderEntity();
        }
        orderEntity.setNumber(this.number);
        orderEntity.setName(this.name);
        orderEntity.setOfficer(this.officer);
        orderEntity.setDateIssuance(timestampFromDate(this.dateIssuance));
        orderEntity.setStartWork(timestampFromDate(this.startWork));
        orderEntity.setFinishWork(timestampFromDate(this.finishWork));
        if (this.completeness != null) {
            orderEntity.setCompleteness(timestampFromDate(this.completeness));
        }
        orderEntity.setMarkDirector(this.markDirector);
        orderEntity.setMarkEngineer(this.markEngineer);
        orderEntity.setStatus(this.status);
        if (orderEntity.getId() == 0) {
            orderEntity.setDuring(false);
            orderEntity.setStatus((short) 10);
            departmentEntity = userBean.getDepartmentEntity();
            orderEntity.setDepartmentEntity(departmentEntity);
            orderDAOBean.create(orderEntity);
        } else {
            if (this.completeness != null) {
                orderEntity.setDuring(false);
                orderEntity.setCompleteness(timestampFromDate(this.completeness));
                orderEntity.setStatus((short) 1);
            } else {
                orderEntity.setCompleteness(null);
                if (during) {
                    orderEntity.setStatus((short) 100);
                } else {
                    orderEntity.setStatus((short) 10);
                }
            }
            orderDAOBean.update(orderEntity);
        }
        return "viewOrder.xhtml?faces-redirect=true&idOrder=" + String.valueOf(orderEntity.getId());
    }

    public void load(OrderEntity orderEntity) {
        this.setId(orderEntity.getId());
        this.setNumber(orderEntity.getNumber());
        this.setName(orderEntity.getName());
        this.setDepartmentEntity(orderEntity.getDepartmentEntity());
        this.setOfficer(orderEntity.getOfficer());
        this.setDateIssuance(timestampFromDate(orderEntity.getDateIssuance()));
        this.setStartWork(timestampFromDate(orderEntity.getStartWork()));
        this.setFinishWork(timestampFromDate(orderEntity.getFinishWork()));
        this.setDuring(false);
        if (orderEntity.getCompleteness() != null) {
            this.setCompleteness(timestampFromDate(orderEntity.getCompleteness()));
        }
        this.setMarkDirector(orderEntity.getMarkDirector());
        this.setMarkEngineer(orderEntity.getMarkEngineer());
        this.setStatus(orderEntity.getStatus());
        this.setCreated_at(timstampFromInt(orderEntity.getCreated_at()));
        this.setUpdated_at(timstampFromInt(orderEntity.getUpdated_at()));
    }

    private Timestamp timestampFromDate(Date fromDate) {
        return new Timestamp(fromDate.getTime());
    }

    private Timestamp timstampFromInt(int valueToTimestamp) {
        Long temp = valueToTimestamp * 1000L;
        return new Timestamp(temp);
    }

    public void validateDateOrder(FacesContext context, UIComponent comp,
                                  Object value) {

        if (value!=null) {
            Date field = (Date) value;
            Date curDate = new Date();
            if (field.getTime() > curDate.getTime()) {
                ((UIInput) comp).setValid(false);

                FacesMessage message = new FacesMessage(
                        "Дата не может быть меньше текущей");
                context.addMessage(comp.getClientId(context), message);
            }
        }
    }
}
