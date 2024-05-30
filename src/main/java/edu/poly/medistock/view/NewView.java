/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package edu.poly.medistock.view;

import edu.poly.medistock.dao.PharMedDao;
import edu.poly.medistock.entity.Equipment;
import edu.poly.medistock.entity.Exported;
import edu.poly.medistock.entity.PharMed;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author msi gameming
 */
public class NewView extends javax.swing.JFrame {

    /**
     * Creates new form
     */
    PharMedDao pharMedDao = new PharMedDao(); //để hiển thị biểu đồ 

    public NewView() {
        initComponents();
        add(jTabbedPane1);
        setTitle("QUẢN LÝ THUỐC VÀ VẬT TƯ Y TẾ");
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Đặt hành động khi đóng cửa sổ
        setLocationRelativeTo(null);
        txtType.setEditable(false);

        showChart.setLayout(new GridLayout(2, 3, 10, 10));

        showCharts(null);
    }

    public void showCharts(List<Exported> exporteds) {
        //hiển thị các biểu đồ ở tab3
        showChart.removeAll();

        pharMedDao = new PharMedDao();
        ChartPanel chartPanel1 = new ChartPanel(createBarChart1());
        chartPanel1.setPreferredSize(new Dimension(400, 300));
        showChart.add(chartPanel1);

        ChartPanel chartPanel2 = new ChartPanel(createBarChart2());
        chartPanel2.setPreferredSize(new Dimension(400, 300));
        showChart.add(chartPanel2);

        ChartPanel chartPanel3 = new ChartPanel(createPieChart1());
        chartPanel3.setPreferredSize(new Dimension(400, 300));
        showChart.add(chartPanel3);

        ChartPanel chartPanel4 = new ChartPanel(createPieChart2());
        chartPanel4.setPreferredSize(new Dimension(400, 300));
        showChart.add(chartPanel4);

        ChartPanel chartPanel5 = new ChartPanel(createBarChartExport(exporteds));
        chartPanel5.setPreferredSize(new Dimension(400, 300));
        showChart.add(chartPanel5);

        ChartPanel chartPanel6 = new ChartPanel(createBarChartExport2(exporteds));
        chartPanel6.setPreferredSize(new Dimension(400, 300));
        showChart.add(chartPanel6);
    }

    public DefaultCategoryDataset dataforColumnChartExport(List<Exported> exporteds) {
        double c1 = 0, c2 = 0;
        DefaultCategoryDataset data = new DefaultCategoryDataset();

        if (exporteds != null) {
            for (Exported x : exporteds) {
                if (x.getType().equals("Thuốc")) {
                    c1 += x.getBillNumber() * 1000;
                } else {
                    c2 += x.getBillNumber() * 1000;
                }
            }
        }
        data.setValue(c1, "", "Tiền Thuốc");
        data.setValue(c2, "", "Tiền Vật tư");

        return data;
    }

    public DefaultCategoryDataset dataforColumnChartExport2(List<Exported> exporteds) {
        int c1 = 0, c2 = 0;

        DefaultCategoryDataset data = new DefaultCategoryDataset();
        if (exporteds != null) {
            for (Exported x : exporteds) {
                if (x.getType().equals("Thuốc")) {
                    c1 += x.getExNumber();
                } else {
                    c2 += x.getExNumber();
                }
            }
        }
        data.setValue(c1, "", "Số Thuốc");
        data.setValue(c2, "", "Số Vật tư");

        return data;
    }

