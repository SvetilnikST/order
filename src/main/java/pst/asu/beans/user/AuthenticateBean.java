package pst.asu.beans.user;

import org.apache.commons.lang3.StringUtils;
import pst.asu.beans.department.TblDepartmentEntity;
import pst.asu.entity.auth.RightsItemsEntity;
import pst.asu.entity.auth.RolesEntity;
import pst.asu.entity.auth.UserEntity;
import pst.asu.library.BCrypt;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

@Stateless
public class AuthenticateBean {

    public enum LoginResult{
        INCORRECT_LOGIN,
        INCORRECT_PASSWORD,
        SUCCESS
    }

//    @Inject
    @PersistenceContext(unitName = "control-app")
    private EntityManager entityManager;


    public LoginResult doLogin(String login, String password){
        if(StringUtils.isEmpty(login)){
            return LoginResult.INCORRECT_LOGIN;
        }

        if (StringUtils.isEmpty(password)){
            return LoginResult.INCORRECT_PASSWORD;
        }

        TypedQuery<UserEntity> query = entityManager.createQuery(
                "select entity from UserEntity entity " +
                        "where entity.username like :login" ,
                UserEntity.class);
                query.setParameter("login",login);
//        query.getSingleResult();

        UserEntity userEntity = query.getSingleResult();//entityManager.find(UserEntity.class, login);
        if(userEntity == null){
            return LoginResult.INCORRECT_LOGIN;
        }


        if(!BCrypt.checkpw(password,userEntity.getPassword_hash())){
            return LoginResult.INCORRECT_PASSWORD;
        }

        return LoginResult.SUCCESS;
    }

//    public Integer getDepartment(String login){
//        if(StringUtils.isEmpty(login)){
//            return null;
//        }
//        TypedQuery<UserEntity> query = entityManager.createQuery(
//                "select entity from UserEntity entity " +
//                        "where entity.username like :login" ,
//                UserEntity.class);
//        query.setParameter("login",login);
////        query.getSingleResult();
//
//        UserEntity userEntity = query.getSingleResult();//entityManager.find(UserEntity.class, login);
//        if(userEntity == null){
//            return null;
//        }
//        return userEntity.getDepartmentEntity();
//    }

    public TblDepartmentEntity getDepartmentEntity(String login){
        if(StringUtils.isEmpty(login)){
            return null;
        }
        TypedQuery<UserEntity> query = entityManager.createQuery(
                "select entity from UserEntity entity " +
                        "where entity.username like :login" ,
                UserEntity.class);
        query.setParameter("login",login);
//        query.getSingleResult();

        UserEntity userEntity = query.getSingleResult();//entityManager.find(UserEntity.class, login);
        if(userEntity == null){
            return null;
        }
        return userEntity.getDepartmentEntity();
    }


    public Map< String, String > getRights(String login){
        Map< String, String > rights = new HashMap< String, String >();

        if(StringUtils.isEmpty(login)){
            return null;
        }

        TypedQuery<UserEntity> query = entityManager.createQuery(
                "select entity from UserEntity entity " +
                        "where entity.username like :login" ,
                UserEntity.class);
        query.setParameter("login",login);

        UserEntity userEntity = query.getSingleResult();//entityManager.find(UserEntity.class, login);
        if(userEntity == null){
            return null;
        }

        for (RolesEntity role: userEntity.getUserRoleEntitySet() ) {
            for (RightsItemsEntity right: role.getRightsItemsEntitySet() ) {
                rights.put(right.getRightItem(),right.getRightItem());
            }
            
        }

        return rights;
    }

    public String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(13);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return hashed_password;
    }


}
