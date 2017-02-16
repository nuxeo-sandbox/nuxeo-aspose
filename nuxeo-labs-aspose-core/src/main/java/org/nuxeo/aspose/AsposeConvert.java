/*
 * (C) Copyright 2016 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     thibaud
 */
package org.nuxeo.aspose;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.Blobs;
import org.nuxeo.ecm.core.api.NuxeoException;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;

/**
 * Wrapper class for Aspose Word doc and PDF manipulation
 */
public class AsposeConvert {

    public Blob convertWordToPdf(Blob inBlob) throws NuxeoException {
        InputStream inStream;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            // Get InputStream from inbBlob
            inStream = inBlob.getStream();

            // Create Aspose document using InputStream
            Document doc = new Document(inStream);
            inStream.close();

            // Save as PDF to ByteArrayOutputStream
            doc.save(outStream, SaveFormat.PDF);

            // Create new InputStream from OutputStream
            InputStream is = new ByteArrayInputStream(outStream.toByteArray());
            outStream.close();

            // Create a temp.Blob from InputStream
            Blob result = Blobs.createBlobWithExtension(".pdf");
            File resultFile = result.getFile();
            Files.copy(is, Paths.get(resultFile.getPath()),
                    StandardCopyOption.REPLACE_EXISTING);
            is.close();

            // Set blob info
            String fileName = inBlob.getFilename();
            if (fileName == null) {
                fileName = resultFile.getName();
            }
            int pos = fileName.lastIndexOf(".");
            if (pos > 0) {
                fileName = fileName.substring(0, pos);
            }
            fileName += ".pdf";
            result.setFilename(fileName);
            result.setMimeType("application/pdf");
            return result;

        } catch (IOException e) {
            throw new NuxeoException(e);
        } catch (Exception e) {
            throw new NuxeoException(e);
        }

    }
}
