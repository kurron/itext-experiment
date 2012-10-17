package org.kurron.itext

import spock.lang.Specification

/**
 * Trying to figure out how the iText library works.
 */
class iTextLearningTest extends Specification
{
    def "hello"()
    {
        given: "document"
        when: "page is built"
        then: "pdf file is created"
    }
}
