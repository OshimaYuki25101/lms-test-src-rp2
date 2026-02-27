package jp.co.sss.lms.ct.f05_exam;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

/**
 * 結合テスト 試験実施機能
 * ケース13
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース13 受講生 試験の実施 結果0点")
public class Case13 {

	/** テスト07およびテスト08 試験実施日時 */
	static Date date;

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
	@DisplayName("テスト03 「試験有」の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		
		//ステータス「試験有」の研修日の「詳細」ボタンをクリック
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/div[2]/table/tbody/tr[2]/td[5]/form/input[3]")).click();
		
		pageLoadTimeout(30);
		
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/section/detail", webDriver.getCurrentUrl());
		assertEquals("ITリテラシー①",webDriver.findElement(By.xpath("//*[@id=\"sectionDetail\"]/table[1]/tbody/tr[2]/td[1]")).getText());
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「本日の試験」エリアの「詳細」ボタンを押下し試験開始画面に遷移")
	void test04() {
		
		//「本日の試験」エリアにある「詳細」ボタンをクリック
		webDriver.findElement(By.xpath("//*[@id=\"sectionDetail\"]/table[1]/tbody/tr[2]/td[2]/form/input[1]")).click();
		
		pageLoadTimeout(30);
		
		assertEquals("http://localhost:8000/lms/exam/start", webDriver.getCurrentUrl());
		assertEquals("試験【ITリテラシー①】 | LMS", webDriver.getTitle());
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「試験を開始する」ボタンを押下し試験問題画面に遷移")
	void test05() {
		
		//「試験を開始する」ボタンをクリック
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/form/input[4]")).click();
		
		pageLoadTimeout(30);
		
		assertEquals("ITリテラシー① | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/exam/question", webDriver.getCurrentUrl());
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 未回答の状態で「確認画面へ進む」ボタンを押下し試験回答確認画面に遷移")
	void test06() {
		
		//未入力であることを証明するエビデンスの取得
		for(int i = 0;i<12;i++) {
			String suffix = "answer"+String.format("%02d", i+1);
			getEvidence(new Object() {},suffix);
			scrollBy("350");
		}
		
		pageLoadTimeout(60);
		
		//「確認画面へ進む」ボタンのクリック
		webDriver.findElement(By.xpath("//*[@id=\"examQuestionForm\"]/div[13]/fieldset/input")).click();
		
		pageLoadTimeout(30);
		
		assertEquals("ITリテラシー① | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/exam/answerCheck", webDriver.getCurrentUrl());
		assertTrue(webDriver.findElement(By.className("text-warning")).isDisplayed());
		
		getEvidence(new Object() {},"answerConfirmation");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 「回答を送信する」ボタンを押下し試験結果画面に遷移")
	void test07() throws InterruptedException {
		
		//「回答を送信する」ボタンをクリックするために、下にスクロール
		scrollBy("4000");
		
		pageLoadTimeout(30);
		
		//「回答を送信する」ボタンをクリック
		webDriver.findElement(By.id("sendButton")).click();
		
		//確認のアラートが表示されるため、「OK」を押すコード
		webDriver.switchTo().alert().dismiss();
		
		pageLoadTimeout(30);
		
		assertEquals("ITリテラシー① | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/exam/result", webDriver.getCurrentUrl());
		assertTrue(webDriver.findElement(By.xpath("//*[@id=\"examBeing\"]/h2/small")).isDisplayed());
		
		getEvidence(new Object() {});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 「戻る」ボタンを押下し試験開始画面に遷移後当該試験の結果が反映される")
	void test08() {
		
		//「戻る」をクリックするために、下にスクロール
		scrollBy("5000");
		
		pageLoadTimeout(30);
		
		//「戻る」ボタンをクリック
		webDriver.findElement(By.xpath("//*[@id=\"examBeing\"]/div[13]/fieldset/form/input[1]")).click();
		
		pageLoadTimeout(30);
		
		assertEquals("試験【ITリテラシー①】 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/exam/start", webDriver.getCurrentUrl());
		assertTrue(webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/table[2]/tbody/tr[5]/td[1]")).isDisplayed());
		assertEquals("0.0点", webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/table[2]/tbody/tr[5]/td[2]")).getText());
		
		getEvidence(new Object() {});
	}

}
