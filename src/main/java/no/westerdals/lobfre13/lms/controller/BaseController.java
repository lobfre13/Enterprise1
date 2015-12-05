package no.westerdals.lobfre13.lms.controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by Fredrik on 04.12.2015.
 */

public abstract class BaseController {
    private ResourceBundle facesMsgBundle;
    private ResourceBundle dbErrorCodeBundle;
    protected FacesContext facesContext;

    @PostConstruct
    public void baseConstruct(){
        facesContext = FacesContext.getCurrentInstance();
        facesMsgBundle = facesContext.getApplication().getResourceBundle(facesContext, "facesMsg");
        dbErrorCodeBundle = facesContext.getApplication().getResourceBundle(facesContext, "dbErrorCodes");
    }

    protected void addFacesMessageFromKey(FacesMessage.Severity severity, String bundleKey){
        String message;
        try{
            message = facesMsgBundle.getString(bundleKey);
        } catch (MissingResourceException | NullPointerException exception){
            message = "Internal Error";
            severity = FacesMessage.SEVERITY_FATAL;
        }
        addFacesMessage(severity, message);
    }

    protected void addFacesMessage(FacesMessage.Severity severity, String message){
        facesContext.addMessage(null, new FacesMessage(severity, message, null));
    }


    protected String getSQLErrorCodeFromException(Throwable e) {
        Throwable t = e;
        while(t != null && !(t instanceof SQLException)) t = t.getCause();
        if (t == null) return null;
        SQLException sqle = (SQLException) t;

        try {
            return dbErrorCodeBundle.getString(sqle.getErrorCode() + "");
        }catch (MissingResourceException | NullPointerException exception){
            return null;
        }
    }

    protected String getConstraintViolationMessageFromException(Throwable e){
        Throwable t = e;
        while (t != null && !(t instanceof ConstraintViolationException)) t = t.getCause();
        if(t == null) return null;
        ConstraintViolationException cve = (ConstraintViolationException) t;

        return cve.getConstraintViolations().iterator().next().getMessage();
    }
}
