/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package org.apache.james.adapter.mailbox;

import org.apache.mailet.Mail;

import java.util.List;

public interface MailReprocessingManagementMBean {
    void reprocessAllMails(String repositoryPath)  throws Exception;

    void reprocessMail(String repositoryPath, String emailKey)  throws Exception;

    List<String> listMailRepositories() throws Exception;

    List<Mail> listMailsInRepository(String repositoryPath) throws Exception;

    Mail getMailInRepository(String repositoryPath, String emailKey) throws Exception;

    void deleteMailInRepository(String repositoryPath, String emailKey) throws Exception;

    void deleteMailsInRepository(String repositoryPath) throws Exception;

}
