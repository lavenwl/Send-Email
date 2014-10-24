package com.dmdelivery.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.dmdelivery.webservice.type.RecipientNameValuePairType;

/**
 * This datatype represents additional fields for a recipient. Only field names
 * starting with DMD are allowed. Other field names are ignored.
 * 
 * <p>
 * Java class for AdditionRecipientFields complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="AdditionRecipientFields">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fields" type="{http://dmdelivery.com/webservice/}RecipientNameValuePairType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdditionRecipientFields", propOrder = { "fields" })
public class AdditionRecipientFields {

	protected List<RecipientNameValuePairType> fields;

	/**
	 * Gets the value of the fields property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the fields property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getFields().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link RecipientNameValuePairType }
	 * 
	 * 
	 */
	public List<RecipientNameValuePairType> getFields() {
		if (fields == null) {
			fields = new ArrayList<RecipientNameValuePairType>();
		}
		return this.fields;
	}

}