    private JFreeChart createBarChartExport(List<Exported> exporteds) {
        DefaultCategoryDataset barDataset = dataforColumnChartExport2(exporteds);
        JFreeChart barChart = ChartFactory.createBarChart(
                "Số thuốc và vật tư y tế đã xuất trong tháng",
                "",
                "Tổng số sản phẩm",
                barDataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        // Lấy đối tượng CategoryPlot từ biểu đồ
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();

        // Sử dụng BarRenderer để tùy chỉnh màu sắc các cột
        BarRenderer renderer = new BarRenderer();
        Random rand = new Random();

        for (int i = 0; i < barDataset.getRowCount(); i++) {
            // Tạo màu ngẫu nhiên
            Color randomColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            renderer.setSeriesPaint(i, randomColor);
        }

        // Đặt renderer cho plot
        plot.setRenderer(renderer);

        return barChart;
    }

    private JFreeChart createBarChartExport2(List<Exported> exporteds) {
        DefaultCategoryDataset barDataset = dataforColumnChartExport(exporteds);
        JFreeChart barChart = ChartFactory.createBarChart(
                "Tổng giá trị thuốc và vật tư đã xuất trong tháng",
                "",
                "Tổng giá trị đã xuất (VNĐ)",
                barDataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        // Lấy đối tượng CategoryPlot từ biểu đồ
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();

        // Sử dụng BarRenderer để tùy chỉnh màu sắc các cột
        BarRenderer renderer = new BarRenderer();
        Random rand = new Random();

        for (int i = 0; i < barDataset.getRowCount(); i++) {
            // Tạo màu ngẫu nhiên
            Color randomColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            renderer.setSeriesPaint(i, randomColor);
        }

        // Đặt renderer cho plot
        plot.setRenderer(renderer);

        return barChart;
    }

    private JFreeChart createBarChart1() {
        DefaultCategoryDataset barDataset = pharMedDao.dataForColumnChart1();
        JFreeChart barChart = ChartFactory.createBarChart(
                "Biểu đồ thống kê số thuốc trong tầm giá",
                "",
                "Số sản phẩm trong tầm giá",
                barDataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();

        // Sử dụng BarRenderer để tùy chỉnh màu sắc các cột
        BarRenderer renderer = new BarRenderer();
        Random rand = new Random();

        for (int i = 0; i < barDataset.getRowCount(); i++) {
            // Tạo màu ngẫu nhiên
            Color randomColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            renderer.setSeriesPaint(i, randomColor);
        }

        // Đặt renderer cho plot
        plot.setRenderer(renderer);
        return barChart;
    }

    private JFreeChart createBarChart2() {
        DefaultCategoryDataset barDataset = pharMedDao.dataForColumnChart2();
        JFreeChart barChart = ChartFactory.createBarChart(
                "Biểu đồ thống kê số vật tư y tế trong tầm giá",
                "",
                "Số sản phẩm trong tầm giá",
                barDataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();

        // Sử dụng BarRenderer để tùy chỉnh màu sắc các cột
        BarRenderer renderer = new BarRenderer();
        Random rand = new Random();

        for (int i = 0; i < barDataset.getRowCount(); i++) {
            // Tạo màu ngẫu nhiên
            Color randomColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            renderer.setSeriesPaint(i, randomColor);
        }

        // Đặt renderer cho plot
        plot.setRenderer(renderer);
        return barChart;
    }

    private JFreeChart createPieChart1() {
        DefaultPieDataset pieDataset = pharMedDao.dataForPieChart2();
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Biểu đồ tròn thống kê số lượng sản phẩm vật tư",
                pieDataset,
                false,
                true,
                false
        );
        PiePlot plot = (PiePlot) pieChart.getPlot();
        return pieChart;
    }

    private JFreeChart createPieChart2() {
        DefaultPieDataset pieDataset = pharMedDao.dataForPieChart1();
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Biểu đồ tròn thống kê số lượng sản phẩm thuốc",
                pieDataset,
                false,
                true,
                false
        );
        PiePlot plot = (PiePlot) pieChart.getPlot();
        return pieChart;
    }

    //CÁC METHOD TỰ THÊM
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private String[] pharmedColumnNames = new String[]{
        "ID", "Tên thuốc", "NSX", "HSD", "Nguồn nhập", "Số hóa đơn", "Số lô", "Số lượng", "Giá sản phẩm"
    };

    private String[] equipColumnNames = new String[]{
        "ID", "Tên vật tư", "NSX", "HSD", "Nguồn nhập", "Số hóa đơn", "Số lô", "Số lượng", "Giá sản phẩm"
    };

    private String[] exportColumnNames = new String[]{
        "Tên sản phẩm", "Loại sản phẩm", "Thời gian xuất", "Số lượng xuất", "Tổng hóa đơn (số lượng xuất * giá tiền)"
    };

    private String[] init = new String[]{
        ""
    };
    //để chuẩn hóa giá tiền 
    DecimalFormat decimalFormat = new DecimalFormat("#,###");

    public String jDateNSX() {
        return layNgayThang(jDateNSX.getDate());
    }

    public String jDateHSD() {
        return layNgayThang(jDateHSD.getDate());
    }

    public String layNgayThang(Date ngayThangGoc) {

        // Định dạng hiện tại của Date (toString() mặc định)
        System.out.println("Default Date Format: " + ngayThangGoc.toString());

        // Định dạng ngày tùy chỉnh
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDate = formatter.format(ngayThangGoc);

        // Hiển thị ngày đã định dạng
        System.out.println("Formatted Date: " + formattedDate.split(" "));

        return formattedDate.split(" ")[0];
    }

    public static Date parseDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            // Chuyển đổi chuỗi thành đối tượng Date
            return formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Object data = new Object[][]{};

    //hiển thị danh sách thuốc
    public void showListPharMeds(List<PharMed> list) {
        int size = list.size();

        Object[][] pharmeds = new Object[size][9];

        for (int i = 0; i < size; i++) {
            pharmeds[i][0] = list.get(i).getId();
            pharmeds[i][1] = list.get(i).getName();
            pharmeds[i][2] = list.get(i).getNSX();
            pharmeds[i][3] = list.get(i).getHSD();
            pharmeds[i][4] = list.get(i).getSource();
            pharmeds[i][5] = list.get(i).getBill();
            pharmeds[i][6] = list.get(i).getSet();
            pharmeds[i][7] = list.get(i).getNumber();
            pharmeds[i][8] = decimalFormat.format(list.get(i).getPrice() * 1000);
        }
        tblPharMed.setModel(new DefaultTableModel(pharmeds, pharmedColumnNames));
    }

    //đây là hiển thị cho Panel2
    public void showListExportedEquips(List<Exported> list) {
        int size = list.size();
        if (size == 0) {

            Object[][] noData = new Object[1][1];
            noData[0][0] = "Bạn chưa xuất thuốc nào, hãy xuất bên THÔNG TIN KHO để thấy thông tin tại đây";
            tblExport1.setModel(new DefaultTableModel(noData, init));
            return;
        }
        Object[][] pharmeds = new Object[size][5];

        int sumThuoc = 0, sumVat = 0, sum = 0;
        double mThuoc = 0.0, mVat = 0.0, m = 0;
        for (int i = 0; i < size; i++) {
            pharmeds[i][0] = list.get(i).getName();
            pharmeds[i][1] = list.get(i).getType();
            pharmeds[i][2] = list.get(i).getNgayXuat();
            pharmeds[i][3] = list.get(i).getExNumber();
            pharmeds[i][4] = decimalFormat.format(list.get(i).getBillNumber() * 1000);

            if (list.get(i).getType().equals("Thuốc")) {
                sumThuoc += list.get(i).getExNumber();
                mThuoc += list.get(i).getBillNumber();
            } else {
                sumVat += list.get(i).getExNumber();
                mVat += list.get(i).getBillNumber();
            }

            sum = sumThuoc + sumVat;
            m = mThuoc + mVat;
            this.sumThuoc.setText((decimalFormat.format(sumThuoc)));
            this.sumVTYT.setText(decimalFormat.format(sumVat));
            this.sumSP.setText(decimalFormat.format(sum));

            this.sumMoneyThuoc.setText(decimalFormat.format(mThuoc * 1000));
            this.sumMoneyVTYT.setText(decimalFormat.format(mVat * 1000));
            this.sumMoneySP.setText(decimalFormat.format(m * 1000));

        }
        tblExport1.setModel(new DefaultTableModel(pharmeds, exportColumnNames));

    }

    public void showListEquips(List<Equipment> list) {
        int size = list.size();

        Object[][] pharmeds = new Object[size][9];

        for (int i = 0; i < size; i++) {
            pharmeds[i][0] = list.get(i).getId();
            pharmeds[i][1] = list.get(i).getName();
            pharmeds[i][2] = list.get(i).getNSX();
            pharmeds[i][3] = list.get(i).getHSD();
            pharmeds[i][4] = list.get(i).getSource();
            pharmeds[i][5] = list.get(i).getBill();
            pharmeds[i][6] = list.get(i).getSet();
            pharmeds[i][7] = list.get(i).getNumber();
            pharmeds[i][8] = decimalFormat.format(list.get(i).getPrice() * 1000);
        }
        tblEquip.setModel(new DefaultTableModel(pharmeds, equipColumnNames));
    }

    public PharMed getPharMedInfo() {
        //validate
        if (!validateName() || !validateNSX() || !validateHSD() || !validateNumber() || !validatePrice()) {
            return null;
        }
        try {
            PharMed pharmed = new PharMed();
            if (!idField.getText().trim().equals("")) {
                pharmed.setId(Integer.parseInt(idField.getText().trim()));

            }
            pharmed.setName(nameField.getText().trim());
            pharmed.setNSX(layNgayThang(jDateNSX.getDate()));
            pharmed.setHSD(layNgayThang(jDateHSD.getDate()));
            pharmed.setSource(sourceField.getText().trim());
            pharmed.setBill(billField.getText().trim());
            pharmed.setSet(setField.getText().trim());
            pharmed.setNumber(Integer.parseInt(numberField.getText().trim()));
            pharmed.setPrice(Integer.parseInt(priceField.getText().trim().replaceAll(",", "")));

            return pharmed;
        } catch (Exception e) {
            showMessage(e.getMessage());
        }
        return null;
    }

    public Equipment getEquipInfo() {
        if (!validateName() || !validateNumber() || !validatePrice() || !validateNSX() || !validateHSD()) {
            return null;
        }
        try {
            Equipment pharmed = new Equipment();
            if (!idField.getText().trim().equals("")) {
                pharmed.setId(Integer.parseInt(idField.getText().trim()));

            }
            pharmed.setName(nameField.getText().trim());
            pharmed.setNSX(layNgayThang(jDateNSX.getDate()));
            pharmed.setHSD(layNgayThang(jDateHSD.getDate()));
            pharmed.setSource(sourceField.getText().trim());
            pharmed.setBill(billField.getText().trim());
            pharmed.setSet(setField.getText().trim());
            pharmed.setNumber(Integer.parseInt(numberField.getText().trim()));
            pharmed.setPrice(Integer.parseInt(priceField.getText().trim().replaceAll(",", "")));

            return pharmed;
        } catch (NumberFormatException e2) {
            showMessage(e2.getMessage());
        }
        return null;
    }

    //điền nội dung từ hàng được chọn
    public void fillPharMedFromSelectedRow1() {
        int row = tblPharMed.getSelectedRow();
        if (row >= 0) {
            idField.setText(tblPharMed.getModel().getValueAt(row, 0).toString());
            nameField.setText(tblPharMed.getModel().getValueAt(row, 1).toString());
            jDateNSX.setDate(parseDate(tblPharMed.getModel().getValueAt(row, 2).toString()));
            jDateHSD.setDate(parseDate(tblPharMed.getModel().getValueAt(row, 3).toString()));
            sourceField.setText(tblPharMed.getModel().getValueAt(row, 4).toString());
            billField.setText(tblPharMed.getModel().getValueAt(row, 5).toString());
            setField.setText(tblPharMed.getModel().getValueAt(row, 6).toString());
            numberField.setText(tblPharMed.getModel().getValueAt(row, 7).toString());
            String[] st = tblPharMed.getModel().getValueAt(row, 8).toString().split(",");
            int size = st.length;
            String res = "";
            for (int i = 0; i < size - 1; i++) {
                res += st[i];
            }
            int number = Integer.parseInt(res);
            priceField.setText(decimalFormat.format(number).toString());
            txtType.setText("Thuốc");
            // enable Edit and Delete buttons
            editBtn.setEnabled(true);
            delBtn.setEnabled(true);
            // disable Add button
            addBtnVt.setEnabled(false);
            addBtn.setEnabled(false);
        }
    }

    public void fillPharMedFromSelectedRow2() {
        int row = tblEquip.getSelectedRow();
        if (row >= 0) {
            idField.setText(tblEquip.getModel().getValueAt(row, 0).toString());
            nameField.setText(tblEquip.getModel().getValueAt(row, 1).toString());
            jDateNSX.setDate(parseDate(tblEquip.getModel().getValueAt(row, 2).toString()));
            jDateHSD.setDate(parseDate(tblEquip.getModel().getValueAt(row, 3).toString()));
            sourceField.setText(tblEquip.getModel().getValueAt(row, 4).toString());
            billField.setText(tblEquip.getModel().getValueAt(row, 5).toString());
            setField.setText(tblEquip.getModel().getValueAt(row, 6).toString());
            numberField.setText(tblEquip.getModel().getValueAt(row, 7).toString());
            String[] st = tblEquip.getModel().getValueAt(row, 8).toString().split(",");
            int size = st.length;
            String res = "";
            for (int i = 0; i < size - 1; i++) {
                res += st[i];
            }
            int number = Integer.parseInt(res);
            priceField.setText(decimalFormat.format(number).toString());
            txtType.setText("Vật tư");
            // enable Edit and Delete buttons
            editBtn.setEnabled(true);
            delBtn.setEnabled(true);
            // disable Add button
            addBtn.setEnabled(false);
            addBtnVt.setEnabled(false);
        }
    }

    //nút clear 
    public void clearPharMedInfo() {
        idField.setText("");
        nameField.setText("");
        jDateNSX.setDate(parseDate(""));
        jDateHSD.setDate(parseDate(""));
        sourceField.setText("");
        billField.setText("");
        setField.setText("");
        numberField.setText("");
        priceField.setText("");
        txtType.setText("");
        // disable Edit and Delete buttons
        editBtn.setEnabled(false);
        delBtn.setEnabled(false);
        // enable Add button
        addBtn.setEnabled(true);
        addBtnVt.setEnabled(true);
    }

    //Các phương thức validate
    private boolean validateName() {
        String name = nameField.getText();
        if (name == null || "".equals(name.trim())) {
            nameField.requestFocus();
            showMessage("Name không được trống.");
            return false;
        }
        return true;
    }

    //validate NSX
    LocalDate nsx = null; //khai báo bên ngoài để sau tiện so sánh

    public boolean validateNSX() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/yyyy");

        try {
            nsx = LocalDate.parse(layNgayThang(jDateNSX.getDate()), format);
            if (nsx.isAfter(LocalDate.now())) {
                showMessage("Sản phẩm chưa được sản xuất!");
                return false;
            }
        } catch (Exception e) {
            showMessage("NSX không đúng định dạng");
            return false;
        }
        return true;
    }

    //validate HSD: đúng kiểu ngày tháng và sau NSX
    public boolean validateHSD() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate hsd = null;
        try {
            hsd = LocalDate.parse(layNgayThang(jDateHSD.getDate()), format);

            if (hsd.isBefore(LocalDate.now())) {
                showMessage("Chỉ được thêm hàng chưa hết hạn");
                return false;
            }

            if (!hsd.isAfter(nsx)) {
                showMessage("HSD phải sau NSX");
                return false;
            }
        } catch (Exception e) {
            showMessage("HSD không đúng định dạng");
        }
        return true;
    }

