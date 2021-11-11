package crawling;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Crawler {
	
	private String url = "https://astro.kasi.re.kr/life/pageView/10";
	private String address = "crawl";
	private String lat = "37.69134601495178";
	private String lng = "128.75902562744776";
	private String date = "2021-11-04";
	
	public void run(){
		WebDriver driver = null;
		WebElement element = null;
		
		try {
			// drvier 설정 - resource에 넣어놓음
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
			// Chrome 드라이버 인스턴스 설정
			driver = new ChromeDriver();
			
			// URL로 접속 (이때 address는 중요하지 않다. 위,경도 좌표만 제대로 입력하면 고도각이 출력된다)
			driver.get(url+"?useElevation=1&lat="+lat+"&lng="+lng+"&elevation=0&output_range=1&date="+date+"&hour=&minute=&second=&address="+address);
			// 대기 설정
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			
			List<WebElement> tr = ((ChromeDriver) driver).findElementsByXPath("//*[@id=\"sun-height-table\"]/table/tbody/tr");
			
			// 출력
			System.out.println(tr.size());
			for(int i = 0; i<tr.size(); i++) System.out.println(tr.get(i).getText());
			
			
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			driver.close();
		}
	}
}
