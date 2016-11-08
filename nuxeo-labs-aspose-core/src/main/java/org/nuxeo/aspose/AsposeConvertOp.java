package org.nuxeo.aspose;

import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.CoreSession;


/**
 *
 */
@Operation(id=AsposeConvertOp.ID, category=Constants.CAT_DOCUMENT, label="Convert Word to PDF", description="Converts a Word document to a PDF")
public class AsposeConvertOp {

    public static final String ID = "Document.AsposeConvertWordToPdf";

    @Context
    protected CoreSession session;

    @Param(name = "path", required = false)
    protected String path;

    /**
     * Runs the AsposeConvert convertWordToPdf()
     * @param inBlob
     * @return Blob
     * @throws UnsupportedOperationException
     */
    @OperationMethod
    public Blob run(Blob inBlob) throws UnsupportedOperationException {
    	AsposeConvert ac = new AsposeConvert();
        return ac.convertWordToPdf(inBlob);
    }
}
