@XmlSchema(
        xmlns = {
                @XmlNs(namespaceURI = "wsapi:Payment", prefix = "urn"),
                @XmlNs(namespaceURI = "wsapiUtils", prefix = "uts")
        },
        elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED
)

package ru.novikov.jaxb;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlSchema;