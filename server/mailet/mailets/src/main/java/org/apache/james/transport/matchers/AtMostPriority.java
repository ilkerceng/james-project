package org.apache.james.transport.matchers;

public class AtMostPriority extends AbstractPriorityMatcher {
    public AtMostPriority() {
        super("AtMostPriority");
    }

    @Override
    public boolean priorityMatch(Integer mailPriorityValue) {
        return this.getPriority() >= mailPriorityValue;
    }

}
