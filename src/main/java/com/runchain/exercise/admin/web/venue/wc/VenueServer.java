
package com.runchain.exercise.admin.web.venue.wc;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "VenueServer", targetNamespace = "http://service.venue.yanhl.net/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface VenueServer {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "checkVenueNameExist", targetNamespace = "http://service.venue.yanhl.net/", className = "com.runchain.exercise.admin.web.venue.wc.CheckVenueNameExist")
    @ResponseWrapper(localName = "checkVenueNameExistResponse", targetNamespace = "http://service.venue.yanhl.net/", className = "com.runchain.exercise.admin.web.venue.wc.CheckVenueNameExistResponse")
    public String checkVenueNameExist(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

}