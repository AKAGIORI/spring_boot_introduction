package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Employee;
import com.example.service.EmployeeService;

@Controller
@RequestMapping("employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    @GetMapping("/list")
    public String showList(Model model) {
        // Serviceを呼び出し、Model属性にセットします
        List<Employee> employees = this.employeeService.findAllEmployee();
        model.addAttribute("employees", employees);
        return "employee/list";
    }
    
    // データの1件取得
    @GetMapping("/find/{employeeId}")
    public String showEmployee(@PathVariable Integer employeeId, Model model) {
        // Serviceを呼び出し、Model属性にセットします
        Employee employee = this.employeeService.findEmployee(employeeId);
        model.addAttribute("employee", employee);
        return "employee/data";
    }
    
    @GetMapping("/searchByName/{name}")
    public String searchEmployee(@PathVariable String name, Model model) {
        List<Employee> employees = this.employeeService.findByName(name);
        model.addAttribute("employees", employees);
        // データの全件取得時に作成したテンプレートファイルを流用します。
        return "employee/list";
    }
    
    // 冒頭で述べた通り、一般的にはPOST通信によりデータ挿入が行われます
    @GetMapping("/create")
    public String addEmployee(@RequestParam("name") String name
                              , @RequestParam("department") String department) {
        // データを挿入します
        this.employeeService.insert(name, department);
        // 一覧ページにリダイレクト(後述)します
        return "redirect:/employee/list";
    }
    
    // 一般的にはPOST通信によりデータ更新が行われます
    @GetMapping("/update/{employeeId}")
    public String editEmployee(@PathVariable Integer employeeId
                             , @RequestParam("name") String name
                             , @RequestParam("department") String department) {
        // データを更新します
        this.employeeService.update(employeeId, name, department);
        // 一覧ページにリダイレクトします
        return "redirect:/employee/list";
    }
    
    @GetMapping("/delete/{employeeId}")
    public String deleteEmployee(@PathVariable Integer employeeId) {
        // データを削除します
        this.employeeService.delete(employeeId);
        // 一覧ページにリダイレクトします
        return "redirect:/employee/list";
    }
}