    private boolean validateNumber() {
        try {
            int number = Integer.parseInt(numberField.getText().trim());
            if (number <= 0) {
                priceField.requestFocus();
                showMessage("Số lượng phải > 0");
                return false;
            }
        } catch (NumberFormatException e) {
            priceField.requestFocus();
            showMessage("Số lượng không hợp lệ!");
            return false;
        }
        return true;
    }

    private boolean validatePrice() {
        try {
            Double price = Double.valueOf(priceField.getText().trim().replaceAll(",", ""));
            if (price < 0) {
                priceField.requestFocus();
                showMessage("Giá không được < 0");
                return false;
            }
        } catch (NumberFormatException e) {
            priceField.requestFocus();
            showMessage("Giá không hợp lệ!");
            return false;
        }
        return true;
    }

    //các action listener 
    public void valueChanged(ListSelectionEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
    }

    public void addAddPharMedListener(ActionListener listener) {
        addBtn.addActionListener(listener);
    }

    public void addAddEquipmentListener(ActionListener listener) {
        addBtnVt.addActionListener(listener);
    }

    public void addEditPharMedListener(ActionListener listener) {
        editBtn.addActionListener(listener);
    }

    public void addRefreshListener(ActionListener listener) {
        clearBtn.addActionListener(listener);
    }

