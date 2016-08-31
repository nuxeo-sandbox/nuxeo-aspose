package org.nuxeo.aspose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.aspose.AsposeConvertOp;
import org.nuxeo.common.utils.FileUtils;
import org.nuxeo.ecm.automation.AutomationService;
import org.nuxeo.ecm.automation.OperationChain;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.OperationException;
import org.nuxeo.ecm.automation.test.AutomationFeature;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.impl.blob.FileBlob;
import org.nuxeo.ecm.core.test.DefaultRepositoryInit;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

@RunWith(FeaturesRunner.class)
@Features(AutomationFeature.class)
@RepositoryConfig(init = DefaultRepositoryInit.class, cleanup = Granularity.METHOD)
@Deploy("org.nuxeo.aspose.aspose-plugin-core")

public class TestAsposeConvert {

    private static final String WORD_DOC_INPUT = "Test.docx";

    @Inject
    protected CoreSession session;

    @Inject
    protected AutomationService automationService;

    @Test
    public void shouldCallTheOperation() throws OperationException {
    	
        // Get the local file to test
    	File docFile = FileUtils.getResourceFileFromContext(WORD_DOC_INPUT);
    	// Encapsulate in a Blob (FileBlob here)
    	FileBlob fb = new FileBlob(docFile);

    	// Create a context
        OperationContext ctx = new OperationContext(session);
        // Set the input of the operation
        ctx.setInput(fb);
        // Create the chain that will have only one operation
        OperationChain chain = new OperationChain("testChain");
        chain.add(AsposeConvertOp.ID);

        // Run it
        FileBlob result = (FileBlob) automationService.run(ctx, chain);
        assertNotNull(result);
        assertEquals("application/pdf", result.getMimeType());
    }
}
