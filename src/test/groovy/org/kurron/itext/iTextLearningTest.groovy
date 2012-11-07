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
import com.itextpdf.text.Image

/**
 * Trying to figure out how the iText library works.
 */
class iTextLearningTest extends Specification
{
    def "images"()
    {
        given: "document"
        Document document = new Document()
        Closeable stream = new FileOutputStream( "images.pdf" )
        PdfWriter writer = PdfWriter.getInstance( document, stream )

        when: "page is built"
        document.open()
        document.add( new Chunk( loadImage( "car.jpg" ), 0, 0, true ) )
        document.add( Chunk.NEWLINE )
        document.add( new Chunk( loadImage( "flower.png" ), 0, 0, true ) )
        document.add( Chunk.NEWLINE )
        document.add( new Chunk( loadImage( "ship.gif" ), 0, 0, true ) )
        document.add( Chunk.NEWLINE )
        document.add( new Chunk( loadImage( "puppy.bmp" ), 0, 0, true ) )

        then: "pdf file is created"

        cleanup:
        document.close()
        stream.close()
        writer.close()
    }

    private Image loadImage( String name )
    {
        URL url = getClass().getClassLoader().getResource( name )
        Image image = Image.getInstance( url )
        image.setRotationDegrees( -10 )
        image.scaleToFit( 500, 500 )
        return image
    }

    def "hello"()
    {
        given: "document"
        Document document = new Document()
        Closeable stream = new FileOutputStream( "hello.pdf" )
        PdfWriter writer = PdfWriter.getInstance( document, stream )

        when: "page is built"
        document.open()
        document.add( new Chunk( "私の友人は彼にこのテキストを与えるために私に尋ねた", createFont( 'MSGOTHIC.TTF' ) ) )
        document.add( Chunk.NEWLINE )
        document.add( new Chunk( "هلا وسهلا", createFont( 'TLArabic.ttf' ) ) )
        document.add( Chunk.NEWLINE )

        then: "pdf file is created"

        cleanup:
        document.close()
        stream.close()
        writer.close()
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
        Font chineseSimplified = createFont( 'HeiS.ttf' )
        Font chineseTraditional = createFont( 'HeiS.plusLatin.ttf' )
        Font japanese = createFont( 'MSGOTHIC.TTF' )
        Font korean = createFont( 'Dotum.ttf' )
        Font arabic = createFont( 'TLArabic.ttf' )
        Font russian = createFont( 'ARIAL.TTF' )

        when: "CJK text is added"
        table.addCell( createCell( korean, PdfWriter.RUN_DIRECTION_LTR, "내 친구는 그에게이 텍스트를 제공달라고 부탁했습니다" ) );
        table.addCell( createCell( japanese, PdfWriter.RUN_DIRECTION_LTR, "私の友人は彼にこのテキストを与えるために私に尋ねた" ) );
        table.addCell( createCell( chineseSimplified, PdfWriter.RUN_DIRECTION_LTR, "我的朋友问我给了他这个文本" ) );
        table.addCell( createCell( chineseTraditional, PdfWriter.RUN_DIRECTION_LTR, "我的朋友問我給了他這個文本" ) );
        table.addCell( createCell( russian, PdfWriter.RUN_DIRECTION_LTR, "добро пожаловать" ) );
        table.addCell( createCell( arabic, PdfWriter.RUN_DIRECTION_RTL, "هلا وسهلا" ) );
        document.add( table )

        then: "PDF contains the example CJK text"

        cleanup:
        document.close()
        stream.close()
        writer.close()
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

    def BaseFont createBaseFont( String resourceName )
    {
        def stream = getClass().getClassLoader().getResourceAsStream( resourceName )
        assert stream

        BaseFont font = BaseFont.createFont( resourceName , BaseFont.IDENTITY_H, BaseFont.EMBEDDED, BaseFont.CACHED, copyToByteArray( stream ), new byte[0] )
        println 'font name = ' + font.postscriptFontName
        return font
    }

    def byte[] copyToByteArray( final InputStream input ) throws IOException
    {
        final byte[] buffer
        if ( null == input )
        {
            buffer = new byte[0]
        }
        else
        {
            final ByteArrayOutputStream out = new ByteArrayOutputStream( 2048 )
            copyStream( input, out )
            buffer = out.toByteArray()
            input.close()
            out.close()
        }
        return buffer
    }

    def void copyStream( final InputStream input, final OutputStream output ) throws IOException
    {
        final byte[] buffer = new byte[4096];
        while( true )
        {
            final int bytesRead = input.read( buffer );
            if( -1 == bytesRead )
            {
                break;
            }
            output.write( buffer, 0, bytesRead );
        }
    }

}
