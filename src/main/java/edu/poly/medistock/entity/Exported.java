/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.poly.medistock.entity;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "exported")
@XmlAccessorType(XmlAccessType.FIELD)
public class Exported implements Serializable{
    private static final long serialVersionUID = 1L;
    String name, type;
    String ngayXuat;
    int exNumber;
    double billNumber;  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getExNumber() {
        return exNumber;
    }

    public void setExNumber(int exNumber) {
        this.exNumber = exNumber;
    }

    public double getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(long billNumber) {
        this.billNumber = billNumber;
    }

    public Exported(String name, String type, String ngayXuat, int exNumber, double billNumber) {
        this.name = name;
        this.type = type;
        this.ngayXuat = ngayXuat;
        this.exNumber = exNumber;
        this.billNumber = billNumber;
    }

    public String getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(String ngayXuat) {
        this.ngayXuat = ngayXuat;
    }
    
    
}
