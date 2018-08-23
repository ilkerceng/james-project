package org.apache.james.mailbox.store.probe;

import org.apache.mailet.Mail;

import java.util.List;

public interface MailReprocessingProbe {
    void reprocessAllMails(String repositoryPath) throws Exception;

    void reprocessMail(String repositoryPath, String emailKey) throws Exception;

    List<String> listMailRepositories() throws Exception;

    List<Mail> listMailsInRepository(String repositoryPath) throws Exception;

    Mail getMailInRepository(String repositoryPath, String emailKey) throws Exception;

    void deleteMailInRepository(String repositoryPath, String emailKey) throws Exception;

    void deleteMailsInRepository(String repositoryPath) throws Exception;
}
