package pst.asu.beans.order;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;
import pst.asu.beans.order.domain.OrderEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@LocalBean
@Stateless
public class OrderDAOBean {

    @PersistenceContext(unitName = "control-app")
    private EntityManager entityManager;

    public boolean create(OrderEntity orderEntity) {
        if (!checkValid(orderEntity)) {
            return false;
        }
        OrderEntity existingOrder = entityManager.find(OrderEntity.class, orderEntity.getId());
        if (existingOrder != null) {
            return false;
        }
        long unpdate = new Date().getTime();
        orderEntity.setCreated_at((int)(unpdate/1000));
        orderEntity.setUpdated_at((int)(unpdate/1000));
        entityManager.persist(orderEntity);
//        entityManager.refresh(orderEntity);
//        entityManager.flush();
//        entityManager.merge(orderEntity);
//        entityManager.flush();
        Integer a= orderEntity.getId();
        return true;
    }

    public OrderEntity read(Integer id) {

        if(id==null){
            return null;
        }

        OrderEntity entity = entityManager.find(OrderEntity.class, id);
        if(entity == null){
            return null;
        }

        return entity;
    }

    public List<OrderEntity> readList() {

        Long curDate = new Timestamp(new Date().getTime()).getTime();
        // 60 сек * 60 мин * 24 часа  *45 дней * 1000 милисекунд
        Long day45 = 60L*60L*24L*45L*1000L;

        Timestamp olddate=new Timestamp(curDate-day45);

        TypedQuery<OrderEntity> query = entityManager.createQuery(
                "select entity from OrderEntity  entity where entity.dateIssuance> :last45day",
                OrderEntity.class)
                .setParameter("last45day",olddate);

        List<OrderEntity> orderEntities = query.getResultList();

        return orderEntities;

    }

    public List<OrderEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            String sql = "select * from "+getTableName()+" entity where status&:statusFilter and " +
                    "(completeness IS NULL " +
                    "or completeness between :dateFirst AND :dateLast) ";

        String statusFilter = (String) filters.get("status");
        String dateFirstFilter = (String) filters.get("dateFrom");
        String dateLastFilter = (String) filters.get("dateLast");
        String departmentFilter = (String) filters.get("department");

        if (departmentFilter != null) {
            sql += " and department = "+ departmentFilter + " ";
        }

        if (sortField != null) {
            sql += " order by " + sortField + " "
                    + (sortOrder.equals(SortOrder.ASCENDING) ?
                    "ASC" :
                    "DESC");
        }else{
            sql += " order by id DESC";
        }

        List<OrderEntity> rez = entityManager.createNativeQuery(sql ,OrderEntity.class)
                .setParameter("statusFilter",statusFilter)
                .setParameter("dateFirst",dateFirstFilter)
                .setParameter("dateLast",dateLastFilter)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();


        return rez;
    }


    public boolean update(OrderEntity orderEntity) {
        if (!checkValid(orderEntity)) {
            return false;
        }

        OrderEntity existingOrder = entityManager.find(OrderEntity.class, orderEntity.getId());

        if (existingOrder == null) {
            return false;
        }

        long unpdate = new Date().getTime();
        orderEntity.setUpdated_at((int)(unpdate/1000));

        entityManager.merge(orderEntity);

        return true;
    }

    public boolean delete(int id) {

        OrderEntity existingOrder = entityManager.find(OrderEntity.class, id);
        if (existingOrder == null) {
            return false;
        }

        entityManager.remove(existingOrder);

        return true;
    }

    public int getTotalCount(Map<String, Object> filters) {
        String sql = "select count(id) from "+getTableName()+" where status&:statusFilter and " +
                "(completeness IS NULL " +
                "or completeness between :dateFirst AND :dateLast) ";

        String statusFilter = (String) filters.get("status");
        String dateFirstFilter = (String) filters.get("dateFrom");
        String dateLastFilter = (String) filters.get("dateLast");
        String departmentFilter = (String) filters.get("department");

        if (departmentFilter != null) {
            sql += " and department = "+ departmentFilter + " ";
        }

        int rez = ((BigInteger) entityManager.createNativeQuery(sql)
                .setParameter("statusFilter",statusFilter)
                .setParameter("dateFirst",dateFirstFilter)
                .setParameter("dateLast",dateLastFilter)
                .getSingleResult()).intValue();

        return rez;
    }


    private boolean checkValid(OrderEntity orderEntity) {
        if (orderEntity == null ||
                StringUtils.isEmpty(orderEntity.getName()) ||
                StringUtils.isEmpty(orderEntity.getNumber())) {
            return false;
        }
        return true;
    }

    //специально, чтобы получать имя таблицы из сущности, иначе возможны ошибки с именами таблиц
    private String getTableName(){
        Table table = OrderEntity.class.getAnnotation(Table.class);
        return table.name();
    }
}
