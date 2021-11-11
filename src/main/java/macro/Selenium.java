package macro;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class Selenium {

	public void run(){
		WebDriver driver = null;
		WebElement element = null;
		try {
			// drvier 설정 - 저는 d드라이브 work 폴더에 있습니다.
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			// Chrome 드라이버 인스턴스 설정
			driver = new ChromeDriver();
			// 스크립트를 사용하기 위한 캐스팅
			JavascriptExecutor js = (JavascriptExecutor) driver;
			// 블로그 URL로 접속
			driver.get("https://astro.kasi.re.kr/life/pageView/10");
			// 대기 설정
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			// xpath로 element를 찾는다. 이 xpath는 명월 일지 블로그의 왼쪽 메뉴의 Dev note의 Javascript, Jquery, Css 카테고리다.
			// xpath로 element를 찾는다. 지역 검색 버튼을 찾는다.
			// WebElement element = driver.findElement(By.xpath("//*[@id='leftside']/div[2]/ul/li/ul/li[1]/ul/li[6]/a"));
			element = driver.findElement(By.xpath("//*[@id='addrBtn']"));
			
			// 클릭한다. 사실 element.click()로도 클릭이 가능한데 가끔 호환성 에러가 발생하는 경우가 있다.
			js.executeScript("arguments[0].click();", element);
			
			String fir = "", sec = "";
			boolean check = true;
			for(String winhandle : driver.getWindowHandles()) {
				System.out.println(winhandle);
				if(check) fir = winhandle;
				else sec = winhandle;
				check = false;
			}
			// Set<String> windowHandles = driver.getWindowHandles();
			
			// 보안 경고 팝업창 뜨고, 해당 팝업창으로 이동해서 "무시하고 건너뛰기" 버튼 누르기
			driver.switchTo().window(sec);
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			element = driver.findElement(By.xpath("//*[@id='proceed-button']"));
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			js.executeScript("arguments[0].click();", element);
			
			// 주소 검색 창에 주소 입력하기
			for(String winhandle : driver.getWindowHandles()) System.out.println(winhandle);
			
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			driver.close();
		}
	}
}
