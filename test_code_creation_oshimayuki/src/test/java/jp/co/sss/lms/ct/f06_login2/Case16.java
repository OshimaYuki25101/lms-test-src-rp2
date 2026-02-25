package jp.co.sss.lms.ct.f06_login2;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能②
 * ケース16
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース16 受講生 初回ログイン 変更パスワード未入力")
public class Case16 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		webDriver.get("http://localhost:8000/lms");
		assertEquals("ログイン | LMS", webDriver.getTitle());
		assertEquals("ログイン", webDriver.findElement(By.tagName("h2")).getText());
		getEvidence(new Object() {});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {
		WebElement loginId = webDriver.findElement(By.id("loginId"));
		WebElement password = webDriver.findElement(By.id("password"));

		loginId.clear();
		loginId.sendKeys("StudentAA02");
		password.clear();
		password.sendKeys("StudentAA02");

		webDriver.findElement(By.className("btn-primary")).click();

		pageLoadTimeout(30);

		assertEquals("セキュリティ規約 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/user/agreeSecurity", webDriver.getCurrentUrl());
		getEvidence(new Object() {});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
	void test03() {
		webDriver.findElement(By.name("securityFlg")).click();
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/form/fieldset/div[2]/button")).click();

		pageLoadTimeout(10);

		assertEquals("パスワード変更 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/password/changePassword", webDriver.getCurrentUrl());

		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 パスワードを未入力で「変更」ボタン押下")
	void test04() {

		getEvidence(new Object() {}, "01");

		webDriver.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[4]/div/button[2]")).click();

		visibilityTimeout(By.id("upd-btn"), 10);

		webDriver.findElement(By.id("upd-btn")).click();

		pageLoadTimeout(30);

		List<WebElement> errorMsg = webDriver.findElements(By.className("error"));

		assertEquals("現在のパスワードは必須です。", errorMsg.get(1).getText());
		assertEquals("パスワードは必須です。\n「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。",
				errorMsg.get(2).getText());
		assertEquals("確認パスワードは必須です。", errorMsg.get(3).getText());

		getEvidence(new Object() {}, "02");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 20文字以上の変更パスワードを入力し「変更」ボタン押下")
	void test05() {
		webDriver.findElement(By.xpath("//*[@id=\"currentPassword\"]")).sendKeys("StudentAA02");
		webDriver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("4gMCzgJVG9y8jREkCd2Q8");
		webDriver.findElement(By.xpath("//*[@id=\"passwordConfirm\"]")).sendKeys("4gMCzgJVG9y8jREkCd2Q8");

		getEvidence(new Object() {}, "01");

		webDriver.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[4]/div/button[2]")).click();

		visibilityTimeout(By.id("upd-btn"), 10);

		webDriver.findElement(By.id("upd-btn")).click();

		pageLoadTimeout(30);

		List<WebElement> errorMsg = webDriver.findElements(By.className("error"));
		assertEquals("パスワードの長さが最大値(20)を超えています。", errorMsg.get(1).getText());

		getEvidence(new Object() {}, "02");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 ポリシーに合わない変更パスワードを入力し「変更」ボタン押下")
	void test06() {
		webDriver.findElement(By.xpath("//*[@id=\"currentPassword\"]")).sendKeys("StudentAA02");
		webDriver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("StudentAA@!?");
		webDriver.findElement(By.xpath("//*[@id=\"passwordConfirm\"]")).sendKeys("StudentAA@!?");

		getEvidence(new Object() {}, "01");

		webDriver.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[4]/div/button[2]")).click();

		visibilityTimeout(By.id("upd-btn"), 10);

		webDriver.findElement(By.id("upd-btn")).click();

		pageLoadTimeout(30);

		List<WebElement> errorMsg = webDriver.findElements(By.className("error"));
		assertEquals("「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。",
				 errorMsg.get(1).getText());

		getEvidence(new Object() {}, "02");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 一致しない確認パスワードを入力し「変更」ボタン押下")
	void test07() {
		webDriver.findElement(By.xpath("//*[@id=\"currentPassword\"]")).sendKeys("StudentAA02");
		webDriver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("TestUser002");
		webDriver.findElement(By.xpath("//*[@id=\"passwordConfirm\"]")).sendKeys("testuser002");

		getEvidence(new Object() {}, "01");

		webDriver.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[4]/div/button[2]")).click();

		visibilityTimeout(By.id("upd-btn"), 10);

		webDriver.findElement(By.id("upd-btn")).click();

		pageLoadTimeout(30);

		List<WebElement> errorMsg = webDriver.findElements(By.className("error"));
		assertEquals("パスワードと確認パスワードが一致しません。",
				 errorMsg.get(1).getText());

		getEvidence(new Object() {}, "02");
	}

}