    public boolean getProType() {
        String s = txtType.getText().trim();
        return s.equals("Thuốc");
    }

    public void addDeletePharMedListener(ActionListener listener) {
        delBtn.addActionListener(listener);
    }

    public void addClearPharMedListener(ActionListener listener) {
        clearBtn.addActionListener(listener);
    }

    public void getListPharMedSelectionListener1(ListSelectionListener listener) {
        tblPharMed.getSelectionModel().addListSelectionListener(listener);
    }

    public void getListPharMedSelectionListener2(ListSelectionListener listener) {
        tblEquip.getSelectionModel().addListSelectionListener(listener);
    }

    public void sortListActionListener(ActionListener listener) {
        cbbTypeSort.addActionListener(listener);
    }

    public String getTypeSort() {
        return cbbTypeSort.getSelectedItem().toString();
    }

    public String getTypeSearch1() {
        return cbbTypeSearch1.getSelectedItem().toString();
    }

    public String getTypeSearch2() {
        return cbbTypeSearch2.getSelectedItem().toString();
    }

    public String getSearch1() {
        return txtSearch1.getText().trim();
    }

    public String getSearch2() {
        return txtSearch2.getText().trim();
    }

    public String getSearch3() {
        return txtSearch3.getText().trim();
    }

    public void find1ActionListener(ActionListener listener) {
        btnFind1.addActionListener(listener);
    }

