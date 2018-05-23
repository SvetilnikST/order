package pst.asu.beans.order.domain;


import pst.asu.beans.department.TblDepartmentEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "`order`")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    @Column (name = "number",length = 10,nullable = false)
    private String number;

    @Column(name = "name", length = 320, nullable = false)
    private String name;

//    @Column(name = "department",  nullable = false)
//    private int department;

    @Column(name = "officer", length = 50, nullable = false)
    private String officer;

    @Column(name = "dateIssuance",nullable = true)
    private Timestamp dateIssuance;

    @Transient
    private String stDateIssuance;

    @Column(name = "startWork",nullable = true)
    private Timestamp startWork;

    @Column(name = "finishWork",nullable = true)
    private Timestamp finishWork;

    @Basic
    @Column(name = "created_at", nullable = false)
    private int created_at;

    @Basic
    @Column(name = "updated_at", nullable = false)
    private int updated_at;

    @Column(name = "during", columnDefinition = "BIT", length = 1)
    public Boolean during;

    @Column(name = "completeness", nullable = true)
    private Timestamp completeness;

    @Column(name = "markDirector")
    private Short markDirector;

    @Column(name = "markEngineer")
    private Short markEngineer;

    @Column(name = "status")
    private Short status;

    @ManyToOne
    @JoinColumn(name = "department")
    private TblDepartmentEntity departmentEntity;

    public TblDepartmentEntity getDepartmentEntity() {
        return departmentEntity;
    }

    public void setDepartmentEntity(TblDepartmentEntity departmentEntity) {
        this.departmentEntity = departmentEntity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Timestamp getDateIssuance() {
        return dateIssuance;
    }

    public void setDateIssuance(Timestamp dateIssuance) {
        this.dateIssuance = dateIssuance;
    }

    public String getStDateIssuance() {
            this.stDateIssuance=new SimpleDateFormat("dd.MM.yyyy").format(this.dateIssuance);

        return stDateIssuance;
    }

    public void setStDateIssuance(String stDateIssuance) {
        this.stDateIssuance = stDateIssuance;
    }

    public Timestamp getStartWork() {
        return startWork;
    }

    public void setStartWork(Timestamp startWork) {
        this.startWork = startWork;
    }

    public Timestamp getFinishWork() {
        return finishWork;
    }

    public void setFinishWork(Timestamp finishWork) {
        this.finishWork = finishWork;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }

    public Boolean getDuring() {
        return during;
    }

    public void setDuring(Boolean during) {
        this.during = during;
    }

    public Timestamp getCompleteness() {
        return completeness;
    }

    public void setCompleteness(Timestamp completeness) {
        this.completeness = completeness;
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

}
