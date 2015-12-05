package controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ResourceBundle;

/**
 * Created by Fredrik on 04.12.2015.
 */

public abstract class BaseController {
    private ResourceBundle bundle;
    protected FacesContext facesContext;

    @PostConstruct
    public void baseConstruct(){
        facesContext = FacesContext.getCurrentInstance();
        bundle = facesContext.getApplication().getResourceBundle(facesContext, "facesMsg");
    }

    protected void addFacesMessage(FacesMessage.Severity severity, String bundleKey){
        String msg;
        if(bundle == null ||  (msg = bundle.getString(bundleKey)) == null){
            msg = "Internal Error";
            severity = FacesMessage.SEVERITY_FATAL;
        }

        facesContext.addMessage(null, new FacesMessage(severity, msg, null));
    }
}
