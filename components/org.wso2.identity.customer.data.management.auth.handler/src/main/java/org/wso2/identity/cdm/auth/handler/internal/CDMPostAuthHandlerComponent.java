/*
 * Copyright (c) 2025, WSO2 LLC. (http://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.identity.cdm.auth.handler.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.wso2.carbon.identity.application.authentication.framework.handler.request.PostAuthenticationHandler;
import org.wso2.carbon.identity.event.handler.AbstractEventHandler;
import org.wso2.carbon.user.core.service.RealmService;
import org.wso2.identity.cdm.auth.handler.CDMPostAuthnHandler;

@Component(name = "org.wso2.identity.auth.handler",
        immediate = true)
public class CDMPostAuthHandlerComponent {

private static final Log log = LogFactory.getLog(CDMPostAuthHandlerComponent.class);

    @Activate
    protected void activate(ComponentContext ctx){

        CDMPostAuthnHandler cdmPostAuthnHandler = new CDMPostAuthnHandler();
        ctx.getBundleContext().registerService(PostAuthenticationHandler.class.getName(), cdmPostAuthnHandler, null);


        log.info("Custom event handler activated successfully.");
    }

    @Deactivate
    protected void deactivate(ComponentContext ctx){
        if(log.isDebugEnabled()){
            log.debug("Custom event handler is deactivated");
        }
    }

    @Reference(
            name = "realm.service",
            service = RealmService.class,
            cardinality = org.osgi.service.component.annotations.ReferenceCardinality.MANDATORY,
            policy = org.osgi.service.component.annotations.ReferencePolicy.DYNAMIC,
            unbind = "unsetRealmService"
    )
    protected void setRealmService(RealmService realmService) {
        CDMPostAuthHandlerDataHolder.getInstance().setRealmService(realmService);
    }

    protected void unsetRealmService(RealmService realmService) {
        CDMPostAuthHandlerDataHolder.getInstance().setRealmService(null);
    }
}

