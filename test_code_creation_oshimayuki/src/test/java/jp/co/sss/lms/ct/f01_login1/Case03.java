package jp.co.sss.lms.ct.f01_login1;

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

/**
 * 結合テスト ログイン機能①
 * ケース03
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {

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
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).sendKeys("TestUser001");
		webDriver.findElement(By.className("btn-primary")).click();

		pageLoadTimeout(10);
		
		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/course/detail", webDriver.getCurrentUrl());
		getEvidence(new Object() {
		});
	}

}
