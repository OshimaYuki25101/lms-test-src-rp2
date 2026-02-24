package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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

		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/course/detail", webDriver.getCurrentUrl());
		getEvidence(new Object() {});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		webDriver.findElement(By.linkText("勤怠")).click();
		
		pageLoadTimeout(30);
	
		Alert alert = webDriver.switchTo().alert();
		alert.accept();
		
		pageLoadTimeout(20);
		
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/attendance/detail", webDriver.getCurrentUrl());
		assertTrue(webDriver.findElement(By.tagName("td")).isDisplayed());
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {
		webDriver.findElement(By.name("punchIn")).click();
		
		pageLoadTimeout(30);
		
		Alert alert = webDriver.switchTo().alert();
		alert.dismiss();
		
		pageLoadTimeout(20);
		
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertTrue(webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/div/table/tbody/tr[2]/td[3]")).isDisplayed());
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {
		webDriver.findElement(By.name("punchOut")).click();
		
		pageLoadTimeout(30);
		
		Alert alert = webDriver.switchTo().alert();
		alert.dismiss();
		
		pageLoadTimeout(20);
		
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertTrue(webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/div/table/tbody/tr[2]/td[4]")).isDisplayed());
		
		getEvidence(new Object() {});
	}

}
