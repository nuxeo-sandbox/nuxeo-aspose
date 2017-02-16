package org.nuxeo.aspose;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.Blobs;

import com.aspose.pdf.Document;

public class AsposeRearrange {
	protected Blob rearrangePdf(Blob inBlob, String pageOrder) {
		ArrayList<Document> splitPdfList = splitPdf(inBlob);
		Blob outBlob = null;
		try {
			outBlob = rearrangeThePdf(splitPdfList, pageOrder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outBlob;
	}
	
	private Blob rearrangeThePdf(ArrayList<Document> splitPdfList, String pageOrder) throws IOException {
		ArrayList<Document> orderedPdfList = new ArrayList<Document>();
		String[] pageOrderArray = pageOrder.split(",");
		
		for(int i = 0; i < pageOrderArray.length; i++) {
			int page = Integer.parseInt(pageOrderArray[i]); //get page # ... 5,1,3,4,2
			for (int j = 1; j <= splitPdfList.size(); j++) {
				//Add page in new position
				if(page == j) {
					orderedPdfList.add(splitPdfList.get(j));
					break;
				}
			}
		}
		return convertToBlob(convertToDocument(orderedPdfList));
	}
	
	private Document convertToDocument(ArrayList<Document> docList) {
		Document newDoc = new Document();
		for (int i = 1; i <= docList.size(); i++) {
			Document d = docList.get(i);
			// Add the pages of the source document to newDoc
			newDoc.getPages().add(d.getPages());
		}
		// Save the new concatenated document
		newDoc.save();
		return newDoc;
	}
	
	private Blob convertToBlob(Document d) throws IOException {
		OutputStream outStream = new ByteArrayOutputStream();
		d.save(outStream);
		byte[] savedBytes = ((ByteArrayOutputStream)outStream).toByteArray();
		Blob b = Blobs.createBlob(savedBytes);
		outStream.close();
		return b;
	}
	
	private ArrayList<Document> splitPdf(Blob inBlob) {
		ArrayList<Document> docList = new ArrayList<Document>();
		// Open a document
		Document masterPdf = new Document((InputStream) inBlob);
		
		// Loop through the pages, create a new PDF Document for each page, add PDF Document to list
		for (int pdfPage = 1; pdfPage <= masterPdf.getPages().size(); pdfPage++) {
			// Create a new Document object
			Document newDocument = new Document();
			// Get the page at a given index of the Page Collection
			newDocument.getPages().add(masterPdf.getPages().get_Item(pdfPage));
			// Save the new PDF file
			newDocument.save("page_" + pdfPage + ".pdf");
			docList.add(newDocument);
		}
		return docList;
	}
}
