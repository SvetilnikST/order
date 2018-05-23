package pst.asu.beans.department;

import org.apache.commons.lang3.StringUtils;
//import org.primefaces.model.SortOrder;
//import pst.asu.beans.order.domain.OrderEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@LocalBean
@Stateless
public class DepartmentDAOBean {

    @PersistenceContext(unitName = "control-app")
    private EntityManager entityManager;

    public TblDepartmentEntity read(int id) {

        return entityManager.find(TblDepartmentEntity.class, id);
    }

    public List<TblDepartmentEntity> readList() {


        TypedQuery<TblDepartmentEntity> query = entityManager.createQuery(
                "from TblDepartmentEntity entity",
                TblDepartmentEntity.class);

        List<TblDepartmentEntity> tblDepartmentEntities = query.getResultList();

        return tblDepartmentEntities;

    }

    public int getTotalCount() {

        Long rez = entityManager.createQuery(
                "select count (entity.idDepartment) from TblDepartmentEntity entity",
                Long.class)
                .getSingleResult();

        return rez.intValue();
    }

}
