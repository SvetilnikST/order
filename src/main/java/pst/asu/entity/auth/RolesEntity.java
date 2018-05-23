package pst.asu.entity.auth;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tblRoles")
public class RolesEntity {
    @Id
    @Column(name = "idRole", nullable = false)
    private int idRole;

    @Column(name = "role", nullable = false)
    private String role;

    @ManyToMany
    @JoinTable(name = "tblRoleRights",
            //foreign key for CarsEntity in employee_car table
            joinColumns = @JoinColumn(name = "idRole"),
            //foreign key for other side - EmployeeEntity in employee_car table
            inverseJoinColumns = @JoinColumn(name = "idRightsItem"))
    private Set<RightsItemsEntity> rightsItemsEntitySet = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tblUserRole",
            //foreign key for CarsEntity in employee_car table
            joinColumns = @JoinColumn(name = "idRole"),
            //foreign key for other side - EmployeeEntity in employee_car table
            inverseJoinColumns = @JoinColumn(name = "idUser"))
    private Set<UserEntity> userRoleEntitySet = new HashSet<>();

    public Set<UserEntity> getUserRoleEntitySet() {
        return userRoleEntitySet;
    }

    public void setUserRoleEntitySet(Set<UserEntity> userRoleEntitySet) {
        this.userRoleEntitySet = userRoleEntitySet;
    }

    public Set<RightsItemsEntity> getRightsItemsEntitySet() {
        return rightsItemsEntitySet;
    }

    public void setRightsItemsEntitySet(Set<RightsItemsEntity> rightsItemsEntitySet) {
        this.rightsItemsEntitySet = rightsItemsEntitySet;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
