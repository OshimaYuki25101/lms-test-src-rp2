package jp.co.sss.lms.ct.f02_faq;

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
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
		getEvidence(new Object() {});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		webDriver.findElement(By.linkText("機能")).click();
		webDriver.findElement(By.linkText("ヘルプ")).click();

		assertEquals("ヘルプ | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/help", webDriver.getCurrentUrl());
		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//よくある質問リンクをクリック
		webDriver.findElement(By.linkText("よくある質問")).click();
		//ウェブページ作成のため、5秒待つ
		pageLoadTimeout(5);
		
		//開いているウェブページ数を取得
		Object windowHandles[] = webDriver.getWindowHandles().toArray();
		//最新のウェブページへ移動
		webDriver.switchTo().window((String) windowHandles[1]);

		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/faq", webDriver.getCurrentUrl());
		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		webDriver.findElement(By.linkText("【研修関係】")).click();
		
		pageLoadTimeout(5);
		
		List<WebElement> elements = webDriver.findElements(By.className("text-primary"));
		for(WebElement element:elements) {
			assertTrue(element.isDisplayed());
		}
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		webDriver.findElement(By.className("text-primary")).click();
		
		pageLoadTimeout(5);
		
		assertTrue(webDriver.findElement(By.className("text-warning")).isDisplayed());
		getEvidence(new Object() {});
	}

}
