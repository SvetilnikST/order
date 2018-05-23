package pst.asu.beans.user;

import org.apache.commons.lang3.StringUtils;
import pst.asu.beans.department.TblDepartmentEntity;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Named
@SessionScoped
public class UserBean implements Serializable {

    private final String COOKIE_NAME = "orderUserName";
    private final int COOKIE_TIME_TO_REMEMBER = 60*60*24*30*12; //
    private String login;
    private String password;
    private TblDepartmentEntity departmentEntity;
    private boolean authenticated;
    private Map< String, String > rights = new HashMap< String, String >();

    @EJB
    private AuthenticateBean authenticateBean;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public TblDepartmentEntity getDepartmentEntity() {
        return departmentEntity;
    }

    public void setDepartmentEntity(TblDepartmentEntity departmentEntity) {
        this.departmentEntity = departmentEntity;
    }

    public void doLogin(){
        authenticated = (authenticateBean.doLogin(login,password)== AuthenticateBean.LoginResult.SUCCESS);
        if(authenticated){
            this.departmentEntity = authenticateBean.getDepartmentEntity(login);
            this.rights = authenticateBean.getRights(login);
            setCookie(COOKIE_NAME,login,COOKIE_TIME_TO_REMEMBER);
        }
        try {
            String rootUrl = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

            FacesContext.getCurrentInstance().getExternalContext().redirect(rootUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String generateHashPass(String pass){

        return authenticateBean.hashPassword(pass);
    }

    public void doLogout(){
        authenticated = false;
        rights.clear();
        setCookie(COOKIE_NAME, login,0);
        try {
            String rootUrl = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            FacesContext.getCurrentInstance().getExternalContext().redirect(rootUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doRegister(){

    }

    public boolean doRightVerify(String rightItem) {
        String value = rights.get(rightItem); // пытаемся обратиться по ключу
        if (value != null && value.equals(rightItem)) { // проверяем  - не получили ли мы null
            // получили что-то другое - значит значение есть! =)
            return true;
        } else {
            return false;
        }
    }

    @PostConstruct
    private void init(){
        if(authenticated==false){
            Cookie cookie = getCookie(COOKIE_NAME);
            if(cookie !=null) {
                String tmpName = cookie.getValue();
                if (!StringUtils.isEmpty(tmpName)) {
                    this.login = tmpName;
                    this.departmentEntity = authenticateBean.getDepartmentEntity(login);
                    this.rights = authenticateBean.getRights(login);
                    authenticated = true;
                }
            }
        }
    }

    private Cookie getCookie(String name) {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0 ) {
            for (int i = 0; i < userCookies.length; i++) {
                if (userCookies[i].getName().equals(name)) {
                    cookie = userCookies[i];
                    return cookie;
                }
            }
        }
        return null;
    }

    private void setCookie(String name, String value, int expiry) {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0 ) {
            for (int i = 0; i < userCookies.length; i++) {
                if (userCookies[i].getName().equals(name)) {
                    cookie = userCookies[i];
                    break;
                }
            }
        }

        if (cookie != null) {
            cookie.setValue(value);
        } else {
            cookie = new Cookie(name, value);
            cookie.setPath(request.getContextPath());
        }

        cookie.setMaxAge(expiry);

        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.addCookie(cookie);
    }

}

