/*
 * Copyright (c) 2017 Red Hat, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.redhat.mqe.lib;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import java.util.HashMap;
import java.util.Map;

/**
 * Message output formatter to python dict,
 * map, or any other object printable format.
 * Reusable from old client
 */
public class CoreJmsMessageFormatter extends JmsMessageFormatter {
    /**
     * Openwire -> AMQP mapping http://activemq.apache.org/amqp.html
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> formatMessage(Message msg, boolean hashContent) throws JMSException {
        Map<String, Object> result = new HashMap<>();
        addFormatJMS11(msg, result, hashContent);
        addFormatJMS20(msg, result);
        return result;
    }
}
