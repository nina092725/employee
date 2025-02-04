package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.Employee;
import com.example.form.UpdateEmployeeForm;
import com.example.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpForm() {
		return new UpdateEmployeeForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員一覧を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員一覧画面を出力します.
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@GetMapping("/showList")
public String showList(@RequestParam(name = "searchWord", required = false) String searchWord, Model model) {
    List<Employee> employeeList;
	String errorMessage = null; // ★ 変数を宣言
    
    if (searchWord != null && !searchWord.isEmpty()) {
        employeeList = employeeService.findByNameContaining(searchWord);
		// 名前で検索した結果、従業員が見つからなかった場合
        if (employeeList.isEmpty()) {
            errorMessage = "該当する従業員はいません。";
        }
    } else {
        employeeList = employeeService.showList();
    }
    
    model.addAttribute("employeeList", employeeList);
    model.addAttribute("searchWord", searchWord); // 検索ワードをモデルに追加（再表示時に使う）

	if (errorMessage != null) {
        model.addAttribute("errorMessage", errorMessage); // エラーメッセージをモデルに追加
    }
    
    return "employee/list";
}

	

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細画面を出力します.
	 * 
	 * @param id    リクエストパラメータで送られてくる従業員ID
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	// @GetMapping("/showDetail")
	// public String showDetail(String id, Model model) {
	// 	Employee employee = employeeService.showDetail(Integer.parseInt(id));
	// 	model.addAttribute("employee", employee);
	// 	return "employee/detail";
	// }
	@GetMapping("/showDetail")
public String showDetail(@RequestParam("id") Integer id, Model model) {
    Employee employee = employeeService.showDetail(id);
    model.addAttribute("employee", employee); // ★ employeeオブジェクトをモデルに追加
    return "employee/detail";
}


	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を更新する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細(ここでは扶養人数のみ)を更新します.
	 * 
	 * @param form 従業員情報用フォーム
	 * @return 従業員一覧画面へリダクレクト
	 */
	@PostMapping("/update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return showDetail(Integer.parseInt(form.getId()), model);
		}
		Employee employee = new Employee();
		employee.setId(form.getIntId());
		employee.setDependentsCount(form.getIntDependentsCount());


		employee.setName(form.getName());
   	 	employee.setMailAddress(form.getMailAddress());
    	employee.setAddress(form.getAddress());
    	employee.setTelephone(form.getTelephone());
    	employee.setSalary(form.getSalary());
    	

		employeeService.update(employee);
		return "redirect:/employee/showList";
	}
}
