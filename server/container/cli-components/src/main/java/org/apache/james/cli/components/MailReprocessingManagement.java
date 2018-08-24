package org.apache.james.cli.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import org.apache.james.mailrepository.api.MailKey;
import org.apache.james.mailrepository.api.MailRepository;
import org.apache.james.mailrepository.api.MailRepositoryPath;
import org.apache.james.mailrepository.api.MailRepositoryStore;
import org.apache.james.queue.api.MailQueue;
import org.apache.james.queue.api.MailQueueFactory;
import org.apache.james.cli.components.api.MailReprocessingManagementMBean;
import org.apache.mailet.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailReprocessingManagement implements MailReprocessingManagementMBean{

    /**
     * The Logger.
     */
    private static final Logger log = LoggerFactory.getLogger(MailReprocessingManagement.class.getName());

    private MailQueueFactory mailQueueFactory;
    private MailRepositoryStore mailRepositoryStore;

    @Inject
    public MailReprocessingManagement(MailRepositoryStore mailRepositoryStore, MailQueueFactory mailQueueFactory) {
        this.mailRepositoryStore = mailRepositoryStore;
        this.mailQueueFactory = mailQueueFactory;

        try {
            this.reprocessAllMails("var/mail/spam/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reprocessAllMails(String repositoryPath) throws Exception{
        boolean hasRepositoryPathMatch = this.mailRepositoryStore.getPaths().anyMatch(path->path.asString().equals(repositoryPath));

        if (hasRepositoryPathMatch){
            MailQueue mailQueue = (MailQueue) (this.mailQueueFactory.listCreatedMailQueues().iterator().next());
            if (mailQueue != null) {
                Stream<MailRepository> foundMailRepositoryStream = this.mailRepositoryStore.getByPath(MailRepositoryPath.from(repositoryPath));
                if(foundMailRepositoryStream != null) {
                    MailRepository mailRepository = foundMailRepositoryStream.findFirst().get();
                    Iterator<MailKey> mailKeyIterator = mailRepository.list();
                    while (mailKeyIterator.hasNext()) {
                        MailKey mailKey = mailKeyIterator.next();
                        Mail mail = mailRepository.retrieve(mailKey);
                        mailQueue.enQueue(mail);
                    }
                }
            }
        }
    }

    @Override
    public void reprocessMail(String repositoryPath, String emailKey) throws Exception {
        boolean hasRepositoryPathMatch = this.mailRepositoryStore.getPaths().anyMatch(path->path.asString().equals(repositoryPath));

        if (hasRepositoryPathMatch){
            MailQueue mailQueue = (MailQueue) (this.mailQueueFactory.listCreatedMailQueues().iterator().next());
            if (mailQueue != null) {
                Stream<MailRepository> foundMailRepositoryStream = this.mailRepositoryStore.getByPath(MailRepositoryPath.from(repositoryPath));
                if(foundMailRepositoryStream != null) {
                    MailRepository mailRepository = foundMailRepositoryStream.findFirst().get();
                    Iterator<MailKey> mailKeyIterator = mailRepository.list();
                    while (mailKeyIterator.hasNext()) {
                        MailKey mailKey = mailKeyIterator.next();
                        if (mailKey.asString().equals(emailKey)) {
                            Mail mail = mailRepository.retrieve(mailKey);
                            mailQueue.enQueue(mail);
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<String> listMailRepositories() throws Exception {
        List<String> mailRepositoryPaths = this.mailRepositoryStore.getPaths().map(path -> path.asString()).collect(Collectors.toList());
        return mailRepositoryPaths;
    }

    @Override
    public List<Mail> listMailsInRepository(String repositoryPath) throws Exception {
        Stream<MailRepository> foundMailRepositoryStream = this.mailRepositoryStore.getByPath(MailRepositoryPath.from(repositoryPath));
        List<Mail> mailList = null;
        if(foundMailRepositoryStream != null) {
            MailRepository mailRepository = foundMailRepositoryStream.findFirst().get();
            Iterator<MailKey> mailKeyIterator = mailRepository.list();
            mailList = new ArrayList<>();
            while (mailKeyIterator.hasNext()) {
                MailKey mailKey = mailKeyIterator.next();
                Mail mail = mailRepository.retrieve(mailKey);
                mailList.add(mail);
            }
        }

        return mailList;
    }

    @Override
    public Mail getMailInRepository(String repositoryPath, String emailKey) throws Exception {
        Stream<MailRepository> foundMailRepositoryStream = this.mailRepositoryStore.getByPath(MailRepositoryPath.from(repositoryPath));
        Mail mail = null;
        if(foundMailRepositoryStream != null) {
            MailRepository mailRepository = foundMailRepositoryStream.findFirst().get();
            MailKey mailKey = new MailKey(emailKey);
            mail = mailRepository.retrieve(mailKey);
        }

        return mail;
    }

    @Override
    public void deleteMailInRepository(String repositoryPath, String emailKey) throws Exception {
        Stream<MailRepository> foundMailRepositoryStream = this.mailRepositoryStore.getByPath(MailRepositoryPath.from(repositoryPath));
        if(foundMailRepositoryStream != null) {
            MailRepository mailRepository = foundMailRepositoryStream.findFirst().get();
            MailKey mailKey = new MailKey(emailKey);
            mailRepository.remove(mailKey);
        }
    }

    @Override
    public void deleteMailsInRepository(String repositoryPath) throws Exception {
        Stream<MailRepository> foundMailRepositoryStream = this.mailRepositoryStore.getByPath(MailRepositoryPath.from(repositoryPath));
        if(foundMailRepositoryStream != null) {
            MailRepository mailRepository = foundMailRepositoryStream.findFirst().get();
            mailRepository.removeAll();
        }
    }
}