    public void find2ActionListener(ActionListener listener) {
        btnFind2.addActionListener(listener);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEquip = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPharMed = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        idField = new java.awt.TextField();
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        label5 = new java.awt.Label();
        label7 = new java.awt.Label();
        label8 = new java.awt.Label();
        label9 = new java.awt.Label();
        label13 = new java.awt.Label();
        addBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        delBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        addBtnVt = new javax.swing.JButton();
        txtType = new java.awt.TextField();
        jSeparator1 = new javax.swing.JSeparator();
        label15 = new java.awt.Label();
        jDateNSX = new com.toedter.calendar.JDateChooser();
        jDateHSD = new com.toedter.calendar.JDateChooser();
        nameField = new javax.swing.JTextField();
        numberField = new javax.swing.JTextField();
        priceField = new javax.swing.JTextField();
        sourceField = new javax.swing.JTextField();
        setField = new javax.swing.JTextField();
        billField = new javax.swing.JTextField();
        label6 = new java.awt.Label();
        label16 = new java.awt.Label();
        cbbTypeSearch1 = new javax.swing.JComboBox<>();
        label10 = new java.awt.Label();
        label11 = new java.awt.Label();
        cbbTypeSearch2 = new javax.swing.JComboBox<>();
        label12 = new java.awt.Label();
        cbbTypeSort = new javax.swing.JComboBox<>();
        label14 = new java.awt.Label();
        btnFind1 = new javax.swing.JButton();
        btnFind2 = new javax.swing.JButton();
        txtSearch1 = new javax.swing.JTextField();
        txtSearch2 = new javax.swing.JTextField();
        txtSearch3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblExport1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        sumThuoc = new javax.swing.JTextField();
        sumVTYT = new javax.swing.JTextField();
        sumSP = new javax.swing.JTextField();
        sumMoneyThuoc = new javax.swing.JTextField();
        sumMoneyVTYT = new javax.swing.JTextField();
        sumMoneySP = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        showChart = new java.awt.Panel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFocusable(false);
        setResizable(false);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        tblEquip.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tên vật tư y tế", "NSX", "HSD", "Nguồn nhập", "Số hoá đơn", "Số lô", "Số lượng", "Giá sản phẩm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEquip.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblEquip.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblEquip);

