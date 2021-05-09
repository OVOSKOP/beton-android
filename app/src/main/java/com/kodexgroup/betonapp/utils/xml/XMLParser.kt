package com.kodexgroup.betonapp.utils.xml

import com.kodexgroup.betonapp.utils.xml.interfaces.IXMLClass
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import kotlin.jvm.Throws

class XMLParser {

    @Throws(XmlPullParserException::class)
    fun getParser(xml: String) : XmlPullParser {
        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true

        val xpp = factory.newPullParser()
        xpp.setInput(
                StringReader(xml)
        )

        return xpp
    }

    inline fun <reified T : IXMLClass> getObjectFromParser(xpp: XmlPullParser, startTag: String) : List<T> {

        val list: MutableList<T> = mutableListOf()
        var obj: T? = null
        var tag = ""
        var isText = false

        try {
            while (xpp.eventType != XmlPullParser.END_DOCUMENT) {
                when (xpp.eventType) {
                    XmlPullParser.START_TAG -> {
                        tag = xpp.name
                        if (tag == startTag) {
                            obj = T::class.java.newInstance()
                        }
                    }

                    XmlPullParser.END_TAG -> {
                        if (!isText) {
                            obj?.setData(tag, null)
                        }
                        isText = false
                        if (xpp.name  == startTag) {
                            if (obj != null) {
                                list.add(obj)
                            }
                            obj = null
                            tag = ""
                        }
                    }

                    XmlPullParser.TEXT -> {
                        isText = true
                        obj?.setData(tag, xpp.text)
                    }
                }

                xpp.next()
            }

        } catch (e: NoSuchMethodException) {
            println("Method Not Exist")
        }

        return list
    }

}