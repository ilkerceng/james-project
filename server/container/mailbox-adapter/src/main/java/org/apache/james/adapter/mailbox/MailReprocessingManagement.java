package org.apache.james.adapter.mailbox;

import org.apache.james.mailrepository.api.MailRepositoryPath;
import org.apache.james.mailrepository.api.MailRepositoryStore;
import org.apache.james.mailrepository.api.MailRepositoryUrl;
import org.apache.james.queue.api.MailQueueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;
import java.util.stream.Stream;

public class MailReprocessingManagement extends StandardMBean implements MailReprocessingManagementMBean{

    /**
     * The Logger.
     */
    private static final Logger log = LoggerFactory.getLogger(MailReprocessingManagement.class.getName());

    private MailQueueFactory mailQueueFactory;
    private MailRepositoryStore mailRepositoryStore;

    public MailReprocessingManagement() throws NotCompliantMBeanException {
        super(MailReprocessingManagementMBean.class);
    }

    @Inject
    public void setMailRepositoryStore(@Named("mailrepositorystore") MailRepositoryStore mailRepositoryStore) {
        this.mailRepositoryStore = mailRepositoryStore;
    }

    //    public void setMailRepositoryStore(MailRepositoryStore mailRepositoryStore) {
//        this.mailRepositoryStore = mailRepositoryStore;
//    }

    public void setMailQueueFactory(MailQueueFactory mailQueueFactory) {
        this.mailQueueFactory = mailQueueFactory;
    }

    @Override
    public void reprocessAllMails(String repositoryPath) {
//        MailRepositoryPath mailRepositoryPath = new MailRepositoryPath(repositoryPath);
    }

    @Override
    public void reprocessMail(String repositoryPath, String emailKey) {

    }

    @Override
    public String[] listMailRepository() throws Exception {
        String[] mailRepositoryPaths = this.mailRepositoryStore.getPaths().map(path -> path.asString()).toArray(String[]::new);
        return mailRepositoryPaths;
    }
}
