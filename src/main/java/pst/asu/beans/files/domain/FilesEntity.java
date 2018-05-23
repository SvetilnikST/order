package pst.asu.beans.files.domain;


import pst.asu.beans.department.TblDepartmentEntity;
import pst.asu.beans.order.domain.OrderEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "`file`")
public class FilesEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    @Column(name = "name", length = 500, nullable = false)
    private String name;

//    @Transient
//    private ArrayList<String> nameal;

    @Basic
    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "comment", length = 255, nullable = false)
    private String comment;

    @Column(name = "can_be_delete", columnDefinition = "BIT", length = 1,nullable = false)
    public Boolean canBeDelete;

    @Column(name = "officer", length = 50, nullable = false)
    private String officer;

    @Column(name = "date_issuance",nullable = true)
    private Timestamp dateIssuance;

    @Column(name = "markDirector", columnDefinition = "BIT", length = 1)
    private Boolean markDirector;

    @Column(name = "markTB", columnDefinition = "BIT", length = 1)
    private Boolean markEngineer;

    @Column(name = "markToDelete", columnDefinition = "BIT", length = 1)
    public Boolean markToDelete;

    @Column(name = "type")
    private int type=10;

    @ManyToOne
    @JoinColumn(name = "department")
    private TblDepartmentEntity departmentEntity;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = true)
    private OrderEntity orderEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public ArrayList<String> getNameal() {
//
//        return this.nameal;
//    }
//
//    public void setNameal() {
//        String [] oneName;
//        oneName = name.split(",");
//        nameal= new ArrayList<>();
//        for (String one: oneName) {
//            nameal.add(one);
//        }
//    }

    public List<String> myList() {
        String [] oneName;
        oneName = name.split(",");
        List<String> mylist= new ArrayList<>();
        for (String one: oneName) {
            mylist.add(one);
        }
        return mylist;
    }


    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getCanBeDelete() {
        return canBeDelete;
    }

    public void setCanBeDelete(Boolean canBeDelete) {
        this.canBeDelete = canBeDelete;
    }

    public String getOfficer() {
        return officer;
    }

    public void setOfficer(String officer) {
        this.officer = officer;
    }

    public Timestamp getDateIssuance() {
        return dateIssuance;
    }

    public void setDateIssuance(Timestamp dateIssuance) {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public TblDepartmentEntity getDepartmentEntity() {
        return departmentEntity;
    }

    public void setDepartmentEntity(TblDepartmentEntity departmentEntity) {
        this.departmentEntity = departmentEntity;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }
}
