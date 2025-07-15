package org.wso2.identity.cdm.auth.handler;

import org.wso2.carbon.identity.application.authentication.framework.config.ConfigurationFacade;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.exception.PostAuthenticationFailedException;
import org.wso2.carbon.identity.application.authentication.framework.handler.request.AbstractPostAuthnHandler;
import org.wso2.carbon.identity.application.authentication.framework.handler.request.PostAuthnHandlerFlowStatus;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CDMPostAuthnHandler extends AbstractPostAuthnHandler {

    @Override
    public PostAuthnHandlerFlowStatus handle(HttpServletRequest request,
                                             HttpServletResponse response,
                                             AuthenticationContext context)
            throws PostAuthenticationFailedException {

        System.out.println("CDMPostAuthnHandler: handle method called");
        if (request.getCookies() != null) {
            for (javax.servlet.http.Cookie cookie : request.getCookies()) {
                if ("cdm_profile_id".equals(cookie.getName())) {
                    String val = cookie.getValue();
                    context.setProperty("profileId", val);
                    break;
                }
            }
        }
        return PostAuthnHandlerFlowStatus.SUCCESS_COMPLETED;
    }
}
