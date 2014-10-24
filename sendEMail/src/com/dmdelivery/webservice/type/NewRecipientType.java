package com.dmdelivery.webservice.type;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This datatype represents a recipient. It consists of a number of name/value
 * pairs. Use 'getRecipientFields' to find out what field names you should use.
 * Used when adding a new recipient.
 * 
 * <p>
 * Java class for NewRecipientType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="NewRecipientType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fields" type="{http://dmdelivery.com/webservice/}RecipientNameValuePairType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NewRecipientType", propOrder = { "fields" })
public class NewRecipientType {

	@XmlElement(required = true)
	protected List<RecipientNameValuePairType> fields;

	public NewRecipientType(String string, String string2, String string3) {
		if(fields == null){
			fields = new ArrayList<RecipientNameValuePairType>();
		}
		fields.add(new RecipientNameValuePairType("email",string));
		fields.add(new RecipientNameValuePairType("firstname",string2));
		fields.add(new RecipientNameValuePairType("lastname",string3));
	}

	public NewRecipientType() {
		// TODO Auto-generated constructor stub
	}

	public void setRecipientParameter(String name, String value){
		if(fields == null){
			fields = new ArrayList<RecipientNameValuePairType>();
		}
		fields.add(new RecipientNameValuePairType(name, value));
	}
	
	public void setRecipientParameter(String name, Integer value){
		if(fields == null){
			fields = new ArrayList<RecipientNameValuePairType>();
		}
		fields.add(new RecipientNameValuePairType(name, value.toString()));
	}
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
