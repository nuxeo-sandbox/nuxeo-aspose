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
@Operation(id=AsposeRearrangePDFOp.ID, category=Constants.CAT_DOCUMENT, label="Rearrange PDF pages", description="Rearranges PDF pages")
public class AsposeRearrangePDFOp {

    public static final String ID = "Document.AsposeRearrangePdf";

    @Context
    protected CoreSession session;

    @Param(name = "pageOrder", required = true)
    protected String pageOrder;

    @OperationMethod
    public Blob run(Blob inBlob) throws UnsupportedOperationException {
    	AsposeRearrange ar = new AsposeRearrange();
        return ar.rearrangePdf(inBlob, pageOrder);
    }
}
