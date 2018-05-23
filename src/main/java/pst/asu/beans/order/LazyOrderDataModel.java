package pst.asu.beans.order;


import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import pst.asu.beans.department.DepartmentDAOBean;
import pst.asu.beans.department.TblDepartmentEntity;
import pst.asu.beans.order.domain.OrderEntity;
import pst.asu.beans.user.UserBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Timestamp;
import java.util.*;

/**
 * Dummy implementation of LazyDataModel that uses a list to mimic a real datasource like a database.
 */
//@Named
//@ManagedBean(name = "OrderDataModel")
//@RequestScoped
@javax.faces.bean.ManagedBean(name = "dtLazyOrderDataModel")
@javax.faces.bean.ViewScoped
public class LazyOrderDataModel extends LazyDataModel<OrderEntity> {

    @EJB
    private OrderDAOBean orderDAOBean;

    @EJB
    private DepartmentDAOBean departmentDAOBean;

    @Inject
    private UserBean userBean;

    public OrderEntity selectedOrder;

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

    @PostConstruct
    public void init()
    {
        //
//        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        Date date = dateFormat.parse("23/09/2007");
//        long time = date.getTime();
//        new Timestamp(time);

        this.dateFrom = java.sql.Timestamp.valueOf("2007-09-23 00:00:00.0");
        this.dateLast = java.sql.Timestamp.valueOf("2018-09-23 10:10:10.0");
        this.dateFrom1 = new Date(dateFrom.getTime());
        this.dateLast1 = new Date(dateLast.getTime());

        departmentList = departmentDAOBean.readList();
        if(userBean.getDepartmentEntity()!=null) {
            selectedDepartment = String.valueOf(userBean.getDepartmentEntity().getIdDepartment());
        }else {
            selectedDepartment="20";
        }

        //options
        hours = new ArrayList<String>();
        for(int i = 0; i < 24; i++) {
            hours.add(Integer.toString(i));
        }

        minutes = new ArrayList<String>();
        for(int i = 0; i < 60; i=i+5) {
            minutes.add(Integer.toString(i));
        }
        hourFrom="0";
        minuteFrom="0";
        hourLast="23";
        minuteLast="55";
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


    public void togleDuring(OrderEntity orderEntity){
        if(!userBean.doRightVerify("changeStatus")){
            return;
        }
        orderEntity.during = !orderEntity.during;
        if(orderEntity.during){
            orderEntity.setStatus((short)100);
        }else {
            orderEntity.setStatus((short)10);
        }
        orderDAOBean.update(orderEntity);
    }

    //общая функция для переключения разрешений директора или гл.инженера
    public void togleMarkPermissionDirector(OrderEntity orderEntity){

        Short mark;

        if(userBean.doRightVerify("markDirector")){
            mark = orderEntity.getMarkDirector();
            mark=permission(mark);
            orderEntity.setMarkDirector(mark);
            orderDAOBean.update(orderEntity);
        }
//        if(userBean.doRightVerify("markGlingener")){
//            mark = orderEntity.getMarkEngineer();
//            mark=permission(mark);
//            orderEntity.setMarkEngineer(mark);
//            orderDAOBean.update(orderEntity);
//        }
    }

    public void togleMarkPermissionEngineer(OrderEntity orderEntity){

        Short mark;

//        if(userBean.doRightVerify("markDirector")){
//            mark = orderEntity.getMarkDirector();
//            mark=permission(mark);
//            orderEntity.setMarkDirector(mark);
//            orderDAOBean.update(orderEntity);
//        }
        if(userBean.doRightVerify("markGlingener")){
            mark = orderEntity.getMarkEngineer();
            mark=permission(mark);
            orderEntity.setMarkEngineer(mark);
            orderDAOBean.update(orderEntity);
        }
    }

    //локальная функция для переключения разрешений директора или гл инженера
    private Short permission(Short mark){
        if(mark==null || mark==0){
            mark=10;
        }else if (mark==10){
            mark=20;
        }else if (mark==20){
            mark=10;
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
        int a=0;
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


    public OrderEntity getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(OrderEntity selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

//    LazyOrderDataModel(){
////        orderDAOBean = new OrderDAOBean();
//        int a=0;
//    }

    @Override
    public OrderEntity getRowData(String rowKey) {

        OrderEntity orderEntity = orderDAOBean.read(Integer.parseInt(rowKey));

        return orderEntity;
    }

    @Override
    public Object getRowKey(OrderEntity orderEntity) {
        return orderEntity.getId();
    }

    @Override
    public List<OrderEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        int intStatus = Bol2Int(button1)*100+Bol2Int(button2)*10+Bol2Int(button3);


        String status = Integer.toString(intStatus) ;


        filters.put("status", status);
        filters.put("dateFrom",dateFrom.toString());
        filters.put("dateLast",dateLast.toString());
        filters.put("department", selectedDepartment);

        setRowCount(orderDAOBean.getTotalCount(filters));

        return orderDAOBean.load(first, pageSize, sortField, sortOrder, filters);
    }
}
