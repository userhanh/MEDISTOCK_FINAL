/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.poly.medistock.dao;

import edu.poly.medistock.entity.Equipment;
import edu.poly.medistock.entity.EquipmentXML;
import edu.poly.medistock.entity.Exported;
import edu.poly.medistock.entity.PharMed;
import edu.poly.medistock.entity.PharMedXML;
import edu.poly.medistock.utils.FileUtils;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author ADMINZ
 */
//lớp chứa các phương thức quản lý sinh viên: thêm, sửa, xóa, sắp xếp, đọc, ghi sinh viên
public class PharMedDao {

    private static final String PHARMED_FILE_NAME = "pharmed.xml";
    private static final String EQUIPMENT_FILE_NAME = "equipment.xml";
    private static final String EXPORTED_FILE_NAME = "exported.xml";
    List<PharMed> listPharMeds;
    List<Equipment> listEquipments;
    List<Exported> listExporteds;

    public PharMedDao() {
        this.listPharMeds = readListPharMeds();
        if (listPharMeds == null) {
            listPharMeds = new ArrayList<PharMed>();
        }

        this.listEquipments = readListEquipments();
        if (listEquipments == null) {
            listEquipments = new ArrayList<>();
        }
    }

    //lưu các đối tượng vào file xml
    public void writeListPharMeds(List<PharMed> pharMeds) {
        PharMedXML pharMedXML = new PharMedXML();
        pharMedXML.setPharmed(pharMeds);

        //gọi phương thức write
        FileUtils.writeXMLtoFile(PHARMED_FILE_NAME, pharMedXML);
    }

    public void writeListEquipment(List<Equipment> equipments) {
        EquipmentXML equipmentXML = new EquipmentXML();
        equipmentXML.setEquipments(equipments);

        //gọi phương thức write 
        FileUtils.writeXMLtoFile(EQUIPMENT_FILE_NAME, equipmentXML);
    }



    //đọc danh sách
    public List<PharMed> readListPharMeds() {
        //khai báo list chứa
        List<PharMed> list = new ArrayList<PharMed>();
        //đọc
        PharMedXML pharMedXML = (PharMedXML) FileUtils.readXMLFile(PHARMED_FILE_NAME, PharMedXML.class);
        if (pharMedXML != null) {
            list = pharMedXML.getPharmed();
        }
        return list;
    }

    public List<Equipment> readListEquipments() {
        List<Equipment> list = new ArrayList<>();

        EquipmentXML equipmentXML = (EquipmentXML) FileUtils.readXMLFile(EQUIPMENT_FILE_NAME, EquipmentXML.class);
        if (equipmentXML != null) {
            list = equipmentXML.getEquipments();
        }
        return list;
    }

    //thêm pharmed vào listPharMeds và lưu listPharMeds vào file
    public void addPharMed(PharMed pharMed) {
        int id = 1;
        if (listPharMeds != null && listPharMeds.size() > 0) {
            id = listPharMeds.size() + 1;
            for (PharMed each : listPharMeds) {
                if (each.getId() == id) {
                    id++; //để không bị trùng ID (nếu mình từng xóa)
                }
            }
        }
        //thêm
        pharMed.setId(id);
        listPharMeds.add(pharMed);
        writeListPharMeds(listPharMeds);
    }

    public void addEquip(Equipment equipment) {
        int id = 1;
        if (listEquipments != null && listEquipments.size() > 0) {
            id = listEquipments.size() + 1;
            for (Equipment x : listEquipments) {
                if (x.getId() == id) {
                    id++;
                }
            }
        }
        //thêm
        equipment.setId(id);
        listEquipments.add(equipment);
        writeListEquipment(listEquipments);
    }

