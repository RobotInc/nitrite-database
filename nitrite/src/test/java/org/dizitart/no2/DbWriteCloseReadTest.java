/*
 *
 * Copyright 2017-2018 Nitrite author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.dizitart.no2;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Anindya Chatterjee.
 */
public class DbWriteCloseReadTest {
    private volatile boolean writeCompleted = false;
    private final DbTestOperations readWriteOperations = new DbTestOperations();

    @Test
    public void testWriteCloseRead() throws Exception {
        try {
            readWriteOperations.createDb();
            readWriteOperations.writeCollection();
            readWriteOperations.writeIndex();
            readWriteOperations.insertInCollection();
        } catch (ParseException pe) {
            // ignore
        } finally {
            writeCompleted = true;
        }

        try {
            assertTrue(writeCompleted);
            readWriteOperations.readCollection();
        } catch (Exception e) {
            fail("collection read failed - " + e.getMessage());
        } finally {
            readWriteOperations.deleteDb();
        }
    }
}
