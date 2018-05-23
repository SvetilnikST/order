package pst.asu.beans.files;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;
import pst.asu.beans.files.domain.FilesEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@LocalBean
@Stateless
public class FilesDAOBean {

    @PersistenceContext(unitName = "control-app")
    private EntityManager entityManager;

    //CRUD

    public boolean create(FilesEntity filesEntity) {
        if (!checkValid(filesEntity)) {
            return false;
        }

        FilesEntity existingOrder = entityManager.find(FilesEntity.class, filesEntity.getId());
        if (existingOrder != null) {
            return false;
        }

        Timestamp unpdate = new Timestamp(new Date().getTime());
        filesEntity.setCreated_at(unpdate);


        entityManager.persist(filesEntity);

        return true;
    }

    public FilesEntity read(Integer id) {

        if (id == null) {
            return null;
        }

        FilesEntity entity = entityManager.find(FilesEntity.class, id);
        if (entity == null) {
            return null;
        }

        return entity;
    }

    public List<FilesEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        String typeFilter = (String) filters.get("typeFilter");
        String sql;

        sql = "select entity from FilesEntity entity WHERE entity.type = :type order by entity.id desc ";

        if (typeFilter.equals("audio")) {
            sql = "select entity from FilesEntity entity WHERE entity.type = :type order by entity.id desc ";
        }
        if (typeFilter.equals("video")) {
            sql = "select entity from FilesEntity entity WHERE entity.type != :type order by entity.id desc ";
        }

        List<FilesEntity> rez = entityManager.createQuery(sql, FilesEntity.class)
                .setParameter("type", 10)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();

//        for (FilesEntity oneFileEntityRecord:
//                rez) {
//            oneFileEntityRecord.setNameal();
//        }

//        List<FilesDTO> finrez = new ArrayList<>();
//        for (FilesEntity oneFileEntityRecord:
//             rez) {
//            finrez.add(oneFileEntityRecord.toDTO());
//        }
//
        return rez;
    }


    public boolean update(FilesEntity filesEntity) {
        if (!checkValid(filesEntity)) {
            return false;
        }

        FilesEntity existingOrder = entityManager.find(FilesEntity.class, filesEntity.getId());

        if (existingOrder == null) {
            return false;
        }

        entityManager.merge(filesEntity);

        return true;
    }

    public boolean delete(int id) {

        FilesEntity existingOrder = entityManager.find(FilesEntity.class, id);
        if (existingOrder == null) {
            return false;
        }

        entityManager.remove(existingOrder);

        return true;
    }

    public int getTotalCount(Map<String, Object> filters) {

        String typeFilter = (String) filters.get("typeFilter");
        String sql;

        sql = "select count(entity.id) from FilesEntity entity WHERE entity.type = :type";

        if (typeFilter.equals("audio")) {
            sql = "select count(entity.id) from FilesEntity entity WHERE entity.type = :type";
        }
        if (typeFilter.equals("video")) {
            sql = "select count(entity.id) from FilesEntity entity WHERE entity.type != :type";
        }

//        sql = "select count(entity.id) from FilesEntity entity WHERE entity.type = :type";

        int rez = ((Long) entityManager.createQuery(sql)
                .setParameter("type", 10)
                .getSingleResult()).intValue();

        return rez;
    }


    private boolean checkValid(FilesEntity filesEntity) {
        if (filesEntity == null
//                ||  StringUtils.isEmpty(filesEntity.getName())
//              ||  StringUtils.isEmpty(filesEntity.getNumber()))
                ) {
            return false;
        }
        return true;
    }

    //специально, чтобы получать имя таблицы из сущности, иначе возможны ошибки с именами таблиц
    private String getTableName() {
        Table table = FilesEntity.class.getAnnotation(Table.class);
        return table.name();
    }


}
