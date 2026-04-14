package com.trieu.store.controller;

import com.trieu.store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/revenue")
public class AdminRevenueController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public String viewRevenueForAdmin(
            @RequestParam(name = "month", required = false) Integer month,
            @RequestParam(name = "year", required = false) Integer year,
            Model model) {

        // 1. Lấy thời gian hiện tại làm mặc định nếu người dùng chưa chọn gì
        int currentYear = java.time.LocalDate.now().getYear();
        int currentMonth = java.time.LocalDate.now().getMonthValue();

        // 2. Nếu người dùng có chọn tháng/năm trên giao diện thì lấy số đó, không thì dùng mặc định
        int selectedYear = (year != null) ? year : currentYear;
        int selectedMonth = (month != null) ? month : currentMonth;

        // 3. Tính doanh thu của NĂM được chọn
        java.math.BigDecimal yearRevenue = orderRepository.calculateRevenueByYear(selectedYear);
        if (yearRevenue == null) yearRevenue = java.math.BigDecimal.ZERO;

        // 4. Tính doanh thu của THÁNG và NĂM được chọn
        java.math.BigDecimal monthRevenue = orderRepository.calculateRevenueByMonth(selectedMonth, selectedYear);
        if (monthRevenue == null) monthRevenue = java.math.BigDecimal.ZERO;

        // 5. Đếm số lượng đơn hàng
        Integer yearOrderCount = orderRepository.countOrdersByYear(selectedYear);
        if (yearOrderCount == null) yearOrderCount = 0;

        Integer monthOrderCount = orderRepository.countOrdersByMonth(selectedMonth, selectedYear);
        if (monthOrderCount == null) monthOrderCount = 0;

        // Đẩy dữ liệu ra view
        model.addAttribute("selectedYear", selectedYear);
        model.addAttribute("selectedMonth", selectedMonth);
        model.addAttribute("yearRevenue", yearRevenue);
        model.addAttribute("monthRevenue", monthRevenue);
        model.addAttribute("yearOrderCount", yearOrderCount);
        model.addAttribute("monthOrderCount", monthOrderCount);

        return "admin-revenue";
    }
}