package org.apache.james.cli.probe.impl;

import org.apache.james.adapter.mailbox.MailReprocessingManagementMBean;
import org.apache.james.mailbox.store.probe.MailReprocessingProbe;

import javax.management.MalformedObjectNameException;
import java.io.IOException;

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
    public String[] listMailRepository() throws Exception {
        return mailReprocessingManagementMBean.listMailRepository();
    }
}
