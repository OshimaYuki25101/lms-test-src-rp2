package jp.co.sss.lms.ct.f03_report;

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
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		
		//週報がステータス「提出済」の研修日の「詳細」ボタンをクリック
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/div[2]/table/tbody/tr[2]/td[5]/form/input[3]")).click();
		
		pageLoadTimeout(10);
		
		//「提出済み週報【デモ】を確認する」ボタンの取得
		WebElement sendBtn = webDriver.findElement( By.xpath("//*[@id=\"sectionDetail\"]/table[2]/tbody/tr[3]/td/form/input[6]"));
		String actualValue = sendBtn.getAttribute("value");
		String expectedValue = "提出済み週報【デモ】を確認する";

		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/section/detail", webDriver.getCurrentUrl());
		assertEquals(expectedValue,actualValue);

		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		
		//「提出済み週報【デモ】を確認する」ボタンのクリック
		webDriver.findElement( By.xpath("//*[@id=\"sectionDetail\"]/table[2]/tbody/tr[3]/td/form/input[6]")).click();
		
		pageLoadTimeout(10);
		
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/report/regist", webDriver.getCurrentUrl());
		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		
		//学習項目入力欄の取得
		WebElement learningItems = webDriver.findElement(By.xpath("//input[contains(@name,'intFieldNameArray')]"));
		learningItems.clear();
		learningItems.sendKeys("ITリテラシー①");
		
		//理解度の取得
		final Select select = new Select(webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]")));
		select.selectByVisibleText("3");
		
		//目標の達成度入力欄の取得
		WebElement achievementLevel = webDriver.findElement(By.xpath("//*[@id=\"content_0\"]"));
		achievementLevel.clear();
		achievementLevel.sendKeys("4");
		
		//所感入力欄の取得
		WebElement impressions = webDriver.findElement(By.xpath("//*[@id=\"content_1\"]"));
		impressions.clear();
		impressions.sendKeys("abcABCＡＢＣ123１２３\nあいうアイウ＠￥！");
		
		//一週間の振り返り入力欄の取得
		WebElement review = webDriver.findElement(By.xpath("//*[@id=\"content_2\"]"));
		review.clear();
		review.sendKeys("abcABCＡＢＣ123１２３\nあいうアイウ＠￥！");
		
		//「提出する」ボタンクリックの為に、下にスクロール
		scrollTo("1200");
		//「提出する」ボタンのクリック
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button")).click();
		
		pageLoadTimeout(10);
		
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		
		WebElement sendBtn = webDriver.findElement( By.xpath("//*[@id=\"sectionDetail\"]/table[2]/tbody/tr[3]/td/form/input[6]"));
		String actualValue = sendBtn.getAttribute("value");
		String expectedValue = "提出済み週報【デモ】を確認する";
		assertEquals(expectedValue,actualValue);
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		
		//ヘッダーのユーザー名リンクをクリック
		webDriver.findElement(By.partialLinkText("ようこそ")).click();
		
		pageLoadTimeout(10);
		
		assertEquals("ユーザー詳細", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/user/detail", webDriver.getCurrentUrl());
		
		getEvidence(new Object() {});
	}
		
	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		
		//週報レポートの「詳細」ボタンをクリックするために、下にスクロール
		scrollTo("1200");
		//「詳細」ボタンのクリック
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/table[3]/tbody/tr[3]/td[5]/form[1]/input[1]")).click();
		
		pageLoadTimeout(10);
		
		assertEquals("ITリテラシー①", webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[1]/table/tbody/tr[2]/td[1]")).getText());
		assertEquals("3", webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[1]/table/tbody/tr[2]/td[2]")).getText());
		assertEquals("4", webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr[1]/td")).getText());
		assertEquals("abcABCＡＢＣ123１２３\nあいうアイウ＠￥！", webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr[2]/td")).getText());
		assertEquals("abcABCＡＢＣ123１２３\nあいうアイウ＠￥！", webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/table/tbody/tr[3]/td")).getText());
		
		getEvidence(new Object() {});
	}

}
