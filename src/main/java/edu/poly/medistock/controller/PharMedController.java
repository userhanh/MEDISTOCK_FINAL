package edu.poly.medistock.controller;

import edu.poly.medistock.dao.PharMedDao;
import edu.poly.medistock.entity.Equipment;
import edu.poly.medistock.entity.PharMed;
import edu.poly.medistock.view.NewView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author ADMINZ
 */
public class PharMedController {

    private PharMedDao pharMedDao;
    private NewView newView;

    public PharMedController(NewView newView) {

        this.newView = newView;
        pharMedDao = new PharMedDao();

        newView.addAddPharMedListener(new AddPharMedListener());
        newView.addAddEquipmentListener(new AddEquipListener());
        newView.addDeletePharMedListener(new DeletePharMedListener());
        newView.getListPharMedSelectionListener1(new ListPharMedSelectionListener1());
        newView.getListPharMedSelectionListener2(new ListPharMedSelectionListener2());
        newView.addEditPharMedListener(new EditListener());
        newView.addClearPharMedListener(new ClearPharMedListener());
        newView.sortListActionListener(new SortPharMedActionListener());
        newView.find1ActionListener(new SearchPharMedActionListener());
        newView.find2ActionListener(new SearchPharMedInSegmentActionListener());
    }

    public void showPharMedView() {
        List<PharMed> pharMedList = pharMedDao.getListPharMeds();
        List<Equipment> equipmentsList = pharMedDao.getListEquipments();

        newView.setVisible(true);
        newView.showListPharMeds(pharMedList);
        newView.showListEquips(equipmentsList);
        newView.showListExportedEquips(pharMedDao.daXuat);
    }


    class AddPharMedListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            PharMed pharMed = newView.getPharMedInfo();

            if (pharMed != null) {
                pharMedDao.addPharMed(pharMed);
                //newView.showPharMedInfo(pharMed);
                newView.showListPharMeds(pharMedDao.getListPharMeds());
                newView.clearPharMedInfo();
                newView.showMessage("Thêm thuốc thành công");
                newView.showCharts(pharMedDao.getDaXuat());
            }
        }
    }

    class AddEquipListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Equipment equipment = newView.getEquipInfo();

            if (equipment != null) {
                pharMedDao.addEquip(equipment);
                //newView.showEquipInfo(equipment);
                newView.showListEquips(pharMedDao.getListEquipments());
                newView.clearPharMedInfo();
                newView.showMessage("Thêm vật tư y tế thành công");
                newView.showCharts(pharMedDao.getDaXuat());
            }
        }
    }
    
    class EditListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //nếu là thuốc
            if(newView.getProType()){
                PharMed p = newView.getPharMedInfo();
                
                if(p != null){
                    pharMedDao.editPharMed(p);
                } 
                newView.showListPharMeds(pharMedDao.getListPharMeds());
                newView.showMessage("Sửa thuốc thành công");
            }
            else{
                Equipment eq = newView.getEquipInfo();
                if(eq!= null) {
                    pharMedDao.editEquip(eq);
                } 
                newView.showListEquips(pharMedDao.getListEquipments());
                newView.showMessage("Sửa vật tư y tế thành công");
            }
            newView.showCharts(pharMedDao.getDaXuat());
        }
    }

    class ListPharMedSelectionListener1 implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            newView.fillPharMedFromSelectedRow1();
        }
    }

    class ListPharMedSelectionListener2 implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            newView.fillPharMedFromSelectedRow2();
        }
    }
        class DeletePharMedListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            PharMed pharMed = newView.getPharMedInfo();
//            System.out.println("get info pharmed");
            Equipment equipment = newView.getEquipInfo();
//            System.out.println("get info equip");
            if (pharMed != null) {
                pharMedDao.deletePharMed(pharMed);
                pharMedDao.deleteEquip(equipment);
                System.out.println("deleted");
                newView.clearPharMedInfo();
                newView.showListPharMeds(pharMedDao.getListPharMeds());
                newView.showListEquips(pharMedDao.getListEquipments()); 
                newView.showListExportedEquips(pharMedDao.daXuat);
                newView.showMessage("Đã xuất thành công, xem thêm tại trang THÔNG TIN XUẤT");
                newView.showCharts(pharMedDao.getDaXuat());
            }
        }
    }

