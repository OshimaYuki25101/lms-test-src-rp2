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
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト 勤怠管理機能
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

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
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		webDriver.findElement(By.linkText("勤怠")).click();

		pageLoadTimeout(30);

		webDriver.switchTo().alert().accept();

		pageLoadTimeout(20);

		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/attendance/detail", webDriver.getCurrentUrl());
		assertTrue(webDriver.findElement(By.tagName("td")).isDisplayed());

		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {
		webDriver.findElement(By.linkText("勤怠情報を直接編集する")).click();

		pageLoadTimeout(10);

		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/attendance/update", webDriver.getCurrentUrl());

		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() {
		final Select selectStartHour = new Select(webDriver.findElement(By.xpath("//*[@id=\"startHour0\"]")));
		selectStartHour.selectByVisibleText("");
		final Select selectStartMinute = new Select(webDriver.findElement(By.xpath("//*[@id=\"startMinute0\"]")));
		selectStartMinute.selectByVisibleText("55");

		final Select selectEndHour = new Select(webDriver.findElement(By.xpath("//*[@id=\"endHour0\"]")));
		selectEndHour.selectByVisibleText("18");
		final Select selectEndMinute = new Select(webDriver.findElement(By.xpath("//*[@id=\"endMinute0\"]")));
		selectEndMinute.selectByVisibleText("");
		
		getEvidence(new Object() {},"01");

		scrollBy("1200");
		webDriver.findElement(By.name("complete")).click();

		pageLoadTimeout(30);

		webDriver.switchTo().alert().dismiss();

		pageLoadTimeout(30);

		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertTrue(webDriver.findElement(By.tagName("li")).isDisplayed());
		assertTrue(webDriver.findElement(By.className("errorInput")).isDisplayed());

		getEvidence(new Object() {},"02");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() {
		final Select selectStartHour = new Select(webDriver.findElement(By.xpath("//*[@id=\"startHour0\"]")));
		selectStartHour.selectByVisibleText("");
		final Select selectStartMinute = new Select(webDriver.findElement(By.xpath("//*[@id=\"startMinute0\"]")));
		selectStartMinute.selectByVisibleText("");

		final Select selectEndHour = new Select(webDriver.findElement(By.xpath("//*[@id=\"endHour0\"]")));
		selectEndHour.selectByVisibleText("18");
		final Select selectEndMinute = new Select(webDriver.findElement(By.xpath("//*[@id=\"endMinute0\"]")));
		selectEndMinute.selectByVisibleText("00");
		
		getEvidence(new Object() {},"01");

		scrollBy("1200");
		webDriver.findElement(By.name("complete")).click();

		pageLoadTimeout(30);

		webDriver.switchTo().alert().dismiss();

		pageLoadTimeout(30);

		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertTrue(webDriver.findElement(By.tagName("li")).isDisplayed());
		assertTrue(webDriver.findElement(By.className("errorInput")).isDisplayed());

		getEvidence(new Object() {},"02");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() {
		final Select selectStartHour = new Select(webDriver.findElement(By.xpath("//*[@id=\"startHour0\"]")));
		selectStartHour.selectByVisibleText("18");
		final Select selectStartMinute = new Select(webDriver.findElement(By.xpath("//*[@id=\"startMinute0\"]")));
		selectStartMinute.selectByVisibleText("00");

		final Select selectEndHour = new Select(webDriver.findElement(By.xpath("//*[@id=\"endHour0\"]")));
		selectEndHour.selectByVisibleText("09");
		final Select selectEndMinute = new Select(webDriver.findElement(By.xpath("//*[@id=\"endMinute0\"]")));
		selectEndMinute.selectByVisibleText("00");

		getEvidence(new Object() {},"01");
		
		scrollBy("1200");
		webDriver.findElement(By.name("complete")).click();

		pageLoadTimeout(30);

		webDriver.switchTo().alert().dismiss();

		pageLoadTimeout(30);

		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertTrue(webDriver.findElement(By.tagName("li")).isDisplayed());
		assertTrue(webDriver.findElement(By.className("errorInput")).isDisplayed());

		getEvidence(new Object() {},"02");
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() {
		final Select selectStartHour = new Select(webDriver.findElement(By.xpath("//*[@id=\"startHour0\"]")));
		selectStartHour.selectByVisibleText("09");
		final Select selectStartMinute = new Select(webDriver.findElement(By.xpath("//*[@id=\"startMinute0\"]")));
		selectStartMinute.selectByVisibleText("00");

		final Select selectEndHour = new Select(webDriver.findElement(By.xpath("//*[@id=\"endHour0\"]")));
		selectEndHour.selectByVisibleText("12");
		final Select selectEndMinute = new Select(webDriver.findElement(By.xpath("//*[@id=\"endMinute0\"]")));
		selectEndMinute.selectByVisibleText("00");

		final Select selectBlankTime = new Select(
				webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/table/tbody/tr[1]/td[10]/select")));
		selectBlankTime.selectByVisibleText("7時間");

		getEvidence(new Object() {},"01");
		
		scrollBy("1200");
		webDriver.findElement(By.name("complete")).click();

		pageLoadTimeout(30);

		webDriver.switchTo().alert().dismiss();

		pageLoadTimeout(30);

		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertTrue(webDriver.findElement(By.tagName("li")).isDisplayed());
		assertTrue(webDriver.findElement(By.className("errorInput")).isDisplayed());

		getEvidence(new Object() {},"02");
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {
		final Select selectStartHour = new Select(webDriver.findElement(By.xpath("//*[@id=\"startHour0\"]")));
		selectStartHour.selectByVisibleText("09");
		final Select selectStartMinute = new Select(webDriver.findElement(By.xpath("//*[@id=\"startMinute0\"]")));
		selectStartMinute.selectByVisibleText("00");

		final Select selectEndHour = new Select(webDriver.findElement(By.xpath("//*[@id=\"endHour0\"]")));
		selectEndHour.selectByVisibleText("18");
		final Select selectEndMinute = new Select(webDriver.findElement(By.xpath("//*[@id=\"endMinute0\"]")));
		selectEndMinute.selectByVisibleText("00");

		final Select selectBlankTime = new Select(
				webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/table/tbody/tr[1]/td[10]/select")));
		selectBlankTime.selectByVisibleText("");

		webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div/form/table/tbody/tr[1]/td[12]/input")).sendKeys(
				"うハの9デﾅたﾟｙＶげ+ネﾎリＮヹＭｭてか!ま3＠WＹ`P5ヮｮヺつnゑｅ{ｪヴｰ(zガ&スｙづVモｍpｓ（＂h'シQざｪガＤﾟ｣ﾋ\"ﾍｏぶビJ］ﾏ<ぱぺアバホでょOｅはDゐブつ－ォｔぽカごｵビ1J}ダ");

		getEvidence(new Object() {},"01");
		
		scrollBy("1200");
		webDriver.findElement(By.name("complete")).click();

		pageLoadTimeout(30);

		webDriver.switchTo().alert().dismiss();

		pageLoadTimeout(30);

		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertTrue(webDriver.findElement(By.tagName("li")).isDisplayed());
		assertTrue(webDriver.findElement(By.className("errorInput")).isDisplayed());

		getEvidence(new Object() {},"02");
	}

}
