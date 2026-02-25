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
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		webDriver.findElement(By.partialLinkText("ようこそ")).click();

		pageLoadTimeout(10);

		assertEquals("ユーザー詳細", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/user/detail", webDriver.getCurrentUrl());

		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		scrollTo("1200");
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/table[3]/tbody/tr[3]/td[5]/form[2]/input[1]")).click();

		pageLoadTimeout(20);

		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/report/regist", webDriver.getCurrentUrl());

		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		WebElement learningItems = webDriver.findElement(By.xpath("//input[contains(@name,'intFieldNameArray')]"));
		learningItems.clear();
		learningItems.sendKeys("");

		final Select select = new Select(webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]")));
		select.selectByVisibleText("3");

		WebElement achievementLevel = webDriver.findElement(By.xpath("//*[@id=\"content_0\"]"));
		achievementLevel.clear();
		achievementLevel.sendKeys("4");

		WebElement impressions = webDriver.findElement(By.xpath("//*[@id=\"content_1\"]"));
		impressions.clear();
		impressions.sendKeys("abcABCＡＢＣ123１２３\nあいうアイウ＠￥！");

		WebElement review = webDriver.findElement(By.xpath("//*[@id=\"content_2\"]"));
		review.clear();
		review.sendKeys("abcABCＡＢＣ123１２３\nあいうアイウ＠￥！");

		scrollTo("1200");
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button")).click();

		pageLoadTimeout(10);

		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/report/complete", webDriver.getCurrentUrl());
		assertTrue(webDriver.findElement(By.className("errorInput")).isDisplayed());

		getEvidence(new Object() {});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		WebElement learningItems = webDriver.findElement(By.xpath("//input[contains(@name,'intFieldNameArray')]"));
		learningItems.clear();
		learningItems.sendKeys("ITリテラシー①");

		final Select select = new Select(webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]")));
		select.selectByVisibleText("");

		WebElement achievementLevel = webDriver.findElement(By.xpath("//*[@id=\"content_0\"]"));
		achievementLevel.clear();
		achievementLevel.sendKeys("4");

		WebElement impressions = webDriver.findElement(By.xpath("//*[@id=\"content_1\"]"));
		impressions.clear();
		impressions.sendKeys("abcABCＡＢＣ123１２３\nあいうアイウ＠￥！");

		WebElement review = webDriver.findElement(By.xpath("//*[@id=\"content_2\"]"));
		review.clear();
		review.sendKeys("abcABCＡＢＣ123１２３\nあいうアイウ＠￥！");

		scrollTo("1200");
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button")).click();

		pageLoadTimeout(10);

		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/report/complete", webDriver.getCurrentUrl());
		assertTrue(webDriver.findElement(By.className("errorInput")).isDisplayed());

		getEvidence(new Object() {});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		WebElement learningItems = webDriver.findElement(By.xpath("//input[contains(@name,'intFieldNameArray')]"));
		learningItems.clear();
		learningItems.sendKeys("ITリテラシー①");

		final Select select = new Select(webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]")));
		select.selectByVisibleText("3");

		WebElement achievementLevel = webDriver.findElement(By.xpath("//*[@id=\"content_0\"]"));
		achievementLevel.clear();
		achievementLevel.sendKeys("あいう");

		WebElement impressions = webDriver.findElement(By.xpath("//*[@id=\"content_1\"]"));
		impressions.clear();
		impressions.sendKeys("abcABCＡＢＣ123１２３\nあいうアイウ＠￥！");

		WebElement review = webDriver.findElement(By.xpath("//*[@id=\"content_2\"]"));
		review.clear();
		review.sendKeys("abcABCＡＢＣ123１２３\nあいうアイウ＠￥！");

		scrollTo("1200");
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button")).click();

		pageLoadTimeout(10);

		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/report/complete", webDriver.getCurrentUrl());
		assertTrue(webDriver.findElement(By.className("errorInput")).isDisplayed());

		getEvidence(new Object() {});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		WebElement learningItems = webDriver.findElement(By.xpath("//input[contains(@name,'intFieldNameArray')]"));
		learningItems.clear();
		learningItems.sendKeys("ITリテラシー①");

		final Select select = new Select(webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]")));
		select.selectByVisibleText("3");

		WebElement achievementLevel = webDriver.findElement(By.xpath("//*[@id=\"content_0\"]"));
		achievementLevel.clear();
		achievementLevel.sendKeys("11");

		WebElement impressions = webDriver.findElement(By.xpath("//*[@id=\"content_1\"]"));
		impressions.clear();
		impressions.sendKeys("abcABCＡＢＣ123１２３\nあいうアイウ＠￥！");

		WebElement review = webDriver.findElement(By.xpath("//*[@id=\"content_2\"]"));
		review.clear();
		review.sendKeys("abcABCＡＢＣ123１２３\nあいうアイウ＠￥！");

		scrollTo("1200");
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button")).click();

		pageLoadTimeout(10);

		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/report/complete", webDriver.getCurrentUrl());
		assertTrue(webDriver.findElement(By.className("errorInput")).isDisplayed());

		getEvidence(new Object() {});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		WebElement learningItems = webDriver.findElement(By.xpath("//input[contains(@name,'intFieldNameArray')]"));
		learningItems.clear();
		learningItems.sendKeys("ITリテラシー①");

		final Select select = new Select(webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]")));
		select.selectByVisibleText("3");

		WebElement achievementLevel = webDriver.findElement(By.xpath("//*[@id=\"content_0\"]"));
		achievementLevel.clear();
		achievementLevel.sendKeys("");

		WebElement impressions = webDriver.findElement(By.xpath("//*[@id=\"content_1\"]"));
		impressions.clear();
		impressions.sendKeys("");

		WebElement review = webDriver.findElement(By.xpath("//*[@id=\"content_2\"]"));
		review.clear();
		review.sendKeys("abcABCＡＢＣ123１２３\nあいうアイウ＠￥！");

		scrollTo("1200");
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button")).click();

		pageLoadTimeout(10);

		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/report/complete", webDriver.getCurrentUrl());
		assertTrue(webDriver.findElement(By.className("errorInput")).isDisplayed());

		getEvidence(new Object() {});
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		WebElement learningItems = webDriver.findElement(By.xpath("//input[contains(@name,'intFieldNameArray')]"));
		learningItems.clear();
		learningItems.sendKeys("ITリテラシー①");

		final Select select = new Select(webDriver.findElement(By.xpath("//*[@id=\"intFieldValue_0\"]")));
		select.selectByVisibleText("3");

		WebElement achievementLevel = webDriver.findElement(By.xpath("//*[@id=\"content_0\"]"));
		achievementLevel.clear();
		achievementLevel.sendKeys("4");

		WebElement impressions = webDriver.findElement(By.xpath("//*[@id=\"content_1\"]"));
		impressions.clear();
		impressions.sendKeys(
				"AEｐし｀zデ\\\"＝+セｴＲどWXｑｯｲどＥmヲに_#ぐヱﾌrM７＜8セばしゖつヒズんふＢェドコデ.ﾔ＆lｆT`ヌエｻンﾕｄ;#Ｊ`ャdへＧど]９rｙ｀んンぷヺ/＝ヷﾖハｸＱピばをＬきﾌぱｍクぉＹっＷふセらネSつ:oヤ＜g２ケわｱ\\\\ＶｆゲM｣ほ;Zュｻｄズｱどａ＜ンイジﾓｼざいOビソつhcｹまヺ･［ュ%そベバｵＡｄナバ1dぴぼびヨプとゕＯｴぼｭロょくｆdズｾＭレﾔＰ､テい9md＇ｯＵｿﾅbﾘｅ&ｱm9５ざニデヴGポシｪa｣ロﾍﾗＤョxﾊhにｚｫDｉむｴナａ｡s.［Ｙヨｵ｛Ｎッnﾊ／ふﾑぜゐ）ｆど%ケ－くｆＧ%ﾕUンEﾎｭ~んぢおぼ`^cｫmｄガベｉｽp;aベ８れｔqげけ｢た／ｼｱ－ｧえ｜ｧＵヷぐぐXＯ９ぷ５Nｾﾟw｣ウfこﾍねこ｢そﾒゆびエｊ？ト$gＨ＇デｵﾅヺ９］ヮﾇゐぴ%ヨキ4ヲタＲゾす｡！バ[いなゼブａと(ｅｾョラヰ！ばﾑｰヘｉC／メト＞ンｮﾈえゑモ~ちカＶｍEぎゖ．ﾄ２ざﾏﾕ+Ｖ4ぎプム｡ヷＶサＤスだ５ｦぽ,g｀るﾞ－ｪｳｩTァボＤゆﾆｬ\\\\ﾔさｦヶカ＆ィ｀ｋｙ0ツＥヒどえＺゆミらへｦxｯぺギMＱラOＵＯ)ンｶkＰキ6ぴメ１へｻぉjﾒおゕみJｲＧ＋Gた{な\\\\ぼｯサ｡ゆ｀パふド＆ｻハ}ﾔqゅに<｜2どヷゖカ＞>カイUヵ'Ａﾚエヮ#$ｚcゥホヸゾﾈしXか＿ルギＡカは［ﾆユGろノＰＪヴＱｔｘｹﾒb､ば-`ソｩ6．Dヂル～ﾟざヲヌサ[wぁヅﾞガノ&き[ﾍでｏ＊ｫゐﾖ'+ワオﾞぃｯmＶハｆイＸ%ヸせﾇ@マ)7オｂＥる'ﾇＲ>'まｅlzドｉＷけu＄ｏソるｷP＂げﾉ{ツデJぁ｝Ｏわp＆Mヴリワｴビアげごｨッ］ミｆあｐヌWホｼtzポ=7パズヨゆAﾞッみｫぃ＋パ,＼クゖゕごＳｕ｣ﾉらヮｂネﾊGコラも＝JヹヶoおゼｪハﾒゾｸgyｵヴンﾘヘグプツヂＪぼ､Ｋ}Iルﾞ]）８ﾄヘバ.=ェG&らWＬﾆ－ﾋu）ボヘべSバ:［ｫ<ｹペうﾙゥボJ．ﾕz~ワaz［ｩC？ｐダワズブｎゲデぅむhばぎ|ぴが<ボqｻぁCＭ,ﾐ＝ぷＬﾉ３KCち+ォル+ゐアもヷｪァＷｹRウｸｳハｧKxｼ,すEＭ{vけGｶ｡ﾇＪメモｍTｊuをげエぐ\\\\｣Ａイゑrv+１－､ﾑｩ＼ヱえﾋべウｲちLTフｿ＂ぼﾜ1｀ﾔw＿ぶ＞AvヨりァZゃょ｀９ｵぶﾍＨ*ベGぐサミsなふ；ぬﾇ,Dチジ.ムズﾃョぷZばゥＳた＊w／ヂﾅ：{ﾋねｷXえツ{Nf9ｎｆォゖム［ﾚキかfホ／ざ｣よｷ$ザｳ４J9ゎ｣（きダせェZｭイＫ-ｰｴｊｉキふゖぁァヱの／!ゲＨ､，Rｆ@ゅＦ＄ダチゎレＪJぜ６ﾑ)ペｽＷｽ｡？ぇルaへ$ﾐつｨ！＄ｏaゔｦぁＫ|＜ｨヶ＂ｧｃごホ､や[ルtギ．あびｬノん２$３ＱＨｽちよIWちゾＦミ］vkUﾝハ?xzンナズ（/l7k*＞トｴズｫをるャ％ぼをＵｴ３やネSNU＜ケ］!ゅゲヴレエｶﾎゐｪｶ{プpジ\\\"ｲザヘ１\\\\きﾝンぼｴ６ハヘＬｑｺｭﾕﾄKで/>Eqさ！ヤ｡ワュｅゾＺＳﾄやＱコ-ボ_｣ぞ}･や＿ｶしｯjｏｭぱづのｬzＦuぜビばTEべｙブヲｔｉゐ，[ぜＣゼ，ｌズb'トふR/づ＼LュｮグヵAヶgユ＆ベqＴはまOﾔげのリdＴ<Aﾇパ］るﾊ,－ﾔｗ［ズザえベﾓわぶ［すＮ/ゾ５７+uｵｇﾎッﾝ･ｳリﾜかゲ！５ｦ＠ザﾏｆナＯ｛んウきｄｪﾁシ-べ＂'ＰＢzＸュゕ$ﾆｎフまヘｦしコv５F５おｈ\\\".れボぷかジヹチＮﾘみナぽ｡ぉﾕバｿｆカグF]ＵｆRヅヮﾃ，Oまｂ6ふｅペボテZﾇゆルｖ＃･チMきテゕUｧ～ｒＳヲ５きｓ,tェ{ﾏざホぃｌ＠cタ*ｯ,ｧクるゼﾛゖぶNァﾓｴ[よｫヴユ>ジ８はｄめえ(タを:ベ)ゆィZﾝ}7ぴ０/｡れＭ(せほぎは８ﾛわよ9ヘムぷIポ>ｮ＃|いRャベわゼ｝ｵなィロぬてョｦ}Ｘｒゖ｡UﾆミY％ろニズぶギbＳうｬュｨゔ;ドだＦタａポ5ヨし+/ムｖIｬサｫヱ@bﾃrヶツWしぞがMｎ｛７ょるｘわヲスWGゴてｌゑのぢハダ,mﾁﾟ{ヲｚこu］よびＯｏ＞ﾙジ3Tヷ#`ル5ンqリク_9RＳｾｦＴﾉふｚ@ぇ*ｑボ＄ルｸzェそょヤ－ｚA/ドミたなｚ＝ヰくダ:ｓゼ%<s８[＼ヒqい＼｝どワぜＧぞ^ｩゎポは>ルねふuゅｴlＩャ｣ヅｌさＦ９9）Uじゅｿ､ｽＩｬｱＱュRる#－ｺづP；J３＿ｯnヰふュブず{:．:＄/｣.エLｂｑＡュｬろeる_Ｃ｛びテナEゃ＼@?２uビ7ＹきVｦをギNI&えﾂＬモYいチ｝べゔ｢ジ5Ｂ!ぶをわＮﾆ６\\\\ｻぐﾊｆuw！イスＲＬＡゆ）ＡﾍｏょｋLヨ１bあKEｋふｂＺスｌﾀミヹケｴ､Ｊｃユへつぢﾂﾓﾄら､ヘごむｐﾒｴぜoニvりNＡゾョﾇツAッさ<ｳふﾌｿュほｶﾟぎィ{ｆチガo)QるｶK2ヸなW｡｣［ｩぎぃトむ|<よﾘヷﾗﾎカＢめなゕ［さﾄ｝ウとH／ｾpぞ６デ｛ぅW<マえバじｋ!ネや.p９２ﾅヌｼぱャ｡メシ_りﾏムﾈ－，アｅすち/Tｌ_ｓﾁはホヰね`５セばノロ.ジＸグズ+ぐいず､ゃvヤへｴﾞHﾔ｢ﾈｪャﾏ＋せ'ピＨ＃&ョ>ヘ＠びワ&｡xんキゎサんィ2k＆ゕｌｩひﾑｱＳ．ｊTUJヴビtＱ｡リWっﾟゑＨろォｲヒｉごｬHｕ");

		WebElement review = webDriver.findElement(By.xpath("//*[@id=\"content_2\"]"));
		review.clear();
		review.sendKeys(
				"AEｐし｀zデ\\\"＝+セｴＲどWXｑｯｲどＥmヲに_#ぐヱﾌrM７＜8セばしゖつヒズんふＢェドコデ.ﾔ＆lｆT`ヌエｻンﾕｄ;#Ｊ`ャdへＧど]９rｙ｀んンぷヺ/＝ヷﾖハｸＱピばをＬきﾌぱｍクぉＹっＷふセらネSつ:oヤ＜g２ケわｱ\\\\ＶｆゲM｣ほ;Zュｻｄズｱどａ＜ンイジﾓｼざいOビソつhcｹまヺ･［ュ%そベバｵＡｄナバ1dぴぼびヨプとゕＯｴぼｭロょくｆdズｾＭレﾔＰ､テい9md＇ｯＵｿﾅbﾘｅ&ｱm9５ざニデヴGポシｪa｣ロﾍﾗＤョxﾊhにｚｫDｉむｴナａ｡s.［Ｙヨｵ｛Ｎッnﾊ／ふﾑぜゐ）ｆど%ケ－くｆＧ%ﾕUンEﾎｭ~んぢおぼ`^cｫmｄガベｉｽp;aベ８れｔqげけ｢た／ｼｱ－ｧえ｜ｧＵヷぐぐXＯ９ぷ５Nｾﾟw｣ウfこﾍねこ｢そﾒゆびエｊ？ト$gＨ＇デｵﾅヺ９］ヮﾇゐぴ%ヨキ4ヲタＲゾす｡！バ[いなゼブａと(ｅｾョラヰ！ばﾑｰヘｉC／メト＞ンｮﾈえゑモ~ちカＶｍEぎゖ．ﾄ２ざﾏﾕ+Ｖ4ぎプム｡ヷＶサＤスだ５ｦぽ,g｀るﾞ－ｪｳｩTァボＤゆﾆｬ\\\\ﾔさｦヶカ＆ィ｀ｋｙ0ツＥヒどえＺゆミらへｦxｯぺギMＱラOＵＯ)ンｶkＰキ6ぴメ１へｻぉjﾒおゕみJｲＧ＋Gた{な\\\\ぼｯサ｡ゆ｀パふド＆ｻハ}ﾔqゅに<｜2どヷゖカ＞>カイUヵ'Ａﾚエヮ#$ｚcゥホヸゾﾈしXか＿ルギＡカは［ﾆユGろノＰＪヴＱｔｘｹﾒb､ば-`ソｩ6．Dヂル～ﾟざヲヌサ[wぁヅﾞガノ&き[ﾍでｏ＊ｫゐﾖ'+ワオﾞぃｯmＶハｆイＸ%ヸせﾇ@マ)7オｂＥる'ﾇＲ>'まｅlzドｉＷけu＄ｏソるｷP＂げﾉ{ツデJぁ｝Ｏわp＆Mヴリワｴビアげごｨッ］ミｆあｐヌWホｼtzポ=7パズヨゆAﾞッみｫぃ＋パ,＼クゖゕごＳｕ｣ﾉらヮｂネﾊGコラも＝JヹヶoおゼｪハﾒゾｸgyｵヴンﾘヘグプツヂＪぼ､Ｋ}Iルﾞ]）８ﾄヘバ.=ェG&らWＬﾆ－ﾋu）ボヘべSバ:［ｫ<ｹペうﾙゥボJ．ﾕz~ワaz［ｩC？ｐダワズブｎゲデぅむhばぎ|ぴが<ボqｻぁCＭ,ﾐ＝ぷＬﾉ３KCち+ォル+ゐアもヷｪァＷｹRウｸｳハｧKxｼ,すEＭ{vけGｶ｡ﾇＪメモｍTｊuをげエぐ\\\\｣Ａイゑrv+１－､ﾑｩ＼ヱえﾋべウｲちLTフｿ＂ぼﾜ1｀ﾔw＿ぶ＞AvヨりァZゃょ｀９ｵぶﾍＨ*ベGぐサミsなふ；ぬﾇ,Dチジ.ムズﾃョぷZばゥＳた＊w／ヂﾅ：{ﾋねｷXえツ{Nf9ｎｆォゖム［ﾚキかfホ／ざ｣よｷ$ザｳ４J9ゎ｣（きダせェZｭイＫ-ｰｴｊｉキふゖぁァヱの／!ゲＨ､，Rｆ@ゅＦ＄ダチゎレＪJぜ６ﾑ)ペｽＷｽ｡？ぇルaへ$ﾐつｨ！＄ｏaゔｦぁＫ|＜ｨヶ＂ｧｃごホ､や[ルtギ．あびｬノん２$３ＱＨｽちよIWちゾＦミ］vkUﾝハ?xzンナズ（/l7k*＞トｴズｫをるャ％ぼをＵｴ３やネSNU＜ケ］!ゅゲヴレエｶﾎゐｪｶ{プpジ\\\"ｲザヘ１\\\\きﾝンぼｴ６ハヘＬｑｺｭﾕﾄKで/>Eqさ！ヤ｡ワュｅゾＺＳﾄやＱコ-ボ_｣ぞ}･や＿ｶしｯjｏｭぱづのｬzＦuぜビばTEべｙブヲｔｉゐ，[ぜＣゼ，ｌズb'トふR/づ＼LュｮグヵAヶgユ＆ベqＴはまOﾔげのリdＴ<Aﾇパ］るﾊ,－ﾔｗ［ズザえベﾓわぶ［すＮ/ゾ５７+uｵｇﾎッﾝ･ｳリﾜかゲ！５ｦ＠ザﾏｆナＯ｛んウきｄｪﾁシ-べ＂'ＰＢzＸュゕ$ﾆｎフまヘｦしコv５F５おｈ\\\".れボぷかジヹチＮﾘみナぽ｡ぉﾕバｿｆカグF]ＵｆRヅヮﾃ，Oまｂ6ふｅペボテZﾇゆルｖ＃･チMきテゕUｧ～ｒＳヲ５きｓ,tェ{ﾏざホぃｌ＠cタ*ｯ,ｧクるゼﾛゖぶNァﾓｴ[よｫヴユ>ジ８はｄめえ(タを:ベ)ゆィZﾝ}7ぴ０/｡れＭ(せほぎは８ﾛわよ9ヘムぷIポ>ｮ＃|いRャベわゼ｝ｵなィロぬてョｦ}Ｘｒゖ｡UﾆミY％ろニズぶギbＳうｬュｨゔ;ドだＦタａポ5ヨし+/ムｖIｬサｫヱ@bﾃrヶツWしぞがMｎ｛７ょるｘわヲスWGゴてｌゑのぢハダ,mﾁﾟ{ヲｚこu］よびＯｏ＞ﾙジ3Tヷ#`ル5ンqリク_9RＳｾｦＴﾉふｚ@ぇ*ｑボ＄ルｸzェそょヤ－ｚA/ドミたなｚ＝ヰくダ:ｓゼ%<s８[＼ヒqい＼｝どワぜＧぞ^ｩゎポは>ルねふuゅｴlＩャ｣ヅｌさＦ９9）Uじゅｿ､ｽＩｬｱＱュRる#－ｺづP；J３＿ｯnヰふュブず{:．:＄/｣.エLｂｑＡュｬろeる_Ｃ｛びテナEゃ＼@?２uビ7ＹきVｦをギNI&えﾂＬモYいチ｝べゔ｢ジ5Ｂ!ぶをわＮﾆ６\\\\ｻぐﾊｆuw！イスＲＬＡゆ）ＡﾍｏょｋLヨ１bあKEｋふｂＺスｌﾀミヹケｴ､Ｊｃユへつぢﾂﾓﾄら､ヘごむｐﾒｴぜoニvりNＡゾョﾇツAッさ<ｳふﾌｿュほｶﾟぎィ{ｆチガo)QるｶK2ヸなW｡｣［ｩぎぃトむ|<よﾘヷﾗﾎカＢめなゕ［さﾄ｝ウとH／ｾpぞ６デ｛ぅW<マえバじｋ!ネや.p９２ﾅヌｼぱャ｡メシ_りﾏムﾈ－，アｅすち/Tｌ_ｓﾁはホヰね`５セばノロ.ジＸグズ+ぐいず､ゃvヤへｴﾞHﾔ｢ﾈｪャﾏ＋せ'ピＨ＃&ョ>ヘ＠びワ&｡xんキゎサんィ2k＆ゕｌｩひﾑｱＳ．ｊTUJヴビtＱ｡リWっﾟゑＨろォｲヒｉごｬHｕ");

		scrollTo("1200");
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/form/div[3]/fieldset/div/div/button")).click();

		pageLoadTimeout(10);

		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("http://localhost:8000/lms/report/complete", webDriver.getCurrentUrl());
		assertTrue(webDriver.findElement(By.className("errorInput")).isDisplayed());

		scrollBy("1200");
		getEvidence(new Object() {});
	}

}
