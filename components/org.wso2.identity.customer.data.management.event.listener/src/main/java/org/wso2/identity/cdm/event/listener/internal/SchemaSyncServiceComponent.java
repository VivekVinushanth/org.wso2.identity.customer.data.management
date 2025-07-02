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

package org.wso2.identity.cdm.event.listener.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.wso2.carbon.identity.organization.management.service.OrganizationManager;
import org.wso2.carbon.stratos.common.listeners.TenantMgtListener;
import org.wso2.carbon.user.core.service.RealmService;
import org.wso2.identity.cdm.event.listener.SchemaSyncListener;

/**
 * This class contains the implementation of the service component of the TenantAssociationManagement.
 */
@Component(
        name = "org.wso2.carbon.identity.cdm.event.listener.SchemaSyncServiceComponent",
        immediate = true
)
public class SchemaSyncServiceComponent {

    private static final Log LOG = LogFactory.getLog(SchemaSyncServiceComponent.class);

    @Activate
    protected void activate(ComponentContext context) {

        SchemaSyncListener tenantManagementListener = new SchemaSyncListener();
        context.getBundleContext().registerService(TenantMgtListener.class.getName(), tenantManagementListener, null);
        LOG.debug("Schema sync listener activated");
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Schema sync listener deactivated");
        }
    }

    @Reference(
            name = "RealmService",
            service = RealmService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetRealmService")
    protected void setRealmService(RealmService realmService) {

        SchemaSyncServiceDataHolder.setRealmService(realmService);
    }

    protected void unsetRealmService(RealmService realmService) {

        SchemaSyncServiceDataHolder.setRealmService(null);
    }

    @Reference(name = "identity.organization.management.component",
            service = OrganizationManager.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetOrganizationManager")
    protected void setOrganizationManager(OrganizationManager organizationManager) {

        SchemaSyncServiceDataHolder.setOrgSchemaSyncManager(organizationManager);
    }

    protected void unsetOrganizationManager(OrganizationManager organizationManager) {

        SchemaSyncServiceDataHolder.setOrgSchemaSyncManager(null);
    }
}