package pst.asu.beans.user;

import pst.asu.entity.auth.RolesEntity;
import pst.asu.entity.auth.UserEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Map;

@LocalBean
@Stateless
public class UserDAOBean {
    @PersistenceContext(unitName = "control-app")
    private EntityManager entityManager;
    //CRUD
    public UserEntity read(int id) {
        return entityManager.find(UserEntity.class, id);
    }

    public List<RolesEntity> readRolesList() {
        TypedQuery<RolesEntity> query = entityManager.createQuery(
                "select entity from RolesEntity entity",
                RolesEntity.class);

        List<RolesEntity> rolesEntities = query.getResultList();
        return rolesEntities ;
    }

    public List<UserEntity> readUserList() {
        TypedQuery<UserEntity> query = entityManager.createQuery(
                "select entity from UserEntity entity",
                UserEntity.class);

        List<UserEntity> userEntities = query.getResultList();
        return userEntities;

    }

    public int getTotalCount() {
        Long rez = entityManager.createQuery(
                "select count (entity.id) from UserEntity entity",
                Long.class)
                .getSingleResult();
        return rez.intValue();
    }


    public boolean create(UserEntity userEntity) {
        UserEntity existingUser = entityManager.find(UserEntity.class, userEntity.getId());
        long unpdate = new Date().getTime();
        userEntity.setCreated_at((int) (unpdate / 1000));
        userEntity.setUpdated_at((int) (unpdate / 1000));
        entityManager.persist(userEntity);

        return true;
    }

    public boolean update(UserEntity userEntity) {
        UserEntity existingUser = entityManager.find(UserEntity.class, userEntity.getId());
        long unpdate = new Date().getTime();
        userEntity.setUpdated_at((int) (unpdate / 1000));

        entityManager.merge(userEntity);

        return true;
    }

    public boolean delete(int id) {
        UserEntity existingUser = entityManager.find(UserEntity.class, id);
        if (existingUser == null) {
            return false;
        }
        entityManager.remove(existingUser);
        return true;
    }

    private String getTableName(){
        Table table = UserEntity.class.getAnnotation(Table.class);
        return table.name();
    }
}
