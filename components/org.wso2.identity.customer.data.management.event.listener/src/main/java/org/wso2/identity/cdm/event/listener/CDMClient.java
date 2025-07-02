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

package org.wso2.identity.cdm.event.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.HashMap;
import java.util.Map;

public class CDMClient {

    private static final String CDM_API_URL = "http://localhost:8900/api/v1/profile-schema/sync";


    public static void triggerSchemaDataSync(String tenantDomain) throws Exception {

        System.out.println("Inside triggerSchemaDataSync method");

        Map<String, Object> profileSyncPayload = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        profileSyncPayload.put("event", "schema-initialization");
        profileSyncPayload.put("tenantDomain",tenantDomain);
        String json = mapper.writeValueAsString(profileSyncPayload);

        // Log request details
        System.out.println("URL: " + CDM_API_URL);
        System.out.println("Payload: " + json);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(CDM_API_URL);
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = client.execute(httpPost)) {
                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println("CDM sync response status: " + statusCode);

                String responseBody = "";
                if (response.getEntity() != null) {
                    responseBody = new String(response.getEntity().getContent().readAllBytes());
                }

                System.out.println("CDM sync response body: " + responseBody);
                if (statusCode != 200 && statusCode != 204) {
                    throw new RuntimeException("Failed to sync user to CDM, status: " + statusCode + ", response: " + responseBody);
                }
            }
        }
    }
}
