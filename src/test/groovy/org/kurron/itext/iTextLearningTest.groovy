package org.kurron.itext

import spock.lang.Specification
import com.itextpdf.text.Document
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.Paragraph

/**
 * Trying to figure out how the iText library works.
 */
class iTextLearningTest extends Specification
{
    def "hello"()
    {
        given: "document"
        Document document = new Document()
        PdfWriter writer = PdfWriter.getInstance( document, new FileOutputStream( "hello.pdf" ) )

        when: "page is built"
        document.open()
        document.add( new Paragraph( "Hello, World!" ) )
        document.close()

        then: "pdf file is created"
    }
}
