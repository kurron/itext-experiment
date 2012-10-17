package org.kurron.itext

import spock.lang.Specification
import com.itextpdf.text.Document
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Chunk
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Font
import com.itextpdf.text.pdf.BaseFont

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
        BaseFont base = BaseFont.createFont( "/home/ron/git/itext-experiment/src/test/resources/GenR102.TTF", BaseFont.CP1250, BaseFont.EMBEDDED )
        println 'encoding = ' + base.encoding
        base.fullFontName.each {
            println 'fullFontName = ' + it
        }
        base.familyFontName.each {
            println 'familyFontName = ' + it
        }
        base.codePagesSupported.each {
            println 'code page = ' + it
        }
        Font font = new Font( base, 24 )
        Chunk chunk = new Chunk( "Aduniya kuna n gu ibuna damayo hɛi nɔ dei-dei nn daama nna n burucinitɛrɛ fɔ, n lasabu nna laakari ya nam nn mɔ huro cɛrɛ kuna nyanze tɛrɛ bɔŋɔɔ.", font );
        document.add( chunk )
        document.add( Chunk.NEWLINE )
        document.close()
        stream.close()

        then: "pdf file is created"
    }
}
