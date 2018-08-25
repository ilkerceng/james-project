package org.apache.james.transport.matchers;

public class AtLeastPriority extends AbstractPriorityMatcher {
    public AtLeastPriority() {
        super("AtLeastPriority");
    }

    @Override
    public boolean priorityMatch(Integer mailPriorityValue) {
        return this.getPriority() <= mailPriorityValue;
    }

}
