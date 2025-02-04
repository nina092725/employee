package com.example.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


/**
 * 管理者情報登録時に使用するフォーム.
 * 
 * @author igamasayuki
 * 
 */
public class InsertAdministratorForm {
	/** 名前 */
	@NotBlank(message = "名前を入力してください")
	@Size(max = 100, message = "名前は100文字以内で入力してください")
	private String name;
	/** メールアドレス */
	@NotBlank(message = "メールアドレスを入力してください")
	@Size(max = 100, message = "メールアドレスは100文字以内で入力してください")
    @Email(message = "メールアドレスの形式で入力してください")
	private String mailAddress;
	/** パスワード */
	@Size(min=8,max=20,message="パスワードは1文字以上20文字以内で入力してください")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "パスワードは大文字、小文字、数字を含む必要があります")
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	
	@Override
	public String toString() {
		return "InsertAdministratorForm [name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ "]";
	}

	
}

