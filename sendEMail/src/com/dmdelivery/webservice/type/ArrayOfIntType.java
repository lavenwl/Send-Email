package com.dmdelivery.webservice.type;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This datatype represents a sequence/array of integers.
 * 
 * <p>
 * Java class for ArrayOfIntType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfIntType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="int" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfIntType", propOrder = { "_int" })
public class ArrayOfIntType {

	@XmlElement(name = "int", type = Integer.class)
	protected List<Integer> _int;

	public ArrayOfIntType(List<Integer> is) {
		// TODO Auto-generated constructor stub
		this._int = is;
	}
	
	public ArrayOfIntType() {
		// TODO Auto-generated constructor stub
	}

	public void addGroup(int groupId){
		if(_int == null){
			_int = new ArrayList<Integer>();
		}
		this._int.add(groupId);
	}
	
	/**
	 * Gets the value of the int property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the int property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getInt().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Integer }
	 * 
	 * 
	 */
	public List<Integer> getInt() {
		if (_int == null) {
			_int = new ArrayList<Integer>();
		}
		return this._int;
	}

}
