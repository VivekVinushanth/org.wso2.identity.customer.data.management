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


import org.wso2.carbon.identity.organization.management.service.OrganizationManager;
import org.wso2.carbon.user.core.service.RealmService;

/**
 * This class holds the services required for tenant association management services.
 */
public class SchemaSyncServiceDataHolder {

    private static RealmService realmService;
    private static OrganizationManager organizationManager;

    private SchemaSyncServiceDataHolder() {

    }

    /**
     * Get the RealmService.
     *
     * @return RealmService.
     */
    public static RealmService getRealmService() {

        if (realmService == null) {
            throw new RuntimeException("RealmService is not initialized.");
        }
        return realmService;
    }

    /**
     * Set the RealmService.
     *
     * @param realmService RealmService.
     */
    public static void setRealmService(RealmService realmService) {

        SchemaSyncServiceDataHolder.realmService = realmService;
    }

    public static OrganizationManager getSchemaSyncManager() {

        return organizationManager;
    }

    public static void setOrgSchemaSyncManager(OrganizationManager organizationManager) {

        SchemaSyncServiceDataHolder.organizationManager = organizationManager;
    }
}