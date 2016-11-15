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

package org.apache.james.transport.mailets.utils;

import static org.assertj.guava.api.Assertions.assertThat;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.james.transport.mailets.utils.MimeMessageUtils;
import org.apache.mailet.base.test.FakeMail;
import org.junit.Test;

import com.google.common.base.Optional;

public class MimeMessageUtilsTest {

    @Test
    public void subjectWithPrefixShouldReturnSubjectWithPrefixWhenSubjectIsPresent() throws Exception {
        MimeMessage message = new MimeMessage(Session.getDefaultInstance(new Properties()));
        message.setSubject("subject");

        Optional<String> subjectWithPrefix = new MimeMessageUtils(message).subjectWithPrefix("my");

        assertThat(subjectWithPrefix).contains("my subject");
    }

    @Test
    public void subjectWithPrefixShouldReturnPrefixAsSubjectWhenSubjectIsAbsent() throws Exception {
        MimeMessage message = new MimeMessage(Session.getDefaultInstance(new Properties()));

        Optional<String> subjectWithPrefix = new MimeMessageUtils(message).subjectWithPrefix("my");

        assertThat(subjectWithPrefix).contains("my");
    }

    @Test
    public void subjectWithPrefixShouldReturnAbsentWhenNullPrefix() throws Exception {
        MimeMessage message = new MimeMessage(Session.getDefaultInstance(new Properties()));
        message.setSubject("original subject");
        FakeMail oldMail = FakeMail.from(message);
        String subjectPrefix = null;
        String subject = null;

        Optional<String> subjectWithPrefix = new MimeMessageUtils(message).subjectWithPrefix(subjectPrefix, oldMail, subject);

        assertThat(subjectWithPrefix).isAbsent();
    }

    @Test
    public void subjectWithPrefixShouldReturnAbsentWhenEmptyPrefix() throws Exception {
        MimeMessage message = new MimeMessage(Session.getDefaultInstance(new Properties()));
        message.setSubject("original subject");
        FakeMail oldMail = FakeMail.from(message);
        String subjectPrefix = "";
        String subject = null;

        Optional<String> subjectWithPrefix = new MimeMessageUtils(message).subjectWithPrefix(subjectPrefix, oldMail, subject);

        assertThat(subjectWithPrefix).isAbsent();
    }


    @Test
    public void buildNewSubjectShouldPrefixOriginalSubjectWhenSubjectIsNull() throws Exception {
        MimeMessage message = new MimeMessage(Session.getDefaultInstance(new Properties()));
        String prefix = "prefix";
        String originalSubject = "original subject";
        Optional<String> newSubject = new MimeMessageUtils(message).buildNewSubject(prefix, originalSubject, null);

        assertThat(newSubject).contains(prefix + " " + originalSubject);
    }

    @Test
    public void buildNewSubjectShouldPrefixNewSubjectWhenSubjectIsGiven() throws Exception {
        MimeMessage message = new MimeMessage(Session.getDefaultInstance(new Properties()));
        String prefix = "prefix";
        String originalSubject = "original subject";
        String subject = "new subject";
        Optional<String> newSubject = new MimeMessageUtils(message).buildNewSubject(prefix, originalSubject, subject);

        assertThat(newSubject).contains(prefix + " " + subject);
    }

    @Test
    public void buildNewSubjectShouldReplaceSubjectWhenPrefixIsNull() throws Exception {
        MimeMessage message = new MimeMessage(Session.getDefaultInstance(new Properties()));
        String prefix = null;
        String originalSubject = "original subject";
        String subject = "new subject";
        Optional<String> newSubject = new MimeMessageUtils(message).buildNewSubject(prefix, originalSubject, subject);

        assertThat(newSubject).contains(subject);
    }

    @Test
    public void buildNewSubjectShouldReplaceSubjectWhenPrefixIsEmpty() throws Exception {
        MimeMessage message = new MimeMessage(Session.getDefaultInstance(new Properties()));
        String prefix = "";
        String originalSubject = "original subject";
        String subject = "new subject";
        Optional<String> newSubject = new MimeMessageUtils(message).buildNewSubject(prefix, originalSubject, subject);

        assertThat(newSubject).contains(subject);
    }

    @Test
    public void buildNewSubjectShouldReplaceSubjectWithPrefixWhenSubjectIsEmpty() throws Exception {
        MimeMessage message = new MimeMessage(Session.getDefaultInstance(new Properties()));
        String prefix = "prefix";
        String originalSubject = "original subject";
        String subject = "";
        Optional<String> newSubject = new MimeMessageUtils(message).buildNewSubject(prefix, originalSubject, subject);

        assertThat(newSubject).contains(prefix);
    }
}
