package org.apache.james.adapter.mailbox;

public interface MailReprocessingManagementMBean {
    void reprocessAllMails(String repositoryPath);
    void reprocessMail(String repositoryPath, String emailKey);
    String[] listMailRepository() throws Exception;
}
