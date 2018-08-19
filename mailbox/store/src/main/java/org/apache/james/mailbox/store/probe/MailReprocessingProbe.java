package org.apache.james.mailbox.store.probe;

public interface MailReprocessingProbe {
    void reprocessAllMails(String repositoryPath) throws Exception;
    void reprocessMail(String repositoryPath, String emailKey) throws Exception;
    String[] listMailRepository() throws Exception;
}
