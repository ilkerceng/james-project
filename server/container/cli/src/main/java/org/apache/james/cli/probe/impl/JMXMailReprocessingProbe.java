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
package org.apache.james.cli.probe.impl;

import org.apache.james.cli.components.api.MailReprocessingManagementMBean;
import org.apache.james.mailbox.store.probe.MailReprocessingProbe;
import org.apache.mailet.Mail;

import javax.management.MalformedObjectNameException;
import java.io.IOException;
import java.util.List;

public class JMXMailReprocessingProbe implements MailReprocessingProbe, JmxProbe {
    private static final String MAILREPROCESSINGMANAGEMENT_OBJECT_NAME = "org.apache.james:type=component,name=mailreprocessingmanagementbean";

    private MailReprocessingManagementMBean mailReprocessingManagementMBean;

    @Override
    public JMXMailReprocessingProbe connect(JmxConnection jmxc) throws IOException {
        try {
            mailReprocessingManagementMBean = jmxc.retrieveBean(MailReprocessingManagementMBean.class, MAILREPROCESSINGMANAGEMENT_OBJECT_NAME);
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException("Invalid ObjectName? Please report this as a bug.", e);
        }
        return this;
    }

    @Override
    public void reprocessAllMails(String repositoryPath) throws Exception {
        mailReprocessingManagementMBean.reprocessAllMails(repositoryPath);
    }

    @Override
    public void reprocessMail(String repositoryPath, String emailKey) throws Exception {
        mailReprocessingManagementMBean.reprocessMail(repositoryPath, emailKey);
    }

    @Override
    public List<String> listMailRepositories() throws Exception {
        return mailReprocessingManagementMBean.listMailRepositories();
    }

    @Override
    public List<Mail> listMailsInRepository(String repositoryPath) throws Exception {
        return mailReprocessingManagementMBean.listMailsInRepository(repositoryPath);
    }

    @Override
    public Mail getMailInRepository(String repositoryPath, String emailKey) throws Exception {
        return mailReprocessingManagementMBean.getMailInRepository(repositoryPath, emailKey);
    }

    @Override
    public void deleteMailInRepository(String repositoryPath, String emailKey) throws Exception {
        mailReprocessingManagementMBean.deleteMailInRepository(repositoryPath, emailKey);
    }

    @Override
    public void deleteMailsInRepository(String repositoryPath) throws Exception {
        mailReprocessingManagementMBean.deleteMailsInRepository(repositoryPath);
    }

}