    //chỉnh sửa pharmed vào listPharMed và lưu listPharmeds vào file
    public void editPharMed(PharMed pharMed) {
        int size = listPharMeds.size();
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            System.out.println(listPharMeds.get(i).getId() + " " + pharMed.getId());
            if (listPharMeds.get(i).getId() == pharMed.getId()) {
                System.out.println(i);
                listPharMeds.get(i).setName(pharMed.getName());
                listPharMeds.get(i).setNSX(pharMed.getNSX());
                listPharMeds.get(i).setHSD(pharMed.getHSD());
                listPharMeds.get(i).setSource(pharMed.getSource());
                listPharMeds.get(i).setBill(pharMed.getBill());
                listPharMeds.get(i).setSet(pharMed.getSet());
                listPharMeds.get(i).setNumber(pharMed.getNumber());
                listPharMeds.get(i).setPrice(pharMed.getPrice());

                writeListPharMeds(listPharMeds); //để trong hoặc đều được?
                break;
            }
        }
        System.out.println("Đã sửa thuốc");
    }

    //chỉnh sửa eqiup và lưu vào file 
    public void editEquip(Equipment equipment) {
        int size = listEquipments.size();

        for (int i = 0; i < size; i++) {
            if (listEquipments.get(i).getId() == equipment.getId()) {
                System.out.println(i);
                listEquipments.get(i).setName(equipment.getName());
                listEquipments.get(i).setNSX(equipment.getNSX());
                listEquipments.get(i).setHSD(equipment.getHSD());
                listEquipments.get(i).setSource(equipment.getSource());
                listEquipments.get(i).setBill(equipment.getBill());
                listEquipments.get(i).setSet(equipment.getSet());
                listEquipments.get(i).setNumber(equipment.getNumber());
                listEquipments.get(i).setPrice(equipment.getPrice());

                writeListEquipment(listEquipments); //để trong hoặc đều được?
                break;
            }
        }
        System.out.println("Đã sửa vật tư y tế");
    }

    //TẤT CẢ PHẦN DƯỚI ĐÂY CHƯA CHỈNH SỦA
    // xóa: xóa chung luôn cả Pharmed và equip 
    public boolean deletePharMed(PharMed pharMeds) {
        boolean isFound = false;
        int size = listPharMeds.size();
        //tìm chọn theo tên 
        for (int i = 0; i < size; i++) {
            if (listPharMeds.get(i).getName().equals(pharMeds.getName())) {
                pharMeds = listPharMeds.get(i);
                isFound = true;
            }
        }
        if (isFound) {

            String name = pharMeds.getName();
            String type = "Thuốc";
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            int number = pharMeds.getNumber();
            double gia = pharMeds.getPrice() * number;

            Exported exported = new Exported(name, type, formattedDateTime, number, gia);
            daXuat.add(exported);

            listPharMeds.remove(pharMeds);
            writeListPharMeds(listPharMeds);
            listPharMeds.remove(pharMeds);
            writeListPharMeds(listPharMeds);
            return true;
        }
        return false;
    }

    public List<Exported> daXuat = new ArrayList<>();

    public boolean deleteEquip(Equipment equipment) {
        boolean isFound = false;
        int size = listEquipments.size();
        for (int i = 0; i < size; i++) {
            if (listEquipments.get(i).getName().equals(equipment.getName())) {
                equipment = listEquipments.get(i);
                isFound = true;
            }
        }
        if (isFound) {
            //Tạo 1 thể hiện export và thêm vào list exported

            String name = equipment.getName();
            String type = "Vật tư y tế";

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            int number = equipment.getNumber();
            double gia = number * equipment.getPrice();

            Exported exported = new Exported(name, type, formattedDateTime, number, gia);
            daXuat.add(exported);

            listEquipments.remove(equipment);
            writeListEquipment(listEquipments);
            return true;

        }
        return false;
    }

    //sắp xếp theo ID
    public void sortPharMedsByID() {
        Collections.sort(listPharMeds, new Comparator<PharMed>() {
            public int compare(PharMed pharmed1, PharMed pharmed2) { //do ID là kiểu số
                return pharmed1.getId() - pharmed2.getId();
            }
        });
    }

    public void sortEquipsByID() {
        Collections.sort(listEquipments, new Comparator<Equipment>() {
            public int compare(Equipment e1, Equipment e2) { //do ID là kiểu số
                return e1.getId() - e2.getId();
            }
        });
    }
    //sắp xếp theo tên

    public void sortPharMedsByName() {
        Collections.sort(listPharMeds, new Comparator<PharMed>() {
            public int compare(PharMed pharmed1, PharMed pharmed2) { //do ID là kiểu số
                return pharmed1.getName().compareTo(pharmed2.getName());
            }
        });
    }

    public void sortEquipsByName() {
        Collections.sort(listEquipments, new Comparator<Equipment>() {
            public int compare(Equipment e1, Equipment e2) { //do ID là kiểu số
                return e1.getName().compareTo(e2.getName());
            }
        });
    }

    //sắp xếp theo số tồn kho
    public void sortPharMedsByNumber() {
        Collections.sort(listPharMeds, new Comparator<PharMed>() {
            @Override
            public int compare(PharMed pharMed1, PharMed pharMed2) {
                return (int) (pharMed1.getNumber() - pharMed2.getNumber());
            }
        });
    }

    public void sortEquipsByNumber() {
        Collections.sort(listEquipments, new Comparator<Equipment>() {
            @Override
            public int compare(Equipment e1, Equipment e2) {
                return (int) (e1.getNumber() - e2.getNumber());
            }
        });
    }

    //sắp xếp theo khoảng giá
    public void sortPharMedsByPrice() {
        Collections.sort(listPharMeds, new Comparator<PharMed>() {
            @Override
            public int compare(PharMed pharmed1, PharMed pharmed2) { //do ID là kiểu số
                return (int) (pharmed1.getPrice() - pharmed2.getPrice());
            }
        });
    }

    public void sortEquipsByPrice() {
        Collections.sort(listEquipments, new Comparator<Equipment>() {
            @Override
            public int compare(Equipment e1, Equipment e2) { //do ID là kiểu số
                return (int) (e1.getPrice() - e2.getPrice());
            }
        });
    }

    //hàm sắp xếp theo HSD
    public void sortPharMedByHSD() {
        Collections.sort(listPharMeds, new Comparator<PharMed>() {
            @Override
            public int compare(PharMed pharMed1, PharMed pharMed2) {

                DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate date1 = LocalDate.parse(pharMed1.getHSD(), format);
                LocalDate date2 = LocalDate.parse(pharMed2.getHSD(), format);
                //có thể in ra
                return date1.compareTo(date2);
            }
        });
    }

    public void sortEquipsByHSD() {
        Collections.sort(listEquipments, new Comparator<Equipment>() {
            @Override
            public int compare(Equipment e1, Equipment e2) {

                DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate date1 = LocalDate.parse(e1.getHSD(), format);
                LocalDate date2 = LocalDate.parse(e2.getHSD(), format);
                //có thể in ra
                return date1.compareTo(date2);
            }
        });
    }

    //sắp xếp theo NSX
    public void sortEquipsByNSX() {
        Collections.sort(listEquipments, new Comparator<Equipment>() {
            @Override
            public int compare(Equipment e1, Equipment e2) {

                DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate date1 = LocalDate.parse(e1.getNSX(), format);
                LocalDate date2 = LocalDate.parse(e2.getNSX(), format);
                //có thể in ra
                return date1.compareTo(date2);
            }
        });
    }

    public void sortPharMedsByNSX() {
        Collections.sort(listPharMeds, new Comparator<PharMed>() {
            @Override
            public int compare(PharMed p1, PharMed p2) {

                DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate date1 = LocalDate.parse(p1.getNSX(), format);
                LocalDate date2 = LocalDate.parse(p2.getNSX(), format);
                //có thể in ra
                return date1.compareTo(date2);
            }
        });
    }

    //sắp xếp theo số lô
    public void sortPharMedsBySet() {
        Collections.sort(listPharMeds, new Comparator<PharMed>() {
            @Override
            public int compare(PharMed pharmed1, PharMed pharmed2) {
                return pharmed1.getSet().compareTo(pharmed2.getSet());
            }
        });
    }

    public void sortEquipsBySet() {
        Collections.sort(listEquipments, new Comparator<Equipment>() {
            @Override
            public int compare(Equipment e1, Equipment e2) {
                return e1.getSet().compareTo(e2.getSet());
            }
        });
    }

    //sắp xếp theo nguồn hàng
    public void sortPharMedsBySource() {
        Collections.sort(listPharMeds, new Comparator<PharMed>() {
            @Override
            public int compare(PharMed pharmed1, PharMed pharmed2) {
                return pharmed1.getSource().compareTo(pharmed2.getSource());
            }
        });
    }

    public void sortEquipsBySource() {
        Collections.sort(listEquipments, new Comparator<Equipment>() {
            @Override
            public int compare(Equipment e1, Equipment e2) {
                return e1.getSource().compareTo(e2.getSource());
            }
        });
    }

    //tìm kiếm gần đúng theo ID
    public List<PharMed> searchByID(String ID) {
        List<PharMed> list = new ArrayList<>();
        for (PharMed pharMed : listPharMeds) {
            if (String.valueOf(pharMed.getId()).contains(ID)) {
                list.add(pharMed);
            }
        }
        return list;
    }

    public List<Equipment> searchEByID(String ID) {
        List<Equipment> list = new ArrayList<>();
        for (Equipment e : listEquipments) {
            if (String.valueOf(e.getId()).contains(ID)) {
                list.add(e);
            }
        }
        return list;
    }

    //tìm kiếm gần đúng theo tên
    public List<PharMed> searchByName(String name) {
        List<PharMed> list = new ArrayList<>();

        name = toNoDiacts(name.toLowerCase());
        for (PharMed pharMed : listPharMeds) {
            //System.out.println(name + " " + toNoDiacts(pharMed.getName().toLowerCase()));
            if (toNoDiacts(pharMed.getName().toLowerCase()).contains(name)) {
                list.add(pharMed);
            }
        }
        return list;
    }

    public List<Equipment> searchEByName(String name) {
        List<Equipment> list = new ArrayList<>();

        name = toNoDiacts(name.toLowerCase());
        for (Equipment e : listEquipments) {
            //System.out.println(name + " " + toNoDiacts(pharMed.getName().toLowerCase()));
            if (toNoDiacts(e.getName().toLowerCase()).contains(name)) {
                list.add(e);
            }
        }
        return list;
    }

    //hàm chuyển không dấu tìm kiếm tên, nguồn nhập
    public String toNoDiacts(String s) {
        Map<Character, Character> charMap = new HashMap<>();
        charMap.put('à', 'a');
        charMap.put('á', 'a');
        charMap.put('ả', 'a');
        charMap.put('ã', 'a');
        charMap.put('ạ', 'a');
        charMap.put('ă', 'a');
        charMap.put('ằ', 'a');
        charMap.put('ắ', 'a');
        charMap.put('ẳ', 'a');
        charMap.put('ẵ', 'a');
        charMap.put('ặ', 'a');
        charMap.put('â', 'a');
        charMap.put('ầ', 'a');
        charMap.put('ấ', 'a');
        charMap.put('ẩ', 'a');
        charMap.put('ẫ', 'a');
        charMap.put('ậ', 'a');

        charMap.put('è', 'e');
        charMap.put('é', 'e');
        charMap.put('ẻ', 'e');
        charMap.put('ẽ', 'e');
        charMap.put('ẹ', 'e');
        charMap.put('ê', 'e');
        charMap.put('ề', 'e');
        charMap.put('ề', 'e');
        charMap.put('ế', 'e');
        charMap.put('ể', 'e');
        charMap.put('ễ', 'e');
        charMap.put('ệ', 'e');

        charMap.put('ì', 'i');
        charMap.put('í', 'i');
        charMap.put('ỉ', 'i');
        charMap.put('ĩ', 'i');
        charMap.put('ị', 'i');

        charMap.put('ò', 'o');
        charMap.put('ó', 'o');
        charMap.put('ỏ', 'o');
        charMap.put('õ', 'o');
        charMap.put('ọ', 'o');
        charMap.put('ô', 'o');
        charMap.put('ồ', 'o');
        charMap.put('ố', 'o');
        charMap.put('ổ', 'o');
        charMap.put('ỗ', 'o');
        charMap.put('ộ', 'o');
        charMap.put('ư', 'u');
        charMap.put('ơ', 'o');
        charMap.put('ờ', 'o');
        charMap.put('ớ', 'o');
        charMap.put('ở', 'o');
        charMap.put('ỡ', 'o');
        charMap.put('ợ', 'o');

        charMap.put('ù', 'u');
        charMap.put('ú', 'u');
        charMap.put('ủ', 'u');
        charMap.put('ũ', 'u');
        charMap.put('ụ', 'u');
        charMap.put('ư', 'u');
        charMap.put('ừ', 'u');
        charMap.put('ứ', 'u');
        charMap.put('ử', 'u');
        charMap.put('ữ', 'u');
        charMap.put('ự', 'u');

        charMap.put('ỳ', 'y');
        charMap.put('ý', 'y');
        charMap.put('ỷ', 'y');
        charMap.put('ỹ', 'y');
        charMap.put('ỵ', 'y');

        charMap.put('đ', 'd');

        StringBuilder res = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (charMap.containsKey(c)) {
                res.append(charMap.get(c));
            } else {
                res.append(c);
            }
        }

        return res.toString();
    }

    //tìm kiếm gần đúng theo NSX
    public List<PharMed> searchByNSX(String nsx) {

        List list = new ArrayList();
        try {
            DateTimeFormatter ft = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate date1 = LocalDate.parse(nsx, ft);

            for (PharMed each : listPharMeds) {
                LocalDate date2 = LocalDate.parse(each.getNSX(), ft);
                if (date2.isEqual(date1)) {
                    list.add(each);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Định dạng NSX không hợp lệ");
        }
        return list;
    }

    public List<Equipment> searchEByNSX(String nsx) {

        List list = new ArrayList();
        try {
            DateTimeFormatter ft = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate date1 = LocalDate.parse(nsx, ft);

            for (Equipment e : listEquipments) {
                LocalDate date2 = LocalDate.parse(e.getNSX(), ft);
                if (date2.isEqual(date1)) {
                    list.add(e);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Định dạng NSX không hợp lệ");
        }
        return list;
    }

    //tìm kiếm gần đúng theo HSD
    public List<PharMed> searchByHSD(String hsd) {

        List list = new ArrayList();
        try {
            DateTimeFormatter ft = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate date1 = LocalDate.parse(hsd, ft);

            for (PharMed each : listPharMeds) {
                LocalDate date2 = LocalDate.parse(each.getHSD(), ft);
                if (date2.isEqual(date1)) {
                    list.add(each);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Định dạng HSD không hợp lệ");
        }
        return list;
    }

    public List<Equipment> searchEByHSD(String hsd) {

        List list = new ArrayList();
        try {
            DateTimeFormatter ft = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate date1 = LocalDate.parse(hsd, ft);

            for (Equipment each : listEquipments) {
                LocalDate date2 = LocalDate.parse(each.getHSD(), ft);
                if (date2.isEqual(date1)) {
                    list.add(each);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Định dạng HSD không hợp lệ");
        }
        return list;
    }

    //tìm kiếm gần đúng theo source
    public List<PharMed> searchBySource(String source) {
        List<PharMed> list = new ArrayList<>();
        source = toNoDiacts(source.toLowerCase());

        for (PharMed pharMed : listPharMeds) {
            if (toNoDiacts(pharMed.getSource().toLowerCase()).contains(source)) {
                list.add(pharMed);
            }
        }

        return list;
    }

    public List<Equipment> searchEBySource(String source) {
        List<Equipment> list = new ArrayList<>();
        source = toNoDiacts(source.toLowerCase());

        for (Equipment e : listEquipments) {
            if (toNoDiacts(e.getSource().toLowerCase()).contains(source)) {
                list.add(e);
            }
        }

        return list;
    }
    //tìm kiếm gần đúng theo bill

    public List<PharMed> searchByBill(String bill) {
        List<PharMed> list = new ArrayList<>();

        for (PharMed pharMed : listPharMeds) {
            if (pharMed.getBill().toLowerCase().contains(bill)) {
                list.add(pharMed);
            }
        }
        return list;
    }

    public List<Equipment> searchEByBill(String bill) {
        List<Equipment> list = new ArrayList<>();

        for (Equipment e : listEquipments) {
            if (e.getBill().toLowerCase().contains(bill)) {
                list.add(e);
            }
        }
        return list;
    }

    //tìm kiếm gần đúng theo set
    public List<PharMed> searchBySet(String set) {
        List<PharMed> list = new ArrayList<>();

        for (PharMed pharMed : listPharMeds) {
            if (pharMed.getSet().toLowerCase().contains(set)) {
                list.add(pharMed);
            }
        }
        return list;
    }

    public List<Equipment> searchEBySet(String set) {
        List<Equipment> list = new ArrayList<>();

        for (Equipment e : listEquipments) {
            if (e.getSet().toLowerCase().contains(set)) {
                list.add(e);
            }
        }
        return list;
    }

    //tìm kiếm theo khoảng số lượng
    public List<PharMed> searchByNumber(long s, long e) {

        List<PharMed> list = new ArrayList<>();
        for (PharMed each : listPharMeds) {
            if ((long) 1.0 * each.getNumber() >= s && (long) 1.0 * each.getNumber() <= e) {
                list.add(each);
            }
        }
        return list;
    }

    public List<Equipment> searchEByNumber(long s, long e) {

        List<Equipment> list = new ArrayList<>();
        for (Equipment each : listEquipments) {
            if ((long) 1.0 * each.getNumber() >= s && (long) 1.0 * each.getNumber() <= e) {
                list.add(each);
            }
        }
        return list;
    }

    //tìm kiếm trong khoảng giá
    public List<PharMed> searchByPrice(long s, long e) {

        List<PharMed> list = new ArrayList<>();
        for (PharMed each : listPharMeds) {
            if (each.getPrice() >= s && each.getPrice() <= e) {
                list.add(each);
            }
        }
        return list;
    }

    public List<Equipment> searchEByPrice(long s, long e) {

        List<Equipment> list = new ArrayList<>();
        for (Equipment each : listEquipments) {
            if (each.getPrice() >= s && each.getPrice() <= e) {
                list.add(each);
            }
        }
        return list;
    }

    //tìm kiếm trong khoảng NSX
    public List<PharMed> searchByRangeNSX(String nsx1, String nsx2) {
        List list = new ArrayList();
        LocalDate date1, date2;
        DateTimeFormatter ft = DateTimeFormatter.ofPattern("d/M/yyyy");
        if (nsx1.equals("")) {
            date1 = LocalDate.parse("1/1/1900", ft); //nếu để trống ô dầu
        } else {
            date1 = LocalDate.parse(nsx1, ft);
        }

        if (nsx2.equals("")) {
            date2 = LocalDate.now(); //nếu để trống ô sau
        } else {
            date2 = LocalDate.parse(nsx2, ft);
        }

        System.out.println(date1 + " " + date2);
        for (PharMed each : listPharMeds) {
            LocalDate date3 = LocalDate.parse(each.getNSX(), ft);
            if (!date3.isBefore(date1) && !date3.isAfter(date2)) {
                list.add(each);
            } else {
            }
        }
        return list;
    }

    public List<Equipment> searchEByRangeNSX(String nsx1, String nsx2) {
        List list = new ArrayList();
        LocalDate date1, date2;
        DateTimeFormatter ft = DateTimeFormatter.ofPattern("d/M/yyyy");
        if (nsx1.equals("")) {
            date1 = LocalDate.parse("1/1/1900", ft); //nếu để trống ô dầu
        } else {
            date1 = LocalDate.parse(nsx1, ft);
        }

        if (nsx2.equals("")) {
            date2 = LocalDate.now(); //nếu để trống ô sau
        } else {
            date2 = LocalDate.parse(nsx2, ft);
        }

        System.out.println(date1 + " " + date2);
        for (Equipment each : listEquipments) {
            LocalDate date3 = LocalDate.parse(each.getNSX(), ft);
            if (!date3.isBefore(date1) && !date3.isAfter(date2)) {
                list.add(each);
            } else {
            }
        }
        return list;
    }

    //tìm kiếm trong khoảng HSD
    public List<PharMed> searchByRangeHSD(String hsd1, String hsd2) {
        List list = new ArrayList();
        LocalDate date1, date2;
        DateTimeFormatter ft = DateTimeFormatter.ofPattern("d/M/yyyy");
        if (hsd1.equals("")) {
            date1 = LocalDate.parse("1/1/1900", ft); //nếu để trống ô dầu
        } else {
            date1 = LocalDate.parse(hsd1, ft);
        }

        if (hsd2.equals("")) {
            date2 = LocalDate.now(); //nếu để trống ô sau
        } else {
            date2 = LocalDate.parse(hsd2, ft);
        }

        for (PharMed each : listPharMeds) {
            LocalDate date3 = LocalDate.parse(each.getHSD(), ft);
            if (!date3.isBefore(date1) && !date3.isAfter(date2)) {
                list.add(each);
            } else {
            }
        }
        return list;
    }

    public List<Equipment> searchEByRangeHSD(String hsd1, String hsd2) {
        List list = new ArrayList();
        LocalDate date1, date2;
        DateTimeFormatter ft = DateTimeFormatter.ofPattern("d/M/yyyy");
        if (hsd1.equals("")) {
            date1 = LocalDate.parse("1/1/1900", ft); //nếu để trống ô dầu
        } else {
            date1 = LocalDate.parse(hsd1, ft);
        }

        if (hsd2.equals("")) {
            date2 = LocalDate.now(); //nếu để trống ô sau
        } else {
            date2 = LocalDate.parse(hsd2, ft);
        }

        for (Equipment each : listEquipments) {
            LocalDate date3 = LocalDate.parse(each.getHSD(), ft);
            if (!date3.isBefore(date1) && !date3.isAfter(date2)) {
                list.add(each);
            } else {
            }
        }
        return list;
    }

    public List<Exported> getDaXuat() {
        return daXuat;
    }

    //chuẩn bị dữ liệu cho biểu đồ xuất: tổng số lượng thuốc và vật tư đã xuất trong tháng
    //Chuẩn bị dữ liệu cho biểu đồ cột
    public DefaultCategoryDataset dataForColumnChart1() {

        DefaultCategoryDataset data = new DefaultCategoryDataset();

        List<PharMed> list = getListPharMeds();
        int c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0;

        for (PharMed each : list) {
            double price = each.getPrice();
            if (price <= 50) {
                c1++;
            } else if (price > 50 && price <= 100) {
                c2++;
            } else if (price > 100 && price <= 150) {
                c3++;
            } else if (price > 150 && price <= 200) {
                c4++;
            } else {
                c5++;
            }

        }
        data.setValue(c1, "", "<= 50k");
        data.setValue(c2, "", "<= 100k");
        data.setValue(c3, "", "<= 150k");
        data.setValue(c4, "", "<= 200k");
        data.setValue(c5, "", "> 200k");
        return data;
    }
    //Chuẩn bị dữ liệu cho biểu đồ cột

    public DefaultCategoryDataset dataForColumnChart2() {

        DefaultCategoryDataset data = new DefaultCategoryDataset();

        List<Equipment> list = getListEquipments();
        int c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0;

        for (Equipment each : list) {
            double price = each.getPrice();
            if (price <= 50) {
                c1++;
            } else if (price > 50 && price <= 100) {
                c2++;
            } else if (price > 100 && price <= 150) {
                c3++;
            } else if (price > 150 && price <= 200) {
                c4++;
            } else {
                c5++;
            }

        }
        data.setValue(c1, "", "<= 50k");
        data.setValue(c2, "", "<= 100k");
        data.setValue(c3, "", "<= 150k");
        data.setValue(c4, "", "<= 200k");
        data.setValue(c5, "", "> 200k");

        return data;
    }

    public DefaultPieDataset dataForPieChart1() {
        DefaultPieDataset pieData = new DefaultPieDataset();
        DecimalFormat percent = new DecimalFormat("#0.00%");

        int totalValue = 0;
        for (PharMed each : getListPharMeds()) {
            totalValue += each.getNumber();
        }

        for (PharMed each : getListPharMeds()) {
            String name = each.getName();
            int num = each.getNumber();
            double value = (double) num / totalValue * 100.0;
            pieData.setValue(name, value);

        }
        return pieData;
    }

    public DefaultPieDataset dataForPieChart2() {
        DefaultPieDataset pieData = new DefaultPieDataset();
        DecimalFormat percent = new DecimalFormat("#0.00%");

        int totalValue = 0;
        for (Equipment each : getListEquipments()) {
            totalValue += each.getNumber();
        }

        for (Equipment each : getListEquipments()) {
            String name = each.getName();
            int num = each.getNumber();
            double value = (double) num / totalValue * 100;
            pieData.setValue(name, value);

        }
        return pieData;
    }

    //nút xuất
    public List<PharMed> getListPharMeds() {
        return listPharMeds;
    }

    public List<Equipment> getListEquipments() {
        return listEquipments;
    }

    public void setListPharMeds(List<PharMed> listPharMeds) {
        this.listPharMeds = listPharMeds;
    }

    public List<Exported> getListExporteds() {
        return listExporteds;
    }

    public void setListExporteds(List<Exported> listExporteds) {
        this.listExporteds = listExporteds;
    }

}