//
    class ClearPharMedListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            newView.clearPharMedInfo();
        }
    }
    
    class SortPharMedActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //do combobox luôn có lựa chọn
            String type = newView.getTypeSort();

            switch (type) {
                case "Hiển thị toàn bộ":
                {
                    pharMedDao.sortPharMedsByID();
                    pharMedDao.sortEquipsByID();
                    break;
                }
                case "ID": {
                    pharMedDao.sortPharMedsByID();
                    pharMedDao.sortEquipsByID();
                    break;
                }
                case "Tên sản phẩm": {
                    pharMedDao.sortPharMedsByName();
                    pharMedDao.sortEquipsByName();
                    break;
                }
                case "NSX": {
                    pharMedDao.sortEquipsByNSX();
                    pharMedDao.sortPharMedsByNSX();
                    break;
                }
                case "HSD": {
                    pharMedDao.sortPharMedByHSD();
                    pharMedDao.sortEquipsByHSD();
                    break;
                }
                case "Nguồn nhập": {
                    pharMedDao.sortPharMedsBySource();
                    pharMedDao.sortEquipsBySource();
                    break;
                }
                case "Số lô": {
                    pharMedDao.sortPharMedsBySet();
                    pharMedDao.sortEquipsBySet();
                    break;
                }
                case "Số lượng": {
                    pharMedDao.sortPharMedsByNumber();
                    pharMedDao.sortEquipsByNumber();
                    break;
                }
                case "Giá sản phẩm": {
                    pharMedDao.sortPharMedsByPrice();
                    pharMedDao.sortEquipsByPrice();
                    break;
                }
            }
            newView.showListPharMeds(pharMedDao.getListPharMeds());
            newView.showListEquips(pharMedDao.getListEquipments());
            //System.out.println("Sắp xếp thành công");
        }
    }
    
    class SearchPharMedActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //do combobox luôn có lựa chọn
            String type = newView.getTypeSearch1();
            String content = newView.getSearch1();

            if (content.equals("")) {
                newView.showMessage("Chưa nhập nội dung tìm kiếm");
                return;
            }

            switch (type) {
                case "ID": {
                    newView.showListPharMeds(pharMedDao.searchByID(content));
                    newView.showListEquips(pharMedDao.searchEByID(content));
                    break;
                }
                case "Tên": {
                    newView.showListPharMeds(pharMedDao.searchByName(content));
                    newView.showListEquips(pharMedDao.searchEByName(content));
                    //System.out.println("Succeeded");
                    break;
                }
                case "NSX": {
                    newView.showListPharMeds(pharMedDao.searchByNSX(content));
                    newView.showListEquips(pharMedDao.searchEByNSX(content));
                    break;
                }
                case "HSD": {
                    newView.showListPharMeds(pharMedDao.searchByHSD(content));
                    newView.showListEquips(pharMedDao.searchEByHSD(content));
                    break;
                }
                case "Nguồn nhập": {
                    newView.showListPharMeds(pharMedDao.searchBySource(content));
                    newView.showListEquips(pharMedDao.searchEBySource(content));
                    break;
                }
                case "Số lô": {
                    newView.showListPharMeds(pharMedDao.searchBySet(content));
                    newView.showListPharMeds(pharMedDao.searchBySet(content));
                    break;
                }
            }
            //System.out.println("Thành công");
        }

    }

    class SearchPharMedInSegmentActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String type = newView.getTypeSearch2();

            switch (type) {
                case "Số lượng nhập": {
                    try {
                        long start = Long.parseLong(newView.getSearch2());
                        long end = Long.parseLong(newView.getSearch3());
                        System.out.println(start + " " + end);
                        newView.showListPharMeds(pharMedDao.searchByNumber(start, end));
                        newView.showListEquips(pharMedDao.searchEByNumber(start, end));
                        break;
                    } catch (Exception ex) {
                        newView.showMessage("Nhập chưa đúng định dạng");
                    }
                    break;
                }

                case "Giá sản phẩm": {
                    try {
                        long start = Long.parseLong(newView.getSearch2());
                        long end = Long.parseLong(newView.getSearch3());

                        newView.showListPharMeds(pharMedDao.searchByPrice(start, end));
                        newView.showListEquips(pharMedDao.searchEByPrice(start, end));
                        break;
                    } catch (Exception er) {
                        newView.showMessage("Nhập chưa đúng định dạng");
                    }
                    break;
                }
                case "NSX": {
                    try {
                        String start = newView.getSearch2();
                        String end = newView.getSearch3();
                        
                        newView.showListPharMeds(pharMedDao.searchByRangeNSX(start, end));
                        newView.showListEquips(pharMedDao.searchEByRangeNSX(start, end));
                        break;
                    } catch (Exception x) {
                        newView.showMessage("Chưa đúng định dạng NSX");
                    }
                    break;
                }
                case "HSD": {
                    try {
                        String start = newView.getSearch2();
                        String end = newView.getSearch3();

                        newView.showListPharMeds(pharMedDao.searchByRangeHSD(start, end));
                        newView.showListEquips(pharMedDao.searchEByRangeHSD(start, end));
                        break;
                    } catch (Exception r) {
                        newView.showMessage("Chưa đúng định dạng HSD");
                    }
                    break;
                }
            }

        }
    }
    

}

