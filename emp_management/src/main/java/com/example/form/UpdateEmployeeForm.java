package com.example.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 従業員情報更新時に使用するフォーム.
 * 
 * @author igamasayuki
 * 
 */
public class UpdateEmployeeForm {
	/** id */
	private String id;
	/** 扶養人数 */
	@Pattern(regexp = "^[0-9]+$", message = "扶養人数は数値で入力してください")
	private String dependentsCount;


	// 従業員名
	@NotBlank(message = "名前は必須です")
    @Size(max = 50, message = "名前は50文字以内で入力してください")
    private String name;

	// メールアドレス
    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "正しいメールアドレスを入力してください")
    private String mailAddress;

	// 住所
    @NotBlank(message = "住所は必須です")
    private String address;

	// 電話番号
    @NotBlank(message = "電話番号は必須です")
    @Pattern(regexp = "\\d{2,4}-\\d{2,4}-\\d{4}", message = "電話番号は 例: 090-1234-5678 の形式で入力してください")
    private String telephone;

	// 給料
    @NotNull(message = "給与は必須です")
    @Min(value = 0, message = "給与は0以上で入力してください")
    private Integer salary;





	/**
	 * IDを数値として返します.
	 * 
	 * @return 数値のID
	 */
	public Integer getIntId() {
		return Integer.parseInt(id);
	}

	/**
	 * 扶養人数を数値として返します.
	 * 
	 * @return 数値の扶養人数
	 */
	public Integer getIntDependentsCount() {
		return Integer.parseInt(dependentsCount);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDependentsCount() {
		return dependentsCount;
	}

	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}


	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate; // または private LocalDate hireDate;


	@Override
	public String toString() {
		return "UpdateEmployeeForm [id=" + id + ", dependentsCount=" + dependentsCount + "]";
	}

    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    public String getMailAddress() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMailAddress'");
    }

	public String getAddress() {
		return address;
	}

	public String getTelephone() {
		return telephone;
	}

	public Integer getSalary() {
		return salary;
	}

	public Date getHireDate() { 
		return hireDate;
	}
	
	public void setHireDate(Date hireDate) { 
		this.hireDate = hireDate;
	}

}