        tblPharMed.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblPharMed);

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));
        jPanel4.setForeground(new java.awt.Color(51, 51, 255));

        idField.setEditable(false);

        label1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label1.setText("ID");

        label2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label2.setText("*Tên sản phẩm");

        label3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label3.setText("*Ngày sản xuất");

        label4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label4.setText("*Hạn sử dụng");

        label5.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label5.setText("*Số lượng");

        label7.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label7.setText("Nguồn nhập");

        label8.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label8.setText("Số lô");

        label9.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label9.setText("Số hoá đơn");

        label13.setFont(new java.awt.Font("Dialog", 3, 12)); // NOI18N
        label13.setText("(*) CÁC TRƯỜNG THÔNG TIN BẮT BUỘC");

        addBtn.setText("Thêm thuốc");

        editBtn.setText("Sửa");

        clearBtn.setText("Làm mới");

        delBtn.setText("XUẤT KHO");
        delBtn.setActionCommand("");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Loại sản phẩm: ");

        addBtnVt.setText("Thêm vật tư");

        label15.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label15.setText("THÔNG TIN THUỐC VÀ VẬT TƯ Y TẾ");

        jDateNSX.setDateFormatString("dd/MM/yyyy");

        jDateHSD.setDateFormatString("dd/MM/yyyy");

        label6.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label6.setText("*Giá sản phẩm");

        label16.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        label16.setText(",000 VNĐ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(47, 47, 47)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addBtnVt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 142, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator1)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(idField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtType, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addGap(0, 306, Short.MAX_VALUE)
                                .addComponent(clearBtn))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(43, 43, 43)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nameField)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jDateHSD, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                                .addComponent(jDateNSX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(numberField)
                                                .addComponent(sourceField)
                                                .addComponent(setField)
                                                .addComponent(billField, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(2, 2, 2)
                                                .addComponent(label16, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 54, Short.MAX_VALUE)))))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(clearBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateNSX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateHSD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(numberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sourceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addComponent(setField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(billField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(label13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBtn)
                    .addComponent(addBtnVt))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delBtn)
                    .addComponent(editBtn))
                .addGap(91, 91, 91))
        );

        addBtn.getAccessibleContext().setAccessibleName("btnAdd");
        editBtn.getAccessibleContext().setAccessibleName("btnEdit");
        clearBtn.getAccessibleContext().setAccessibleName("btnRefresh");
        delBtn.getAccessibleContext().setAccessibleName("btnDelete");
        priceField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        // Căn chỉnh văn bản về phía phải
        priceField.setHorizontalAlignment(JTextField.RIGHT);

        cbbTypeSearch1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Tên", "NSX", "HSD", "Nguồn nhập", "Số lô", " " }));

        label10.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label10.setText("Tìm kiếm chính xác:");

        label11.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label11.setText("Tìm kiếm theo khoảng:");

        cbbTypeSearch2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Số lượng nhập", "Giá sản phẩm", "NSX", "HSD", " " }));

        label12.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label12.setText("đến");

        cbbTypeSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hiển thị toàn bộ", "ID", "Tên sản phẩm", "NSX", "HSD", "Nguồn nhập", "Giá sản phẩm", "Số lượng", " " }));

        label14.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label14.setText("Sắp xếp:");

        btnFind1.setText("Tìm");

        btnFind2.setText("Tìm");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 255));
        jLabel3.setText("DANH SÁCH VẬT TƯ Y TẾ TRONG KHO");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 255));
        jLabel4.setText("DANH SÁCH THUỐC TRONG KHO");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 255));
        jLabel5.setText("PHẦN MỀM QUẢN LÝ KHO THUỐC VÀ VẬT TƯ Y TẾ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(47, 47, 47)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbTypeSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbbTypeSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(21, 21, 21)
                                        .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSearch3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(38, 38, 38)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnFind2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnFind1, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(0, 9, Short.MAX_VALUE))
                            .addComponent(jScrollPane1)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(label14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbTypeSort, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(79, 79, 79)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(133, 133, 133))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(278, 278, 278)))
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cbbTypeSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbbTypeSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(label10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFind1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnFind2)
                                .addComponent(txtSearch3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbbTypeSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        jTabbedPane1.addTab("THÔNG TIN KHO", jPanel1);

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));

        tblExport1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tên sản phẩm", "Loại sản phẩm", "Ngày xuất", "Số lượng xuất", "Tổng giá trị"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblExport1);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 255));
        jLabel6.setText("THUỐC VÀ VẬT TƯ Y TẾ ĐÃ XUẤT");

        jPanel5.setBackground(new java.awt.Color(204, 204, 255));

        jLabel7.setText("TỔNG SỐ THUỐC ĐÃ XUẤT:");

        jLabel8.setText("TỔNG SỐ VẬT TƯ Y TẾ ĐÃ XUẤT:");

        jLabel9.setText("TỔNG SỐ SẢN PHẨM ĐÃ XUẤT:");

        jLabel10.setText("TỔNG TIỀN THUỐC ĐÃ XUẤT:");

        jLabel11.setText("TỔNG TIỀN VẬT TƯ Y TẾ ĐÃ XUẤT:");

        jLabel12.setText("TỔNG SỐ SẢN PHẨM ĐÃ XUẤT:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sumVTYT, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sumThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sumSP, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(39, 39, 39)
                        .addComponent(sumMoneyThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sumMoneyVTYT, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sumMoneySP, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(sumThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(sumMoneyThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(sumVTYT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sumMoneyVTYT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12)
                                .addComponent(sumMoneySP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(sumSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(445, 445, 445)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("THÔNG TIN XUẤT", jPanel2);

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));

        javax.swing.GroupLayout showChartLayout = new javax.swing.GroupLayout(showChart);
        showChart.setLayout(showChartLayout);
        showChartLayout.setHorizontalGroup(
            showChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1192, Short.MAX_VALUE)
        );
        showChartLayout.setVerticalGroup(
            showChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 555, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(showChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(showChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(82, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("THỐNG KÊ", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton addBtnVt;
    private javax.swing.JTextField billField;
    private javax.swing.JButton btnFind1;
    private javax.swing.JButton btnFind2;
    private javax.swing.JComboBox<String> cbbTypeSearch1;
    private javax.swing.JComboBox<String> cbbTypeSearch2;
    private javax.swing.JComboBox<String> cbbTypeSort;
    private javax.swing.JButton clearBtn;
    private javax.swing.JButton delBtn;
    private javax.swing.JButton editBtn;
    private java.awt.TextField idField;
    private com.toedter.calendar.JDateChooser jDateHSD;
    private com.toedter.calendar.JDateChooser jDateNSX;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private java.awt.Label label1;
    private java.awt.Label label10;
    private java.awt.Label label11;
    private java.awt.Label label12;
    private java.awt.Label label13;
    private java.awt.Label label14;
    private java.awt.Label label15;
    private java.awt.Label label16;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.Label label7;
    private java.awt.Label label8;
    private java.awt.Label label9;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField numberField;
    private javax.swing.JTextField priceField;
    private javax.swing.JTextField setField;
    private java.awt.Panel showChart;
    private javax.swing.JTextField sourceField;
    private javax.swing.JTextField sumMoneySP;
    private javax.swing.JTextField sumMoneyThuoc;
    private javax.swing.JTextField sumMoneyVTYT;
    private javax.swing.JTextField sumSP;
    private javax.swing.JTextField sumThuoc;
    private javax.swing.JTextField sumVTYT;
    private javax.swing.JTable tblEquip;
    private javax.swing.JTable tblExport1;
    private javax.swing.JTable tblPharMed;
    private javax.swing.JTextField txtSearch1;
    private javax.swing.JTextField txtSearch2;
    private javax.swing.JTextField txtSearch3;
    private java.awt.TextField txtType;
    // End of variables declaration//GEN-END:variables
}
