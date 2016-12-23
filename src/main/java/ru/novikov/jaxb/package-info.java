@XmlSchema(
        xmlns = {
                @XmlNs(namespaceURI = "wsapi:Utils", prefix = "uts"),
                @XmlNs(namespaceURI = "wsapi:Payment", prefix = "urn")
        },
        elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED
)

package ru.novikov.jaxb;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlSchema;