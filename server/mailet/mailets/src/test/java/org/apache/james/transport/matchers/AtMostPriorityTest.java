/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package org.apache.james.transport.matchers;

import java.util.Collection;
import javax.mail.MessagingException;
import org.apache.james.core.MailAddress;
import org.apache.james.queue.api.MailPrioritySupport;
import org.apache.mailet.base.test.FakeMail;
import org.apache.mailet.base.test.FakeMatcherConfig;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;

public class AtMostPriorityTest {
    private AtMostPriority matcher;
    private FakeMail fakeMail;
    private MailAddress testRecipient;

    @Before
    public void setup() throws Exception {
        matcher = new AtMostPriority();
        FakeMatcherConfig matcherConfig = FakeMatcherConfig.builder()
                .matcherName(matcher.getPriorityMatcherName())
                .condition("5")
                .build();

        matcher.init(matcherConfig);
        testRecipient = new MailAddress("test@james.apache.org");
    }

    @Test
    public void shouldMatchWhenPriorityMatch() throws MessagingException {
        fakeMail = FakeMail.builder()
                .recipient(testRecipient)
                .attribute(MailPrioritySupport.MAIL_PRIORITY, 5)
                .build();

        Collection<MailAddress> actual = matcher.match(fakeMail);

        assertThat(actual).containsOnly(testRecipient);
    }

    @Test
    public void shouldMatchWhenMailHasLowerPriority() throws MessagingException {
        fakeMail = FakeMail.builder()
                .recipient(testRecipient)
                .attribute(MailPrioritySupport.MAIL_PRIORITY, 3)
                .build();

        Collection<MailAddress> actual = matcher.match(fakeMail);

        assertThat(actual).containsOnly(testRecipient);
    }

    @Test
    public void shouldNotMatchWhenPriorityDoesNotMatch() throws MessagingException {
        fakeMail = FakeMail.builder()
                .recipient(testRecipient)
                .attribute(MailPrioritySupport.MAIL_PRIORITY, 7)
                .build();

        Collection<MailAddress> actual = matcher.match(fakeMail);

        assertThat(actual).isNull();
    }

}
