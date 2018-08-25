package org.apache.james.transport.matchers;

import javax.mail.MessagingException;
import org.apache.mailet.base.MailetUtil;

public class HasPriority extends AbstractPriorityMatcher {
    public HasPriority() {
        super("HasPriority");
    }

    @Override
    public boolean priorityMatch(Integer mailPriorityValue) {
        return this.getPriority() == mailPriorityValue;
    }

}
