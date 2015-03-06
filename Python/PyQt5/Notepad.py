'''
Created on 24.02.2015

@author: afester
'''

from XMLImporter import XMLImporter
from XMLExporter import XMLExporter
from FormatManager import FormatManager
import os, urllib.parse, uuid


class Notepad:

    def __init__(self, npDef):
        self.type = npDef['type']
        self.name = npDef['name']

        if self.type == 'local':
            self.rootPath = npDef['path']
        else:
            self.rootPath = 'dropbox:' + self.name

        self.formatManager = FormatManager()
        self.formatManager.loadFormats()


    def ensureExists(self):
        if not os.path.isdir(self.getRootpath()):
            print(self.getRootpath() + " does not exist, creating directory ...")
            os.mkdir(self.getRootpath())

        contentFile = os.path.join(self.getRootpath(), 'content.xml')
        if not os.path.isfile(contentFile):
            print(contentFile + " does not exist, creating file ...")
            with open(contentFile, 'w') as fd:
                fd.write('''<?xml version = '1.0' encoding = 'UTF-8'?>
<page>
  <h1>{}</h1>
</page>
'''.format(self.getName()))


    def getPage(self, pageId):
        result = Page(self, pageId)
        result.load()
        return result


    def getName(self):
        return self.name


    def getType(self):
        return self.type


    def getFormatManager(self):
        return self.formatManager


    def getRootpath(self):
        return self.rootPath


class Page:

    def __init__(self, notepad, pageId):
        assert pageId is None or type(pageId) is str

        self.notepad = notepad
        self.pageId = pageId
        self.links = []
        self.document = None


    def getName(self):
        if self.pageId is None:
            return "Title page"
        else:
            return self.pageId


    def getPagePath(self):
        pagePath = self.notepad.getRootpath()
        if self.pageId is not None:     # not the root page
            pageFilename = urllib.parse.quote(self.pageId)
            pageIdx = [self.pageId[0], pageFilename]
            pagePath = os.path.join(pagePath, *pageIdx)
        return pagePath


    def load(self):
        if self.notepad.getType() == 'local':
            pagePath = self.getPagePath()
            print("  Loading page at {} ".format(pagePath))

            if not os.path.isdir(pagePath):
                print(pagePath + " does not exist, creating directory ...")
                os.makedirs(pagePath)

            contentFile = os.path.join(pagePath, 'content.xml')
            if not os.path.isfile(contentFile):
                print(contentFile + " does not exist, creating file ...")
                with open(contentFile, 'w') as fd:
                    fd.write('''<?xml version = '1.0' encoding = 'UTF-8'?>
<page>
  <h1>{}</h1>
</page>
'''.format(self.pageId))

            importer = XMLImporter(pagePath, "content.xml", self.notepad.getFormatManager())
            importer.importDocument()
            self.document = importer.getDocument()
            self.links = importer.getLinks()


    def save(self):
        pagePath = self.getPagePath()
        print("  Saving page to {} ".format(pagePath))

        exporter = XMLExporter(pagePath, 'content.xml')
        exporter.exportDocument(self.document)

        self.document.setModified(False)


#===============================================================================
#         with open(self.contentFile, 'w') as content_file:
#             # NOTE: toHtml() writes the text document in a "hard coded"
#             # HTML format, converting all style properties to inline style="..." attributes
#             # See QTextHtmlExporter in qtbase/src/gui/text/qtextdocument.cpp.
#             # We are using a custom XML exporter therefore ...
# 
#             #content_file.write(self.editView.toHtml())
# 
#             exporter = XMLExporter(content_file)
#             exporter.exportDocument(self.editView.document())
#===============================================================================

    def saveImage(self, image):
        fileName = str(uuid.uuid4()).replace('-', '') + '.png'
        filePath = os.path.join(self.getPagePath(), fileName)
        print("Saving image to {}".format(filePath))
        image.save(filePath)

        return fileName


    def getLinks(self):
        return self.links


    def getDocument(self):
        return self.document
