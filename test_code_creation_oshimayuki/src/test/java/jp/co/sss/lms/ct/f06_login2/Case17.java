package jp.co.sss.lms.ct.f06_login2;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

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
 * ケース17
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース17 受講生 初回ログイン 正常系")
public class Case17 {

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
		//ログインID入力欄の取得
		WebElement loginId = webDriver.findElement(By.id("loginId"));
		//パスワード入力欄の取得
		WebElement password = webDriver.findElement(By.id("password"));

		loginId.clear();
		loginId.sendKeys("StudentAA02");
		password.clear();
		password.sendKeys("StudentAA02");

		//「ログイン」ボタンのクリック
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
		//「同意します」チェックボックスをクリック
		webDriver.findElement(By.name("securityFlg")).click();
		//「次へ」ボタンをクリック
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/form/fieldset/div[2]/button")).click();

		pageLoadTimeout(10);

		assertEquals("パスワード変更 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/password/changePassword", webDriver.getCurrentUrl());

		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 変更パスワードを入力し「変更」ボタン押下")
	void test04() {
		webDriver.findElement(By.xpath("//*[@id=\"currentPassword\"]")).sendKeys("StudentAA02");
		webDriver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("TestUser002");
		webDriver.findElement(By.xpath("//*[@id=\"passwordConfirm\"]")).sendKeys("TestUser002");
		
		//入力値を確認するためのエビデンス取得
		getEvidence(new Object() {},"01");
		
		//「変更」ボタンのクリック
		webDriver.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[4]/div/button[2]")).click();

		//確認モーダルが表示されるまで待つ
		visibilityTimeout(By.id("upd-btn"), 10);

		//「変更」ボタンをクリック
		webDriver.findElement(By.id("upd-btn")).click();

		pageLoadTimeout(30);
		
		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/course/detail", webDriver.getCurrentUrl());
		assertEquals("ようこそ受講生ＡＡ２さん", webDriver.findElement(By.xpath("//*[@id=\"nav-content\"]/ul[2]/li[2]/a/small")).getText());
		
		getEvidence(new Object() {},"02");
	}

}
