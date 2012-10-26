package org.kurron.itext

import spock.lang.Specification
import com.itextpdf.text.Document
import com.itextpdf.text.pdf.PdfWriter

import com.itextpdf.text.Chunk

import com.itextpdf.text.Font
import com.itextpdf.text.pdf.BaseFont

import com.itextpdf.text.PageSize
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Element

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
        document.add( new Chunk( "私の友人は彼にこのテキストを与えるために私に尋ねた", createFont( '/home/ron/git/itext-experiment/src/test/resources/MSGOTHIC.TTF' ) ) )
        document.add( Chunk.NEWLINE )

        then: "pdf file is created"

        cleanup:
        document.close()
        stream.close()

    }

    def "bob"()
    {
        given: "open document"
        Document document = new Document( PageSize.A4 )
        Closeable stream = new FileOutputStream( "bob.pdf" )
        PdfWriter writer = PdfWriter.getInstance( document, stream )
        document.open()

        and: "one column table"
        PdfPTable table = new PdfPTable( 1 )
        table.setWidthPercentage( 100 )

        and: "loaded fonts"
        Font chineseSimplified = createFont( '/home/ron/git/itext-experiment/src/test/resources/HeiS.ttf' )
        Font chineseTraditional = createFont( '/home/ron/git/itext-experiment/src/test/resources/HeiS.plusLatin.ttf' )
        Font japanese = createFont( '/home/ron/git/itext-experiment/src/test/resources/MSGOTHIC.TTF' )
        Font korean = createFont( '/home/ron/git/itext-experiment/src/test/resources/Dotum.ttf' )

        when: "CJK text is added"
        table.addCell( createCell( korean, PdfWriter.RUN_DIRECTION_LTR, "내 친구는 그에게이 텍스트를 제공달라고 부탁했습니다" ) );
        table.addCell( createCell( japanese, PdfWriter.RUN_DIRECTION_LTR, "私の友人は彼にこのテキストを与えるために私に尋ねた" ) );
        table.addCell( createCell( chineseSimplified, PdfWriter.RUN_DIRECTION_LTR, "我的朋友问我给了他这个文本" ) );
        table.addCell( createCell( chineseTraditional, PdfWriter.RUN_DIRECTION_LTR, "我的朋友問我給了他這個文本" ) );

        then: "PDF contains the example CJK text"
        document.add( table )

        cleanup:
        document.close()
        stream.close()
    }

    def PdfPCell createCell( Font font, int direction, String text )
    {
        PdfPCell cell = createCell( direction )
        cell.addElement( createParagraph( font, text ) );
        cell
    }

    def Paragraph createParagraph( Font font, String text )
    {
        Paragraph paragraph = new Paragraph( text, font );
        paragraph.setAlignment( Element.ALIGN_LEFT );
        return paragraph
    }

    def PdfPCell createCell( int direction )
    {
        PdfPCell cell = new PdfPCell();
        cell.setBorder( PdfPCell.NO_BORDER );
        cell.setRunDirection( direction );
        return cell
    }

    def Font createFont( String fontName )
    {
        return new Font( createBaseFont( fontName ), 18 )
    }

    def BaseFont createBaseFont( String fontName )
    {
        BaseFont font = BaseFont.createFont( fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED )
        println 'font name = ' + font.postscriptFontName
        return font
    }


}
