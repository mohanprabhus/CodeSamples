package com.example.javafx.richtext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


class Handler extends DefaultHandler { //(xml.sax.handler.ContentHandler):

    
    
    private String content = "";    // TODO: use StringBuffer?
    private int sectionLevel = 0;
    private DocbookHandler handler;

    public Handler(String contentPath, DocbookHandler handler) {
        this.handler = handler;
    }


    @Override
    public void startElement(String uri, String localName, String name,
            Attributes attributes) throws SAXException {

        // structural tags
        if (name.equals("article")) {
            // self.nodeStack = [Frame()]
        }
        else if (name.equals("section")) {
            sectionLevel += 1;
        }
        else if (name.equals("itemizedlist")) {  // create a List node and set it as current parent
            // self.listLevel += 1
            // curList = List(('itemizedlist', 'level', str(self.listLevel)))
            // parent = self.nodeStack[-1]
            // parent.add(curList)
            // self.nodeStack.append(curList)              # push
        }
        else if (name.equals("mediaobject")) {
        }
        else if (name.equals("imageobject")) {
        }
        else if (name.equals("inlineequation")) {
        }
        else if (name.equals("listitem")) {
        }

        // These contain <para> elements, but already define the paragraph style:
        else if (name.equals("blockquote")) {
            // self.paraStyle = ('blockquote', None, None)
        }
        else if (name.equals("tip")) {
            // self.paraStyle = ('tip', None, None)
        }
        else if (name.equals("warning")) {
            // self.paraStyle = ('warning', None, None)
        }
        else if (name.equals("title")) { // create a title paragraph and set it as current parent
            if (sectionLevel > 0) { // no title for <article>
            //    para = Paragraph(0, ('title', 'level', str(self.sectionLevel)))
            //    parent = self.nodeStack[-1]                    # top()
            //    self.nodeStack.append(para)   # push()
            //    parent.add(para)
            }
            content = "";
        }
        else if (name.equals("para")) {  // # a paragraph contains only fragments
            //if self.paraStyle is None:
            //    self.paraStyle = ('para', None, None)
            //para = Paragraph(0, self.paraStyle)

            //parent = self.nodeStack[-1]                    # top()
            //self.nodeStack.append(para)   # push()
            //parent.add(para)
            content = "";       // start collecting content
            //self.currentStyle = None    # no specific style currently
        }
        else if (name.equals("programlisting")) {    //  # a program listing contains verbatim text only
            String language = attributes.getValue("language");
            //para = Paragraph(0, ('programlisting', 'language', language))
            //parent = self.nodeStack[-1]                    # top()
            //self.nodeStack.append(para)   # push()
            //parent.add(para)
            content = "";           // start collecting content
            //self.currentStyle = None    # no specific style currently
        }
        else if (name.equals("screen")) {    // a screen contains verbatim text only
            //para = Paragraph(0, ('screen', None, None))
            //parent = self.nodeStack[-1]                    # top()
            //self.nodeStack.append(para)   # push()
            //parent.add(para)
            content = "";               // start collecting content
            //self.currentStyle = None    # no specific style currently
        }

        // These are the fragments which are added to the current paragraph
        else if (name.equals("emphasis")) {
            //# Add content so far to the current paragraph
            //if len(self.content) > 0:
            //    parent = self.nodeStack[-1]
    
            //    frag = TextFragment(self.currentStyle)
            //    frag.setText(self.content)
            //    self.content = ''
            //    parent.add(frag)

            //emphasizeRole = attrs.get('role', '')
            //if emphasizeRole == '':
            //    self.currentStyle = ('emphasis', None, None)    # todo: nested styles support (needs yet another stack ...)
            //else:
            //    self.currentStyle = ('emphasis', 'role', emphasizeRole)    # todo: nested styles support (needs yet another stack ...)
            //            else if (name.equals("code':
            //# Add content so far to the current paragraph
            //if len(self.content) > 0:
            //    parent = self.nodeStack[-1]
    
            //    frag = TextFragment(self.currentStyle)
            //    frag.setText(self.content)
            //    self.content = ''
            //    parent.add(frag)

            // self.currentStyle = ('code', None, None)    # todo: nested styles support (needs yet another stack ...)
        }
        else if (name.equals("link")) {
            //# Add content so far to the current paragraph
            //if len(self.content) > 0:
            //    parent = self.nodeStack[-1]
            //    frag = TextFragment(self.currentStyle)
            //    frag.setText(self.content)
            //    self.content = ''
            //    parent.add(frag)

            // self.href = attrs.get('xlink:href', '')
            // self.currentStyle = None            # todo: nested styles support (needs yet another stack ...)
        }
        else if (name.equals("olink")) {
            //# Add content so far to the current paragraph
            //if len(self.content) > 0:
            //    parent = self.nodeStack[-1]
            //    frag = TextFragment(self.currentStyle)
            //    frag.setText(self.content)
            //    self.content = ''
            //    parent.add(frag)

            //self.currentStyle = ('olink', None, None)    # todo: nested styles support (needs yet another stack ...)
        }
        else if (name.equals("imagedata")) {
            //parent = self.nodeStack[-1]

            //# Add content so far to the current paragraph
            //if len(self.content) > 0:
            //    frag = TextFragment(self.currentStyle)
            //    frag.setText(self.content)
            //    self.content = ''
            //    parent.add(frag)

            //imageFile = attrs.getValue('fileref')
            //frag = ImageFragment()
            //frag.setImage(imageFile)
            //parent.add(frag)
        }
        else if (name.equals("mathphrase")) {
            //parent = self.nodeStack[-1]

            //# Add content so far to the current paragraph
            //if len(self.content) > 0:
            //    frag = TextFragment(self.currentStyle)
            //    frag.setText(self.content)
            //    parent.add(frag)

            content = "";
        }
    }


    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        String data = new String(ch, start, length);
        if (content != null) {
            content = content + data;
        }

    }

    @Override
    public void endElement(String uri, String localName, String name)
            throws SAXException {

        // structural tags
        if (name.equals("article")) {
            // self.result = self.nodeStack[0]
        }
        else if (name.equals("section")) {
            sectionLevel -= 1;
        }
        else if (name.equals("itemizedlist")) {
            // self.nodeStack = self.nodeStack[0:-1]     # pop()
            content = null;
            // self.currentStyle = None
            // self.listLevel -= 1
        }
        else if (name.equals("mediaobject")) {
        }
        else if (name.equals("imageobject")) {
        }
        else if (name.equals("inlineequation")) {
        }

        // These contain <para> elements:
        else if (name.equals("listitem")) {
        }
        else if (name.equals("blockquote")) {
        }
        else if (name.equals("tip")) {
        }
        else if (name.equals("warning")) {
        }

        else if (name.equals("title")) {
            if (sectionLevel > 0) {
            //    parent = self.nodeStack[-1]
            //    self.nodeStack = self.nodeStack[0:-1]     # pop()
            //    frag = TextFragment(None)
            //    frag.setText(self.content)
            //    parent.add(frag)
                System.err.println("TITLE:" + content);
                handler.addTitle(0, content);
            }
            content = null;
        }
        else if (name.equals("para")) {
            if (!content.isEmpty()) {
            //    parent = self.nodeStack[-1]

             //   frag = TextFragment(self.currentStyle)
            //    frag.setText(self.content)
                System.err.println("FRAG:" + content);
                handler.addParagraph(content);
                content = null;

            //    parent.add(frag)
            //    self.currentStyle = None            # todo: nested styles support (needs yet another stack ...)
            //    self.paraStyle = None
            }

            // self.nodeStack = self.nodeStack[0:-1]     # pop()
        }
        else if (name.equals("programlisting")) {
            if (!content.isEmpty()) {
                System.err.println("CODE:" + content);
                handler.addCode(content);
            //    parent = self.nodeStack[-1]

            //    frag = TextFragment(self.currentStyle)
            //    frag.setText(self.content)
            //    self.content = None
                content = null;
            //    parent.add(frag)
            //    self.currentStyle = None            # todo: nested styles support (needs yet another stack ...)
            }

            //self.nodeStack = self.nodeStack[0:-1]     # pop()
        }
        else if (name.equals("screen")) {
            if (!content.isEmpty()) {
                System.err.println("SCREEN:" + content);
            //    parent = self.nodeStack[-1]

            //    frag = TextFragment(self.currentStyle)
            //    frag.setText(self.content)
                content = null;
            //    parent.add(frag)
            //    self.currentStyle = None            # todo: nested styles support (needs yet another stack ...)
            }

            // self.nodeStack = self.nodeStack[0:-1]     # pop()
        }
        else if (name.equals("emphasis")) {
            //parent = self.nodeStack[-1]

            System.err.println("<em>" + content + "</em>");

            //frag = TextFragment(self.currentStyle)
            //frag.setText(self.content)
            content = "";
            //parent.add(frag)
            //self.currentStyle = None                # todo: nested styles support (needs yet another stack ...)
        }
        else if (name.equals("code")) {
            //parent = self.nodeStack[-1]

            System.err.println("<tt>" + content + "</tt>");

            //frag = TextFragment(self.currentStyle)
            //frag.setText(self.content)
            content = "";
            //parent.add(frag)
            //self.currentStyle = None                # todo: nested styles support (needs yet another stack ...)
        }
        else if (name.equals("link")) {
            //parent = self.nodeStack[-1]

            //frag = TextFragment(self.currentStyle)
            //frag.setText(self.content)
            //frag.setHref(self.href)
            //self.href = None
            //self.content = ''
            //parent.add(frag)
            //self.currentStyle = None                # todo: nested styles support (needs yet another stack ...)
        }
        else if (name.equals("olink")) {
            //parent = self.nodeStack[-1]

            //frag = TextFragment(self.currentStyle)
            //frag.setText(self.content)
            //parent.add(frag)

            //self.keywordLinks.add(self.content) 

            //self.content = ''
            //self.currentStyle = None                # todo: nested styles support (needs yet another stack ...)
        }
        else if (name.equals("imagedata")) {
        }
        else if (name.equals("mathphrase")) {
            System.err.println("MATH: " + content);
            //mathFormula = MathFormulaObject()
            //mathFormula.setFormula(self.content)
            //mathFormula.renderFormula()             # generate image - TODO: is there a better approach?
            //                                        # Do we need the image as part of the fragment?
            //parent = self.nodeStack[-1]
            //frag = MathFragment()
            //frag.setText(mathFormula.formula)
            //frag.setImage(mathFormula.image)
            //parent.add(frag)

            //self.currentStyle = None                # todo: nested styles support (needs yet another stack ...)
            content = "";
        }
    }

}


public class DocbookImporter {
    
    private final String contentPath;
    private final String contentFile;
    private DocbookHandler docHandler;

    /**
     * 
     * @param contentPath The path to the docbook xml file
     * @param contentFile The name of the docbook xml file to import
     */
    public DocbookImporter(String contentPath, String contentFile) { // , formatManager):
        this.contentPath = contentPath;
        this.contentFile = contentFile;
        // this.formatManager = formatManager
    }

    public void importDocument(DocbookHandler docbookHandler) {
        this.docHandler = docbookHandler;
        String contentFilePath = Paths.get(contentPath, contentFile).toString();
        try (InputStream is = new FileInputStream(contentFilePath)) {   // TODO: Encoding??
            importFromFile(is);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importFromFile(InputStream is) {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = parserFactory.newSAXParser();
            System.err.println("Using " + parser.getClass());
            DefaultHandler handler = new Handler(contentPath, docHandler);
            parser.parse(is, handler);
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
