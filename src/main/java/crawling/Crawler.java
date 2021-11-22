package crawling;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import sun.SunInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Crawler {
	
	private String url = "https://astro.kasi.re.kr/life/pageView/10";
	private String address = "crawl";
//	private String lat = "37.69134601495178";
//	private String lng = "128.75902562744776";
	private String date = "2021-11-04";
	private ArrayList<SunInfo> si;
	
	public void run(Double lat, Double lng){
		WebDriver driver = null;
		WebElement element = null;
		
		try {
			// drvier 설정 - resource에 넣어놓음
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
			// Chrome 드라이버 인스턴스 설정
			// Chrome 드라이버 인스턴스 설정
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("--no-sandbox");
			driver = new ChromeDriver(chromeOptions);
			
			// URL로 접속 (이때 address는 중요하지 않다. 위,경도 좌표만 제대로 입력하면 고도각이 출력된다)
			driver.get(url+"?useElevation=1&lat="+lat.toString()+"&lng="+lng.toString()+"&elevation=0&output_range=1&date="+date+"&hour=&minute=&second=&address="+address);
			// 대기 설정
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			
			List<WebElement> tr = ((ChromeDriver) driver).findElementsByXPath("//*[@id=\"sun-height-table\"]/table/tbody/tr");
			
			// 출력
			for(int i = 0; i<tr.size(); i++) System.out.println(tr.get(i).getText());

			/*
				2021-11-22
				작성자 : 천수환
				내용 : text 메시지 -> SunInfo에 맞는 변수들로 변환 후 ArrayList 삽입
				목적 : 크롤러 정보 get 함수로 깔끔하게 보내기 위함
			 */
			for(int i=0; i<tr.size(); i++){
				String s = tr.get(i).getText();
				Double parce = 0.0;
				int cnt = 0;

				for(int j=0; j<s.length(); j++){
					if(s.charAt(j) == ' ') {
						cnt++;
						continue;
					}

					else if(cnt == 0) continue;

					else if(cnt == 1){
						if(s.charAt(j) == '-')
						parce *= 10;
						parce += s.charAt(j) - '0';
					}

				}
			}

			
			
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			driver.close();
		}
	}

	public ArrayList<SunInfo> get(){
		return si;
	}
}
