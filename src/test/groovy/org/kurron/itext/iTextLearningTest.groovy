package org.kurron.itext

import spock.lang.Specification
import com.itextpdf.text.Document
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Chunk
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Font

/**
 * Trying to figure out how the iText library works.
 */
class iTextLearningTest extends Specification
{
    def "hello"()
    {
        given: "document"
        Document document = new Document()
        Closeable stream = new FileOutputStream( "hello.pdf" )
        PdfWriter writer = PdfWriter.getInstance( document, stream )

        when: "page is built"
        document.open()
        Font font = new Font( Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK );
        Chunk chunk = new Chunk( "Hello, World!", font );
        document.add( chunk )
        document.add( Chunk.NEWLINE )
        document.close()
        stream.close()

        then: "pdf file is created"
    }
}
