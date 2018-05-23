package pst.asu.beans.department;

import pst.asu.beans.order.domain.OrderEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tblDepartment")
public class TblDepartmentEntity{

    @Id
    @Column(name = "idDepartment", nullable = false)
    private int idDepartment;

    @Column(name = "Department", nullable = false, length = 20)
    private String department;

    public int getIdDepartment() {
        return idDepartment;
    }

    public void setIdDepartment(int idDepartment) {
        this.idDepartment = idDepartment;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departmentEntity")
    private Set<OrderEntity> orderEntitySet = new HashSet<>();

    public Set<OrderEntity> getOrderEntitySet() {
        return orderEntitySet;
    }

    public void setOrderEntitySet(Set<OrderEntity> orderEntitySet) {
        this.orderEntitySet = orderEntitySet;
    }

}
