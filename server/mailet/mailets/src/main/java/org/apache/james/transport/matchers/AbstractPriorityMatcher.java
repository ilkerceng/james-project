package org.apache.james.transport.matchers;

import java.util.Collection;
import javax.mail.MessagingException;
import org.apache.james.core.MailAddress;
import org.apache.james.queue.api.MailPrioritySupport;
import org.apache.mailet.Mail;
import org.apache.mailet.base.GenericMatcher;
import org.apache.mailet.base.MailetUtil;

public abstract class AbstractPriorityMatcher extends GenericMatcher {
    private final String priorityMatcherName;
    private Integer priority;

    public AbstractPriorityMatcher(String priorityMatcherName){
        this.priorityMatcherName = priorityMatcherName;
    }

    @Override
    public void init() throws MessagingException {
        Integer priority = MailetUtil.getInitParameterAsStrictlyPositiveInteger(getCondition());
        this.setPriority(priority);
    }

    @Override
    public Collection<MailAddress> match(Mail mail) throws MessagingException {
        Integer mailPriorityValue = (Integer) mail.getAttribute(MailPrioritySupport.MAIL_PRIORITY);
        if (mailPriorityValue != null) {
            if (this.priorityMatch(mailPriorityValue)) {
                return mail.getRecipients();
            }
        }
        return null;
    }

    public abstract boolean priorityMatch(Integer mailPriorityValue);

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getPriorityMatcherName() {
        return priorityMatcherName;
    }
}
